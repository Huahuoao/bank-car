package com.huahuo.huahuobank.mapper;

import com.huahuo.huahuobank.pojo.Hgroups;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Administrator
 * @description 针对表【hgroups】的数据库操作Mapper
 * @createDate 2023-02-08 03:18:12
 * @Entity com.huahuo.huahuobank.pojo.Hgroups
 */
@Mapper
public interface HgroupsMapper extends BaseMapper<Hgroups> {
    @Select("select * from hgroups where name = #{name}")
    Hgroups getGroup(@Param("name") String name);
}




