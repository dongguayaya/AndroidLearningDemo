package com.donguawa.exmple.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient mClient=new OkHttpClient();

    private static final MediaType MEDIA_TYPE_MARKKDOWN= MediaType.parse("text/x-markdown;charset=utf-8");
    public static final String POST_URL="https://api.github.com/markdown/raw";
    private String TAG="MainActivity-xx";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView=findViewById(R.id.tvContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGet:
                get();
                break;
            case R.id.menuResponse:
                response();
                break;
            case R.id.menuPost:
                post();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void post() {
        Request.Builder builder=new Request.Builder();
        builder.url(POST_URL);
        builder.post(RequestBody.create(MEDIA_TYPE_MARKKDOWN,"Hello world github/linguist#1 **cool**, and #1!"));
        Request request=builder.build();
        Call call=mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String content=response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText(content);
                        }
                    });
                }
            }
        });
    }

    private void response() {
        Request.Builder builder=new Request.Builder();
        builder.url("https://raw.githubusercontent.com/square/okhttp/master/README.md");
        Request request=builder.build();
        Call call=mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"onFailure() called with: call=["+call+"],e=["+e+"]");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"onResponse() called with: call=["+call+"],e=["+response+"]");
                int code=response.code();
                Headers headers=response.headers();
                String content=response.body().string();
//                String contentType=headers.get("Content-Type");

                final StringBuilder buf=new StringBuilder();
                buf.append("code: "+code);
                buf.append("\nHeaders:"+headers);
                buf.append("\nbody: "+content);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(buf.toString());
                    }
                });
            }
        });
    }

    private void get() {
        ExecutorService executor= Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Request.Builder builder=new Request.Builder();
                builder.url("https://raw.githubusercontent.com/square/okhttp/master/README.md");
                Request request=builder.build();
                Log.d(TAG,"run:"+request);
                Call call=mClient.newCall(request);
                try {
                    Response response=call.execute();
                    if(response.isSuccessful()){
                       final String string=response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText(string);
                            }
                        });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.shutdown();

    }
}
