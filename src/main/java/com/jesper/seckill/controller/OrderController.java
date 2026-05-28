package com.jesper.seckill.controller;

import com.jesper.seckill.entity.OrderInfo;
import com.jesper.seckill.entity.User;
import com.jesper.seckill.result.CodeMsg;
import com.jesper.seckill.result.Result;
import com.jesper.seckill.service.OrderService;
import com.jesper.seckill.service.UserService;
import com.jesper.seckill.service.impl.UserServiceImpl;
import com.jesper.seckill.vo.GoodsVo;
import com.jesper.seckill.vo.OrderDetailVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
@Slf4j
@Tag(name = "订单管理", description = "订单相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/detail")
    @ResponseBody
    @Operation(summary = "订单详情")
    public Result<OrderDetailVo> detail(HttpServletResponse response,
                                        @CookieValue(value = UserServiceImpl.COOKIE_NAME_TOKEN, required = false) String token,
                                        @RequestParam("orderId") long orderId) {
        User user = userService.getByToken(response, token);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if (orderInfo == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        GoodsVo goods = new GoodsVo();
        // 这里需要根据goodsId查询商品信息，简化处理
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrderInfo(orderInfo);
        return Result.success(vo);
    }
}
