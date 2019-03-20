package com.github.afterloe.pifinder.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

public class Device implements Serializable {

    private String deviceName;
    private Boolean online;
    private String ssid;
    private String secret;
    private String dataURL;

    public Device(String deviceName) {
        this.deviceName = deviceName;
        this.ssid = "pi@" + deviceName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(deviceName, device.deviceName) &&
                Objects.equals(online, device.online) &&
                Objects.equals(ssid, device.ssid) &&
                Objects.equals(secret, device.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceName, online, ssid, secret);
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getDataURL() {
        return dataURL;
    }

    public void setDataURL(String dataURL) {
        this.dataURL = dataURL;
    }
}
