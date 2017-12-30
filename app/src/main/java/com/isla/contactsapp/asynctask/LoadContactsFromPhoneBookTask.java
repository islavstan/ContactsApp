package com.isla.contactsapp.asynctask;

import android.os.AsyncTask;

import com.isla.contactsapp.data.ContactsDBHelper;
import com.isla.contactsapp.models.PhoneBookContact;
import com.isla.contactsapp.screens.contacts.ContactsView;

import java.util.List;

import static com.isla.contactsapp.utils.ContactsHelper.getPhoneBookContact;

//load contacts from phone book
public class LoadContactsFromPhoneBookTask extends AsyncTask<Void, Void, List<PhoneBookContact>> {
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
    protected List<PhoneBookContact> doInBackground(Void... voids) {
        List<PhoneBookContact> contactList = getPhoneBookContact();
        if (contactList != null) {
            ContactsDBHelper.getInstance().saveAllContacts(contactList);
            return contactList;
        }
        return null;
    }


    @Override
    protected void onPostExecute(List<PhoneBookContact> phoneBookContacts) {
        super.onPostExecute(phoneBookContacts);
        mContactsView.hideProgress();
        mContactsView.onContactLoaded(phoneBookContacts);
    }
}