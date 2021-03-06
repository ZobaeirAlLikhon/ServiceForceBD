package com.sfbd.serviceforcebd.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

public class ConnectionManager {
    public static boolean connection(Context context)
    {

        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info=connectivityManager.getActiveNetworkInfo();
        if(connectivityManager!=null)
        {
            NetworkInfo[] info=connectivityManager.getAllNetworkInfo();
            if(info!=null)
            {
                for (int i = 0; i<info.length; i++)
                {
                    if(info[i].getState()==NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

                }
            }

        }
        return false;
    }
}
