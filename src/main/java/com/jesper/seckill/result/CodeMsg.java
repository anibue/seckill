package com.jesper.seckill.result;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CodeMsg {
    
    private int code;
    private String msg;
    
    // 通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    
    // 登录异常
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500211, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500212, "密码错误");
    
    // 秒杀异常
    public static CodeMsg SECKILL_OVER = new CodeMsg(500500, "商品已经秒杀完毕");
    public static CodeMsg REPEATE_SECKILL = new CodeMsg(500501, "不能重复秒杀");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500502, "访问频率过高");
    
    // 订单异常
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500600, "订单不存在");
    
    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }
}
