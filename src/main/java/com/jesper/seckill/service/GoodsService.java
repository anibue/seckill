package com.jesper.seckill.service;

import com.jesper.seckill.vo.GoodsVo;

import java.util.List;

public interface GoodsService {
    
    List<GoodsVo> listGoodsVo();
    
    GoodsVo getGoodsVoByGoodsId(long goodsId);
    
    boolean reduceStock(GoodsVo goods);
}
