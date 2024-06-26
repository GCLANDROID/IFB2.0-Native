package io.cordova.ifb.module;

public class DialogItemModuleTest {
    String item,itemId,sendModel;
    private boolean isSelected = false;


    public DialogItemModuleTest(String item, String itemId, String sendModel) {
        this.item = item;
        this.itemId = itemId;
        this.sendModel = sendModel;
    }

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

    public String getSendModel() {
        return sendModel;
    }

    public void setSendModel(String sendModel) {
        this.sendModel = sendModel;
    }


}
