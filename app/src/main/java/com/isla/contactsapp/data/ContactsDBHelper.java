package com.isla.contactsapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.isla.contactsapp.ContactsApp;
import com.isla.contactsapp.models.PhoneBookContact;

import java.util.ArrayList;
import java.util.List;

public class ContactsDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ContactsAppDB.db";
    private static final String SQL_CREATE_CONTACTS_TABLE =
            "CREATE TABLE " + DatabaseContract.TABLE_CONTACTS + " (" +
                    DatabaseContract._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.COLUMN_NAME + " TEXT, " +
                    DatabaseContract.COLUMN_EMAIL + " TEXT, " +
                    DatabaseContract.COLUMN_BIRTHDAY + " TEXT, " +
                    DatabaseContract.COLUMN_PHONENUMBER + " TEXT, " +
                    DatabaseContract.COLUMN_PHOTO_PATH + " TEXT " +
                    DatabaseContract.COLUMN_EVENT_ID + " INTEGER " +");";

    private static ContactsDBHelper sInstance;

    private ContactsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static ContactsDBHelper getInstance() {
        if (sInstance == null) {
            sInstance = new ContactsDBHelper(ContactsApp.getsInstance());
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
        long eventId;
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
                eventId = cursor.getLong(cursor.getColumnIndex(DatabaseContract.COLUMN_EVENT_ID));
                contactList.add(new PhoneBookContact(phoneNumber, name, photoUri, email, birthday, id, eventId));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }

    public PhoneBookContact getDetailContact(int id) {
        SQLiteDatabase db = getReadableDatabase();
        PhoneBookContact contact = null;
        String phoneNumber;
        String name;
        String photoUri;
        String email;
        String birthday;
        int contactId;
        long eventId;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.TABLE_CONTACTS +
                " where " + DatabaseContract._ID + " = " + id, null);
        if (cursor.moveToNext()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_PHONENUMBER));
            name = cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_NAME));
            photoUri = cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_PHOTO_PATH));
            email = cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_EMAIL));
            birthday = cursor.getString(cursor.getColumnIndex(DatabaseContract.COLUMN_BIRTHDAY));
            contactId = cursor.getInt(cursor.getColumnIndex(DatabaseContract._ID));
            eventId = cursor.getLong(cursor.getColumnIndex(DatabaseContract.COLUMN_EVENT_ID));
            contact = new PhoneBookContact(phoneNumber, name, photoUri, email, birthday, contactId, eventId);
        }
        cursor.close();
        return contact;
    }

    public void saveEvent(int contactId, long eventId) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        cv.put(DatabaseContract.COLUMN_EVENT_ID, eventId);
        db.update(DatabaseContract.TABLE_CONTACTS, cv, DatabaseContract._ID + " = ?", new String[]{String.valueOf(contactId)});
    }

    public void saveAllContacts(List<PhoneBookContact> list) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        for (PhoneBookContact contact : list) {
            cv.put(DatabaseContract._ID, contact.getId());
            cv.put(DatabaseContract.COLUMN_PHONENUMBER, contact.getPhoneNumber());
            cv.put(DatabaseContract.COLUMN_BIRTHDAY, contact.getBirthday());
            cv.put(DatabaseContract.COLUMN_EMAIL, contact.getEmail());
            cv.put(DatabaseContract.COLUMN_PHOTO_PATH, contact.getPhotoUri());
            cv.put(DatabaseContract.COLUMN_NAME, contact.getName());
            db.insert(DatabaseContract.TABLE_CONTACTS, null, cv);
        }
    }
}
