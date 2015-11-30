package com.mio.jrdv.ghfincas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";


    private static final String [] EmailTO = {"interamaster@gmail.com"};

    private boolean SendEmailOK=false;




    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_telefono) EditText _telefonoText;

    @InjectView(R.id.input_nombrecomunidad) EditText _nombreComunidad;


    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });



        //redondeamos el LOGO:

        ImageView LOGO=(ImageView)findViewById(R.id.LOGOSignup);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo7);

        RoundedBitmapDrawable img = RoundedBitmapDrawableFactory.create(getResources(), largeIcon);

        //asi con un radio

        img.setCornerRadius(100.0f);


        //asi es circular perfecta
        //img.setCornerRadius(Math.min(img.getMinimumWidth(), img.getMinimumHeight())/2.0f);

        LOGO.setImageDrawable(img);
        //recupermos los valores del SharedPRefs sis e guardaron tras el signup activity



    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);


        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Generando  email encriptado para enviar a servidor,por favor envie el email a continuacion... en 24h recibira por email su contraseña.");
        progressDialog.show();


//eperamos 4 segs


        ///////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////EMAIL MANUAL//////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////
/*
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {


            public void run() {
                //aqui klo que queramosd que pase en 3 segs


                String name = _nameText.getText().toString();
                String email = _emailText.getText().toString();
                String telefono = _telefonoText.getText().toString();
                String comunidad = _nombreComunidad.getText().toString();




                //el imei:

                TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String imei = mngr.getDeviceId();

                Log.i(" imei:", "" + imei);


                String last2CharacteresIemi = imei.substring(Math.max(imei.length() - 2, 0));

                 String decryptedpassword="ghfincas"+last2CharacteresIemi;




                //los Valores on correctos asi que los guardamos en le SharedPreference!!!


                SharedPreferences pref = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);

                // We need an editor object to make changes
                SharedPreferences.Editor edit = pref.edit();

                // Set/Store data
                edit.putString(LoginActivity.PREF_NOMBREVECINO, name);
                edit.putString(LoginActivity.PREF_EMAIL, email);
                edit.putString(LoginActivity.PREF_NOMBRECMUNIDAD, comunidad);
                edit.putString(LoginActivity.PREF_TELEFONO, telefono);
                edit.putString(LoginActivity.PREF_PASSWORD, decryptedpassword);

                edit.putBoolean(LoginActivity.PREF_BOOL_LOGINYAOK,false);



                // Commit the changes
                edit.commit();


                // TODO: Hacer envio email automatico
                //aqui habria que mandar email con datos!!!

                Log.d("Send email", "Send email");


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
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("message/rfc822");


                emailIntent.putExtra(Intent.EXTRA_EMAIL, EmailTO);

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Nueva Solicitud alta GHFINCAS  APK");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Gracias por enviarnos su email estos son sus datos:\n" + name + "\n" + email + "\n" + telefono + "\n" + comunidad + "\n signature:" + "Start ENCRYPTED:" +
                        "%%ADFSDLIFSDLJKHDLKASHDLKHSLKJDHLSDHLKASDKJSKDJJDKJKLDLSKAJDLKAJSDKJLDJLKSJDLKAJSDLKSJADLKJASLDJASKDKJDLKJERIUFH" +
                        "KLJHFDKHGJKFHKJHGKJHJKFJKDFHGKJHDFKJGHKJDFHGKJFHJKHJFGKJHFDKJGHKJFDHGJKFHGKJDKHGDFKHGJKFDHGKSHFJGHDFSKGKJFDHGKFH" +
                        ""+imei+"DSADASKDJKASJDLKAJDLKJSALKDJLKJKDLJSALKDJASLKDJLKASDJLKSDJKLSJDLKASJDLKJSLKJDLKJDDSJLKADJSLDSJAKSDKLDSLJ" +
                        "DNSDJLKAJJKVJKVSDIOUFISODUOIFSJKLKDLSFJLKSDJFLKDSJFKLJSDLKFJKLDSJFLKSJDFLKJSDLKFJKLDJFLKSDJFKLJSDFKLJSDKLFJSDKLF");


                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.d("  email", "Finished sending email");
                } catch (android.content.ActivityNotFoundException ex) {
                    //no me deja poner un toast dentro de un runnable!!!!

                   // Toast.makeText(this, "Lo siento su movil no esta preparado para mandar emails..", Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        }, 4000);
            */
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////AUTOMATICO//////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////

        //asi da erro Could not send email
        //android.os.NetworkOnMainThreadException
        //hay que hacerlo en en ASYNTASK!!!!

        progressDialog.hide();


        new SendMail().execute("");

