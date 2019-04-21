package com.example.dongguawawa.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private int resourceId;
    public ChatAdapter(Context context, int textViewResourceId, List<Chat> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        Chat chat=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.chatImage=view.findViewById(R.id.iv_chat_image);
            viewHolder.chatname=view.findViewById(R.id.tv_chat_name);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.chatImage.setImageResource(chat.getImageId());
        viewHolder.chatname.setText(chat.getName());
        return view;
    }
    class ViewHolder{
        ImageView chatImage;
        TextView chatname;
    }
}
