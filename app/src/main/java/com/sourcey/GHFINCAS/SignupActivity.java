package com.mio.jrdv.ghfincas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.mio.jrdv.ghfincas.LoginActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";


    private static final String [] EmailTO = {"interamaster@gmail.com"};





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
        progressDialog.setMessage("Generando  email encriptado para enviar a servidor,por favor envie el email a continuacion... en 24h recibira por email su contraseña.");
        progressDialog.show();


//eperamos 4 segs


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


                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");


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


        if (name.isEmpty() || name.length() < 15) {
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


        if (comunidad.isEmpty() || name.length() < 15) {
            _nombreComunidad.setError("Por favor introduzca el nombre completo de su comunidad!!");
            valid = false;
        } else {
            _nameText.setError(null);
        }


        return valid;
    }
}