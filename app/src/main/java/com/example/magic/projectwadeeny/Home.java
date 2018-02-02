package com.example.magic.projectwadeeny;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText from;
    private EditText to;
    private Button search;
    private LinearLayout screen;
    private ImageView changLang;
    ProgressDialog dialog;
    ImageView signOut;

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

        setContentView(R.layout.activity_home);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("data");
        signOut = (ImageView) findViewById(R.id.logOut);
        from = (EditText) findViewById(R.id.source);
        to = (EditText) findViewById(R.id.destination);
        search = (Button) findViewById(R.id.search);
        screen = (LinearLayout) findViewById(R.id.screen);
        changLang = (ImageView) findViewById(R.id.chngLang);
        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignIn.class));
        }
        signOut.setOnClickListener(this);
        changLang.setOnClickListener(this);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.setMessage(getString(R.string.loading));
                dialog.show();
                //todo handel empty fields
                try {
                    final String fromStr = from.getText().toString().trim();
                    final String toStr = to.getText().toString().trim();
                    screen.removeAllViews();

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            boolean flag = true;

                            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                try {
                                    Buss currentBus = snap.getValue(Buss.class);
                                    if (TextUtils.isEmpty(fromStr) || TextUtils.isEmpty(toStr)) {
                                        Toast.makeText(Home.this, R.string.fill, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        return;
                                    }


                                    if (currentBus.getPath().toString().contains(fromStr) && currentBus.getPath().toString().contains(toStr)) {
                                        flag = false;
                                        TextView busNumber = new TextView(Home.this);
                                        busNumber.setTextColor(Color.WHITE);
                                        TextView price = new TextView(Home.this);
                                        price.setTextColor(Color.WHITE);
                                        TextView path = new TextView(Home.this);
                                        path.setTextColor(Color.WHITE);
                                        TextView blank = new TextView(Home.this);
                                        blank.setTextColor(Color.WHITE);
                                        busNumber.setTextSize(20);
                                        price.setTextSize(20);
                                        path.setTextSize(20);
                                        String[] array = currentBus.getPath().toArray(new String[currentBus.getPath().size()]);
                                        String stations = Arrays.toString(array);

                                        busNumber.setText("Bus Number" +" "+ snap.getKey());
                                        price.setText("Ticket Price" +" "+ currentBus.getPrice());
                                        path.setText("Bus Stations" +" "+ stations);
                                        blank.setText("\n");
                                        screen.addView(busNumber);
                                        screen.addView(price);
                                        screen.addView(path);
                                        screen.addView(blank);
                                        dialog.dismiss();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }

                            if (flag) {
                                dialog.dismiss();
                                Toast.makeText(Home.this, R.string.sorry, Toast.LENGTH_LONG).show();
                            }

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } catch (Exception e) {
                    Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void onClick(View view) {
        if (view == signOut) {
            mAuth.signOut();
            finish();
            Intent signOut = new Intent(Home.this, SignIn.class);
            startActivity(signOut);
            dialog.dismiss();

        } else if (view == changLang) {
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



