package com.huahuo.huahuobank.mapper;

import com.huahuo.huahuobank.pojo.TaskDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * @author Administrator
 * @description 针对表【task_detail】的数据库操作Mapper
 * @createDate 2023-02-04 15:10:12
 * @Entity com.huahuo.huahuobank.pojo.TaskDetail
 */
@Mapper
public interface TaskDetailMapper extends BaseMapper<TaskDetail> {

    @Select("select car_plate from task_detail where car_plate  in #{carPlate}")
    ArrayList<String> findRepeat(@Param("carPlate") ArrayList<String> carPlate);
}




