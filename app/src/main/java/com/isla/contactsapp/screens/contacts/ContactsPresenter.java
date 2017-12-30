package com.isla.contactsapp.screens.contacts;

import com.isla.contactsapp.asynctask.LoadContactsFromPhoneBookTask;
import com.isla.contactsapp.base.Presenter;

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


}
