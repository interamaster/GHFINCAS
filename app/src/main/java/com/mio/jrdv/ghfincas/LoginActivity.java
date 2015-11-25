package com.mio.jrdv.ghfincas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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









public class LoginActivity extends AppCompatActivity {




    //mio para guradr los valores de comunidad/nomre/telfonno/emila y pasw!!son public y accesibles desde toda la APK


    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREF_EMAIL = "email";//sera el user name
    public static final String PREF_PASSWORD = "password";
    public static final String PREF_TELEFONO = "telefono";
    public static final String PREF_NOMBRECMUNIDAD = "nombreComunidad";
    public static final String PREF_NOMBREVECINO = "nombreVecino";
    public static final String PREF_BOOL_LOGINYAOK ="false";




    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), com.mio.jrdv.ghfincas.SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        /*

        //para probar!!!!!! y saber el apssword ojoj quitar:


        //sacamos imei:

        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();

        String last2CharacteresIemi = imei.substring(Math.max(imei.length() - 2, 0));


        //deber ser final para poderla usar en el if de abajo!!!

        String decryptedpassword="ghfincas"+last2CharacteresIemi;

        Toast.makeText(this, "Este es tu password!!!(EL email da =) habra que enviarselo por email al cliente en un futuro:"+decryptedpassword, Toast.LENGTH_LONG).show();



         */

        //recupermos los valores del SharedPRefs sis e guardaron tras el signup activity



        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);




        /*

        estos son los vaslores guarados en el signup activity tras el ok

        edit.putString(LoginActivity.PREF_NOMBREVECINO, name);
        edit.putString(LoginActivity.PREF_EMAIL, email);
        edit.putString(LoginActivity.PREF_NOMBRECMUNIDAD, comunidad);
        edit.putString(LoginActivity.PREF_TELEFONO, telefono);
        edit.putString(LoginActivity.PREF_PASSWORD, decryptedpassword);

        edit.putBoolean(LoginActivity.PREF_BOOL_LOGINYAOK,true);

        */

        String email = pref.getString(PREF_EMAIL, null);//esto devolvera el nombre si existe o null!!
        String password = pref.getString(PREF_PASSWORD, null);


        boolean alreadyloggedinbefore =  pref.getBoolean(PREF_BOOL_LOGINYAOK, false);

        Log.d(TAG, "email:" +email+" y  password:"+password);

         Log.d(TAG, String.valueOf(alreadyloggedinbefore));


        //si existen el username y password los ponemos n los campos de manera automatica


            if (email != null && password != null && alreadyloggedinbefore){


        _emailText.setText(email);
        _passwordText.setText(password);

            }

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Entrando...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();


        //sacamos imei:

        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();

        String last2CharacteresIemi = imei.substring(Math.max(imei.length() - 2, 0));


        //deber ser final para poderla usar en el if de abajo!!!

        final String decryptedpassword="ghfincas"+last2CharacteresIemi;

      //  Toast.makeText(this, "Este es tu password!!! habra que enviarselo por email al cluiente en un futuro:"+decryptedpassword, Toast.LENGTH_SHORT).show();



        //  Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed

                        //////////////////////mi0o/////////////////////

                        if ((password.equals("sevilla")) || (password.equals(decryptedpassword)))
                        {
                            onLoginSuccess();
                        }

                        else {
                             onLoginFailed();
                        }

                        progressDialog.dismiss();
                    }
                }, 1500);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
               //yo de moento no hago nada

                //this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);


        //una vez hemos hecho el primer loging OK cmabiamos le bool para que la proxima vez lo guarde solo!!!!


        SharedPreferences pref = getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE);

        // We need an editor object to make changes
        SharedPreferences.Editor edit = pref.edit();


        edit.putBoolean( PREF_BOOL_LOGINYAOK,true);



        // Commit the changes
        edit.commit();




        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();




        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Introduzca una direccion de email valida");
            valid = false;
        } else {
            _emailText.setError(null);
        }






        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("su password debe tener entre 4 y 10 caracteres");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}