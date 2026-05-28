package com.jesper.seckill.vo;

import com.jesper.seckill.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailVo {
    private GoodsVo goods;
    private User user;
    private int seckillStatus;
    private int remainSeconds;
}
