package io.cordova.ifb.module;

public class CategoryItem {

    private final String id;
    private final String value;

    public CategoryItem(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
