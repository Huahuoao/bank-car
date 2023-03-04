package com.huahuo.huahuobank.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.huahuo.huahuobank.common.CommonConverter;
import com.huahuo.huahuobank.common.HasConverter;
import lombok.Data;

/**
 * @TableName task_detail
 */
@TableName(value = "task_detail")
@Data
public class TaskDetail implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;
    /**
     * 车主姓名 1
     */
    @TableField(value = "car_owner_name")
    @ExcelProperty(value = "车主姓名")
    @ColumnWidth(10)
    private String carOwnerName;

    /**
     * 车名 2
     */
    @TableField(value = "car_name")
    @ExcelProperty(value = "车型")
    @ColumnWidth(40)
    private String carName;

    /**
     * 车牌 3
     */
    @TableField(value = "car_plate")
    @ExcelProperty(value = "车牌号")
    @ColumnWidth(10)
    private String carPlate;
    @TableField(value = "update_time")
    @ExcelIgnore
    private String updateTime;

    /**
     * 车架 4
     */
    @TableField(value = "frame")
    @ExcelProperty(value = "车架号")
    @ColumnWidth(30)
    private String frame;

    /**
     * gps情况 5
     */
    @TableField(value = "gps_situation")
    @ExcelProperty(value = "gps现况")
    @ColumnWidth(10)
    private String gpsSituation;

    /**
     * 收车时间 6
     */
    @TableField(value = "get_car_time")
    @ExcelProperty(value = "收车时间")
    @ColumnWidth(20)
    private String getCarTime;
    @TableField(value = "principal")  //7
    @ExcelProperty(value = "剩余本金")
    @ColumnWidth(10)
    private Double principal;
    @TableField(value = "gps_situation_two") //8
    @ExcelProperty(value = "gps在线情况")
    @ColumnWidth(10)
    private String gpsSituationTwo;
    @TableField(value = "get_car_group") //9
    @ExcelProperty(value = "收车团队")
    @ColumnWidth(40)
    private String getCarGroup;
    /**
     * 送达车库
     */
    @TableField(value = "sent_garage")   //10
    @ExcelProperty(value = "送达车库")
    @ColumnWidth(20)
    private String sentGarage;
    /**
     * 车辆现状  //11
     */
    @TableField(value = "car_situation")
    @ExcelProperty(value = "车辆现状")
    @ColumnWidth(10)
    private String carSituation;



    /**
     * 车主电话号码 12
     */
    @TableField(value = "car_owner_phone")
    @ExcelProperty(value = "车主电话号码")
    @ColumnWidth(10)
    private String carOwnerPhone;

    /**
     * 车辆属性 13
     */
    @TableField(value = "car_attribute")
    @ExcelProperty(value = "车辆属性")
    @ColumnWidth(10)
    private String carAttribute;

    /**
     * 订单编号
     */
    @TableField(value = "order_id")
    @ExcelProperty(value = "订单编号")
    @ColumnWidth(40)
    private String orderId;

    /**
     * 合同编号
     */
    @TableField(value = "contract_id") //14
    @ExcelProperty(value = "合同编号")
    @ColumnWidth(40)
    private String contractId;

    @TableField(value = "color")
    @ExcelProperty(value = "车身颜色") //15
    @ColumnWidth(10)
    private String color;
    @TableField(value = "remark")
    @ExcelProperty(value = "备注") //16
    @ColumnWidth(10)
    private String remark;


    /**
     * 项目名称  14
     */
    @TableField(value = "project_name")
    @ExcelProperty(value = "项目名称")
    @ColumnWidth(10)
    private String projectName;
    /**
     * 实际用车人
     */
    @TableField(value = "actual_user")
    @ExcelProperty(value = "实际用车人")  //15
    @ColumnWidth(10)
    private String actualUser;
    /**
     * 车上电话
     */
    @TableField(value = "car_phone")
    @ExcelProperty(value = "车上电话")
    @ColumnWidth(20)
    private String carPhone;


    /**
     * 车钥匙有无
     */
    @TableField(value = "has_key")
    @ExcelProperty(value = "有无车钥匙", converter = HasConverter.class)
    @ColumnWidth(10)
    private Integer hasKey;
    /**
     * 驾驶证有无
     */
    @TableField(value = "has_license")
    @ExcelProperty(value = "有无驾驶证", converter = HasConverter.class)
    @ColumnWidth(10)
    private Integer hasLicense;
    /**
     *
     */

    /**
     * 车况描述
     */
    @TableField(value = "car_condition_des")
    @ExcelProperty(value = "车况描述")
    @ColumnWidth(20)
    private String carConditionDes;

    /**
     * 车内物品描述
     */
    @TableField(value = "items_in_car_des")
    @ExcelProperty(value = "车内物品描述")
    @ColumnWidth(20)
    private String itemsInCarDes;

    /**
     * 属于哪一组的任务
     */
    @TableField(value = "task_group")
    @ExcelProperty(value = "任务组")   //
    @ColumnWidth(25)
    private String taskGroup;
    /**
     * 入库仓库
     */
    @TableField(value = "inbox_ware")
    @ExcelProperty(value = "车库保管位置")
    @ColumnWidth(35)
    private String inboxWare;
    @TableField(value = "has_manage")
    @ExcelProperty(value = "是否处置", converter = CommonConverter.class)
    @ColumnWidth(10)
    private Integer hasManage;

    /**
     * 车300估价
     */
    @TableField(value = "evaluation")
    @ExcelProperty(value = "车300估价")
    @ColumnWidth(10)
    private Double evaluation;
    @TableField(value = "manage_time")
    @ExcelProperty(value = "处置时间")
    @ColumnWidth(20)
    private String manageTime;
    @TableField(value = "province")
   @ExcelIgnore
    private String province;

    /**
     *
     */
    @TableField(value = "address")
    @ExcelIgnore
    private String address;
    /**
     * 备注
     */




    /**
     * 回收时间
     */
    @TableField(value = "recovery_time")
    @ExcelIgnore
    private String recoveryTime;

    @TableField(value = "count_time")
    @ExcelIgnore
    private Integer countTime;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    @ExcelIgnore
    private Double longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    @ExcelIgnore
    private Double latitude;

    /**
     * 位置信息（文本）
     */
    @TableField(value = "position")
    @ExcelIgnore
    private String position;










    /**
     * 审核一是否通过
     */
    @TableField(value = "is_remark_one")
    @ExcelIgnore
    private Integer isRemarkOne;

    /**
     * 审核二是否通过
     */
    @TableField(value = "is_remark_two")
    @ExcelIgnore
    private Integer isRemarkTwo;
    @TableField(value = "is_remark_fifth")
    @ExcelIgnore
    private Integer isRemarkFifth;

    /**
     * 审核三是否通过
     */
    @TableField(value = "is_remark_three")
    @ExcelIgnore
    private Integer isRemarkThree;

    /**
     *
     */
    @TableField(value = "is_remark_four")
    @ExcelIgnore
    private Integer isRemarkFour;

    /**
     * 第一次审核提交时间
     */
    @TableField(value = "first_sub_time")
    @ExcelIgnore
    private String firstSubTime;

    /**
     * 第一次审核通过时间
     */
    @TableField(value = "first_pass_time")
    @ExcelIgnore
    private String firstPassTime;

    /**
     * 第二次审核提交时间
     */
    @TableField(value = "second_sub_time")
    @ExcelIgnore
    private String secondSubTime;

    /**
     * 第二次审核通过时间
     */
    @TableField(value = "second_pass_time")
    @ExcelIgnore
    private String secondPassTime;

    /**
     * 第三次审核提交时间
     */
    @TableField(value = "third_sub_time")
    @ExcelIgnore
    private String thirdSubTime;

    /**
     * 第三次审核通过时间
     */
    @TableField(value = "third_pass_time")
    @ExcelIgnore
    private String thirdPassTime;

    /**
     * 第一次审核提交者
     */
    @TableField(value = "first_sub_user")
    @ExcelIgnore
    private String firstSubUser;

    /**
     * 第一次审核通过者
     */
    @TableField(value = "first_pass_user")
    @ExcelIgnore
    private String firstPassUser;
    @TableField(value = "second_sub_user")
    @ExcelIgnore
    private String secondSubUser;
    @TableField(value = "second_pass_user")
    @ExcelIgnore
    private String secondPassUser;
    /**
     * 第三次审核提交者
     */
    @TableField(value = "third_sub_user")
    @ExcelIgnore
    private String thirdSubUser;
    /**
     * 第三次审核通过者
     */
    @TableField(value = "third_pass_user")
    @ExcelIgnore
    private String thirdPassUser;

    /**
     * 第二次审核提交者
     */


    /**
     * 第二次审核通过者
     */






    /**
     *
     */
    @TableField(value = "forth_sub_time")
    @ExcelIgnore
    private String forthSubTime;

    /**
     *
     */
    @TableField(value = "forth_sub_user")
    @ExcelIgnore
    private String forthSubUser;

    /**
     *
     */
    @TableField(value = "forth_pass_time")
    @ExcelIgnore
    private String forthPassTime;

    /**
     *
     */
    @TableField(value = "forth_pass_user")
    @ExcelIgnore
    private String forthPassUser;

    /**
     *
     */


    /**
     *
     */
    @TableField(value = "has_inbox")
    @ExcelIgnore
    private Integer hasInbox;

    /**
     *
     */
    @TableField(value = "create_time")
    @ExcelIgnore
    private String createTime;

    /**
     * 审核状态
     * 1 未提交
     * 2 待审核
     * 3 驳回
     */
    @TableField(value = "process_situation")
    @ExcelIgnore
    private String processSituation;

    /**
     *
     */


    /**
     *
     */
    @TableField(value = "manage_way")
    @ExcelIgnore
    private Integer manageWay;

    /**
     *
     */


    /**
     *
     */
    @TableField(value = "is_clear")
    @ExcelIgnore
    private Integer isClear;

    /**
     * 离线
     */


    /**
     *
     */
    @TableField(value = "order_num")
    @ExcelIgnore
    private Integer orderNum;

    /**
     *
     */


    @TableField(exist = false)
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TaskDetail other = (TaskDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCarName() == null ? other.getCarName() == null : this.getCarName().equals(other.getCarName()))
                && (this.getFrame() == null ? other.getFrame() == null : this.getFrame().equals(other.getFrame()))
                && (this.getGpsSituation() == null ? other.getGpsSituation() == null : this.getGpsSituation().equals(other.getGpsSituation()))
                && (this.getGetCarTime() == null ? other.getGetCarTime() == null : this.getGetCarTime().equals(other.getGetCarTime()))
                && (this.getCarSituation() == null ? other.getCarSituation() == null : this.getCarSituation().equals(other.getCarSituation()))
                && (this.getCarOwnerName() == null ? other.getCarOwnerName() == null : this.getCarOwnerName().equals(other.getCarOwnerName()))
                && (this.getCarOwnerPhone() == null ? other.getCarOwnerPhone() == null : this.getCarOwnerPhone().equals(other.getCarOwnerPhone()))
                && (this.getCarAttribute() == null ? other.getCarAttribute() == null : this.getCarAttribute().equals(other.getCarAttribute()))
                && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
                && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
                && (this.getCarPlate() == null ? other.getCarPlate() == null : this.getCarPlate().equals(other.getCarPlate()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                && (this.getProjectName() == null ? other.getProjectName() == null : this.getProjectName().equals(other.getProjectName()))
                && (this.getRecoveryTime() == null ? other.getRecoveryTime() == null : this.getRecoveryTime().equals(other.getRecoveryTime()))
                && (this.getActualUser() == null ? other.getActualUser() == null : this.getActualUser().equals(other.getActualUser()))
                && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
                && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
                && (this.getPosition() == null ? other.getPosition() == null : this.getPosition().equals(other.getPosition()))
                && (this.getCarPhone() == null ? other.getCarPhone() == null : this.getCarPhone().equals(other.getCarPhone()))
                && (this.getHasKey() == null ? other.getHasKey() == null : this.getHasKey().equals(other.getHasKey()))
                && (this.getHasLicense() == null ? other.getHasLicense() == null : this.getHasLicense().equals(other.getHasLicense()))
                && (this.getCarConditionDes() == null ? other.getCarConditionDes() == null : this.getCarConditionDes().equals(other.getCarConditionDes()))
                && (this.getItemsInCarDes() == null ? other.getItemsInCarDes() == null : this.getItemsInCarDes().equals(other.getItemsInCarDes()))
                && (this.getSentGarage() == null ? other.getSentGarage() == null : this.getSentGarage().equals(other.getSentGarage()))
                && (this.getIsRemarkOne() == null ? other.getIsRemarkOne() == null : this.getIsRemarkOne().equals(other.getIsRemarkOne()))
                && (this.getIsRemarkTwo() == null ? other.getIsRemarkTwo() == null : this.getIsRemarkTwo().equals(other.getIsRemarkTwo()))
                && (this.getIsRemarkThree() == null ? other.getIsRemarkThree() == null : this.getIsRemarkThree().equals(other.getIsRemarkThree()))
                && (this.getIsRemarkFour() == null ? other.getIsRemarkFour() == null : this.getIsRemarkFour().equals(other.getIsRemarkFour()))
                && (this.getFirstSubTime() == null ? other.getFirstSubTime() == null : this.getFirstSubTime().equals(other.getFirstSubTime()))
                && (this.getFirstPassTime() == null ? other.getFirstPassTime() == null : this.getFirstPassTime().equals(other.getFirstPassTime()))
                && (this.getSecondSubTime() == null ? other.getSecondSubTime() == null : this.getSecondSubTime().equals(other.getSecondSubTime()))
                && (this.getSecondPassTime() == null ? other.getSecondPassTime() == null : this.getSecondPassTime().equals(other.getSecondPassTime()))
                && (this.getThirdSubTime() == null ? other.getThirdSubTime() == null : this.getThirdSubTime().equals(other.getThirdSubTime()))
                && (this.getThirdPassTime() == null ? other.getThirdPassTime() == null : this.getThirdPassTime().equals(other.getThirdPassTime()))
                && (this.getFirstSubUser() == null ? other.getFirstSubUser() == null : this.getFirstSubUser().equals(other.getFirstSubUser()))
                && (this.getFirstPassUser() == null ? other.getFirstPassUser() == null : this.getFirstPassUser().equals(other.getFirstPassUser()))
                && (this.getThirdSubUser() == null ? other.getThirdSubUser() == null : this.getThirdSubUser().equals(other.getThirdSubUser()))
                && (this.getSecondSubUser() == null ? other.getSecondSubUser() == null : this.getSecondSubUser().equals(other.getSecondSubUser()))
                && (this.getSecondPassUser() == null ? other.getSecondPassUser() == null : this.getSecondPassUser().equals(other.getSecondPassUser()))
                && (this.getTaskGroup() == null ? other.getTaskGroup() == null : this.getTaskGroup().equals(other.getTaskGroup()))
                && (this.getInboxWare() == null ? other.getInboxWare() == null : this.getInboxWare().equals(other.getInboxWare()))
                && (this.getThirdPassUser() == null ? other.getThirdPassUser() == null : this.getThirdPassUser().equals(other.getThirdPassUser()))
                && (this.getForthSubTime() == null ? other.getForthSubTime() == null : this.getForthSubTime().equals(other.getForthSubTime()))
                && (this.getForthSubUser() == null ? other.getForthSubUser() == null : this.getForthSubUser().equals(other.getForthSubUser()))
                && (this.getForthPassTime() == null ? other.getForthPassTime() == null : this.getForthPassTime().equals(other.getForthPassTime()))
                && (this.getForthPassUser() == null ? other.getForthPassUser() == null : this.getForthPassUser().equals(other.getForthPassUser()))
                && (this.getHasManage() == null ? other.getHasManage() == null : this.getHasManage().equals(other.getHasManage()))
                && (this.getHasInbox() == null ? other.getHasInbox() == null : this.getHasInbox().equals(other.getHasInbox()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getProcessSituation() == null ? other.getProcessSituation() == null : this.getProcessSituation().equals(other.getProcessSituation()))
                && (this.getEvaluation() == null ? other.getEvaluation() == null : this.getEvaluation().equals(other.getEvaluation()))
                && (this.getManageTime() == null ? other.getManageTime() == null : this.getManageTime().equals(other.getManageTime()))
                && (this.getManageWay() == null ? other.getManageWay() == null : this.getManageWay().equals(other.getManageWay()))
                && (this.getPrincipal() == null ? other.getPrincipal() == null : this.getPrincipal().equals(other.getPrincipal()))
                && (this.getIsClear() == null ? other.getIsClear() == null : this.getIsClear().equals(other.getIsClear()))
                && (this.getGpsSituationTwo() == null ? other.getGpsSituationTwo() == null : this.getGpsSituationTwo().equals(other.getGpsSituationTwo()))
                && (this.getOrderNum() == null ? other.getOrderNum() == null : this.getOrderNum().equals(other.getOrderNum()))
                && (this.getColor() == null ? other.getColor() == null : this.getColor().equals(other.getColor()))
                && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
                && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCarName() == null) ? 0 : getCarName().hashCode());
        result = prime * result + ((getFrame() == null) ? 0 : getFrame().hashCode());
        result = prime * result + ((getGpsSituation() == null) ? 0 : getGpsSituation().hashCode());
        result = prime * result + ((getGetCarTime() == null) ? 0 : getGetCarTime().hashCode());
        result = prime * result + ((getCarSituation() == null) ? 0 : getCarSituation().hashCode());
        result = prime * result + ((getCarOwnerName() == null) ? 0 : getCarOwnerName().hashCode());
        result = prime * result + ((getCarOwnerPhone() == null) ? 0 : getCarOwnerPhone().hashCode());
        result = prime * result + ((getCarAttribute() == null) ? 0 : getCarAttribute().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        result = prime * result + ((getCarPlate() == null) ? 0 : getCarPlate().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getProjectName() == null) ? 0 : getProjectName().hashCode());
        result = prime * result + ((getRecoveryTime() == null) ? 0 : getRecoveryTime().hashCode());
        result = prime * result + ((getActualUser() == null) ? 0 : getActualUser().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getPosition() == null) ? 0 : getPosition().hashCode());
        result = prime * result + ((getCarPhone() == null) ? 0 : getCarPhone().hashCode());
        result = prime * result + ((getHasKey() == null) ? 0 : getHasKey().hashCode());
        result = prime * result + ((getHasLicense() == null) ? 0 : getHasLicense().hashCode());
        result = prime * result + ((getCarConditionDes() == null) ? 0 : getCarConditionDes().hashCode());
        result = prime * result + ((getItemsInCarDes() == null) ? 0 : getItemsInCarDes().hashCode());
        result = prime * result + ((getSentGarage() == null) ? 0 : getSentGarage().hashCode());
        result = prime * result + ((getIsRemarkOne() == null) ? 0 : getIsRemarkOne().hashCode());
        result = prime * result + ((getIsRemarkTwo() == null) ? 0 : getIsRemarkTwo().hashCode());
        result = prime * result + ((getIsRemarkThree() == null) ? 0 : getIsRemarkThree().hashCode());
        result = prime * result + ((getIsRemarkFour() == null) ? 0 : getIsRemarkFour().hashCode());
        result = prime * result + ((getFirstSubTime() == null) ? 0 : getFirstSubTime().hashCode());
        result = prime * result + ((getFirstPassTime() == null) ? 0 : getFirstPassTime().hashCode());
        result = prime * result + ((getSecondSubTime() == null) ? 0 : getSecondSubTime().hashCode());
        result = prime * result + ((getSecondPassTime() == null) ? 0 : getSecondPassTime().hashCode());
        result = prime * result + ((getThirdSubTime() == null) ? 0 : getThirdSubTime().hashCode());
        result = prime * result + ((getThirdPassTime() == null) ? 0 : getThirdPassTime().hashCode());
        result = prime * result + ((getFirstSubUser() == null) ? 0 : getFirstSubUser().hashCode());
        result = prime * result + ((getFirstPassUser() == null) ? 0 : getFirstPassUser().hashCode());
        result = prime * result + ((getThirdSubUser() == null) ? 0 : getThirdSubUser().hashCode());
        result = prime * result + ((getSecondSubUser() == null) ? 0 : getSecondSubUser().hashCode());
        result = prime * result + ((getSecondPassUser() == null) ? 0 : getSecondPassUser().hashCode());
        result = prime * result + ((getTaskGroup() == null) ? 0 : getTaskGroup().hashCode());
        result = prime * result + ((getInboxWare() == null) ? 0 : getInboxWare().hashCode());
        result = prime * result + ((getThirdPassUser() == null) ? 0 : getThirdPassUser().hashCode());
        result = prime * result + ((getForthSubTime() == null) ? 0 : getForthSubTime().hashCode());
        result = prime * result + ((getForthSubUser() == null) ? 0 : getForthSubUser().hashCode());
        result = prime * result + ((getForthPassTime() == null) ? 0 : getForthPassTime().hashCode());
        result = prime * result + ((getForthPassUser() == null) ? 0 : getForthPassUser().hashCode());
        result = prime * result + ((getHasManage() == null) ? 0 : getHasManage().hashCode());
        result = prime * result + ((getHasInbox() == null) ? 0 : getHasInbox().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getProcessSituation() == null) ? 0 : getProcessSituation().hashCode());
        result = prime * result + ((getEvaluation() == null) ? 0 : getEvaluation().hashCode());
        result = prime * result + ((getManageTime() == null) ? 0 : getManageTime().hashCode());
        result = prime * result + ((getManageWay() == null) ? 0 : getManageWay().hashCode());
        result = prime * result + ((getPrincipal() == null) ? 0 : getPrincipal().hashCode());
        result = prime * result + ((getIsClear() == null) ? 0 : getIsClear().hashCode());
        result = prime * result + ((getGpsSituationTwo() == null) ? 0 : getGpsSituationTwo().hashCode());
        result = prime * result + ((getOrderNum() == null) ? 0 : getOrderNum().hashCode());
        result = prime * result + ((getColor() == null) ? 0 : getColor().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", carName=").append(carName);
        sb.append(", frame=").append(frame);
        sb.append(", gpsSituation=").append(gpsSituation);
        sb.append(", getCarTime=").append(getCarTime);
        sb.append(", carSituation=").append(carSituation);
        sb.append(", carOwnerName=").append(carOwnerName);
        sb.append(", carOwnerPhone=").append(carOwnerPhone);
        sb.append(", carAttribute=").append(carAttribute);
        sb.append(", orderId=").append(orderId);
        sb.append(", contractId=").append(contractId);
        sb.append(", carPlate=").append(carPlate);
        sb.append(", remark=").append(remark);
        sb.append(", projectName=").append(projectName);
        sb.append(", recoveryTime=").append(recoveryTime);
        sb.append(", actualUser=").append(actualUser);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", position=").append(position);
        sb.append(", carPhone=").append(carPhone);
        sb.append(", hasKey=").append(hasKey);
        sb.append(", hasLicense=").append(hasLicense);
        sb.append(", carConditionDes=").append(carConditionDes);
        sb.append(", itemsInCarDes=").append(itemsInCarDes);
        sb.append(", sentGarage=").append(sentGarage);
        sb.append(", isRemarkOne=").append(isRemarkOne);
        sb.append(", isRemarkTwo=").append(isRemarkTwo);
        sb.append(", isRemarkThree=").append(isRemarkThree);
        sb.append(", isRemarkFour=").append(isRemarkFour);
        sb.append(", firstSubTime=").append(firstSubTime);
        sb.append(", firstPassTime=").append(firstPassTime);
        sb.append(", secondSubTime=").append(secondSubTime);
        sb.append(", secondPassTime=").append(secondPassTime);
        sb.append(", thirdSubTime=").append(thirdSubTime);
        sb.append(", thirdPassTime=").append(thirdPassTime);
        sb.append(", firstSubUser=").append(firstSubUser);
        sb.append(", firstPassUser=").append(firstPassUser);
        sb.append(", thirdSubUser=").append(thirdSubUser);
        sb.append(", secondSubUser=").append(secondSubUser);
        sb.append(", secondPassUser=").append(secondPassUser);
        sb.append(", taskGroup=").append(taskGroup);
        sb.append(", inboxWare=").append(inboxWare);
        sb.append(", thirdPassUser=").append(thirdPassUser);
        sb.append(", forthSubTime=").append(forthSubTime);
        sb.append(", forthSubUser=").append(forthSubUser);
        sb.append(", forthPassTime=").append(forthPassTime);
        sb.append(", forthPassUser=").append(forthPassUser);
        sb.append(", hasManage=").append(hasManage);
        sb.append(", hasInbox=").append(hasInbox);
        sb.append(", createTime=").append(createTime);
        sb.append(", processSituation=").append(processSituation);
        sb.append(", evaluation=").append(evaluation);
        sb.append(", manageTime=").append(manageTime);
        sb.append(", manageWay=").append(manageWay);
        sb.append(", principal=").append(principal);
        sb.append(", isClear=").append(isClear);
        sb.append(", gpsSituationTwo=").append(gpsSituationTwo);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", color=").append(color);
        sb.append(", province=").append(province);
        sb.append(", address=").append(address);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}