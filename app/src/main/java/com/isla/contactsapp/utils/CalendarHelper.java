package com.isla.contactsapp.utils;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;

import com.isla.contactsapp.ContactsApp;

public class CalendarHelper {
    public static final String[] EVENT_PROJECTION = new String[]{
            Calendars._ID
    };
    private static final int PROJECTION_ID_INDEX = 0;

    private CalendarHelper() {
    }

 /*   there is different visible calendar list so below is the code
    to select PRIMARY calendar and in older devices this query return 0
    record, so used second one if 1st one return 0 records.
*/
    @SuppressLint("MissingPermission")
    public static Long getCalId() {
        Long calendarId = null;
        Cursor calCursor = ContactsApp.getsInstance()
                .getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, EVENT_PROJECTION, CalendarContract.Calendars.VISIBLE + " = 1 AND "
                        + CalendarContract.Calendars.IS_PRIMARY + "=1", null, CalendarContract.Calendars._ID + " ASC");
        if (calCursor != null && calCursor.getCount() <= 0) {
            calCursor = ContactsApp.getsInstance().getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, EVENT_PROJECTION, CalendarContract.Calendars.VISIBLE + " = 1", null, CalendarContract.Calendars._ID + " ASC");
        }
        if (calCursor != null && calCursor.moveToFirst()) {
            calendarId = calCursor.getLong(PROJECTION_ID_INDEX);
            calCursor.close();
        }
        return calendarId;

    }

}
