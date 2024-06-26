package io.cordova.ifb.module;

public class DisplayMatrixReportModel {
    String itemName,companyName,quantity;

    public DisplayMatrixReportModel(String itemName, String companyName, String quantity) {
        this.itemName = itemName;
        this.companyName = companyName;
        this.quantity = quantity;

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
