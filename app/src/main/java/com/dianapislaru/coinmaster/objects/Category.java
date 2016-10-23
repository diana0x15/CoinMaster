package com.dianapislaru.coinmaster.objects;

/**
 * Created by Diana on 23/10/2016.
 */

public class Category {
    private String mTitle;
    private double mPrice;


    public Category() {

    }

    public Category(String title, double price) {
        mTitle = title;
        mPrice = price;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }
}
