package com.example.cardviewdemo;

import java.util.ArrayList;
import java.util.List;

public class MsgLab {
    public static List<Msg> generateMockList(){
        List<Msg> msgList=new ArrayList<>();

        Msg msg=new Msg(1,R.drawable.img01,"test1","ceshiwendangfajofpijasfoi");
        msgList.add(msg);
        msg=new Msg(2,R.drawable.img02,"test2","ceshiwendangfajofpijasfoi");
        msgList.add(msg);
        msg=new Msg(3,R.drawable.img03,"test3","ceshiwendangfajofpijasfoi");
        msgList.add(msg);
        msg=new Msg(4,R.drawable.img04,"test4","ceshiwendangfajofpijasfoi");
        msgList.add(msg);
        msg=new Msg(5,R.drawable.img05,"test5","ceshiwendangfajofpijasfoi");
        msgList.add(msg);

        return msgList;
    }
}
