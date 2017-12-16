package com.example.magic.projectwadeeny;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    private Button submit;
    private EditText email;
    private EditText password;
    private EditText passwordConf;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        LinearLayout contentPain = findViewById(R.id.signupcontentPain);
        contentPain.setAlpha(0);
        contentPain.animate().alpha(1.0f).setDuration(2000).start();

        submit = (Button) findViewById(R.id.submitBtn);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        passwordConf = (EditText) findViewById(R.id.password2);
        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setMessage(getString(R.string.loading));
                dialog.show();
                final String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                String passwordConfStr = passwordConf.getText().toString().trim();

                if (TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(passwordConfStr)) {
                    Toast.makeText(SignUp.this, R.string.fill, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;

                }


                if (!(passwordStr.equals(passwordConfStr))) {
                    Toast.makeText(SignUp.this, R.string.noMatch, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(SignUp.this, "User " + emailStr + " is signed Up successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUp.this, Home.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                    finish();
                                    dialog.dismiss();
                                }


                            }
                        });


            }
        });


    }
}
