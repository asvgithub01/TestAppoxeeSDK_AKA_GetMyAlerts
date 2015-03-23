package com.gigigo.asv.testappoxeesdk;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.appoxee.utils.Utils;
import com.gigigo.asv.testappoxeesdk.Adapters.SuscriptionRecyclerAdapter;
import com.gigigo.asv.testappoxeesdk.Models.CategoriasModel;
import com.gigigo.asv.testappoxeesdk.Models.GetMyAlertsModel;
import com.gigigo.asv.testappoxeesdk.Models.KEYS;
import com.gigigo.asv.testappoxeesdk.interfaces.OnMyVHlisterner;

import java.util.Random;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

///endregion
interface CategoriasService2 {
    @GET("/{JsonFile}")
    public CategoriasModel[] getCategorias(@Path("JsonFile") String fileJson);
}

interface GetMyAlertsService2 {
    @GET("/getMyAlerts.json")
    public GetMyAlertsModel getMyAlerts();
}

public class SuscriptionActivity extends ActionBarActivity {

    RecyclerView reciclerSuscription;
    AsyncTask GetCategoriasAsync;
    GetMyAlertsModel myAlertsModel;
    CategoriasModel[] CatModel;
    SuscriptionRecyclerAdapter adapter;


    ImageView img4Anim1;
    ImageView img4Anim2;
    int lastAnim = -1;
    int lastImg = -1;

    /*NAV drawer*/
    private String[] opcionesMenu;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
   android.support.v7.app.ActionBarDrawerToggle drawerToggle;
    //private Fragment fragNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscription);
        reciclerSuscription = (RecyclerView) findViewById(R.id.lstSuscriptions);

        // use a linear layout manager
        //reciclerSuscription.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new SuscriptionRecyclerAdapter(this,new CategoriasModel[]{
                new CategoriasModel("NOTICIAS","caca","NOTICIAS"),
                new CategoriasModel("DEPORTE","caca","DEPORTE"),
                new CategoriasModel("FUTBOL","caca","FUTBOL"),
                new CategoriasModel("ECONOMIA","caca","ECONOMIA",true),
                new CategoriasModel("VIRAL","caca","VIRAL"),
                new CategoriasModel("BOLSA","caca","BOLSA"),
                new CategoriasModel("HOROSCOPO","caca","HOROSCOPO"),
                new CategoriasModel("CITAS","caca","CITAS"),
                new CategoriasModel("BUSINESS","caca","BUSINESS",true),
                new CategoriasModel("SALUD","caca","SALUD"),
                new CategoriasModel("VIDA","caca","VIDA"),
                new CategoriasModel("DISRUPTIVE","caca","DISRUPTIVE")});

        adapter.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        adapter.setOnMyVHlisterner(new OnMyVHlisterner() {
            @Override
            public void OnClickedAllItem(int idx) {

                adapter.mModel[idx].CategoriaON=!adapter.mModel[idx].CategoriaON;
                adapter.notifyItemChanged(idx);
            }

            @Override
            public void OnClickedImageItem(int idx) {

               // int idx =(int) v.getTag();
                adapter.mModel[idx].CategoriaON=!adapter.mModel[idx].CategoriaON;
                adapter.notifyItemChanged(idx);
            }
        });

         reciclerSuscription.setAdapter(adapter);
        reciclerSuscription.setLayoutManager(new GridLayoutManager(this,3));

        /*navigation Drawer*/

        opcionesMenu = new String[] {"About", "OpciónFaKE 2", "OpciónFaKE 3"};
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) ?
                        android.R.layout.simple_list_item_activated_1 :
                        android.R.layout.simple_list_item_1, opcionesMenu));


//
//        Toolbar tbl =(Toolbar)findViewById(R.id.toolbar_bottom);
//
//
//        drawerToggle = new ActionBarDrawerToggle(this,
//                drawerLayout,
//               tbl,
//                R.string.open_inbox,
//                R.string.close_icon) {
//
//            public void onDrawerClosed(View view) {
//                getSupportActionBar().setTitle("tituloSeccion");
//                ActivityCompat.invalidateOptionsMenu(SuscriptionActivity.this);
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                getSupportActionBar().setTitle("tituloApp");
//                ActivityCompat.invalidateOptionsMenu(SuscriptionActivity.this);
//            }
//        };


        //region boton animacion
        Button btn = (Button)findViewById(R.id.BtnAnim);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img4Anim1 =(ImageView)findViewById(R.id.img1);
                img4Anim2 =(ImageView)findViewById(R.id.img2);
                int[] imgRes= {R.drawable.ic_uno,
                        R.drawable.ic_dos,
                        R.drawable.ic_tres,
                        R.drawable.ic_cuatro,
                        R.drawable.ic_cinco,
                        R.drawable.ic_seis,
                        R.drawable.ic_siete,
                        R.drawable.ic_ocho,
                        R.drawable.ic_nueve,
                        R.drawable.ic_diez,
                        R.drawable.ic_once,
                        R.drawable.ic_doce};

                int[] imgAnim={
                        R.anim.top2down,
                        R.anim.down2top,
                        R.anim.left2rigth,
                        R.anim.rigth2left

                };

                //region Random Img


                Random rndImg = new Random();
                int rImg = 0;

                while (rImg == lastImg ) rImg = rndImg.nextInt(imgRes.length-1);

                //endregion
                //region Random ANimation
                Random rnd = new Random();
                int res = 0;

                while (res == lastAnim) res = rnd.nextInt(imgAnim.length-1);


