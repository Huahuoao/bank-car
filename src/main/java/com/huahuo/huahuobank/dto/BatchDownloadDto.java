package com.huahuo.huahuobank.dto;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @作者 花火
 * @创建日期 2023/2/2 21:41
 */
@Data
public class BatchDownloadDto {
    Integer taskId;
    Integer type;
}
