package com.huahuo.huahuobank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huahuo.huahuobank.pojo.Task;
import com.huahuo.huahuobank.service.TaskService;
import com.huahuo.huahuobank.mapper.TaskMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author Administrator
* @description 针对表【task】的数据库操作Service实现
* @createDate 2023-01-31 13:30:36
*/
@Transactional
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService{

}




