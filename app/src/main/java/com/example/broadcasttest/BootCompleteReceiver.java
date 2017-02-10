package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 坏蛋 on 2017/2/10.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"First receiver",Toast.LENGTH_SHORT).show();
        //abort 中断传递
        abortBroadcast();
        Log.d(TAG,"boot complete");
    }
}
