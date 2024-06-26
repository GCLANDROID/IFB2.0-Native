package io.cordova.ifb.module;

public class CVisitModel {
    String visitingDate,cusName,cusMob,cusEmail,category,model,engVisit,engName,engMob,location,imageUrl,cusAddress,remarks;

    public CVisitModel(String visitingDate, String cusName, String cusMob, String cusEmail, String category, String model, String engVisit, String engName, String engMob, String location,String url,String cusAddress,String remarks) {
        this.visitingDate = visitingDate;
        this.cusName = cusName;
        this.cusMob = cusMob;
        this.cusEmail = cusEmail;
        this.category = category;
        this.model = model;
        this.engVisit = engVisit;
        this.engName = engName;
        this.engMob = engMob;
        this.location = location;
        this.imageUrl=url;
        this.cusAddress=cusAddress;
        this.remarks=remarks;
    }


     public String getVisitingDate() {
        return visitingDate;
    }

    public void setVisitingDate(String visitingDate) {
        this.visitingDate = visitingDate;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusMob() {
        return cusMob;
    }

    public void setCusMob(String cusMob) {
        this.cusMob = cusMob;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngVisit() {
        return engVisit;
    }

    public void setEngVisit(String engVisit) {
        this.engVisit = engVisit;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getEngMob() {
        return engMob;
    }

    public void setEngMob(String engMob) {
        this.engMob = engMob;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
