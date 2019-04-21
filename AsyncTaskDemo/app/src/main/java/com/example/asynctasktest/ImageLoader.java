package com.example.asynctasktest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageLoader {

    private ImageView mImageview;
    private String mUrl;

    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(mImageview.getTag().equals(mUrl)){
                mImageview.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };
    //多线程方式
    public void showImageByThread(ImageView imageView, final String url) {

        mImageview=imageView;
        mUrl=url;
        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap=getBitmapFromURL(url);
                Message message=Message.obtain();
                message.obj=bitmap;
                mhandler.sendMessage(message);
            }
        }.start();
    }

    public Bitmap getBitmapFromURL(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }finally {

            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void showImageByAsyncTask(ImageView imageView,String url){
        new NewsAsyncTask(imageView,url).execute(url);
    }
    private class NewsAsyncTask extends AsyncTask<String,Void,Bitmap>{

        private  String mUrl;
        private  ImageView mImageView;
        public NewsAsyncTask(ImageView imageView,String url){
            mImageView=imageView;
            mUrl=url;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            return getBitmapFromURL(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(mImageView.getTag()==null){
                mImageView.setTag(mUrl);
            }
            else if(mImageView.getTag().equals(mUrl)){
                mImageView.setImageBitmap(bitmap);
            }

        }
    }
}
