package com.tdmr.demo.two.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdmr.demo.two.R;
import com.tdmr.demo.two.databinding.ItemContactBinding;
import com.tdmr.demo.two.models.Contact;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by abigail on 6/6/2016.
 */
public class ContactsAdapter extends CursorAdapter implements StickyListHeadersAdapter {

    private final LayoutInflater mInflater;
    private final List<Contact> selectedIds;

    public ContactsAdapter(Context context, Cursor c, boolean autoRequery, List<Contact> selectedIds) {
        super(context, c, autoRequery);
        this.selectedIds = selectedIds;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.header_contact, parent, false);
        }
        ((TextView) convertView).setText("" + getItem(position).getDisplayName().subSequence(0, 1).charAt(0));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return getItem(position).getDisplayName().subSequence(0, 1).charAt(0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder holder = new ViewHolder(ItemContactBinding.inflate(mInflater, parent, false));
        View newView = holder.binding.getRoot();
        newView.setTag(holder);
        return newView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        Contact contact = Contact.fromCursor(cursor, context);
        holder.binding.setContact(contact);
        holder.binding.checkbox.setChecked(selectedIds.contains(contact));
    }

    @Override
    public Contact getItem(int position) {
        //converts cursor to contact object for easier referencing
        Cursor c = (Cursor) super.getItem(position);
        if (c != null)
            return Contact.fromCursor(c, mContext);
        return null;
    }

    class ViewHolder {
        public ViewHolder(ItemContactBinding binding) {
            this.binding = binding;
        }

        ItemContactBinding binding;
    }

}