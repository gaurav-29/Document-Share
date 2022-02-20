package com.oceanmtech.documentshare.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {


    public static SharedPreferences DocumentShareSharedPreference;
    private static final String APP_PREFERENCE_NAME = "DocumentShareSharedPreference";

    public static void putString(String key, String value) {
        DocumentShareSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = DocumentShareSharedPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key, String defaultValue) {
        DocumentShareSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return DocumentShareSharedPreference.getString(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        DocumentShareSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = DocumentShareSharedPreference.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(String key, int defaultValue) {
        DocumentShareSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        int string = DocumentShareSharedPreference.getInt(key, defaultValue);
        return string;
    }

    public static void putBoolean(String key, boolean value) {
        DocumentShareSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = DocumentShareSharedPreference.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        DocumentShareSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean string = DocumentShareSharedPreference.getBoolean(key, defaultValue);
        return string;
    }

    public static boolean contains(String key) {
        DocumentShareSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (DocumentShareSharedPreference.contains(key)) {
            return true;
        } else {
            return false;
        }
    }

    public static void remove(String key) {
        DocumentShareSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = DocumentShareSharedPreference.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clearPreference() {
        DocumentShareSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        DocumentShareSharedPreference.edit().clear().commit();
    }
}
