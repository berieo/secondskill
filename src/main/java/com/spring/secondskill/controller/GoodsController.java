package com.spring.secondskill.controller;

import com.spring.secondskill.domain.Goods;
import com.spring.secondskill.domain.SecondsKillUser;
import com.spring.secondskill.redis.GoodsKey;
import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.service.GoodsService;
import com.spring.secondskill.service.SecondsKillUserService;
import com.spring.secondskill.service.UserService;
import com.spring.secondskill.vo.GoodsVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    SecondsKillUserService secondsKillUserService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model, SecondsKillUser secondsKillUser){
        model.addAttribute("user", secondsKillUser);
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        System.out.println(html);
        if(!StringUtils.isEmpty(html)) {
            return html;
        }
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        //return "goods_list";
        WebContext ctx  = new WebContext(httpServletRequest, httpServletResponse, httpServletRequest.getServletContext(),
                httpServletRequest.getLocale(), model.asMap());
        //缓存中没有数据的时候手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    /*
        URL变量可以用{}动态指定
     */
    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model, SecondsKillUser secondsKillUser, @PathVariable("goodsId")long goodsId){
        model.addAttribute("user", secondsKillUser);

        //取URL缓存,不同URL有不同缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, "", String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
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
        model.addAttribute("secondsKillStatus", secondsKillStatus);
        model.addAttribute("remainSeconds", remianSeconds);

        //return "goods_detail";
        //缓存中没有数据的时候手动渲染
        WebContext ctx  = new WebContext(httpServletRequest, httpServletResponse, httpServletRequest.getServletContext(),
                httpServletRequest.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "", html);
        }
        return html;
    }

}
