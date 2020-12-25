package com.spring.secondskill.service;

import com.spring.secondskill.dao.GoodsDao;
import com.spring.secondskill.domain.Goods;
import com.spring.secondskill.domain.SecondsKillGoods;
import com.spring.secondskill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

//    NEW
//    public boolean reduceStock(GoodsVo goodsVo) {
//        SecondsKillGoods secondsKillGoods = new SecondsKillGoods();
//        secondsKillGoods.setGoodsId(goodsVo.getId());
//        int ret = goodsDao.reduceStock(secondsKillGoods);
//        return ret > 0;
//    }

//    OLD
    public void reduceStock(GoodsVo goodsVo){
        SecondsKillGoods secondsKillGoods = new SecondsKillGoods();
        secondsKillGoods.setGoodsId(goodsVo.getId());
        goodsDao.reduceStock(secondsKillGoods);
    }


    public void resetStock(List<GoodsVo> goodsList) {
        for(GoodsVo goods : goodsList ) {
            SecondsKillGoods secondsKillGoods = new SecondsKillGoods();
            secondsKillGoods.setGoodsId(goods.getId());
            secondsKillGoods.setStockCount(goods.getStockCount());
            goodsDao.resetStock(secondsKillGoods);
        }
    }
}
