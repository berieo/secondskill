package com.spring.secondskill.service;

import com.spring.secondskill.dao.GoodsDao;
import com.spring.secondskill.domain.Goods;
import com.spring.secondskill.domain.OrderInfo;
import com.spring.secondskill.domain.SecondsKillUser;
import com.spring.secondskill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecondsKillService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo secondsKill(SecondsKillUser secondsKillUser, GoodsVo goodsVo){
        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goodsVo);
        return orderService.createOrder(secondsKillUser, goodsVo);


    }
}

