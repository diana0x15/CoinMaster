package com.dianapislaru.coinmaster.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianapislaru.coinmaster.R;
import com.dianapislaru.coinmaster.Utils;
import com.dianapislaru.coinmaster.fragments.MonthFragment;
import com.dianapislaru.coinmaster.fragments.TodayFragment;
import com.dianapislaru.coinmaster.objects.Category;
import com.dianapislaru.coinmaster.objects.Item;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = "MainActivity";

    public static TextView mTitleTextView;
    public static TextView mDateTextView;
    public static TextView mFundsSpentTextView;
    public static TextView mFundsLeftTextView;

    private ImageView mArrowButton, mMenuButton;

    public static FragmentManager fragmentManager;
    public static int displayWidth;
    public static int displayHeight;
    public static int currentFragment;
    public static int change = -1;
    public static Resources resources;
    public static double mFunds;
    public static ArrayList<Item> mItems;

    private static DatabaseReference mRootReference = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference mFundsReference;
    private static DatabaseReference mItemsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayHeight = Utils.getDisplayHeight(this);
        displayWidth = Utils.getDisplayWidth(this);
        resources = getResources();
        fragmentManager = getSupportFragmentManager();
        mItems = new ArrayList<>();

        mTitleTextView = (TextView) findViewById(R.id.activity_main_title_text);
        mDateTextView = (TextView) findViewById(R.id.activity_main_title_date);
        mFundsLeftTextView = (TextView) findViewById(R.id.activity_main_funds_left_textView);
        mFundsSpentTextView = (TextView) findViewById(R.id.activity_main_funds_spent_textView);
        mArrowButton = (ImageView) findViewById(R.id.activity_main_title_arrow);
        mMenuButton = (ImageView) findViewById(R.id.activity_main_menu);

        mArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideRecyclerView();
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideRecyclerView();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        if (change == 2) {
            openFragment(new MonthFragment());
        } else {
            openFragment(new TodayFragment());
        }
        change = -1;

        mFundsReference = mRootReference.child("funds");
        mItemsReference = mRootReference.child("items");
    }

    public static void hideRecyclerView() {
        if (TodayFragment.mDaysRecyclerView != null) {
            TodayFragment.mDaysRecyclerView.setBackgroundColor(resources.getColor(R.color.white));
        }
        if (MonthFragment.mMonthsRecyclerView != null) {
            MonthFragment.mMonthsRecyclerView.setBackgroundColor(resources.getColor(R.color.white));
        }
    }

    public static void openFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_main_fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (change == 2) {
            openFragment(new MonthFragment());
        } else {
            openFragment(new TodayFragment());
        }
        change = -1;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.layout_menu_item_layout:
                break;
            case R.id.layout_menu_money_layout:
                break;
            case R.id.layout_menu_cancel_button:
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFundsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mFundsLeftTextView != null) {
                    if (dataSnapshot.getValue() != null) {
                        mFunds = dataSnapshot.getValue(Double.class);
                        mFundsLeftTextView.setText("$" + mFunds);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mItemsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Item item = dataSnapshot.getValue(Item.class);
                String key = dataSnapshot.getKey();

                if (checkItemIsInList(dataSnapshot.getKey())) {
                    mItems.add(item);
                }

                if (TodayFragment.mItems != null && TodayFragment.checkItemIsInList(key)) {
                    if (TodayFragment.mSelectedDate == null)
                        return;
                    if (TodayFragment.mSelectedDate.toString("dd-MM-yyyy").equals(item.getDate())) {
                        TodayFragment.mItems.add(item);
                        if (TodayFragment.mItemsAdapter == null)
                            return;

                        TodayFragment.mItemsAdapter.notifyDataSetChanged();
                        TodayFragment.mMoneySpent += item.getPrice();
                        mFundsSpentTextView.setText("$" + TodayFragment.mMoneySpent);
                    }
                }

                if (MonthFragment.mItems != null && MonthFragment.checkItemIsInList(key)) {
                    if (MonthFragment.mSelectedDate == null)
                        return;

                    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
                    LocalDate itemDate = LocalDate.parse(item.getDate(), formatter);
                    if (MonthFragment.mSelectedDate.toString("MM-yyyy").equals(itemDate.toString("MM-yyyy"))) {

                        MonthFragment.mItems.add(item);
                        int found = -1;
                        for(int i = 0; i < MonthFragment.mCategories.size(); ++i) {
                            if (MonthFragment.mCategories.get(i).getTitle().equals(item.getCategory()))
                                found = i;
                        }

                        if (found != -1) {
                            double price = MonthFragment.mCategories.get(found).getPrice();
                            MonthFragment.mCategories.get(found).setPrice(price + item.getPrice());
                            Log.i(TAG, "found category " + item.getCategory());
                        } else {
                            MonthFragment.mCategories.add(new Category(item.getCategory(), item.getPrice()));
                            Log.i(TAG, "added category " + item.getCategory());
                        }

                        if (MonthFragment.mCategoryAdapter == null)
                            return;

                        MonthFragment.mCategoryAdapter.notifyDataSetChanged();
                        MonthFragment.mMoneySpent += item.getPrice();
                        mFundsSpentTextView.setText("$" + MonthFragment.mMoneySpent);

                        MonthFragment.setCategory(item);
                    }
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    private boolean checkItemIsInList(String key) {
        for (Item i : mItems) {
            if (key.equals(i.getKey()))
                return false;
        }
        return true;
    }

}
