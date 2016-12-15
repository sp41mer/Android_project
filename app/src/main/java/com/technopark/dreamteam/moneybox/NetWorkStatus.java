package com.technopark.dreamteam.moneybox;

/**
 * Created by sp41mer on 14.12.16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class NetWorkStatus extends BroadcastReceiver {

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;



    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (callback != null)
            callback.callBackToActivity(getCurrentNetwork(context));
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    private NetWorkCallback callback;

    public void registerCallback(NetWorkCallback callback) {
        this.callback = callback;
    }

    public boolean getCurrentNetwork(Context context){
        int status = getConnectivityStatus(context);
        boolean network = false;
        switch (status) {
            case TYPE_WIFI:
            case TYPE_MOBILE:
                network = true;
                break;
            case TYPE_NOT_CONNECTED:
                network = false;
                break;
        }
        return network;
    }

}