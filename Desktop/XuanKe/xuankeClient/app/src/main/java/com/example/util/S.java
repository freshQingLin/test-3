package com.example.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bean.Users;
import com.google.gson.Gson;

/**
 * 缓存数据工具类
 */
public class S {
    public static Context context;

    public static void setBoolean(Context context, String type, String key,
                                  boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(type,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value).commit();

    }


    public static boolean getBoolean(Context context, String type, String key,
                                     boolean defaults) {
        SharedPreferences preferences = context.getSharedPreferences(type,
                context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaults);
    }

    public static void setString(Context context, String type, String key,
                                 String value) {
        SharedPreferences preferences = context.getSharedPreferences(type,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value).commit();

    }


    public static String getString(Context context, String type, String key,
                                   String def) {
        SharedPreferences preferences = context.getSharedPreferences(type,
                context.MODE_PRIVATE);
        return preferences.getString(key, def);

    }


    public static void saveU(Users u) {
        SharedPreferences preferences = context.getSharedPreferences("user",
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", new Gson().toJson(u)).commit();

    }

    public static Users loadU() {
        SharedPreferences preferences = context.getSharedPreferences("user",
                context.MODE_PRIVATE);
        String json = preferences.getString("user", "");
        return new Gson().fromJson(json, Users.class);

    }


}
