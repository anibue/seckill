package com.jesper.seckill.controller;

import com.jesper.seckill.entity.User;
import com.jesper.seckill.result.Result;
import com.jesper.seckill.service.GoodsService;
import com.jesper.seckill.service.UserService;
import com.jesper.seckill.service.impl.UserServiceImpl;
import com.jesper.seckill.vo.GoodsDetailVo;
import com.jesper.seckill.vo.GoodsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/goods")
@Slf4j
@Tag(name = "商品管理", description = "商品相关接口")
public class GoodsController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/to_list")
    @Operation(summary = "商品列表页面")
    public String list(HttpServletResponse response, Model model,
                       @CookieValue(value = UserServiceImpl.COOKIE_NAME_TOKEN, required = false) String token) {
        User user = userService.getByToken(response, token);
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @GetMapping("/to_detail2/{goodsId}")
    @Operation(summary = "商品详情页面")
    public String detail2(HttpServletResponse response, Model model,
                          @CookieValue(value = UserServiceImpl.COOKIE_NAME_TOKEN, required = false) String token,
                          @PathVariable("goodsId") long goodsId) {
        User user = userService.getByToken(response, token);
        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        int remainSeconds = 0;

        if (now < startTime) {
            seckillStatus = 0;
            remainSeconds = (int) ((startTime - now) / 1000);
        } else if (now > endTime) {
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }

    @GetMapping("/detail/{goodsId}")
    @ResponseBody
    @Operation(summary = "商品详情API")
    public Result<GoodsDetailVo> detail(HttpServletResponse response,
                                        @CookieValue(value = UserServiceImpl.COOKIE_NAME_TOKEN, required = false) String token,
                                        @PathVariable("goodsId") long goodsId) {
        User user = userService.getByToken(response, token);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        int remainSeconds = 0;

        if (now < startTime) {
            seckillStatus = 0;
            remainSeconds = (int) ((startTime - now) / 1000);
        } else if (now > endTime) {
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            seckillStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setSeckillStatus(seckillStatus);

        return Result.success(vo);
    }
}
