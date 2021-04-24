package com.example.fatgone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    View view;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment newFrag;

    // USER //
    User curUser = new User();

    // FIREBASE //
    FirebaseHandler firebase = new FirebaseHandler();

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

        // FIREBASE TEST //
        //updateFirebaseUser(curUser);
        firebase.fetchNewestData(curUser);

        // Initialise drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Load home fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        newFrag = new FragmentHome();
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
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    public void loadFragment (String fragName) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }*/

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveUserData (View view) {
        firebase.saveUser(curUser);

    }

    //////// FIREBASE ////////

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateFirebaseUser (User user) {
        firebase.saveUser(user);
    };
}