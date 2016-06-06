package com.tdmr.demo.two;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.tdmr.demo.two.adapters.ContactsAdapter;
import com.tdmr.demo.two.adapters.PicsAdapter;
import com.tdmr.demo.two.databinding.ActivityContactListBinding;
import com.tdmr.demo.two.models.Contact;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ContactListActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private ActivityContactListBinding binding;

    private ArrayList<Contact> selectedContacts = new ArrayList<>();
    private String mCurFilter;

    private ContactsAdapter mAdapter;
    private PicsAdapter mPicsAdaprer = new PicsAdapter(selectedContacts);

    // These are the Contacts rows that we will retrieve.
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
    };
    private TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            onQueryTextChange(s.toString());
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list);

        mAdapter = new ContactsAdapter(this, null, true, selectedContacts);
        binding.recycler.setAdapter(mPicsAdaprer);
        binding.search.addTextChangedListener(mWatcher);

        StickyListHeadersListView listView = binding.list;
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = mAdapter.getItem(position);
                if (contact != null)
                    updateSelection(contact, selectedContacts.contains(contact));
            }
        });


        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void updateSelection(Contact contact, boolean isPreviouslySelected) {
        if (isPreviouslySelected)
            selectedContacts.remove(contact);
        else selectedContacts.add(contact);

        mPicsAdaprer.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    public void onMenuItemClicked(MenuItem item) {
        // there's only one action item which is "Done", no need for checking
        Intent data = new Intent();
        data.putParcelableArrayListExtra("contacts", selectedContacts);
        setResult(RESULT_OK, data);
        finish();
    }

    private boolean onQueryTextChange(String newText) {
        mCurFilter = !TextUtils.isEmpty(newText) ? newText : null;
        getSupportLoaderManager().restartLoader(0, null, this);
        return true;
    }

    // Loader methods
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri;

        if (mCurFilter != null) { // uses content_filter_uri to get filtered data
            baseUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI,
                    Uri.encode(mCurFilter));
        } else {
            baseUri = ContactsContract.Contacts.CONTENT_URI;
        }

        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";

        return new CursorLoader(this, baseUri,
                CONTACTS_SUMMARY_PROJECTION, select, null,
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
