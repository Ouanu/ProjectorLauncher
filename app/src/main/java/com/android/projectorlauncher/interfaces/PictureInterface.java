/*
 * Copyright (c) Hisilicon Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.android.projectorlauncher.interfaces;

import com.android.projectorlauncher.utils.LogHelper;
import com.hisilicon.android.tvapi.HitvManager;
import com.hisilicon.android.tvapi.Picture;
import com.hisilicon.android.tvapi.vo.ColorTempInfo;

import java.util.ArrayList;

/**
 * interface of Picture
 *
 * @author hisilicon
 * @since 2019 -07-01
 */
@SuppressWarnings("unused")
public class PictureInterface {
    private static final String TAG = "PictureInterface";

    /**
     * get instance of Picture
     *
     * @return an instance of Picture(from MW)
     */
    private static Picture getPictureManager() {
        return HitvManager.getInstance().getPicture();
    }

    /**
     * enableBacklight
     *
     * @param onOff the on off
     * @return the int
     */
    public static int enableBacklight(boolean onOff) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableBacklight(boolean onOff = " + onOff + ") begin");

        int value = getPictureManager().enableBacklight(onOff);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableBacklight(boolean onOff = " + onOff
            + ")   end value = " + value);
        return value;
    }

    /**
     * enableBlueExtend
     *
     * @param onOff the on off
     * @return the int
     */
    public static int enableBlueExtend(boolean onOff) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableBlueExtend(boolean onOff = " + onOff + ") begin");

        int value = getPictureManager().enableBlueExtend(onOff);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableBlueExtend(boolean onOff = " + onOff
            + ")  end value = " + value);
        return value;
    }

    /**
     * enableDCI
     *
     * @param onOff the on off
     * @return the int
     */
    public static int enableDCI(boolean onOff) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableDCI(boolean onOff = " + onOff + ") begin");

        int value = getPictureManager().enableDCI(onOff);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableDCI(boolean onOff = " + onOff + ")  end value = "
            + value);
        return value;
    }

    /**
     * enableDynamicBL
     *
     * @param onOff the on off
     * @return the int
     */
    public static int enableDynamicBL(boolean onOff) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableDynamicBL(boolean onOff = " + onOff + ") begin");

        int value = getPictureManager().enableDynamicBL(onOff);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableDynamicBL(boolean onOff = " + onOff
            + ")  end value = " + value);
        return value;
    }

    /**
     * enableFreeze
     *
     * @param onOff the on off
     * @return the int
     */
    public static int enableFreeze(boolean onOff) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableFreeze(boolean onOff = " + onOff + ") begin");

        int value = getPictureManager().enableFreeze(onOff);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableFreeze(boolean onOff = " + onOff
            + ")  end value = " + value);
        return value;
    }

    /**
     * enableGameMode
     *
     * @param bEnable the b enable
     * @return the int
     */
    public static int enableGameMode(boolean bEnable) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableGameMode(boolean bEnable = " + bEnable
            + ") begin");

        int value = getPictureManager().enableGameMode(bEnable);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableGameMode(boolean bEnable = " + bEnable
            + ")  end value = " + value);
        return value;
    }

    /**
     * enableOverscan
     *
     * @param bEnable the b enable
     * @return the int
     */
    public static int enableOverscan(boolean bEnable) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableOverscan(boolean bEnable = " + bEnable
            + ") begin");

        int value = getPictureManager().enableOverscan(bEnable);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "enableOverscan(boolean bEnable = " + bEnable
            + ")  end value = " + value);
        return value;
    }

    /**
     * getAspect
     *
     * @return the aspect
     */
    public static int getAspect() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getAspect() begin");

        int value = getPictureManager().getAspect();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getAspect()  end value = " + value);
        return value;
    }

    /**
     * Timming getAvailAspectList
     *
     * @return the avail aspect list
     */
    public static ArrayList<Integer> getAvailAspectList() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getAvailAspectList() begin");

        ArrayList<Integer> value = getPictureManager().getAvailAspectList();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getAvailAspectList()  end value = " + value);
        return value;
    }

    /**
     * getBacklight
     *
     * @return the backlight
     */
    public static int getBacklight() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getBacklight() begin");

        int value = getPictureManager().getBacklight();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getBacklight()  end value = " + value);
        return value;
    }

    /**
     * getBrightness
     *
     * @return the brightness
     */
    public static int getBrightness() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getBrightness() begin");

        int value = getPictureManager().getBrightness();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getBrightness()  end value = " + value);
        return value;
    }

    /**
     * getColorTemp
     *
     * @return the color temp
     */
    public static int getColorTemp() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getColorTemp() begin");

        int value = getPictureManager().getColorTemp();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getColorTemp()  end value = " + value);
        return value;
    }

    /**
     * get ColorTempInfo
     *
     * @return the color temp para
     */
    public static ColorTempInfo getColorTempPara() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getColorTempPara() begin");

        ColorTempInfo value = getPictureManager().getColorTempPara();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getColorTempPara()  end value = " + value);
        return value;
    }

    /**
     * getContrast
     *
     * @return the contrast
     */
    public static int getContrast() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getContrast() begin");

        int value = getPictureManager().getContrast();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getContrast()  end value = " + value);
        return value;
    }

    /**
     * getDeBlocking
     *
     * @return the de blocking
     */
    public static int getDeBlocking() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getDeBlocking() begin");

        int value = getPictureManager().getDeBlocking();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getDeBlocking()  end value = " + value);
        return value;
    }

    /**
     * getDemoMode
     *
     * @param mode the mode
     * @return the demo mode
     */
    public static boolean getDemoMode(int mode) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getDemoMode() begin");

        boolean value = getPictureManager().getDemoMode(mode);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getDemoMode()  end value = " + value);
        return value;
    }

    /**
     * getDeRinging
     *
     * @return the de ringing
     */
    public static int getDeRinging() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getDeRinging() begin");

        int value = getPictureManager().getDeRinging();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getDeRinging()  end value = " + value);
        return value;
    }

    /**
     * getFilmMode
     *
     * @return the film mode
     */
    public static int getFilmMode() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getFilmMode() begin");

        int value = getPictureManager().getFilmMode();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getFilmMode()  end value = " + value);
        return value;
    }

    /**
     * getFleshTone
     *
     * @return the flesh tone
     */
    public static int getFleshTone() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getFleshTone() begin");

        int value = getPictureManager().getFleshTone();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getFleshTone()  end value = " + value);
        return value;
    }

    /**
     * getHDMIColorRange
     *
     * @return the hdmi color range
     */
    public static int getHDMIColorRange() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getHDMIColorRange() begin");

        int value = getPictureManager().getHDMIColorRange();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getHDMIColorRange()  end value = " + value);
        return value;
    }

    /**
     * getHue
     *
     * @return the hue
     */
    public static int getHue() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getHue() begin");

        int value = getPictureManager().getHue();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getHue()  end value = " + value);
        return value;
    }

    /**
     * getMEMCLevel
     *
     * @return the memc level
     */
    public static int getMEMCLevel() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getMEMCLevel() begin");

        int value = getPictureManager().getMEMCLevel();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getMEMCLevel()  end value = " + value);
        return value;
    }

    /**
     * getNR
     *
     * @return the nr
     */
    public static int getNR() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getNR() begin");

        int value = getPictureManager().getNR();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getNR()  end value = " + value);
        return value;
    }

    /**
     * getPictureMode
     *
     * @return the picture mode
     */
    public static int getPictureMode() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getPictureMode() begin");

        int value = getPictureManager().getPictureMode();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getPictureMode()  end value = " + value);
        return value;
    }

    /**
     * getSaturation
     *
     * @return the saturation
     */
    public static int getSaturation() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getSaturation() begin");

        int value = getPictureManager().getSaturation();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getSaturation()  end value = " + value);
        return value;
    }

    /**
     * getSharpness
     *
     * @return the sharpness
     */
    public static int getSharpness() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getSharpness() begin");

        int value = getPictureManager().getSharpness();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getSharpness()  end value = " + value);
        return value;
    }

    /**
     * Sets color system.
     *
     * @param colorSys the color sys
     * @return the color system
     */
    public static int setColorSystem(int colorSys) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "set ColorSystem value = " + colorSys);

        int res = getPictureManager().setColorSystem(colorSys);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setColorSystem return res = " + res);
        return res;
    }

    /**
     * Gets color system.
     *
     * @return the color system
     */
    public static int getColorSystem() {
        int value = getPictureManager().getRealColorSystem();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getColorSystem value = " + value);

        return value;
    }

    /**
     * isBacklightEnable
     *
     * @return the boolean
     */
    public static boolean isBacklightEnable() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isBacklightEnable() begin");

        boolean value = getPictureManager().isBacklightEnable();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isBacklightEnable()  end value = " + value);
        return value;
    }

    /**
     * isBlueExtendEnable
     *
     * @return the boolean
     */
    public static boolean isBlueExtendEnable() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isBlueExtendEnable() begin");

        boolean value = getPictureManager().isBlueExtendEnable();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isBlueExtendEnable()  end value = " + value);
        return value;
    }

    /**
     * isDCIEnable
     *
     * @return the boolean
     */
    public static boolean isDCIEnable() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isDCIEnable() begin");

        boolean value = getPictureManager().isDCIEnable();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isDCIEnable()  end value = " + value);
        return value;
    }

    /**
     * isDynamicBLEnable
     *
     * @return the boolean
     */
    public static boolean isDynamicBLEnable() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isDynamicBLEnable() begin");

        boolean value = getPictureManager().isDynamicBLEnable();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isDynamicBLEnable()  end value = " + value);
        return value;
    }

    /**
     * isFreezeEnable
     *
     * @return the boolean
     */
    public static boolean isFreezeEnable() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isFreezeEnable() begin");

        boolean value = getPictureManager().isFreezeEnable();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isFreezeEnable()  end value = " + value);
        return value;
    }

    /**
     * isGameModeEnable
     *
     * @return the boolean
     */
    public static boolean isGameModeEnable() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isGameModeEnable() begin");

        boolean value = getPictureManager().isGameModeEnable();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isGameModeEnable()  end value = " + value);
        return value;
    }

    /**
     * isOverscanEnable
     *
     * @return the boolean
     */
    public static boolean isOverscanEnable() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isOverscanEnable() begin");

        boolean value = getPictureManager().isOverscanEnable();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isOverscanEnable()  end value = " + value);
        return value;
    }

    /**
     * setAspect
     *
     * @param aspect the aspect
     * @param mute the mute
     * @return the aspect
     */
    public static int setAspect(int aspect, boolean mute) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setAspect(int aspect = " + aspect + ", boolean mute ="
            + mute + ") begin");

        int value = getPictureManager().setAspect(aspect, mute);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setAspect(int aspect =" + aspect + ", boolean mute ="
            + mute + ")  end value = " + value);
        return value;
    }

    /**
     * setBacklight
     *
     * @param backlight the backlight
     * @return the backlight
     */
    public static int setBacklight(int backlight) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setBacklight(int backlight = " + backlight + ") begin");

        int value = getPictureManager().setBacklight(backlight);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setBacklight(int backlight = " + backlight
            + ")  end value = " + value);
        return value;
    }

    /**
     * Sets all set to user.
     *
     * @param userMode the user mode
     * @param isHueEnable the is hue enable
     * @return the all set to user
     */
    public static int setAllSetToUser(int userMode, boolean isHueEnable) {
        setPictureMode(userMode);
        setBrightness(getBrightness());
        setContrast(getContrast());
        setSaturation(getSaturation());
        if (isHueEnable) {
            setHue(getHue());
        }
        setSharpness(getSharpness());
        return 0;
    }

    /**
     * setBrightness
     *
     * @param brightness the brightness
     * @return the brightness
     */
    public static int setBrightness(int brightness) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setBrightness(int brightness = " + brightness
            + ") begin");

        int value = getPictureManager().setBrightness(brightness);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setBrightness(int brightness = " + brightness
            + ")  end value = " + value);
        return value;
    }

    /**
     * setColorTemp
     *
     * @param colorTemp the color temp
     * @return color temp
     */
    public static int setColorTemp(int colorTemp) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setColorTemp(int colorTemp = " + colorTemp + ") begin");

        int value = getPictureManager().setColorTemp(colorTemp);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setColorTemp(int colorTemp = " + colorTemp
            + ") end value = " + value);
        return value;
    }

    /**
     * setColorTempPara range 0-100
     *
     * @param stColorTemp the st color temp
     * @return the color temp para
     */
    public static int setColorTempPara(ColorTempInfo stColorTemp) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setColorTempPara(ColorTempInfo stColorTemp = "
            + stColorTemp + ") begin");

        int value = getPictureManager().setColorTempPara(stColorTemp);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setColorTempPara(ColorTempInfo stColorTemp = "
            + stColorTemp + ") end value = " + value);
        return value;
    }

    /**
     * setContrast
     *
     * @param contrast the contrast
     * @return the contrast
     */
    public static int setContrast(int contrast) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setContrast(int contrast = " + contrast + ") begin");

        int value = getPictureManager().setContrast(contrast);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setContrast(int contrast=" + contrast
            + ") end value = " + value);
        return value;
    }

    /**
     * setDeBlocking
     *
     * @param dbLevel the db level
     * @return the de blocking
     */
    public static int setDeBlocking(int dbLevel) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setDeBlocking(int dbLevel = " + dbLevel + ") begin");

        int value = getPictureManager().setDeBlocking(dbLevel);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setDeBlocking(int dbLevel = " + dbLevel
            + ") end value = " + value);
        return value;
    }

    /**
     * setDemoMode
     *
     * @param demoMode the demo mode
     * @param onOff the on off
     * @return the demo mode
     */
    public static int setDemoMode(int demoMode, boolean onOff) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setDemoMode(int demoMode = " + demoMode + " onOff = "
            + onOff + ")begin");

        int value = getPictureManager().setDemoMode(demoMode, onOff);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setDemoMode(int demoMode = " + demoMode
            + ") end  onOff= " + onOff + " + value=" + value);
        return value;
    }

    /**
     * setDeRinging
     *
     * @param DRLevel the dr level
     * @return the de ringing
     */
    public static int setDeRinging(int DRLevel) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setDeRinging(int DRLevel =" + DRLevel + ") begin");

        int value = getPictureManager().setDeRinging(DRLevel);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setDeRinging(int DRLevel =" + DRLevel
            + ") end value = " + value);
        return value;
    }

    /**
     * setFilmMode
     *
     * @param filmMode the film mode
     * @return the film mode
     */
    public static int setFilmMode(int filmMode) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setFilmMode(int filmMode = " + filmMode + ") begin");

        int value = getPictureManager().setFilmMode(filmMode);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setFilmMode(int filmMode = " + filmMode
            + ") end value = " + value);
        return value;
    }

    /**
     * setFleshTone
     *
     * @param fleshTone the flesh tone
     * @return the flesh tone
     */
    public static int setFleshTone(int fleshTone) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setFleshTone(int fleshTone = " + fleshTone + ") begin");

        int value = getPictureManager().setFleshTone(fleshTone);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setFleshTone(int fleshTone = " + fleshTone
            + ") end value = " + value);
        return value;
    }

    /**
     * setHDMIColorRange
     *
     * @param val the val
     * @return the hdmi color range
     */
    public static int setHDMIColorRange(int val) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setHDMIColorRange(int val = " + val + ") begin");

        int value = getPictureManager().setHDMIColorRange(val);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setHDMIColorRange(int val = " + val + ") end value = "
            + value);
        return value;
    }

    /**
     * setHue
     *
     * @param hue the hue
     * @return the hue
     */
    public static int setHue(int hue) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setHue(int hue = " + hue + " begin");

        int value = getPictureManager().setHue(hue);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setHue(int hue = " + hue + ") end value = " + value);
        return value;
    }

    /**
     * setMEMCLevel
     *
     * @param MEMCLevel the memc level
     * @return the memc level
     */
    public static int setMEMCLevel(int MEMCLevel) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setMEMCLevel(int MEMCLevel = " + MEMCLevel + ") begin");

        int value = getPictureManager().setMEMCLevel(MEMCLevel);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setMEMCLevel(int MEMCLevel = " + MEMCLevel
            + ") end value = " + value);
        return value;
    }

    /**
     * setNR
     *
     * @param NR the nr
     * @return the nr
     */
    public static int setNR(int NR) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setNR(int NR = " + NR + ") begin");

        int value = getPictureManager().setNR(NR);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setNR(int NR = " + NR + ") end value = " + value);
        return value;
    }

    /**
     * setPictureMode
     *
     * @param pictureMode the picture mode
     * @return the picture mode
     */
    public static int setPictureMode(int pictureMode) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setPictureMode(int pictureMode = " + pictureMode
            + ") begin");
        int value = getPictureManager().setPictureMode(pictureMode);
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setPictureMode(int pictureMode = " + pictureMode
            + ") end value = " + value);
        return value;
    }

    /**
     * Sets saturation.
     *
     * @param saturation the saturation
     * @return the saturation
     */
    public static int setSaturation(int saturation) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setSaturation(int saturation = " + saturation
            + ") begin");

        int value = getPictureManager().setSaturation(saturation);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setSaturation(int saturation = " + saturation
            + ") end value = " + value);
        return value;
    }

    /**
     * setSharpness
     *
     * @param sharpness the sharpness
     * @return the sharpness
     */
    public static int setSharpness(int sharpness) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setSharpness(int sharpness = " + sharpness + ") begin");

        int value = getPictureManager().setSharpness(sharpness);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setSharpness(int sharpness = " + sharpness
            + " end value = " + value);
        return value;
    }

    /**
     * setSRLevel
     *
     * @param SRLevel the sr level
     * @return sr level
     */
    public static int setSRLevel(int SRLevel) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "srlevel(int SRLevel = " + SRLevel + ") begin");

        int value = getPictureManager().setSRLevel(SRLevel);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "srlevel(int SRLevel = " + SRLevel + ") end");
        return value;
    }

    /**
     * getSRLevel
     *
     * @return sr level
     */
    public static int getSRLevel() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getSRLevel() begin");

        int value = getPictureManager().getSRLevel();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getSRLevel()  end value = " + value);
        return value;
    }

    /**
     * setGraphicsMode
     *
     * @param enable the b
     * @return graphics mode
     */
    public static int setGraphicsMode(boolean enable) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setGraphicsMode() begin");
        int value = getPictureManager().setGraphicsMode(enable);
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setGraphicsMode()  end value = " + value);
        return value;
    }

    /**
     * setGraphicsMode
     *
     * @return boolean
     */
    public static boolean isGraphicsMode() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isGraphicsMode() begin");

        boolean value = getPictureManager().isGraphicsMode();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "isGraphicsMode()  end value = " + value);
        return value;
    }

    /**
     * 比例模式为“自动”是打开AFD，其他模式关闭AFD
     *
     * @param flag the flag
     * @return int
     */
    public static int enableAFD(boolean flag) {
        return 0;
    }

    /**
     * setHdrEnable
     *
     * @param bEnable the b enable
     * @return hdr enable
     */
    public static int setHdrEnable(boolean bEnable) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setHdrEnable() begin");

        int value = getPictureManager().setHdrEnable(bEnable);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setHdrEnable()  end value = " + value);
        return value;
    }

    /**
     * getHdrEnable
     *
     * @return hdr enable
     */
    public static boolean getHdrEnable() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getHdrEnable() begin");

        boolean value = getPictureManager().getHdrEnable();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getHdrEnable()  end value = " + value);
        return value;
    }

    /**
     * getHdrType *
     *
     * @return hdr type
     */
    public static int getHdrType() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getHdrType() begin");

        int value = getPictureManager().getHdrType();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getHdrType)  end value = " + value);
        return value;
    }

    /**
     * getPcMode *
     *
     * @return 0:success -1:fail
     */
    public static int getPcMode() {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getPcMode() begin");

        int value = getPictureManager().getPcMode();

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "getPcMode()  end value = " + value);
        return value;
    }

    /**
     * getPcMode *
     *
     * @return PC mode
     */
    public static int setPcMode(int pcMode) {
        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setPcMode() begin");

        int value = getPictureManager().setPcMode(pcMode);

        LogHelper.debug(LogHelper.TAG_SETTING, TAG, "setPcMode()  end value = " + value);
        return value;
    }
}
