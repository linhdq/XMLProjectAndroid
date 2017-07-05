package com.ldz.fpt.xmlprojectandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by linhdq on 7/1/17.
 */

public class PreferenceUtil {
    private static final String APP_NAME = "loto";
    private static final String REMEMBER_ME = "remember_me";
    private static final String PRICE_ON_EACH_POINT_LO = "lo_price";
    private static final String DE_PRICE = "de_price";
    private static final String BA_CANG_PRICE = "ba_cang_price";
    private static final String LO_PRICE_NHAN = "lo_price_nhan";
    private static final String LO_PRICE_TRA = "lo_price_tra";
    private static final String LO_XIEN_2_PRICE_NHAN = "lo_xien_2_price_nhan";
    private static final String LO_XIEN_2_PRICE_TRA = "lo_xien_2_price_tra";
    private static final String LO_XIEN_3_PRICE_NHAN = "lo_xien_3_price_nhan";
    private static final String LO_XIEN_3_PRICE_TRA = "lo_xien_3_price_tra";
    private static final String LO_XIEN_4_PRICE_NHAN = "lo_xien_4_price_nhan";
    private static final String LO_XIEN_4_PRICE_TRA = "lo_xien_4_price_tra";

    private static SharedPreferences sharedPreferences;

    public PreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

    private static PreferenceUtil inst;

    public static PreferenceUtil getInst(Context context) {
        if (inst == null) {
            inst = new PreferenceUtil(context);
        }
        return inst;
    }

    public void setRememberMe(boolean isRemember) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(REMEMBER_ME, isRemember);
        editor.commit();
    }

    public boolean isRememberMe() {
        return sharedPreferences.getBoolean(REMEMBER_ME, false);
    }

    public String getLastTimeUpdated(String key) {
        return sharedPreferences.getString(key, "Chưa cập nhật");
    }

    public void setLastTimeUpdated(String key, String time) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, time);
        editor.commit();
    }

    public void setDePrice(int price) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DE_PRICE, price);
        editor.commit();
    }

    public int getDePrice() {
        return sharedPreferences.getInt(DE_PRICE, 0);
    }

    public void setBaCangPrice(int price) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(BA_CANG_PRICE, price);
        editor.commit();
    }

    public int getBaCangPrice() {
        return sharedPreferences.getInt(BA_CANG_PRICE, 0);
    }

    public void setLoPriceNhan(int price) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LO_PRICE_NHAN, price);
        editor.commit();
    }

    public int getLoPriceNhan() {
        return sharedPreferences.getInt(LO_PRICE_NHAN, 0);
    }

    public void setLoPriceTra(int price) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LO_PRICE_TRA, price);
        editor.commit();
    }

    public int getLoPriceTra() {
        return sharedPreferences.getInt(LO_PRICE_TRA, 0);
    }

    public void setLoXien2PriceNhan(int price) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LO_XIEN_2_PRICE_NHAN, price);
        editor.commit();
    }

    public int getLoXien2PriceNhan() {
        return sharedPreferences.getInt(LO_XIEN_2_PRICE_NHAN, 0);
    }

    public void setLoXien2PriceTra(int price) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LO_XIEN_2_PRICE_TRA, price);
        editor.commit();
    }

    public int getLoXien2PriceTra() {
        return sharedPreferences.getInt(LO_XIEN_2_PRICE_TRA, 0);
    }

    public void setLoXien3PriceNhan(int price) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LO_XIEN_3_PRICE_NHAN, price);
        editor.commit();
    }

    public int getLoXien3PriceNhan() {
        return sharedPreferences.getInt(LO_XIEN_3_PRICE_NHAN, 0);
    }

    public void setLoXien3PriceTra(int price) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LO_XIEN_3_PRICE_TRA, price);
        editor.commit();
    }

    public int getLoXien3PriceTra() {
        return sharedPreferences.getInt(LO_XIEN_3_PRICE_TRA, 0);
    }

    public void setLoXien4PriceNhan(int price) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LO_XIEN_4_PRICE_NHAN, price);
        editor.commit();
    }

    public int getLoXien4PriceNhan() {
        return sharedPreferences.getInt(LO_XIEN_4_PRICE_NHAN, 0);
    }

    public void setLoXien4PriceTra(int price) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LO_XIEN_4_PRICE_TRA, price);
        editor.commit();
    }

    public int getLoXien4PriceTra() {
        return sharedPreferences.getInt(LO_XIEN_4_PRICE_TRA, 0);
    }
}
