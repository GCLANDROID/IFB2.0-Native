package io.cordova.ifb.module;

public class ProductDisplayModel {
    String modelName,modelCode;
    int IsScan,Display_Actual;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public int getIsScan() {
        return IsScan;
    }

    public void setIsScan(int isScan) {
        IsScan = isScan;
    }

    public int getDisplay_Actual() {
        return Display_Actual;
    }

    public void setDisplay_Actual(int display_Actual) {
        Display_Actual = display_Actual;
    }
}
