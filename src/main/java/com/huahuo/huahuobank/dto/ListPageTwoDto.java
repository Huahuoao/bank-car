package com.huahuo.huahuobank.dto;

import lombok.Data;

/**
 * @作者 花火
 * @创建日期 2023/2/4 16:05
 */
@Data
public class ListPageTwoDto extends PageRequestDto{
    Integer orderByPrice;
    Integer orderByPriceType;
    String group;
    Integer orderByTime;
    Integer orderByTimeType;
    String keyword;
    Integer ManageWay;
    Integer type; // 1 2 3 4 5
    Integer inStage; //1
}
