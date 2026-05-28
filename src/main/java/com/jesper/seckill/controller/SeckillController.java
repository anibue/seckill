package com.jesper.seckill.controller;

import com.jesper.seckill.entity.SeckillOrder;
import com.jesper.seckill.entity.User;
import com.jesper.seckill.rabbitmq.MQSender;
import com.jesper.seckill.rabbitmq.SeckillMessage;
import com.jesper.seckill.redis.GoodsKey;
import com.jesper.seckill.redis.RedisService;
import com.jesper.seckill.result.CodeMsg;
import com.jesper.seckill.result.Result;
import com.jesper.seckill.service.GoodsService;
import com.jesper.seckill.service.OrderService;
import com.jesper.seckill.service.SeckillService;
import com.jesper.seckill.service.UserService;
import com.jesper.seckill.service.impl.UserServiceImpl;
import com.jesper.seckill.vo.GoodsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/seckill")
@Slf4j
@Tag(name = "秒杀管理", description = "秒杀相关接口")
public class SeckillController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;

    @Autowired
    private UserService userService;

    // 本地标记
    private Map<Long, Boolean> localOverMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if (goodsVoList != null) {
            for (GoodsVo goods : goodsVoList) {
                // 仅当键不存在时才设置，避免重启时重置已结束的秒杀库存
                redisService.setIfAbsent(GoodsKey.getGoodsStock, "" + goods.getId(), goods.getStockCount());
                localOverMap.put(goods.getId(), false);
            }
        }
    }

    @PostMapping("/do_seckill")
    @ResponseBody
    @Operation(summary = "执行秒杀")
    public Result<Integer> doSeckill(HttpServletResponse response,
                                     @CookieValue(value = UserServiceImpl.COOKIE_NAME_TOKEN, required = false) String token,
                                     @RequestParam("goodsId") long goodsId) {
        User user = userService.getByToken(response, token);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 内存标记
        if (localOverMap.getOrDefault(goodsId, false)) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        // 判断重复秒杀（先去重，避免重复请求消耗库存）
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }

        // 预减库存
        Long stock = redisService.decr(GoodsKey.getGoodsStock, "" + goodsId);
        if (stock == null || stock < 0) {
            localOverMap.put(goodsId, true);
            // 库存不足时回补Redis库存，避免负数
            redisService.incr(GoodsKey.getGoodsStock, "" + goodsId);
            return Result.error(CodeMsg.SECKILL_OVER);
        }

        // 入队
        SeckillMessage message = new SeckillMessage();
        message.setUser(user);
        message.setGoodsId(goodsId);
        sender.sendSeckillMessage(message);
        return Result.success(0); // 排队中
    }

    @GetMapping("/result")
    @ResponseBody
    @Operation(summary = "获取秒杀结果")
    public Result<Long> seckillResult(HttpServletResponse response,
                                      @CookieValue(value = UserServiceImpl.COOKIE_NAME_TOKEN, required = false) String token,
                                      @RequestParam("goodsId") long goodsId) {
        User user = userService.getByToken(response, token);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long orderId = seckillService.getSeckillResult(user.getId(), goodsId);
        return Result.success(orderId);
    }
}
