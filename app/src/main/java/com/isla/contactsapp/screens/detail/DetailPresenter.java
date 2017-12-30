package com.isla.contactsapp.screens.detail;

import com.isla.contactsapp.asynctask.LoadContactsFromDBTask;
import com.isla.contactsapp.base.Presenter;

//https://developer.android.com/guide/topics/providers/calendar-provider.html
public class DetailPresenter implements Presenter<DetailView> {

    private DetailView mDetailView;

    @Override
    public void onViewAttached(DetailView view) {
        mDetailView = view;
    }

    public void getDetailContact(int id) {
        new LoadContactsFromDBTask(mDetailView, id).execute();
    }

    @Override
    public void onViewDetached() {
        mDetailView = null;
    }

    @Override
    public void onDestroyed() {

    }
}
