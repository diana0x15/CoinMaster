package com.dianapislaru.coinmaster.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.dianapislaru.coinmaster.R;
import com.dianapislaru.coinmaster.activities.ListActivity;
import com.dianapislaru.coinmaster.activities.MainActivity;
import com.dianapislaru.coinmaster.adapters.CategoryAdapter;
import com.dianapislaru.coinmaster.adapters.MonthsAdapter;
import com.dianapislaru.coinmaster.objects.Category;
import com.dianapislaru.coinmaster.objects.Item;

import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import static com.dianapislaru.coinmaster.activities.MainActivity.displayWidth;

/**
 * Created by Diana on 08/10/2016.
 */

public class MonthFragment extends Fragment {

    private final String TAG = "MonthFragment";

    private ArrayList<LocalDate> mMonths;
    public static ArrayList<Item> mItems;
    public static ArrayList<Category> mCategories;
    public static RecyclerView mMonthsRecyclerView;
    private static RecyclerView mCategoriesRecyclerView;
    public static MonthsAdapter mMonthsAdapter;
    public static CategoryAdapter mCategoryAdapter;

    public static int finalWidthDate;
    public static int itemWidthDate;
    public static int paddingDate;
    public static int firstItemWidthDate;
    public static int allPixelsDate = 0;

    public static LocalDate mSelectedDate;
    public static double mMoneySpent = 0;
    public static double mGroceries = 0;
    public static double mBills = 0;
    public static double mShopping = 0;
    public static double mFood = 0;
    public static double mEntertainment = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        MainActivity.currentFragment = ListActivity.MONTH_FRAGMENT;

        initData(view);
        getRecyclerviewDate();

        return view;
    }

    private void initData(View view) {
        mSelectedDate = new LocalDate();
        MainActivity.mTitleTextView.setText(mSelectedDate.monthOfYear().getAsText() + " " + mSelectedDate.toString("yyyy"));
        MainActivity.mDateTextView.setText("");

        mMonths = new ArrayList<>();
        mItems = new ArrayList<>();
        mCategories = new ArrayList<>();

        mMonthsRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_main_date_recyclerView);
        mCategoriesRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_main_items_recyclerView);


        generateTestData();

        mMonthsAdapter = new MonthsAdapter(getActivity(), mMonths);
        mCategoryAdapter = new CategoryAdapter(mCategories);
        RecyclerView.LayoutManager mLayoutManagerItems = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mCategoriesRecyclerView.setLayoutManager(mLayoutManagerItems);
        mCategoriesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCategoriesRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryAdapter.notifyDataSetChanged();



        setSelectedDay(mSelectedDate);
    }

    private void generateTestData() {
        LocalDate today = new LocalDate();
        LocalDate oneYearAgo = today.minusMonths(24);
        LocalDate nextYear = today.plusMonths(24);

        int months = Months.monthsBetween(oneYearAgo, nextYear).getMonths();
        for (int i=0; i < months; i++) {
            LocalDate month = oneYearAgo.withFieldAdded(DurationFieldType.months(), i);
            mMonths.add(month);
        }
    }

    public void getRecyclerviewDate() {
        ViewTreeObserver vtoDate = mMonthsRecyclerView.getViewTreeObserver();
        vtoDate.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mMonthsRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                finalWidthDate = displayWidth;
                itemWidthDate = displayWidth / 7;
                paddingDate = (finalWidthDate - itemWidthDate) / 2;
                firstItemWidthDate = paddingDate ;
                allPixelsDate = 0;

                final LinearLayoutManager dateLayoutManager = new LinearLayoutManager(getActivity());
                dateLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mMonthsRecyclerView.setLayoutManager(dateLayoutManager);
                mMonthsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        synchronized (this) {
                            if(newState == RecyclerView.SCROLL_STATE_IDLE){
                                calculatePositionAndScrollDate(recyclerView);
                            }
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        allPixelsDate += dx;
                    }
                });

                mMonthsAdapter = new MonthsAdapter(getActivity(), mMonths);
                mMonthsRecyclerView.setAdapter(mMonthsAdapter);
                mMonthsRecyclerView.scrollToPosition(21);

                return true;
            }
        });

    }

    private void calculatePositionAndScrollDate(RecyclerView recyclerView) {
        int expectedPositionDate = Math.round((allPixelsDate + paddingDate - firstItemWidthDate) / itemWidthDate);

        if (expectedPositionDate == -1) {
            expectedPositionDate = 0;
        } else if (expectedPositionDate >= recyclerView.getAdapter().getItemCount() - 2) {
            expectedPositionDate--;
        }
        scrollListToPositionDate(recyclerView, expectedPositionDate);
    }

    private void scrollListToPositionDate(RecyclerView recyclerView, int expectedPositionDate) {
        float targetScrollPosDate = expectedPositionDate * itemWidthDate + firstItemWidthDate - paddingDate;
        float missingPxDate = targetScrollPosDate - allPixelsDate;
        if (missingPxDate != 0) {
            recyclerView.smoothScrollBy((int) missingPxDate, 0);
        }
        setDateValue();
    }

    private void setDateValue() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) mMonthsRecyclerView.getLayoutManager());
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        mMonthsAdapter.setSelectedItem(firstVisiblePosition + 3);
    }

    public static void setSelectedDay(LocalDate day) {
        MainActivity.mTitleTextView.setText(mSelectedDate.monthOfYear().getAsText() + " " + mSelectedDate.toString("yyyy"));
        MainActivity.mDateTextView.setText("");
        mSelectedDate = day;
        mMoneySpent = 0;

        mItems = new ArrayList<>();
        mCategories = new ArrayList<>();

        for(Item item : MainActivity.mItems) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
            LocalDate itemDate = LocalDate.parse(item.getDate(), formatter);
            if (itemDate.toString("MM-yyyy").equals(mSelectedDate.toString("MM-yyyy"))) {
                if (checkItemIsInList(item.getKey())) {
                    mItems.add(item);
                    mMoneySpent += item.getPrice();
                }
            }
        }

        for(Item item : mItems) {

            int found = -1;
            for (int i = 0; i < MonthFragment.mCategories.size(); ++i) {
                if (MonthFragment.mCategories.get(i).getTitle().equals(item.getCategory()))
                    found = i;
            }

            if (found != -1) {
                double price = MonthFragment.mCategories.get(found).getPrice();
                mCategories.get(found).setPrice(price + item.getPrice());
            } else {
                mCategories.add(new Category(item.getCategory(), item.getPrice()));
            }
        }

        MainActivity.mFundsSpentTextView.setText("$" + mMoneySpent);
        mCategoryAdapter = new CategoryAdapter(mCategories);
        mCategoriesRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryAdapter.notifyDataSetChanged();
    }


    public static boolean checkItemIsInList(String key) {
        for (Item i : mItems) {
            if (i.getKey() != null && key.equals(i.getKey()))
                return false;
        }
        return true;
    }

    public static void setCategory(Item item) {
        switch (item.getCategory()) {
            case "Groceries":
                mGroceries += item.getPrice();
                break;
            case "Bills":
                mBills += item.getPrice();
                break;
            case "Shopping":
                mShopping += item.getPrice();
                break;
            case "Food":
                mFood += item.getPrice();
                break;
            case "Entertainment":
                mEntertainment = item.getPrice();
                break;
        }
    }

}
