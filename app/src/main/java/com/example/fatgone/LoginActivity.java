package com.example.fatgone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText eName;
    private EditText ePassword;
    private TextView eRegister;
    private ProgressBar eLoginProg;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.LoginButton);
        eName = (EditText) findViewById(R.id.etEmail);
        ePassword = (EditText) findViewById(R.id.etPassword);
        eRegister = (TextView) findViewById(R.id.tvRegister);
        eLoginProg = (ProgressBar) findViewById(R.id.progressBarLogin);

        fAuth = FirebaseAuth.getInstance();

        eRegister.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegistrationActivity.class)));
        loginButton.setOnClickListener(v -> {
            String email = eName.getText().toString().trim();
            String password = ePassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                eName.setError("Email is required!");
                return;
            } if (TextUtils.isEmpty(password)) {
                ePassword.setError("Password is required!");
                return;
            } if (password.length() < 8) {
                ePassword.setError("Password must be at least 8 characters!");
                return;
            }
            eLoginProg.setVisibility(View.VISIBLE);
            System.out.println(email);
            System.out.println(password);
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

    private String retrieveUID() {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String userUID = fUser.getUid();

        return userUID;
    };

    private void loadHome() {
        Intent home = new Intent(LoginActivity.this, MainActivity.class);
        home.putExtra("userUID", retrieveUID());
        startActivity(home);
    };
}