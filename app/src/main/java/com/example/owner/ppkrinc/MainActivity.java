package com.example.owner.ppkrinc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String userID;
    private Bundle loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        loginData = getIntent().getExtras();
        userID = loginData.getString("UserID");

    }

    public void launchPostParkingSpotActivity(View view){
        Intent intent = new Intent(getApplicationContext(), PostParkingSpotActivity.class);
        intent.putExtras(loginData);
        startActivity(intent);
    }

    public void launchSearchParkingSpotAct(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchParking.class);
        intent.putExtras(loginData);
        startActivity(intent);
    }

    public void launchRecentMatchesAct(View view) {
        Intent intent = new Intent(getApplicationContext(), PostParkingSpotActivity.class);
        intent.putExtras(loginData);
        startActivity(intent);
    }

    public void launchMyAccountAct(View view) {
        Intent intent = new Intent(getApplicationContext(), MyAccount.class);
        intent.putExtras(loginData);
        startActivity(intent);
    }

    public void launchAboutUsAct(View view) {

        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
        intent.putExtras(loginData);
        startActivity(intent);
    }
}
