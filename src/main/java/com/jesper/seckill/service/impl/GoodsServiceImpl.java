package com.jesper.seckill.service.impl;

import com.jesper.seckill.entity.SeckillGoods;
import com.jesper.seckill.mapper.GoodsMapper;
import com.jesper.seckill.service.GoodsService;
import com.jesper.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    public boolean reduceStock(GoodsVo goods) {
        SeckillGoods sg = new SeckillGoods();
        sg.setGoodsId(goods.getId());
        sg.setVersion(goods.getVersion());
        // MQ单线程消费已序列化写入，无需乐观锁重试
        int ret = goodsMapper.reduceStockByVersion(sg);
        return ret > 0;
    }
}
