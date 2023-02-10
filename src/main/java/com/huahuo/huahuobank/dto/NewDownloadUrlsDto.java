package com.huahuo.huahuobank.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @作者 花火
 * @创建日期 2023/2/8 2:07
 */
@Data
public class NewDownloadUrlsDto extends DownloadUrlsDto implements Serializable {
    Integer num;
}
