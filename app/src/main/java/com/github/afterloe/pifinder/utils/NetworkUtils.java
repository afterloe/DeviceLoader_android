package com.github.afterloe.pifinder.utils;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.github.afterloe.pifinder.R;
import com.google.gson.JsonSyntaxException;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class NetworkUtils implements Serializable {

    public static final int WIFICIPHER_NOPASS = 0;
    public static final int WIFICIPHER_WEP = 1;
    public static final int WIFICIPHER_WPA = 2;

    /**
     * Convert a IPv4 address from an integer to an InetAddress.
     *
     * @param hostAddress
     *            an int corresponding to the IPv4 address in network byte order
     */
    public static InetAddress intToInetAddress(int hostAddress) {
        byte[] addressBytes = { (byte) (0xff & hostAddress), (byte) (0xff & (hostAddress >> 8)),
                (byte) (0xff & (hostAddress >> 16)), (byte) (0xff & (hostAddress >> 24)) };

        try {
            return InetAddress.getByAddress(addressBytes);
        } catch (UnknownHostException e) {
            throw new AssertionError();
        }
    }

    /**
     * Convert a IPv4 address from an InetAddress to an integer
     *
     * @param inetAddr
     *            is an InetAddress corresponding to the IPv4 address
     * @return the IP address as an integer in network byte order
     */
    public static int inetAddressToInt(InetAddress inetAddr) throws IllegalArgumentException {
        byte[] addr = inetAddr.getAddress();
        return ((addr[3] & 0xff) << 24) | ((addr[2] & 0xff) << 16) | ((addr[1] & 0xff) << 8) | (addr[0] & 0xff);
    }

    private static final double A_Value = 50; /**A - 发射端和接收端相隔1米时的信号强度*/
    private static final double n_Value = 2.77; /** n - 环境衰减因子*/

    /**
     * 估算wifi距离
     *
     * @param rssi
     * @return
     */
    public static Double wifiDistance(int rssi){
        int iRssi = Math.abs(rssi);
        double power = (iRssi - A_Value) / ( 10 * n_Value);
        return Math.pow(10, power);
    }

    private static WifiConfiguration isExist(List<WifiConfiguration> result, String ssid) {
        String fetchSSID = "\"" + ssid + "\"";
        return result.stream().filter(r -> r.SSID.equals(fetchSSID)).findAny().orElse(null);
    }

    public static WifiConfiguration createWifiConfig(WifiManager mWifiManager, String ssid, String password, int type) {
        //初始化WifiConfiguration
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();

        //指定对应的SSID
        config.SSID = "\"" + ssid + "\"";

        //如果之前有类似的配置
        WifiConfiguration tempConfig = isExist(mWifiManager.getConfiguredNetworks(), ssid);
        if(tempConfig != null) {
            return tempConfig;
        }

        //不需要密码的场景
        if(type == WIFICIPHER_NOPASS) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            //以WEP加密的场景
        } else if(type == WIFICIPHER_WEP) {
            config.hiddenSSID = true;
            config.wepKeys[0]= "\""+password+"\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
            //以WPA加密的场景，自己测试时，发现热点以WPA2建立时，同样可以用这种配置连接
        } else if(type == WIFICIPHER_WPA) {
            config.preSharedKey = "\""+password+"\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }

        return config;
    }

    public static <T> ObservableTransformer<T, T> compose() {
        return upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public static void processRequestException(Throwable e) {
        if(e instanceof ConnectException || e instanceof SocketException) {
            ToastUtils.shortToast(ResUtils.getString(R.string.network_connected_exception));
        } else if(e instanceof SocketTimeoutException) {
            ToastUtils.shortToast(ResUtils.getString(R.string.network_socket_time_out));
        } else if(e instanceof JsonSyntaxException) {
            ToastUtils.shortToast(ResUtils.getString(R.string.network_json_syntax_exception));
        } else if(e instanceof UnknownHostException) {
            ToastUtils.shortToast(ResUtils.getString(R.string.network_unknown_host));
        } else {
            Timber.d(e.getMessage());
        }
    }
}
