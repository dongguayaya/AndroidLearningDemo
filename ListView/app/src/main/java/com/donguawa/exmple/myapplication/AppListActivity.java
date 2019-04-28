package com.donguawa.exmple.myapplication;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        ListView appListView=findViewById(R.id.lv_app);

//        List<String> appNames=new ArrayList<>();
//
//        appNames.add("QQ");
//        appNames.add("微信");
//        appNames.add("全民K歌");
        final List<ResolveInfo> appInfos=getAppInfos();
        appListView.setAdapter(new AppListAdapter(appInfos));

        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String packageName=appInfos.get(position).activityInfo.packageName;

                String classString=appInfos.get(position).activityInfo.name;

                ComponentName componentName=new ComponentName(packageName,classString);

                final Intent intent=new Intent();
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取所有应用信息
     * @return
     */
    private List<ResolveInfo> getAppInfos(){
        Intent intent=new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return getPackageManager().queryIntentActivities(intent,0);
    }
    public class AppListAdapter extends BaseAdapter{

        //这就是要填充的数据列表
        List<ResolveInfo> mAppInfos;

        public AppListAdapter(List<ResolveInfo> appNames){
        mAppInfos=appNames;
        }

        @Override
        public int getCount() {
            //有多少条数据
            return mAppInfos.size();
        }

        @Override
        public Object getItem(int position) {
            //获取当前position的位置的这一条
            return mAppInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            //返回当前position位置的这一条的ID
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //处理 View--data填充数据的一个过程
            //把View读取出来
            LayoutInflater layoutInflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder viewHolder;
            //相当于viewHolder保存了视图
            if(convertView==null){
                viewHolder = new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.item_app_list_view,null);
                viewHolder.mAppIconImageView=convertView.findViewById(R.id.app_icon_image_view);
                viewHolder.mAppNameTextView=convertView.findViewById(R.id.app_name_text_view);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.mAppNameTextView.setText(mAppInfos.get(position).activityInfo.loadLabel(getPackageManager()));
            // viewHolder.mAppIconImageView.setImageDrawable((Drawable) mAppInfos.get(position).activityInfo.loadLabel(getPackageManager()));



//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String packageName=mAppInfos.get(position).activityInfo.packageName;
//
//                    String classString=mAppInfos.get(position).activityInfo.name;
//
//                    ComponentName componentName=new ComponentName(packageName,classString);
//
//                    final Intent intent=new Intent();
//                    intent.setComponent(componentName);
//                    startActivity(intent);
//                }
//            });

            return convertView;
        }

        public class ViewHolder{
            public ImageView mAppIconImageView;
            public TextView mAppNameTextView;
        }
    }

}
