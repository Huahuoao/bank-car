package com.huahuo.huahuobank.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @作者 花火
 * @创建日期 2023/1/31 0:54
 */
public interface MinioService {
    public String upload(MultipartFile file);
}
