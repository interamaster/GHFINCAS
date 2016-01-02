package com.mio.jrdv.ghfincas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


//V09 casi tutto ok 16/12/15
//V0966 AÑADIDO chequeo de google play services
//V0.98 AÑADIDA AYUDA INICIAL 2 VECES
//v.0989 añadido huevo von 7 toques y boton de ayuda

public class MainActivity extends Activity  implements OnShowcaseEventListener {


    //ivars de los buttons

    //TODO numero del telefono al que se llama

    final public  String phno = "tel:673787175";

    //TODO web de ghfincas

    final public   String url = "http://www.ghfincas.es/";

    //TODO web de ghfincas Comunidad

    final public   String urlcomunidad = "http://www.ghfincas.es/oficina-virtual";


    //TODO email GHFINCAS

    private static final String [] EmailTO = {"interamaster@gmail.com"};

    //para evitar un doble click rapido

    private long mLastClickTime = 0;



    //private ImageButton DialButton;
   // private ImageButton EmailButton;
    //private ImageButton WebGHFINCASButton;
    //private ImageButton INCIDENCIAButton;
    //private ImageButton OTHERButton;


    //para el map  para el huevo de pascua:

    private ImageView LogoPulsar;
    private boolean mClicked = false;

//para el logging

    private static String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.mainnew);




        SharedPreferences pref = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
         boolean alreadyElegidoNoActualizrGooglePlay =  pref.getBoolean(LoginActivity.PREF_INFO_GOOGLEPLAYSERVICES, false);



        //lo primero chequeamos los google play services!!!

        //LUEGO HABRA QUE CHEQUEAR SI ESTAN OK LAS GOOGLE PLAY SERVICES!!!!!!

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(status == ConnectionResult.SUCCESS || alreadyElegidoNoActualizrGooglePlay) {
            //Success! Do what you want
            Log.e(TAG, "Google Services OK!!!!");

            Intent intent = new Intent(this, com.mio.jrdv.ghfincas.LoginActivity.class);
            startActivity(intent);

            //la ponemos mejor en gris:

            setContentView(R.layout.mainnewgrisabajo);

            //  Intent intent = new Intent(this, com.mio.jrdv.ghfincas.LoginActivity.class);
            //  startActivity(intent);


            //para el map

            LogoPulsar=(ImageView)findViewById(R.id.logoMainPulsar);




        }

        else {



            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

            // Setting Dialog Title
            alertDialog.setTitle("CONTINUAR SIN RECIBIR COMUNICADOS!!!");

            // Setting Dialog Message
            alertDialog.setMessage("Para poder recibir comunicados necesitamos los Google Play Services\n Si quieres continuar sin instalarlos pulsa SI \n (pero recuerda que no te" +
                    " llegaran NUNCA los comunicados que enviemos a tu comunidad o a ti en particular)\n Si quieres actualizarlos pulsa en NO \n y te reenviaremos a Google Play para qe  los instales..." +
                    "\n Una vez los tengas instalados vuelve a abrir GHFINCAS");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.logo7redondo);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {

                    // Write your code here to invoke YES event
                    SharedPreferences pref = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);


                    // We need an editor object to make changes
                    SharedPreferences.Editor edit = pref.edit();


                    edit.putBoolean( LoginActivity.PREF_INFO_GOOGLEPLAYSERVICES,true);



                    // Commit the changes
                    edit.commit();


                    //arrancams normal:

                    Log.e(TAG, "Google Services OK!!!!");

                    Intent intent = new Intent(MainActivity.this, com.mio.jrdv.ghfincas.LoginActivity.class);
                    startActivity(intent);

                    //la ponemos mejor en gris:

                    setContentView(R.layout.mainnewgrisabajo);

                    //  Intent intent = new Intent(this, com.mio.jrdv.ghfincas.LoginActivity.class);
                    //  startActivity(intent);


                    //para el map

                    LogoPulsar=(ImageView)findViewById(R.id.logoMainPulsar);

                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event

                    dialog.cancel();



                    Log.e(TAG, "Google Services MAL!!!!");

                    int requestCode = 1;
                    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(MainActivity.this);
                    Dialog dialogGooglePlay = GooglePlayServicesUtil.getErrorDialog(status,MainActivity.this , requestCode);
                    dialogGooglePlay.show();

                   // finish();
                }
            });

            // Showing Alert Message
            alertDialog.show();


        }




