package com.isla.contactsapp.data;

import android.provider.BaseColumns;

public interface DatabaseContract extends BaseColumns {
    String TABLE_CONTACTS = "contacts";
    String COLUMN_NAME = "name";
    String COLUMN_PHONENUMBER = "phonenumber";
    String COLUMN_EMAIL = "email";
    String COLUMN_BIRTHDAY = "birthday";
    String COLUMN_PHOTO_PATH = "photopath";
}
