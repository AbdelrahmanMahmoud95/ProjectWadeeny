package com.example.magic.projectwadeeny;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {

    Button upload;
    EditText txt;
    EditText txtKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        upload = (Button) findViewById(R.id.uploadBtn);
        txt = (EditText) findViewById(R.id.uploaded);
        txtKey = (EditText)findViewById(R.id.textkey);
       // final String mtxt = txt.getText().toString();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("projectwadeeny");

      //  Toast.makeText(this, myRef.toString(), Toast.LENGTH_SHORT).show();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = txt.getText().toString();
                String key = txtKey.getText().toString();
                DatabaseReference mychild =myRef.child(key);
                mychild.setValue(value);


            }
        });

    }
}
