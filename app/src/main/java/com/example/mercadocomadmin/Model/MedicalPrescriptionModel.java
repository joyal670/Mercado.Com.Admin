package com.example.mercadocomadmin.Model;

public class MedicalPrescriptionModel
{
    private String Image;
    private String UploadId;
    private String Qty;
    private String status;
    private String userId;
    private String Phone;
    private String temp2;

    public MedicalPrescriptionModel()
    {

    }

    public MedicalPrescriptionModel(String image, String uploadId, String qty, String status, String userId, String phone, String temp2) {
        Image = image;
        UploadId = uploadId;
        Qty = qty;
        this.status = status;
        this.userId = userId;
        Phone = phone;
        this.temp2 = temp2;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUploadId() {
        return UploadId;
    }

    public void setUploadId(String uploadId) {
        UploadId = uploadId;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }
}
