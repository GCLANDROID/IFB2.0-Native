package io.cordova.ifb.module;

public class CategoryData {
    private String name;
    private int target;
    private int sold;
    private double percentage;
    private double earn;

    public CategoryData(String name, int target, int sold, double percentage, double earn) {
        this.name = name;
        this.target = target;
        this.sold = sold;
        this.percentage = percentage;
        this.earn = earn;
    }

    public String getName() { return name; }
    public int getTarget() { return target; }
    public int getSold() { return sold; }
    public double getPercentage() { return percentage; }
    public double getEarn() { return earn; }
}
