package com.example.fatgone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class YouActivity extends Fragment {
    View view;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.you, container, false);
    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        yourWeight = (TextView) view.findViewById(R.id.yourWeight);
        yourHeight = (TextView) view.findViewById(R.id.yourHeight);
        yourBmi    = (TextView) view.findViewById(R.id.yourBmi);
        editHeightInput = (EditText) view.findViewById(R.id.editHeight);
        editWeightInput = (EditText) view.findViewById(R.id.editWeight);
        setHeight = (Button) view.findViewById(R.id.setHeight);
        setWeight = (Button) view.findViewById(R.id.setWeight);

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