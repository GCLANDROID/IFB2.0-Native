package io.cordova.ifb.module;

public class TSRSaleItemModule {
    String item,itemId,editvalue;
    private boolean isSelected = false;

   /* public TSRSaleItemModule(String item, String itemId) {
        this.item = item;
        this.itemId = itemId;

    }*/

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getEditvalue() {
        return editvalue;
    }

    public void setEditvalue(String editvalue) {
        this.editvalue = editvalue;
    }
}
