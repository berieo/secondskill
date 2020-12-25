package com.spring.secondskill.controller;

import com.spring.secondskill.domain.OrderInfo;
import com.spring.secondskill.domain.SecondsKillOrder;
import com.spring.secondskill.domain.SecondsKillUser;
import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.result.CodeMsg;
import com.spring.secondskill.service.*;
import com.spring.secondskill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/secondskill")
public class SecondsKillController {

    private static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    SecondsKillUserService secondsKillUserService;

    @Autowired
    SecondsKillService secondsKillService;

    @Autowired
    OrderService orderService;

    @RequestMapping("/do_secondskill")
    public String toLogin(
            Model model,
            SecondsKillUser secondsKillUser,
            @RequestParam("goodsId")long goodsId){
        model.addAttribute("user", secondsKillUser);

        //查询商品列表
        if(secondsKillUser == null){
            return "login";
        }

        //判断库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if(stock <= 0){
            model.addAttribute("errmsg" , CodeMsg.MIAO_SHA_OVER.getMsg());
            return "secondskill_fail";
        }

        //判断是否秒杀到了
        SecondsKillOrder secondsKillOrder = orderService.getSecondsKillByUserIdGoodsId(secondsKillUser.getId(), goodsId);
        if(secondsKillOrder != null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
        }

        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = secondsKillService.secondsKill(secondsKillUser, goodsVo);
        model.addAttribute("orderinfo", orderInfo);
        model.addAttribute("goods", goodsVo);
        return "order_detail";
    }

}
