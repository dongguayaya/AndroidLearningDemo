package com.donguawa.exmple.asynctasklearning;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 1.download方法 url localpath listener(监听接口)
 * 2.listener:start,success,fail progress
 * 3.用asynctask封装
 * 工具类
 */
public class DownloadHelper {
    public static void download(String url,String localPath,OnDownloadListener listener){
        DownloadAsyncTask task=new DownloadAsyncTask(url,localPath,listener);
        task.execute();
    }
    public static class DownloadAsyncTask extends AsyncTask<String,Integer,Boolean> {
        String mFilePath;
        String mUrl;
        OnDownloadListener mListener;

        public DownloadAsyncTask(String mFilePath, String mUrl, OnDownloadListener mListener) {
            this.mFilePath = mFilePath;
            this.mUrl = mUrl;
            this.mListener = mListener;
        }


        /**
         * 在异步任务之前，在主线程中
         */
        //执行前
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //可操作UI
            if(mListener!=null){
                mListener.onStart();
            }
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
                String qqurl=mUrl;
                try {
                    //构造URL
                    URL url=new URL(qqurl);
                    //构造链接并打开
                    URLConnection urlConnection=url.openConnection();
                    InputStream inputStream=urlConnection.getInputStream();
                    //获取下载内容的总长度
                    int contentLength=urlConnection.getContentLength();

                    //下载地址传进来了就不用做下载地址的准备
                    //对下载地址进行处理
                    File apkFile=new File(mFilePath);
                    if(apkFile.exists()){
                        boolean result=apkFile.delete();
                        if(!result){
                            if(mListener!=null){
                                mListener.onFail(-1,apkFile,"文件删除失败");
                            }
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
                    if(mListener!=null){
                        mListener.onFail(-2,new File(mFilePath),e.getMessage());
                    }
                    return false;
                }

            if(mListener!=null){
                mListener.onSuccess(0,new File(mFilePath));
            }
            return true;
        }

        //执行后
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(mListener!=null){
                if(result){
                    mListener.onSuccess(0,new File(mFilePath));
                }else{
                    mListener.onFail(-1,new File(mFilePath),"下载失败");
                }
            }

            //也是在主线程中，执行结果 处理
        }
        //处理进度
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //收到进度，处理
            if(values!=null&&values.length>0){
                if(mListener!=null){
                    mListener.onProgress(values[0]);
                }
            }

        }

    }
    public interface OnDownloadListener{
        void onStart();
        void onSuccess(int code , File file);//下载的code，文件传出来
        void onFail(int code ,File file, String message);
        void onProgress(int progress);

        //抽象类
        abstract class SimpleDownloadListener implements OnDownloadListener{
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(int progress) {

            }
        }
    }
}
