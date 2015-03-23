package com.gigigo.asv.testappoxeesdk.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gigigo.asv.testappoxeesdk.Models.ChatModel;
import com.gigigo.asv.testappoxeesdk.R;
import com.gigigo.asv.testappoxeesdk.Singleton.ChatColorsStatic;

import java.util.List;
import java.util.Random;

/**
 * al lurila q desde el broadcast tengo q noptificar al chatactivity q hay nuevo mensaje
 * y este debe añadir a la coleccion  List<ChatModel> y notificar al adaptador q se ha cambiado el array
 * Created by Alberto on 06/03/2015.
 */
public class ConversationAdapter extends ArrayAdapter {
    private Activity context;
    private List<ChatModel> model;
    private String Nick;
    // private HashMap<String, Integer> colors;


    public ConversationAdapter(Activity context, List<ChatModel> data,String nickName) {
        super(context, R.layout.chatfragmenttemplate, data); //asv ein?
        this.context = context;
        this.model = data;
        this.Nick=nickName;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        ChatModel currentChatModel = model.get(position);

        return  BindingData2Template(inflater,currentChatModel);
    }

    private View BindingData2Template(LayoutInflater inflater, ChatModel currentChatModel) {

        Integer foreColor = getColor4Nick(currentChatModel);

        int refView = getTemplate4Chat(currentChatModel);
        View itemView = inflater.inflate(refView, null);

        TextView lblNick = (TextView) itemView.findViewById(R.id.LblNick);
        TextView lblTime = (TextView) itemView.findViewById(R.id.LblTime);
        TextView lblMsg = (TextView) itemView.findViewById(R.id.LblMsg);

        //asv lo ponemos en español(_ES) xq nos sale del cimbrel, ni more ni less
        lblNick.setText(currentChatModel.nick);
        lblNick.setTextColor(foreColor);

        //asv todo calcular q poner(solo la hora)
        lblTime.setText(currentChatModel.time);

        lblMsg.setText(currentChatModel.msg);

        return itemView;
    }

    /*funciones que deberian implementarse ene l container de chatmodel pa hacer un pokemons de DI*/

    private int getTemplate4Chat(ChatModel currentChatModel) {
        //asv ene l di no habria q pasarle el chat, lo llevaria el pispo, a futuro se generanran mas tipos
        //aunk será subtipos en plan izq fichero de imagen...
        if (currentChatModel.nick.toLowerCase().equals(Nick.toLowerCase()))
            return R.layout.chattemplateizq;
        else
            return R.layout.chattemplatedere;
    }

    private Integer getColor4Nick(ChatModel currentChatModel) {
        Integer color = getNickColor(currentChatModel);
        return color;
    }

    private Integer getNickColor(ChatModel currentChatModel) {

        if (!ChatColorsStatic.getColors().containsKey(currentChatModel.nick))
            ChatColorsStatic.getColors().put(currentChatModel.nick, GenerateColor());

        return ChatColorsStatic.getColors().get(currentChatModel.nick);

    }

    private Integer GenerateColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }



}
