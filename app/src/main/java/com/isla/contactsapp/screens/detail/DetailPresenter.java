package com.isla.contactsapp.screens.detail;

import com.isla.contactsapp.asynctask.ChangeContactBirthdayEventTask;
import com.isla.contactsapp.asynctask.DeleteContactBirthdayEventTask;
import com.isla.contactsapp.asynctask.LoadContactsFromDBTask;
import com.isla.contactsapp.asynctask.SaveContactBirthdayEventTask;
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


    void saveBirthdayEvent(String birthday, String title, String description, int contactId) {
        new SaveContactBirthdayEventTask(mDetailView, birthday, title, description, contactId).execute();
    }

    void deleteBirthdayEvent(long eventId) {
        new DeleteContactBirthdayEventTask(mDetailView, eventId).execute();
    }

    void changeBirthdayEvent(String title, String description, long eventId) {
        new ChangeContactBirthdayEventTask(mDetailView, eventId, title, description).execute();
    }

    @Override
    public void onViewDetached() {
        mDetailView = null;
    }

    @Override
    public void onDestroyed() {

    }
}
