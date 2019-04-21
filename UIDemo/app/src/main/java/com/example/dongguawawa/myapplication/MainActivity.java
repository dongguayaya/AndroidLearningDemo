package com.example.dongguawawa.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    //对控件进行初始化
    private Button register;
    private EditText accoutEdit;
    private EditText passwordEdit;
    private Button login;
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter =new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver=new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
        accoutEdit=findViewById(R.id.et_username);
        passwordEdit=findViewById(R.id.et_password);
        login=(Button) findViewById(R.id.bt_login);
        Button register=(Button) findViewById(R.id.bt_register);
        Button init=(Button) findViewById(R.id.bt_forget);
        Button test=findViewById(R.id.bt_test);
        Button hinnit=findViewById(R.id.bt_hinnit);
        Button webnew=findViewById(R.id.bt_web);
        Log.d("MainActivity","OnCreate execute");
        //隐式Intent用法
        hinnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.example.activitytest.ACTION_START");
                intent.addCategory("com.example.activitytest.MY_CATEGORY");
                startActivity(intent);
            }
        });

        webnew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            Intent intent =new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.baidu.com"));
            startActivity(intent);
            }
            });

        //显式Intent用法:
        //注册点击事件
        //通过startactivity的方式来实现
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,Second_Activity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String account=accoutEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                if(account.equals("donggua")&&password.equals("123456")){
                    Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,fourth_Activity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this,"账号密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        init.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,Third_Activity.class);
                startActivity(intent);
            }
        });
        test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,test_Activity.class);
                startActivity(intent);
            }
        });
    }
    //判断网络请求
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,Intent intent){
            ConnectivityManager connectionManager=(ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectionManager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isAvailable()){
                Toast.makeText(context,"network is availabel",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
