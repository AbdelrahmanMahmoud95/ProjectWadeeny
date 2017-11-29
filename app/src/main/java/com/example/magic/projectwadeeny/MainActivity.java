package com.example.magic.projectwadeeny;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signIn;
    private Button signUp;
    FirebaseAuth mAuth;


    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
        else {

        }
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            //finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        }

        signIn = (Button) findViewById(R.id.signin);
        signUp = (Button) findViewById(R.id.signup);
        ImageView myImageView = (ImageView) findViewById(R.id.myimage);
        myImageView.setImageResource(R.drawable.bus5);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);


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
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

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
            startActivity(signIn);
            /*AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
            View mview = getLayoutInflater().inflate(R.layout.activity_sign_in, null);
            mbuilder.setView(mview);
            AlertDialog alertDialog = mbuilder.create();
            alertDialog.show();*/
        } else if (view == signUp) {

            Intent signUp = new Intent(MainActivity.this, SignUp.class);
            startActivity(signUp);

        }


    }

}
