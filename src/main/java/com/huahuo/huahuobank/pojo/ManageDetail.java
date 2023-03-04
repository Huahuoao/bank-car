package com.huahuo.huahuobank.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

/**
 * 
 * @TableName manage_detail
 */
@TableName(value ="manage_detail")
@Data
public class ManageDetail implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 1 债转 2过户
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 
     */
    @TableField(value = "the_other_name")
    private String theOtherName;

    /**
     * 
     */
    @TableField(value = "company")
    private String company;

    /**
     * 
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 
     */
    @TableField(value = "time")
    private String time;

    /**
     * 
     */
    @TableField(value = "register_name")
    private String registerName;

    /**
     * 
     */
    @TableField(value = "certificate_location")
    private String certificateLocation;

    /**
     * 
     */
    @TableField(value = "plate")
    private String plate;

    /**
     * 
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 
     */
    @TableField(value = "task_id")
    private Integer taskId;

    /**
     * 1 表示记录
       2 表示待审核
     */
    @TableField(value = "is_clear")
    private Integer isClear;

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
        ManageDetail other = (ManageDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getTheOtherName() == null ? other.getTheOtherName() == null : this.getTheOtherName().equals(other.getTheOtherName()))
            && (this.getCompany() == null ? other.getCompany() == null : this.getCompany().equals(other.getCompany()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getRegisterName() == null ? other.getRegisterName() == null : this.getRegisterName().equals(other.getRegisterName()))
            && (this.getCertificateLocation() == null ? other.getCertificateLocation() == null : this.getCertificateLocation().equals(other.getCertificateLocation()))
            && (this.getPlate() == null ? other.getPlate() == null : this.getPlate().equals(other.getPlate()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getIsClear() == null ? other.getIsClear() == null : this.getIsClear().equals(other.getIsClear()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getTheOtherName() == null) ? 0 : getTheOtherName().hashCode());
        result = prime * result + ((getCompany() == null) ? 0 : getCompany().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getRegisterName() == null) ? 0 : getRegisterName().hashCode());
        result = prime * result + ((getCertificateLocation() == null) ? 0 : getCertificateLocation().hashCode());
        result = prime * result + ((getPlate() == null) ? 0 : getPlate().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getIsClear() == null) ? 0 : getIsClear().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", price=").append(price);
        sb.append(", theOtherName=").append(theOtherName);
        sb.append(", company=").append(company);
        sb.append(", phone=").append(phone);
        sb.append(", time=").append(time);
        sb.append(", registerName=").append(registerName);
        sb.append(", certificateLocation=").append(certificateLocation);
        sb.append(", plate=").append(plate);
        sb.append(", remark=").append(remark);
        sb.append(", taskId=").append(taskId);
        sb.append(", isClear=").append(isClear);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}