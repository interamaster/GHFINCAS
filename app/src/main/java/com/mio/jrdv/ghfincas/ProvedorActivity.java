package com.mio.jrdv.ghfincas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProvedorActivity extends AppCompatActivity {

    /*In this activity, strings are retrieved from the ListViewAdapter by using Intent and sets into the TextViews and
    an image URL into ImageLoader class to load images into the ImageView.
    */

    // Declare Variables
    String telefono;
    String empresa;
    String descripcion;
    String logo;

    ImageLoader imageLoader = new ImageLoader(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_provedor);

         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//con esto cambiamos el tiotulod el ActionBar
        getSupportActionBar().setTitle("PROVEEDORES DE CONFIANZA");


        //recuepramos los valores del intent

        Intent i = getIntent();
        // Get the result of telefono
        telefono = i.getStringExtra("Telefono");
        // Get the result of empresa
        empresa = i.getStringExtra("Empresa");
        // Get the result of descripcion
        descripcion = i.getStringExtra("Descripcion");
        // Get the result of logo
        logo = i.getStringExtra("Logo");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.llamarfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();




                //String TelefonoProveedorElegido = "tel:123456789";//la declaramos public!!!



                String TelefonoProveedorElegido =("tel:"+ telefono);
                Intent intentLlamarTelefono = new Intent(Intent.ACTION_DIAL, Uri.parse(TelefonoProveedorElegido));
                startActivity(intentLlamarTelefono);

            }



        });
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);//CON ESTO QIOTADO NO SALE EL BOTN DE BACK EN BARRA ARIIBA


        // Locate the TextViews in singleitemview.xml
        TextView empresatxw = (TextView) findViewById(R.id.nombreempresa);
        TextView descripcionempresatxw = (TextView) findViewById(R.id.descripcionempresa);


        // Locate the ImageView in singleitemview.xml
        ImageView logoempresaimagen = (ImageView) findViewById(R.id.empresaLogo);

        // Set results to the TextViews
        empresatxw.setText(empresa);
        descripcionempresatxw.setText(descripcion);


        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(logo, logoempresaimagen);


    }

}
