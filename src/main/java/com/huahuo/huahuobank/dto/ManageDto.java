package com.huahuo.huahuobank.dto;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @作者 花火
 * @创建日期 2023/2/4 15:45
 */
@Data
public class ManageDto {
    Integer taskId;
    Integer userId;
    Integer type;
    Double price;
    String theOtherName;
    String company;
    String phone;
    String time;
    String registerName;
    String certificateLocation;
    String plate;
    String remark;
    Integer clearType; //0分期 1一次性
}
