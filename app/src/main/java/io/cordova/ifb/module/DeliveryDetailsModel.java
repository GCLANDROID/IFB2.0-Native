package io.cordova.ifb.module;

public class DeliveryDetailsModel {
    String refNo,date,cusName,category,contactNumber,lastName,firstName,month,financialyear,InvoiceValue;
    String altNumber;
    String modelCode,InstallationBy,WiFiDeviceStatus,SalesType,Quantity,address,pincode,categoryID,underExchange,financialScheme;

    public DeliveryDetailsModel(String refNo, String date, String cusName, String category) {
        this.refNo = refNo;
        this.date = date;
        this.cusName = cusName;
        this.category = category;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAltNumber() {
        return altNumber;
    }

    public void setAltNumber(String altNumber) {
        this.altNumber = altNumber;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getInstallationBy() {
        return InstallationBy;
    }

    public void setInstallationBy(String installationBy) {
        InstallationBy = installationBy;
    }

    public String getWiFiDeviceStatus() {
        return WiFiDeviceStatus;
    }

    public void setWiFiDeviceStatus(String wiFiDeviceStatus) {
        WiFiDeviceStatus = wiFiDeviceStatus;
    }

    public String getSalesType() {
        return SalesType;
    }

    public void setSalesType(String salesType) {
        SalesType = salesType;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getUnderExchange() {
        return underExchange;
    }

    public void setUnderExchange(String underExchange) {
        this.underExchange = underExchange;
    }

    public String getFinancialScheme() {
        return financialScheme;
    }

    public void setFinancialScheme(String financialScheme) {
        this.financialScheme = financialScheme;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFinancialyear() {
        return financialyear;
    }

    public void setFinancialyear(String financialyear) {
        this.financialyear = financialyear;
    }

    public String getInvoiceValue() {
        return InvoiceValue;
    }

    public void setInvoiceValue(String invoiceValue) {
        InvoiceValue = invoiceValue;
    }
}
