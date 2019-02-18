package com.example.owner.ppkrinc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MyAccount extends AppCompatActivity {
    private String userID;
    private Bundle loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        
        loginData = getIntent().getExtras();
        userID = loginData.getString("UserID");
        
        Button mUpdateCarActivityButton = (Button) findViewById(R.id.button14);
        mUpdateCarActivityButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                launchUpdateCarInfoActivity();
            }
        });
    }
    
    private void launchUpdateCarInfoActivity(){
        Intent intent = new Intent(getApplicationContext(), UpdateCar.class);
        intent.putExtras(loginData);
        startActivity(intent);
    }

}
