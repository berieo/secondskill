package com.spring.secondskill.controller;

import com.spring.secondskill.domain.SecondsKillUser;
import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.service.GoodsService;
import com.spring.secondskill.service.SecondsKillService;
import com.spring.secondskill.service.UserService;
import com.spring.secondskill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    SecondsKillService secondsKillService;

    @RequestMapping("/to_list")
    public String toLogin(Model model, SecondsKillUser secondsKillUser){
        model.addAttribute("user", secondsKillUser);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        /*
            goodList需要与前端保持一致
         */
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    /*
        URL变量可以用{}动态指定
     */
    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, SecondsKillUser secondsKillUser, @PathVariable("goodsId")long goodsId){
        model.addAttribute("user", secondsKillUser);
        System.out.println("here");

        @NotNull
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        //获取秒杀开始结束时间
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int secondsKillStatus = 0; //秒杀状态码
        int remianSeconds = 0; //剩余多少时间


        if(now < startAt){  //秒杀未开始
            secondsKillStatus = 0;
            remianSeconds = (int) ((startAt - now) / 1000);
        }else if(now > endAt){ //秒杀已结束
            secondsKillStatus = 2;
            remianSeconds = -1;
        }else{   //秒杀进行中
            secondsKillStatus = 1;
            remianSeconds = 0;
        }
        System.out.println("remainSeconds"+remianSeconds);
        System.out.println("secondsKillStatus"+secondsKillStatus);
        model.addAttribute("secondsKillStatus", secondsKillStatus);
        model.addAttribute("remainSeconds", remianSeconds);
        return "goods_detail";
    }

}
