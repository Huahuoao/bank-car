package com.huahuo.huahuobank.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huahuo.huahuobank.common.ResponseResult;
import com.huahuo.huahuobank.mapper.TaskDetailMapper;
import com.huahuo.huahuobank.pojo.Task;
import com.huahuo.huahuobank.pojo.TaskDetail;
import com.huahuo.huahuobank.service.TaskDetailService;
import com.huahuo.huahuobank.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @作者 花火
 * @创建日期 2023/1/31 12:17
 */
@RestController
@Slf4j
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    TaskDetailService taskDetailService;
    @Autowired
    TaskDetailMapper taskDetailMapper;


    @Autowired
    TaskService taskService;

    public static Double format(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @PostMapping("/upload")
    public ResponseResult importMemberList(@RequestPart("file") MultipartFile file) throws IOException {
        Integer id = null;
        List<Task> taskList = EasyExcel.read(file.getInputStream())
                .head(Task.class)
                .sheet()
                .doReadSync();
        LambdaQueryWrapper<TaskDetail> queryWrapper = new LambdaQueryWrapper<>();
        ArrayList<String> taskListTrue = new ArrayList<>();
        //先获取一个全是车牌的list
        for (Task task : taskList) {
            taskListTrue.add(task.getCarPlate());
        }
        queryWrapper.in(TaskDetail::getCarPlate, taskListTrue);
        //查找有没有相同的
        List<TaskDetail> list = taskDetailService.list(queryWrapper);
        //如果有就处理掉
        if (!list.isEmpty()) {
            List<String> realList = new ArrayList<>();
            for (TaskDetail taskDetail : list) {
                realList.add(taskDetail.getCarPlate());
            }
            return ResponseResult.errorResult(realList);
        }
//没有就正常加
        for (Task task : taskList) {
            if (StringUtils.isBlank(task.getCarPlate())) continue;
            TaskDetail taskDetail = new TaskDetail();
            if (task.getPrincipal() != null)
                task.setPrincipal(format(task.getPrincipal()));
            if (task.getEvaluation() != null)
                task.setEvaluation(format(task.getEvaluation()));
            taskDetail.setCarPlate("123");
            taskDetailService.save(taskDetail);
            taskDetail.setCarPlate(null);
            id = taskDetail.getId();
            task.setDetailId(id);
            taskService.save(task);
            BeanUtils.copyProperties(task, taskDetail);
            taskDetail.setId(id);
            taskDetail.setUpdateTime(DateUtil.now());
            taskDetailService.updateById(taskDetail);
        }

        return ResponseResult.okResult("上传任务成功！");
    }

    @PostMapping("/upload/new")
    public ResponseResult importMemberListNew(@RequestPart("file") MultipartFile file) throws IOException {
        Integer id = null;
        List<Task> taskList = EasyExcel.read(file.getInputStream())
                .head(Task.class)
                .sheet()
                .doReadSync();
        int sum = 0;
        for (Task task : taskList) {
            if (StringUtils.isBlank(task.getCarPlate())) continue;
            //     log.info("task=="+task.toString());
            LambdaQueryWrapper<TaskDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TaskDetail::getCarPlate, task.getCarPlate());
            TaskDetail taskDetail = taskDetailService.getOne(queryWrapper);
            if (task.getTaskGroup() != null) log.info(task.getTaskGroup());
            taskDetail.setTaskGroup(task.getTaskGroup());
            taskDetail.setCreateTime(task.getCreateTime());
            taskDetailService.updateById(taskDetail);
            sum++;
        }
        log.info("sum=" + sum);
        return ResponseResult.okResult("更新任务成功！");
    }

    @PostMapping("/download")
    public void upload(HttpServletResponse response, @RequestParam(value = "ids[]") Integer[] ids) throws IOException {
        List<TaskDetail> list = taskDetailService.list();
        LambdaQueryWrapper<TaskDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TaskDetail::getId, ids);
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 13);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String excelName = URLEncoder.encode("任务发布清单", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + excelName + ExcelTypeEnum.XLSX.getValue());
        EasyExcel.write(response.getOutputStream(), TaskDetail.class).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("任务发布清单")
                .doWrite(list);  //list就是存储的数据
    }

    @GetMapping("/download/all")
    public void upload(HttpServletResponse response) throws IOException {
        List<TaskDetail> list = taskDetailService.list();
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 13);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String excelName = URLEncoder.encode("任务发布清单", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + excelName + ExcelTypeEnum.XLSX.getValue());
        EasyExcel.write(response.getOutputStream(), TaskDetail.class).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("任务发布清单")
                .doWrite(list);  //list就是存储的数据
    }
}
