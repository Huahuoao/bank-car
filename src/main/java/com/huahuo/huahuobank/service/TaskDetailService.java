package com.huahuo.huahuobank.service;

import com.huahuo.huahuobank.common.ResponseResult;
import com.huahuo.huahuobank.dto.ListPageDto;
import com.huahuo.huahuobank.dto.ListPageThreeDto;
import com.huahuo.huahuobank.dto.ListPageTwoDto;
import com.huahuo.huahuobank.pojo.TaskDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @author Administrator
* @description 针对表【task_detail】的数据库操作Service
* @createDate 2023-02-04 15:10:12
*/
public interface TaskDetailService extends IService<TaskDetail> {
    public ResponseResult listGpsNotPull(ListPageDto dto);
    public ResponseResult listPageTwo(ListPageTwoDto dto);
    public ResponseResult listPageThree(@RequestBody ListPageThreeDto dto);


}
