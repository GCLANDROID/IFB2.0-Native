package io.cordova.ifb.module;

public class QueryModel {
    String date,product,contactPerson,contactNumber,landLineNumber,emailId,customerCat,address,pincoe,remarks,otherProduct,otherCat,orgName;

    public QueryModel(String date, String product, String contactPerson, String contactNumber, String landLineNumber, String emailId, String customerCat, String address, String pincoe, String remarks, String otherProduct, String otherCat,String orgName) {
        this.date = date;
        this.product = product;
        this.contactPerson = contactPerson;
        this.contactNumber = contactNumber;
        this.landLineNumber = landLineNumber;
        this.emailId = emailId;
        this.customerCat = customerCat;
        this.address = address;
        this.pincoe = pincoe;
        this.remarks = remarks;
        this.otherProduct = otherProduct;
        this.otherCat = otherCat;
        this.orgName=orgName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLandLineNumber() {
        return landLineNumber;
    }

    public void setLandLineNumber(String landLineNumber) {
        this.landLineNumber = landLineNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCustomerCat() {
        return customerCat;
    }

    public void setCustomerCat(String customerCat) {
        this.customerCat = customerCat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincoe() {
        return pincoe;
    }

    public void setPincoe(String pincoe) {
        this.pincoe = pincoe;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOtherProduct() {
        return otherProduct;
    }

    public void setOtherProduct(String otherProduct) {
        this.otherProduct = otherProduct;
    }

    public String getOtherCat() {
        return otherCat;
    }

    public void setOtherCat(String otherCat) {
        this.otherCat = otherCat;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
