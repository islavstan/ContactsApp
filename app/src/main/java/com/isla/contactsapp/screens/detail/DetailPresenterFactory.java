package com.isla.contactsapp.screens.detail;

import com.isla.contactsapp.base.PresenterFactory;

public class DetailPresenterFactory implements PresenterFactory<DetailPresenter> {
    @Override
    public DetailPresenter create() {
        return new DetailPresenter();
    }
}
