package com.gigigo.asv.testappoxeesdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ListView;

import com.appoxee.Appoxee;
import com.appoxee.AppoxeeManager;
import com.appoxee.AppoxeeObserver;
import com.appoxee.asyncs.initAsync;
import com.appoxee.utils.Utils;
import com.gigigo.asv.testappoxeesdk.Adapters.CategoriasAdapter;
import com.gigigo.asv.testappoxeesdk.Models.CategoriasModel;
import com.gigigo.asv.testappoxeesdk.Models.GetMyAlertsModel;
import com.gigigo.asv.testappoxeesdk.Models.KEYS;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;


//ASV Option A: Appoxee Explicit Integration (with Observer) :
//public class MainActivity extends AppoxeeBaseActivity implements AppoxeeObserver
public class MainActivity extends ActionBarActivity implements AppoxeeObserver {


    ///Region VARIABLES

    ListView lstSuscriptions2;
    Button btnAppTags;
    Button btnDevTags;
    Button btnSetDevTags;
    Button btnPush;


    AsyncTask GetCategoriasAsync;
    AsyncTask SelectCatAsync;


    ProgressDialog dialog;
    ProgressDialog dialogNoInternet;
    android.os.Handler hldNoInternet;
    //asv variables de las AsyncTask
    GetMyAlertsModel myAlertsModel;
    CategoriasModel[] CatModel;

    ///endregion
    interface CategoriasService {
        @GET("/{JsonFile}")
        public CategoriasModel[] getCategorias(@Path("JsonFile") String fileJson);
    }

    interface GetMyAlertsService {
        @GET("/getMyAlerts.json")
        public GetMyAlertsModel getMyAlerts();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //asv forzar a que salgan los 3 puntos dle menu aunk el terminal tenga boton menu fisico
        getOverflowMenu();

        if (isOnline()) InitActivity();

        hldNoInternet = new android.os.Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (!isOnline()) {
                        if (dialogNoInternet == null || !dialogNoInternet.isShowing())
                            dialogNoInternet = ProgressDialog.show(MainActivity.this, "", getApplicationContext().getResources().getString(R.string.NoInternetDialog), true, false);


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
        //endregion


    }

