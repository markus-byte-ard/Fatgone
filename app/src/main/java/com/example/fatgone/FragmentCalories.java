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
        argCalories = getArguments().getDouble("keyCalories");
        yourCalories = (TextView) view.findViewById(R.id.yourCalories);
        caloriesEnough = (TextView) view.findViewById(R.id.caloriesEnough);
        editCaloriesInput = (EditText) view.findViewById(R.id.editCaloriesInput);
        setCalories = (Button) view.findViewById(R.id.setCalories);
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

        argCalories = (Math.round(argCalories * 100.0) / 100.0);
        if (argCalories < 30) {
            caloriesEnough.setText("you should be eating more!");
        } else {
            caloriesEnough.setText("you have eaten enough!");
        }
        yourCalories.setText("Eaten today: " + argCalories + " calories");
    }

    public double sendFragCalories() {
        if (!editCaloriesInput.getText().toString().isEmpty()) {
            argCalories = argCalories + Double.parseDouble(editCaloriesInput.getText().toString());

            //yourHeight.setText("Height: "+editHeightInput.getText().toString()+" cm"); // same without needing height
            argCalories = (Math.round(argCalories * 100.0) / 100.0);
            if (argCalories < 30) {
                caloriesEnough.setText("you should be eating more!");
            } else {
                caloriesEnough.setText("you have eaten enough!");
            }
            yourCalories.setText("Eaten today: " + argCalories + " calories");

        }
        return argCalories;
    }


}
