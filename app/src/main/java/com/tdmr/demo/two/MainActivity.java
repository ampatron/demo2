package com.tdmr.demo.two;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CODE_PICK_CONTACTS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        // we only have one button
        launchContactPicker();
    }

    private void launchContactPicker() {
        Intent contactPickerIntent = new Intent(getApplicationContext(), ContactListActivity.class);
        startActivityForResult(contactPickerIntent, CODE_PICK_CONTACTS);
    }
}
