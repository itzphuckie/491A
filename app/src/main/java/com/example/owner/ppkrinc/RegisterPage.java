package com.example.owner.ppkrinc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private static final String TAG = RegisterPage.class.getSimpleName();
    private ViewFlipper viewFlipper;
    private REGISTER_VIEWS currentView = REGISTER_VIEWS.EMAIL;
    private enum REGISTER_VIEWS{
        EMAIL, PASSWORD, NAME
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register_page);
        viewFlipper = findViewById(R.id.viewFlipper);
    }

    public void createAccount(View view) {
        TextView emailInput = findViewById(R.id.emailInput);
        TextView passwordInput = findViewById(R.id.passswordInput);
        TextView nameInput = findViewById(R.id.nameInput);

        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        currentUser = mAuth.getCurrentUser();
                        assert currentUser != null;
                        currentUser.sendEmailVerification().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Log.d(TAG, "Email Sent");
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                User user = new User();
                                user.email = email;
                                user.name = name;
                                user.firebaseID = currentUser.getUid();
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://ppkr-users.firebaseio.com/");
                                DatabaseReference myRef = firebaseDatabase.getReference();
                                String key = myRef.push().getKey();
                                myRef.child(user.firebaseID).setValue(user);
                            }
                        });


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterPage.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public void nextButtonPressed(View view) {
        switch (currentView){
            case EMAIL:
                TextView emailInput = findViewById(R.id.emailInput);

                String email = emailInput.getText().toString();
                if (!email.contains("csulb.edu")) {
                    Toast.makeText(RegisterPage.this, "Please enter a valid CSULB email.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    viewFlipper.showNext();
                    currentView = REGISTER_VIEWS.PASSWORD;
                }
                break;

            case PASSWORD:
                TextView passwordInput = findViewById(R.id.passswordInput);
                TextView confirmPasswordInput = findViewById(R.id.confirmPasswordInput);

                String password = passwordInput.getText().toString();
                String confirmedPassword = confirmPasswordInput.getText().toString();

                if (password.equals(confirmedPassword)){
                    viewFlipper.showNext();
                    currentView=REGISTER_VIEWS.NAME;
                } else {
                    passwordInput.setText("");
                    confirmPasswordInput.setText("");
                    Toast.makeText(RegisterPage.this, "Your passwords are not the same.", Toast.LENGTH_SHORT).show();

                }

                break;
            case NAME:
                createAccount(view);
                break;
        }
    }

    public void previousButtonPressed(View view) {
        switch (currentView){
            case EMAIL:
                finish();
                break;

            case PASSWORD:
                viewFlipper.showPrevious();
                currentView = REGISTER_VIEWS.EMAIL;
                break;

            case NAME:
                viewFlipper.showPrevious();
                currentView = REGISTER_VIEWS.PASSWORD;
                break;
        }
    }
}
