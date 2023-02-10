package com.huahuo.huahuobank.service.impl;

import com.huahuo.huahuobank.pojo.TaskDetail;
import com.huahuo.huahuobank.service.MinioService;
import com.huahuo.huahuobank.service.TaskDetailService;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @作者 花火
 * @创建日期 2023/1/31 0:55
 */
@Service
@Slf4j
public class MinioServiceImpl implements MinioService {
    @Resource
    private MinioClient minioClient;

    public static String filenameEncoding(String filename, HttpServletRequest request) throws UnsupportedEncodingException {
        // 获得请求头中的User-Agent
        String agent = request.getHeader("User-Agent");
        // 根据不同的客户端进行不同的编码

        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?" + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    @Autowired
    private TaskDetailService taskDetailService;

    public String upload(MultipartFile file, Integer taskId, String num, Integer type) {
        String filename = file.getOriginalFilename();
        String str1 = filename.substring(0, filename.indexOf("."));
        String str2 = filename.substring(str1.length() + 1, filename.length());
        TaskDetail task = taskDetailService.getById(taskId);
        String plate = task.getCarPlate();
        String name = task.getCarOwnerName();
        String typeName = null;
        switch (type) {
            case 1:
                typeName = "贴G审核文件";
                break;
            case 2:
                typeName = "定位变动审核文件";
                break;
            case 3:
                typeName = "处置审核文件";
                break;
            case 4:
                typeName = "申请结清审核文件";
                break;
            case 5:
                typeName = "入库文件";
                break;
        }
        filename = name + "-" + plate + "-" + typeName + num + "." + str2;
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().object(filename)
                    .bucket("test")
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), -1).build();

            minioClient.putObject(objectArgs);
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    public void batchDownload(List<String> filenames, String zip, HttpServletResponse res, HttpServletRequest req) {
        try {
            String bucketName = "test";
            BucketExistsArgs bucketArgs = BucketExistsArgs.builder().bucket(bucketName).build();
            boolean bucketExists = minioClient.bucketExists(bucketArgs);
            BufferedOutputStream bos = null;
            res.reset();
            bos = new BufferedOutputStream(res.getOutputStream());
            ZipOutputStream out = new ZipOutputStream(bos);
            res.setHeader("Access-Control-Allow-Origin", "*");
            for (int i = 0; i < filenames.size(); i++) {
                GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName)
                        .object(filenames.get(i)).build();
                InputStream object = minioClient.getObject(objectArgs);
                byte buf[] = new byte[1024];
                int length = 0;
                res.setCharacterEncoding("utf-8");
                res.setContentType("application/force-download");// 设置强制下载不打开
                res.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                res.setHeader("Content-Disposition", "attachment;filename=" + filenameEncoding(zip, req) + ".zip");
                out.putNextEntry(new ZipEntry(filenames.get(i)));
                while ((length = object.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
            }
            out.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean remove(String fileName){
        try {
            minioClient.removeObject( RemoveObjectArgs.builder().bucket("test").object(fileName).build());
        }catch (Exception e){
            return false;
        }
        return true;
    }


}
