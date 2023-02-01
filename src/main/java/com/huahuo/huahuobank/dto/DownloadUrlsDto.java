package com.huahuo.huahuobank.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @作者 花火
 * @创建日期 2023/1/31 1:14
 */
@Data
public class DownloadUrlsDto implements Serializable {
    Integer taskId;
    Integer type;
}
