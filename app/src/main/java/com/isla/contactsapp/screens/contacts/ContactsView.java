package com.isla.contactsapp.screens.contacts;

import com.isla.contactsapp.models.PhoneBookContact;

import java.util.List;

public interface ContactsView {

    void showProgress();

    void hideProgress();

    void onContactLoaded(List<PhoneBookContact> phoneBookContacts);
}
