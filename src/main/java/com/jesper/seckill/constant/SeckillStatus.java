package com.jesper.seckill.constant;

public enum SeckillStatus {
    
    NOT_START(0, "秒杀未开始"),
    IN_PROGRESS(1, "秒杀进行中"),
    ENDED(2, "秒杀已结束"),
    SUCCESS(3, "秒杀成功"),
    FAILED(4, "秒杀失败"),
    REPEAT(5, "重复秒杀"),
    QUEUE(6, "排队中");
    
    private final int code;
    private final String message;
    
    SeckillStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
}
