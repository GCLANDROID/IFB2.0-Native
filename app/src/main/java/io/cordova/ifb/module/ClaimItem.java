package io.cordova.ifb.module;

public class ClaimItem {
    public String claimID;
    public String saleDate;
    public String invoiceValue;
    public String deliveryDate;
    public String deliveryBy;
    public int isModelCodeMissing;
    public String product;
    public String customer;
    public String mobileNo;

    public boolean isSaving = false;

    public String selectedCategoryId = "";
    public String selectedCategory = "";
    public String selectedModel = "";
    public String selectedModelCode = "";

    public ClaimItem(
            String claimID,
            String saleDate,
            String invoiceValue,
            String deliveryDate,
            String deliveryBy,
            int isModelCodeMissing,
            String product,
            String customer,
            String mobileNo) {

        this.claimID = claimID;
        this.saleDate = saleDate;
        this.invoiceValue = invoiceValue;
        this.deliveryDate = deliveryDate;
        this.deliveryBy = deliveryBy;
        this.isModelCodeMissing = isModelCodeMissing;
        this.product = product;
        this.customer = customer;
        this.mobileNo = mobileNo;
    }
}