//endregion

//                imgAnim[res]
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fliprigth   );

                animation.reset();
//                img4Anim1.setImageDrawable(img4Anim2.getDrawable());
//                img4Anim2.setImageResource(imgRes[rImg]);
                img4Anim2.startAnimation(animation);
                lastAnim = res;
                lastImg  = rImg;
            }
        });
//endregion
    }

    private void InitActivity() {


        //2º En EJECUTAR
        GetCategoriasAsync = new AsyncTask<Object, Void, Void>() {
            CategoriasService2 CategoriaSrv;

            @Override
            protected Void doInBackground(Object... params) {


                try{
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .setEndpoint(KEYS.HOST_URL)
                        .build();

                CategoriaSrv = restAdapter.create(CategoriasService2.class);


                    CatModel = CategoriaSrv.getCategorias("/CategoriasES.json");
                }catch (Exception e){
                    Utils.Debug(e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {


               // adapter = new SuscriptionRecyclerAdapter(SuscriptionActivity.this, CatModel);




//                adapter = new SuscriptionRecyclerAdapter(SuscriptionActivity.this.getApplicationContext(),new CategoriasModel[]{
//                new CategoriasModel("mierda","caca","NOTICIAS"),
//                new CategoriasModel("mierda","caca","DEPORTE"),
//                new CategoriasModel("mierda","caca","FUTBOL"),
//                new CategoriasModel("mierda","caca","ECONOMIA",true),
//                new CategoriasModel("mierda","caca","VIRAL"),
//                new CategoriasModel("mierda","caca","BOLSA"),
//                new CategoriasModel("mierda","caca","HOROSCOPO"),
//                new CategoriasModel("mierda","caca","CITAS"),
//                new CategoriasModel("mierda","caca","BUSINESS",true),
//                new CategoriasModel("mierda","caca","SALUD"),
//                new CategoriasModel("mierda","caca","VIDA"),
//                new CategoriasModel("mierda","caca","DISRUPTIVE")
//
//        });


              ((SuscriptionRecyclerAdapter) reciclerSuscription.getAdapter())

                        .setData(new CategoriasModel[]{
                        new CategoriasModel("NOTICIAS","caca","NOTICIAS"),
                        new CategoriasModel("DEPORTE","caca","DEPORTE"),
                        new CategoriasModel("FUTBOL","caca","FUTBOL"),
                        new CategoriasModel("ECONOMIA","caca","ECONOMIA",true),
                        new CategoriasModel("VIRAL","caca","VIRAL"),
                        new CategoriasModel("BOLSA","caca","BOLSA"),
                        new CategoriasModel("HOROSCOPO","caca","HOROSCOPO"),
                        new CategoriasModel("CITAS","caca","CITAS"),
                        new CategoriasModel("BUSINESS","caca","BUSINESS",true),
                        new CategoriasModel("SALUD","caca","SALUD"),
                        new CategoriasModel("VIDA","caca","VIDA"),
                        new CategoriasModel("DISRUPTIVE","caca","DISRUPTIVE")});

              //  reciclerSuscription.setLayoutManager(new LinearLayoutManager(SuscriptionActivity.this.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//               reciclerSuscription.setAdapter(adapter);
//                reciclerSuscription.getAdapter().notify();
//            // reciclerSuscription.setLayoutManager(new GridLayoutManager(SuscriptionActivity.this.getApplicationContext(),3 ));
                /**/
                //adapter=  reciclerSuscription.getAdapter();

//adapter.notify();

            }

            @Override
            protected void onPreExecute() {

            }

        };
        /*
        //1º en EJECUTAR region obtener el JSON de Version
        new AsyncTask<Void, Void, Void>() {
            GetMyAlertsService2 GetMyAlertsSrv;
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

                GetMyAlertsSrv = restAdapter.create(GetMyAlertsService2.class);
                myAlertsModel = GetMyAlertsSrv.getMyAlerts();
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                GetCategoriasAsync.execute();
            }
        }.execute();*/
        //endregion
       GetCategoriasAsync.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suscription, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

