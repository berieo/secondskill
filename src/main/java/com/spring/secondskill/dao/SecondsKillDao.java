package com.spring.secondskill.dao;

import com.spring.secondskill.domain.SecondsKillUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SecondsKillDao {

    /*
        返回一个SecondsKillUser对象
     */

    @Select("select * from secondskill_user where id = #{id}")
    public SecondsKillUser getById(@Param("id")long id);

}
