package com.jesper.seckill.mapper;

import com.jesper.seckill.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from sk_user where id = #{id}")
    User getById(@Param("id") long id);

    @Insert("insert into sk_user(id, nickname, password, salt, register_date, last_login_date, login_count) values(#{id}, #{nickname}, #{password}, #{salt}, #{registerDate}, #{lastLoginDate}, #{loginCount})")
    int insert(User user);

    @Update("update sk_user set password = #{password} where id = #{id}")
    int update(User user);
}
