package com.github.afterloe.pifinder.domain;

import java.io.Serializable;

public class Device implements Serializable {

    private Integer id;
    private String uid;
    private String ssid;
    private String pwd;
    private String name;
    private String remark;
    private String position;
    private Double createTime;
    private Double modifyTime;
    private Boolean status;

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", ssid='" + ssid + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", position='" + position + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", status=" + status +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Double createTime) {
        this.createTime = createTime;
    }

    public Double getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Double modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
