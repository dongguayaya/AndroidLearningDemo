package com.example.servicebasedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    private  int i;
    //创建
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "服务创建了" );
        //开启一个线程(从1数到100)，用于模拟耗时的任务
        new Thread(){
            @Override
            public void run() {
                super.run();
                for(i=1;i<=100;i++){
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //启动
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TAG", "服务启动了" );
        return super.onStartCommand(intent, flags, startId);
    }

    //绑定
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e("TAG", "服务绑定了" );
        //实现IBinder
        return new MyBinder();
    }
    //对于onBind方法而言，要求返回IBinder对象
    //实际上，我们会自己定义一个内部类，继承Binder类

    class MyBinder extends Binder{
        //定义自己需要的方法(实现进度监控)
        public int getProcess(){
        return i;
        }
    }
    //解绑
    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("TAG", "服务解绑了" );
        return super.onUnbind(intent);
    }

    //摧毁
    @Override
    public void onDestroy() {
        Log.e("TAG", "服务销毁了" );
        super.onDestroy();
    }
}
