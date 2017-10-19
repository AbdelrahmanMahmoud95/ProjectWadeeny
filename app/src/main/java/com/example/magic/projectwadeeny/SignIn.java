package com.example.magic.projectwadeeny;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignIn extends AppCompatActivity {
    Button signin;
    private EditText email;
    private EditText password;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signin = (Button) findViewById(R.id.buttonin);
        email = (EditText) findViewById(R.id.emailin);
        password = (EditText) findViewById(R.id.passwordin);


        mAuth = FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String emailSt = email.getText().toString().trim();
                String passwordSt = password.getText().toString().trim();
                if (TextUtils.isEmpty(emailSt) || TextUtils.isEmpty(passwordSt)) {
                    Toast.makeText(SignIn.this, "Fill Missing Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(emailSt, passwordSt)
                        .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignIn.this, task.getException().getMessage().toString(),
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    finish();
                                    Intent home = new Intent(SignIn.this, Home.class);
                                    startActivity(home);
                                }



                            }
                        });



            }

        });


        // dah ebn ws5a msh sh3'al
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    finish();
                    Intent home = new Intent(SignIn.this, Home.class);
                    startActivity(home);


                }
            }
        };

    }
}
