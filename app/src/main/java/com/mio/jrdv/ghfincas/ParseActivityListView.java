package com.mio.jrdv.ghfincas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.mio.jrdv.ghfincas.SQL.NotificationAdapter;
import com.mio.jrdv.ghfincas.modelParse.MessageParse;

import java.util.ArrayList;

public class ParseActivityListView extends AppCompatActivity {

    //para el parse
    private ListView listView;
    private ArrayList<MessageParse> listMessages = new ArrayList<MessageParse>();

     private NotificationAdapter adapternew;




    private final String TAG = ParseActivityListView.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_activity_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarparse);
        setSupportActionBar(toolbar);
        //con esto cambiamos el tiotulod el ActionBar
        getSupportActionBar().setTitle("COMUNICADOS");

        /*
        //no uso el fab button!!!

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
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String message = intent.getStringExtra("message");
        Log.e(TAG, "Push received in ParseListActivity onNewIntent!!! :" + message);
        MessageParse m = new MessageParse(0,message, System.currentTimeMillis());
        listMessages.add(0, m);
        //adapter.notifyDataSetChanged();
        adapternew.notifyDataSetChanged();
    }


}
