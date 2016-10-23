package com.dianapislaru.coinmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.dianapislaru.coinmaster.R;

/**
 * Created by Diana on 05/10/2016.
 */

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "PasswordActivity";

    private int mDigit1;
    private int mDigit2;
    private int mDigit3;
    private int mDigit4;
    private int mTypedDigits;

    private RelativeLayout mButton0;
    private RelativeLayout mButton1;
    private RelativeLayout mButton2;
    private RelativeLayout mButton3;
    private RelativeLayout mButton4;
    private RelativeLayout mButton5;
    private RelativeLayout mButton6;
    private RelativeLayout mButton7;
    private RelativeLayout mButton8;
    private RelativeLayout mButton9;
    private RelativeLayout mButtonBack;

    private RelativeLayout mDot1;
    private RelativeLayout mDot2;
    private RelativeLayout mDot3;
    private RelativeLayout mDot4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        mTypedDigits = 0;
        mButton0 = (RelativeLayout) findViewById(R.id.activity_password_digit0);
        mButton1 = (RelativeLayout) findViewById(R.id.activity_password_digit1);
        mButton2 = (RelativeLayout) findViewById(R.id.activity_password_digit2);
        mButton3 = (RelativeLayout) findViewById(R.id.activity_password_digit3);
        mButton4 = (RelativeLayout) findViewById(R.id.activity_password_digit4);
        mButton5 = (RelativeLayout) findViewById(R.id.activity_password_digit5);
        mButton6 = (RelativeLayout) findViewById(R.id.activity_password_digit6);
        mButton7 = (RelativeLayout) findViewById(R.id.activity_password_digit7);
        mButton8 = (RelativeLayout) findViewById(R.id.activity_password_digit8);
        mButton9 = (RelativeLayout) findViewById(R.id.activity_password_digit9);
        mButtonBack = (RelativeLayout) findViewById(R.id.activity_password_button_back);

        mDot1 = (RelativeLayout) findViewById(R.id.activity_password_dot1);
        mDot2 = (RelativeLayout) findViewById(R.id.activity_password_dot2);
        mDot3 = (RelativeLayout) findViewById(R.id.activity_password_dot3);
        mDot4 = (RelativeLayout) findViewById(R.id.activity_password_dot4);

        mButton0.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeDigit();
            }
        });

    }

    private void addDigit(int digit) {
        if (mTypedDigits == 4) {
            return;
        }

        mTypedDigits++;
        switch (mTypedDigits) {
            case 1:
                mDigit1 = digit;
                mDot1.setVisibility(View.VISIBLE);
                break;
            case 2:
                mDigit2 = digit;
                mDot2.setVisibility(View.VISIBLE);
                break;
            case 3:
                mDigit3 = digit;
                mDot3.setVisibility(View.VISIBLE);
                break;
            case 4:
                mDigit4 = digit;
                mDot4.setVisibility(View.VISIBLE);
                Intent intent = new Intent(this, MainActivity.class);
                if (checkPassword()) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    private void removeDigit() {
        if (mTypedDigits == 0) {
            return;
        }

        switch (mTypedDigits) {
            case 1:
                mDigit1 = -1;
                mDot1.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mDigit2 = -1;
                mDot2.setVisibility(View.INVISIBLE);
                break;
            case 3:
                mDigit3 = -1;
                mDot3.setVisibility(View.INVISIBLE);
                break;
            case 4:
                mDigit4 = -1;
                mDot4.setVisibility(View.INVISIBLE);
                break;
        }
        mTypedDigits--;
    }

    private boolean checkPassword() {
        if (mDigit1 != 0 || mDigit2 != 0 || mDigit3 != 0 || mDigit4 != 0)
            return false;
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_password_digit0: {
                addDigit(0);
                break;
            }
            case R.id.activity_password_digit1: {
                addDigit(1);
                break;
            }
            case R.id.activity_password_digit2: {
                addDigit(2);
                break;
            }
            case R.id.activity_password_digit3: {
                addDigit(3);
                break;
            }
            case R.id.activity_password_digit4: {
                addDigit(4);
                break;
            }
            case R.id.activity_password_digit5: {
                addDigit(5);
                break;
            }
            case R.id.activity_password_digit6: {
                addDigit(6);
                break;
            }
            case R.id.activity_password_digit7: {
                addDigit(7);
                break;
            }
            case R.id.activity_password_digit8: {
                addDigit(8);
                break;
            }
            case R.id.activity_password_digit9: {
                addDigit(9);
                break;
            }
        }
    }
}
