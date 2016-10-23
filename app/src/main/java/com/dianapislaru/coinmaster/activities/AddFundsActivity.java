package com.dianapislaru.coinmaster.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dianapislaru.coinmaster.R;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Diana on 08/10/2016.
 */

public class AddFundsActivity extends AppCompatActivity {

    private Button mAddButton, mCancelButton;
    private EditText mEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_funds);

        mAddButton = (Button) findViewById(R.id.activity_add_funds_add_button);
        mCancelButton = (Button) findViewById(R.id.activity_add_funds_cancel_button);
        mEditText = (EditText) findViewById(R.id.activity_add_funds_editText);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add funds to database
                double funds = Double.parseDouble(mEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("funds").setValue(funds + MainActivity.mFunds);
                finish();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                checkDone();
            }
        });
    }

    private void checkDone() {
        if (mEditText.getText().toString().equals("")) {
            mAddButton.setBackground(getResources().getDrawable(R.drawable.gray_button));
        } else {
            mAddButton.setBackground(getResources().getDrawable(R.drawable.list_background));
        }
    }
}
