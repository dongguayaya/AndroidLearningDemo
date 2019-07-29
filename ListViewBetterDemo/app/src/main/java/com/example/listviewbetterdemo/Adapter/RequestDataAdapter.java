package com.example.listviewbetterdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listviewbetterdemo.R;
import com.example.listviewbetterdemo.model.LessonInfo;

import java.util.ArrayList;
import java.util.List;

public class RequestDataAdapter extends BaseAdapter {
    private List<LessonInfo> mLessonInfos=new ArrayList<>();
    private Context mContext;
    public RequestDataAdapter(Context context,List<LessonInfo> infos){
        mLessonInfos=infos;
        mContext=context;
    }
    @Override
    public int getCount() {
        return mLessonInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mLessonInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //ViewHolder优化列表
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=new ViewHolder();
        if(convertView==null){
            LayoutInflater layoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.item_app_list_view,null);
            viewHolder.mAppIconImageView=convertView.findViewById(R.id.app_icon_image_view);
            viewHolder.mAppNameTextView=convertView.findViewById(R.id.app_name_text_view);

            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.mAppNameTextView.setText(mLessonInfos.get(position).getmName());
        viewHolder.mAppIconImageView.setVisibility(View.GONE);
        return convertView;
    }
    public class ViewHolder{
        public ImageView mAppIconImageView;
        public TextView mAppNameTextView;
    }
}
