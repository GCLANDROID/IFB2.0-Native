package io.cordova.ifb.test.model;

public class RangeModel {
    String text="";
    int range;

    public RangeModel(String text, int range) {
        this.text = text;
        this.range = range;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
