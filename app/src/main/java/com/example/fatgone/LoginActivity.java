package com.example.fatgone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText eName;
    private EditText ePassword;

    final private String password = "12345678";
    final private String username = "Admin";

    String inputName;
    String inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.LoginButton);
        eName = (EditText) findViewById(R.id.etUsername);
        ePassword = (EditText) findViewById(R.id.etPassword);

        inputName = "";
        inputPassword = "";
    }

    public void login (View v){
        inputName = eName.getText().toString();
        inputPassword = ePassword.getText().toString();

        if (inputName.equals(username) && inputPassword.equals(password)) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Login failed !", Toast.LENGTH_SHORT).show();
        }
    }
}