package com.example.fatgone;

public class User {
    private String UID = "";
    private String name = "Name Here";
    private double weight = 50;     //kg
    private double height = 170;    //cm
    private double bmi = 99;        //kg/m^2
    private double exercise = 2;   //min
    private double sleep = 5;       //h
    private double calories = 200;       //

    public User() {
        this.setName("Adam");
        this.setWeight(80.0);
        this.setHeight(1.80);
        this.setBmi(0);
    }

    // SET METHODS
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
    public void setSleep() {
        this.sleep = sleep;
    }

    public void setCalories(double bmi) {
        this.calories = calories;
    }

    public void setExercise(double bmi) {
        this.exercise = exercise;
    }

    // GET METHODS
    public double getExercise() { return this.exercise; }
    public double getCalories() { return this.calories; }
    public double getSleep() { return this.sleep; }
    public String getName() { return this.name; }
    public Double getWeight() { return this.weight; }
    public Double getHeight() { return this.height; }
    public Double getBmi() { return this.bmi; }
}
