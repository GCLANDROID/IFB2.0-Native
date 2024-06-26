package io.cordova.ifb.module;

public class IMEIReqModel {
    String imeiNumber,approvalStatus,reqDetails;

    public String getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getReqDetails() {
        return reqDetails;
    }

    public void setReqDetails(String reqDetails) {
        this.reqDetails = reqDetails;
    }
}
