package com.isla.contactsapp.screens.contacts;

import com.isla.contactsapp.models.PhoneBookContact;

import java.util.HashSet;

public interface ContactsView {

    void showProgress();

    void hideProgress();

    void onContactLoaded(HashSet<PhoneBookContact> phoneBookContacts);
}
