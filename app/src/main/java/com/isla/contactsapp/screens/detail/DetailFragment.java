package com.isla.contactsapp.screens.detail;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.isla.contactsapp.MainView;
import com.isla.contactsapp.R;
import com.isla.contactsapp.base.BasePresenterFragment;
import com.isla.contactsapp.base.PresenterFactory;
import com.isla.contactsapp.models.PhoneBookContact;
import com.isla.contactsapp.utils.GlideUtils;
import com.isla.contactsapp.utils.SimplePermissionCallback;
import com.master.permissionhelper.PermissionHelper;

public class DetailFragment extends BasePresenterFragment<DetailPresenter, DetailView>
        implements DetailView {
    private static final String ID_EXTRA = "ID_EXTRA";
    private DetailPresenter mDetailPresenter;
    private MainView mMainView;
    private int mId;
    private Toolbar toolbar;
    private EditText etFio;
    private EditText etBirthday;
    private EditText etPhone;
    private EditText etEmail;
    private PhoneBookContact mPhoneBookContact;
    private ImageView ivPhoto;
    private PermissionHelper mPermissionHelper;

    public static DetailFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(ID_EXTRA, id);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_detail, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainView) {
            mMainView = (MainView) context;
        }
        Bundle args = getArguments();
        if (args != null) {
            mId = args.getInt(ID_EXTRA);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etFio = (EditText) view.findViewById(R.id.etFio);
        etPhone = (EditText) view.findViewById(R.id.etPhone);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etBirthday = (EditText) view.findViewById(R.id.etBirthday);
        ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
        blockAllFields(etFio, etPhone, etEmail, etBirthday);

    }

    private void saveBirthdayEvent() {
        mPermissionHelper = new PermissionHelper(this, new String[]{Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR}, 100);
        mPermissionHelper.request(new SimplePermissionCallback() {
            @Override
            public void onPermissionGranted() {
                mDetailPresenter.saveBirthdayEvent();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null) {
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void blockAllFields(@NonNull EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setFocusable(false);
            editText.setLongClickable(false);
            editText.setFocusableInTouchMode(false);
            editText.setCursorVisible(false);
            editText.setClickable(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mDetailPresenter.getDetailContact(mId);
        saveBirthdayEvent();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onContactLoaded(PhoneBookContact phoneBookContact) {
        if (phoneBookContact != null) {
            mPhoneBookContact = phoneBookContact;
            GlideUtils.loadCircularImage(ivPhoto, phoneBookContact.getPhotoUri());
            if (!TextUtils.isEmpty(phoneBookContact.getBirthday())) {
                etBirthday.setText(phoneBookContact.getBirthday());
            }
            if (!TextUtils.isEmpty(phoneBookContact.getName())) {
                toolbar.setTitle(phoneBookContact.getName());
                etFio.setText(phoneBookContact.getName());
            }
            if (!TextUtils.isEmpty(phoneBookContact.getPhoneNumber())) {
                etPhone.setText(phoneBookContact.getPhoneNumber());
            }
            if (!TextUtils.isEmpty(phoneBookContact.getEmail())) {
                etEmail.setText(phoneBookContact.getEmail());
            }
        }
    }

    @NonNull
    @Override
    protected String tag() {
        return "DetailFragment";
    }

    @NonNull
    @Override
    protected PresenterFactory<DetailPresenter> getPresenterFactory() {
        return new DetailPresenterFactory();
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull DetailPresenter presenter) {
        mDetailPresenter = presenter;
    }


}
