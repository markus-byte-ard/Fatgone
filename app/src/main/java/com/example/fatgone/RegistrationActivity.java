package com.example.fatgone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class RegistrationActivity extends AppCompatActivity {

    private EditText eRegName;
    private EditText eRegPassword;
    private EditText eRegEmail;
    private TextView eLogin;
    private Button eRegister;
    private ProgressBar eProgress;
    FirebaseAuth fAuth;
    PasswordValidication passworValidication = new PasswordValidication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Inialize views
        eRegPassword = findViewById(R.id.etRegPassword);
        eRegEmail = findViewById(R.id.etRegEmail);
        eLogin = findViewById(R.id.tvReturn2Login);
        eRegister = findViewById(R.id.btnRegister);
        eProgress = findViewById(R.id.progressBarRegister);
        // Get firebase Instance
        fAuth = FirebaseAuth.getInstance();
        // Will send user back to login activity if clicked
        eLogin.setOnClickListener(v -> startActivity(new Intent(RegistrationActivity.this, LoginActivity.class)));

        eRegister.setOnClickListener(v -> {

            String regEmail = eRegEmail.getText().toString().trim();
            String regPassword = eRegPassword.getText().toString().trim();

            if (TextUtils.isEmpty(regEmail)) {
                eRegEmail.setError("Email is required!");
                return;
            }
            if (TextUtils.isEmpty(regPassword)) {
                eRegPassword.setError("Password is required!");
                return;
            }
            if (!passworValidication.isValid(regPassword)) {
                eRegPassword.setError("Password must be at least 12 characters and strong!");
                return;
            }
            // Progress bar will show the user something is going on
            eProgress.setVisibility(View.VISIBLE);
            // Create user to firebase
            fAuth.createUserWithEmailAndPassword(regEmail, regPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "Registeration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Error ! " + task.getException(), Toast.LENGTH_SHORT).show();
                        eProgress.setVisibility(View.GONE);
                    }
                }
            });
        });
    }

    // Java code to validate a password Thank you GeeksForGeeks
    public boolean isValid(String password) {
        // for checking if password length
        // is between 8 and 15
        if (!(password.length() >= 12)) {
            return false;
        }
        // to check space
        if (password.contains(" ")) {
            return false;
        }
        if (true) {
            int count = 0;
            // check digits from 0 to 9
            for (int i = 0; i <= 9; i++) {
                // to convert int to string
                String str1 = Integer.toString(i);
                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }
        // for special characters
        if (!(password.contains("@") || password.contains("#")
                || password.contains("!") || password.contains("~")
                || password.contains("$") || password.contains("%")
                || password.contains("^") || password.contains("&")
                || password.contains("*") || password.contains("(")
                || password.contains(")") || password.contains("-")
                || password.contains("+") || password.contains("/")
                || password.contains(":") || password.contains(".")
                || password.contains(", ") || password.contains("<")
                || password.contains(">") || password.contains("?")
                || password.contains("|"))) {
            return false;
        }

        if (true) {
            int count = 0;
            // checking capital letters
            for (int i = 65; i <= 90; i++) {
                // type casting
                char c = (char) i;
                String str1 = Character.toString(c);
                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }
        if (true) {
            int count = 0;
            // checking small letters
            for (int i = 90; i <= 122; i++) {
                // type casting
                char c = (char) i;
                String str1 = Character.toString(c);
                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }
        // if all conditions fails
        return true;
    }
}