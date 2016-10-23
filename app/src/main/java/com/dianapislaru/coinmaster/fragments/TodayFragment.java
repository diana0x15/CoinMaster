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
import com.dianapislaru.coinmaster.adapters.DaysAdapter;
import com.dianapislaru.coinmaster.adapters.ItemsAdapter;
import com.dianapislaru.coinmaster.objects.Item;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.ArrayList;

/**
 * Created by Diana on 08/10/2016.
 */

public class TodayFragment extends Fragment {

    private final String TAG = "TodayFragment";

    private ArrayList<LocalDate> mDays;
    public static ArrayList<Item> mItems;
    public static RecyclerView mDaysRecyclerView;
    private static RecyclerView mItemsRecyclerView;
    private DaysAdapter mDaysAdapter;
    public static ItemsAdapter mItemsAdapter;

    public static LocalDate mSelectedDate;
    public static double mMoneySpent = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        MainActivity.currentFragment = ListActivity.TODAY_FRAGMENT;

        initData(view);
        initWeekdaysView();

        return view;
    }

    private void initData(View view) {
        MainActivity.mTitleTextView.setText("TODAY");
        mMoneySpent = 0;
        mSelectedDate = new LocalDate();

        mDays = new ArrayList<>();
        mItems = new ArrayList<>();

        mDays.add(mSelectedDate.withDayOfWeek(DateTimeConstants.MONDAY));
        mDays.add(mSelectedDate.withDayOfWeek(DateTimeConstants.TUESDAY));
        mDays.add(mSelectedDate.withDayOfWeek(DateTimeConstants.WEDNESDAY));
        mDays.add(mSelectedDate.withDayOfWeek(DateTimeConstants.THURSDAY));
        mDays.add(mSelectedDate.withDayOfWeek(DateTimeConstants.FRIDAY));
        mDays.add(mSelectedDate.withDayOfWeek(DateTimeConstants.SATURDAY));
        mDays.add(mSelectedDate.withDayOfWeek(DateTimeConstants.SUNDAY));

        mDaysRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_main_date_recyclerView);
        mItemsRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_main_items_recyclerView);

        mDaysAdapter = new DaysAdapter(getActivity(), mDays);
        mItemsAdapter = new ItemsAdapter(mItems);
        RecyclerView.LayoutManager mLayoutManagerDays = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManagerItems = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mDaysRecyclerView.setLayoutManager(mLayoutManagerDays);
        mItemsRecyclerView.setLayoutManager(mLayoutManagerItems);
        mDaysRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mItemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mDaysRecyclerView.setAdapter(mDaysAdapter);
        mItemsRecyclerView.setAdapter(mItemsAdapter);

        mDaysAdapter.notifyDataSetChanged();
        mItemsAdapter.notifyDataSetChanged();

        setSelectedDay(mSelectedDate);

    }

    public void initWeekdaysView() {
        ViewTreeObserver vtoDate = mDaysRecyclerView.getViewTreeObserver();
        vtoDate.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mDaysRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                LinearLayoutManager dateLayoutManager = new LinearLayoutManager(getActivity());
                dateLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mDaysRecyclerView.setLayoutManager(dateLayoutManager);

                mDaysAdapter = new DaysAdapter(getActivity(), mDays);
                mDaysRecyclerView.setAdapter(mDaysAdapter);

                return true;
            }
        });

    }

    public static void setSelectedDay(LocalDate day) {
        MainActivity.mDateTextView.setText(day.toString("MMM dd, yyyy"));
        mSelectedDate = day;
        mMoneySpent = 0;

        mItems = new ArrayList<>();
        for(Item item : MainActivity.mItems) {
            if (item.getDate().equals(mSelectedDate.toString("dd-MM-yyyy"))) {
                if (checkItemIsInList(item.getKey())) {
                    mItems.add(item);
                    mMoneySpent += item.getPrice();
                }
            }
        }

        MainActivity.mFundsSpentTextView.setText("$" + mMoneySpent);
        mItemsAdapter = new ItemsAdapter(mItems);
        mItemsRecyclerView.setAdapter(mItemsAdapter);
        mItemsAdapter.notifyDataSetChanged();
    }

    public static boolean checkItemIsInList(String key) {
        for (Item i : mItems) {
            if (i.getKey() != null && key.equals(i.getKey()))
                return false;
        }
        return true;
    }

}
