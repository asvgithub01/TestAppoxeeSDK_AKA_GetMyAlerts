package com.gigigo.asv.testappoxeesdk.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ConversationModel {

    @SerializedName("chat")
    public List<ChatModel> chat = new ArrayList<ChatModel>();
    public ConversationModel withChat(List<ChatModel> chat) {
        this.chat = chat;
        return this;
    }
}

