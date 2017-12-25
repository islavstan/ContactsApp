package com.isla.contactsapp;

import android.app.Application;

public class ContactsApp extends Application {
    private static ContactsApp sInstance;

    public static ContactsApp getsInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
