package com.jesper.seckill.result;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Result<T> {
    
    private int code;
    private String msg;
    private T data;
    
    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }
    
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }
    
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<>(codeMsg.getCode(), codeMsg.getMsg());
    }
}
