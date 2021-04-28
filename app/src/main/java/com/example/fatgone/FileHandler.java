package com.example.fatgone;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileHandler extends AppCompatActivity {
    Context context = null;

    FileHandler(Context c) {
        context = c;
    }

    public String readNewestLine (String filename) {
        String currentLine;
        String lastLine = "ERROR";
        try {
            InputStream ins = context.openFileInput(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));

            while ((currentLine = br.readLine()) != null) {
                System.out.println(currentLine);
                lastLine = currentLine;
            }

            ins.close();
            br.close();

        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", "FileHandler readNewestLine() ERROR: Could not find with name '"+filename+"'.");
        } catch (IOException e) {
            Log.e("IOException", "FileHandler readNewestLine() ERROR: Generic error in input.");
        }
        return lastLine;
    }

    public void appendData (String filename, User user) {
        String title = "Name;Weight;Height;BMI;Exercise;Sleep;Calories:Epoch";
        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput(filename, context.MODE_APPEND));

            if (isEmpty(filename)) {
                ows.write(title);
            }

            //ows.

            ows.close();
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", "FileHandler appendData() ERROR: Could not find with name '"+filename+"'.");
        } catch (IOException e) {
            Log.e("IOException", "FileHandler appendData() ERROR: Generic error in input.");
        }
    }

    private boolean isEmpty(String filename) {
        File file = new File (filename);
        if (file.length() == 0 || !(file.exists())) {
            return true;
        }

        return false;
        /*
        try {
            InputStream ins = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));

            while ((currentLine = br.readLine()) != null) {
                System.out.println(currentLine);
                lastLine = currentLine;
            }

        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", "FileHandler isEmpty() ERROR: Could not find with name '"+filename+"'.");
        } catch (IOException e) {
            Log.e("IOException", "FileHandler isEmpty() ERROR: Generic error in input.");
        }*/
    }
}