//*****************************************************************************************
//***************************HELP INTRO!!!!!**************************************
//*****************************************************************************************
//*****************************************************************************************



        int nuemArranuesParaayuda=pref.getInt(LoginActivity.PREF_NUMERO_DEARRANQUES, 1);

        if (nuemArranuesParaayuda <=1 ) {

            //solo lo hara las 3 primeras veces!!!


            Target viewTarget = new ViewTarget(R.id.IncidenciaButton, this);
            ShowcaseView sc = new ShowcaseView.Builder(this)
                    .setTarget(viewTarget)
                    .setContentTitle("INCIDENCIA")
                    .setContentText("AL PULSAR ESTE BOTON NOS PODRA  INFORMAR SOBRE CUALQUIER INCIDENCIA O BIEN POR EMAIL O POR TELEFONO(24H)")
                    //singleShot(42)
                    .setShowcaseEventListener(this)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .replaceEndButton(R.layout.view_custom_button)

                    .blockAllTouches()

                    .build();

            sc.setTag(2);

        }




        //*****************************************************************************************
        //*****************************************************************************************
        //ponemos un listener al LogoPulsar Para el huevo de Pascua!!!
        //http://stackoverflow.com/questions/27757099/android-detect-doubletap-and-tripletap-on-view
        // *****************************************************************************************
        //*****************************************************************************************




        LogoPulsar.setOnTouchListener(new View.OnTouchListener() {
            Handler handler = new Handler();

            int numberOfTaps = 0;
            long lastTapTimeMs = 0;
            long touchDownMs = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchDownMs = System.currentTimeMillis();

                        //que vibre
                        v.performHapticFeedback(1, 2);

                        break;
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacksAndMessages(null);

                        if ((System.currentTimeMillis() - touchDownMs) > ViewConfiguration.getTapTimeout()) {
                            //it was not a tap

                            numberOfTaps = 0;
                            lastTapTimeMs = 0;
                            break;
                        }

                        if (numberOfTaps > 0
                                && (System.currentTimeMillis() - lastTapTimeMs) < ViewConfiguration.getDoubleTapTimeout()) {
                            numberOfTaps += 1;
                        } else {
                            numberOfTaps = 1;
                        }

                        lastTapTimeMs = System.currentTimeMillis();

                        if (numberOfTaps == 7) {
                            Toast.makeText(getApplicationContext(), "PREMIO PARA EL CABALLERO!!", Toast.LENGTH_LONG).show();
                            //handle seven taps

                            LogoPulsar.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            LogoPulsar.setBackgroundColor(getResources().getColor(R.color.black));


                            LogoPulsar.setImageResource(R.drawable.foto_huevo1);

                        }


                        else if (numberOfTaps == 2) {

                            if(mClicked) {
                                //ic1.setBackground(ic1dark);

                                LogoPulsar.setScaleType(ImageView.ScaleType.FIT_XY);


                                LogoPulsar.setImageResource(R.drawable.plano_ghfincas_2);
                                //LogoPulsar.setScaleType(ImageView.ScaleType.FIT_CENTER);//lo ponemos antes
                            }
                            else {
                                //ic1.setBackground(ic1light);

                                LogoPulsar.setScaleType(ImageView.ScaleType.FIT_CENTER);

                                LogoPulsar.setImageResource(R.drawable.logo7);

                                //este lo poemos que ocupe toda lapantala

                                // LogoPulsar.setScaleType(ImageView.ScaleType.FIT_XY);//lo pnemos antes

                            }

                            mClicked = !mClicked;


                        }
                }
                return true;
            }
        });



            //la ponemos mejor en gris:

      //  setContentView(R.layout.mainnewgrisabajo);

      //  Intent intent = new Intent(this, com.mio.jrdv.ghfincas.LoginActivity.class);
      //  startActivity(intent);


        //para el map

     //   LogoPulsar=(ImageView)findViewById(R.id.logoMainPulsar);



        //TOMAMOS LA REF DE LOS IMAGEBUTTONS:
        //1º el dial:
/*      //lo hago mejor con un onclick

        //DialButton =(ImageButton)findViewById(R.id.DialButton);

        //le damos su funcion al Dialbutton:



        DialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /////////////para evitar dobles clicks rapidos //////////////
///////////////////////////////////////////////////////////////////


                // mis -clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                // do your magic here . . . .


                //que vibre al pulsar
                v.performHapticFeedback(1, 2);

                //String phno = "tel:123456789";la declaramos public!!!


                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(phno));
                startActivity(i);
            }
        });

*/
        //2º) el de la WEB:!!!no l hago desde inclick
        /*

        WebGHFINCASButton=(ImageButton)findViewById(R.id.WebButton);

        WebGHFINCASButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /////////////para evitar dobles clicks rapidos //////////////
///////////////////////////////////////////////////////////////////


                // mis -clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                // do your magic here . . . .

                //que vibre al pulsar
                v.performHapticFeedback(1, 2);


                //este abre una web

               // String url = "http://www.ghfincas.es/";la declaramos public!!!
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);



            }
        });

*/

        //3º el del email de consulta

