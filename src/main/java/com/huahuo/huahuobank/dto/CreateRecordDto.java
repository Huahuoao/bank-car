package com.huahuo.huahuobank.dto;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @作者 花火
 * @创建日期 2023/2/6 11:28
 */
@Data
public class CreateRecordDto {
    Integer taskId;
    Integer userId;
    String text;
}
