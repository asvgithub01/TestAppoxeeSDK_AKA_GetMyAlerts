package com.gigigo.asv.testappoxeesdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodSession;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appoxee.Appoxee;
import com.appoxee.utils.Utils;
import com.gigigo.asv.testappoxeesdk.Adapters.ConversationAdapter;
import com.gigigo.asv.testappoxeesdk.Singleton.ChatArrayStatic;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;


public class ChatActivity extends ActionBarActivity {


    ListView lstConversation;
    EventListener myListener;

    EditText txtInput;


    LinearLayout layNick;
    LinearLayout layFooter;
    EditText txtNick;

    EditText txtMsg;
    String Nick = "";
    ProgressDialog dialog;

    ArrayList<String> deviceTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        InitActivity();

        Button btn = (Button) findViewById(R.id.BtnSendChatGrp);
        txtInput = (EditText) findViewById(R.id.TxtChatGrpInput);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (ChatActivity.this) {

                    SendChatNotification();
                    //    ChatArrayStatic.AddChat2Chats(new ChatModel(MyNickFAAAAKE, "06/03/2015 13:03", txtInput.getText().toString()));//,ChatActivity.this);
                }
            }
        });


        layNick = (LinearLayout) findViewById(R.id.LayNick);
        layFooter = (LinearLayout) findViewById(R.id.footer);
        txtNick = (EditText) findViewById(R.id.TxtNickInput);

        Button btn2 = (Button) findViewById(R.id.BtnSetNick);
        if (GetNick().equals("")) {
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!txtNick.getText().equals("")) {
                        Nick = txtNick.getText().toString();
                        layFooter.setVisibility(View.VISIBLE);
                        lstConversation.setVisibility(View.VISIBLE);
                        layNick.setVisibility(View.GONE);

                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... params) {
                                String appxID = Appoxee.getAppoxeeAppID();
                                String AppxDevAlias = Appoxee.getDeviceAlias();
                                Appoxee.setDeviceAlias(Nick);
                                SetNick(Nick);
                                return null;
                            }
                        }.execute();


                    }

                }
            });
        } else {

            Nick = GetNick();
            layFooter.setVisibility(View.VISIBLE);
            lstConversation.setVisibility(View.VISIBLE);
            layNick.setVisibility(View.GONE);
        }


        //establecer el tag de chat

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                deviceTags = Appoxee.getDeviceTags();

                if (deviceTags == null) deviceTags = new ArrayList<String>();

                if (deviceTags.contains("CHAT") == false) {
                    deviceTags.add(0, "CHAT");
                    Appoxee.addTagsToDevice(deviceTags);
                }

                return null;
            }
        }.execute();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                ArrayList<String> chatdeviceTags = new ArrayList<String>();
                chatdeviceTags.add(0, "CHAT");
                Appoxee.removeTagsFromDevice(chatdeviceTags);
                return null;
            }
        }.execute();

    }

    ConversationAdapter catAdp;

    private void InitActivity() {

        lstConversation = (ListView) findViewById(R.id.LstChatGrp);
        //asv todo suscribirme al evento de OnNewChatAdded para refrescar la lista con el nuevo modelo
        //  myListener = ChatArrayStatic.OnNewChatAdded;

        catAdp = new ConversationAdapter(ChatActivity.this, ChatArrayStatic.getInitChatList(), GetNick());
        lstConversation.setDividerHeight(0);
        lstConversation.setAdapter(catAdp);


        ChatArrayStatic.OnNewChatAdded = new InputMethodSession.EventCallback() {
            @Override
            public void finishedEvent(int seq, boolean handled) {

                Utils.Debug("*******************************FUCK YEAH, evento producido***********************************");
                lstConversation = (ListView) findViewById(R.id.LstChatGrp);
                //asv todo suscribirme al evento de OnNewChatAdded para refrescar la lista con el nuevo modelo
                catAdp = new ConversationAdapter(ChatActivity.this, ChatArrayStatic.getChatList(), GetNick());
                lstConversation.setAdapter(catAdp);



            }
        };

    }


    private void SendChatNotification() {

        txtMsg = (EditText) findViewById(R.id.TxtChatGrpInput);
        // Nick = "maderFaker";


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(ChatActivity.this, "",
                        getApplicationContext().getResources().getString(R.string.LoadingDialog), true);
            }

            @Override
            protected Void doInBackground(Void... params) {

                //asv hacer el push con el headers y el data
                String Appoxee_HOST = "https://saas.appoxee.com";
                String JsonPostFile = "api/v3/message";

                try {
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setLogLevel(RestAdapter.LogLevel.FULL)
                            .setEndpoint(Appoxee_HOST)
                            .build();


                    RequestJsonPOSTService rqtPOST = restAdapter.create(RequestJsonPOSTService.class);

                    String TimeStampa = "" + System.currentTimeMillis();

                    String testStr = " {\"campaign_id\": 170048," +
                            "\"application_id\": 104672," +
                            "\"sound\": \"\"," +
                            "\"name\": \"MENSA NUEVO: " + TimeStampa + " \"," +
                            "\"description\": \"\"," +
                            "\"type\": 0," +
                            "\"content_type\": 1," +
                            "\"inbox_title\": \"title\"," +
                            "\"inbox_description\": \"\"," +
                            "\"push_body\": \"%s\"," +
                            "\"push_badge\": 1," +
                            "\"schedule_type\":0," +
                            "\"timezone\": \"\"," +
                            "\"overdue\": 0," +
                            "\"schedule_date\":0 ," +
                            "\"expire_date\" :0, " +
                            "\"segments\":[{\"segment_id\":14551342}], " +
                            "\"payload\" : {\"tag\":\"%s\", \"nick\":\"%s\",\"time\":\"%s\"}}";

// \"segments\":   [\n  \n    {\n        \"segment_id\": 13602312\n    } \n ],\n

                    String Mensaje = "";
                    String Tag = "CHAT";

                    String hora = getDateTime();
                    //hora="";
                    Mensaje = txtMsg.getText().toString();

                    testStr = String.format(testStr, Mensaje, Tag, Nick, hora);

                    TypedInput in = new TypedByteArray("application/json", testStr.getBytes("UTF-8"));

                    Object obj = rqtPOST.doPost(JsonPostFile, in, "gigigoapps", "gigigoapps", "gigigoMadrid1", "1");

                    Utils.Debug(obj.toString());

                    //  Toast.makeText(getApplicationContext(), getResources().getString(R.string.OK), Toast.LENGTH_LONG).show();

                } catch (RetrofitError e) {
                    String json = new String(((TypedByteArray) e.getResponse().getBody()).getBytes());

                    //    Toast.makeText(getApplicationContext(), getResources().getString(R.string.NOTOK), Toast.LENGTH_LONG).show();
                    Utils.Debug("******************************************************");
                    Utils.Debug(json);
                    Utils.Debug("******************************************************");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();

                    //     Toast.makeText(getApplicationContext(), getResources().getString(R.string.NOTOK), Toast.LENGTH_LONG).show();
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                txtInput.setText("");
                dialog.dismiss();
                dialog = null;
            }

            @Override
            protected void onCancelled() {
                dialog.dismiss();
                dialog = null;
                super.onCancelled();
            }

        }.execute();
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    /*ISOLATED */
    //asv q ricas estas dos funciones para inyeccion de dependencias o pa crearse un singleton

    final String NameFilePref = "ChatiChatiBangBang";
    final String NICK_KEY = "nick";
    SharedPreferences prefs;
    //  getSharedPreferences(NameFilePref, Context.MODE_PRIVATE);

    private String GetNick() {
        if (prefs == null) prefs = getSharedPreferences(NameFilePref, Context.MODE_PRIVATE);
        return prefs.getString(NICK_KEY, "");
    }

    private void SetNick(String nickNew) {
        if (prefs == null) prefs = getSharedPreferences(NameFilePref, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(NICK_KEY, nickNew);
        editor.commit();
    }

//    http://www.vogella.com/tutorials/JavaSerialization/article.html

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mnu_Suscription) {
            Intent goHome = new Intent(this, MainActivity.class);
            startActivity(goHome);
        }


        if (id == R.id.mnu_notification) {
            Intent goPush = new Intent(this, PushActivity.class);
            startActivity(goPush);
        }
        if (id == R.id.mnu_chat) {
            Intent goChat = new Intent(this, ChatActivity.class);
            startActivity(goChat);
        }

        return super.onOptionsItemSelected(item);
    }
}
