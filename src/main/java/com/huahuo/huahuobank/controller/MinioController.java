package com.huahuo.huahuobank.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huahuo.huahuobank.common.ResponseResult;
import com.huahuo.huahuobank.dto.BatchDownloadDto;
import com.huahuo.huahuobank.pojo.Materials;
import com.huahuo.huahuobank.pojo.TaskDetail;
import com.huahuo.huahuobank.service.MaterialsService;
import com.huahuo.huahuobank.service.MinioService;
import com.huahuo.huahuobank.service.TaskDetailService;
import io.minio.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@RestController
@RequestMapping("/io")
public class MinioController {

    @Resource
    private MinioClient minioClient;

    @Autowired
    private MinioService minioService;
    @Autowired
    private TaskDetailService taskDetailService;
    @Autowired
    private MaterialsService materialsService;

    @GetMapping("/batch/download/{type}")
    public void downloadByBatch(@RequestParam(value = "id") Integer[] taskId, @PathVariable Integer type, HttpServletResponse res, HttpServletRequest req) {
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
        for (Materials materials : list) {
            filenames.add(materials.getName());
        }
        minioService.batchDownload(filenames, zip, res, req);
    }

    /**
     * 下载文件
     *
     * @param filename
     */
    @GetMapping("/download/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse res) {

        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket("test")
                .object(filename).build();

        try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
            byte[] buf = new byte[1024];

            int len;

            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {

                while ((len = response.read(buf)) != -1) {

                    os.write(buf, 0, len);

                }
                os.flush();

                byte[] bytes = os.toByteArray();

                res.setCharacterEncoding("utf-8");
                res.setContentType("application/force-download");// 设置强制下载不打开
                res.addHeader("Content-Disposition", "attachment;fileName=" + filename);
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}