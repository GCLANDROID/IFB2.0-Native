package io.cordova.ifb.module;

public class ReportModule {
    String date,empid,day,time,location,type,CheckOutStatus;

    public ReportModule(String date, String empid, String day, String time, String location, String type) {
        this.date = date;
        this.empid = empid;
        this.day = day;
        this.time = time;
        this.location = location;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckOutStatus() {
        return CheckOutStatus;
    }

    public void setCheckOutStatus(String checkOutStatus) {
        CheckOutStatus = checkOutStatus;
    }
}
