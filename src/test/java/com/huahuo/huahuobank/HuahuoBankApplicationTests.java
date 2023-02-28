package com.huahuo.huahuobank;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huahuo.huahuobank.pojo.User;
import com.huahuo.huahuobank.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class HuahuoBankApplicationTests {
    @Autowired
    UserService userService;

    public String getAccessToken() {
        String appId = "wxa3a65dedbd291f1d";
        String appSecret = "2c9b92bcd7a2f44f3682c9cebec5da3a";
        String result = cn.hutool.http.HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(result);
        return jsonObject.getStr("access_token");
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void send() {
        String text="审核通知";
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getLevel, 2);
        List<User> list = userService.list(queryWrapper);
        String openid = null;
        for (User user : list) {
            openid = user.getOpenId();
            cn.hutool.json.JSONObject body = new cn.hutool.json.JSONObject();
            body.set("touser", openid);
            body.set("template_id", "CeOn6aZx5kKt0o1YADNQBYCLTHmoujFriEbpeDqzVPw");
            cn.hutool.json.JSONObject json = new cn.hutool.json.JSONObject();
            json.set("phrase1", new cn.hutool.json.JSONObject().set("value", text));
            json.set("date3", new cn.hutool.json.JSONObject().set("value", DateUtil.now()));
            body.set("data", json);
            String accessToken = getAccessToken();
            String post = HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken, body.toString());
            System.out.println(post);
        }
    }
}
