package com.example.fatgone;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.ServerTimestamps;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firestore.v1.DocumentTransform;

import java.lang.annotation.Annotation;

import java.lang.reflect.Field;
import java.sql.Time;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHandler {
    public static final String TAG = "FirebaseHandler";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String UID; // TEMPORARY UID FOR TESTING
    private DocumentReference docRef;
    //@ServerTimestamp Date time;
    //@ServerTimestamp Timestamp time;
    //DocumentTransform.FieldTransform.ServerValue.REQUEST_TIME


    // SAVE USER //
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveUser (User user) {
        Map<String, Object> userMap = createMap(user);

        //System.out.println("-----------------------------------------" + time + "-----------------------------------------");
        long epoch = Instant.now().toEpochMilli();
        UID = user.getUID();
        docRef = FirebaseFirestore.getInstance().document("users/" + UID).collection("Data").document(Long.toString(epoch));

        /*
        long epoch = Instant.now().toEpochMilli();
        userMap.put("epoch", epoch);
        Timestamp time = new Timestamp.now();
        //DocumentTransform.FieldTransform.ServerValue time = DocumentTransform.FieldTransform.ServerValue.REQUEST_TIME;
        userMap.put("epoch", time);
        long epoch = Instant.now().toEpochMilli();*/

        docRef.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "############################ User has been saved ############################");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "############################ Error while saving user ############################", e);
            }
        });
    };

    private Map<String, Object> createMap (User user) {
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("UID", user.getUID());
        userMap.put("name", user.getName());
        userMap.put("weight", user.getWeight());
        userMap.put("height", user.getHeight());
        userMap.put("bmi", user.getBmi());
        userMap.put("exercise", user.getExercise());
        userMap.put("sleep", user.getSleep());
        userMap.put("calories", user.getCalories());

        return userMap;
    }
    /*
    private class FirebaseHandler () {
        // Add a new document with a generated ID
    db.collection("users")
            .add(user)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
    }*/
}
