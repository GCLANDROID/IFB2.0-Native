package io.cordova.ifb.module;

public class DWReportModel {
    String date,cusNAME,cusMob,cusAddress,category,model,advanced,amt,remarks;

    public DWReportModel(String date, String cusNAME, String cusMob, String cusAddress, String category, String model, String advanced, String amt, String remarks) {
        this.date = date;
        this.cusNAME = cusNAME;
        this.cusMob = cusMob;
        this.cusAddress = cusAddress;
        this.category = category;
        this.model = model;
        this.advanced = advanced;
        this.amt = amt;
        this.remarks = remarks;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCusNAME() {
        return cusNAME;
    }

    public void setCusNAME(String cusNAME) {
        this.cusNAME = cusNAME;
    }

    public String getCusMob() {
        return cusMob;
    }

    public void setCusMob(String cusMob) {
        this.cusMob = cusMob;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
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

    public String getAdvanced() {
        return advanced;
    }

    public void setAdvanced(String advanced) {
        this.advanced = advanced;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
