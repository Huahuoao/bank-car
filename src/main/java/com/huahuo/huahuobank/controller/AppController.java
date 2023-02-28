package com.huahuo.huahuobank.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huahuo.huahuobank.common.ResponseResult;
import com.huahuo.huahuobank.dto.*;
import com.huahuo.huahuobank.mapper.TaskDetailMapper;
import com.huahuo.huahuobank.pojo.*;
import com.huahuo.huahuobank.service.*;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;


/**
 * @作者 花火
 * @创建日期 2023/1/31 0:57
 */
@Slf4j
@RestController
@RequestMapping("/api/1")
public class AppController {
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private TaskDetailMapper taskDetailMapper;
    @Autowired
    private MaterialsService materialsService;
    @Autowired
    private TaskDetailService taskDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private ManageDetailService manageDetailService;

    public static Double format(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * 上传文件
     *
     * @param file
     * @param type
     * @param taskId
     * @return
     */
    public String getFileName(MultipartFile file, Integer taskId, String num, Integer type) {
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
        return filename;
    }


    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile file, @RequestParam Integer type, @RequestParam Integer taskId, @RequestParam String num) throws UnsupportedEncodingException {
        String filename = getFileName(file, taskId, num, type);
        HashMap map = qiniuService.saveImage(file);
        String downloadUrl = (String) map.get("url");
        String key = (String) map.get("key");
        log.info("filename==" + filename);
        String str1 = filename.substring(0, filename.indexOf("."));
        String str2 = filename.substring(str1.length() + 1, filename.length());
        TaskDetail task = taskDetailService.getById(taskId);
        Materials materials = new Materials();
        materials.setOrderNum(task.getOrderNum());
        materials.setName(filename);
        materials.setImgK(key);
        materials.setCreateTime(DateUtil.now());
        materials.setUrl(downloadUrl);
        materials.setTaskId(taskId);
        materials.setType(type);
        materials.setFileType(str2);
        materialsService.save(materials);
        return ResponseResult.okResult("上传文件成功");
    }


