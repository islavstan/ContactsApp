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
            Locale ruLocale = new Locale("ru", "RU");
            Locale engLocale = Locale.ENGLISH;
            SimpleDateFormat[] formats =
                    new SimpleDateFormat[]{new SimpleDateFormat("dd MMM yyyy", defLocale),
                            new SimpleDateFormat("dd MM yyyy", defLocale),
                            new SimpleDateFormat("MM-dd-yyyy", defLocale),
                            new SimpleDateFormat("yyyyMMdd", defLocale),
                            new SimpleDateFormat("MM/dd/yyyy", defLocale),
                            new SimpleDateFormat("MMM yyyy", defLocale),
                            new SimpleDateFormat("M yyyy", defLocale),
                            new SimpleDateFormat("MMM dd, yyyy", defLocale),
                            new SimpleDateFormat("dd MMM yyyy", ruLocale),
                            new SimpleDateFormat("dd MM yyyy", ruLocale),
                            new SimpleDateFormat("MM-dd-yyyy", ruLocale),
                            new SimpleDateFormat("yyyyMMdd", ruLocale),
                            new SimpleDateFormat("MM/dd/yyyy", ruLocale),
                            new SimpleDateFormat("MMM yyyy", ruLocale),
                            new SimpleDateFormat("M yyyy", ruLocale),
                            new SimpleDateFormat("MMM dd, yyyy", ruLocale),
                            new SimpleDateFormat("dd MMM yyyy", engLocale),
                            new SimpleDateFormat("dd MM yyyy", engLocale),
                            new SimpleDateFormat("MM-dd-yyyy", engLocale),
                            new SimpleDateFormat("yyyyMMdd", engLocale),
                            new SimpleDateFormat("MM/dd/yyyy", engLocale),
                            new SimpleDateFormat("MMM yyyy", engLocale),
                            new SimpleDateFormat("M yyyy", engLocale),
                            new SimpleDateFormat("MMM dd, yyyy", engLocale)
                    };
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
