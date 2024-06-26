package io.cordova.ifb.module;

public class ELearningModel {
    String videoURL,caption;

    public ELearningModel(String videoURL, String caption) {
        this.videoURL = videoURL;
        this.caption = caption;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
