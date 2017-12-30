package com.isla.contactsapp.screens.contacts;

import android.os.AsyncTask;

import com.isla.contactsapp.base.Presenter;
import com.isla.contactsapp.data.ContactsDBHelper;
import com.isla.contactsapp.models.PhoneBookContact;

import java.util.List;

import static com.isla.contactsapp.utils.ContactsHelper.getPhoneBookContact;

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
    public static class LoadContactsFromPhoneBookTask extends AsyncTask<Void, Void, List<PhoneBookContact>> {
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
}
