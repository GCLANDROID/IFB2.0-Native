package io.cordova.ifb.module;

public class ProductDetailsModel {

    String modelId,category,product,status,serialNo;
    boolean selected=false;

    public ProductDetailsModel(String modelId, String category, String product, String status,String serialNo) {
        this.modelId = modelId;
        this.category = category;
        this.product = product;
        this.status = status;
        this.serialNo=serialNo;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
