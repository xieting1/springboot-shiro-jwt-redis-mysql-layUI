package com.power.authority.authorization.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 资源信息权限表
 * </p>
 *
 * @author xieting
 * @since 2020-05-13
 */
@TableName("sys_resource")
public class ResourceEntity implements Serializable {

    private static final long serialVersionUID = 6078433162587783957L;
    /**
     * 资源id
     */
    private Long sId;

    /**
     * 资源父id
     */
    private Long sParentId;

    /**
     * 资源名称
     */
    private String sName;

    /**
     * 资源唯一标识
     */
    private String sSourceKey;

    /**
     * 资源类型, 0:目录; 1:菜单; 2:按钮
     */
    private Boolean sType;

    /**
     * 资源url
     */
    private String sSourceUrl;

    /**
     * 层级
     */
    private Integer sLevel;

    /**
     * 图标
     */
    private String sIcon;

    /**
     * 是否隐藏
     */
    private Boolean sIsHide;

    /**
     * 描述
     */
    private String sDescription;

    /**
     * 创建时间
     */
    private LocalDateTime sCreateTime;

    /**
     * 更新时间
     */
    private LocalDateTime sUpdateTime;

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }
    public Long getsParentId() {
        return sParentId;
    }

    public void setsParentId(Long sParentId) {
        this.sParentId = sParentId;
    }
    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
    public String getsSourceKey() {
        return sSourceKey;
    }

    public void setsSourceKey(String sSourceKey) {
        this.sSourceKey = sSourceKey;
    }
    public Boolean getsType() {
        return sType;
    }

    public void setsType(Boolean sType) {
        this.sType = sType;
    }
    public String getsSourceUrl() {
        return sSourceUrl;
    }

    public void setsSourceUrl(String sSourceUrl) {
        this.sSourceUrl = sSourceUrl;
    }
    public Integer getsLevel() {
        return sLevel;
    }

    public void setsLevel(Integer sLevel) {
        this.sLevel = sLevel;
    }
    public String getsIcon() {
        return sIcon;
    }

    public void setsIcon(String sIcon) {
        this.sIcon = sIcon;
    }
    public Boolean getsIsHide() {
        return sIsHide;
    }

    public void setsIsHide(Boolean sIsHide) {
        this.sIsHide = sIsHide;
    }
    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }
    public LocalDateTime getsCreateTime() {
        return sCreateTime;
    }

    public void setsCreateTime(LocalDateTime sCreateTime) {
        this.sCreateTime = sCreateTime;
    }
    public LocalDateTime getsUpdateTime() {
        return sUpdateTime;
    }

    public void setsUpdateTime(LocalDateTime sUpdateTime) {
        this.sUpdateTime = sUpdateTime;
    }

    @Override
    public String toString() {
        return "ResourceEntity{" +
            "sId=" + sId +
            ", sParentId=" + sParentId +
            ", sName=" + sName +
            ", sSourceKey=" + sSourceKey +
            ", sType=" + sType +
            ", sSourceUrl=" + sSourceUrl +
            ", sLevel=" + sLevel +
            ", sIcon=" + sIcon +
            ", sIsHide=" + sIsHide +
            ", sDescription=" + sDescription +
            ", sCreateTime=" + sCreateTime +
            ", sUpdateTime=" + sUpdateTime +
        "}";
    }
}
