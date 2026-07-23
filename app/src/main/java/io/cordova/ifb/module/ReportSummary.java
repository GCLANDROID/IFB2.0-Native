package io.cordova.ifb.module;

public class ReportSummary {
    private int totalTarget;
    private int totalSold;
    private double totalPercentage;
    private double totalEarn;
    private double specialIncentive;
    private double topupBonus;
    private double specialEarning;
    private double specialDeduction;
    private double netPay;

    public ReportSummary(int totalTarget, int totalSold, double totalPercentage,
                         double totalEarn, double specialIncentive, double topupBonus,
                         double specialEarning, double specialDeduction, double netPay) {
        this.totalTarget = totalTarget;
        this.totalSold = totalSold;
        this.totalPercentage = totalPercentage;
        this.totalEarn = totalEarn;
        this.specialIncentive = specialIncentive;
        this.topupBonus = topupBonus;
        this.specialEarning = specialEarning;
        this.specialDeduction = specialDeduction;
        this.netPay = netPay;
    }

    public int getTotalTarget() { return totalTarget; }
    public int getTotalSold() { return totalSold; }
    public double getTotalPercentage() { return totalPercentage; }
    public double getTotalEarn() { return totalEarn; }
    public double getSpecialIncentive() { return specialIncentive; }
    public double getTopupBonus() { return topupBonus; }
    public double getSpecialEarning() { return specialEarning; }
    public double getSpecialDeduction() { return specialDeduction; }
    public double getNetPay() { return netPay; }
}
