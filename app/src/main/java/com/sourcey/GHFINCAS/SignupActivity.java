package com.mio.jrdv.ghfincas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    //para hacer un timne out de 3 segs

    private Thread thread;




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
        progressDialog.setMessage("Enviando datos a Servidor seguro en un plazo maximo de  24h recibira por email su contraseña.");
        progressDialog.show();




//eperamos 3 segs


        final SignupActivity myActivity = this;

        thread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(3000);
                    }
                }
                catch(InterruptedException ex){
                }

                // TODO
            }
        };

        thread.start();


        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String telefono = _telefonoText.getText().toString();
        String comunidad=_nombreComunidad.getText().toString();


                //el imei:

        TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String imei=mngr.getDeviceId();

        Log.i("email:", ""+imei);





        // TODO: Implement your own signup logic here.
        //aqui habria que mandar email con datos!!!

        Log.i("Send email", "");

        String[] TO = {"someone@gmail.com"};
        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Nueva Solicitud alta GHFINCASN APK");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Gracias por enviarnos su email estos son sus datos:"+name+" "+email+""+telefono+""+comunidad+"signature"+imei);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Lo siento su movil no esta preparado para mandar emails..", Toast.LENGTH_SHORT).show();
        }



        finish();


    }




    //enviar email:

    /*
    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"someone@gmail.com"};
        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(com.mio.jrdv.ghfincas.MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
*/

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


        if (name.isEmpty() || name.length() < 15) {
            _nameText.setError("Su nombre de ser completo con Apellidos para asegurar confidencialidad de datos a los que puede acceder!!!");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        /*
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("entre una direccion valida de email para poder enviarle su password");
            valid = false;
        } else {
            _emailText.setError(null);
        }
        */

        if (telefono.isEmpty() || telefono.length() < 9 || telefono.length() > 9) {
            _telefonoText.setError("Por favor entre un numero de telefono valido!!");
            valid = false;
        } else {
            _telefonoText.setError(null);
        }


        if (comunidad.isEmpty() || name.length() < 15) {
            _nombreComunidad.setError("Por favor introduzca el nombre completo de su comunidad!!");
            valid = false;
        } else {
            _nameText.setError(null);
        }


        return valid;
    }
}