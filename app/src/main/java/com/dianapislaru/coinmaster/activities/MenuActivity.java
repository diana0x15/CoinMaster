package com.dianapislaru.coinmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.dianapislaru.coinmaster.fragments.MonthFragment;
import com.dianapislaru.coinmaster.R;
import com.dianapislaru.coinmaster.fragments.TodayFragment;

/**
 * Created by Diana on 08/10/2016.
 */

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mItemLayout;
    private RelativeLayout mMoneyLayout;
    private Button mCancelButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mItemLayout = (RelativeLayout) findViewById(R.id.layout_menu_item_layout);
        mMoneyLayout = (RelativeLayout) findViewById(R.id.layout_menu_money_layout);
        mCancelButton = (Button) findViewById(R.id.layout_menu_cancel_button);

        mItemLayout.setOnClickListener(this);
        mMoneyLayout.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
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
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.layout_menu_item_layout:
                Intent intent = new Intent(MenuActivity.this, AddItemActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.layout_menu_money_layout:
                Intent intent2 = new Intent(MenuActivity.this, AddFundsActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.layout_menu_cancel_button:
                finish();
                break;
        }
    }
}
