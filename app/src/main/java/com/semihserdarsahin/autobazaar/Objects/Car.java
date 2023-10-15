package com.semihserdarsahin.autobazaar.Objects;

import java.io.Serializable;

public class Car implements Serializable {
    public String title;
    public String km;
    public String price;
    public String brand;
    public String fuel;
    public String phone;
    public String year;
    public String enginepower;
    public String firstLink;
    public String secondLink;
    public String thirdLink;

    public Car(String title, String km, String price, String brand, String fuel, String phone, String year, String enginepower, String firstLink, String secondLink, String thirdLink) {
        this.title = title;
        this.km = km;
        this.price = price;
        this.brand = brand;
        this.fuel = fuel;
        this.phone = phone;
        this.year = year;
        this.enginepower = enginepower;
        this.firstLink = firstLink;
        this.secondLink = secondLink;
        this.thirdLink = thirdLink;
    }
}
