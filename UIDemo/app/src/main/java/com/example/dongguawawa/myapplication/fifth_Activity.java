package com.example.dongguawawa.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class fifth_Activity extends AppCompatActivity {
//    //数组中的数据无法直接传递给ListView，需要借助适配器来实现。
////运行程序，可以通过滚动的方式来查看屏幕外的数据。
    private String[] data={"Apple","banana","juice","pair","applation","Watermelon","Pear","Grape","Pineapple","Strawberry","Cherry","Mango","Apple","Banana","Orange","Watermelon","Pear","Grape","Pineapple","Strawberry","Cherry","Mango"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_layout);
        //ArrayAdapter的构造函数中依次传入当前上下文，ListView子项布局的id，以及要适配的数据；
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(fifth_Activity.this,android.R.layout.simple_list_item_1,data);
        ListView listView=findViewById(R.id.lv_list_view);
        //调用ListView的setAdapter()方法，将构建好的适配器对象传递进去，这样ListView和数据之间的关联就建立完成。
        listView.setAdapter(adapter);
    }
}
