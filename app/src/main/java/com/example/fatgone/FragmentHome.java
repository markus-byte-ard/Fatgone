package com.example.fatgone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentHome extends Fragment {
    View view;
    private TextView tvCalories;
    private TextView tvSleep;
    private TextView tvExercise;
    double argCalories;
    double argSleep;
    double argExercise;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Get data from bundle
        argCalories = getArguments().getDouble("keyCalories");
        argCalories = (Math.round(argCalories * 100.0) / 100.0);
        argSleep = getArguments().getDouble("keySleep");
        argExercise = getArguments().getDouble("keyExercise");
        // Inialize Views
        tvCalories = view.findViewById(R.id.field_calories);
        tvSleep = view.findViewById(R.id.field_sleep);
        tvExercise = view.findViewById(R.id.field_exercise);
        // Set TextViews
        tvCalories.setText(""+argCalories+" Calories");
        tvSleep.setText(""+argSleep+" Hours");
        tvExercise.setText(""+argExercise+" Minutes");
    }
}
