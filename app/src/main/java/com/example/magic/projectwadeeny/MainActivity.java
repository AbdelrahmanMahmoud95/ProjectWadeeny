package com.example.magic.projectwadeeny;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signIn;
    private Button signUp;
    private ImageView changeLang;
    FirebaseAuth mAuth;


    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //load current language
        SharedPreferences pref = getSharedPreferences("lang", MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = getSharedPreferences("lang", MODE_PRIVATE).edit();
        String language = pref.getString("language", "en");

        if (language.equals("en")) {
            prefEdit.putString("language", "en");
            prefEdit.apply();
        } else {
            prefEdit.putString("language", "ar");
            prefEdit.apply();
        }
        LanSettings.changeLang(language, getBaseContext());


        setContentView(R.layout.activity_main);

        if (!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, Home.class));
            finish();
        }

        signIn = (Button) findViewById(R.id.signin);
        signUp = (Button) findViewById(R.id.signup);
        changeLang = (ImageView) findViewById(R.id.lang);
        changeLang.setOnClickListener(this);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

        signIn.setAlpha(0);
        signIn.animate().alpha(1.0f).setDuration(4000).start();
        signUp.setAlpha(0);
        signUp.animate().alpha(1.0f).setDuration(4000).start();

    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(R.string.noInternet);
        builder.setMessage(R.string.noInternetMessage);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            }
        });

        return builder;
    }

    @Override
    public void onClick(View view) {


        if (view == signIn) {

            Intent signIn = new Intent(MainActivity.this, SignIn.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(signIn);
        } else if (view == signUp) {

            Intent signUp = new Intent(MainActivity.this, SignUp.class);
            signUp.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(signUp);

        } else if (view == changeLang) {

            SharedPreferences pref = getSharedPreferences("lang", MODE_PRIVATE);
            SharedPreferences.Editor prefEdit = getSharedPreferences("lang", MODE_PRIVATE).edit();
            String language = pref.getString("language", "not_Set");
            if (language.equals("en")) {
                LanSettings.changeLang("ar", getBaseContext());
                prefEdit.putString("language", "ar");
                prefEdit.apply();
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);

            } else if (language.equals("ar")) {
                LanSettings.changeLang("en", getBaseContext());
                prefEdit.putString("language", "en");
                prefEdit.apply();
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        }


    }

}
