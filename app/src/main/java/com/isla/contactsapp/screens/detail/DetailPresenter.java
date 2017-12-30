package com.isla.contactsapp.screens.detail;

import android.util.Log;

import com.isla.contactsapp.asynctask.LoadContactsFromDBTask;
import com.isla.contactsapp.base.Presenter;
import com.isla.contactsapp.utils.CalendarHelper;

import java.text.SimpleDateFormat;

//https://developer.android.com/guide/topics/providers/calendar-provider.html
public class DetailPresenter implements Presenter<DetailView> {
    private SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
    private DetailView mDetailView;

    @Override
    public void onViewAttached(DetailView view) {
        mDetailView = view;
    }

    public void getDetailContact(int id) {
        new LoadContactsFromDBTask(mDetailView, id).execute();
    }

    public void saveBirthdayEvent(/*String birthday, String title, String description*/) {
        Long calId = CalendarHelper.getCalId();
        Log.d("stas", "cal id = " + calId);
        /*ContentResolver cr = ContactsApp.getsInstance().getContentResolver();
        ContentValues values = new ContentValues();
        Calendar time = Calendar.getInstance();
        try {
            time.setTime(formatter.parse(birthday));
            values.put(CalendarContract.Events.DTSTART, time.getTimeInMillis());
            values.put(CalendarContract.Events.DTEND, time.getTimeInMillis());
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
        } catch (ParseException e) {

        }*/


    }

    @Override
    public void onViewDetached() {
        mDetailView = null;
    }

    @Override
    public void onDestroyed() {

    }
}
