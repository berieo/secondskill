package com.spring.secondskill.controller;

import com.spring.secondskill.domain.User;
import com.spring.secondskill.rabbitmq.MQReceiver;
import com.spring.secondskill.rabbitmq.MQSender;
import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.redis.UserKey;
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

    @Autowired
    MQSender mqSender;

    @RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq() {
		mqSender.send("hello");
        return Result.success("Hello，world");
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> topic() {
        mqSender.sendTopic("topic");
        return Result.success("Hello，world");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> fanout() {
		mqSender.sendFanout("fanout");
        return Result.success("Hello，world");
    }


    @RequestMapping("/mq/header")
    @ResponseBody
    public Result<String> header() {
        mqSender.sendFanout("fanout");
        return Result.success("Hello，world");
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        /*
        Model 存的是实体类的模型,前台需要什么数据，model就包含什么数据就行了
        model是用于前端页面数据展示的，而entity则是与数据库进行交互做存储用途。
         */
        model.addAttribute("name","yang");
        return "hello";
    }

    /*
        从Mysql拿数据
     */
    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbget(){

        User user = userService.getById(1);
        return Result.success(user);
    }

    /*
        往Mysql插入数据
     */
    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        userService.tx();
        return Result.success(true);
    }

    /*
        Redis get操作
     */
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisget(){
        User user = redisService.get(UserKey.getById,""+1, User.class);
        return Result.success(user);
    }

    /*
        Redis set操作
     */
    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user  = new User(3, "333");

        boolean v1 = redisService.set(UserKey.getById, ""+1, "222");//UserKey:id1
        return Result.success(v1);
    }

    /*
       Redis exist操作
    */
    @RequestMapping("/redis/exist")
    @ResponseBody
    public Result<Boolean> redisExist() {
        User user  = new User(3, "333");

        boolean v1 = redisService.exist(UserKey.getById, ""+2);//UserKey:id1
        return Result.success(v1);
    }

    /*
        Redis incr操作
    */
    @RequestMapping("/redis/incr")
    @ResponseBody
    public Result<Long> redisIncr() {
        User user  = new User(3, "333");

        Long v1 = redisService.incr(UserKey.getById, ""+1);//UserKey:id1
        return Result.success(v1);
    }

    /*
        Redis decr操作
    */
    @RequestMapping("/redis/decr")
    @ResponseBody
    public Result<Long> redisDecr() {
        User user  = new User(3, "333");

        Long v1 = redisService.decr(UserKey.getById, ""+1);//UserKey:id1
        return Result.success(v1);
    }


}
