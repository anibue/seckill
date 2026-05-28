package com.jesper.seckill.vo;

import com.jesper.seckill.entity.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {
    private OrderInfo orderInfo;
    private GoodsVo goods;
}
