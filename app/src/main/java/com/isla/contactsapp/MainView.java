package com.isla.contactsapp;

public interface MainView {
    void showContactsScreen();

    void showDetailScreen(int id);

    void showSnackbar(String message);

    void showProgress();

    void hideProgress();
}
