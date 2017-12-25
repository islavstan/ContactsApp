package com.isla.contactsapp.screens.contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.isla.contactsapp.ContactsApp;
import com.isla.contactsapp.base.Presenter;
import com.isla.contactsapp.models.PhoneBookContact;

import java.util.HashSet;

public class ContactsPresenter implements Presenter<ContactsView> {
    private ContactsView mContactsView;

    @Override
    public void onViewAttached(ContactsView view) {
        mContactsView = view;
    }

    public void loadContacts() {
        new LoadContactsFromPhoneBookTask(mContactsView).execute();
    }

    @Override
    public void onViewDetached() {
        mContactsView = null;
    }

    @Override
    public void onDestroyed() {

    }

    //load contacts from phone book
    public static class LoadContactsFromPhoneBookTask extends AsyncTask<Void, Void, HashSet<PhoneBookContact>> {
        private ContactsView mContactsView;

        public LoadContactsFromPhoneBookTask(ContactsView contactsView) {
            mContactsView = contactsView;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mContactsView.showProgress();
        }

        @Override
        protected HashSet<PhoneBookContact> doInBackground(Void... voids) {
            HashSet<PhoneBookContact> phoneBookSet = new HashSet<>();
            ContentResolver contentResolver = ContactsApp.getsInstance().getContentResolver();
            Cursor ce;
            Cursor phones = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                    null, null);
            String name, phoneNumber, imageUri, id;
            String email = "";
            String nickName = "";
            if (phones != null) {
                while (phones.moveToNext()) {
                    id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));
                    name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    imageUri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    ce = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (ce != null && ce.moveToFirst()) {
                        email = ce.getString(ce.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        //nickName = ce.getString(ce.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                        ce.close();
                    }
                    PhoneBookContact phoneBookContact = new PhoneBookContact(phoneNumber, name, imageUri, email, nickName);
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phoneNumber) && phoneNumber.length() > 9) {
                        phoneBookSet.add(phoneBookContact);
                    }
                }
                phones.close();
            }


            return phoneBookSet;
        }

        @Override
        protected void onPostExecute(HashSet<PhoneBookContact> phoneBookContacts) {
            super.onPostExecute(phoneBookContacts);
            mContactsView.hideProgress();
            mContactsView.onContactLoaded(phoneBookContacts);
        }
    }

}
