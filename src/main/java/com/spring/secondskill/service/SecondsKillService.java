package com.spring.secondskill.service;

import com.spring.secondskill.dao.SecondsKillDao;
import com.spring.secondskill.domain.SecondsKillUser;
import com.spring.secondskill.result.CodeMsg;
import com.spring.secondskill.util.MD5Util;
import com.spring.secondskill.vo.LoginVo;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

@Service
public class SecondsKillService implements SecondsKillDao {

    @Autowired
    SecondsKillDao secondskillDao;

    @Override
    public SecondsKillUser getById(long id) {
        return secondskillDao.getById(id);
    }

    public CodeMsg login(LoginVo loginVo){
        if(loginVo == null){
            return CodeMsg.SERVER_ERROR;
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

        //判断手机号是否存在
        SecondsKillUser secondsKillUser = getById(Long.parseLong(mobile));
        if(secondsKillUser == null){
            return CodeMsg.MOBILE_NOT_EXIST;
        }

        //验证密码
        String dbPass = secondsKillUser.getPassword();
        String slatDB = secondsKillUser.getSalt();
        String clacPass = MD5Util.formPassToDBPass(formPass, slatDB);
        if(!clacPass.equals(dbPass)){
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;
    }
}
