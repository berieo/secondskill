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
    public Result<String> redisget(){
        String v1 = redisService.get("key1", String.class);
        return Result.success(v1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user  = new User();
        user.setId(1);
        user.setName("111");
        boolean v1 = redisService.set("key2", "222");//UserKey:id1
        return Result.success(v1);
    }

}
