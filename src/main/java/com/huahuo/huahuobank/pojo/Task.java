package com.huahuo.huahuobank.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName task
 */
@TableName(value ="task")
@Data
public class Task implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;

    /**
     *
     */
    @TableField(value = "car_name")
    @ExcelProperty("车型")
    @ColumnWidth(35)
    private String carName;

    /**
     *
     */
    @TableField(value = "frame")
    @ExcelProperty("车架号")
    @ColumnWidth(25)
    private String frame;

    /**
     *
     */
    @TableField(value = "car_owner_name")
    @ExcelProperty("车主姓名")
    @ColumnWidth(20)
    private String carOwnerName;

    /**
     *
     */
    @TableField(value = "car_owner_phone")
    @ExcelProperty("车主电话号码")
    @ColumnWidth(30)
    private String carOwnerPhone;

    /**
     *
     */
    @TableField(value = "car_attribute")
    @ExcelProperty("车辆属性")
    @ColumnWidth(15)
    private String carAttribute;

    /**
     *
     */
    @TableField(value = "order_id")
    @ExcelProperty("订单编号")
    @ColumnWidth(30)
    private String orderId;

    /**
     *
     */
    @TableField(value = "contract_id")
    @ExcelProperty("合同编号")
    @ColumnWidth(30)
    private String contractId;

    /**
     *
     */
    @TableField(value = "car_plate")
    @ExcelProperty("车牌号")
    @ColumnWidth(30)
    private String carPlate;

    /**
     *
     */
    @TableField(value = "remark")
    @ExcelProperty("备注")
    @ColumnWidth(50)
    private String remark;

    /**
     *
     */
    @TableField(value = "project_name")
    @ExcelProperty("项目名称")
    @ColumnWidth(15)
    private String projectName;

    /**
     *
     */
    @TableField(value = "task_group")
    @ExcelProperty("任务组")
    @ColumnWidth(10)
    private String taskGroup;
    @TableField(value = "principal")
    @ExcelProperty(value = "本金(单位/元)")
    @ColumnWidth(10)
    private Double principal;
    /**
     *
     */
    @TableField(value = "detail_id")
    @ExcelIgnore
    private Integer detailId;

    @TableField(value = "evaluation")
    @ExcelProperty(value = "车300估价(单位/元)")
    @ColumnWidth(15)
    private Double evaluation;

    /**
     * 
     */
    @TableField(value = "color")
    @ExcelProperty(value = "车身颜色")
    @ColumnWidth(15)
    private String color;

    /**
     * 
     */
    @TableField(value = "province")
    @ExcelProperty(value = "省份")
    @ColumnWidth(40)
    private String province;

    /**
     * 
     */
    @TableField(value = "address")
    @ExcelProperty(value = "车主地址")
    @ColumnWidth(40)
    private String address;

    /**
     * 
     */
    @TableField(value = "gps_situation_two")
    @ExcelProperty(value = "GPS情况")
    @ColumnWidth(10)
    private String gpsSituationTwo;




    @ExcelProperty(value = "导包时间")
    @ColumnWidth(40)
    @TableField(value = "create_time")
    private String createTime;
    @TableField(exist = false)
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
        Task other = (Task) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCarName() == null ? other.getCarName() == null : this.getCarName().equals(other.getCarName()))
            && (this.getFrame() == null ? other.getFrame() == null : this.getFrame().equals(other.getFrame()))
            && (this.getCarOwnerName() == null ? other.getCarOwnerName() == null : this.getCarOwnerName().equals(other.getCarOwnerName()))
            && (this.getCarOwnerPhone() == null ? other.getCarOwnerPhone() == null : this.getCarOwnerPhone().equals(other.getCarOwnerPhone()))
            && (this.getCarAttribute() == null ? other.getCarAttribute() == null : this.getCarAttribute().equals(other.getCarAttribute()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
            && (this.getCarPlate() == null ? other.getCarPlate() == null : this.getCarPlate().equals(other.getCarPlate()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getProjectName() == null ? other.getProjectName() == null : this.getProjectName().equals(other.getProjectName()))
            && (this.getTaskGroup() == null ? other.getTaskGroup() == null : this.getTaskGroup().equals(other.getTaskGroup()))
            && (this.getDetailId() == null ? other.getDetailId() == null : this.getDetailId().equals(other.getDetailId()))
            && (this.getEvaluation() == null ? other.getEvaluation() == null : this.getEvaluation().equals(other.getEvaluation()))
            && (this.getPrincipal() == null ? other.getPrincipal() == null : this.getPrincipal().equals(other.getPrincipal()))
            && (this.getColor() == null ? other.getColor() == null : this.getColor().equals(other.getColor()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getGpsSituationTwo() == null ? other.getGpsSituationTwo() == null : this.getGpsSituationTwo().equals(other.getGpsSituationTwo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCarName() == null) ? 0 : getCarName().hashCode());
        result = prime * result + ((getFrame() == null) ? 0 : getFrame().hashCode());
        result = prime * result + ((getCarOwnerName() == null) ? 0 : getCarOwnerName().hashCode());
        result = prime * result + ((getCarOwnerPhone() == null) ? 0 : getCarOwnerPhone().hashCode());
        result = prime * result + ((getCarAttribute() == null) ? 0 : getCarAttribute().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        result = prime * result + ((getCarPlate() == null) ? 0 : getCarPlate().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getProjectName() == null) ? 0 : getProjectName().hashCode());
        result = prime * result + ((getTaskGroup() == null) ? 0 : getTaskGroup().hashCode());
        result = prime * result + ((getDetailId() == null) ? 0 : getDetailId().hashCode());
        result = prime * result + ((getEvaluation() == null) ? 0 : getEvaluation().hashCode());
        result = prime * result + ((getPrincipal() == null) ? 0 : getPrincipal().hashCode());
        result = prime * result + ((getColor() == null) ? 0 : getColor().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getGpsSituationTwo() == null) ? 0 : getGpsSituationTwo().hashCode());
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
        sb.append(", carOwnerName=").append(carOwnerName);
        sb.append(", carOwnerPhone=").append(carOwnerPhone);
        sb.append(", carAttribute=").append(carAttribute);
        sb.append(", orderId=").append(orderId);
        sb.append(", contractId=").append(contractId);
        sb.append(", carPlate=").append(carPlate);
        sb.append(", remark=").append(remark);
        sb.append(", projectName=").append(projectName);
        sb.append(", taskGroup=").append(taskGroup);
        sb.append(", detailId=").append(detailId);
        sb.append(", evaluation=").append(evaluation);
        sb.append(", principal=").append(principal);
        sb.append(", color=").append(color);
        sb.append(", province=").append(province);
        sb.append(", address=").append(address);
        sb.append(", gpsSituationTwo=").append(gpsSituationTwo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}