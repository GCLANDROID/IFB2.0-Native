package io.cordova.ifb.module;

public class RefInfoModel {
    String compName,compID,ffEditVolume,dcEditVolume;
    boolean iselected=false;

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompID() {
        return compID;
    }

    public void setCompID(String compID) {
        this.compID = compID;
    }

    public String getFfEditVolume() {
        return ffEditVolume;
    }

    public void setFfEditVolume(String ffEditVolume) {
        this.ffEditVolume = ffEditVolume;
    }

    public String getDcEditVolume() {
        return dcEditVolume;
    }

    public void setDcEditVolume(String dcEditVolume) {
        this.dcEditVolume = dcEditVolume;
    }

    public boolean isIselected() {
        return iselected;
    }

    public void setIselected(boolean iselected) {
        this.iselected = iselected;
    }
}
