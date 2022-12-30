package com.android.projectorlauncher.utils;

import android.content.Context;

import com.android.internal.app.LocalePicker;

import java.util.LinkedHashMap;
import java.util.List;

public class LanguageUtils {

    public static LinkedHashMap<String, LocalePicker.LocaleInfo> getLocalMap(Context context, boolean isInDevelopMode) {
        List<LocalePicker.LocaleInfo> allAssetLocales = LocalePicker.getAllAssetLocales(context, isInDevelopMode);
        LinkedHashMap<String, LocalePicker.LocaleInfo> mLocaleInfoMap = new LinkedHashMap<>();
        for (LocalePicker.LocaleInfo localeInfo : allAssetLocales) {
            final String languageTag = localeInfo.getLocale().toLanguageTag();
            mLocaleInfoMap.put(languageTag, localeInfo);
        }
        return mLocaleInfoMap;
    }

    public static void setLanguage(LocalePicker.LocaleInfo localeInfo) {
        LocalePicker.updateLocale(localeInfo.getLocale());
    }
}
