package com.huahuo.huahuobank.service.impl;

import com.huahuo.huahuobank.service.MinioService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @作者 花火
 * @创建日期 2023/1/31 0:55
 */
@Service
public class MinioServiceImpl implements MinioService {


    @Resource
    private MinioClient minioClient;

    public String upload(MultipartFile file){
        String filename = file.getOriginalFilename();
        String str1 = filename.substring(0,filename.indexOf("."));
        String str2 = filename.substring(str1.length()+1, filename.length());
        filename = UUID.randomUUID()+"."+str2;

        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().object(filename)
                    .bucket("test")
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(),file.getSize(),-1).build();

            minioClient.putObject(objectArgs);
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }














}
