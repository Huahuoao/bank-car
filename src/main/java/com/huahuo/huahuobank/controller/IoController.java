package com.huahuo.huahuobank.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huahuo.huahuobank.common.ResponseResult;
import com.huahuo.huahuobank.dto.DeleteDto;
import com.huahuo.huahuobank.pojo.Materials;
import com.huahuo.huahuobank.pojo.Task;
import com.huahuo.huahuobank.pojo.TaskDetail;
import com.huahuo.huahuobank.service.MaterialsService;
import com.huahuo.huahuobank.service.QiniuService;
import com.huahuo.huahuobank.service.TaskDetailService;
import com.huahuo.huahuobank.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/io")
public class IoController {

    @Autowired
    private TaskDetailService taskDetailService;
    @Autowired
    private MaterialsService materialsService;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private TaskService taskService;

    private static InputStream getImageStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return inputStream;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/delete/file")
    public ResponseResult deleteFile(@RequestBody DeleteDto dto) throws ParseException {
        if(!dto.getPassword().equals("20030416cjh"))
            return ResponseResult.errorResult("权限认证失败");
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = ft.parse(dto.getDate());
//        List<Materials> list = materialsService.list();
//        for (Materials materials : list) {
//            if (ft.parse(materials.getCreateTime()).compareTo(date) < 0) {
//                qiniuService.deleteImg(materials.getKey());
//                materialsService.removeById(materials.getId());
//            }
//        }
        return ResponseResult.okResult("成功删除" + dto.getDate() + "前的文件资源");
    }

    @PostMapping("/delete/task")
    public ResponseResult deleteTask(@RequestBody DeleteDto dto) throws ParseException {
        if(!dto.getPassword().equals("20030416cjh"))
            return ResponseResult.errorResult("权限认证失败");
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date = ft.parse(dto.getDate());
        List<TaskDetail> list = taskDetailService.list();
        for (TaskDetail materials : list) {
            if (ft.parse(materials.getCreateTime()).compareTo(date) < 0) {
                taskDetailService.removeById(materials.getId());
                taskService.removeById(materials.getId());
            }
        }
        return ResponseResult.okResult("成功删除" + dto.getDate() + "前的车辆信息");
    }

    @CrossOrigin
    @GetMapping("/batch/download/{type}")
    public void downloadByBatch(@RequestParam(value = "id") Integer[] taskId, @PathVariable Integer type, HttpServletResponse response, HttpServletRequest req) throws IOException {
        String preName = "";
        log.info(String.valueOf(taskId.length));
        if (taskId.length == 1) {
            TaskDetail task = taskDetailService.getById(taskId[0]);
            String plate = task.getCarPlate();
            String name = task.getCarOwnerName();
            preName = name + "-" + plate + "-";
        } else {
            preName = DateUtil.now() + "-";
        }
        List<String> filenames = new ArrayList<>();
        String zip = taskId.toString();
        for (Integer integer : taskId) {
            log.info(integer.toString());
        }

        switch (type) {
            case 0:
                zip = preName + "所有文件";
                break;
            case 1:
                zip = preName + "贴G审核文件";
                break;
            case 2:
                zip = preName + "定位变动审核文件";
                break;
            case 3:
                zip = preName + "处置审核文件";
                break;
            case 4:
                zip = preName + "申请结清审核文件";
                break;
            case 6:
                zip = preName + "入库文件";
                break;
        }
        LambdaQueryWrapper<Materials> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(Materials::getTaskId, taskId);
        if (type != 0) {
            queryWrapper.eq(Materials::getType, type);
        }
        List<Materials> list = materialsService.list(queryWrapper);
        Integer num = list.size();
        //被压缩文件InputStream
        InputStream[] srcFiles = new InputStream[num];
        //被压缩文件名称
        String[] srcFileNames = new String[num];
        for (int i = 0; i < num; i++) {
            InputStream inputStream = getImageStream(list.get(i).getUrl());
            if (inputStream == null) {
                continue;
            }
            srcFiles[i] = inputStream;
            srcFileNames[i] = list.get(i).getName();
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zip + ".zip", "UTF-8"));
        //多个文件压缩成压缩包返回
        log.info("123123");
        ZipUtil.zip(response.getOutputStream(), srcFileNames, srcFiles);

    }


}