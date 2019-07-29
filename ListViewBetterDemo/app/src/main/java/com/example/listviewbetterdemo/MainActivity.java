package com.example.listviewbetterdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.nio.charset.MalformedInputException;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private Button mChatButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton=findViewById(R.id.bt_better);
        mChatButton=findViewById(R.id.bt_chat);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RequestDataActivity.class);
                startActivity(intent);
            }
        });
        mChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}
