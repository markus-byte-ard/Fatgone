package com.example.fatgone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class YouActivity extends AppCompatActivity {

    private Button setWeight;
    private Button setHeight;
    private EditText editWeightInput;
    private EditText editHeightInput;
    private TextView yourWeight;
    private TextView yourHeight;
    private TextView yourBmi;

    private double height = 0;
    private double weight = 0;
    private double bmi; // = (weight/(height*height));


    //You YOU = You.getInstance();

/*
        // you()
    private String name;
    private double weight = 50;
    private double height;
    private double bmi;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.you);

        yourWeight = (TextView) findViewById(R.id.yourWeight);
        yourHeight = (TextView) findViewById(R.id.yourHeight);
        yourBmi    = (TextView) findViewById(R.id.yourBmi);
        editHeightInput = (EditText) findViewById(R.id.editHeight);
        editWeightInput = (EditText) findViewById(R.id.editWeight);
        setHeight = (Button) findViewById(R.id.setHeight);
        setWeight = (Button) findViewById(R.id.setWeight);

        yourHeight.setText("Height: "+height+" cm");
        yourWeight.setText("Weight: "+weight+" kg");

        setHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editHeightInput.getText().toString().isEmpty()) {
                    height = Double.parseDouble(editHeightInput.getText().toString());

                    //yourHeight.setText("Height: "+editHeightInput.getText().toString()+" cm"); // same without needing height
                    yourHeight.setText("Height: " + height + " cm");
                    bmi = (weight / ((height / 100) * (height / 100)));
                    bmi = (Math.round(bmi * 100.0) / 100.0);
                    yourBmi.setText("BMI: " + String.valueOf(bmi) + " kg/m^2");
                }
            }
        });
        setWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editWeightInput.getText().toString().isEmpty()) {

                    weight = Double.parseDouble(editWeightInput.getText().toString());
                    yourWeight.setText("Weight: " + weight + " kg");
                    bmi = (weight / ((height / 100) * (height / 100)));
                    bmi = (Math.round(bmi * 100.0) / 100.0);
                    yourBmi.setText("BMI: " + String.valueOf(bmi) + " kg/m^2");
                }
            }
        });


    }
}