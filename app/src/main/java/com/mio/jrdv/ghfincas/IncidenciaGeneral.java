package com.mio.jrdv.ghfincas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class IncidenciaGeneral extends AppCompatActivity {

    //TODO numero del telefono al que se llama

    final public  String phno = "tel:673787175";
    //para evitar un doble click rapido

    private long mLastClickTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencia_general);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //con esto cambiamos el tiotulod el ActionBar
        getSupportActionBar().setTitle("INCIDENCIAS");


       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);//sin esto no sale botn atras

    }

    public void TelefonoPulsadoGenral(View view) {

        /////////////para evitar dobles clicks rapidos //////////////
///////////////////////////////////////////////////////////////////


        // mis -clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // do your magic here . . . .


        //que vibre al pulsar
        view.performHapticFeedback(1, 2);

        //String phno = "tel:123456789";la declaramos public!!!


        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(phno));
        startActivity(i);


    }

    public void EmailGenralPulsado(View view) {

        /////////////para evitar dobles clicks rapidos //////////////
///////////////////////////////////////////////////////////////////


        // mis -clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // do your magic here . . . .


        //que vibre al pulsar

        view.performHapticFeedback(1, 2);

        //Y ahora a que abra la nueva pagina

        //Ahora mejotr la nueva IncidenciaSinFotoASctibvity

        Intent intentIncidencia =new Intent(this,InicidenciaSinFotoActivity.class);
        startActivity(intentIncidencia);


    }
}
