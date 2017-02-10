package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    TextView tv_send;
    private static final String TAG = "MainActivity";
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    //本地广播
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        intentFilter=new IntentFilter();
        //对网络变化进行监听
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        networkChangeReceiver=new NetworkChangeReceiver();
        //注册 broadcast
        registerReceiver(networkChangeReceiver,intentFilter);

        //
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        localReceiver=new LocalReceiver();
        IntentFilter intentFilter1=new IntentFilter();
        intentFilter1.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        localBroadcastManager.registerReceiver(localReceiver,intentFilter1);
    }

    private void init() {
        tv_send= (TextView) findViewById(R.id.tv_send);
        tv_send.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销 broadcast
        Log.d(TAG,"on Destroy");
        unregisterReceiver(networkChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_send:
                Intent intent=new Intent("com.example.broadcasttest.FORCE_OFFLINE");
                //发送普通广播
                sendBroadcast(intent);
                //发送有序广播
//                sendOrderedBroadcast(intent,null);

                //发送本地广播
                Intent intent1=new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent1);
                break;
        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager= (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isAvailable()){
                Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();

            }
        }
    }
    //
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"local receiver",Toast.LENGTH_SHORT).show();

        }
    }
}
