package io.cordova.ifb.activity;

public class ReplashedReportModel {
    String repalcementDate,productName,modelName;

    public ReplashedReportModel(String repalcementDate, String productName, String modelName) {
        this.repalcementDate = repalcementDate;
        this.productName = productName;
        this.modelName = modelName;
    }

    public String getRepalcementDate() {
        return repalcementDate;
    }

    public void setRepalcementDate(String repalcementDate) {
        this.repalcementDate = repalcementDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
