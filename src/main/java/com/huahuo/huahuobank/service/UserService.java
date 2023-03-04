package com.huahuo.huahuobank.service;

import com.huahuo.huahuobank.common.ResponseResult;
import com.huahuo.huahuobank.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service
* @createDate 2023-01-30 22:07:30
*/
public interface UserService extends IService<User> {
    public ResponseResult<String> login(@RequestBody User user);
    public ResponseResult<String> register(@RequestBody User user);
}
