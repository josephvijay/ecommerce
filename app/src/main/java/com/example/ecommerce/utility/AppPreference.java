package com.example.ecommerce.utility;

import android.content.Context;
import android.content.SharedPreferences;


public class AppPreference {

    private SharedPreferences mSharedPreferences;

    private static final String CART_ID = "CartId";

    private static final String LOGGED = "Logged";


    public AppPreference(Context context) {
        mSharedPreferences = context.getSharedPreferences("vajro_service_preference", Context.MODE_PRIVATE);
    }


    public void setCartId(String orgId) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(CART_ID, orgId);
        edit.commit();
    }

    public String getCartId() {
        return mSharedPreferences.getString(CART_ID, "0");
    }


    public String getLogged() {
        return mSharedPreferences.getString(LOGGED, "");
    }

    public void setLogged(String log) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(LOGGED, log);
        edit.commit();
    }
}
