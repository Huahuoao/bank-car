package com.huahuo.huahuobank.dto;

import lombok.Data;

/**
 * @作者 花火
 * @创建日期 2023/2/8 22:04
 */

@Data
public class GetUserDto extends PageRequestDto {
    String group;
    String level;
    String keyWord;
}
