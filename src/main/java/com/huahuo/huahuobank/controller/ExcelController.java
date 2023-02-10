package com.huahuo.huahuobank.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.huahuo.huahuobank.common.ResponseResult;
import com.huahuo.huahuobank.mapper.TaskDetailMapper;
import com.huahuo.huahuobank.pojo.Task;
import com.huahuo.huahuobank.pojo.TaskDetail;
import com.huahuo.huahuobank.service.TaskDetailService;
import com.huahuo.huahuobank.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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

    @PostMapping("/upload")
    public ResponseResult importMemberList(@RequestPart("file") MultipartFile file) throws IOException {
        Integer id = null;
        List<Task> taskList = EasyExcel.read(file.getInputStream())
                .head(Task.class)
                .sheet()
                .doReadSync();

        for (Task task : taskList) {
           TaskDetail taskDetail = new TaskDetail();
           taskDetail.setCarPlate("123");
           taskDetailService.save(taskDetail);
           taskDetail.setCarPlate(null);
           id = taskDetail.getId();
           task.setDetailId(id);
           taskService.save(task);
           BeanUtils.copyProperties(task,taskDetail);
           taskDetail.setId(id);
           taskDetailService.updateById(taskDetail);
        }

        return ResponseResult.okResult("上传任务成功！");}

    @PostMapping("/download")
    public void upload(HttpServletResponse response) throws IOException {
        List<Task> list = taskService.list();
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
        EasyExcel.write(response.getOutputStream(), Task.class).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("任务发布清单")
                .doWrite(list);  //list就是存储的数据
    }
}
