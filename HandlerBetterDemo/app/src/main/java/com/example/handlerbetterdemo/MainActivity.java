package com.example.handlerbetterdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    /**
     * 倒计时标记
     */
    public static final int COUNTDOWN_TIME_CODE = 100001;
    public static final int DELAY_MILLIS = 1000;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //控件初始化
        textView = findViewById(R.id.tv_test);

        //创建了一个handler
        CountdownTimeHandler handler=new CountdownTimeHandler(this);
//        {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if(msg!=null&&msg.what==COUNTDOWN_TIME_CODE){
//                    textView.setText(String.valueOf(9));
//                }
//            }
//        }
        //新建一个message
        Message message=Message.obtain();
        message.what= COUNTDOWN_TIME_CODE;
        message.arg1=10;

        //第一次发送这个message
        handler.sendMessageDelayed(message, DELAY_MILLIS);

    }

    //弱引用
    public static class CountdownTimeHandler extends Handler{
        //在构造器里进行初始化
        final WeakReference<MainActivity> mainActivityWeakReference;

         CountdownTimeHandler(MainActivity activity) {
            mainActivityWeakReference =new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity=mainActivityWeakReference.get();

            switch (msg.what){
                case COUNTDOWN_TIME_CODE:
                    int value=msg.arg1;
                    activity.textView.setText(String.valueOf(value--));


                    //循发消息的控制
                    if (value>=0){
                        Message message=Message.obtain();
                        message.what=COUNTDOWN_TIME_CODE;
                        message.arg1=value;
                        sendMessageDelayed(message,DELAY_MILLIS);
                    }

                    break;
            }
        }
    }
}
