package com.example.servicebasedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //IBinder,接口，android进行远程操作的基本接口
    //ServiceConnection
    //进度监控
    private ServiceConnection conn=new ServiceConnection() {
        //当客户端正常连接着服务时，执行服务的绑定操作会被调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.e("TAG", "连接了" );
            MyService.MyBinder mb=(MyService.MyBinder)iBinder;
            int step=mb.getProcess();
            Log.e("TAG", "当前进度是: "+step );
        }
        //当客户端与服务端丢失
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void operate(View v) {
        switch (v.getId()){
            case R.id.bt_start:
                //启动服务
                Intent it1=new Intent(this,MyService.class);
                startService(it1);
                break;
            case R.id.bt_stop:
                Intent it2=new Intent(this,MyService.class);
                stopService(it2);
                break;
            case R.id.bt_bind:
                //绑定服务
                Intent it3=new Intent(this,MyService.class);
                bindService(it3,conn ,BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbind:
                //解绑服务
                unbindService(conn);
                break;
        }
    }
}
