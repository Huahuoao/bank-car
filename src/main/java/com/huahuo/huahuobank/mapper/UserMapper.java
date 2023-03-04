package com.huahuo.huahuobank.mapper;

import com.huahuo.huahuobank.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-01-30 22:07:30
* @Entity com.huahuo.huahuobank.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {




    @Select("select * from user where username = #{username}")
    User selectByUsername(@Param("username") String username);
}




