package com.example.owner.ppkrinc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SearchParking extends AppCompatActivity {

    private CheckBox rideShareCheckMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_parking2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rideShareCheckMark = (CheckBox) findViewById(R.id.rideShareCheckMark);

        Button sendLocationRequestButton = (Button) findViewById(R.id.searchParkingSearchButton);
        sendLocationRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParkingMapsActivity.class);
                Bundle extraLocationData = new Bundle();

                extraLocationData.putString("type", "Request");
                extraLocationData.putBoolean("rideShare", rideShareCheckMark.isChecked());

                intent.putExtras(extraLocationData);

                startActivity(intent);
            }
        });
    }

}
