package com.dianapislaru.coinmaster.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dianapislaru.coinmaster.R;
import com.dianapislaru.coinmaster.objects.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.LocalDate;

import static com.dianapislaru.coinmaster.R.id.activity_add_item_add_button;
import static com.dianapislaru.coinmaster.R.id.activity_add_item_category_layout;
import static com.dianapislaru.coinmaster.R.id.activity_add_item_price_layout;

/**
 * Created by Diana on 08/10/2016.
 */

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mCancelButton, mAddButton, mPriceButton, mNameButton;
    private RelativeLayout mNameLayout, mCategoryLayout, mPriceLayout;
    private RelativeLayout mGroceries, mBills, mShopping, mEntertainment, mFood;
    private ScrollView mCategoriesScrollView;
    private TextView mNameTextView, mCategoryTextView, mPriceTextView;
    private LinearLayout mListLayout, mButtonsLayout, mEnterPriceLayout, mEnterNameLayout;
    private EditText mPriceEditText, mNameEditText;


    private String mName = "", mCategory = "";
    private double mPrice = 0;
    private LocalDate mCurrentDate;

    private DatabaseReference mItemsReference;
    private DatabaseReference mFundsReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mCurrentDate = new LocalDate();
        mFundsReference = FirebaseDatabase.getInstance().getReference().child("funds");
        mItemsReference = FirebaseDatabase.getInstance().getReference().child("items");

        mCancelButton = (Button) findViewById(R.id.activity_add_item_cancel_button);
        mAddButton = (Button) findViewById(activity_add_item_add_button);
        mNameLayout = (RelativeLayout) findViewById(R.id.activity_add_item_name_layout);
        mCategoryLayout = (RelativeLayout) findViewById(activity_add_item_category_layout);
        mPriceLayout = (RelativeLayout) findViewById(activity_add_item_price_layout);
        mGroceries = (RelativeLayout) findViewById(R.id.category_groceries);
        mBills = (RelativeLayout) findViewById(R.id.category_bills);
        mShopping = (RelativeLayout) findViewById(R.id.category_shopping);
        mEntertainment = (RelativeLayout) findViewById(R.id.category_entertainment);
        mFood = (RelativeLayout) findViewById(R.id.category_food);
        mCategoriesScrollView = (ScrollView) findViewById(R.id.activity_add_item_category_scrollview);
        mNameTextView = (TextView) findViewById(R.id.activity_add_item_name_text);
        mCategoryTextView = (TextView) findViewById(R.id.activity_add_item_category_text);
        mPriceTextView = (TextView) findViewById(R.id.activity_add_item_price_text);
        mListLayout = (LinearLayout) findViewById(R.id.activity_add_item_list);
        mButtonsLayout = (LinearLayout) findViewById(R.id.activity_add_item_buttons_layout);
        mEnterPriceLayout = (LinearLayout) findViewById(R.id.enter_price_layout);
        mEnterNameLayout = (LinearLayout) findViewById(R.id.enter_name_layout);
        mPriceEditText = (EditText) findViewById(R.id.price_editText);
        mPriceButton = (Button) findViewById(R.id.price_button);
        mNameButton = (Button) findViewById(R.id.name_button);
        mNameEditText = (EditText) findViewById(R.id.name_editText);

        mAddButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        mNameLayout.setOnClickListener(this);
        mCategoryLayout.setOnClickListener(this);
        mPriceLayout.setOnClickListener(this);
        mGroceries.setOnClickListener(this);
        mBills.setOnClickListener(this);
        mShopping.setOnClickListener(this);
        mFood.setOnClickListener(this);
        mEntertainment.setOnClickListener(this);
        mPriceButton.setOnClickListener(this);
        mNameButton.setOnClickListener(this);

    }

    private void showCategories(boolean show) {
        if (show) {
            mCategoriesScrollView.setVisibility(View.VISIBLE);
            mButtonsLayout.setVisibility(View.GONE);
            mListLayout.setVisibility(View.GONE);
        } else {
            mCategoriesScrollView.setVisibility(View.GONE);
            mButtonsLayout.setVisibility(View.VISIBLE);
            mListLayout.setVisibility(View.VISIBLE);

            checkDone();
        }
    }

    private void showEnterPrice(boolean show) {
        if (show) {
            mEnterPriceLayout.setVisibility(View.VISIBLE);
            mButtonsLayout.setVisibility(View.GONE);
            mListLayout.setVisibility(View.GONE);
        } else {
            mEnterPriceLayout.setVisibility(View.GONE);
            mButtonsLayout.setVisibility(View.VISIBLE);
            mListLayout.setVisibility(View.VISIBLE);

            checkDone();
        }
    }

    private void showEnterName(boolean show) {
        if (show) {
            mEnterNameLayout.setVisibility(View.VISIBLE);
            mButtonsLayout.setVisibility(View.GONE);
            mListLayout.setVisibility(View.GONE);
        } else {
            mEnterNameLayout.setVisibility(View.GONE);
            mButtonsLayout.setVisibility(View.VISIBLE);
            mListLayout.setVisibility(View.VISIBLE);

            checkDone();
        }
    }

    private void checkDone() {
        if (mNameTextView.getText().equals("Set Item Name"))
            return;
        if (mCategoryTextView.getText().equals("Select Category"))
            return;
        if (mPriceTextView.getText().equals("Enter Amount"))
            return;

        mAddButton.setBackground(getResources().getDrawable(R.drawable.list_background));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.activity_add_item_cancel_button:
                finish();
                break;
            case R.id.activity_add_item_add_button:
                if (!(mName.equals("")) && !(mCategory.equals("")) && mPrice != 0) {
                    LocalDate date = new LocalDate();
                    Item item = new Item(mName, mCategory, date.toString("dd-MM-yyyy"), mPrice);
                    mItemsReference.push().setValue(item);
                    mFundsReference.setValue(MainActivity.mFunds - mPrice);
                }
                finish();
                break;
            case R.id.activity_add_item_category_layout:
                showCategories(true);
                break;
            case R.id.activity_add_item_name_layout:
                showEnterName(true);
                break;
            case R.id.activity_add_item_price_layout:
                showEnterPrice(true);
                break;
            case R.id.category_groceries:
                showCategories(false);
                mCategoryTextView.setText("Groceries");
                mCategoryTextView.setTextColor(getResources().getColor(R.color.black));
                mCategory = "Groceries";
                checkDone();
                break;
            case R.id.category_bills:
                showCategories(false);
                mCategoryTextView.setText("Bills");
                mCategoryTextView.setTextColor(getResources().getColor(R.color.black));
                mCategory = "Bills";
                checkDone();
                break;
            case R.id.category_shopping:
                showCategories(false);
                mCategoryTextView.setText("Shopping");
                mCategoryTextView.setTextColor(getResources().getColor(R.color.black));
                mCategory = "Shopping";
                checkDone();
                break;
            case R.id.category_entertainment:
                showCategories(false);
                mCategoryTextView.setText("Entertainment");
                mCategoryTextView.setTextColor(getResources().getColor(R.color.black));
                mCategory = "Entertainment";
                checkDone();
                break;
            case R.id.category_food:
                showCategories(false);
                mCategoryTextView.setText("Food");
                mCategoryTextView.setTextColor(getResources().getColor(R.color.black));
                mCategory = "Food";
                checkDone();
                break;
            case R.id.price_button:
                showEnterPrice(false);
                mPrice = Double.parseDouble(mPriceEditText.getText().toString());
                mPriceTextView.setText("$" + mPrice);
                mPriceTextView.setTextColor(getResources().getColor(R.color.black));
                checkDone();
                break;
            case R.id.name_button:
                showEnterName(false);
                mName = mNameEditText.getText().toString();
                mNameTextView.setText(mName);
                mNameTextView.setTextColor(getResources().getColor(R.color.black));
                checkDone();
                break;
        }
    }
}
