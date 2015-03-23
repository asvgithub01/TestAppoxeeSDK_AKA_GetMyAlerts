package com.gigigo.asv.testappoxeesdk.Singleton;

import android.view.inputmethod.InputMethodSession;

import com.appoxee.utils.Utils;
import com.gigigo.asv.testappoxeesdk.Models.ChatModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto on 06/03/2015.
 */
public class ChatArrayStatic {



    //instancia bus DE evENTOS

    public static InputMethodSession.EventCallback OnNewChatAdded;
    private static List<ChatModel> chats;

    public static  List<ChatModel> getChatList(){return  chats;}

    public static List<ChatModel> getInitChatList()
    {
        chats= new ArrayList<ChatModel>();
        ChatModel m = new ChatModel("maderFaker","06/03/2015 13:03","Cuanto cuate con guantes combate");
        chats.add(m);
          m = new ChatModel("maderFaker","06/03/2015 13:03"," con bate??");
        chats.add(m);
        m = new ChatModel("anonimous","06/03/2015 13:04"," xo que diche usté paaaaata lieeeebre!");
        chats.add(m);
        m = new ChatModel("maderFaker","06/03/2015 13:04"," pos no te digo trigo por no llamarte rodrigor");
        chats.add(m);
        m = new ChatModel("anonimous","06/03/2015 13:04"," vamos que eres un pecadior de la pradera,no?");
        chats.add(m);
        m = new ChatModel("grijanderMonderGraham","06/03/2015 13:06","Que pasa piratas como vais??");
        chats.add(m);
        chats.add(m);
        m = new ChatModel("maderFaker","06/03/2015 13:06","estabamos de puta madre hasta que has entrao joputa");
        chats.add(m);
        m = new ChatModel("anonimous","06/03/2015 13:07","suscribo lo que dice el maderfaker(esto es una prueba para meter el nick spanneable :P)");
        chats.add(m);

        return chats;

    }

    public static void AddChat2Chats(ChatModel m)
    {
        try {
            chats.add(m);
            OnNewChatAdded.finishedEvent(0, true);
        }
        catch (Exception    e){
            Utils.Debug("*******************"+e.toString());
        }
    }

//    public static void AddChat2Chats(ChatModel m, View.OnClickListener onClickListener) {
//        synchronized(onClickListener) {
//            chats.add(m);
//
//            OnNewChatAdded.notify();
//        }
//    }
//
//    public static void AddChat2Chats(ChatModel m, Context cntxt) {
//
//        synchronized(cntxt) {
//            chats.add(m);
//
//            OnNewChatAdded.finishedEvent(0,true);
//        }
//
//    }
}
