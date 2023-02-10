package com.huahuo.huahuobank.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface QiniuService {
    public HashMap saveImage(MultipartFile file);
    public void deleteImg(String fileKey);
    public String getUpToken();
}
