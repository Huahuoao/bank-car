package com.huahuo.huahuobank.mapper;

import com.huahuo.huahuobank.pojo.Code;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Administrator
 * @description 针对表【code】的数据库操作Mapper
 * @createDate 2023-02-08 02:42:07
 * @Entity com.huahuo.huahuobank.pojo.Code
 */
@Mapper
public interface CodeMapper extends BaseMapper<Code> {
    @Select("SELECT * from code where text = #{text}")
    Code getCode(@Param("text") String text);
}




