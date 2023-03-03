package com.huahuo.huahuobank.dto;

import lombok.Data;

/**
 * @作者 花火
 * @创建日期 2023/3/2 22:23
 */
@Data
public class ChangeMsgDto {
    String carOwnerPhone;
    String carAttribute;
    String orderId;
    String contractId;
    Double principal;
    Integer taskId;
}
