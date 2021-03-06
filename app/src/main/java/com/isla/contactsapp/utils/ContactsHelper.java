package com.isla.contactsapp.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.isla.contactsapp.ContactsApp;
import com.isla.contactsapp.models.PhoneBookContact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ContactsHelper {

    private static final String[] PROJECTION = new String[]{
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.Data.DATA1,
            ContactsContract.Data.PHOTO_URI,
            ContactsContract.Data.MIMETYPE
    };

    private ContactsHelper() {

    }

    public static List<PhoneBookContact> getPhoneBookContact() {
        ContentResolver cr = ContactsApp.getsInstance().getContentResolver();
        HashMap<Integer, PhoneBookContact> tempContacts = new LinkedHashMap<>();
        Cursor cursor = cr.query(
                ContactsContract.Data.CONTENT_URI,
                PROJECTION,
                ContactsContract.Data.MIMETYPE + " = ?" +
                        " OR " +
                        ContactsContract.Data.MIMETYPE + " = ?" +
                        " OR " +
                        ContactsContract.Data.MIMETYPE + " = ?",
                new String[]{
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                        ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
                },
                "lower(" + ContactsContract.Data.DISPLAY_NAME + ")"
        );
        if (cursor != null) {
            try {
                final int idPos = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID);
                final int namePos = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME);
                final int photoPos = cursor.getColumnIndex(ContactsContract.Data.PHOTO_URI);
                final int emailNoPos = cursor.getColumnIndex(ContactsContract.Data.DATA1);
                final int mimePos = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE);
                String additionalData, photo, name, mime;
                PhoneBookContact phoneBookContact;
                while (cursor.moveToNext()) {
                    int contactId = cursor.getInt(idPos);
                    additionalData = cursor.getString(emailNoPos);
                    photo = cursor.getString(photoPos);
                    name = cursor.getString(namePos);
                    mime = cursor.getString(mimePos);
                    // If contact is not yet created
                    if (tempContacts.get(contactId) == null) {
                        // If type email, add all detail, else add name and photo
                        phoneBookContact = new PhoneBookContact();
                        phoneBookContact.setName(name);
                        phoneBookContact.setPhotoUri(photo);
                        if (mime.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            phoneBookContact.setEmail(additionalData);
                        } else if (mime.equals(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE)) {
                            phoneBookContact.setBirthday(additionalData);
                        } else if (mime.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            phoneBookContact.setPhoneNumber(additionalData);
                        }
                        tempContacts.put(contactId, phoneBookContact);
                    } else {
                        // Contact is already present
                        // Add email if type email
                        if (mime.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            tempContacts.get(contactId).setEmail(additionalData);
                        } else if (mime.equals(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE)) {
                            tempContacts.get(contactId).setBirthday(additionalData);
                        } else if (mime.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            tempContacts.get(contactId).setPhoneNumber(additionalData);
                        }
                    }
                }
            } finally {
                cursor.close();
            }

            List<PhoneBookContact> contactList = new ArrayList<>();
            PhoneBookContact contact;
            for (Map.Entry<Integer, PhoneBookContact> contacts : tempContacts.entrySet()) {
                contact = contacts.getValue();
                if (!TextUtils.isEmpty(contact.getBirthday())) {
                    contact.setId(contacts.getKey());
                    contactList.add(contact);
                }
            }
            return contactList;
        }
        return null;
    }

}

