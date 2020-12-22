package com.spring.secondskill.service;

import com.spring.secondskill.dao.UserDao;
import com.spring.secondskill.domain.SecondsKillUser;
import com.spring.secondskill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx(){
        User u1 = new User(2, "222");
        userDao.insert(u1);

        User u2 = new User(3, "333");
        userDao.insert(u2);

        return true;
    }


}
