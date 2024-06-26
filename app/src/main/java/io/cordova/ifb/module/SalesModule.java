package io.cordova.ifb.module;

public class SalesModule {
    String date,ticketNumber,tokenNumber,modelName,productCode,customerName,customerPhn,customerEmail,status,remarks,fileUrl,serialNumber;

    public SalesModule(String date, String ticketNumber, String tokenNumber, String modelName, String productCode, String customerName, String customerPhn, String customerEmail, String status, String remarks,String fileUrl,String serialNumber) {
        this.date = date;
        this.ticketNumber = ticketNumber;
        this.tokenNumber = tokenNumber;
        this.modelName = modelName;
        this.productCode = productCode;
        this.customerName = customerName;
        this.customerPhn = customerPhn;
        this.customerEmail = customerEmail;
        this.status = status;
        this.remarks = remarks;
        this.fileUrl=fileUrl;
        this.serialNumber=serialNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhn() {
        return customerPhn;
    }

    public void setCustomerPhn(String customerPhn) {
        this.customerPhn = customerPhn;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
