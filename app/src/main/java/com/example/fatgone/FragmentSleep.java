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

public class FragmentSleep extends Fragment {
    View view;
    private Button setSleep;
    private EditText editSleepInput;
    private TextView yourSleep;
    private TextView sleptEnough;
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
        argSleep = getArguments().getDouble("keySleep");
        yourSleep = (TextView) view.findViewById(R.id.yourSleep);
        sleptEnough = (TextView) view.findViewById(R.id.sleptEnough);
        editSleepInput = (EditText) view.findViewById(R.id.editSleep);
        setSleep = (Button) view.findViewById(R.id.setSleep);
        argSleep = (Math.round(argSleep * 100.0) / 100.0);
        if (argSleep < 8){
            sleptEnough.setText("you should be sleeping more!");
        }
        else {
            sleptEnough.setText("you have slept enough!");
        }
        yourSleep.setText("Sleep tonight: "+argSleep+" h");

        setSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editSleepInput.getText().toString().isEmpty()) {
                    argSleep = Double.parseDouble(editSleepInput.getText().toString());

                    //yourHeight.setText("Height: "+editHeightInput.getText().toString()+" cm"); // same without needing height
                    argSleep = (Math.round(argSleep * 100.0) / 100.0);
                    if (argSleep < 8){
                        sleptEnough.setText("you should be sleeping more!");
                    }
                    else {
                        sleptEnough.setText("you have slept enough!");
                    }
                    yourSleep.setText("Sleep tonight: "+argSleep+" h");
                }
            }
        });

    }
}
