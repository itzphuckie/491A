package com.example.owner.ppkrinc;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
        mUpdateCarActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchUpdateCarInfoActivity();
            }
        });


    }
    
    private void launchUpdateCarInfoActivity(){
        Intent intent = new Intent(getApplicationContext(), CarInformation.class);
        intent.putExtras(loginData);
        startActivity(intent);
    }


}
