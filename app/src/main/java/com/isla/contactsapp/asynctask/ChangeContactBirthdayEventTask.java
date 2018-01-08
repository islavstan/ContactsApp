package com.isla.contactsapp.asynctask;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;

import com.isla.contactsapp.ContactsApp;
import com.isla.contactsapp.screens.detail.DetailView;

public class ChangeContactBirthdayEventTask extends AsyncTask<Void, Void, Void> {
    private DetailView mDetailView;
    private long mEventId;
    private String mTitle;
    private String mDescription;

    public ChangeContactBirthdayEventTask(DetailView detailView, long eventId, String title, String description) {
        mDetailView = detailView;
        mEventId = eventId;
        mTitle = title;
        mDescription = description;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDetailView.showProgress();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        changeEvent(mEventId, mTitle, mDescription);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mDetailView.hideProgress();
    }

    private void changeEvent(long eventId, String title, String description) {
        ContentResolver cr = ContactsApp.getsInstance().getContentResolver();
        ContentValues values = new ContentValues();
// The new title for the event
        values.put(CalendarContract.Events.TITLE, title);
        values.put(CalendarContract.Events.DESCRIPTION, description);
        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
        cr.update(updateUri, values, null, null);
    }
}
