package com.spring.secondskill.controller;

import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.result.Result;
import com.spring.secondskill.service.SecondsKillService;
import com.spring.secondskill.service.UserService;
import com.spring.secondskill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    SecondsKillService secondsKillService;

    @RequestMapping("/to_list")
    public String toLogin(){
        return "goods_list";
    }

}
