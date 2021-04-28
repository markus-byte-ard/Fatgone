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

public class FragmentExercise extends Fragment {
    View view;
    private Button setExercise;
    private EditText editExerciseInput;
    private TextView yourExercise;
    private TextView exerciseEnough;
    private GraphView graph;
    double argExercise;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercise, container, false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        // Get data from bundle
        argExercise = getArguments().getDouble("keyExercise");
        // Inialize views
        yourExercise = (TextView) view.findViewById(R.id.yourExercise);
        exerciseEnough = (TextView) view.findViewById(R.id.exercisedEnough);
        editExerciseInput = (EditText) view.findViewById(R.id.editExerciseInput);
        setExercise = (Button) view.findViewById(R.id.setExercise);
        graph = (GraphView) view.findViewById(R.id.graph);

        // Graph test
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        argExercise = (Math.round(argExercise * 100.0) / 100.0);
        if (argExercise < 30) {
            exerciseEnough.setText("You should be exercising more!");
        } else {
            exerciseEnough.setText("You have exercised enough!");
        }
        yourExercise.setText("Exercise today: " + argExercise + " min");
    }

    // adds exercise the user inputs and sends that data to mainActivity, changes what the fragment shows
    public double sendFragExercise () {
        if (!editExerciseInput.getText().toString().isEmpty()) {
            argExercise = argExercise + Double.parseDouble(editExerciseInput.getText().toString());
            if (argExercise < 0) {
                argExercise = 0;
            }
            argExercise = (Math.round(argExercise * 100.0) / 100.0);
            if (argExercise < 30) {
                exerciseEnough.setText("You should be exercising more!");
            } else {
                exerciseEnough.setText("You have exercised enough!");
            }
            yourExercise.setText("Exercise today: " + argExercise + " min");

        }
        return argExercise;
    }

}
