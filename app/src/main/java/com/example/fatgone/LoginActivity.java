package com.example.fatgone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText eName;
    private EditText ePassword;
    private TextView eRegister;
    private CheckBox eRememberMe;

    public Credentials credentials;

    boolean isValid = false;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.LoginButton);
        eName = (EditText) findViewById(R.id.etUsername);
        ePassword = (EditText) findViewById(R.id.etPassword);
        eRegister = (TextView) findViewById(R.id.tvRegister);
        eRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);

        credentials = new Credentials();

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if(sharedPreferences != null){

            Map<String, ?> preferencesMap = sharedPreferences.getAll();

            if(preferencesMap.size() != 0){
                credentials.loadCredentials(preferencesMap);
            }

            String savedUsername = sharedPreferences.getString("LastSavedUsername", "");
            String savedPassword = sharedPreferences.getString("LastSavedPassword", "");

            if(sharedPreferences.getBoolean("RememberMeCheckBox", false)){
                eName.setText(savedUsername);
                ePassword.setText(savedPassword);
                eRememberMe.setChecked(true);
            }
        }
        eRememberMe.setOnClickListener(v -> {

            sharedPreferencesEditor.putBoolean("RememberMeCheckBox", eRememberMe.isChecked());
            sharedPreferencesEditor.apply();
        });
        eRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegistrationActivity.class)));
        loginButton.setOnClickListener(v -> {

            String inputName = eName.getText().toString();
            String inputPassword = ePassword.getText().toString();

            if(inputName.isEmpty() || inputPassword.isEmpty())
            {
                Toast.makeText(LoginActivity.this, "Please enter all the details correctly!", Toast.LENGTH_SHORT).show();
            }else{

                isValid = validate(inputName, inputPassword);

                if(!isValid){
                    Toast.makeText(LoginActivity.this, "Incorrect credentials entered!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                    sharedPreferencesEditor.putString("LastSavedUsername", inputName);
                    sharedPreferencesEditor.putString("LastSavedPassword", inputPassword);
                    sharedPreferencesEditor.apply();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });
    }
    private boolean validate(String name, String password){
        return credentials.verifyCredentials(name, password);
    }
}