package com.example.mercadocomadmin.Model;

public class ChatModel
{
    String msg;
    String time;
    String date;
    String userId;
    String pushKey;
    String temp1;
    String temp2;
    String temp3;

    public ChatModel()
    {
    }

    public ChatModel(String msg, String time, String date, String userId, String pushKey, String temp1, String temp2, String temp3) {
        this.msg = msg;
        this.time = time;
        this.date = date;
        this.userId = userId;
        this.pushKey = pushKey;
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.temp3 = temp3;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getTemp3() {
        return temp3;
    }

    public void setTemp3(String temp3) {
        this.temp3 = temp3;
    }
}
