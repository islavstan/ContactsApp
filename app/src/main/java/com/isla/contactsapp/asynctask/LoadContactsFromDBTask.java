package com.isla.contactsapp.asynctask;

import android.os.AsyncTask;

import com.isla.contactsapp.data.ContactsDBHelper;
import com.isla.contactsapp.models.PhoneBookContact;
import com.isla.contactsapp.screens.detail.DetailView;
//load detail contact info from db
public class LoadContactsFromDBTask extends AsyncTask<Void, Void, PhoneBookContact> {
    private DetailView mDetailView;
    private int mId;

    public LoadContactsFromDBTask(DetailView detailView, int id) {
        mDetailView = detailView;
        mId = id;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDetailView.showProgress();

    }

    @Override
    protected PhoneBookContact doInBackground(Void... voids) {
        return ContactsDBHelper.getInstance().getDetailContact(mId);
    }

    @Override
    protected void onPostExecute(PhoneBookContact phoneBookContact) {
        super.onPostExecute(phoneBookContact);
        mDetailView.hideProgress();
        mDetailView.onContactLoaded(phoneBookContact);
    }
}