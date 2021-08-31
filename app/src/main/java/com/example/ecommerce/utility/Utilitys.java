package com.example.ecommerce.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.EditText;

public class Utilitys {

    Context m_cContext;

    public Utilitys(Context pContext){

        m_cContext = pContext;
    }

    public static boolean isEmpty(EditText text) {
        CharSequence string = text.getText().toString();
        return (TextUtils.isEmpty(string));
    }


    public boolean isNetAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) m_cContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            connectivity=null;
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
