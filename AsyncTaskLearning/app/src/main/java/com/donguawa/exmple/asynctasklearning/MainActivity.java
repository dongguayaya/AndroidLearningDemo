package com.donguawa.exmple.asynctasklearning;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 1.网络上请求数据:申请网络权限 读写存储权限
 * 2.布局我们的layout
 * 3.下载之前我们要做什么？  UI
 * 4.下载中我们要做什么？   数据
 * 5.下载后我们要做什么？   UI
 *
 */
public class MainActivity extends AppCompatActivity {


    private Button mButton;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private static final String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //初始化视图
        initView();

        //设置点击监听
        setLinstener();

        //初始化UI数据
        setData();

//        DownloadHelper.download("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk", "", new DownloadHelper.OnDownloadListener.SimpleDownloadListener() {
//            @Override
//            public void onSuccess(int code, File file) {
//
//            }
//
//            @Override
//            public void onFail(int code, File file, String message) {
//
//            }
//            @Override
//            public void onStart(){
//                super.onStart();
//            }
//            @Override
//            public void onProgress(int progress){
//                super.onProgress(progress);
//            }
//
//        });
    }

    private void setData() {
        mProgressBar.setProgress(0);
        mButton.setText("点击下载");
        mTextView.setText("准备下载");
    }

    private void setLinstener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO:下载任务
                DownloadAsyncTask asyncTask=new DownloadAsyncTask();
                asyncTask.execute("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
            }
        });
    }

    private void initView() {

        mButton=findViewById(R.id.button);
        mProgressBar=findViewById(R.id.progressBar);
        mTextView=findViewById(R.id.textView);
    }

    /**
     * String 入参
     * Integer 进度
     * Boolean 返回值
     */
    public class DownloadAsyncTask extends AsyncTask<String,Integer,Boolean>{
        String mFilePath;
        /**
         * 在异步任务之前，在主线程中
         */
        //执行前

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //可操作UI
            mButton.setText("下载中...");
            mTextView.setText("下载中....");
            mProgressBar.setProgress(0);
        }

        /**
         * 在另外一个线程中处理事件
         * @param params 入参
         * @return结果
         */
        //执行中
        @Override
        protected Boolean doInBackground(String... params) {
//            for(int i=0;i<10000;i++){
//                Log.i(TAG,"doInBackground: "+params[0]);
//                //抛出进度
//                publishProgress(i);
//            }
//            try {
//                Thread.sleep(100000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            //可变参数，判断不为空或者length>0，把URL取出来
            if(params !=null&&params.length>0){
                String qqurl=params[0];
                try {
                    //构造URL
                    URL url=new URL(qqurl);
                    //构造链接并打开
                    URLConnection urlConnection=url.openConnection();
                    InputStream inputStream=urlConnection.getInputStream();
                    //获取下载内容的总长度
                    int contentLength=urlConnection.getContentLength();

                    //下载地址准备
                     mFilePath= Environment.getExternalStorageDirectory()
                            + File.separator+"qq.apk";
                    //对下载地址进行处理
                    File apkFile=new File(mFilePath);
                    if(apkFile.exists()){
                        boolean result=apkFile.delete();
                        if(!result){
                            return false;
                        }
                    }
                    //已下载的大小
                    int downloadSize=0;

                    byte[] bytes=new byte[1024];//缓存
                    int length;
                    //创建一个输出管道
                    OutputStream outputStream=new FileOutputStream(mFilePath);
                    //不断的一车一车的挖土，直到挖不到为止
                    while((length=inputStream.read(bytes))!=-1){
                        //挖到的放到我们的文件管道里
                        outputStream.write(bytes,0,length);
                        //累加我们的大小
                        downloadSize+=length;
                        //发送进度
                        publishProgress(downloadSize+100/contentLength);
                    }
                    //记得关闭输入输出流
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

            }else{
                return false;
            }
            return true;
        }

        //执行后
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mButton.setText(result?"下载完成":"下载失败");
            mTextView.setText(result?"下载完成"+mFilePath:"下载失败");

            //也是在主线程中，执行结果 处理
        }
        //处理进度
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //收到进度，处理
            if(values!=null&&values.length>0){
                mProgressBar.setProgress(values[0]);
            }

        }

    }
}
