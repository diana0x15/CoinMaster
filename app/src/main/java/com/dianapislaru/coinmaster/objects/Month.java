package com.dianapislaru.coinmaster.objects;

/**
 * Created by Diana on 08/10/2016.
 */

public class Month {

    private final String TAG = "Month";

    private String mCategory;
    private int mPrice;

    public Month(String category, int price) {
        mCategory = category;
        mPrice = price;
    }

    public String getCategory() {
        return mCategory;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

}
