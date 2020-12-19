package com.example.mercadocomadmin.Model;

public class UserDataModel {
    private String userEmail;
    private String userId;
    private String userName;

    public UserDataModel()
    {
    }

    public UserDataModel(String userEmail, String userId, String userName) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
