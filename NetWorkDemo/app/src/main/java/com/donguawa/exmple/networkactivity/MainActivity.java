package com.donguawa.exmple.networkactivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;
    private Button mGetData;
    private Button mParseData;

    private static final String TAG = "MainActivity";
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initEvent() {
        mGetData.setOnClickListener(this);
        mParseData.setOnClickListener(this);
    }

    private void initView() {
        mGetData=findViewById(R.id.bt_getData);
        mParseData=findViewById(R.id.bt_pauseData);
        mTextView=findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_getData:
                new Thread(new Runnable() {//直接新开一个线程
                    @Override
                    public void run() {
                        requestDataByGet();
                    }
                }).start();
                break;
            case R.id.bt_pauseData:
                handleJSONData(result);
                break;
        }
    }

    private void handleJSONData(String result) {
        try {

            ResultData resultData=new ResultData();

            //创建list
            List<ResultData.Test> datalist=new ArrayList<>();
            JSONObject jsonObject=new JSONObject(result);
            int code=jsonObject.getInt("code");
            JSONArray tests=jsonObject.getJSONArray("result");

            resultData.setMcode(code);
            if(tests!=null&&tests.length()>0){
                for(int index=0;index<tests.length();index++){
                    JSONObject test1=(JSONObject) tests.get(index);
                    int sid=test1.getInt("sid");
                    String text=test1.getString("text");
                    String type=test1.getString("type");
                    int uid=test1.getInt("uid");
                    String name=test1.getString("name");

                    //每次拿出一个对象就构建一个对象
                    ResultData.Test testItem=new ResultData.Test();
                    testItem.setmSid(sid);
                    testItem.setmText(text);
                    testItem.setmType(type);
                    testItem.setUid(uid);
                    testItem.setmName(name);
                    datalist.add(testItem);
                }
                resultData.setmTest(datalist);//把dadalist添加进去
            }

            mTextView.setText(resultData.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestDataByGet() {
        try {
            URL url=new URL("https://api.apiopen.top/getJoke?page=1&count=2&type=video");
            HttpsURLConnection connection=(HttpsURLConnection)url.openConnection();
            connection.setConnectTimeout(30*1000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Charset","UTF-8");//字符集
            connection.setRequestProperty("Accept-Charset","UTF-8");
            connection.connect();//发起连接
            int responseCode=connection.getResponseCode();//获取请求到的响应码
            String responseMessage=connection.getResponseMessage();//获取请求码的消息
            if(responseCode==HttpsURLConnection.HTTP_OK){
                InputStream inputStream=connection.getInputStream();//获得connenction里面字节流
                result= streamToString(inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result=decode(result);
                        mTextView.setText(result);
                    }
                });

            }else{
                Log.e (TAG,"run:error code:"+responseCode+",message:"+responseMessage);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();//如果是不合法的URL就抛出异常
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void requestDataByPost() {
        try {
            //?page=1&count=2&type=video
            URL url=new URL("https://api.apiopen.top/getJoke");
            HttpsURLConnection connection=(HttpsURLConnection)url.openConnection();
            connection.setConnectTimeout(30*1000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Charset","UTF-8");//字符集
            connection.setRequestProperty("Accept-Charset","UTF-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false); //不能缓存

            connection.connect();//发起连接

            String data= "key=" + getEncodeValue("value") + "&number="+getEncodeValue("150088886666");

            OutputStream outputStream=connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode=connection.getResponseCode();//获取请求到的响应码
            String responseMessage=connection.getResponseMessage();//获取请求码的消息
            if(responseCode==HttpsURLConnection.HTTP_OK){
                InputStream inputStream=connection.getInputStream();//获得connenction里面字节流
                result= streamToString(inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result=decode(result);
                        mTextView.setText(result);
                    }
                });

            }else{
                Log.e (TAG,"run:error code:"+responseCode+",message:"+responseMessage);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();//如果是不合法的URL就抛出异常
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @NonNull
    private String getEncodeValue(String value) {
        String encode=null;
        try {
            encode=URLEncoder.encode("value","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return 字符串
     */
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//将它放在缓冲区里面
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }//不停的读，将buffer写进缓冲区里面
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();//转换成字节数组
            return new String(byteArray);//然后在转换成String
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
    /**
     * 将Unicode字符转换为UTF-8类型字符串
     */
    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuilder retBuf = new StringBuilder();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else {
                    retBuf.append(unicodeStr.charAt(i));
                }
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }
}
