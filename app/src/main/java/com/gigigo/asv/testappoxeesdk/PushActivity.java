package com.gigigo.asv.testappoxeesdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;

import com.appoxee.utils.Utils;
import com.gigigo.asv.testappoxeesdk.Adapters.RequestJsonAdapter;
import com.gigigo.asv.testappoxeesdk.Models.KEYS;
import com.gigigo.asv.testappoxeesdk.Models.RequestJsonModel;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

interface RequestJsonService {
    @GET("/{JsonFile}")
    public RequestJsonModel getRequestJson(@Path("JsonFile") String fileJson);
}

//,{"Cache-Control: no-cache"}
interface RequestJsonPOSTService {
    @Headers({"Content-type: application/json", "Cache-Control: no-cache"})
    @POST("/{JsonFile}")
    Object doPost(@Path("JsonFile") String fileJson,
                  @Body TypedInput body,
                  @Header("X-ACCOUNT_CODE") String accountCode,
                  @Header("X-USERNAME") String username,
                  @Header("X-PASSWORD") String password,
                  @Query("finalize") String fin);
}


//JSONObject
public class PushActivity extends ActionBarActivity {

    RequestJsonService resqSrv;
    RequestJsonModel resqModel;
    ProgressDialog dialogNoInternet;
    ProgressDialog dialog;
    android.os.Handler hldNoInternet;
    ListView cmbRequests;
    Button btnPost;

    Switch swctForms;

    //    LinearLayout layoutCustom;
//    LinearLayout layoutDefault;
    EditText txtMsg;
    EditText txtUrlAd;
    EditText txtUrlEnd;
    EditText txtTag;
    TextView lblSwitch;
    Button btnPostCustom;

