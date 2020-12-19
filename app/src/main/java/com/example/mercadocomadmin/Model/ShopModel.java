package com.example.mercadocomadmin.Model;

public class ShopModel {
    private String mName;
    private String mImageUrl;
    private String mPlace;
    private String mPhone;
    private String mType;
    private String mUserKey;

    public ShopModel() {
    }

    public ShopModel(String mName, String mImageUrl, String mPlace, String mPhone, String mType, String mUserKey) {
        this.mName = mName;
        this.mImageUrl = mImageUrl;
        this.mPlace = mPlace;
        this.mPhone = mPhone;
        this.mType = mType;
        this.mUserKey = mUserKey;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmPlace() {
        return mPlace;
    }

    public void setmPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmUserKey() {
        return mUserKey;
    }

    public void setmUserKey(String mUserKey) {
        this.mUserKey = mUserKey;
    }
}
