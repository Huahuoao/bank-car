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
 * @TableName sub_time
 */
@TableName(value ="sub_time")
@Data
public class SubTime implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "sub_user")
    private String subUser;

    /**
     * 
     */
    @TableField(value = "pass_user")
    private String passUser;

    /**
     * 
     */
    @TableField(value = "sub_time")
    private String subTime;

    /**
     * 
     */
    @TableField(value = "pass_time")
    private String passTime;

    /**
     * 
     */
    @TableField(value = "before_time")
    private Integer beforeTime;

    /**
     * 
     */
    @TableField(value = "now_time")
    private Integer nowTime;

    /**
     * 
     */
    @TableField(value = "text")
    private String text;

    /**
     * 0驳回 1通过 3待审核
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 
     */
    @TableField(value = "task_id")
    private Integer taskId;

    /**
     * 
     */
    @TableField(value = "reject_text")
    private String rejectText;

    /**
     * 
     */
    @TableField(value = "is_new")
    private Integer isNew;

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
        SubTime other = (SubTime) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSubUser() == null ? other.getSubUser() == null : this.getSubUser().equals(other.getSubUser()))
            && (this.getPassUser() == null ? other.getPassUser() == null : this.getPassUser().equals(other.getPassUser()))
            && (this.getSubTime() == null ? other.getSubTime() == null : this.getSubTime().equals(other.getSubTime()))
            && (this.getPassTime() == null ? other.getPassTime() == null : this.getPassTime().equals(other.getPassTime()))
            && (this.getBeforeTime() == null ? other.getBeforeTime() == null : this.getBeforeTime().equals(other.getBeforeTime()))
            && (this.getNowTime() == null ? other.getNowTime() == null : this.getNowTime().equals(other.getNowTime()))
            && (this.getText() == null ? other.getText() == null : this.getText().equals(other.getText()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getRejectText() == null ? other.getRejectText() == null : this.getRejectText().equals(other.getRejectText()))
            && (this.getIsNew() == null ? other.getIsNew() == null : this.getIsNew().equals(other.getIsNew()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSubUser() == null) ? 0 : getSubUser().hashCode());
        result = prime * result + ((getPassUser() == null) ? 0 : getPassUser().hashCode());
        result = prime * result + ((getSubTime() == null) ? 0 : getSubTime().hashCode());
        result = prime * result + ((getPassTime() == null) ? 0 : getPassTime().hashCode());
        result = prime * result + ((getBeforeTime() == null) ? 0 : getBeforeTime().hashCode());
        result = prime * result + ((getNowTime() == null) ? 0 : getNowTime().hashCode());
        result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getRejectText() == null) ? 0 : getRejectText().hashCode());
        result = prime * result + ((getIsNew() == null) ? 0 : getIsNew().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", subUser=").append(subUser);
        sb.append(", passUser=").append(passUser);
        sb.append(", subTime=").append(subTime);
        sb.append(", passTime=").append(passTime);
        sb.append(", beforeTime=").append(beforeTime);
        sb.append(", nowTime=").append(nowTime);
        sb.append(", text=").append(text);
        sb.append(", type=").append(type);
        sb.append(", taskId=").append(taskId);
        sb.append(", rejectText=").append(rejectText);
        sb.append(", isNew=").append(isNew);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}