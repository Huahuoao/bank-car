package com.huahuo.huahuobank.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @作者 花火
 * @创建日期 2023/1/31 0:54
 */
public interface MinioService {
    public String upload(MultipartFile file,Integer taskId,String num,Integer type);

    public void batchDownload(List<String> filenames, String zip, HttpServletResponse res, HttpServletRequest req);


    public boolean remove(String fileName);
}
