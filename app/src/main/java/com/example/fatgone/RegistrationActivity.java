package com.example.fatgone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText eRegName;
    private EditText eRegPassword;
    private EditText eRegEmail;
    private TextView eLogin;
    private Button eRegister;
    private ProgressBar eProgress;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        eRegName = findViewById(R.id.etRegName);
        eRegPassword = findViewById(R.id.etRegPassword);
        eRegEmail = findViewById(R.id.etRegEmail);
        eLogin = findViewById(R.id.tvReturn2Login);
        eRegister = findViewById(R.id.btnRegister);
        eProgress = findViewById(R.id.progressBarRegister);

        fAuth = FirebaseAuth.getInstance();

        eLogin.setOnClickListener(v -> startActivity(new Intent(RegistrationActivity.this, LoginActivity.class)));
        eRegister.setOnClickListener(v -> {

            String regUsername = eRegName.getText().toString().trim();
            String regEmail = eRegEmail.getText().toString().trim();
            String regPassword = eRegPassword.getText().toString().trim();

            if (TextUtils.isEmpty(regEmail)) {
                eRegEmail.setError("Email is required!");
                return;
            } if (TextUtils.isEmpty(regPassword)) {
                eRegPassword.setError("Password is required!");
                return;
            } if (regPassword.length() < 8 ) {
                eRegPassword.setError("Password must be at least 8 characters!");
                return;
            }
            eProgress.setVisibility(View.VISIBLE);

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
}