package com.spring.secondskill.controller;

import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "Login";
    }

    @RequestMapping("/doLogin")
    public String doLogin(){
        return "doLogin";
    }

}
