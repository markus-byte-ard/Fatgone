package com.example.fatgone;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHandler {
    public static final String TAG = "FirebaseHandler";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document("Test");

    public void saveUser (User user) {
        Map<String, Object> userMap = createMap(user);

        docRef.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "User has been saved");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Error when saving user", e);
            }
        });
    };

    private Map<String, Object> createMap (User user) {
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("name", user.getName());
        userMap.put("weight", user.getWeight());
        userMap.put("height", user.getHeight());
        userMap.put("bmi", user.getBmi());

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
