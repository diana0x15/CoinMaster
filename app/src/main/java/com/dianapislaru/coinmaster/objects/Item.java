package com.dianapislaru.coinmaster.objects;

/**
 * Created by Diana on 05/10/2016.
 */

public class Item {

    private String mTitle;
    private String mCategory;
    private String mDate;
    private double mPrice;
    private String mKey;


    public Item() {

    }

    public Item(String title, String category, String date, double price) {
        mTitle = title;
        mCategory = category;
        mDate = date;

        mPrice = price;

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getKey() {
        return mKey;
    }

}
