package com.app.cloudpet.model;

import cn.bmob.v3.BmobObject;

public class Topic extends BmobObject {
    private String title;
    private String img;
    private String hot;
    private String read;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
