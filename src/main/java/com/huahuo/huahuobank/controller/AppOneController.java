package com.huahuo.huahuobank.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.j2objc.annotations.AutoreleasePool;
import com.huahuo.huahuobank.common.ResponseResult;
import com.huahuo.huahuobank.dto.CheckDto;
import com.huahuo.huahuobank.dto.DownloadUrlsDto;
import com.huahuo.huahuobank.dto.FinalCheckDto;
import com.huahuo.huahuobank.dto.ListPageDto;
import com.huahuo.huahuobank.mapper.TaskDetailMapper;
import com.huahuo.huahuobank.pojo.Materials;
import com.huahuo.huahuobank.pojo.TaskDetail;
import com.huahuo.huahuobank.pojo.User;
import com.huahuo.huahuobank.service.MaterialsService;
import com.huahuo.huahuobank.service.MinioService;
import com.huahuo.huahuobank.service.TaskDetailService;
import com.huahuo.huahuobank.service.UserService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @作者 花火
 * @创建日期 2023/1/31 0:57
 */
@Slf4j
@RestController
@RequestMapping("/api/1")
public class AppOneController {

    @Autowired
    private MinioService minioService;
    @Autowired
    private TaskDetailMapper taskDetailMapper;
    @Autowired
    private MaterialsService materialsService;
    @Autowired
    private TaskDetailService taskDetailService;
    @Autowired
    private UserService userService;

    /**
     * 上传文件
     *
     * @param file
     * @param type
     * @param taskId
     * @return
     */
    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile file, @RequestParam Integer type, @RequestParam Integer taskId) {
        String filename = minioService.upload(file);
        String downloadUrl = "14.29.181.103:51608/io/download/" + filename;
        Materials materials = new Materials();
        materials.setName(filename);
        materials.setUrl(downloadUrl);
        materials.setTaskId(taskId);
        materials.setType(type);
        materialsService.save(materials);
        return ResponseResult.okResult("上传文件成功");
    }

    /**
     * 返回所需文件的所有下载信息
     *
     * @param downloadUrlsDto
     * @return
     */
    @PostMapping("/list/urls")
    public ResponseResult getDownloadUrls(@RequestBody DownloadUrlsDto downloadUrlsDto) {
        LambdaQueryWrapper<Materials> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Materials::getTaskId, downloadUrlsDto.getTaskId())
                .eq(Materials::getType, downloadUrlsDto.getType());
        List<Materials> list = materialsService.list(queryWrapper);
        return ResponseResult.okResult(list);
    }

    @PostMapping("/check/1")
    public ResponseResult checkOne(@RequestBody CheckDto dto) {
        TaskDetail task = taskDetailService.getById(dto.getTaskId());
        log.info("1");
        User user = userService.getById(dto.getUserId());
        // 3 为待审核状态
        log.info("2");
        task.setIsRemarkOne(3);
        task.setFirstSubUser(user.getNickname());
        task.setFirstSubTime(DateUtil.now());
        log.info("3");
        taskDetailService.updateById(task);
        log.info("4");
        return ResponseResult.okResult("成功提交第一次审核");
    }

    @PostMapping("/check/2")
    public ResponseResult checkTwo(@RequestBody CheckDto dto) {
        TaskDetail task = taskDetailService.getById(dto.getTaskId());
        User user = userService.getById(dto.getUserId());
        task.setIsRemarkTwo(3);
        task.setSecondSubUser(user.getNickname());
        task.setSecondSubTime(DateUtil.now());
        taskDetailService.updateById(task);
        return ResponseResult.okResult("成功提交第二次审核");
    }

    @PostMapping("/check/3")
    public ResponseResult checkThree(@RequestBody FinalCheckDto dto) {
        TaskDetail task = taskDetailMapper.selectById(dto.getTaskId());
        User user = userService.getById(dto.getUserId());
        task.setActualUser(dto.getActualUser());
        task.setRecoveryTime(dto.getRecoveryTime());
        task.setInboxWare(dto.getInboxWare());
        task.setSentGarage(dto.getSentGarage());
        task.setCarPhone(dto.getCarPhone());
        task.setHasLicense(dto.getHasLicense());
        task.setHasKey(dto.getHasKey());
        task.setCarConditionDes(dto.getCarConditionDes());
        task.setItemsInCarDes(dto.getItemsInCarDes());
        task.setIsRemarkThree(3);
        task.setThirdSubUser(user.getNickname());
        task.setThirdSubTime(DateUtil.now());
        taskDetailService.updateById(task);
        return ResponseResult.okResult("成功提交第三次审核");
    }

    /**
     * 分页查询条目
     * type 1 2 3 分别对应三个页面
     * <p>
     * group就是对应的分组
     *
     * @param dto
     * @return
     */
    @PostMapping("/list/page")
    public ResponseResult listGpsNotPull(@RequestBody ListPageDto dto) {
        return taskDetailService.listGpsNotPull(dto);
    }

}
