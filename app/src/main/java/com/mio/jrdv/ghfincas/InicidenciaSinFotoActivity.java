package com.mio.jrdv.ghfincas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InicidenciaSinFotoActivity extends AppCompatActivity {


    private EditText TituloText,DescripcionText;
    private static final String TAG = "InicidenciaSinFotoActiv";

    private boolean SendEmailOK=false;//PARASABER SI SE MANDO OK  O NO EL AUTO EMAIL

    //PARA PODER HIDE LA PROGRESSBAR DESDE EL ASYNTASK
     private  ProgressDialog progressDialog;

    private static final String [] EmailTO = {"incidenciasghfincas@gmail.com"};//A donde se enviara el correo si es MANUAL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicidencia_sin_foto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //con esto cambiamos el tiotulod el ActionBar
        getSupportActionBar().setTitle("ENVIO INCIDENCIA POR EMAIL");


        //referencia a los Edittext

        TituloText= (EditText)findViewById(R.id.TituloText);
        DescripcionText=(EditText)findViewById(R.id.DescripcionText);



        //FLOATING  de EMAIL:

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(view, "Preparar Email para enviar?", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar1 = Snackbar.make(view, "Preparando Email!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();


                                //esperamos 2 segs

                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {


                                               // finish();
                                                //TODO aqui habria que preparar le email con los datos!!!


                                                progressDialog = new ProgressDialog(InicidenciaSinFotoActivity.this,  R.style.AppTheme_Dark_Dialog);
                                                progressDialog.setIndeterminate(true);
                                                progressDialog.setMessage("Generando  email encriptado para enviar a servidor!!");
                                                progressDialog.show();


                                                //Ejecutamps el asyntask para envio auto del email:

                                                new SendMail().execute("");




                                            }
                                        }, 2000);


                            }
                        });

                snackbar.show();


            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);//con esto se poen o quita el botonde atras en la barra de arriba

    }


    public void SendEmailInBackgroundMio(){

        //sacamos los textos de Edittext:

        String titulo=TituloText.getText().toString();
        String Descripcion=DescripcionText.getText().toString();


        //Sacamos los valores de las Preferecias del Vecino:

        SharedPreferences pref = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
                                                /*
                                                public static final String PREFS_NAME = "MyPrefsFile";
                                                public static final String PREF_EMAIL = "email";//sera el user name
                                                public static final String PREF_PASSWORD = "password";
                                                public static final String PREF_TELEFONO = "telefono";
                                                public static final String PREF_NOMBRECMUNIDAD = "nombreComunidad";
                                                public static final String PREF_NOMBREVECINO = "nombreVecino";
                                                public static final String PREF_BOOL_LOGINYAOK ="false";
                                                */

        String email = pref.getString(LoginActivity.PREF_EMAIL, null);//esto devolvera el nombre si existe o null!!
        String telefono = pref.getString(LoginActivity.PREF_TELEFONO, null);
        String nombreComunidad = pref.getString(LoginActivity.PREF_NOMBRECMUNIDAD, null);
        String nombreVecino = pref.getString(LoginActivity.PREF_NOMBREVECINO, null);




        Log.d(TAG, "email:" + email + " y  telefono:" + telefono + "y nombre comunidad:"+nombreComunidad +" y nombre Vecino:"+nombreVecino);


        //en vez de asi..automatico

        Mail m = new Mail("jrdvsoftyopozi@gmail.com", "sevilla4");

        //String[] toArr = {"jrdvsoftyopozi@gmail.com"};
        //new Email::

        String[] toArr = {"incidenciasghfincas@gmail.com"};

        m.setTo(toArr);
        m.setFrom("jrdvsoftyopozi@gmail.com");
        m.setSubject("Nueva Inicidencia en Comunidad:"+nombreComunidad);
        m.setBody("Gracias por enviarnos su email estos son sus datos:\n" + email + "\n" + telefono + "\n" + nombreComunidad +
                        "\n" + nombreVecino+ "\n"+ "\n"+ "\n"+ "\n"+"-----------------------------------------"+ "\n"+ "\n"+
        "ESTE ES EL TITULO DE LA INCIDENCIA:"+ "\n"+titulo+ "\n"+ "\n"+"Y ESTA LA DESCRIPCION:"+ "\n"+ "\n"+Descripcion);
        try {
            // m.addAttachment("/sdcard/bday.jpg");
            if(m.send()) {
                //Toast.makeText(this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                //al hacerlo en backgrousd nos e pueden poner Toast!!!
                //asin que pongo la ivar a true y ya en el postexecute del asyntask que lo ponga!!!

                SendEmailOK=true;

            } else {

                //al hacerlo en backgrousd nos e pueden poner Toast!!!
                //asin que pongo la ivar a false  y ya en el postexecute del asyntask que lo ponga!!!

                SendEmailOK=false;


                //Toast.makeText(this, "Email was not sent.", Toast.LENGTH_LONG).show();
                // Toast.makeText(this, "Lo siento su movil no esta preparado para mandar emails de manera autoamtica vams ahcerlo de manera manual" +
                //   "", Toast.LENGTH_SHORT).show();

                ManualEmailSiFallaAutomatico();

            }
        } catch(Exception e) {
            Log.e("MailApp", "Could not send email", e);
        }





    }

    public void ManualEmailSiFallaAutomatico(){

        //sacamos los textos de Edittext:

        String titulo=TituloText.getText().toString();
        String Descripcion=DescripcionText.getText().toString();


        //Sacamos los valores de las Preferecias del Vecino:

        SharedPreferences pref = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
                                                /*
                                                public static final String PREFS_NAME = "MyPrefsFile";
                                                public static final String PREF_EMAIL = "email";//sera el user name
                                                public static final String PREF_PASSWORD = "password";
                                                public static final String PREF_TELEFONO = "telefono";
                                                public static final String PREF_NOMBRECMUNIDAD = "nombreComunidad";
                                                public static final String PREF_NOMBREVECINO = "nombreVecino";
                                                public static final String PREF_BOOL_LOGINYAOK ="false";
                                                */

        String email = pref.getString(LoginActivity.PREF_EMAIL, null);//esto devolvera el nombre si existe o null!!
        String telefono = pref.getString(LoginActivity.PREF_TELEFONO, null);
        String nombreComunidad = pref.getString(LoginActivity.PREF_NOMBRECMUNIDAD, null);
        String nombreVecino = pref.getString(LoginActivity.PREF_NOMBREVECINO, null);




        Log.d(TAG, "email:" + email + " y  telefono:" + telefono + "y nombre comunidad:"+nombreComunidad +" y nombre Vecino:"+nombreVecino);




                  /*
                //enviar solo por email!!!

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                */


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("message/rfc822");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, EmailTO);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Nueva Solicitud alta GHFINCAS  APK");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Gracias por enviarnos su email estos son sus datos:\n" + email + "\n" + telefono + "\n" + nombreComunidad +
                "\n" + nombreVecino+ "\n"+ "\n"+ "\n"+ "\n"+"-----------------------------------------"+ "\n"+ "\n"+
                "ESTE ES EL TITULO DE LA INCIDENCIA:"+ "\n"+titulo+ "\n"+ "\n"+"Y ESTA LA DESCRIPCION:"+ "\n"+ "\n"+Descripcion);


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.d("  email", "Finished sending email");
        } catch (android.content.ActivityNotFoundException ex) {
            //no me deja poner un toast dentro de un runnable!!!!

            Toast.makeText(this, "Lo siento su movil no esta preparado para mandar emails..", Toast.LENGTH_SHORT).show();
        }

        finish();


    }



    private class SendMail extends AsyncTask<String, Integer, Void> {


        protected void onProgressUpdate() {
            //called when the background task makes any progress
        }

        @Override
        protected Void doInBackground(String... params) {
            SendEmailInBackgroundMio();
            return null;
        }

        protected void onPreExecute() {
            //called before doInBackground() is started
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (SendEmailOK) {
                //funciono ok

                progressDialog.hide();
                Toast.makeText(InicidenciaSinFotoActivity.this, "Su email se envio correctamente!!", Toast.LENGTH_LONG).show();

                finish();


            }

            else {

                //ha fallado

                Toast.makeText(InicidenciaSinFotoActivity.this, "Email fallo, Por Favor envie  Manual!!!", Toast.LENGTH_LONG).show();
            }


        }
    }


}
