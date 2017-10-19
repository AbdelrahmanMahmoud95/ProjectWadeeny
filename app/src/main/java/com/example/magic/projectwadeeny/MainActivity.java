package com.example.magic.projectwadeeny;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signIn;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn = (Button) findViewById(R.id.signin);
        signUp = (Button) findViewById(R.id.signup);
        ImageView myImageView = (ImageView) findViewById(R.id.myimage);
        myImageView.setImageResource(R.drawable.bus5);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {


        if (view == signIn) {

            Intent signIn = new Intent(MainActivity.this,SignIn.class);
            startActivity(signIn);



        } else if (view == signUp) {

            Intent signUp = new Intent(MainActivity.this,SignUp.class);
            startActivity(signUp);

        }

    }
}