    public void InitActivity() {


        dialog = ProgressDialog.show(this, "",
                getApplicationContext().getResources().getString(R.string.LoadingDialog), true);


        //region Conexion con el Appoxee

        new initAsync(getApplicationContext(), "54e214dae12d63.34253343",
                "54e214dae12f23.08257699", "com.package.MainActivity", true, this).execute();
        AppoxeeManager.setDebug(true);


        Appoxee.parseExtraFields(getIntent().getExtras());
        setContentView(R.layout.activity_main);

        //endregion

        //region Recuperacion de los controles de la vista
        btnAppTags = (Button) findViewById(R.id.BtnAppTags);
        btnDevTags = (Button) findViewById(R.id.BtnDeviceTags);
        btnSetDevTags = (Button) findViewById(R.id.BtnSetDeviceTags);
        btnPush = (Button) findViewById(R.id.BtnPush);
        lstSuscriptions2 = (ListView) findViewById(R.id.LstSuscripciones2);
        //endregion

        //region Tarea Asincrona para traernos la info de las tags seleccionadas por el device
        //3º En EJECUTAR
        SelectCatAsync = new AsyncTask<Object, Void, Void>() {
            ArrayList<String> deviceTags;

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected Void doInBackground(Object... params) {
                //asv sacamos los tags del Device
                deviceTags = Appoxee.getDeviceTags();
                Utils.Debug("Movil Tag List = " + deviceTags);
                if (deviceTags == null)
                    deviceTags = new ArrayList<String>();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                //Do What you need with the Data, Dismiss Progress Bar etc.
                if (deviceTags != null) {

                    CategoriasAdapter catAdp = (CategoriasAdapter) lstSuscriptions2.getAdapter();

                    for (int i = 0; i < catAdp.model.length - 1; i++) {

                        if (deviceTags.contains(catAdp.model[i].CategoriaTAG))
                            catAdp.model[i].CategoriaON = true;
                        else
                            catAdp.model[i].CategoriaON = false;

                    }
                    lstSuscriptions2.setAdapter(catAdp);
                    dialog.dismiss();
                    dialog = null;
                }


            }

        };
        //2º En EJECUTAR
        GetCategoriasAsync = new AsyncTask<Object, Void, Void>() {
            CategoriasService CategoriaSrv;

            @Override
            protected Void doInBackground(Object... params) {

                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .setEndpoint(KEYS.HOST_URL)
                        .build();

                CategoriaSrv = restAdapter.create(CategoriasService.class);
                CatModel = CategoriaSrv.getCategorias(GetJsonFileName());




                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                CategoriasAdapter catAdp = new CategoriasAdapter(MainActivity.this, CatModel);
                lstSuscriptions2.setAdapter(catAdp);
                SelectCatAsync.execute();
            }

            @Override
            protected void onPreExecute() {

            }

        };
        //1º en EJECUTAR region obtener el JSON de Version
        new AsyncTask<Void, Void, Void>() {
            GetMyAlertsService GetMyAlertsSrv;

            @Override
            protected void onPreExecute() {
                //Do what you need before executing, Progress Bar init etc.
            }

            @Override
            protected Void doInBackground(Void... params) {
                //asv recuperamos el json de la version y los idiomas
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .setEndpoint(KEYS.HOST_URL)
                        .build();

                GetMyAlertsSrv = restAdapter.create(GetMyAlertsService.class);
                myAlertsModel = GetMyAlertsSrv.getMyAlerts();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                GetCategoriasAsync.execute();
            }
        }.execute();
        //endregion

        //region OnClick de los Botones
        btnAppTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Appoxee.isReady()) {

                    GetAllTagsAsync getallTagsasync = new GetAllTagsAsync();
                    getallTagsasync.execute("");
                }
            }
        });

        btnSetDevTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {
                    ArrayList<String> arrTags;

                    @Override
                    protected Void doInBackground(Void... params) {

//                        CategoriasAdapter catAdp = (CategoriasAdapter) lstSuscriptions2.getAdapter();
//                        arrTags = new ArrayList<String>();
//                        for (int i = 0; i < catAdp.model.length - 1; i++) {
//                            System.out.println(catAdp.model[i].CategoriaLbl);
//
//                            if (catAdp.model[i].CategoriaON)
//                                arrTags.add(0, catAdp.model[i].CategoriaTAG);
//                        }
                        Appoxee.addCustomFieldToRegistration("Campichi", "algoValgo");
                        String appxID = Appoxee.getAppoxeeAppID();
                        String AppxDevAlias = Appoxee.getDeviceAlias();
                        Appoxee.setDeviceAlias("maderFaker");


                      //  Appoxee.getTagList()
                        //Appoxee.setClient();
//                        Appoxee.setCustomField()


                        //Appoxee.addTagsToDevice(arrTags);
                        return null;
                    }
                }.execute();
            }
        });

        btnDevTags.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              new AsyncTask<Void, Void, Void>() {
                                                  ArrayList<String> deviceTags;

                                                  @Override
                                                  protected void onPreExecute() {
                                                      //Do what you need before executing, Progress Bar init etc.

                                                  }

                                                  @Override
                                                  protected Void doInBackground(Void... params) {
                                                      // get all tags
                                                      deviceTags = Appoxee.getDeviceTags();
                                                      Utils.Debug("Got Device Tag List = " + deviceTags);
                                                      if (deviceTags == null)
                                                          deviceTags = new ArrayList<String>();
                                                      return null;
                                                  }

                                                  @Override
                                                  protected void onPostExecute(Void result) {
                                                      //Do What you need with the Data, Dismiss Progress Bar etc.
                                                      if (deviceTags != null) {

                                                          CategoriasAdapter catAdp = (CategoriasAdapter) lstSuscriptions2.getAdapter();

                                                          for (int i = 0; i < catAdp.model.length - 1; i++) {

                                                              if (deviceTags.contains(catAdp.model[i].CategoriaTAG))
                                                                  catAdp.model[i].CategoriaON = true;
                                                              else
                                                                  catAdp.model[i].CategoriaON = false;

                                                          }
                                                          lstSuscriptions2.setAdapter(catAdp);

                                                      }


                                                  }
                                              }
                                                      .execute();
                                          }
                                      }

        );
        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goNP = new Intent(getApplicationContext(), PushActivity.class);
                startActivity(goNP);
            }
        });
        //endregion
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    ///todo aqui hay que pillar el fichero del json de version
    private String GetJsonFileName() {

        String strJsonResult = "";
        int max = myAlertsModel.Idiomas.length - 1;
        int idx = 0;
        //region determinamos random el index del jsn q vamos a sacar
//        int min = 0;
//        Random r = new Random();
//        int idx = r.nextInt(max - min + 1) + min;
        //endregion

        Resources res = getApplicationContext().getResources();
        Configuration conf = res.getConfiguration();
        String deviceLangCode = conf.locale.getLanguage();


        for (int i = 0; i <= max; i++) {

            if (myAlertsModel.Idiomas[i].idioma.toLowerCase().equals(deviceLangCode)) {
                idx = i;
                break;
            }
        }
        //asv si no lo encuentra idx vale 0
        strJsonResult = myAlertsModel.Idiomas[idx].urlJson;

        return strJsonResult;
        // return "CategoriasEN.json";
    }


    //region Menu

    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
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
//endregion

    private void updateMsgBadge() {
        Utils.Debug("***ASV***updateMsgBadgeNO HAGO NA");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // If Inbox implemented and you have a method for updating your inbox’s
        // Badge
        updateMsgBadge();
    }

    @Override
    public void onRegistrationCompleted() {
        Utils.Debug("***ASV***Can perform all API Calls");
    }

    @Override
    public void onMessagesUpdateCompleted() {
        Utils.Debug("***ASV***Can update Badge");
        updateMsgBadge();
    }
//region chorradaca de analitycs

    @Override
    protected void onStart() {
        try {
            super.onStart();
        } catch (Exception e) {
            Utils.Debug(e.toString());
        }

        if (isOnline()) {
            Appoxee.onStart();
        }
    }

    @Override
    protected void onStop() {
        try {
            super.onStop();

        } catch (Exception e) {
            Utils.Debug(e.toString());
        }
        if (isOnline()) {
            Appoxee.onStop();
        }
    }
    //endregion

}

//region AsyncTaks de los clicks de los botones
class GetAllTagsAsync extends AsyncTask<String, String, String> {
    ArrayList<String> allTags;

    @Override
    protected String doInBackground(String... params) {

        allTags = Appoxee.getTagList();

        if (allTags == null)
            allTags = new ArrayList<String>();

        return null;

    }

    @Override
    protected void onPostExecute(String result) {
        Utils.Debug("*******************************************************");
        Utils.Debug("AllTags: " + allTags);
        Utils.Debug("*******************************************************");
    }
}
//endregion
