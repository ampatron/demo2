package com.tdmr.demo.two;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tdmr.demo.two.models.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CODE_PICK_CONTACTS = 1;
    private TextView mSelectedVw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSelectedVw = ((TextView) findViewById(android.R.id.text1));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_PICK_CONTACTS && resultCode == RESULT_OK && data != null) {
            displaySelectedContacts(data.getParcelableArrayListExtra("contacts"));
        }
    }

    private void displaySelectedContacts(ArrayList<Parcelable> contacts) {
        String display = "";
        for (int i = 0; i < contacts.size(); ++i) {
            Contact contact = (Contact) contacts.get(i);
            display += contact.getDisplayName() + "\n"; //Appends new line character per contact for better view.
        }

        mSelectedVw.setText(display);
    }

    @Override
    public void onClick(View v) {
        // we only have one button(Select Contacts button) ; no need for checking
        launchContactPicker();
    }

    private void launchContactPicker() {
        Intent contactPickerIntent = new Intent(getApplicationContext(), ContactListActivity.class);
        startActivityForResult(contactPickerIntent, CODE_PICK_CONTACTS);
    }
}
