package com.example.listviewbetterdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.listviewbetterdemo.Adapter.RequestDataAdapter;
import com.example.listviewbetterdemo.model.LessonInfo;
import com.example.listviewbetterdemo.model.LessonResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RequestDataActivity extends AppCompatActivity {
    private ListView mlistView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

         mlistView=findViewById(R.id.lv_data);

        //List<data> item.view

        new RequestDataAsyncTask().execute();

    }
    //用asyncTask进行异步网络请求
    public class RequestDataAsyncTask extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Loading
        }

        //网络请求
        @Override
        protected String doInBackground(Void... params) {
            return request("http://www.imooc.com/api/teacher?type=2&page=1");
        }

        private String request(String urlString) {
            try {
                URL url=new URL(urlString);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(30000);
                connection.connect();

                int responseCode=connection.getResponseCode();
                String responseMessage=connection.getResponseMessage();
                if(responseCode==HttpURLConnection.HTTP_OK){
                    InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder=new StringBuilder();

                    String line;

                    while ((line=bufferedReader.readLine())!=null){
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Loading 消失，数据处理 刷新界面

            LessonResult lessonResult=new LessonResult();
            try {
                JSONObject jsonObject=new JSONObject(result);
                lessonResult.setmStatus(jsonObject.getInt("status"));
                lessonResult.setmMessage(jsonObject.getString("msg"));

                List<LessonInfo> lessonInfos=new ArrayList<>();
                JSONArray dataArray=jsonObject.getJSONArray("data");

                for(int index=0;index<dataArray.length();index++){
                    LessonInfo lessonInfo=new LessonInfo();
                    JSONObject tempJSONObject= (JSONObject) dataArray.get(index);
                    lessonInfo.setmName(tempJSONObject.getString("name"));
                    lessonInfos.add(lessonInfo);

                }
                lessonResult.setmLessonInfoList(lessonInfos);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //onPostExecute适配器将获得的数据与List绑定
            mlistView.setAdapter(new RequestDataAdapter(RequestDataActivity.this,lessonResult.getmLessonInfoList()));
        }

    }
}
