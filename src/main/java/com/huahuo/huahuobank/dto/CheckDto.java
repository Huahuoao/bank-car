package com.huahuo.huahuobank.dto;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @作者 花火
 * @创建日期 2023/1/31 1:25
 */
@Data
public class CheckDto implements Serializable {
    Integer taskId;
    Integer userId;

}
