package com.tdmr.demo.two.models;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import com.tdmr.demo.two.DbUtils;

/**
 * Created by abigail on 6/6/2016.
 */
public class Contact implements Parcelable {
    private long id;
    private String displayName;
    private String phoneNumber;
    private Uri photoUri;

    private Contact() {

    }

    protected Contact(Parcel in) {
        id = in.readLong();
        displayName = in.readString();
        phoneNumber = in.readString();
        photoUri = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(displayName);
        dest.writeString(phoneNumber);
        dest.writeParcelable(photoUri, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    @Override
    public int hashCode() {
        return (int) id + displayName.hashCode() + photoUri.hashCode() + phoneNumber.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return ((o instanceof Contact) && ((Contact) o).id == id);
    }

    public static Contact fromCursor(Cursor cursor, Context context) {
        Contact contact = new Contact();
        contact.id = cursor.getLong(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
        contact.displayName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
        contact.photoUri = DbUtils.getPhotoUri(cursor.getLong(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)));
        contact.phoneNumber = DbUtils.retrieveContactNumber(context, cursor.getLong(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)));

        return contact;
    }

}
