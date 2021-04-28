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

public class FragmentSleep extends Fragment {
    View view;
    private Button setSleep;
    private EditText editSleepInput;
    private TextView yourSleep;
    private TextView sleptEnough;
    private GraphView graph;
    double argSleep;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sleep, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        // Inialize views
        argSleep = getArguments().getDouble("keySleep");
        yourSleep = (TextView) view.findViewById(R.id.yourSleep);
        sleptEnough = (TextView) view.findViewById(R.id.sleptEnough);
        editSleepInput = (EditText) view.findViewById(R.id.editSleep);
        setSleep = (Button) view.findViewById(R.id.setSleep);
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

        argSleep = (Math.round(argSleep * 100.0) / 100.0);
        if (argSleep < 8){
            sleptEnough.setText("You should be sleeping more!");
        }
        else {
            sleptEnough.setText("You have slept enough!");
        }
        yourSleep.setText("Sleep tonight: "+argSleep+" h");

    }

    // adds sleep the user inputs and sends that data to mainActivity, changes what the fragment shows
    public double sendFragSleep() {
        if (!editSleepInput.getText().toString().isEmpty()) {
            argSleep = argSleep + Double.parseDouble(editSleepInput.getText().toString());
            if (argSleep <0){
                argSleep = 0;
            }

            argSleep = (Math.round(argSleep * 100.0) / 100.0);
            if (argSleep < 7){
                sleptEnough.setText("You should be sleeping more!");
            }
            else if (argSleep >= 7 && 10 >= argSleep){
                sleptEnough.setText("You have slept enough!");
            } else {
                sleptEnough.setText("You have slept too long!");
            }

            yourSleep.setText("Sleep tonight: "+argSleep+" h");
        }
        return argSleep;
    }
}
