package com.example.owner.ppkrinc;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccount extends AppCompatActivity {
    private String userID;
    private Bundle loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ppkr-users.firebaseio.com/");
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                TextView textView = findViewById(R.id.nameTextView);
                textView.setText(user.name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loginData = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar);
        Drawable backButton = getResources().getDrawable(R.drawable.baseline_arrow_back_black_18dp);
        toolbar.setNavigationIcon(backButton);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
            }
        });

        setSupportActionBar(toolbar);

        Button mUpdateCarActivityButton = (Button) findViewById(R.id.myAccountCarInformationButton);
        mUpdateCarActivityButton.setOnClickListener(view -> launchUpdateCarInfoActivity());


    }
    
    private void launchUpdateCarInfoActivity(){
        Intent intent = new Intent(this, CarInformation.class);
//        intent.putExtras(loginData);
        startActivity(intent);
    }


    public void launchUpdateCarActivity(View view) {
        Intent intent = new Intent(this, UpdateCar.class);
        intent.putExtras(loginData);
        startActivity(intent);
    }
}
