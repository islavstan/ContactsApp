package com.isla.contactsapp.screens.contacts;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.provider.CalendarContract;

import com.isla.contactsapp.ContactsApp;
import com.isla.contactsapp.asynctask.LoadContactsFromPhoneBookTask;
import com.isla.contactsapp.base.Presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//https://developer.android.com/guide/topics/providers/calendar-provider.html
public class ContactsPresenter implements Presenter<ContactsView> {
    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
    private ContactsView mContactsView;

    @Override
    public void onViewAttached(ContactsView view) {
        mContactsView = view;
    }

    public void loadContacts() {
        new LoadContactsFromPhoneBookTask(mContactsView).execute();
    }

    public void saveBirthdayEvent(String birthday, String title, String description) {
        ContentResolver cr = ContactsApp.getsInstance().getContentResolver();
        ContentValues values = new ContentValues();
        Calendar time = Calendar.getInstance();
        try {
            time.setTime(formatter.parse(birthday));
            values.put(CalendarContract.Events.DTSTART, time.getTimeInMillis());
            values.put(CalendarContract.Events.DTEND, time.getTimeInMillis());
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
        } catch (ParseException e) {

        }


    }

    @Override
    public void onViewDetached() {
        mContactsView = null;
    }

    @Override
    public void onDestroyed() {

    }


}
