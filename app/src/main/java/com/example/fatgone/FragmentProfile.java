package com.example.fatgone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 123;

    View view;
    private Button setWeight;
    private Button setHeight;
    private Button setName;
    private EditText editWeightInput;
    private EditText editHeightInput;
    private EditText editNameInput;
    private TextView yourName;
    private TextView yourWeight;
    private TextView yourHeight;
    private TextView yourBmi;
    private ImageView yourPicture;
    private Button setPicture;
    private GraphView graph;
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
        editNameInput = (EditText) view.findViewById(R.id.editName);
        setHeight = (Button) view.findViewById(R.id.setHeight);
        setWeight = (Button) view.findViewById(R.id.setWeight);
        setName = (Button) view.findViewById(R.id.setName);
        setPicture = (Button) view.findViewById(R.id.setPicture);
        yourPicture = (ImageView) view.findViewById(R.id.imageView);
        graph = (GraphView) view.findViewById(R.id.graph);

        // Graph test
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        yourName.setText(argName);
        yourHeight.setText("Height: "+argHeight+" cm");
        yourWeight.setText("Weight: "+argWeight+" kg");
        argBmi = (Math.round(argBmi * 100.0) / 100.0);
        yourBmi.setText("BMI: " + String.valueOf(argBmi) + " kg/m^2");

        updateBmi();

        setPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick an image"), GALLERY_REQUEST_CODE);
            }
        });
        /*
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
                    updateBmi();
                }
            }
        });
         */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageData = data.getData();
            yourPicture.setImageURI(imageData);
        }
    }

    private void updateBmi() {
        argBmi = (argWeight / ((argHeight / 100) * (argHeight / 100)));
        argBmi = (Math.round(argBmi * 100.0) / 100.0);
        yourBmi.setText("BMI: " + String.valueOf(argBmi) + " kg/m^2");
    }

    public double sendFragWeight() {
        if (!editWeightInput.getText().toString().isEmpty()) {
            argWeight = Double.parseDouble(editWeightInput.getText().toString());
            yourWeight.setText("Weight: " + argWeight + " kg");
            updateBmi();
        }
        return argWeight;
    }

    public double sendFragHeight() {
        if (!editHeightInput.getText().toString().isEmpty()) {
            argHeight = Double.parseDouble(editHeightInput.getText().toString());
            yourHeight.setText("Weight: " + argHeight + " kg");
            updateBmi();
        }
        return argHeight;
    }
    public String sendFragName() {
        if (!editNameInput.getText().toString().isEmpty()) {
            argName = editNameInput.getText().toString();
            yourName.setText(argName);
        }
        return argName;
    }

    public double sendFragBMI() {
        return argBmi;
    }
}