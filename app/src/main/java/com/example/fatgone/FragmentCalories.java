package com.example.fatgone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class FragmentCalories extends Fragment {
    View view;
    private Button setCalories;
    private EditText editCaloriesInput;
    private TextView yourCalories;
    private TextView caloriesEnough;
    private GraphView graph;
    double argCalories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calories, container, false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        // Get data from bundle
        argCalories = getArguments().getDouble("keyCalories");
        // Initialize views
        yourCalories = (TextView) view.findViewById(R.id.yourCalories);
        caloriesEnough = (TextView) view.findViewById(R.id.caloriesEnough);
        editCaloriesInput = (EditText) view.findViewById(R.id.editCaloriesInput);
        setCalories = (Button) view.findViewById(R.id.setCalories);
        graph = (GraphView) view.findViewById(R.id.graph);

        // Add graph
        ArrayList<String> list = getArguments().getStringArrayList("keyGraph");
        DataPoint[] data = new DataPoint[list.size()];
        int counter = 0;
        for( String item : list) {
            data[counter] = new DataPoint(counter, Double.parseDouble(item));
            System.out.println(data[counter]);
            counter++;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
        graph.addSeries(series);

        argCalories = (Math.round(argCalories * 100.0) / 100.0);
        if (argCalories < 2000) {
            caloriesEnough.setText("You should be eating more calories!");
        } else {
            caloriesEnough.setText("You have eaten enough calories!");
        }
        yourCalories.setText("Eaten today: " + argCalories + " calories");
    }

    // adds calories the user inputs and sends that data to mainActivity, changes what the fragment shows
    public double sendFragCalories() {
        if (!editCaloriesInput.getText().toString().isEmpty()) {
            argCalories = argCalories + Double.parseDouble(editCaloriesInput.getText().toString());

            if (argCalories <0){
                argCalories = 0;
            }

            argCalories = (Math.round(argCalories * 100.0) / 100.0);
            if (argCalories < 2000) {
                caloriesEnough.setText("You should be eating more calories!");
            } else {
                caloriesEnough.setText("You have eaten enough calories!");
            }
            yourCalories.setText("Eaten today: " + argCalories + " calories");

        }
        return argCalories;
    }


}
