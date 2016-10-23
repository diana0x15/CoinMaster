package com.dianapislaru.coinmaster.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dianapislaru.coinmaster.fragments.MonthFragment;
import com.dianapislaru.coinmaster.R;
import com.dianapislaru.coinmaster.fragments.TodayFragment;

/**
 * Created by Diana on 08/10/2016.
 */

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "ListActivity";

    public static final int TODAY_FRAGMENT = 1;
    public static final int MONTH_FRAGMENT = 2;

    private ImageView mTodayCheck, mMonthCheck;
    private RelativeLayout mTodayLayout, mMonthLayout;
    private Button mCancelButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mode);

        mTodayCheck = (ImageView) findViewById(R.id.layout_list_today_check);
        mMonthCheck = (ImageView) findViewById(R.id.layout_list_month_check);
        mTodayLayout = (RelativeLayout) findViewById(R.id.layout_list_today_layout);
        mMonthLayout = (RelativeLayout) findViewById(R.id.layout_list_month_layout);
        mCancelButton = (Button) findViewById(R.id.layout_list_cancel_button);

        mTodayLayout.setOnClickListener(this);
        mMonthLayout.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        if (MainActivity.currentFragment == TODAY_FRAGMENT) {
            mTodayCheck.setVisibility(View.VISIBLE);
            mMonthCheck.setVisibility(View.INVISIBLE);
        } else {
            mMonthCheck.setVisibility(View.VISIBLE);
            mTodayCheck.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        if (TodayFragment.mDaysRecyclerView != null) {
            TodayFragment.mDaysRecyclerView.setBackgroundColor(getResources().getColor(R.color.black));
        }
        if (MonthFragment.mMonthsRecyclerView != null) {
            MonthFragment.mMonthsRecyclerView.setBackgroundColor(getResources().getColor(R.color.black));
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_list_today_layout:
                MainActivity.change = TODAY_FRAGMENT;
                finish();
                break;
            case R.id.layout_list_month_layout:
                MainActivity.change = MONTH_FRAGMENT;
                finish();
                break;
            case R.id.layout_list_cancel_button:
                finish();
                break;
        }
    }
}
