package io.cordova.ifb.module;

public class RefNoModel {
    String date,refno,model,cusName,cusEmail,cusPhn,delieryDate,deliveryStatus,invoicecopyUrl,cancel,ticketNumber,tokenNumber,approvalStatus;
    String categoryName,csdSales,Cust_Conf_Stats;

    public RefNoModel(String date, String refno, String model, String cusName, String cusEmail, String cusPhn, String delieryDate, String deliveryStatus, String invoicecopyUrl, String cancel) {
        this.date = date;
        this.refno = refno;
        this.model = model;
        this.cusName = cusName;
        this.cusEmail = cusEmail;
        this.cusPhn = cusPhn;
        this.delieryDate = delieryDate;
        this.deliveryStatus = deliveryStatus;
        this.invoicecopyUrl = invoicecopyUrl;
        this.cancel = cancel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusPhn() {
        return cusPhn;
    }

    public void setCusPhn(String cusPhn) {
        this.cusPhn = cusPhn;
    }

    public String getDelieryDate() {
        return delieryDate;
    }

    public void setDelieryDate(String delieryDate) {
        this.delieryDate = delieryDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getInvoicecopyUrl() {
        return invoicecopyUrl;
    }

    public void setInvoicecopyUrl(String invoicecopyUrl) {
        this.invoicecopyUrl = invoicecopyUrl;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
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

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCsdSales() {
        return csdSales;
    }

    public void setCsdSales(String csdSales) {
        this.csdSales = csdSales;
    }

    public String getCust_Conf_Stats() {
        return Cust_Conf_Stats;
    }

    public void setCust_Conf_Stats(String cust_Conf_Stats) {
        Cust_Conf_Stats = cust_Conf_Stats;
    }
}
