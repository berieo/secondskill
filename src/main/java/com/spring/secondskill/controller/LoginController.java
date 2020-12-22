package com.spring.secondskill.controller;

import com.spring.secondskill.domain.SecondsKillUser;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

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
    public Result<Boolean> doLogin(HttpServletResponse httpServletResponse, @Valid LoginVo loginVo){
        log.info(loginVo.toString());
        /*
            登录
         */
        secondsKillService.login(httpServletResponse, loginVo);
        return Result.success(true);
    }
}
