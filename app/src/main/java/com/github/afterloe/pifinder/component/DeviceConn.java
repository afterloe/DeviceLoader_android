package com.github.afterloe.pifinder.component;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.github.afterloe.pifinder.utils.NetworkUtils;
import com.github.afterloe.pifinder.domain.Device;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 *  create by afterloe
 */
public class DeviceConn implements Serializable {

    private WifiManager wifiManager;

    public DeviceConn(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }

    // 查看以前是否也配置过这个设备
    public WifiConfiguration isExist(String ssid) {
        String SSID = "\"" + ssid + "\"";
        List<WifiConfiguration> existConfigs = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration existConfig: existConfigs) {
            if (existConfig.SSID.equals(SSID)) {
                return existConfig;
            }
        }
        return null;
    }

    // 创建device 连接文件
    public WifiConfiguration createDeviceConnFile(Device device) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + device.getSsid() + "\"";
        wifiConfiguration.preSharedKey =  "\"" + device.getSecret() + "\"";
        wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
        return wifiConfiguration;
    }

    /**
     *  打开设备
     */
    public void openDevice() {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
    }

    private static boolean isHexWepKey(String wepKey) {
        final int len = wepKey.length();

        // WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
        if (len != 10 && len != 26 && len != 58) {
            return false;
        }

        return isHex(wepKey);
    }

    private static boolean isHex(String key) {
        for (int i = key.length() - 1; i >= 0; i--) {
            final char c = key.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a'
                    && c <= 'f')) {
                return false;
            }
        }

        return true;
    }

    /**
     * 获取 bssid 接入点的地址
     * @return
     */
    public String getBSSID() {
        if (wifiManager == null) {
            return null;
        }
        WifiInfo info = wifiManager.getConnectionInfo();
        if (info == null) {
            return null;
        }
        return info.getBSSID();
    }

    /**
     * 获取网关地址
     *
     * @return
     */
    public String getGateway() {
        if (wifiManager == null) {
            return null;
        }
        InetAddress inetAddress = NetworkUtils.intToInetAddress(wifiManager.getDhcpInfo().gateway);
        if (inetAddress == null) {
            return "";
        }
        return inetAddress.getHostAddress();
    }

    /**
     * 获取ip地址
     * @return
     */
    public String getIpAddress(){
        if (wifiManager == null) {
            return null;
        }
        InetAddress inetAddress = NetworkUtils.intToInetAddress(wifiManager.getConnectionInfo().getIpAddress());
        if (inetAddress == null) {
            return "";
        }
        return inetAddress.getHostAddress();
    }

    /**
     * 获取mac地址
     * @return
     */
    public String getMacAddress(){
        if (wifiManager == null) {
            return null;
        }
        return wifiManager.getConnectionInfo().getMacAddress();
    }

    /**
     * 获取wifi名称
     *
     * @return
     */
    public String getSSID() {
        if (wifiManager == null) {
            return null;
        }
        WifiInfo info = wifiManager.getConnectionInfo();
        String ssid = info.getSSID();
        if (ssid != null) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        return ssid;
    }

    /**
     * 扫描WIFI AP
     */
    public boolean startStan() {
        if (wifiManager == null) {
            return false;
        }
        return wifiManager.startScan();
    }

    /**
     * 获取所有WIFI AP
     */
    public List<ScanResult> getScanResults() {
        List<ScanResult> srList = wifiManager.getScanResults();
        if (srList == null) {
            srList = new ArrayList<ScanResult>();
        }
        return srList;
    }
}
