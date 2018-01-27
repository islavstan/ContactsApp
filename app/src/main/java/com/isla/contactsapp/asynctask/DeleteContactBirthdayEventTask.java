package com.isla.contactsapp.asynctask;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;

import com.isla.contactsapp.ContactsApp;
import com.isla.contactsapp.data.ContactsDBHelper;
import com.isla.contactsapp.screens.detail.DetailView;

public class DeleteContactBirthdayEventTask extends AsyncTask<Void, Void, Void> {
    private DetailView mDetailView;
    private long mEventId;
    private int mContatId;

    public DeleteContactBirthdayEventTask(DetailView detailView, long eventId, int contactId) {
        mDetailView = detailView;
        mEventId = eventId;
        mContatId = contactId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDetailView.showProgress();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        deleteEvent(mEventId);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mDetailView.hideProgress();
        mDetailView.onDeleteEventSuccess();
    }

    private void deleteEvent(long eventId) {
        ContentResolver cr = ContactsApp.getsInstance().getContentResolver();
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
        cr.delete(deleteUri, null, null);
        ContactsDBHelper.getInstance().saveEvent(mContatId, 0);
    }
}
