package com.isla.contactsapp.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.util.Pair;

import com.isla.contactsapp.ContactsApp;

public class CalendarHelper {
    private static final String[] EVENT_PROJECTION = new String[]{
            Calendars._ID
    };

    private static final String[] INSTANCE_PROJECTION = new String[]{
            CalendarContract.Instances._ID,      // 0
            CalendarContract.Instances.DESCRIPTION,         // 1
            CalendarContract.Instances.TITLE          // 2
    };
    private static final int PROJECTION_DESCRIPTION_INDEX = 1;
    private static final int PROJECTION_TITLE_INDEX = 2;
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
            calCursor = ContactsApp.getsInstance().getContentResolver().query(CalendarContract.Calendars.CONTENT_URI,
                    EVENT_PROJECTION, CalendarContract.Calendars.VISIBLE + " = 1", null, CalendarContract.Calendars._ID + " ASC");
        }
        if (calCursor != null && calCursor.moveToFirst()) {
            calendarId = calCursor.getLong(PROJECTION_ID_INDEX);
            calCursor.close();
        }
        return calendarId;

    }

    public static Pair<String, String> getTitleAndDescription(Long eventId) {
        ContentResolver cr = ContactsApp.getsInstance().getContentResolver();
        Cursor cur = null;
        String title = null;
        String description = null;
        // The ID of the recurring event whose instances you are searching
// for in the Instances table
        String selection = CalendarContract.Instances._ID + " = ?";
        String[] selectionArgs = new String[]{eventId.toString()};
        Uri.Builder builder = CalendarContract.Events.CONTENT_URI.buildUpon();
        cur = cr.query(builder.build(),
                INSTANCE_PROJECTION,
                selection,
                selectionArgs,
                null);
        if (cur != null && cur.moveToFirst()) {
            description = cur.getString(PROJECTION_DESCRIPTION_INDEX);
            title = cur.getString(PROJECTION_TITLE_INDEX);
            cur.close();
        }
        return new Pair<>(title, description);
    }
}
