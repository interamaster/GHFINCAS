package com.mio.jrdv.ghfincas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.mio.jrdv.ghfincas.SQL.GHFINCASDataBaseHelper;
import com.mio.jrdv.ghfincas.SQL.NotificationAdapter;
import com.mio.jrdv.ghfincas.modelParse.MessageParse;

import java.util.ArrayList;

public class ParseActivityListView extends AppCompatActivity {

    //para el parse
    private ListView listView;
    private ArrayList<MessageParse> listMessages = new ArrayList<MessageParse>();

     private NotificationAdapter adapternew;

    GHFINCASDataBaseHelper dbhandler;



    private final String TAG = ParseActivityListView.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_activity_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarparse);
        setSupportActionBar(toolbar);
        //con esto cambiamos el tiotulod el ActionBar
        getSupportActionBar().setTitle("COMUNICADOS");


        //SQL

        //iniciamos
        dbhandler=new GHFINCASDataBaseHelper(this);

        //recuperamos los gudados en el array
        listMessages=dbhandler.getAllNotifications();




        SharedPreferences pref = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);




        /*

        estos son los vaslores guarados en el signup activity tras el ok

        edit.putString(LoginActivity.PREF_NOMBREVECINO, name);
        edit.putString(LoginActivity.PREF_EMAIL, email);
        edit.putString(LoginActivity.PREF_NOMBRECMUNIDAD, comunidad);
        edit.putString(LoginActivity.PREF_TELEFONO, telefono);
        edit.putString(LoginActivity.PREF_PASSWORD, decryptedpassword);

        edit.putBoolean(LoginActivity.PREF_BOOL_LOGINYAOK,true);

        */

        //solo si no hay ninguno!!!

        if (listMessages.size()<1) {



        String nombrevecino = pref.getString(LoginActivity.PREF_NOMBREVECINO, null);//esto devolvera el nombre si existe o null!!


        //TODO quitar luego :prerellenamos el array:





        MessageParse mensajenicial=new MessageParse(0,"Aqui ira recibiendo comunicados o bien de su comunidad o exclusivos para Usted: "+nombrevecino, System.currentTimeMillis());


        listMessages.add(0,mensajenicial);

            //y en al SQL

            dbhandler.addNotification(mensajenicial);


        }


        /*

        MessageParse mensajeprueba2=new MessageParse(0,"inicial desde Adapter2",787878989);


        listMessages.add(0,mensajeprueba2);



        MessageParse mensajeprueba3=new MessageParse(0,"inicial desde Adapter 33",787878989);


        listMessages.add(0,mensajeprueba3);
*/
        /*
        //no uso el fab button EE!!!

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);//CON ESTO QIOTADO NO SALE EL BOTN DE BACK EN BARRA ARIIBA
        */

        /*
        MessageParse mensajeprueba=new MessageParse(0,"inicial desde ParseListView",787878989);


        listMessages.add(0, mensajeprueba);

        */
        //referencias la listview

        listView = (ListView) findViewById(R.id.list_view_parse);
       // adapter = new MessageAdapterParse(this,listMessages);



        //si no esta activa est activity o pillamos por aqui

        Intent intent=getIntent();

        String message = intent.getStringExtra("message");

        if(message!= null){


            Log.e(TAG, "Push received in ParseListActivity oncreate :" + message);
            MessageParse m = new MessageParse(0,message, System.currentTimeMillis());
            listMessages.add(0, m);

            //en vez de al Arraylist al SQL

            dbhandler.addNotification(m);

            listMessages=dbhandler.getAllNotifications();

            //y vuelvo a actualzair el arraylist


            //adapter.notifyDataSetChanged();
         // adapternew.notifyDataSetChanged();
            //en vez de esto que NO hace nada:

            //adapternew.updateList(listMessages);

        }

        //El adapter o creasmo despues SI NO NO ACTULIZA!!!

        //probamos con otro adapter:

        adapternew=new NotificationAdapter(this,this,listMessages);


        //  listView.setAdapter(adapter);

        listView.setAdapter(adapternew);


    }

    /*
    Here onNewIntent() method will be called whenever a new push message is received.
     We’ll add the new message to list data source and refresh the list view.
     ESTO NUNCA SE LLAMA!!!!AL MENOS EN 5.1.1
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String message = intent.getStringExtra("message");
        Log.e(TAG, "Push received in ParseListActivity onNewIntent!!! :" + message);
        MessageParse m = new MessageParse(0,message, System.currentTimeMillis());
        listMessages.add(0, m);

        //en vez de al Arraylist al SQL

        dbhandler.addNotification(m);

        listMessages=dbhandler.getAllNotifications();

    }


}
