package com.huahuo.huahuobank.controller;

import com.huahuo.huahuobank.common.ResponseResult;
import com.huahuo.huahuobank.pojo.TaskDetail;
import com.huahuo.huahuobank.pojo.User;
import com.huahuo.huahuobank.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @作者 花火
 * @创建日期 2023/1/30 22:09
 */
@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseResult<String> login(@RequestBody User user) {

        return userService.login(user);
    }

    @PostMapping("/register")
    public ResponseResult<String> register(@RequestBody User user) {

        return userService.register(user);
    }

}