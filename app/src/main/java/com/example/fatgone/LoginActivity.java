package com.example.fatgone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private boolean DEBUG = false;
    private Button loginButton;
    private EditText eEmail;
    private EditText ePassword;
    private TextView eRegister;
    private ProgressBar eLoginProg;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Inialize views
        loginButton = (Button) findViewById(R.id.LoginButton);
        eEmail = (EditText) findViewById(R.id.etEmail);
        ePassword = (EditText) findViewById(R.id.etPassword);
        eRegister = (TextView) findViewById(R.id.tvRegister);
        eLoginProg = (ProgressBar) findViewById(R.id.progressBarLogin);
        //Get firebase instance
        fAuth = FirebaseAuth.getInstance();
        //eRegister will move you to register activity
        eRegister.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegistrationActivity.class)));
        //Login button will login to firebase
        loginButton.setOnClickListener(v -> {
            String email = eEmail.getText().toString().trim();
            String password = ePassword.getText().toString().trim();
            //Debug was so that we didn't need to write credentials over and over again
            if (DEBUG) {
                email = "admin@admin.com";
                password = "adminadminadmin";
            }

            if (TextUtils.isEmpty(email)) {
                eEmail.setError("Email is required!");
                return;
            } if (TextUtils.isEmpty(password)) {
                ePassword.setError("Password is required!");
                return;
            } if (password.length() < 12) {
                ePassword.setError("Password must be at least 12 characters!");
                return;
            }
            //Progress bar will tell the user something is happening.
            eLoginProg.setVisibility(View.VISIBLE);
            System.out.println(email);
            System.out.println(password);
            //Firebase method sign in will sign the user in
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        loadHome();
                    } else {
                        Toast.makeText(LoginActivity.this, "Error ! " + task.getException(), Toast.LENGTH_SHORT).show();
                        eLoginProg.setVisibility(View.GONE);
                    }
                }
            });
        });
    }
    // Retrieves logged in users UID
    private String retrieveUID() {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser() ;
        if (fUser != null) {
            String userUID = fUser.getUid();
            return userUID;
        } else {
            Log.d("LoginActivity", "###############################" + "CRITICAL ERROR LOADING USER UID" + "###############################");
            return "";
        }

    };

    // Loads the home activity
    private void loadHome() {
        Intent home = new Intent(LoginActivity.this, MainActivity.class);
        //Sends UID to mainActivity
        home.putExtra("userUID", retrieveUID());
        startActivity(home);
    };
}