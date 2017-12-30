package com.isla.contactsapp.screens.detail;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import com.isla.contactsapp.ContactsApp;
import com.isla.contactsapp.asynctask.LoadContactsFromDBTask;
import com.isla.contactsapp.base.Presenter;
import com.isla.contactsapp.utils.CalendarHelper;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

//https://developer.android.com/guide/topics/providers/calendar-provider.html
public class DetailPresenter implements Presenter<DetailView> {
    private SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
    private DetailView mDetailView;

    @Override
    public void onViewAttached(DetailView view) {
        mDetailView = view;
    }

    public void getDetailContact(int id) {
        new LoadContactsFromDBTask(mDetailView, id).execute();
    }

    @SuppressLint("MissingPermission")
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
            values.put(CalendarContract.Events.CALENDAR_ID, CalendarHelper.getCalId());
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

            // get the event ID that is the last element in the Uri
            if (uri != null) {
                long eventID = Long.parseLong(uri.getLastPathSegment());
                Log.d("stas", "event id = " + eventID);
            }

        } catch (ParseException e) {
            Log.d("stas", e.getMessage());
        }


    }

    @Override
    public void onViewDetached() {
        mDetailView = null;
    }

    @Override
    public void onDestroyed() {

    }
}
