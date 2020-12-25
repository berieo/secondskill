package com.spring.secondskill.service;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.spring.secondskill.dao.GoodsDao;
import com.spring.secondskill.dao.OrderDao;
import com.spring.secondskill.domain.OrderInfo;
import com.spring.secondskill.domain.SecondsKillGoods;
import com.spring.secondskill.domain.SecondsKillOrder;
import com.spring.secondskill.domain.SecondsKillUser;
import com.spring.secondskill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public SecondsKillOrder getSecondsKillByUserIdGoodsId(long id, long goodsId){
        return orderDao.getSecondsKillOrderByUserIdGoodsId(id, goodsId);
    }

    //创建订单
    @Transactional
    public OrderInfo createOrder(SecondsKillUser secondsKillUser, GoodsVo goodsVo) {
        //更改订单表
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getSecondsKillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(secondsKillUser.getId());
        long orderId = orderDao.insert(orderInfo);

        //更改秒杀订单表
        SecondsKillOrder secondsKillOrder = new SecondsKillOrder();
        secondsKillOrder.setGoodsId(goodsVo.getId());
        secondsKillOrder.setOrderId(orderId);
        secondsKillOrder.setUserId(secondsKillUser.getId());
        orderDao.insertSecondsKillOrder(secondsKillOrder);

        return orderInfo;
    }
}
