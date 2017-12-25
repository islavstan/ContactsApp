package com.isla.contactsapp;

public interface MainView {
    void showContactsScreen();

    void showSnackbar(String message);

    void showProgress();

    void hideProgress();
}
