package com.example.uiapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private AppCompatButton mAppCompatiButton;
    private TextView mRegister;
    private EditText mUsername;
    private EditText mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initEvents();
    }



    private void initView() {
        mAppCompatiButton=findViewById(R.id.btn_login);
        mRegister=findViewById(R.id.tv_register);
        mUsername=findViewById(R.id.ed_username);
        mPassword=findViewById(R.id.ed_password);
    }
    private void initEvents(){
        mAppCompatiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });

    }

    private void login() {
        if(!validate()){
            onLoginFailed();
            return;
        }
        mAppCompatiButton.setEnabled(false);
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this,R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登陆中");
        progressDialog.show();

        String username=mUsername.getText().toString();
        String password=mPassword.getText().toString();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoginSuccess();
                progressDialog.dismiss();
            }
        },3000);
    }

    private void onLoginSuccess() {
        mAppCompatiButton.setEnabled(true);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(),"Login failed",Toast.LENGTH_LONG).show();
        mAppCompatiButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid=true;
        String username=mUsername.getText().toString();
        String password=mPassword.getText().toString();
        if(username.isEmpty()||username.length()>10){
            mUsername.setError("用户名不能超过10位哟~");
            valid=false;

        }else{
            mUsername.setError(null);
        }

        if(password.isEmpty()||password.length()<4||password.length()>10){
            mPassword.setError("请输入4到10位之间的密码~");
            valid=false;
        }else {
            mPassword.setError(null);
        }

        return valid;
    }

    private void Register() {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivityForResult(intent,0);
        finish();
    }

}
