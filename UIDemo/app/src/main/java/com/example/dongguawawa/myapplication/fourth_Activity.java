package com.example.dongguawawa.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class fourth_Activity extends AppCompatActivity {



private List<Chat> chatList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_layout);
        initChat();
        ChatAdapter adapter=new ChatAdapter(fourth_Activity.this,R.layout.chat_item,chatList);
        ListView listView=findViewById(R.id.lv_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chat chat=chatList.get(position);
                Toast.makeText(fourth_Activity.this,chat.getName(),Toast.LENGTH_SHORT).show();
            }
        });
//        Button sendNotice=(Button)findViewById(R.id.bt_notice);
//        sendNotice.setOnClickListener(this);
    }
    private void initChat(){
        for(int i=0;i<2;i++){
            Chat marry=new Chat("marry",R.drawable.marry);
            chatList.add(marry);
            Chat alice=new Chat("alice",R.drawable.alice);
            chatList.add(alice);
            Chat john=new Chat("john",R.drawable.john);
            chatList.add(john);
            Chat cute=new Chat("cute",R.drawable.cute);
            chatList.add(cute);
            Chat jack=new Chat("jack",R.drawable.jack);
            chatList.add(jack);
        }
    }

//    @Override
//    public void onClick(View v){
//        switch (v.getId()){
//            case R.id.bt_notice:
//                NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                Notification notification =new NotificationCompat.Builder(this,"test")
//                        .setContentTitle("This is a message")
//                        .setContentText("This is a test")
//                        .setWhen(System.currentTimeMillis())
//                        .setSmallIcon(R.drawable.dongxi)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.dongxi)).build();
//                        manager.notify(1,notification);
//                        break;
//                        default:
//                        break;
//        }
//    }
}