    public void InitActivity() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    //Do what you need before executing, Progress Bar init etc.
                    dialog = ProgressDialog.show(PushActivity.this, "",
                            getApplicationContext().getResources().getString(R.string.LoadingDialog), true);
                }

                @Override
                protected Void doInBackground(Void... params) {
                    //region obtencion del json y relleno del modelo
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setLogLevel(RestAdapter.LogLevel.FULL)
                            .setEndpoint(KEYS.HOST_URL)
                            .build();

                    resqSrv = restAdapter.create(RequestJsonService.class);
                    resqModel = resqSrv.getRequestJson(GetJsonFileName());
                    //endregion

                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    //region Paso del model relleno al adaptador y el adaptaer al combo
                    cmbRequests = (ListView) findViewById(R.id.CmbRequests);
                    RequestJsonAdapter resqAdp = new RequestJsonAdapter(PushActivity.this, resqModel.requests);
                    cmbRequests.setAdapter(resqAdp);
                    dialog.dismiss();
                    dialog = null;
                    //endregion
                }
            }.execute();

        } catch (Exception e) {
            Utils.Debug(e.toString());
        }

    }

    private String GetJsonFileName() {

        return "RequestES.json";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        txtMsg = (EditText) findViewById(R.id.TxtMsg);
        txtTag = (EditText) findViewById(R.id.TxtTag);
        txtUrlAd = (EditText) findViewById(R.id.TxtUrlAd);
        txtUrlEnd = (EditText) findViewById(R.id.TxtUrlEnd);

//region control de pestañas
        Resources res = getResources();

        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        String tabTxt1 = res.getString(R.string.lblSwctPushOFF);
        TabHost.TabSpec spec = tabs.newTabSpec("default");

        spec.setContent(R.id.LayoutDefault);
        spec.setIndicator(tabTxt1,
                res.getDrawable(android.R.drawable.ic_dialog_alert));
        tabs.addTab(spec);

        String tabTxt2 = res.getString(R.string.lblSwctPushON);
        spec = tabs.newTabSpec("custom");
        spec.setContent(R.id.LayoutCustom);
        spec.setIndicator(tabTxt2,
                res.getDrawable(android.R.drawable.ic_menu_agenda));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Utils.Debug("TabId :" + tabId);
                if(tabId.equals("custom"))
                {
                    if(txtMsg.requestFocus()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(txtMsg, InputMethodManager.SHOW_IMPLICIT);

                       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                }
                else
                {
                    //ocultar teclao
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow (txtMsg.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });
        //endregion

        /*swctForms = (Switch) findViewById(R.id.SwctPushForm);
          layoutCustom = (LinearLayout) findViewById(R.id.LayoutCustom);
          layoutDefault = (LinearLayout) findViewById(R.id.LayoutDefault);



//switch
        swctForms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    lblSwitch.setText(R.string.lblSwctPushON);
                    layoutCustom.setVisibility(View.VISIBLE);
                    layoutDefault.setVisibility(View.GONE);

                } else {
                    lblSwitch.setText(R.string.lblSwctPushOFF);
                    layoutCustom.setVisibility(View.GONE);
                    layoutDefault.setVisibility(View.VISIBLE);
                }

            }
        });
*/

        // lblSwitch = (TextView) findViewById(R.id.LblSwitch);

        btnPostCustom = (Button) findViewById(R.id.BtnPostCustom);
        btnPostCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendCustomNotification();
            }
        });


        //empieza el default
        btnPost = (Button) findViewById(R.id.BtnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendDefaultNotification();

            }
        });

        if (isOnline()) InitActivity();


        hldNoInternet = new android.os.Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (!isOnline()) {
                        if (dialogNoInternet == null || !dialogNoInternet.isShowing())
                            dialogNoInternet = ProgressDialog.show(PushActivity.this, "", getApplicationContext().getResources().getString(R.string.NoInternetDialog), true, false);


                    } else {
                        if (dialogNoInternet != null && dialogNoInternet.isShowing()) {
                            dialogNoInternet.hide();
                            dialogNoInternet.dismiss();
                            dialogNoInternet = null;
                            InitActivity();
                        }
                    }

                    hldNoInternet.postDelayed(this, 3000);
                } catch (Exception e) {
                }
            }
        };
        hldNoInternet.postDelayed(runnable, 10);

    }

    private void SendCustomNotification() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(PushActivity.this, "",
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
                            "\"expire_date\" :0," +
                            "\"payload\" : {\"tag\":\"%s\", \"url_ad\":\"%s\",\"url_end\":\"%s\"}}";

                    String Mensaje = "";
                    String Tag = "";
                    String UrlAd = "";
                    String UrlEnd = "";
                    Mensaje = txtMsg.getText().toString();
                    Tag = txtTag.getText().toString();
                    UrlAd = txtUrlAd.getText().toString();
                    UrlEnd = txtUrlEnd.getText().toString();

                    testStr = String.format(testStr, Mensaje, Tag, UrlAd, UrlEnd);

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

    public void SendDefaultNotification() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(PushActivity.this, "",
                        getApplicationContext().getResources().getString(R.string.LoadingDialog), true);
            }


            @Override
            protected Void doInBackground(Void... params) {


                RequestJsonAdapter rAdap = (RequestJsonAdapter) cmbRequests.getAdapter();
                List<String> selectNP = rAdap.SelectedRequest;

                for (int i = 0; i <= resqModel.requests.length - 1; i++) {

                    if (selectNP.contains(resqModel.requests[i].name)) {
                        //asv hacer el push con el headers y el data
                        String Appoxee_HOST = "https://saas.appoxee.com";
                        String JsonPostFile = "api/v3/message";

                        try {
                            RestAdapter restAdapter = new RestAdapter.Builder()

                                    .setLogLevel(RestAdapter.LogLevel.FULL)
                                    .setEndpoint(Appoxee_HOST)
                                    .build();


                            RequestJsonPOSTService rqtPOST = restAdapter.create(RequestJsonPOSTService.class);
                            String DataRaw = resqModel.requests[i].data.toString();//.replace("\n", "").replace("\t", "").replace(" ", "");//.replace("\"","'")
                            Utils.Debug(DataRaw);


                            TypedInput in = new TypedByteArray("application/json", DataRaw.getBytes("UTF-8"));


                            //  JSONObject jsonObject = new JSONObject(testStr);


                            Object obj = rqtPOST.doPost(JsonPostFile, in, "gigigoapps", "gigigoapps", "gigigoMadrid1", "1");

                            Utils.Debug(obj.toString());
                            //Toast.makeText(getApplicationContext(), getResources().getString(R.string.OK), Toast.LENGTH_LONG).show();
                        } catch (RetrofitError e) {
                            String json = new String(((TypedByteArray) e.getResponse().getBody()).getBytes());

                            //  Toast.makeText(getApplicationContext(), getResources().getString(R.string.NOTOK), Toast.LENGTH_LONG).show();
                            Utils.Debug("******************************************************");
                            Utils.Debug(json);
                            Utils.Debug("******************************************************");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();

                            // Toast.makeText(getApplicationContext(), getResources().getString(R.string.NOTOK), Toast.LENGTH_LONG).show();
                        }

                    }


                }


                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                dialog.dismiss();
                dialog = null;
            }
        }.execute();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_push, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.mnu_Suscription) {
            Intent goHome = new Intent(this, MainActivity.class);
            startActivity(goHome);
        }
        if (id == R.id.mnu_BtnSend) {

            TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
            if (tabs.getCurrentTab() == 0)
                SendDefaultNotification();
            else
                SendCustomNotification();


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


