package com.isla.contactsapp.screens.detail;

import com.isla.contactsapp.models.PhoneBookContact;

public interface DetailView {
    void showProgress();

    void hideProgress();

    void onContactLoaded(PhoneBookContact phoneBookContacts);
}
