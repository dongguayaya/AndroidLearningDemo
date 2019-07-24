package com.example.handlerbetterdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

public class DiglettActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private TextView mResult;
    private Button mClickGame;
    private ImageView mPicMouse;

    private DiglettHandler mHandler=new DiglettHandler(this);

    //设置地鼠随机坐标
    public int [][] mPosition=new int[][]{
            {342,180},{432,880},
            {521,256},{429,780},
            {123,321},{452,254},
            {269,629},{564,557},
    };

    private int mTotalCount;
    private int mSuccess;

    public static final int MAX_COUNT=10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diglett);

        initView();

        setTitle("打地鼠");
    }

    private void initView() {
        mResult = findViewById(R.id.tv_show);
        mClickGame = findViewById(R.id.btn_click);
        mPicMouse = findViewById(R.id.iv_mouse);

        mClickGame.setOnClickListener(this);
        mPicMouse.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_click:
                start();
                break;
        }
    }

    private void start() {
        //发送消息 handler.sendmessagedelayer
        mResult.setText("开始了~");
        mClickGame.setText("游戏中...");
        mClickGame.setEnabled(false);
        next(0);
    }

    private void next(int delayTime){
        int position=new Random().nextInt(mPosition.length);

        Message message=Message.obtain();
        message.what= 123;
        message.arg1=position;

        mHandler.sendMessageDelayed(message,delayTime);
        mTotalCount++;
    }

    //点击时间进行分数增加
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.setVisibility(View.GONE);
        mSuccess++;
        mResult.setText("打到了"+mSuccess+"只，共"+MAX_COUNT+"只");
        return false;
    }

    //用activity的弱引用解决内存泄漏问题
    public static class DiglettHandler extends Handler{

        public final WeakReference<DiglettActivity> mWeakReference;

        public DiglettHandler(DiglettActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            DiglettActivity activity=mWeakReference.get();
            switch (msg.what){
                case 123:

                    if(activity.mTotalCount>MAX_COUNT){
                        activity.clear();
                        Toast.makeText(activity,"地鼠打完了",Toast.LENGTH_LONG).show();
                        return;
                    }
                    //将图片的显示和坐标设置好
                    int position=msg.arg1;
                    activity.mPicMouse.setX(activity.mPosition[position][0]);
                    activity.mPicMouse.setY(activity.mPosition[position][1]);
                    activity.mPicMouse.setVisibility(View.VISIBLE);

                    //new Randow().nextInt(500)表示生成0~500之间的随机整数，包括0，不包括500，生产随机坐标
                    int randomTime=new Random().nextInt(500)+ 500;

                    activity.next(randomTime);
                    break;
            }
        }
    }

    //打完10只地鼠之后重置数据
    private void clear() {
        mTotalCount=0;
        mSuccess=0;
        mPicMouse.setVisibility(View.GONE);
        mClickGame.setText("点击开始");
        mClickGame.setEnabled(true);
    }
}
