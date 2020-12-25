package com.spring.secondskill.controller;

import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.result.Result;
import com.spring.secondskill.service.SecondsKillUserService;
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
@RequestMapping("/login")
public class LoginController {

    /*
        在日志中打印对象信息
     */
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    SecondsKillUserService secondsKillUserService;

    @RequestMapping("/to_login")
    public String toLogin(){

        return "login";
    }

    /*
        点击登录后触发/do_login
     */
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse httpServletResponse, @Valid LoginVo loginVo){
        log.info(loginVo.toString());
        /*
            登录
         */
        secondsKillUserService.login(httpServletResponse, loginVo);
        return Result.success(true);
    }
}
