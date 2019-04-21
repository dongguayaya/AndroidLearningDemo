package com.example.asynctasktest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageTask extends Activity {
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private static String URL=
            "https://images2015.cnblogs.com/blog/755627/201511/755627-20151115133636462-977319220.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        mImageView=findViewById(R.id.iv_testtask);
        mProgressBar=findViewById(R.id.progressBar);
        //设置传递进去的参数
        new MyAsyncTask().execute(URL);
    }
    //传入URL
    class MyAsyncTask extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //操作UI
            mProgressBar.setVisibility(View.GONE);
            mImageView.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //获取传递进来的参数
            String url=params[0];//取出对应的URL
            Bitmap bitmap=null;
            URLConnection connection;
            InputStream is;//用于获取输入流
            //整个网络的耗时操作
            try {
                connection =new URL(url).openConnection();//获取网络连接对象
                is=connection.getInputStream();
                BufferedInputStream bis=new BufferedInputStream(is);
                Thread.sleep(3000);
                //通过decodeStream解析输入流
                bitmap= BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //最后将bitmap作为返回值返回给后面调用的方法
            return bitmap;
        }
    }
}
