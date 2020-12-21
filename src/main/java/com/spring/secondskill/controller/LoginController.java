package com.spring.secondskill.controller;

import com.spring.secondskill.domain.User;
import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.result.CodeMsg;
import com.spring.secondskill.result.Result;
import com.spring.secondskill.service.SecondsKillService;
import com.spring.secondskill.service.UserService;
import com.spring.secondskill.util.ValidatorUtil;
import com.spring.secondskill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    SecondsKillService secondsKillService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    /*
        点击登录后触发/do_login
     */
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginVo loginVo){
        log.info(loginVo.toString());
        String mobile = loginVo.getMobile();
        String passInput = loginVo.getPassword();
        /*
            判断是否位空
            判断电话号码是否正确
         */
        if(StringUtils.isEmpty(passInput)){
            Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(mobile)){
            Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if(!ValidatorUtil.isMobile(loginVo.getMobile())){
            Result.error(CodeMsg.MOBILE_ERROR);
        }

        /*
            登录
         */
        CodeMsg cm = secondsKillService.login(loginVo);
        if(cm.getCode() == 0){
            return Result.success(true);
        }else{
            return Result.error(cm);
        }
    }




}
