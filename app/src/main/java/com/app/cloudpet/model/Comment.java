package com.app.cloudpet.model;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {
    private String momentId;
    private String commentname;
    private String commentcontent;

    @Override
    public String toString() {
        return "Comment{" +
                "momentId='" + momentId + '\'' +
                ", commentname='" + commentname + '\'' +
                ", commentcontent='" + commentcontent + '\'' +
                '}';
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getCommentname() {
        return commentname;
    }

    public void setCommentname(String commentname) {
        this.commentname = commentname;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }
}
