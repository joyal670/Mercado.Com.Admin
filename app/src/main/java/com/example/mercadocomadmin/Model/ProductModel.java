package com.example.mercadocomadmin.Model;

public class ProductModel {

    private String pName;
    private String pImageUrl;
    private String mDescription;
    private String pType;
    private String pPrice;
    private String pStoke;
    private String pUserKey;
    private String mKey;

    public ProductModel() {
    }

    public String getpUserKey() {
        return pUserKey;
    }

    public void setpUserKey(String pUserKey) {
        this.pUserKey = pUserKey;
    }

    public ProductModel(String pName, String pImageUrl, String mDescription, String pType, String pPrice, String pStoke, String pUserKey) {
        if(pName.trim().equals("")){
            pName = "No name";
        }
        this.pName = pName;
        this.pImageUrl = pImageUrl;
        this.mDescription = mDescription;
        this.pType = pType;
        this.pPrice = pPrice;
        this.pStoke = pStoke;
        this.pUserKey = pUserKey;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpImageUrl() {
        return pImageUrl;
    }

    public void setpImageUrl(String pImageUrl) {
        this.pImageUrl = pImageUrl;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpStoke() {
        return pStoke;
    }

    public void setpStoke(String pStoke) {
        this.pStoke = pStoke;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
