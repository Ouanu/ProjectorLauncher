package com.android.projectorlauncher.utils;

import android.content.Context;
import android.util.Log;

import com.android.internal.app.LocalePicker;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class LanguageUtils {

    public static String displayLanguage = "";

    public static LinkedHashMap<String, LocalePicker.LocaleInfo> getLocalMap(Context context, boolean isInDevelopMode) {
        List<LocalePicker.LocaleInfo> allAssetLocales = LocalePicker.getAllAssetLocales(context, isInDevelopMode);
        LocalePicker.LocaleInfo localeInfo1 = allAssetLocales.get(0);
        allAssetLocales.remove(0);
        allAssetLocales.add(localeInfo1);
        LinkedHashMap<String, LocalePicker.LocaleInfo> mLocaleInfoMap = new LinkedHashMap<>();
        for (LocalePicker.LocaleInfo localeInfo : allAssetLocales) {
            final String languageTag = localeInfo.getLabel();
            mLocaleInfoMap.put(languageTag, localeInfo);
            if (Locale.getDefault().toLanguageTag().equals(localeInfo.getLocale().toLanguageTag())) {
                displayLanguage = languageTag;
            }
        }
        return mLocaleInfoMap;
    }

    public static void setLanguage(LocalePicker.LocaleInfo localeInfo) {
        LocalePicker.updateLocale(localeInfo.getLocale());
    }

    public static String getLanguage() {
        return Locale.getDefault().getDisplayLanguage();
    }

}
