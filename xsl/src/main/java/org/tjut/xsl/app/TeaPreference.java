package org.tjut.xsl.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Create by mejietao on 2019/4/18
 */
public class TeaPreference {

    /**
     * 提示:
     * Activity.getPreferences(int mdoe)生成 Activity名.xml 用于Activity内部存储
     * PreferenceManager.getDefoultSharedPreferences(context)生成 包名_preference.xml
     * Context.getSharedpreferences(String name,int mode)生成name.xml
     */
    private static final SharedPreferences PREFERENCES =
            PreferenceManager.getDefaultSharedPreferences(Tea.getAppContext());

    private static final SharedPreferences CONTEXT_PREFERENCES =
            Tea.getAppContext().getSharedPreferences("app_xsl", Context.MODE_PRIVATE);

    private static final String APP_PREFERENCE_KEY = "profile";

    private static SharedPreferences getAppPreference() {
        return PREFERENCES;
    }

    private static SharedPreferences getContextPreference() {
        return CONTEXT_PREFERENCES;
    }

    public static void setAppProfile(String val) {
        getAppPreference()
                .edit()
                .putString(APP_PREFERENCE_KEY, val)
                .apply();
    }

    public static String getAppProfile() {
        return getAppPreference().getString(APP_PREFERENCE_KEY, null);
    }

    public static JSONObject getAppProfileJson() {
        final String profile = getAppProfile();
        return JSON.parseObject(profile);
    }

    public static void removeAppProfile() {
        getAppPreference()
                .edit()
                .remove(APP_PREFERENCE_KEY)
                .apply();
    }

    public static void clearAppPreferences() {
        getAppPreference()
                .edit()
                .clear()
                .apply();
    }

    public static void setAppFlag(String key, boolean flag) {
        getAppPreference()
                .edit()
                .putBoolean(key, flag)
                .apply();
    }

    public static boolean getAppFlag(String key) {
        return getAppPreference()
                .getBoolean(key, false);
    }

    public static void setContextProfile(String key, String value) {
        getContextPreference()
                .edit()
                .putString(key, value)
                .apply();
    }

    public static String getContextProfile(String key) {
        return getContextPreference().getString(key, "Empty");
    }

    public static Long getContextLong(String key) {
        return getContextPreference().getLong(key, 0L);
    }

    public static void setContextLong(String key, Long l) {
        getContextPreference()
                .edit()
                .putLong(key, l)
                .apply();
    }

    public static Integer getContextInteger(String key) {
        return getContextPreference().getInt(key, 0);
    }

    public static void setContextInteger(String key, Integer i) {
        getContextPreference()
                .edit()
                .putInt(key, i)
                .apply();
    }

    public static void clearContextPreferences() {
        getContextPreference()
                .edit()
                .clear()
                .apply();
    }
}