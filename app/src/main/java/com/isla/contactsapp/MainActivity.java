package com.isla.contactsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.isla.contactsapp.base.BasePresenterActivity;
import com.isla.contactsapp.base.PresenterFactory;
import com.isla.contactsapp.screens.MainPresenterFactory;
import com.isla.contactsapp.screens.contacts.ContactsFragment;

public class MainActivity extends BasePresenterActivity<MainPresenter, MainView> implements MainView {
    private MainPresenter mPresenter;
    private FrameLayout progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressView = (FrameLayout) findViewById(R.id.progressView);
        if (savedInstanceState == null) {
            showContactsScreen();
        }
    }

    @NonNull
    @Override
    protected String tag() {
        return "MainActivity";
    }

    private void replaceFragment(@NonNull Fragment fragment, boolean needToAddToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        String name = fragment.getClass().getName();
        transaction.replace(R.id.flContainer, fragment, name);
        if (needToAddToBackStack) {
            transaction.addToBackStack(name);
        }
        transaction.commit();
    }

    @NonNull
    @Override
    protected PresenterFactory<MainPresenter> getPresenterFactory() {
        return new MainPresenterFactory();
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull MainPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showContactsScreen() {
        replaceFragment(ContactsFragment.newInstance(), false);
    }

    @Override
    public void showSnackbar(String message) {

    }

    @Override
    public void showProgress() {
        progressView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressView.setVisibility(View.GONE);
    }
}
