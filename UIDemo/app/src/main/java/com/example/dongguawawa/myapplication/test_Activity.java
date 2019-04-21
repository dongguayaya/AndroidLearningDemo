package com.example.dongguawawa.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class test_Activity extends AppCompatActivity  {
    MyTask mTask;
    Button button,cancel; // 加载、取消按钮
    TextView text; // 更新的UI组件
    ProgressBar progressBar; // 进度条
    private class MyTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            text.setText("加载中");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                int count = 0;
                int length = 1;
                while (count<99) {

                    count += length;
                    // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                    publishProgress(count);
                    // 模拟耗时任务
                    Thread.sleep(50);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progresses) {

            progressBar.setProgress(progresses[0]);
            text.setText("loading..." + progresses[0] + "%");

        }
        @Override
        protected void onPostExecute(String result) {
            text.setText("加载完毕");
        }

        @Override
        protected void onCancelled() {

            text.setText("已取消");
            progressBar.setProgress(0);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        button = (Button) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.cancel);
        text = (TextView) findViewById(R.id.text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mTask = new MyTask();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.execute();
            }
        });
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.cancel(true);
            }
        });
    }
//    @Override
//    public void onClick(View v){
//        switch (v.getId()){
//            case R.id.bt_try:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Message msg=handler.obtainMessage();
//                        message.what=UPDATE_TEXT;
//                        handler.sendMessage(message);
//                    }
//                }).start();
//                break;
//            default:
//                break;
//        }
//    }
}
