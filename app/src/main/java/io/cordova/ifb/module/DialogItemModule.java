package io.cordova.ifb.module;

public class DialogItemModule {
    String item,itemId,sendModel;
    private boolean isSelected = false;
    String mapFlag;



    public DialogItemModule(String item, String itemId) {
        this.item = item;
        this.itemId = itemId;
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

    public String getMapFlag() {
        return mapFlag;
    }

    public void setMapFlag(String mapFlag) {
        this.mapFlag = mapFlag;
    }
}
