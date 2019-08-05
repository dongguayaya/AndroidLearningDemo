package com.example.cardviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mlvListView;
    //初始化list要new否则会报空对象引用
    private List<Msg> mDatas=new ArrayList<>();
    private MsgAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlvListView=findViewById(R.id.lv_msgList);

        //初始化数据集
        mDatas.addAll(MsgLab.generateMockList());
        mDatas.addAll(MsgLab.generateMockList());

        msgAdapter=new MsgAdapter(this,mDatas);


        mlvListView.setAdapter(msgAdapter);
    }
}
