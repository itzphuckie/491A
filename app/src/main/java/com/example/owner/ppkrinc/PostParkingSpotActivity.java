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


public class PostParkingSpotActivity extends AppCompatActivity {

    private EditText parkingLotField;
    private EditText descriptorField;
    private CheckBox locationShareCheckMark;
    private CheckBox rideShareCheckMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_parking_spot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parkingLotField = (EditText) findViewById(R.id.parkingLotInput);
        descriptorField = (EditText) findViewById(R.id.descriptorInput);
        locationShareCheckMark = ((CheckBox) findViewById(R.id.locationSharingCheckMark));
        rideShareCheckMark = (CheckBox) findViewById(R.id.rideShareCheckMark);

        Button sendLocationPostButton = (Button) findViewById(R.id.postParkingPostButton);
        sendLocationPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParkingMapsActivity.class);
                Bundle extraLocationData = new Bundle();

                extraLocationData.putString("type" , "Post");
                extraLocationData.putString("parkingLot" , String.valueOf(parkingLotField.getText()));
                extraLocationData.putString("descriptor" , String.valueOf(descriptorField.getText()));
                extraLocationData.putBoolean("locationShare", locationShareCheckMark.isChecked());
                extraLocationData.putBoolean("rideShare", rideShareCheckMark.isChecked());

                intent.putExtras(extraLocationData);

                startActivity(intent);
            }
        });
    }

}
