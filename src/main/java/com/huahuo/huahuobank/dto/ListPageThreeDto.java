package com.huahuo.huahuobank.dto;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @作者 花火
 * @创建日期 2023/2/7 0:10
 */
@Data
public class ListPageThreeDto  extends PageRequestDto{
    Integer userId;
    String keyword;
    String group;
    Integer orderByPrice;
    Integer orderByPriceType;
    Integer manageWay;
}
