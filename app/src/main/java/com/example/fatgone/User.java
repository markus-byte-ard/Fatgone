package com.example.fatgone;

public class User {
    private String UID = "";
    private String name = "";
    private double weight = 50;
    private double height = 170;
    private double bmi = 99;

    public User() {
        this.setName("Adam");
        this.setWeight(80.0);
        this.setHeight(1.80);
        this.setBmi(0);
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

    public String getName() {
        return this.name;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Double getHeight() {
        return this.height;
    }

    public Double getBmi() {
        return this.bmi;
    }
}
