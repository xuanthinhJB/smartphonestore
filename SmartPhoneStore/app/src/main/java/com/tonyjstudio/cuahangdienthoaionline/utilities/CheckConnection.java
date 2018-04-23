package com.tonyjstudio.cuahangdienthoaionline.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnection {

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo networkInfo: netInfo) {
            if (networkInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                if (networkInfo.isConnected()) {
                    haveConnectedWifi = true;
                }
            }

            if (networkInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (networkInfo.isConnected()) {
                    haveConnectedMobile = true;
                }
            }
        }
        return haveConnectedMobile || haveConnectedWifi;
    }

    public static void showToast_Short (Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
