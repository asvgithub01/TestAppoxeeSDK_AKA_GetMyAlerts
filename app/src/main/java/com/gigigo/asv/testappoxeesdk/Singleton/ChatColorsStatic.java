package com.gigigo.asv.testappoxeesdk.Singleton;

import android.graphics.Color;

import java.util.HashMap;

/**
 * Created by Alberto on 06/03/2015.
 * singleton pa guardar los colores de una conversacion
 */
public class ChatColorsStatic {

    private static HashMap<String,Integer> ChatsColors;

    public static HashMap<String,Integer> getColors() {
        if(ChatsColors == null) {
            ChatsColors = new HashMap<String,Integer>();
        }
        return ChatsColors;
    }
}
