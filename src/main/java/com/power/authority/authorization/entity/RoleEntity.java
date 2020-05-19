package com.power.authority.authorization.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author xieting
 * @since 2020-05-13
 */
@TableName("sys_role")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = -170211694739673722L;
    /**
     * 角色编号
     */
    private Long rId;

    /**
     * 角色名称
     */
    private String rName;

    /**
     * 角色描述
     */
    private String rDescription;

    /**
     * 角色key
     */
    private String rKey;

    /**
     * 角色状态, 0:正常; 1:删除
     */
    private Boolean rStatus;

    /**
     * 创建人id
     */
    private Long uId;

    /**
     * 创建时间
     */
    private LocalDateTime rCreateTime;

    /**
     * 修改时间
     */
    private LocalDateTime rUpdateTime;

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }
    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }
    public String getrDescription() {
        return rDescription;
    }

    public void setrDescription(String rDescription) {
        this.rDescription = rDescription;
    }
    public String getrKey() {
        return rKey;
    }

    public void setrKey(String rKey) {
        this.rKey = rKey;
    }
    public Boolean getrStatus() {
        return rStatus;
    }

    public void setrStatus(Boolean rStatus) {
        this.rStatus = rStatus;
    }
    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }
    public LocalDateTime getrCreateTime() {
        return rCreateTime;
    }

    public void setrCreateTime(LocalDateTime rCreateTime) {
        this.rCreateTime = rCreateTime;
    }
    public LocalDateTime getrUpdateTime() {
        return rUpdateTime;
    }

    public void setrUpdateTime(LocalDateTime rUpdateTime) {
        this.rUpdateTime = rUpdateTime;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
            "rId=" + rId +
            ", rName=" + rName +
            ", rDescription=" + rDescription +
            ", rKey=" + rKey +
            ", rStatus=" + rStatus +
            ", uId=" + uId +
            ", rCreateTime=" + rCreateTime +
            ", rUpdateTime=" + rUpdateTime +
        "}";
    }
}
