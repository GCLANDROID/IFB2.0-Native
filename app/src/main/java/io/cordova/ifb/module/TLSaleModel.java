package io.cordova.ifb.module;

public class TLSaleModel {
    String monthname,year,url;

    public TLSaleModel(String monthname, String year, String url) {
        this.monthname = monthname;
        this.year = year;
        this.url = url;
    }

    public String getMonthname() {
        return monthname;
    }

    public void setMonthname(String monthname) {
        this.monthname = monthname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
