package com.isla.contactsapp.models;

public class PhoneBookContact {
    private String mPhoneNumber;
    private String mName;
    private String mPhotoUri;
    private String mEmail;
    private String mBirthday;
    private Integer mId;

    public PhoneBookContact() {

    }

    public PhoneBookContact(String phoneNumber, String name, String photoUri, String email, String birthday, Integer id) {
        mPhoneNumber = phoneNumber;
        mName = name;
        mPhotoUri = photoUri;
        mEmail = email;
        mBirthday = birthday;
        mId = id;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneBookContact that = (PhoneBookContact) o;

        return mPhoneNumber.equals(that.mPhoneNumber);
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhotoUri() {
        return mPhotoUri;
    }

    public void setPhotoUri(String photoUri) {
        mPhotoUri = photoUri;
    }

    @Override
    public int hashCode() {
        return mPhoneNumber.hashCode();
    }
}
