package io.cordova.ifb.module;

public class Category {
    private String name;
    private int target;
    private int sold;
    private int earn; // Total earnings for this category



    public Category(String name, int target, int sold, int earn) {
        this.name = name;
        this.target = target;
        this.sold = sold;
        this.earn = earn;
    }

    public String getName() { return name; }
    public int getTarget() { return target; }
    public int getSold() { return sold; }
    public double getEarn() { return earn; }

    public void setEarn(int earn) { this.earn = earn; }
}
