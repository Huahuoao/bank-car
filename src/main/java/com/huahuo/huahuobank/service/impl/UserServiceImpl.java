package com.huahuo.huahuobank.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huahuo.huahuobank.common.ResponseResult;
import com.huahuo.huahuobank.mapper.CodeMapper;
import com.huahuo.huahuobank.mapper.HgroupsMapper;
import com.huahuo.huahuobank.pojo.Code;
import com.huahuo.huahuobank.pojo.Hgroups;
import com.huahuo.huahuobank.pojo.User;
import com.huahuo.huahuobank.service.CodeService;
import com.huahuo.huahuobank.service.HgroupsService;
import com.huahuo.huahuobank.service.UserService;
import com.huahuo.huahuobank.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-01-30 22:07:30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseResult<String> login(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        queryWrapper.eq(User::getIsDelete,0);
        User realUser = getOne(queryWrapper);
        if (realUser == null)
            return ResponseResult.errorResult(301, "用户未注册");
        Integer id = realUser.getId();
        if (DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).equals(realUser.getPassword())) //密码正确。生成token 返回
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", Integer.parseInt(realUser.getId().toString()));
            map.put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24);
            String token = JWTUtil.createToken(map, "huahuo".getBytes());
            if (JWTUtil.verify(token, "huahuo".getBytes())) {
                Map result = new HashMap();
                result.put("token", token);
                result.put("id", id);
                result.put("level", realUser.getLevel());
                result.put("nickName", realUser.getNickname());
                result.put("belongGroup", realUser.getBelongGroup());
                result.put("is_delete",realUser.getIsDelete());
                result.put("map",map);
                return ResponseResult.okResult(result);

            }
            return ResponseResult.errorResult(401, "token不存在");
        }
        return ResponseResult.errorResult(302, "密码错误");


    }

    @Autowired
    CodeService codeService;
    @Autowired
    HgroupsService groupsService;
    @Autowired
    CodeMapper codeMapper;
    @Autowired
    HgroupsMapper groupsMapper;

    @Override
    public ResponseResult<String> register(User user) {
        Code code = codeMapper.getCode(user.getCode());
        if (code == null) {
            return ResponseResult.errorResult(301,"邀请码不存在");
        }
        if (code.getIsUsed() == 1) {
            return ResponseResult.errorResult(302,"邀请码已被使用");
        }
        Hgroups group = groupsMapper.getGroup(user.getBelongGroup());
        if(group==null)
        {
            return ResponseResult.errorResult(303,"输入的队伍不存在");
        }
        User realUser = new User();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        queryWrapper.eq(User::getIsDelete,0);
        User user1 = getOne(queryWrapper);
        if (user1==null||user1.getIsDelete()==1) {
            realUser.setUsername(user.getUsername());
            String pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            realUser.setPassword(pwd);
            realUser.setCreateTime(DateUtil.now());
            realUser.setNickname(user.getNickname());
            realUser.setLevel(code.getLevel());
            realUser.setIdCard(user.getIdCard());
            realUser.setBelongGroup(user.getBelongGroup());
            save(realUser);
            Integer id = realUser.getId();
            if (code.getIsUsed() == 0) {
                code.setIsUsed(1);
                codeService.updateById(code);
            }
            group.setNum(group.getNum()+1);
            groupsService.updateById(group);
             Map map = new HashMap<>();
             map.put("id",id);
             map.put("message","操作成功");
            return ResponseResult.okResult(map);
        }
        return ResponseResult.errorResult(304, "用户已存在");
    }

}




