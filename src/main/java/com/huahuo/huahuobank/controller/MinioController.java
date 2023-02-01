package com.huahuo.huahuobank.controller;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/io")
public class MinioController {

    @Resource
    private MinioClient minioClient;


    /**
     * 下载文件
     * @param filename
     */
    @GetMapping("/download/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse res){

        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket("test")
                .object(filename).build();

        try (GetObjectResponse response = minioClient.getObject(objectArgs)){
            byte[] buf = new byte[1024];

            int len;

            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){

                while ((len=response.read(buf))!=-1){

                    os.write(buf,0,len);

                }
                os.flush();

                byte[] bytes = os.toByteArray();

                res.setCharacterEncoding("utf-8");
                res.setContentType("application/force-download");// 设置强制下载不打开
                res.addHeader("Content-Disposition", "attachment;fileName=" + filename);
                try ( ServletOutputStream stream = res.getOutputStream()){
                    stream.write(bytes);
                    stream.flush();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}