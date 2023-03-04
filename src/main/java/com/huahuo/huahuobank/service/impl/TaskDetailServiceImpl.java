package com.huahuo.huahuobank.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huahuo.huahuobank.common.PageResponseResult;
import com.huahuo.huahuobank.common.ResponseResult;
import com.huahuo.huahuobank.dto.ListPageDto;
import com.huahuo.huahuobank.dto.ListPageThreeDto;
import com.huahuo.huahuobank.dto.ListPageTwoDto;
import com.huahuo.huahuobank.pojo.ManageDetail;
import com.huahuo.huahuobank.pojo.TaskDetail;
import com.huahuo.huahuobank.pojo.User;
import com.huahuo.huahuobank.service.ManageDetailService;
import com.huahuo.huahuobank.service.TaskDetailService;
import com.huahuo.huahuobank.mapper.TaskDetailMapper;
import com.huahuo.huahuobank.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【task_detail】的数据库操作Service实现
 * @createDate 2023-01-31 01:02:51
 */
@Transactional
@Slf4j
@Service
public class TaskDetailServiceImpl extends ServiceImpl<TaskDetailMapper, TaskDetail>
        implements TaskDetailService {
    @Autowired
    UserService userService;
    @Autowired
    ManageDetailService manageDetailService;

    @Override
    public ResponseResult listGpsNotPull(ListPageDto dto) {
        dto.checkParam();
        User user = userService.getById(dto.getUserId());
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<TaskDetail> queryWrapper = new LambdaQueryWrapper<>();

        if (!user.getBelongGroup().equals("老板")) {
            log.info("7777777777777777777777");
            queryWrapper.eq(TaskDetail::getTaskGroup, user.getBelongGroup());
        }
        if (dto.getOrderByPrice() == 1) {
            if (dto.getOrderByPriceType() == 1) {
                queryWrapper.orderByAsc(TaskDetail::getEvaluation);
            }

            if (dto.getOrderByPriceType() == 0) {
                queryWrapper.orderByDesc(TaskDetail::getEvaluation);
            }


        }
        if (dto.getOrderByPrice() != 1) {
            queryWrapper.orderByDesc(TaskDetail::getUpdateTime);
        }
        if (dto.getType() == 1) {
            queryWrapper.in(TaskDetail::getIsRemarkOne, 0, 3, 4)
                    .ne(TaskDetail::getCarSituation, "已处置");

        } else if (dto.getType() == 2) {
            queryWrapper.eq(TaskDetail::getIsRemarkOne, 1)
                    .eq(TaskDetail::getIsRemarkTwo, 1)
                    .ne(TaskDetail::getCarSituation, "已处置")
                    .eq(TaskDetail::getGetCarTime, "未入库");
        } else if (dto.getType() == 3) {
            queryWrapper.eq(TaskDetail::getHasInbox, 1).ne(TaskDetail::getCarSituation, "已处置");

        }
        if (StringUtils.isNotBlank(dto.getGroup())) {
            queryWrapper.eq(TaskDetail::getProjectName, dto.getGroup());
        }

        if (StringUtils.isNotBlank(dto.getKeyWord()))
            queryWrapper.and(wrapper->wrapper.like(TaskDetail::getCarName, dto.getKeyWord()).or().like(TaskDetail::getCarOwnerName, dto.getKeyWord()).or().like(TaskDetail::getCarPlate, dto.getKeyWord()).or().like(TaskDetail::getTaskGroup,dto.getKeyWord()));

        IPage pageResult = page(page, queryWrapper);
        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(pageResult.getRecords());
        return responseResult;
    }

    // 只完成123 还有manageway没搞 45没搞
    @Override
    public ResponseResult listPageTwo(ListPageTwoDto dto) {
        dto.checkParam();
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<TaskDetail> queryWrapper = new LambdaQueryWrapper<>();
        if (dto.getOrderByPrice() == 1) {
            if (dto.getOrderByPriceType() == 0)
                queryWrapper.orderByDesc(TaskDetail::getEvaluation);
            if (dto.getOrderByPriceType() == 1)
                queryWrapper.orderByAsc(TaskDetail::getEvaluation);
        }

        if (dto.getOrderByTime() == 1) {
            switch (dto.getType()) {
                case 1:
                    if (dto.getOrderByTimeType() == 1)
                        queryWrapper.orderByAsc(TaskDetail::getCreateTime);
                    if (dto.getOrderByTimeType() == 0)
                        queryWrapper.orderByDesc(TaskDetail::getCreateTime);
                    break;
                case 2:
                    if (dto.getOrderByTimeType() == 1)
                        queryWrapper.orderByAsc(TaskDetail::getFirstSubTime);
                    if (dto.getOrderByTimeType() == 0)
                        queryWrapper.orderByDesc(TaskDetail::getFirstSubTime);
                    break;
                case 3:
                    if (dto.getOrderByTimeType() == 1)
                        queryWrapper.orderByAsc(TaskDetail::getGetCarTime);
                    if (dto.getOrderByTimeType() == 0)
                        queryWrapper.orderByDesc(TaskDetail::getGetCarTime);
                    break;
                case 4:
                    if (dto.getOrderByTimeType() == 1)
                        queryWrapper.orderByAsc(TaskDetail::getCreateTime);
                    if (dto.getOrderByTimeType() == 0)
                        queryWrapper.orderByDesc(TaskDetail::getCreateTime);
                    break;
                case 5:
                    if (dto.getOrderByTimeType() == 1)
                        queryWrapper.orderByAsc(TaskDetail::getThirdPassTime);
                    if (dto.getOrderByTimeType() == 0)
                        queryWrapper.orderByDesc(TaskDetail::getThirdPassTime);
                    break;

            }

        }
        if (dto.getOrderByTime() != 1 && dto.getOrderByPrice() != 1) {
            queryWrapper.orderByDesc(TaskDetail::getUpdateTime);
        }
        if (StringUtils.isNotBlank(dto.getGroup())) {
            queryWrapper.eq(TaskDetail::getProjectName, dto.getGroup());
        }
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            queryWrapper.and(wrapper->wrapper.like(TaskDetail::getCarName, dto.getKeyword()).or().like(TaskDetail::getCarOwnerName, dto.getKeyword()).or().like(TaskDetail::getCarPlate, dto.getKeyword()).or().like(TaskDetail::getTaskGroup,dto.getKeyword()));
        }
        switch (dto.getType()) {
            case 1:
                queryWrapper.eq(TaskDetail::getGpsSituation, "未贴G").ne(TaskDetail::getCarSituation, "已处置");
                break;
            case 2:
                queryWrapper.eq(TaskDetail::getGpsSituation, "已贴G")
                        .eq(TaskDetail::getGetCarTime, "未入库").ne(TaskDetail::getCarSituation, "已处置");
                break;
            case 3:
                queryWrapper.ne(TaskDetail::getGetCarTime, "未入库").ne(TaskDetail::getCarSituation, "已处置");
                break;
            case 4:
                queryWrapper.ne(TaskDetail::getCarSituation, "已处置").ne(TaskDetail::getCarSituation, "已处置");
                if (dto.getInStage() == 1)
                    queryWrapper.eq(TaskDetail::getIsClear, 3);
                break;
            case 5:
                if (dto.getManageWay() != null) {
                    queryWrapper.eq(TaskDetail::getManageWay, dto.getManageWay());

                }
                queryWrapper.eq(TaskDetail::getCarSituation, "已处置");
                break;
        }
        IPage pageResult = page(page, queryWrapper);
        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(pageResult.getRecords());
        return responseResult;
    }

    @Override
    public ResponseResult listPageThree(ListPageThreeDto dto) {
        dto.checkParam();
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<TaskDetail> queryWrapper = new LambdaQueryWrapper<>();
        User user = userService.getById(dto.getUserId());
        if (user.getLevel() != 1) {
            return ResponseResult.errorResult(400, "用户权限不足");
        }
        if (dto.getOrderByPrice() == 1) {
            if (dto.getOrderByPriceType() == 0)
                queryWrapper.orderByDesc(TaskDetail::getEvaluation);
            if (dto.getOrderByPriceType() == 1)
                queryWrapper.orderByAsc(TaskDetail::getEvaluation);
        }
        if (dto.getOrderByPrice() != 1) {
            queryWrapper.orderByDesc(TaskDetail::getUpdateTime);
        }
        if (StringUtils.isNotBlank(dto.getGroup())) {
            queryWrapper.eq(TaskDetail::getProjectName, dto.getGroup());
        }
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            queryWrapper.and(wrapper->wrapper.like(TaskDetail::getCarName, dto.getKeyword()).or().like(TaskDetail::getCarOwnerName, dto.getKeyword()).or().like(TaskDetail::getCarPlate, dto.getKeyword()).or().like(TaskDetail::getTaskGroup,dto.getKeyword()));
        }
        if (dto.getManageWay() != null) {
            queryWrapper.eq(TaskDetail::getManageWay, dto.getManageWay());

        }
        LambdaQueryWrapper<ManageDetail> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(ManageDetail::getIsClear, 2);
        List<Integer> tasks = new ArrayList<>();
        List<ManageDetail> list = manageDetailService.list(queryWrapper1);
        for (ManageDetail manageDetail : list) {
            tasks.add(manageDetail.getTaskId());
        }
        if (!tasks.isEmpty())
            queryWrapper.in(TaskDetail::getId, tasks);
        if (tasks.isEmpty())
            return ResponseResult.okResult(201, "当前没有待审核任务");
        IPage pageResult = page(page, queryWrapper);
        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(pageResult.getRecords());
        return responseResult;
    }
}



