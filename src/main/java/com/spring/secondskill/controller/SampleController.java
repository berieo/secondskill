package com.spring.secondskill.controller;

import com.spring.secondskill.domain.User;
import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.result.CodeMsg;
import com.spring.secondskill.result.Result;
import com.spring.secondskill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        /*
        Model 存的是实体类的模型,前台需要什么数据，model就包含什么数据就行了
        model是用于前端页面数据展示的，而entity则是与数据库进行交互做存储用途。
         */
        model.addAttribute("name","yang");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbget(){

        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<Boolean> redisget(){
        redisService.get("1", User.class);
        return Result.success(true);
    }

}
