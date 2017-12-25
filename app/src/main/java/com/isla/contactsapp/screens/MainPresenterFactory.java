package com.isla.contactsapp.screens;

import com.isla.contactsapp.MainPresenter;
import com.isla.contactsapp.base.PresenterFactory;

public class MainPresenterFactory implements PresenterFactory<MainPresenter> {

    @Override
    public MainPresenter create() {
        return new MainPresenter();
    }
}
