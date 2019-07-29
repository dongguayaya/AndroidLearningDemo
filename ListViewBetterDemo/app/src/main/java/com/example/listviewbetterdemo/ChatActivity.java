package com.example.listviewbetterdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ListView mListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mListView=findViewById(R.id.lv_chat);

        ArrayList<ChatMessage> chatMessages=new ArrayList<>();
        chatMessages.add(new ChatMessage(1,2,"Jack","8:20","How are you",true));
        chatMessages.add(new ChatMessage(2,1,"John","8:21","我很好",false));
        chatMessages.add(new ChatMessage(1,2,"Jack","8:22","今天天气怎么样",true));
        chatMessages.add(new ChatMessage(2,1,"John","8:23","热成狗了",false));

        mListView.setAdapter(new ChatMessageAdapter( this,chatMessages));

    }

    public  class ChatMessage{
        private int mID;
        private int mFriendID;
        private String mName;
        private String mDate;
        private String mContent;
        private boolean mIsComeMessage;

        public ChatMessage(int mID, int mFriendID, String mName, String mDate, String mContent, boolean mIsComeMessage) {
            this.mID = mID;
            this.mFriendID = mFriendID;
            this.mName = mName;
            this.mDate = mDate;
            this.mContent = mContent;
            this.mIsComeMessage = mIsComeMessage;
        }

        public int getmID() {
            return mID;
        }

        public void setmID(int mID) {
            this.mID = mID;
        }

        public int getmFriendID() {
            return mFriendID;
        }

        public void setmFriendID(int mFriendID) {
            this.mFriendID = mFriendID;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmDate() {
            return mDate;
        }

        public void setmDate(String mDate) {
            this.mDate = mDate;
        }

        public String getmContent() {
            return mContent;
        }

        public void setmContent(String mContent) {
            this.mContent = mContent;
        }

        public boolean ismIsComeMessage() {
            return mIsComeMessage;
        }

        public void setmIsComeMessage(boolean mIsComeMessage) {
            this.mIsComeMessage = mIsComeMessage;
        }
    }
    public static class ChatMessageAdapter extends BaseAdapter{
        ArrayList<ChatMessage> mchatMessages=new ArrayList<>();
        private Context mContext;

        //接口实现两种数据类型
        interface IMessageViewType{
            int COM_MESSAGE=0;
            int TO_MESSAGE=1;
        }
        public ChatMessageAdapter(Context mContext, ArrayList<ChatMessage> mchatMessages) {
            this.mContext = mContext;
            this.mchatMessages = mchatMessages;

        }

        @Override
        public int getCount() {
            return mchatMessages.size();
        }

        @Override
        public Object getItem(int position) {
            return mchatMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //根据不同类型返回不同的视图,根据消息类型返回对应的值
        @Override
        public int getItemViewType(int position) {

            ChatMessage entity = mchatMessages.get(position);
            if (entity.ismIsComeMessage()) {
                return IMessageViewType.COM_MESSAGE;
            } else {
                return IMessageViewType.TO_MESSAGE;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final ChatMessage entity = mchatMessages.get(position);
            boolean isComMsg = entity.ismIsComeMessage();
            ViewHolder viewHolder;
            if(convertView==null){
               if(isComMsg){
                convertView=LayoutInflater.from(mContext).inflate(R.layout.chatting_item_msg_text_left,null);
               }else{
                   convertView=LayoutInflater.from(mContext).inflate(R.layout.chatting_item_msg_text_right,null);
               }
                viewHolder = new ViewHolder();
                viewHolder.mSendTime = (TextView) convertView.findViewById(R.id.tv_send_time);
                viewHolder.mUserName = (TextView) convertView.findViewById(R.id.tv_username);
                viewHolder.mContent = (TextView) convertView.findViewById(R.id.tv_chat_content);
                viewHolder.mTime = (TextView) convertView.findViewById(R.id.tv_time);
                viewHolder.mUserAvatar = (ImageView) convertView.findViewById(R.id.iv_user_head);
                viewHolder.mIsComMessage = isComMsg;
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mSendTime.setText(entity.getmDate());
            viewHolder.mContent.setText(entity.getmContent());
            viewHolder.mContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            viewHolder.mTime.setText("");
            viewHolder.mUserName.setText(entity.getmName());
            if (isComMsg) {
                viewHolder.mUserAvatar.setImageResource(R.drawable.avatar);
            } else {
                viewHolder.mUserAvatar.setImageResource(R.mipmap.ic_launcher);
            }

            return convertView;
        }
        class ViewHolder {
            public TextView mSendTime;
            public TextView mUserName;
            public TextView mContent;
            public TextView mTime;
            public ImageView mUserAvatar;
            public boolean mIsComMessage = true;
        }
    }
}
