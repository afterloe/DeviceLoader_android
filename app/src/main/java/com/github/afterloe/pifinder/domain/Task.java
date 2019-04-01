package com.github.afterloe.pifinder.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

public class Task implements Serializable {

    private Integer id;
    private String name;
    private String ssid;
    private String pwd;
    private String remark;
    private Collection<Point> points;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Collection<Point> getPoints() {
        return points;
    }

    public void setPoints(Collection<Point> points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ssid='" + ssid + '\'' +
                ", pwd='" + pwd + '\'' +
                ", remark='" + remark + '\'' +
                ", points=" + points +
                '}';
    }
}
