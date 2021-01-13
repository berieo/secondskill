package com.spring.secondskill.service;

import com.spring.secondskill.dao.SecondsKillDao;
import com.spring.secondskill.domain.SecondsKillUser;
import com.spring.secondskill.exception.GlobalException;
import com.spring.secondskill.redis.RedisService;
import com.spring.secondskill.redis.SecondsKillUserKey;
import com.spring.secondskill.result.CodeMsg;
import com.spring.secondskill.util.MD5Util;
import com.spring.secondskill.util.UUIDUtil;
import com.spring.secondskill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class SecondsKillUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    SecondsKillDao secondskillDao;

    @Autowired
    RedisService redisService;


    public SecondsKillUser getById(long id) {
        //对象缓存, 取缓存
        SecondsKillUser secondsKillUser = redisService.get(SecondsKillUserKey.getById,""+id,SecondsKillUser.class);
        if(secondsKillUser != null){
            return secondsKillUser;
        }
        //取数据库
        secondsKillUser = secondskillDao.getById(id);
        if(secondsKillUser != null){
            redisService.set(SecondsKillUserKey.getById,""+id, secondsKillUser);
        }
        return secondsKillUser;
    }

    public boolean updatePassword(String token, long id, String formPass){
        //取user
        SecondsKillUser secondsKillUser = getById(id);
        if(secondsKillUser == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //新建一个user，不使用前面的user，修改哪个字段更新哪个字段
        SecondsKillUser toBeUpdate = new SecondsKillUser();
        //更新数据库
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, secondsKillUser.getSalt()));
        secondskillDao.update(toBeUpdate);
        //更新缓存
        redisService.delete(SecondsKillUserKey.getById, ""+id);
        secondsKillUser.setPassword(toBeUpdate.getPassword());
        redisService.set(SecondsKillUserKey.token, token, secondsKillUser);
        return true;

    }

    public SecondsKillUser getByToken(HttpServletResponse httpServletResponse, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        SecondsKillUser secondsKillUser = redisService.get(SecondsKillUserKey.token, token, SecondsKillUser.class);
        //延长有效期
        if(secondsKillUser != null){
            addCookie(httpServletResponse, token, secondsKillUser);
        }
        return secondsKillUser;
    }

    public String login(HttpServletResponse httpServletResponse, LoginVo loginVo){
        if(loginVo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

        //验证手机号是否存在
        SecondsKillUser secondsKillUser = getById(Long.parseLong(mobile));
        if(secondsKillUser == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //验证密码
        String dbPass = secondsKillUser.getPassword();
        String slatDB = secondsKillUser.getSalt();
        String clacPass = MD5Util.formPassToDBPass(formPass, slatDB);
        if(!clacPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(httpServletResponse, token, secondsKillUser);
        return token;
    }

    private void addCookie(HttpServletResponse httpServletResponse,String token, SecondsKillUser secondsKillUser){
        /*
            token保存到redis中
            secondsKillUser在前端装配上
         */
        redisService.set(SecondsKillUserKey.token, token, secondsKillUser);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SecondsKillUserKey.token.expireSeconds());
        /*
            可以在secondskill文件夹下的所有应用共享cookie
         */
        cookie.setPath("/");
        /*
            cookie加入到Response中去
         */
        httpServletResponse.addCookie(cookie);
    }
}
