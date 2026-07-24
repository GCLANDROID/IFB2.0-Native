package io.cordova.ifb.module;

public class CompetitonSalesBrandModel {
    private String id;
    private String brandId;
    private String name;
    private String qty;
    private String color;

    public CompetitonSalesBrandModel(String id, String brandId, String name, String qty, String color) {
        this.id = id;
        this.brandId = brandId;
        this.name = name;
        this.qty = qty;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
