package com.tdmr.demo.two;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by abigail on 6/6/2016.
 */
public class DbUtils {
    public static Uri getPhotoUri(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        return Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }
}
