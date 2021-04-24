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

public class FragmentExercise extends Fragment {
    View view;
    private Button setExercise;
    private EditText editExerciseInput;
    private TextView yourExercise;
    private TextView exerciseEnough;
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
        argExercise = getArguments().getDouble("keyExercise");
        yourExercise = (TextView) view.findViewById(R.id.yourExercise);
        exerciseEnough = (TextView) view.findViewById(R.id.exercisedEnough);
        editExerciseInput = (EditText) view.findViewById(R.id.editExerciseInput);
        setExercise = (Button) view.findViewById(R.id.setExercise);
        argExercise = (Math.round(argExercise * 100.0) / 100.0);
        if (argExercise < 30) {
            exerciseEnough.setText("you should be exercising more!");
        } else {
            exerciseEnough.setText("you have exercised enough!");
        }
        yourExercise.setText("Exercise today: " + argExercise + " min");

        setExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editExerciseInput.getText().toString().isEmpty()) {
                    argExercise = Double.parseDouble(editExerciseInput.getText().toString());

                    //yourHeight.setText("Height: "+editHeightInput.getText().toString()+" cm"); // same without needing height
                    argExercise = (Math.round(argExercise * 100.0) / 100.0);
                    if (argExercise < 30) {
                        exerciseEnough.setText("you should be exercising more!");
                    } else {
                        exerciseEnough.setText("you have exercised enough!");
                    }
                    yourExercise.setText("Exercise today: " + argExercise + " min");
                }
            }
        });
    }

}
