package com.example.asynctasktest;

import android.os.AsyncTask;
import android.util.Log;

public class MyAsyncTask extends AsyncTask<Void,Void,Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("xys","doInBackground");
        publishProgress();//传入进度值
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("xys","onPreExecute");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("xys","onPostExecute");
    }

    @Override
    //获取更新获得进度条
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.d("xys","onProgressUpdate");
    }
}
