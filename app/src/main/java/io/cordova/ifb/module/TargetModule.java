package io.cordova.ifb.module;

public class TargetModule {
    String productName,target;

    public TargetModule(String productName, String target) {
        this.productName = productName;
        this.target = target;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
