package com.dongua.activityprogram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<Fruit> fruitList=new ArrayList<>();
    //活动第一次创建时调用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");
        initFruits();
        RecyclerView recyclerView=findViewById(R.id.rv_test);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits() {
        for(int i=0;i<10;i++){
            Fruit apple=new Fruit("Apple",R.drawable.apple);
            fruitList.add(apple);
            Fruit banana=new Fruit("banana",R.drawable.banana);
            fruitList.add(banana);
        }
    }

    //活动即将可见时调用
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    //当活动可见时调用
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    //当其他活动获得焦点时调用
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    //当活动不在可见时调用
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }
    //当活动被销毁时调用
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

}
