package com.shawon.myvideostream.Model;

public class videoUploaderModel {
    String videoTitle,videoUrl;

    public videoUploaderModel() {
    }

    public videoUploaderModel(String videoTitle, String videoUrl) {
        this.videoTitle = videoTitle;
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
