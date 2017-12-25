package com.isla.contactsapp.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.isla.contactsapp.models.PhoneBookContact;

import java.util.ArrayList;
import java.util.List;

public class ContactsDBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "ContactsAppDB.db";
    private static final String SQL_CREATE_CONTACTS_TABLE =
            "CREATE TABLE " + DatabaseContract.TABLE_CONTACTS + " (" +
                    DatabaseContract._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.COLUMN_NAME + " TEXT, " +
                    DatabaseContract.COLUMN_EMAIL + " TEXT, " +
                    DatabaseContract.COLUMN_BIRTHDAY + " TEXT, " +
                    DatabaseContract.COLUMN_PHONENUMBER + " TEXT, " +
                    DatabaseContract.COLUMN_PHOTO_PATH + " TEXT " +
                    ");";

    private static ContactsDBHelper sInstance;

    private ContactsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static ContactsDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ContactsDBHelper(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_CONTACTS);
        onCreate(db);
    }

    public List<PhoneBookContact> getAllContacts() {
        List<PhoneBookContact> contactList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String phoneNumber;
        String name;
        String photoUri;
        String email;
        String birthday;
        int id;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.TABLE_CONTACTS, null);
        if (cursor.moveToFirst()) {
            do {
                phoneNumber = cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_PHONENUMBER));
                name = cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_NAME));
                photoUri = cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_PHOTO_PATH));
                email = cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_EMAIL));
                birthday = cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_BIRTHDAY));
                id = cursor.getInt(cursor.getColumnIndex(DatabaseContract._ID));
                contactList.add(new PhoneBookContact(phoneNumber, name, photoUri, email, birthday, id));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }

    public PhoneBookContact getDetailContact(int id) {
        return null;
    }

}
