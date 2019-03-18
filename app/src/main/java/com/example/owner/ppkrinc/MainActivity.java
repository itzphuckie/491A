package com.example.owner.ppkrinc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Toolbar my_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"Car Information");
        menu.add(0,1,0,"Account Information");
        menu.add(0,2,0,"Log Out");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 2){
            launchHomePage();
        };
        if(item.getItemId() == 1){
            launchUserInfoActivity();
        }
        if(item.getItemId() == 0){
            launchUpdateCarInfoActivity();
        }
        return super.onOptionsItemSelected(item);
    }
    // Logging out
    private void launchHomePage() {
        Intent intent = new Intent(getApplicationContext(), HomePage.class);
        startActivity(intent);
    }


    public void launchPostParkingSpotActivity(View view){
        Intent intent = new Intent(getApplicationContext(), PostParkingSpotActivity.class);
        startActivity(intent);
    }

    public void launchSearchParkingSpotAct(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchParking.class);
        startActivity(intent);
    }

    public void launchRecentMatchesAct(View view) {
        Intent intent = new Intent(getApplicationContext(), PostParkingSpotActivity.class);
        startActivity(intent);
    }

    public void launchMyAccountAct(View view) {
        Intent intent = new Intent(getApplicationContext(), MyAccount.class);
        startActivity(intent);
    }

    public void launchAboutUsAct(View view) {

        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
        startActivity(intent);
    }
    // This is for setting menu
    private void launchUpdateCarInfoActivity(){
        Intent intent = new Intent(getApplicationContext(), CarInformation.class);
        startActivity(intent);
    }

    private void launchUserInfoActivity(){
        Intent intent = new Intent(getApplicationContext(), UserInformation.class);
        startActivity(intent);
    }
}
