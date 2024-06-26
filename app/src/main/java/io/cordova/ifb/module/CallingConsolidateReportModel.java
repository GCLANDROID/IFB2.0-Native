package io.cordova.ifb.module;

public class CallingConsolidateReportModel {
    String date,called,callConnected,callNotConnected;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public String getCallConnected() {
        return callConnected;
    }

    public void setCallConnected(String callConnected) {
        this.callConnected = callConnected;
    }

    public String getCallNotConnected() {
        return callNotConnected;
    }

    public void setCallNotConnected(String callNotConnected) {
        this.callNotConnected = callNotConnected;
    }

    public CallingConsolidateReportModel(String date, String called, String callConnected, String callNotConnected) {
        this.date = date;
        this.called = called;
        this.callConnected = callConnected;
        this.callNotConnected = callNotConnected;
    }
}
