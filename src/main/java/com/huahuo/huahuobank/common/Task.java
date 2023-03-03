package com.huahuo.huahuobank.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huahuo.huahuobank.pojo.Materials;
import com.huahuo.huahuobank.pojo.Record;
import com.huahuo.huahuobank.pojo.SubTime;
import com.huahuo.huahuobank.pojo.TaskDetail;
import com.huahuo.huahuobank.service.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务的使用
 *
 * @author pan_junbiao
 **/
@Slf4j
@Component
public class Task {
    @Autowired
    TaskDetailService taskService;
    @Autowired
    QiniuService qiniuService;
    @Autowired
    MaterialsService materialsService;
    @Autowired
    RecordService recordService;
    @Autowired //"0 0 1 * * ?"
    SubTimeService subTimeService;

    @Scheduled(cron = "0 0 1 * * ?")   //每天零点开始
    public void execute() {
        Integer day = 1;
        LambdaQueryWrapper<TaskDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskDetail::getIsRemarkOne, 1)
                .eq(TaskDetail::getIsRemarkTwo, 1)
                .ne(TaskDetail::getCarSituation, "已处置")
                .eq(TaskDetail::getGetCarTime, "未入库");
        List<TaskDetail> list = taskService.list(queryWrapper);
        for (TaskDetail taskDetail : list) {
            day = taskDetail.getCountTime();
            day = day - 1;
            if (day > 0) {
                taskDetail.setCountTime(day);
            } else {
                taskDetail.setIsRemarkOne(0);
                taskDetail.setFirstPassTime(null);
                taskDetail.setFirstPassUser(null);
                taskDetail.setCountTime(60);
                taskDetail.setFirstSubTime(null);
                taskDetail.setFirstSubUser(null);
                taskDetail.setGpsSituation("未贴G");
                taskDetail.setGpsSituationTwo("GPS失效");
                LambdaQueryWrapper<Materials> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(Materials::getTaskId, taskDetail.getId()).eq(Materials::getType, 1);
                List<Materials> list1 = materialsService.list(queryWrapper1);
                if (!list1.isEmpty())
                    for (Materials materials : list1) {
                        qiniuService.deleteImg(materials.getImgK());
                        materialsService.removeById(materials);
                    }
                LambdaQueryWrapper<Record> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.eq(Record::getTaskId, taskDetail.getId());
                recordService.remove(queryWrapper2);
                LambdaQueryWrapper<SubTime> queryWrapper3 = new LambdaQueryWrapper<>();
                queryWrapper3.eq(SubTime::getTaskId, taskDetail.getId());
                subTimeService.remove(queryWrapper3);

            }
        }
        taskService.updateBatchById(list);
        log.info(DateUtil.now() + "定时任务完成！");
    }
}