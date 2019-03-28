package com.github.afterloe.pifinder.utils;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtils implements Serializable {
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
}
