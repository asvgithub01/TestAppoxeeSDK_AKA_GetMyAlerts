package com.gigigo.asv.testappoxeesdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.gigigo.asv.testappoxeesdk.Models.KEYS;


public class NotificationActivity extends ActionBarActivity {

    private Button BtnContinue;
    private WebView webView;
    private String strUrlAD = "";
    private String strUrlEND = "";
    //asv esto es para el timer
    private int repeticiones = 0;
    private final int maxrepeticiones = 5;
    Handler handler;
    Runnable runnable;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Intent intent = this.getIntent();

        strUrlAD = intent.getStringExtra(KEYS.AD_URL_KEY);
        strUrlEND = intent.getStringExtra(KEYS.END_URL_KEY);
        webView = (WebView) findViewById(R.id.webView);
        //asv esto es pa q chute chupi web con js
        webView.getSettings().setJavaScriptEnabled(true);
        BtnContinue = (Button) findViewById(R.id.BtnContinue);


        if (strUrlAD.equals("")) {
            BtnContinue.setVisibility(View.GONE);
            NavigateTo(strUrlEND);

        } else {
            //region asv timer 5 segundos y muestra el boton de continuar
            BtnContinue.setVisibility(View.GONE);
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {

                    repeticiones += 1;
                    if (repeticiones < maxrepeticiones)
                        handler.postDelayed(this, 1000);
                    else {
                        //asv ocultar etiquetas y textview y mostrar el boton
                        BtnContinue.setVisibility(View.VISIBLE);
                    }

                }
            };

            webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (url.equals(strUrlAD+"/"))
                        handler.postDelayed(runnable, 1);
                }

            });
            //endregion
            NavigateTo(strUrlAD);
            BtnContinue.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NavigateTo(strUrlEND);
                            BtnContinue.setVisibility(View.GONE);
                        }
                    });


        }

    }


    @Override
    public void onPause() {
        super.onPause();
        //asv esto es cuando se salga de la app si la vuelve abrir va a la pagina de seleccion de categorias
//        Intent goHome = new Intent(this, MainActivity.class);
//        startActivity(goHome);
    }

    void NavigateTo(String strURL) {
        if (isOnline())
            webView.loadUrl(strURL);
        else {
            dialog = ProgressDialog.show(this, "",
                    getApplicationContext().getResources().getString(R.string.NoInternetDialog), true, true, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            if (isOnline()) {
                                dialog.dismiss();
                                dialog = null;
                            }
                        }
                    });
        }

        return;
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
        getMenuInflater().inflate(R.menu.menu_notification, menu);
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
//        if (id == R.id.mnu_myalert) {
//            Intent goNoti = new Intent(this, NotificationActivity.class);
//            startActivity(goNoti);
//        }
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
