package com.spring.secondskill.controller;

import com.spring.secondskill.domain.SecondsKillUser;
import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.service.SecondsKillService;
import com.spring.secondskill.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

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
    public String toLogin(
            Model model, SecondsKillUser secondsKillUser){
        model.addAttribute("user", secondsKillUser);
        return "goods_list";
    }

}
