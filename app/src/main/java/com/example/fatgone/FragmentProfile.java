package com.example.fatgone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentProfile extends Fragment {
    View view;
    private Button setWeight;
    private Button setHeight;
    private EditText editWeightInput;
    private EditText editHeightInput;
    private TextView yourName;
    private TextView yourWeight;
    private TextView yourHeight;
    private TextView yourBmi;
    /*
    private double height = 0;
    private double weight = 0;
    private double bmi; // = (weight/(height*height));

     */
    double argBmi;
    double argHeight;
    double argWeight;
    String argName;

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
        view = inflater.inflate(R.layout.fragment_profile, container, false);
    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        argName = getArguments().getString("keyName");
        argWeight = getArguments().getDouble("keyWeight");
        argHeight = getArguments().getDouble("keyHeight");
        argBmi = getArguments().getDouble("keyBmi");

        yourName = (TextView) view.findViewById(R.id.yourName);
        yourWeight = (TextView) view.findViewById(R.id.yourWeight);
        yourHeight = (TextView) view.findViewById(R.id.yourHeight);
        yourBmi    = (TextView) view.findViewById(R.id.yourBmi);
        editHeightInput = (EditText) view.findViewById(R.id.editHeight);
        editWeightInput = (EditText) view.findViewById(R.id.editWeight);
        setHeight = (Button) view.findViewById(R.id.setHeight);
        setWeight = (Button) view.findViewById(R.id.setWeight);

        yourName.setText(argName);
        yourHeight.setText("Height: "+argHeight+" cm");
        yourWeight.setText("Weight: "+argWeight+" kg");
        argBmi = (Math.round(argBmi * 100.0) / 100.0);
        yourBmi.setText("BMI: " + String.valueOf(argBmi) + " kg/m^2");

        setHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editHeightInput.getText().toString().isEmpty()) {
                    argHeight = Double.parseDouble(editHeightInput.getText().toString());

                    //yourHeight.setText("Height: "+editHeightInput.getText().toString()+" cm"); // same without needing height
                    yourHeight.setText("Height: " + argHeight + " cm");
                    argBmi = (argWeight / ((argHeight / 100) * (argHeight / 100)));
                    argBmi = (Math.round(argBmi * 100.0) / 100.0);
                    yourBmi.setText("BMI: " + String.valueOf(argBmi) + " kg/m^2");
                }
            }
        });
        setWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editWeightInput.getText().toString().isEmpty()) {

                    argWeight = Double.parseDouble(editWeightInput.getText().toString());
                    yourWeight.setText("Weight: " + argWeight + " kg");
                    argBmi = (argWeight / ((argHeight / 100) * (argHeight / 100)));
                    argBmi = (Math.round(argBmi * 100.0) / 100.0);
                    yourBmi.setText("BMI: " + String.valueOf(argBmi) + " kg/m^2");
                }
            }
        });
    }

    public double getWeight(){
        System.out.println("paino on "+argWeight);
        return argWeight;
    }
}