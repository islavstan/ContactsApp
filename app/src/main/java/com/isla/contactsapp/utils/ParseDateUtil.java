package com.isla.contactsapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ParseDateUtil {
    private ParseDateUtil() {
    }

    public static Date parseDate(String strDate) throws Exception {
        if (strDate != null && !strDate.isEmpty()) {
            Locale defLocale = Locale.getDefault();
            SimpleDateFormat[] formats =
                    new SimpleDateFormat[]{new SimpleDateFormat("dd MMM yyyy", defLocale),
                            new SimpleDateFormat("dd MM yyyy", defLocale),
                            new SimpleDateFormat("MM-dd-yyyy", defLocale),
                            new SimpleDateFormat("yyyyMMdd", defLocale),
                            new SimpleDateFormat("MM/dd/yyyy", defLocale),
                            new SimpleDateFormat("MMM yyyy", defLocale),
                            new SimpleDateFormat("M yyyy", defLocale),
                            new SimpleDateFormat("MMM dd, yyyy", defLocale)};
            Date parsedDate = null;
            for (int i = 0; i < formats.length; i++) {
                try {
                    parsedDate = formats[i].parse(strDate);
                    return parsedDate;
                } catch (ParseException e) {
                    continue;
                }
            }
        }
        throw new Exception("Unknown date format: '" + strDate + "'");
    }
}