/*
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String telefono = _telefonoText.getText().toString();
        String comunidad = _nombreComunidad.getText().toString();




        //el imei:

        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();

        Log.i(" imei:", "" + imei);


        String last2CharacteresIemi = imei.substring(Math.max(imei.length() - 2, 0));

        String decryptedpassword="ghfincas"+last2CharacteresIemi;




        //los Valores on correctos asi que los guardamos en le SharedPreference!!!


        SharedPreferences pref = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);

        // We need an editor object to make changes
        SharedPreferences.Editor edit = pref.edit();

        // Set/Store data
        edit.putString(LoginActivity.PREF_NOMBREVECINO, name);
        edit.putString(LoginActivity.PREF_EMAIL, email);
        edit.putString(LoginActivity.PREF_NOMBRECMUNIDAD, comunidad);
        edit.putString(LoginActivity.PREF_TELEFONO, telefono);
        edit.putString(LoginActivity.PREF_PASSWORD, decryptedpassword);

        edit.putBoolean(LoginActivity.PREF_BOOL_LOGINYAOK,false);



        // Commit the changes
        edit.commit();

         progressDialog.hide();
        //en vez de asi..automatico

        Mail m = new Mail("jrdvsoftyopozi@gmail.com", "sevilla4");
        String[] toArr = {"jrdvsoftyopozi@gmail.com"};
        m.setTo(toArr);
        m.setFrom("jrdvsoftyopozi@gmail.com");
        m.setSubject("Email en pantalla de incidencia!!");
        m.setBody("Email body 2");
        try {
            // m.addAttachment("/sdcard/bday.jpg");
            if(m.send()) {
                Toast.makeText(this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "Email was not sent.", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Lo siento su movil no esta preparado para mandar emails de manera autoamtica vams ahcerlo de manera manual" +
                        "", Toast.LENGTH_SHORT).show();

                ManualEmailSiFallaAutomatico();

            }
        } catch(Exception e) {
            Log.e("MailApp", "Could not send email", e);
        }

*/

    }


    public void SendEmailInBackgroundMio(){


        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String telefono = _telefonoText.getText().toString();
        String comunidad = _nombreComunidad.getText().toString();




        //el imei:

        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();

        Log.i(" imei:", "" + imei);


        String last2CharacteresIemi = imei.substring(Math.max(imei.length() - 2, 0));

        String decryptedpassword="ghfincas"+last2CharacteresIemi;




        //los Valores on correctos asi que los guardamos en le SharedPreference!!!


        SharedPreferences pref = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);

        // We need an editor object to make changes
        SharedPreferences.Editor edit = pref.edit();

        // Set/Store data
        edit.putString(LoginActivity.PREF_NOMBREVECINO, name);
        edit.putString(LoginActivity.PREF_EMAIL, email);
        edit.putString(LoginActivity.PREF_NOMBRECMUNIDAD, comunidad);
        edit.putString(LoginActivity.PREF_TELEFONO, telefono);
        edit.putString(LoginActivity.PREF_PASSWORD, decryptedpassword);

        edit.putBoolean(LoginActivity.PREF_BOOL_LOGINYAOK, false);



        // Commit the changes
        edit.commit();


        //en vez de asi..automatico

        Mail m = new Mail("jrdvsoftyopozi@gmail.com", "sevilla4");
        String[] toArr = {"jrdvsoftyopozi@gmail.com"};
        m.setTo(toArr);
        m.setFrom("jrdvsoftyopozi@gmail.com");
        m.setSubject("Email en pantalla de incidencia!!");
        m.setBody("Email body 2");
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



        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String telefono = _telefonoText.getText().toString();
        String comunidad = _nombreComunidad.getText().toString();




        //el imei:

        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();

        Log.i(" imei:", "" + imei);


        String last2CharacteresIemi = imei.substring(Math.max(imei.length() - 2, 0));

        String decryptedpassword="ghfincas"+last2CharacteresIemi;





        // TODO: Hacer envio email manualñ si gfallo el automatico
        //aqui habria que mandar email con datos!!!

        Log.d("Send email", "Send email");


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
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Gracias por enviarnos su email estos son sus datos:\n" + name + "\n" + email + "\n" + telefono + "\n" + comunidad + "\n signature:" + "Start ENCRYPTED:" +
                        "%%ADFSDLIFSDLJKHDLKASHDLKHSLKJDHLSDHLKASDKJSKDJJDKJKLDLSKAJDLKAJSDKJLDJLKSJDLKAJSDLKSJADLKJASLDJASKDKJDLKJERIUFH" +
                        "KLJHFDKHGJKFHKJHGKJHJKFJKDFHGKJHDFKJGHKJDFHGKJFHJKHJFGKJHFDKJGHKJFDHGJKFHGKJDKHGDFKHGJKFDHGKSHFJGHDFSKGKJFDHGKFH" +
                        ""+imei+"DSADASKDJKASJDLKAJDLKJSALKDJLKJKDLJSALKDJASLKDJLKASDJLKSDJKLSJDLKASJDLKJSLKJDLKJDDSJLKADJSLDSJAKSDKLDSLJ" +
                        "DNSDJLKAJJKVJKVSDIOUFISODUOIFSJKLKDLSFJLKSDJFLKDSJFKLJSDLKFJKLDSJFLKSJDFLKJSDLKFJKLDJFLKSDJFKLJSDFKLJSDKLFJSDKLF");


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
 

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Datos no Correctos!!", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String telefono = _telefonoText.getText().toString();
        String comunidad=_nombreComunidad.getText().toString();


        if (name.isEmpty() || name.length() < 5) {
            _nameText.setError("Su nombre de ser completo con Apellidos para asegurar confidencialidad de datos a los que puede acceder!!!");
            valid = false;
        } else {
            _nameText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("entre una direccion valida de email para poder enviarle su password");
            valid = false;
        } else {
            _emailText.setError(null);
        }


        if (telefono.isEmpty() || telefono.length() < 9 || telefono.length() > 9) {
            _telefonoText.setError("Por favor entre un numero de telefono valido!!");
            valid = false;
        } else {
            _telefonoText.setError(null);
        }


        if (comunidad.isEmpty() || name.length() < 5) {
            _nombreComunidad.setError("Por favor introduzca el nombre completo de su comunidad!!");
            valid = false;
        } else {
            _nameText.setError(null);
        }


        return valid;
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

                  Toast.makeText(SignupActivity.this, "Su email se envio correctamente!!", Toast.LENGTH_LONG).show();

                finish();


            }

            else {

                //ha fallado

                 Toast.makeText(SignupActivity.this, "Email fallo!!!", Toast.LENGTH_LONG).show();
        }


        }
    }


}