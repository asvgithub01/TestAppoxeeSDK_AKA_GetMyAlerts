package com.gigigo.asv.testappoxeesdk.Models;

import com.google.gson.annotations.SerializedName;


public class ChatModel {

    @SerializedName("nick")
    public String nick;
    @SerializedName("time")
    public String time;
    @SerializedName("msg")
    public String msg;

    public ChatModel (String nick,String time,String msg) {
        this.nick = nick;
        this.time = time;
        this.msg = msg;
    }


}
