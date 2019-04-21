package com.example.asynctasktest;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private List<NewsBean> mList;
    private LayoutInflater minflater;

    public NewsAdapter(Context context,List<NewsBean> data){
        mList=data;
        minflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=minflater.inflate(R.layout.item_layout,null);
            viewHolder.ivIcon=convertView.findViewById(R.id.iv_icon);
            viewHolder.tvTitle=convertView.findViewById(R.id.tv_title);
            viewHolder.tvContent=convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        //viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        String url=mList.get(position).newsIconUrl;
        viewHolder.ivIcon.setTag(url);
        new ImageLoader().showImageByAsyncTask(viewHolder.ivIcon,url);
        viewHolder.tvTitle.setText(mList.get(position).newsTitle);
        viewHolder.tvContent.setText(mList.get(position).newsContent);
        return convertView;
    }
    class ViewHolder{
        public TextView tvTitle,tvContent;
        public ImageView ivIcon;

    }
}
