package com.jesper.seckill.exception;

import com.jesper.seckill.result.CodeMsg;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    
    private final CodeMsg codeMsg;
    
    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
    }
}
