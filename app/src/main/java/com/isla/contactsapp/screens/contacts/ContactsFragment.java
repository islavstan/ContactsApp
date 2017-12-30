package com.isla.contactsapp.screens.contacts;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isla.contactsapp.MainView;
import com.isla.contactsapp.R;
import com.isla.contactsapp.base.BasePresenterFragment;
import com.isla.contactsapp.base.PresenterFactory;
import com.isla.contactsapp.models.PhoneBookContact;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends BasePresenterFragment<ContactsPresenter, ContactsView>
        implements ContactsView, ContactsAdapter.ContactClickedListener {
    private final int REQUEST_GET_CONTACTS = 1;
    private ContactsPresenter mContactsPresenter;
    private MainView mMainView;
    private List<PhoneBookContact> mPhoneBookContactList = new ArrayList<>();
    private RecyclerView rvContacts;
    private ContactsAdapter mContactsAdapter;

    public static ContactsFragment newInstance() {
        Bundle args = new Bundle();
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvContacts = (RecyclerView) view.findViewById(R.id.rvContacts);
        mContactsAdapter = new ContactsAdapter(mPhoneBookContactList, this);
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContacts.setAdapter(mContactsAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainView) {
            mMainView = (MainView) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPhoneNumbers();
    }

    private void getPhoneNumbers() {
        if (checkPermission()) {
            if (mPhoneBookContactList.isEmpty()) {
                mContactsPresenter.loadContacts();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_GET_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mMainView != null) {
                        mMainView.showSnackbar(getString(R.string.contacts_access));
                    }
                } else {
                    if (mMainView != null) {
                        mMainView.showSnackbar(getString(R.string.contacts_access_error));
                    }
                }
            }
        }
    }

    public boolean checkPermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_GET_CONTACTS);
        }
        return hasPermission;
    }

    @Override
    public void showProgress() {
        if (mMainView != null) {
            mMainView.showProgress();
        }

    }

    @Override
    public void hideProgress() {
        if (mMainView != null) {
            mMainView.hideProgress();
        }

    }

    @Override
    public void onContactLoaded(List<PhoneBookContact> phoneBookContacts) {
        mPhoneBookContactList.clear();
        mPhoneBookContactList.addAll(phoneBookContacts);
        mContactsAdapter.notifyDataSetChanged();
    }

    @NonNull
    @Override
    protected String tag() {
        return "ContactsFragment";
    }

    @NonNull
    @Override
    protected PresenterFactory<ContactsPresenter> getPresenterFactory() {
        return new ContactsPresenterFactory();
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull ContactsPresenter presenter) {
        mContactsPresenter = presenter;
    }

    @Override
    public void onContactClicked(PhoneBookContact contact) {

    }
}
