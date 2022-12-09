package com.android.projectorlauncher.utils;

import android.os.RemoteException;
import android.os.SystemProperties;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vendor.hisilicon.hardware.hwsysmanager.V1_0.IHwSysManager;

public class KeystoneUtils {

    public static int[] getLtXY() {
        return strToArray(SystemProperties.get("persist.sys.keystone.lt", "[0,1080]"));
    }

    public static int[] getLbXY() {
        return strToArray(SystemProperties.get("persist.sys.keystone.lb", "[0,0]"));
    }

    public static int[] getRtXY() {
        return strToArray(SystemProperties.get("persist.sys.keystone.rt", "[1920,1080]"));
    }

    public static int[] getRbXY() {
        return strToArray(SystemProperties.get("persist.sys.keystone.rb", "[1920,0]"));
    }

    public static void setLtXY(IHwSysManager sysManager, String lt) {
        try {
            sysManager.hiPropertySet("persist.hisi.keystone.enable", "true");
            sysManager.hiPropertySet("persist.hisi.keystone.lt", lt);
            sysManager.hiPropertySet("persist.hisi.keystone.enable", "true");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        SystemProperties.set("persist.sys.keystone.enable", "true");
        SystemProperties.set("persist.sys.keystone.update", "true");
        SystemProperties.set("persist.sys.keystone.lt", lt);
        SystemProperties.set("persist.sys.keystone.update", "true");
        getLtXY();
    }

    public static void setLbXY(IHwSysManager sysManager, String lb) {
        try {
            sysManager.hiPropertySet("persist.hisi.keystone.enable", "true");
            sysManager.hiPropertySet("persist.hisi.keystone.lb", lb);
            sysManager.hiPropertySet("persist.hisi.keystone.enable", "true");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        SystemProperties.set("persist.sys.keystone.enable", "true");
        SystemProperties.set("persist.sys.keystone.update", "true");
        SystemProperties.set("persist.sys.keystone.lb", lb);
        SystemProperties.set("persist.sys.keystone.update", "true");
        getLbXY();
    }

    public static void setRtXY(IHwSysManager sysManager, String rt) {
        try {
            sysManager.hiPropertySet("persist.hisi.keystone.enable", "true");
            sysManager.hiPropertySet("persist.hisi.keystone.rt", rt);
            sysManager.hiPropertySet("persist.hisi.keystone.enable", "true");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        SystemProperties.set("persist.sys.keystone.enable", "true");
        SystemProperties.set("persist.sys.keystone.update", "true");
        SystemProperties.set("persist.sys.keystone.rt", rt);
        SystemProperties.set("persist.sys.keystone.update", "true");
        getRtXY();
    }

    public static void setRbXY(IHwSysManager sysManager, String rb) {
        try {
            sysManager.hiPropertySet("persist.hisi.keystone.enable", "true");
            sysManager.hiPropertySet("persist.hisi.keystone.rb", rb);
            sysManager.hiPropertySet("persist.hisi.keystone.enable", "true");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        SystemProperties.set("persist.sys.keystone.enable", "true");
        SystemProperties.set("persist.sys.keystone.update", "true");
        SystemProperties.set("persist.sys.keystone.rb", rb);
        SystemProperties.set("persist.sys.keystone.update", "true");
        getRbXY();
    }

    public static int[] strToArray(String str) {
        int[] xy = new int[2];
        int i = 0;
        String[] splits = str.split(",");
        for (String split : splits) {
            Pattern p = Pattern.compile("[^0-9]");
            Matcher m = p.matcher(split);
            String trim = m.replaceAll("").trim();
            if (i == 2) {
                break;
            }
            xy[i] = Integer.parseInt(trim);
            i++;
        }
        return xy;
    }

    public static String getKs() {
        return "Lt = " + Arrays.toString(getLtXY()) + "\n"
                + "Lb = " + Arrays.toString(getLbXY()) + "\n"
                + "Rt = " + Arrays.toString(getRtXY()) + "\n"
                + "Rb = " + Arrays.toString(getRbXY()) + "\n";
    }

}
