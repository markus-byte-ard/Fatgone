package com.example.fatgone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    View view;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment newFrag;

    public String userUID;
    private DocumentReference docRef;

    FileHandler filehandler = new FileHandler(MainActivity.this);

    // USER //
    User curUser = new User();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Retrieve UID passed by the log in activity
        String userUID = getIntent().getStringExtra("userUID");
        curUser.setUID(userUID);

        updateHomeFragment(curUser);

        // Initialise drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    public void updateHomeFragment (User user) {
        // Load home fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        newFrag = new FragmentHome();

        Bundle bundle = new Bundle();
        bundle.putString("keyName", user.getName());
        bundle.putDouble("keyBmi", user.getBmi());
        bundle.putDouble("keyWeight", user.getWeight());
        bundle.putDouble("keyHeight", user.getHeight());
        bundle.putDouble("keySleep", user.getSleep());
        bundle.putDouble("keyCalories", user.getCalories());
        bundle.putDouble("keyExercise", user.getExercise());

        newFrag.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, newFrag);
        fragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveUserDataButton(View v) {
        saveUserData();
    }

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

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment newFrag = null;

        /* SYNTAX
        case (R.id.button_id):
            newFrag = new JavaClass();
            break;
        */
        switch (item.getItemId()) {
            case R.id.nav_exercise:
                newFrag = new FragmentExercise();
                break;
            case R.id.nav_calories:
                newFrag = new FragmentCalories();
                break;
            case R.id.nav_sleep:
                newFrag = new FragmentSleep();
                break;
            case R.id.nav_profile:
                newFrag = new FragmentProfile();
                break;
            case R.id.nav_LogOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return true;
            default:
                newFrag = new FragmentHome();
                return true;
        }
        Bundle bundle = new Bundle();
        bundle.putString("keyName", curUser.getName());
        bundle.putDouble("keyBmi", curUser.getBmi());
        bundle.putDouble("keyWeight", curUser.getWeight());
        bundle.putDouble("keyHeight", curUser.getHeight());
        bundle.putDouble("keySleep", curUser.getSleep());
        bundle.putDouble("keyCalories", curUser.getCalories());
        bundle.putDouble("keyExercise", curUser.getExercise());
        newFrag.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container, newFrag);
        fragmentTransaction.commit();
        return true;
    }

    public void getFragProfile(View view){
        fragmentManager = getSupportFragmentManager();
        FragmentProfile frag = (FragmentProfile) fragmentManager.findFragmentById(R.id.fragment_container); //Retrieve the fragment and save it into a variable
        curUser.setWeight(frag.sendFragWeight());
        curUser.setHeight(frag.sendFragHeight());
        curUser.setBmi(frag.sendFragBMI());
        curUser.setName(frag.sendFragName());
        //testi
        System.out.println("paino on "+frag.sendFragWeight());
        System.out.println("pituus on "+frag.sendFragHeight());
        System.out.println("bmi on "+frag.sendFragBMI());
        System.out.println("nimi on "+frag.sendFragName());

    }
    public void getFragCalories(View view){
        fragmentManager = getSupportFragmentManager();
        FragmentCalories frag = (FragmentCalories) fragmentManager.findFragmentById(R.id.fragment_container); //Retrieve the fragment and save it into a variable
        curUser.setCalories(frag.sendFragCalories());
    }
    public void getFragSleep(View view){
        fragmentManager = getSupportFragmentManager();
        FragmentSleep frag = (FragmentSleep) fragmentManager.findFragmentById(R.id.fragment_container); //Retrieve the fragment and save it into a variable
        curUser.setSleep(frag.sendFragSleep());
    }
    public void getFragExercise(View view){
        fragmentManager = getSupportFragmentManager();
        FragmentExercise frag = (FragmentExercise) fragmentManager.findFragmentById(R.id.fragment_container); //Retrieve the fragment and save it into a variable
        curUser.setExercise(frag.sendFragExercise());
    }
}