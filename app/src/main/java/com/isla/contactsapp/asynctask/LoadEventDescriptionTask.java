package com.isla.contactsapp.asynctask;

import android.os.AsyncTask;
import android.util.Pair;

import com.isla.contactsapp.screens.detail.DetailView;
import com.isla.contactsapp.utils.CalendarHelper;

public class LoadEventDescriptionTask extends AsyncTask<Void, Void, Pair<String, String>> {
    private DetailView mDetailView;
    private long mEventId;

    public LoadEventDescriptionTask(DetailView detailView, long eventId) {
        mDetailView = detailView;
        mEventId = eventId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDetailView.showProgress();
    }

    @Override
    protected Pair<String, String> doInBackground(Void... voids) {
        return CalendarHelper.getTitleAndDescription(mEventId);
    }

    @Override
    protected void onPostExecute(Pair<String, String> pair) {
        super.onPostExecute(pair);
        mDetailView.hideProgress();
        mDetailView.showChangeBirthdayDialog(pair.first, pair.second);
    }

}
