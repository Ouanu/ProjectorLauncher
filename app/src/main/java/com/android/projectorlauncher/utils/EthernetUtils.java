package com.android.projectorlauncher.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.EthernetManager;
import android.net.IpConfiguration;
import android.net.LinkAddress;
import android.net.StaticIpConfiguration;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SuppressWarnings("unused")
public class EthernetUtils {

    /**
     * 设置动态IP
     * @param context 上下文
     */
    public static void setDhcpIp(Context context) {
        if (context == null) return;
        @SuppressLint("WrongConstant")
        EthernetManager manager = (EthernetManager) context.getSystemService("ethernet");
        manager.setConfiguration(
                "eth0",
                new IpConfiguration(
                        IpConfiguration.IpAssignment.DHCP,
                        IpConfiguration.ProxySettings.NONE,
                        null,
                        null));
        manager.setEthernetEnabled(true);
    }

    /**
     * 设置静态IP
     * @param context 上下文
     * @param ipAddress ip地址
     * @param netmask 掩码
     * @param gate 网关
     * @param dns DNS地址
     */
    public static void setStaticIp(Context context, String ipAddress, String netmask, String gate, String dns) {
        if (context == null) return;
        @SuppressLint("WrongConstant")
        EthernetManager manager = (EthernetManager) context.getSystemService("ethernet");
        StaticIpConfiguration staticIpConfiguration = new StaticIpConfiguration();
        Class<?> clazz = null;
        try {
            clazz = Class.forName("android.net.LinkAddress");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Class[] cl = new Class[]{InetAddress.class, int.class};
        Constructor cons = null;
        try {
            cons = clazz.getConstructor(cl);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            staticIpConfiguration.ipAddress = (LinkAddress) cons.newInstance(InetAddress.getByName(ipAddress), getPrefixLength(netmask));
            staticIpConfiguration.gateway = InetAddress.getByName(gate);
            staticIpConfiguration.domains = netmask;
            staticIpConfiguration.dnsServers.add(InetAddress.getByName(dns));
            IpConfiguration ipConfiguration = new IpConfiguration();
            ipConfiguration.ipAssignment = IpConfiguration.IpAssignment.STATIC;
            ipConfiguration.proxySettings = IpConfiguration.ProxySettings.STATIC;
            ipConfiguration.staticIpConfiguration = staticIpConfiguration;
            manager.setConfiguration("eth0", ipConfiguration);
        } catch (UnknownHostException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // 获取IP地址
    public static String getIpAddress(Context context) {
        if (context == null) return "";
        @SuppressLint("WrongConstant")
        EthernetManager manager = (EthernetManager) context.getSystemService("ethernet");
        DhcpInfo dhcpInfo = manager.getDhcpInfo();
        if (dhcpInfo == null) {
            return "";
        }
        return formatAddress(dhcpInfo.ipAddress);
    }

    //获取网关地址
    public static String getGateway(Context context) {
        if (context == null) return "";
        @SuppressLint("WrongConstant")
        EthernetManager manager = (EthernetManager) context.getSystemService("ethernet");
        DhcpInfo dhcpInfo = manager.getDhcpInfo();
        if (dhcpInfo == null) {
            return "";
        }
        return formatAddress(dhcpInfo.gateway);
    }

    //获取子网掩码
    public static String getNetmask(Context context) {
        if (context == null) return "";
        @SuppressLint("WrongConstant")
        EthernetManager manager = (EthernetManager) context.getSystemService("ethernet");
        DhcpInfo dhcpInfo = manager.getDhcpInfo();
        if (dhcpInfo == null) {
            return "";
        }
        return formatAddress(dhcpInfo.netmask);
    }

    //获取DNS
    public static String getDNS(Context context) {
        if (context == null) return "";
        @SuppressLint("WrongConstant")
        EthernetManager manager = (EthernetManager) context.getSystemService("ethernet");
        DhcpInfo dhcpInfo = manager.getDhcpInfo();
        if (dhcpInfo == null) {
            return "";
        }
        return formatAddress(dhcpInfo.dns1);
    }

    //获取模式
    public static String getMode(Context context) {
        if (context == null) return "";
        @SuppressLint("WrongConstant")
        EthernetManager manager = (EthernetManager) context.getSystemService("ethernet");
        return manager.getEthernetMode();
    }

    private static int getPrefixLength(String mask) {
        String[] strs = mask.split("\\.");
        int count = 0;
        for (String str : strs) {
            if (str.equals("255")) {
                ++count;
            }
        }
        return count * 8;
    }

    public static String formatAddress(int n) {
        return ((n) & 0xFF) + "." + ((n >> 8) & 0xFF)  + "." + ((n >> 16) & 0xFF) + "." + ((n >> 24) & 0xFF) ;
    }
}
