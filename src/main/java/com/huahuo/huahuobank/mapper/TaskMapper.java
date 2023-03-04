package com.huahuo.huahuobank.mapper;

import com.huahuo.huahuobank.pojo.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【task】的数据库操作Mapper
* @createDate 2023-01-31 13:30:36
* @Entity com.huahuo.huahuobank.pojo.Task
*/
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}




