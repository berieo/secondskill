package com.spring.secondskill.dao;

import com.spring.secondskill.domain.SecondsKillUser;
import com.spring.secondskill.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") int id);

    @Insert("insert into user(id, name)values(#{id}, #{name})")
    public int insert(User user);



}
