package com.example.fatgone;

public class User {
    private String UID = "";
    private String name = "";
    private double weight = 0;     //kg
    private double height = 0;    //cm
    private double bmi = 0;        //kg/m^2
    private double exercise = 0;    //min
    private double sleep = 0;       //h
    private double calories = 0;  //kCal
    private long epoch = 0;

    /*
    public User() {
        this.setName("Adam");
        this.setWeight(80.0);
        this.setHeight(180);
        this.setBmi(0);
    }*/

    // SET METHODS
    public void setUID (String UID) {
        this.UID = UID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public void setSleep(double sleep) {
        this.sleep = sleep;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setExercise(double exercise) {
        this.exercise = exercise;
    }

    public void setEpoch(long epoch) { this.epoch = epoch; }

    // GET METHODS
    public String getUID() { return this.UID; }
    public long getEpoch() { return this.epoch; }
    public double getExercise() { return this.exercise; }
    public double getCalories() { return this.calories; }
    public double getSleep() { return this.sleep; }
    public String getName() { return this.name; }
    public double getWeight() { return this.weight; }
    public double getHeight() { return this.height; }
    public double getBmi() { return this.bmi; }
}
