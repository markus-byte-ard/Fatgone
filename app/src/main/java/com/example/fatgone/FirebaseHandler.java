package com.example.fatgone;

import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
    public String UID;
    private DocumentReference docRef;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveUser (User user) {
        long epoch = Instant.now().getEpochSecond();
        user.setEpoch(epoch);

        Map<String, Object> userMap = createMap(user);

        UID = user.getUID();
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + UID + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        docRef = FirebaseFirestore.getInstance().collection(UID).document("users").collection("data").document(Long.toString(epoch));

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
        userMap.put("epoch", user.getEpoch());
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

    public User fetchNewestData(User user) {
        UID = user.getUID();
        final User[] newUser = {new User()};

        Query query = FirebaseFirestore.getInstance().document("users/" + UID).collection("Data").orderBy("epoch", Query.Direction.DESCENDING).limit(1);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            Log.d(TAG, "#########################" + document.getId() + " => " + document.getData() + "#########################");
                            newUser[0] = createUserFromMap(document.getData());
                        } else {
                            System.out.println("JÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖTI");
                        }
                    }
                } else {
                    Log.w(TAG, "######################### Error getting documents. #########################", task.getException());
                    User nUser = new User();
                    nUser.setUID(UID);
                    newUser[0] = nUser;
                }
            }
        });

        return newUser[0];
    }

    private User createUserFromMap (Map <String, Object> data) {
        User user = new User();
        user.setUID((String) data.get("UID"));
        user.setBmi((double) data.get("bmi"));
        user.setHeight((double) data.get("height"));
        user.setWeight((double) data.get("weight"));
        user.setName((String) data.get("name"));
        user.setCalories((double) data.get("calories"));
        user.setEpoch((long) data.get("epoch"));
        user.setExercise((double) data.get("exercise"));
        user.setSleep((double) data.get("sleep"));

        return user;
    }
}
