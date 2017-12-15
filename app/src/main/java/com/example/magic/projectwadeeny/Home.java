package com.example.magic.projectwadeeny;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("data");
        final TextView textView_1 = ((TextView) findViewById(R.id._1));
        final TextView textView_2 = ((TextView) findViewById(R.id._2));
        final TextView textView_3 = ((TextView) findViewById(R.id._3));
        signOut = (Button) findViewById(R.id.btnout);
        TextView textView = (TextView) findViewById(R.id._4);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignIn.class));
        }
        FirebaseUser user = mAuth.getCurrentUser();
        textView.setText("Welcome " + user.getEmail());
        signOut.setOnClickListener(this);

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                textView_1.setText("");
                textView_2.setText("");
                textView_3.setText("");

                for (DataSnapshot s : dataSnapshot.getChildren()) {

                    try {
                        Data mData = s.getValue(Data.class);

                        textView_1.append(mData.getStartingPoint());

                        ArrayList<String> path = mData.getPath();

                        for (int i = 0; i < path.size(); i++) {
                            textView_3.append(path.get(i) + "\n");

                        }
                        textView_2.append(mData.getEndingPoint());
                    } catch (Exception e) {
                        Toast.makeText(Home.this, "Error ! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Home.this, "Error ! " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View view) {


        if (view == signOut) {
            mAuth.signOut();
            finish();
            Intent signOut = new Intent(Home.this, SignIn.class);
            startActivity(signOut);


        }
    }


}
