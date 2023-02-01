package com.huahuo.huahuobank.dto;

import lombok.Data;

/**
 * @作者 花火
 * @创建日期 2023/1/31 11:45
 */
@Data
public class FinalCheckDto extends CheckDto {
    String actualUser;
    String recoveryTime;
    String sentGarage;
    String inboxWare;
    String carPhone;
    Integer hasLicense;
    Integer hasKey;
    String carConditionDes;
    String itemsInCarDes;
}