    /**
     * 返回所需文件的所有下载信息
     *
     * @param downloadUrlsDto
     * @return
     */
    @PostMapping("/list/urls")
    public ResponseResult getDownloadUrls(@RequestBody DownloadUrlsDto downloadUrlsDto) {
        LambdaQueryWrapper<Materials> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Materials::getTaskId, downloadUrlsDto.getTaskId())
                .eq(Materials::getType, downloadUrlsDto.getType());
        List<Materials> list = materialsService.list(queryWrapper);
        return ResponseResult.okResult(list);
    }

    @PostMapping("/list/urls/1")
    public ResponseResult getDownloadUrls1(@RequestBody NewDownloadUrlsDto downloadUrlsDto) {
        LambdaQueryWrapper<Materials> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Materials::getTaskId, downloadUrlsDto.getTaskId())
                .eq(Materials::getType, downloadUrlsDto.getType())
                .eq(Materials::getOrderNum, downloadUrlsDto.getNum());
        List<Materials> list = materialsService.list(queryWrapper);
        return ResponseResult.okResult(list);
    }

    @PostMapping("/check/1")
    public ResponseResult checkOne(@RequestBody CheckDto dto) {
        TaskDetail task = taskDetailService.getById(dto.getTaskId());
        log.info("1");
        User user = userService.getById(dto.getUserId());
        // 3 为待审核状态
        log.info("2");
        task.setIsRemarkOne(3);
        task.setFirstSubUser(user.getNickname());
        task.setFirstSubTime(DateUtil.now());
        task.setUpdateTime(DateUtil.now());
        log.info("3");
        taskDetailService.updateById(task);
        log.info("4");
        send();
        return ResponseResult.okResult("成功提交第一次审核");
    }

    @PostMapping("/check/2")
    public ResponseResult checkTwo(@RequestBody CheckDto dto) {
        TaskDetail task = taskDetailService.getById(dto.getTaskId());
        User user = userService.getById(dto.getUserId());
        task.setIsRemarkTwo(3);
        task.setUpdateTime(DateUtil.now());
        task.setSecondSubUser(user.getNickname());
        task.setSecondSubTime(DateUtil.now());
        taskDetailService.updateById(task);
        send();
        return ResponseResult.okResult("成功提交第二次审核");
    }

    @PostMapping("/inbox")
    public ResponseResult checkThree(@RequestBody FinalCheckDto dto) {
        TaskDetail task = taskDetailMapper.selectById(dto.getTaskId());
        User user = userService.getById(dto.getUserId());
        task.setActualUser(dto.getActualUser());
        task.setRecoveryTime(dto.getRecoveryTime());
        task.setInboxWare(dto.getInboxWare());
        task.setSentGarage(dto.getSentGarage());
        task.setCarPhone(dto.getCarPhone());
        task.setHasLicense(dto.getHasLicense());
        task.setUpdateTime(DateUtil.now());
        task.setHasKey(dto.getHasKey());
        task.setCarConditionDes(dto.getCarConditionDes());
        task.setItemsInCarDes(dto.getItemsInCarDes());
        task.setGetCarTime(DateUtil.now());
        task.setCarSituation("已收车");
        task.setGetCarGroup(user.getBelongGroup());
        task.setHasInbox(1);
        taskDetailService.updateById(task);
        return ResponseResult.okResult("成功入库");
    }


    /**
     * 分页查询条目
     * type 1 2 3 分别对应三个页面
     * <p>
     * group就是对应的分组
     *
     * @param dto
     * @return
     */
    @PostMapping("/list/page")
    public ResponseResult listGpsNotPull(@RequestBody ListPageDto dto) {
        return taskDetailService.listGpsNotPull(dto);
    }

    //2-4
    @GetMapping("/check/num/{type}")
    public ResponseResult checkNum(@PathVariable Integer type) {
        Integer num = 0;
        List<TaskDetail> list = taskDetailService.list();
        List<TaskDetail> toDoList = new ArrayList<>();
        if (type == 1) {
            for (TaskDetail task : list) {
                if (task.getIsRemarkOne() == 3 || task.getIsRemarkTwo() == 3) {
                    num++;
                    toDoList.add(task);
                }
            }
        }
        if (type == 3) {
            for (TaskDetail task : list) {
                if (task.getIsRemarkThree() == 3) {
                    num++;
                    toDoList.add(task);
                }
            }
        }
        if (type == 4) {
            for (TaskDetail task : list) {
                if (task.getIsRemarkFour() == 3) {
                    num++;
                    toDoList.add(task);
                }
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("num", num);
        response.put("list", toDoList);
        return ResponseResult.okResult(response);
    }


    /**
     * 审核驳回
     * 1 通过 0未提交 3 待审核 4 驳回
     */
    @PostMapping("/check/reject")
    public ResponseResult checkReject(@RequestBody RejectDto dto) {
        Integer taskId = dto.getTaskId();
        Integer type = dto.getType();
        String text = dto.getText();
        TaskDetail task = taskDetailService.getById(taskId);
        task.setProcessSituation("已驳回");
        Record record = new Record();
        record.setType(1);
        record.setCreateTime(DateUtil.now());
        record.setText(text);
        record.setTaskId(dto.getTaskId());
        recordService.save(record);
        switch (type) {
            case 1:
                task.setIsRemarkOne(4);
                break;
            case 2:
                task.setIsRemarkTwo(4);
                task.setIsRemarkOne(4);
                break;
            case 3:
                task.setIsRemarkThree(4);
                LambdaQueryWrapper<ManageDetail> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ManageDetail::getTaskId, taskId).eq(ManageDetail::getIsClear, 2);
                manageDetailService.remove(queryWrapper);
                break;
        }
        List<String> files = new ArrayList<>();
        task.setUpdateTime(DateUtil.now());
        taskDetailService.updateById(task);
        LambdaQueryWrapper<Materials> queryWrapper = new LambdaQueryWrapper<>();
        if (dto.getType() == 2) {
            queryWrapper.in(Materials::getType, 1, 2).eq(Materials::getTaskId, dto.getTaskId());

        } else if (dto.getType() != 2)
            queryWrapper.eq(Materials::getType, dto.getType()).eq(Materials::getTaskId, dto.getTaskId());
        List<Materials> list = materialsService.list(queryWrapper);
        materialsService.remove(queryWrapper);
        return ResponseResult.okResult("驳回成功");
    }

    /**
     * 获取记录
     * 1驳回
     * 2变动
     *
     * @param dto
     * @return
     */
    @PostMapping("/get/record")
    public ResponseResult getRecord(@RequestBody BatchDownloadDto dto) {
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Record::getType, dto.getType());
        queryWrapper.eq(Record::getTaskId, dto.getTaskId());
        List<Record> list = recordService.list(queryWrapper);
        return ResponseResult.okResult(list);
    }

    //
    @PostMapping("/manage")
    public ResponseResult manage(@RequestBody ManageDto dto) {
        Integer taskId = dto.getTaskId();
        Integer type = dto.getType(); ///这个type表示的是 1 2 3 4 5五种
        TaskDetail task = taskDetailService.getById(taskId);
        task.setManageWay(dto.getType());
        if (type != 5)
            task.setIsRemarkThree(3);
        if (type == 5)
            task.setIsRemarkFour(3);
        task.setThirdSubTime(DateUtil.now());
        task.setThirdSubUser(userService.getById(dto.getUserId()).getNickname());
        task.setUpdateTime(DateUtil.now());
        taskDetailService.updateById(task);
        ManageDetail manageDetail = new ManageDetail();
        manageDetail.setType(dto.getType());
        manageDetail.setTaskId(taskId);
        manageDetail.setPhone(dto.getPhone());
        manageDetail.setCompany(dto.getCompany());
        manageDetail.setPlate(dto.getPlate());
        manageDetail.setCertificateLocation(dto.getCertificateLocation());
        manageDetail.setPrice(dto.getPrice());
        manageDetail.setRemark(dto.getRemark());
        manageDetail.setRegisterName(dto.getRegisterName());
        manageDetail.setTheOtherName(dto.getTheOtherName());
        manageDetail.setTime(dto.getTime());
        manageDetail.setIsClear(2);
        manageDetailService.save(manageDetail);
        return ResponseResult.okResult(manageDetail.toString());
    }

    //1 通过的 0 还没审核和未通过的
    //获取处置记录
    @GetMapping("/get/manage/detail/{taskId}")
    public ResponseResult getManageDetail(@PathVariable Integer taskId) {
        LambdaQueryWrapper<ManageDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ManageDetail::getTaskId, taskId);
        List<ManageDetail> list = manageDetailService.list(queryWrapper);
        return ResponseResult.okResult(list);
    }


    //获取催收记录
    @GetMapping("/get/record/2/{id}")
    public ResponseResult getClearRecord(@PathVariable Integer id) {
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Record::getType, 2)
                .eq(Record::getTaskId, id);
        List<Record> list = recordService.list(queryWrapper);
        return ResponseResult.okResult(list);
    }

    //新增催收记录---------------------------------------------------------
    @PostMapping("/create/record/2")
    public ResponseResult createRecord2(@RequestBody CreateRecordDto dto) {
        User user = userService.getById(dto.getUserId());
        Record record = new Record();
        record.setUserName(user.getNickname());
        record.setType(2);
        record.setCreateTime(DateUtil.now());
        record.setText(dto.getText());
        record.setTaskId(dto.getTaskId());
        recordService.save(record);
        return ResponseResult.okResult("新增催收记录成功！");
    }

    @PostMapping("/change")
    public ResponseResult change(@RequestBody ChangeDto dto) {
        TaskDetail task = taskDetailService.getById(dto.getTaskId());
        task.setSentGarage(dto.getSentGarage());
        taskDetailService.updateById(task);
        return ResponseResult.okResult("挪库成功");
    }


    @PostMapping("/list/page/2")
    public ResponseResult listPageTwo(@RequestBody ListPageTwoDto dto) {
        return taskDetailService.listPageTwo(dto);

    }

    @PostMapping("/list/page/3")
    public ResponseResult listPageThree(@RequestBody ListPageThreeDto dto) {
        return taskDetailService.listPageThree(dto);

    }

    @PostMapping("/pass")
    public ResponseResult pass(@RequestBody PassDto dto) {
        User user = userService.getById(dto.getUserId());
        TaskDetail task = taskDetailService.getById(dto.getTaskId());
        log.info(task.toString());
        LambdaQueryWrapper<ManageDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ManageDetail::getTaskId, dto.getTaskId()).eq(ManageDetail::getIsClear, 2);
        ManageDetail manageDetail = manageDetailService.getOne(queryWrapper);
        switch (dto.getType()) {
            case 1:
                task.setIsRemarkOne(1);
                task.setFirstPassTime(DateUtil.now());
                task.setFirstPassUser(user.getNickname());
                task.setGpsSituation("已贴G");
                task.setGpsSituationTwo("在线");
                break;
            case 2:
                task.setIsRemarkOne(1);
                task.setFirstPassTime(DateUtil.now());
                task.setFirstPassUser(user.getNickname());
                task.setIsRemarkTwo(1);
                task.setSecondPassTime(DateUtil.now());
                task.setSecondPassUser(user.getNickname());
                task.setGpsSituation("已贴G");
                task.setGpsSituationTwo("在线");
                break;
            case 3:
                task.setIsRemarkThree(1);
                task.setThirdPassTime(DateUtil.now());
                task.setThirdPassUser(user.getNickname());
                task.setCarSituation("已处置");
                task.setIsClear(1);
                task.setHasManage(1);
                task.setManageTime(DateUtil.now());
                manageDetail.setIsClear(1);
                manageDetailService.updateById(manageDetail);
                break;
            case 4:   //分期
                log.info("本金", task.getPrincipal());
                LambdaQueryWrapper<Materials> queryWrapper2 = new LambdaQueryWrapper<>();
                task.setOrderNum(task.getOrderNum() + 1);
                queryWrapper2.eq(Materials::getIsNew, 1).eq(Materials::getTaskId, dto.getTaskId()).eq(Materials::getType, 4);
                List<Materials> list = materialsService.list(queryWrapper2);
                for (Materials materials : list) {
                    materials.setIsNew(0);
                    materialsService.updateById(materials);
                }
                task.setTaskGroup(null);
                if (task.getIsRemarkOne() == 4 || task.getIsRemarkTwo() == 4) {
                    //删除record
                    LambdaQueryWrapper<Record> queryWrapper4 = new LambdaQueryWrapper<>();
                    queryWrapper4.eq(Record::getTaskId, task.getId());
                    recordService.remove(queryWrapper4);
                    task.setIsRemarkOne(0);
                    task.setIsRemarkTwo(1);
                }
                //待审核变成未提交
                if (task.getIsRemarkOne() == 3 || task.getIsRemarkTwo() == 3) {
                    //materials
                    task.setIsRemarkOne(0);
                    task.setIsRemarkTwo(1);
                    LambdaQueryWrapper<Materials> queryWrapper5 = new LambdaQueryWrapper<>();
                    queryWrapper5.eq(Materials::getTaskId, task.getId());
                    queryWrapper5.in(Materials::getType, 1, 2);
                    materialsService.remove(queryWrapper5);
                }
                manageDetail = manageDetailService.getOne(queryWrapper);
                manageDetail.setIsClear(1);
                task.setIsClear(3); //分期中
                task.setIsRemarkFour(0);
                task.setIsRemarkThree(0);
                queryWrapper.eq(ManageDetail::getIsClear, 2);
                manageDetailService.updateById(manageDetail);
                break;
        }
        task.setUpdateTime(DateUtil.now());
        taskDetailService.updateById(task);
        return ResponseResult.okResult("通过审核成功");
    }

    @Autowired
    HgroupsService groupsService;

    @GetMapping("/get/car/{id}")
    public ResponseResult getCar(@PathVariable Integer id) {

        return ResponseResult.okResult(taskDetailService.getById(id));


    }


    @PostMapping("/create/group")
    public ResponseResult createGroup(@RequestBody CreateGroupDto dto) {
        User user = userService.getById(dto.getUserId());
        if (user.getLevel() > 2) {
            return ResponseResult.errorResult(400, "权限不足");
        }
        Hgroups groups = new Hgroups();
        groups.setName(dto.getName());
        groups.setNum(0);
        groups.setLevel(dto.getLevel());
        groupsService.save(groups);
        return ResponseResult.okResult("创建队伍成功");
    }

    @GetMapping("/remove/group/{id}")
    public ResponseResult removeGroup(@PathVariable Integer id) {
        Hgroups group = groupsService.getById(id);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getBelongGroup, group.getName());
        userService.remove(queryWrapper);
        groupsService.removeById(id);
        return ResponseResult.okResult("删除队伍成功");
    }

    @Autowired
    CodeService codeService;

    @GetMapping("/create/code/{userId}")
    public ResponseResult createCode(@PathVariable Integer userId) {
        String code1 = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        User user = userService.getById(userId);
        if (user.getLevel() == 3)
            return ResponseResult.errorResult(400, "权限不足");
        Code code = new Code();
        code.setText(code1);
        code.setCreateUser(user.getNickname());
        code.setLevel(user.getLevel() + 1);
        code.setIsUsed(0);
        codeService.save(code);
        return ResponseResult.okResult(code.getText());
    }

    @GetMapping("/get/groups/{level}")
    public ResponseResult getGroups(@PathVariable Integer level) {
        List<String> list = new ArrayList<>();
        LambdaQueryWrapper<Hgroups> queryWrapper = new LambdaQueryWrapper<>();
        if (level != 0)
            queryWrapper.eq(Hgroups::getLevel, level);
        List<Hgroups> groups = groupsService.list(queryWrapper);
        return ResponseResult.okResult(groups);
    }

    @GetMapping("/remove/user/{id}")
    public ResponseResult removeUser(@PathVariable Integer id) {
        userService.removeById(id);
        return ResponseResult.okResult("删除成功");
    }


    @PostMapping("/get/users")
    public ResponseResult getUsers(@RequestBody GetUserDto dto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(dto.getGroup())) {
            queryWrapper.eq(User::getBelongGroup, dto.getGroup());
        }
        if (StringUtils.isNotBlank(dto.getLevel())) {
            queryWrapper.eq(User::getLevel, dto.getLevel());
        }
        if (StringUtils.isNotBlank(dto.getKeyWord())) {
            queryWrapper.like(User::getNickname, dto.getKeyWord());
        }
        List<User> list = userService.list(queryWrapper);
        return ResponseResult.okResult(list);
    }

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/get/phone")
    public String getPhone(@RequestBody GetPhoneDto dto) {

        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + dto.getToken();
        LinkedMultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        //入参
        request.set("code", dto.getCode());

        //请求
        String result = restTemplate.postForObject(url, request, String.class);
        return result;


    }

    @Autowired
    OwnerPhoneService ownerPhoneService;

    @PostMapping("/add/phone")
    public ResponseResult addPhone(@RequestBody AddPhoneDto dto) {
        OwnerPhone phone = new OwnerPhone();
        phone.setCarId(dto.getTaskId());
        phone.setNumber(dto.getNumber());
        ownerPhoneService.save(phone);
        return ResponseResult.okResult("新增电话成功");
    }

    @GetMapping("/remove/phone/{id}")
    public ResponseResult addPhone(@PathVariable Integer id) {
        ownerPhoneService.removeById(id);
        return ResponseResult.okResult("删除电话成功");
    }

    @GetMapping("/get/more/phone/{id}")
    public ResponseResult getMorePhone(@PathVariable Integer id) {
        LambdaQueryWrapper<OwnerPhone> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OwnerPhone::getCarId, id);
        List<OwnerPhone> list = ownerPhoneService.list(queryWrapper);
        return ResponseResult.okResult(list);
    }


    @PostMapping("/change/group")
    public ResponseResult changeGroup(@RequestBody ChangeGroupDto dto) {
        TaskDetail taskDetail = taskDetailService.getById(dto.getTaskId());
        taskDetail.setTaskGroup(dto.getName());
        Integer id = taskDetail.getId();
        if (dto.getName().isEmpty()) {
            //驳回搞掉
            if (taskDetail.getIsRemarkOne() == 4 || taskDetail.getIsRemarkTwo() == 4) {
                //删除record
                LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Record::getTaskId, id);
                recordService.remove(queryWrapper);
                taskDetail.setIsRemarkOne(0);
                taskDetail.setIsRemarkTwo(1);
            }
            //待审核变成未提交
            if (taskDetail.getIsRemarkOne() == 3 || taskDetail.getIsRemarkTwo() == 3) {
                //materials
                taskDetail.setIsRemarkOne(0);
                taskDetail.setIsRemarkTwo(1);
                LambdaQueryWrapper<Materials> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Materials::getTaskId, id);
                queryWrapper.in(Materials::getType, 1, 2);
                materialsService.remove(queryWrapper);
            }


        }
        taskDetailService.updateById(taskDetail);
        return ResponseResult.okResult("更改成功！");

    }

    @PostMapping("/change/text")
    public ResponseResult changeText(@RequestBody ChangeGroupDto dto) {
        TaskDetail taskDetail = taskDetailService.getById(dto.getTaskId());
        taskDetail.setRemark(dto.getName());
        taskDetailService.updateById(taskDetail);
        return ResponseResult.okResult("更改成功！");

    }

    @GetMapping("/get/user/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PostMapping("/get/ng/num")
    public Integer getNgNum() {
        List<TaskDetail> list = taskDetailService.list();
        Integer sum = 0;
        for (TaskDetail taskDetail : list) {
            if (taskDetail.getGpsSituation().equals("未贴G") && !taskDetail.getCarSituation().equals("已处置")) ;
            sum++;
        }
        return sum;
    }

    @PostMapping("/change/group/2")
    public ResponseResult changeBatchGroup(@RequestBody ChangeGroupTwoDto dto) {
        Integer[] ids = dto.getIds();

        LambdaQueryWrapper<TaskDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TaskDetail::getId, ids);
        List<TaskDetail> tasks = taskDetailService.list(queryWrapper);
        for (TaskDetail taskDetail : tasks) {
            if (taskDetail.getIsRemarkOne() == 4 || taskDetail.getIsRemarkTwo() == 4) {
                //删除record
                LambdaQueryWrapper<Record> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.eq(Record::getTaskId, taskDetail.getId());
                recordService.remove(queryWrapper2);
                taskDetail.setIsRemarkOne(0);
                taskDetail.setIsRemarkTwo(1);
            }
            //待审核变成未提交
            if (taskDetail.getIsRemarkOne() == 3 || taskDetail.getIsRemarkTwo() == 3) {
                //materials
                taskDetail.setIsRemarkOne(0);
                taskDetail.setIsRemarkTwo(1);
                LambdaQueryWrapper<Materials> queryWrapper3 = new LambdaQueryWrapper<>();
                queryWrapper3.eq(Materials::getTaskId, taskDetail.getId());
                queryWrapper3.in(Materials::getType, 1, 2);
                materialsService.remove(queryWrapper3);
            }
        }
        List<TaskDetail> list = taskDetailService.list(queryWrapper);
        List<Hgroups> groups = groupsService.list();
        boolean has = false;
        for (Hgroups group : groups) {
            if (group.getName().equals(dto.getName())) {
                has = true;
                break;
            }
        }
        if (!has) {
            return ResponseResult.errorResult("队伍不存在");
        }
        for (TaskDetail taskDetail : list) {
            taskDetail.setTaskGroup(dto.getName());
        }
        taskDetailService.updateBatchById(list);
        return ResponseResult.okResult("更改队伍成功,队伍更改为" + dto.getName());
    }

    public String getAccessToken() {
        String appId = "wxa3a65dedbd291f1d";
        String appSecret = "2c9b92bcd7a2f44f3682c9cebec5da3a";
        String result = cn.hutool.http.HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(result);
        return jsonObject.getStr("access_token");
    }

    @PostMapping("/save/open/id")
    public ResponseResult saveOpenId(@RequestBody OpenIdDto dto) {
        String appId = "wxa3a65dedbd291f1d";
        String secret = "2c9b92bcd7a2f44f3682c9cebec5da3a";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret + "&js_code=" + dto.getCode() + "&grant_type=authorization_code";
        Map<String, Object> paramMap = new HashMap<>();  //map中带不带参数都可以这样用
        HttpResponse httpResponse = HttpRequest.get(url).form(paramMap).timeout(100000).execute();
        int status = httpResponse.getStatus();
        JSONObject arr = JSONObject.parseObject(httpResponse.body());
        String openId = arr.getString("openid");
        log.info(httpResponse.body());
        log.info("openid" + openId);
        if (openId == null) {
            return
                    ResponseResult.errorResult(arr.getString("errmsg"));
        }
        User user = userService.getById(dto.getUserId());
        user.setOpenId(openId);
        userService.updateById(user);
        return ResponseResult.okResult("操作成功");
    }


    @PostMapping("/delete/projects")
    public ResponseResult deleteProjects(@RequestParam String name) {
        LambdaQueryWrapper<TaskDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskDetail::getProjectName, name);
        taskDetailService.remove(queryWrapper);
        return ResponseResult.okResult("删除成功");

    }

    public void send() {
        String text = "审核通知";
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
            log.info(post);
        }
    }

}
