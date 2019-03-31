package com.github.afterloe.pifinder.domain;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {

    private Integer deviceId;
    private String host;
    private String url;
    private String remarks;
    private Integer id;
    private Boolean status;
    private Long createTime;
    private Long modifyTime;
    private Long lastSync;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getLastSync() {
        return lastSync;
    }

    public void setLastSync(Long lastSync) {
        this.lastSync = lastSync;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(id, point.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Point{" +
                "deviceId=" + deviceId +
                ", host='" + host + '\'' +
                ", url='" + url + '\'' +
                ", remarks='" + remarks + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", lastSync=" + lastSync +
                '}';
    }
}
