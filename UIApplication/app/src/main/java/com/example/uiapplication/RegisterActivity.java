package com.example.uiapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private TextView mLinkLogin;
    private EditText mName;
    private EditText mPhoneNumber;
    private EditText mInputPassword;
    private AppCompatButton mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initEvents();
    }
    private void initView() {
    mButtonLogin=findViewById(R.id.btn_register);
    mLinkLogin=findViewById(R.id.link_login);
    mInputPassword=findViewById(R.id.input_password);
    mName=findViewById(R.id.input_name);
    mPhoneNumber=findViewById(R.id.ed_inputPhoneNamber);

    }

    private void initEvents() {
        mLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linklogin();
            }
        });
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    private void signup() {
        if (!validate()) {
            onSignupFailed();
            return;
        }

        mButtonLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("创建用户");
        progressDialog.show();

        String name = mName.getText().toString();
        String phonenumber = mPhoneNumber.getText().toString();
        String password = mInputPassword.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "注册失败", Toast.LENGTH_LONG).show();

        mButtonLogin.setEnabled(true);
    }

    private void onSignupSuccess() {
        mButtonLogin.setEnabled(true);
        setResult(RESULT_OK, null);
        linklogin();

    }

    private boolean validate() {
        boolean valid = true;

        String name = mName.getText().toString();
        String phonenumber = mPhoneNumber.getText().toString();
        String password = mInputPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            mName.setError("至少三个字符");
            valid = false;
        } else {
            mName.setError(null);
        }

        if (phonenumber.isEmpty() ||phonenumber.length()<11 ) {
            mPhoneNumber.setError("请输入11位电话号码");
            valid = false;
        } else {
            mPhoneNumber.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mInputPassword.setError("请输入4到10位的密码~");
            valid = false;
        } else {
            mInputPassword.setError(null);
        }

        return valid;
    }

    private void linklogin() {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }


}