/*
        EmailButton=(ImageButton)findViewById(R.id.EmailButton);
        EmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /////////////para evitar dobles clicks rapidos //////////////
///////////////////////////////////////////////////////////////////


                // mis -clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                // do your magic here . . . .

                //que vibre al pulsar
                v.performHapticFeedback(1, 2);

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
/*
                String email = pref.getString(LoginActivity.PREF_EMAIL, null);//esto devolvera el nombre si existe o null!!
                String comunidad  = pref.getString(LoginActivity.PREF_NOMBRECMUNIDAD, null);
                String Telefono  = pref.getString(LoginActivity.PREF_TELEFONO, null);
                String NombreVecino  = pref.getString(LoginActivity.PREF_NOMBREVECINO, null);

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

                /*

                String recepientEmail = ""; // either set to destination email or leave empty
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + recepientEmail));
                startActivity(intent);
                 */


             //   Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:someone@example.com"));

/*
                 Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));


                emailIntent.setType("message/rfc822");


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


        */

        //4º la incidencia esta pla vamos a sacra fuera en su button


/*
        INCIDENCIAButton =(ImageButton)findViewById(R.id.IncidenciaButton);

        //le damos su funcion al Dialbutton:



        INCIDENCIAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /////////////para evitar dobles clicks rapidos //////////////
///////////////////////////////////////////////////////////////////


                // mis -clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                // do your magic here . . . .


                //que vibre al pulsar

                v.performHapticFeedback(1, 2);

                //Y ahora a que abra la nueva pagina

                Intent intentIncidencia =new Intent(this,IncidenciaActivity.class);



            }
        });*/


    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Pulsa Atras de nuevo para salir de GHFINCAS", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    //Se ejecuta la pulsar el boton de inicdencia

    public void IncidenciaButtonPulsado (View view){

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
    /*
        Intent intentIncidencia =new Intent(this,IncidenciaActivity.class);
        startActivity(intentIncidencia);
    */
        //Ahora mejotr la nueva IncidenciaSinFotoASctibvity

       /* Intent intentIncidencia =new Intent(this,InicidenciaSinFotoActivity.class);
        startActivity(intentIncidencia);
*/

        //o mejor aun la nueva IncidenciaGenral

        Intent intentIncidencia =new Intent(this,IncidenciaGeneral.class);
        startActivity(intentIncidencia);
    }

    public void PulsadoLogobutton (View view){

        //lo hago conlostener en oncreate para detcetar 7 toques



    }




        public void ProveedoresButtonPulsado (View view){

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


            /*Intent intentIncidencia =new Intent(this,ListaProveedores.class);
            startActivity(intentIncidencia);*/

            //Yahora cargandolo desde internet!!!

            Intent intentIncidencia =new Intent(this,ListaProveedoresFromWeb.class);
            startActivity(intentIncidencia);


        }

    public void PulsadoBotonEmpresa (View view){


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


        //se ejecuta la pulsar el boton web


        Intent intentIncidencia =new Intent(this,GHFincasActivity.class);
        startActivity(intentIncidencia);

    }

    public void ComunidadBotonPulsado(View view) {

        //que habr al web especifica...

        // String url = "http://www.ghfincas.es/";la declaramos public!!!
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(urlcomunidad));
        startActivity(i);


    }

    public void comunicadoplusadobutton(View view) {

        //pulsado el botn comunicados
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

        //ahora abrimos   la lstview de comunicados


        Intent intentIncidencia =new Intent(this,ParseActivityListView.class);

        startActivity(intentIncidencia);




    }

    public void TelefonoPulsadoMain(View view) {

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


    public void HelpPulsado(View view) {

        //pulsada ayuda





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




        Target viewTarget = new ViewTarget(R.id.IncidenciaButton, this);
        ShowcaseView sc = new ShowcaseView.Builder(this)
                .setTarget(viewTarget)
                .setContentTitle("INCIDENCIA")
                .setContentText("AL PULSAR ESTE BOTON NOS PODRA  INFORMAR SOBRE CUALQUIER INCIDENCIA O BIEN POR EMAIL O POR TELEFONO(24H)")
                //singleShot(42)
                .setShowcaseEventListener(this)
                .setStyle(R.style.CustomShowcaseTheme2)
                .replaceEndButton(R.layout.view_custom_button)

                .blockAllTouches()

                .build();

        sc.setTag(2);
    }

    //metodos de la ayuda!!!

    @Override
    public void onShowcaseViewHide(ShowcaseView showcaseView) {

        //cuando se cieera se llama esto:
    }

    @Override
    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {


        Log.e(TAG, "Showcasedidhide TAG:" +showcaseView.getTag() );


        if (showcaseView.getTag()==2 ){

            Target viewTarget2 = new ViewTarget(R.id.imageViewTelefonoMain, this);
            ShowcaseView sc2= new ShowcaseView.Builder(this)
                    .setTarget(viewTarget2)
                    .setContentTitle("LLAMARNOS")
                    .setContentText("AL PULSAR ESTE BOTON PODRA LLAMARNOS DESDE SU MOVIL DIRECTAMENTE")
                    // .singleShot(43)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .replaceEndButton(R.layout. view_custom_button)
                    .setShowcaseEventListener( this)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .replaceEndButton(R.layout. view_custom_button)
                    .hideOnTouchOutside()
                    .blockAllTouches()
                    .build();

            sc2.setTag(3 );

        }

        else if  (showcaseView.getTag()==3 ){

            //es el tercer help!!

            Target viewTarget3 = new ViewTarget(R.id.ProveedoresButton, this);
            ShowcaseView sc2= new ShowcaseView.Builder(this)

                    .setTarget(viewTarget3)
                    .setContentTitle("PROVEEDORES DE CONFIANZA")
                    .setContentText("AL PULSAR ESTE BOTON LE APARACERA UNA LISTA  ACTUALIZADA CON PROVEEDORES DE NUESTRA CONFIAZA POR SI PARTICUALRMENTE USTED NECESITA DE SUS SERVICIOS. \n " +
                            "ADEMAS AL PULSAR SOBRE EL PROVEEDOR PODRA LLAMARLO DIRECTAMENTE DESDE SU MOVIL Y LE APLICARAN UN DESCUENTO SI DICE QUE LES LLAMA DESDE AL APLICACION DE GHFINCAS!!")
                    //.singleShot(44)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .replaceEndButton(R.layout. view_custom_button)
                    .setShowcaseEventListener( this)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .replaceEndButton(R.layout. view_custom_button)
                    .hideOnTouchOutside()
                    .blockAllTouches()
                    .build();

            sc2.setTag(4 );

        }


        else if  (showcaseView.getTag()==4 ){

            //es el tercer help!!

            Target viewTarget3 = new ViewTarget(R.id.comunicadoplusadobutton, this);
            ShowcaseView sc2= new ShowcaseView.Builder(this)

                    .setTarget(viewTarget3)
                    .setContentTitle("COMUNICADOS")
                    .setContentText("AL PULSAR ESTE BOTON LE APARACERA UNA LISTA  ACTUALZIADA  CON LOS COMUNICADOS DE SU COMUNIDAD" +
                            " O LOS QUE GHFINCAS LES HAYA MANDADO A USTED EN PARTICULAR CON INFORMACION SOBRE SUS INCIDENCIAS U OTROS ASUNTOS PERSONALES")
                    //.singleShot(44)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .replaceEndButton(R.layout. view_custom_button)
                    .setShowcaseEventListener( this)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .replaceEndButton(R.layout. view_custom_button)
                    .hideOnTouchOutside()
                    .blockAllTouches()
                    .build();

            sc2.setTag(5 );

        }


        else if  (showcaseView.getTag()==5 ){

            //es el tercer help!!

            Target viewTarget3 = new ViewTarget(R.id.sucomunidad, this);
            ShowcaseView sc2= new ShowcaseView.Builder(this)

                    .setTarget(viewTarget3)
                    .setContentTitle("SU COMUNIDAD")
                    .setContentText("AL PULSAR ESTE BOTON LE DIRIGIREMOS A LOS DOCUMENTOS RELATIVOS A SU COMUNIDAD ALOJADOS EN NUESTRO SERVIDOR SEGURO.")
                    //.singleShot(44)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .replaceEndButton(R.layout. view_custom_button)
                    .setShowcaseEventListener( this)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .replaceEndButton(R.layout. view_custom_button)
                    .hideOnTouchOutside()
                    .blockAllTouches()
                    .build();

            sc2.setTag(6 );

        }

        else if  (showcaseView.getTag()==6 ){

            //es el tercer help!!

            Target viewTarget3 = new ViewTarget(R.id.EmpresaButton, this);
            ShowcaseView sc2= new ShowcaseView.Builder(this)

                    .setTarget(viewTarget3)
                    .setContentTitle("NUESTRA EMPRESA")
                    .setContentText("AL PULSAR ESTE BOTON LE EXPLICAMOS NUESTRA FILOSOFIA DE EMPRESA Y LE ENLAZAMOS " +
                            "SI QUIERE A VER NUESTRA PAGINA WEB POR SI NECEISTA SABER ALGO MAS DE NOSOTROS")
                    //.singleShot(44)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .replaceEndButton(R.layout. view_custom_button)
                    .setShowcaseEventListener( this)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .replaceEndButton(R.layout. view_custom_button)
                    .hideOnTouchOutside()
                    .blockAllTouches()
                    .build();

            sc2.setTag(7 );

        }

    }


    @Override
    public void onShowcaseViewShow(ShowcaseView showcaseView) {

    }


}

