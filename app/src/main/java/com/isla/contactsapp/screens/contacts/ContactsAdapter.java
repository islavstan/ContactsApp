package com.isla.contactsapp.screens.contacts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isla.contactsapp.R;
import com.isla.contactsapp.models.PhoneBookContact;
import com.isla.contactsapp.utils.GlideUtils;

import java.lang.ref.WeakReference;
import java.util.List;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {
    private List<PhoneBookContact> mPhoneBookContactList;
    private WeakReference<ContactClickedListener> mOnItemClickListenerWeakReference;

    public ContactsAdapter(List<PhoneBookContact> contactsList, ContactClickedListener listener) {
        mOnItemClickListenerWeakReference = new WeakReference<>(listener);
        mPhoneBookContactList = contactsList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ContactViewHolder.newInstance(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
        holder.bind(mPhoneBookContactList.get(position));
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactClickedListener listener = mOnItemClickListenerWeakReference.get();
                if (listener != null) {
                    listener.onContactClicked(mPhoneBookContactList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhoneBookContactList.size();
    }

    interface ContactClickedListener {
        void onContactClicked(PhoneBookContact contact);
    }

    static final class ContactViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivUserAva;
        private TextView tvUserName;
        private TextView tvNumber;
        private TextView tvBirthday;
        private TextView tvEmail;
        private RelativeLayout rlContainer;

        ContactViewHolder(View itemView) {
            super(itemView);
            ivUserAva = (ImageView) itemView.findViewById(R.id.ivUserAva);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            tvBirthday = (TextView) itemView.findViewById(R.id.tvBirthday);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            rlContainer = (RelativeLayout) itemView.findViewById(R.id.rlContainer);
        }

        private static ContactsAdapter.ContactViewHolder newInstance(@NonNull LayoutInflater inflater,
                                                                     @NonNull ViewGroup parent) {
            View itemView = inflater.inflate(R.layout.item_contact, parent, false);
            return new ContactsAdapter.ContactViewHolder(itemView);
        }

        private void bind(PhoneBookContact phoneBookContact) {
            if (phoneBookContact.getPhotoUri() != null) {
                GlideUtils.loadCircularImage(ivUserAva, phoneBookContact.getPhotoUri());
            } else {
                ivUserAva.setImageResource(R.drawable.ic_user_placeholder);
            }
            tvUserName.setText(phoneBookContact.getName());

            if (!TextUtils.isEmpty(phoneBookContact.getPhoneNumber())) {
                tvNumber.setVisibility(View.VISIBLE);
                tvNumber.setText(tvNumber.getContext().getString(R.string.phone, phoneBookContact.getPhoneNumber()));
            } else {
                tvNumber.setVisibility(View.GONE);
            }
            tvNumber.setText(phoneBookContact.getPhoneNumber());

            if (!TextUtils.isEmpty(phoneBookContact.getEmail())) {
                tvEmail.setVisibility(View.VISIBLE);
                tvEmail.setText(tvEmail.getContext().getString(R.string.email, phoneBookContact.getEmail()));
            } else {
                tvEmail.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(phoneBookContact.getBirthday())) {
                tvBirthday.setVisibility(View.VISIBLE);
                tvBirthday.setText(tvBirthday.getContext().getString(R.string.birthday, phoneBookContact.getBirthday()));
            } else {
                tvBirthday.setVisibility(View.GONE);
            }
        }
    }
}