package com.huahuo.huahuobank.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @作者 花火
 * @创建日期 2023/1/31 1:50
 */
@Data
public class ListPageDto extends PageRequestDto implements Serializable {
    Integer userId;
    Integer type;
    String group;
    String keyWord;
}
