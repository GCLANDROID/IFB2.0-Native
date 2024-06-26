package io.cordova.ifb.module;

public class CompetiorSaleModel {
    String itemName,comapnyName,editVolume,companyId,categoryId;
    boolean iselected=false;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getComapnyName() {
        return comapnyName;
    }

    public void setComapnyName(String comapnyName) {
        this.comapnyName = comapnyName;
    }

    public String getEditVolume() {
        return editVolume;
    }

    public void setEditVolume(String editVolume) {
        this.editVolume = editVolume;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isIselected() {
        return iselected;
    }

    public void setIselected(boolean iselected) {
        this.iselected = iselected;
    }
}
