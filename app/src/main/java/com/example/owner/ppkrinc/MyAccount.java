package com.example.owner.ppkrinc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MyAccount extends AppCompatActivity {
    private String userID;
    private Bundle loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        loginData = getIntent().getExtras();
        userID = loginData.getString("UserID");
        
        Button mUpdateCarActivityButton = (Button) findViewById(R.id.myAccountCarInformationButton);
        mUpdateCarActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchUpdateCarInfoActivity();
            }
        });

        Button mUserInfoActivityButton = (Button) findViewById(R.id.myAccountMyInformationButton);
        mUserInfoActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchUserInfoActivity();
            }
        });
    }
    
    private void launchUpdateCarInfoActivity(){
        Intent intent = new Intent(getApplicationContext(), CarInformation.class);
        intent.putExtras(loginData);
        startActivity(intent);
    }

    private void launchUserInfoActivity(){
        Intent intent = new Intent(getApplicationContext(), UserInformation.class);
        intent.putExtras(loginData);
        startActivity(intent);
    }

}
