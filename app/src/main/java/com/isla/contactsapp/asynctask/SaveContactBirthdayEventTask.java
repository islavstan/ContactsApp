package com.isla.contactsapp.asynctask;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.util.Log;

import com.isla.contactsapp.ContactsApp;
import com.isla.contactsapp.data.ContactsDBHelper;
import com.isla.contactsapp.screens.detail.DetailView;
import com.isla.contactsapp.utils.CalendarHelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import static com.isla.contactsapp.utils.ParseDateUtil.parseDate;

public class SaveContactBirthdayEventTask extends AsyncTask<Void, Void, Long> {
    private DetailView mDetailView;
    private int mId;
    private String mBirthday;
    private String mTitle;
    private String mDescription;

    public SaveContactBirthdayEventTask(DetailView detailView, String birthday, String title,
                                        String description, int contactId) {
        mDetailView = detailView;
        mId = contactId;
        mBirthday = birthday;
        mTitle = title;
        mDescription = description;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDetailView.showProgress();

    }

    @Override
    protected Long doInBackground(Void... voids) {
        return saveBirthdayEvent(mBirthday, mTitle, mDescription, mId);
    }

    @Override
    protected void onPostExecute(Long eventId) {
        super.onPostExecute(eventId);
        mDetailView.hideProgress();
        mDetailView.onSaveEvent(eventId);
    }

    @SuppressLint("MissingPermission")
    private Long saveBirthdayEvent(String birthday, String title, String description, int contactId) {
        ContentResolver cr = ContactsApp.getsInstance().getContentResolver();
        ContentValues values = new ContentValues();
        Calendar currentTime = Calendar.getInstance();
        Calendar birthdayTime = Calendar.getInstance();
        Long eventId = null;
        try {
            birthdayTime.setTime(parseDate(birthday));
            birthdayTime.set(Calendar.YEAR, currentTime.get(Calendar.YEAR));
            values.put(CalendarContract.Events.DTSTART, birthdayTime.getTimeInMillis());
            values.put(CalendarContract.Events.DTEND, birthdayTime.getTimeInMillis());
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
            values.put(CalendarContract.Events.CALENDAR_ID, CalendarHelper.getCalId());
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

            // get the event ID that is the last element in the Uri
            if (uri != null) {
                eventId = Long.parseLong(uri.getLastPathSegment());
                ContactsDBHelper.getInstance().saveEvent(contactId, eventId);
            }
        } catch (ParseException e) {
            Log.d("stas", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventId;
    }
}