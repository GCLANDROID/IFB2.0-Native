package io.cordova.ifb.module;

public class DownloadModel {
    String month,url;

    public DownloadModel(String month, String url) {
        this.month = month;
        this.url = url;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
