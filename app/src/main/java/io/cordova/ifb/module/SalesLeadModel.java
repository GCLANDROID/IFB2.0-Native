package io.cordova.ifb.module;

public class SalesLeadModel {
    String callToken,cusName,mobile,date;

    public SalesLeadModel(String callToken, String cusName, String mobile, String date) {
        this.callToken = callToken;
        this.cusName = cusName;
        this.mobile = mobile;
        this.date = date;
    }

    public String getCallToken() {
        return callToken;
    }

    public void setCallToken(String callToken) {
        this.callToken = callToken;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
