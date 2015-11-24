package com.mio.jrdv.ghfincas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends Activity {


    //ivars de los buttons

    //TODO numero del telefono al que se llama

    final public  String phno = "tel:673787175";

    //TODO web de ghfincas

    final public   String url = "http://www.ghfincas.es/";

    //TODO email GHFINCAS

    private static final String [] EmailTO = {"interamaster@gmail.com"};

    private ImageButton DialButton;
    private ImageButton EmailButton;
    private ImageButton WebGHFINCASButton;
    private ImageButton INCIDENCIAButton;
    private ImageButton OTHERButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainnew);


        Intent intent = new Intent(this, com.mio.jrdv.ghfincas.LoginActivity.class);
        startActivity(intent);



        //TOMAMOS LA REF DE LOS IMAGEBUTTONS:
        //1º el dial:


        DialButton =(ImageButton)findViewById(R.id.DialButton);

        //le damos su funcion al Dialbutton:



        DialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String phno = "tel:123456789";la declaramos public!!!


                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(phno));
                startActivity(i);
            }
        });


        //2º) el de la WEB:

        WebGHFINCASButton=(ImageButton)findViewById(R.id.WebButton);

        WebGHFINCASButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //este abre una web

               // String url = "http://www.ghfincas.es/";la declaramos public!!!
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



        //3º el del email de consulta


        EmailButton=(ImageButton)findViewById(R.id.EmailButton);
        EmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //este manda un email prellenado




                //recupermos los valores del SharedPRefs sis e guardaron tras el signup activity



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

                String email = pref.getString(LoginActivity.PREF_EMAIL, null);//esto devolvera el nombre si existe o null!!
                String comunidad  = pref.getString(LoginActivity.PREF_NOMBRECMUNIDAD, null);
                String Telefono  = pref.getString(LoginActivity.PREF_TELEFONO, null);
                String NombreVecino  = pref.getString(LoginActivity.PREF_NOMBREVECINO, null);


                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");


                emailIntent.putExtra(Intent.EXTRA_EMAIL, EmailTO);

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Consulta  de la comunidad:"+comunidad);
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Gracias por contactar con nosotros \n \n  recuerde que dentro de la aplicacion tambien tiene un " +
                                "formulario para poder adjuntar imagenes de una manera mas sencilla \n (aunque tambien puede  hacerlo desde aqui simplemente adjuntado" +
                                " lo que necesite como cualquier correo):\n Estos son sus datos para poder resolver sus dudas a la mayor brevedad compruebe que son correctos : \n"
                                +"nombre del Vecino(Su nombre): "+ NombreVecino + "\n" +
                                "Su Comunidad: " + comunidad +
                                "\n" +
                        "  Su telefono de Contacto(El que dio al darse de alta en aplicacion: " + Telefono +"\n \n -----------------------------------------------------\n" +
                        "  " +
                        "A continuacion Diganos su consulta e intentaremos resolverla a la mayor brevedad posible:\n" +
                        " ");


                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.d("  email", "Finished sending email");
                } catch (android.content.ActivityNotFoundException ex) {
                    //no me deja poner un toast dentro de un runnable!!!!

                    Toast.makeText(getApplicationContext(), "Lo siento su movil no esta preparado para mandar emails..", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

}


/*


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(this, com.mio.jrdv.ghfincas.LoginActivity.class);
        startActivity(intent);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
 */
