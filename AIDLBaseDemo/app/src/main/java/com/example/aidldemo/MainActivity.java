package com.example.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.servicebasedemo.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            IMyAidlInterface imai=IMyAidlInterface.Stub.asInterface(iBinder);//还是把basedemo的service的iBinder传过来
            try {
                imai.showProgress();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void operate(View v){
        switch (v.getId()){
            case R.id.bt_start:
                //远程启动服务
                Intent it=new Intent();
                it.setAction("com.dongua.myservice");
                it.setPackage("com.example.servicebasedemo");
                startService(it);
                break;
            case R.id.bt_stop:
                //远程停止服务
                Intent it2=new Intent();
                it2.setAction("com.dongua.myservice");
                it2.setPackage("com.example.servicebasedemo");
                stopService(it2);
                break;
            case R.id.bt_bind:
                //远程绑定服务
                Intent it3=new Intent();
                it3.setAction("com.dongua.myservice");
                it3.setPackage("com.example.servicebasedemo");
                bindService(it3,conn,BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbind:
                //远程解绑服务
                unbindService(conn);
                break;
        }
    }
}
