package com.isla.contactsapp.screens.contacts;

import com.isla.contactsapp.base.PresenterFactory;

public class ContactsPresenterFactory implements PresenterFactory<ContactsPresenter> {
    @Override
    public ContactsPresenter create() {
        return new ContactsPresenter();
    }
}
