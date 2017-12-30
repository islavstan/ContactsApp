package com.isla.contactsapp;

import com.isla.contactsapp.base.PresenterFactory;

public class MainPresenterFactory implements PresenterFactory<MainPresenter> {

    @Override
    public MainPresenter create() {
        return new MainPresenter();
    }
}
