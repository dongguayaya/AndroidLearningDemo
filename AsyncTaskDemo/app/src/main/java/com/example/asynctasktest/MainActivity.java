package com.example.asynctasktest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView  mListView;
    private static String URL="https://api.apiopen.top/getJoke?page=1&count=2&type=video";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView=findViewById(R.id.lv_main);
        new NewsAsyncTask().execute(URL);
        MyAsyncTask task=new MyAsyncTask();
        task.execute();
    }

    //将url对应的json格式数据转化为我们所封装的NewsBean对象
    private List<NewsBean> getJsonData(String url){
        List<NewsBean> newsBeanList=new ArrayList<>();
        try{
            String jsonString=readStream(new URL(url).openStream());
            Log.d("xys",jsonString);
            JSONObject jsonObject;
            NewsBean newsBean;
            jsonObject=new JSONObject(jsonString);
            JSONArray jsonArray=jsonObject.getJSONArray("result");

            for(int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                newsBean=new NewsBean();
                newsBean.newsIconUrl=jsonObject.getString("thumbnail");
                newsBean.newsTitle=jsonObject.getString("text");
                newsBean.newsContent=jsonObject.getString("name");
                newsBeanList.add(newsBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch(IOException e) {
        e.printStackTrace();
    }
        return newsBeanList;
    }

    //通过inputstream解析网页返回的数据
    private String readStream(InputStream is){
        InputStreamReader isr;
        String result="";
        try{
            String line;
            isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            while((line=br.readLine())!=null){
                result+=line;
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void loadImage(View view) {
        startActivity(new Intent(this,ImageTask.class));
    }

    //实现网络的异步访问
    class NewsAsyncTask extends AsyncTask<String,Void, List<NewsBean>>{


        @Override
        protected List<NewsBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeans){
            super.onPostExecute(newsBeans);
            NewsAdapter adapter=new NewsAdapter(MainActivity.this,newsBeans);
            mListView.setAdapter(adapter);
        }
    }
}
