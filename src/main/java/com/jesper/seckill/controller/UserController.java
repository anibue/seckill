package com.jesper.seckill.controller;

import com.jesper.seckill.entity.User;
import com.jesper.seckill.result.Result;
import com.jesper.seckill.service.UserService;
import com.jesper.seckill.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@Slf4j
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    @ResponseBody
    @Operation(summary = "获取用户信息")
    public Result<User> info(HttpServletResponse response,
                             @CookieValue(value = UserServiceImpl.COOKIE_NAME_TOKEN, required = false) String token) {
        User user = userService.getByToken(response, token);
        return Result.success(user);
    }
}
