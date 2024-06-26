package io.cordova.ifb.module;

public class IssueModel {

    String issueType,remarks,date;
    String reqStatus,reqRemarks;

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getReqRemarks() {
        return reqRemarks;
    }

    public void setReqRemarks(String reqRemarks) {
        this.reqRemarks = reqRemarks;
    }
}
