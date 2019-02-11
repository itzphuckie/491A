package com.example.owner.ppkrinc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateCar extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private static final String TAG = UpdateCar.class.getSimpleName();
    private String userID;
    private FirebaseFirestore carInfoDB;

    // UI references.
    private AutoCompleteTextView mCarYearField;
    private AutoCompleteTextView mCarLicenseField;
    private Spinner mCarMakeSpinner;
    private Spinner mCarModelSpinner;
    private View mProgressView;
    private View mUpdateCarInfoFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car);

        mUpdateCarInfoFormView = findViewById(R.id.car_info_scrollview);
        mProgressView = findViewById(R.id.update_progress);

        Bundle loginData = getIntent().getExtras();
        userID = loginData.getString("UserID");
        carInfoDB = FirebaseFirestore.getInstance();

        // Set up the Update Car Info Form.
        mCarYearField = (AutoCompleteTextView) findViewById(R.id.year);

        mCarMakeSpinner = (Spinner) findViewById(R.id.car_make_spinner);

        mCarModelSpinner = (Spinner) findViewById(R.id.car_model_spinner);

        mCarLicenseField = (AutoCompleteTextView) findViewById(R.id.license_plate);

        Button mUpdateCarInfoButton = (Button) findViewById(R.id.update_car);
        mUpdateCarInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> carData = new HashMap<>();
                carData.put("Year", String.valueOf(mCarYearField.getText()));
                carData.put("Make", String.valueOf(mCarMakeSpinner.getSelectedItem()));
                carData.put("Model", String.valueOf(mCarModelSpinner.getSelectedItem()));
                carData.put("PlateNumber", String.valueOf(mCarLicenseField.getText()));
                carData.put("UserID", userID);

                carInfoDB.collection("userCarData").add(carData);


                Toast.makeText(UpdateCar.this,
                        "Car Information Updated",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mUpdateCarInfoFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mUpdateCarInfoFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mUpdateCarInfoFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mUpdateCarInfoFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
