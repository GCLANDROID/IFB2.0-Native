package io.cordova.ifb.test.model;

public class PersonModel {
    String name="",text="",range="",remask="";

    public PersonModel(String name, String text, String range,String remask) {
        this.name = name;
        this.text = text;
        this.range = range;
        this.remask = remask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getRemask() {
        return remask;
    }

    public void setRemask(String remask) {
        this.remask = remask;
    }
}
