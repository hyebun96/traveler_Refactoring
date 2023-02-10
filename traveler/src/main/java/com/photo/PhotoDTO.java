package com.photo;

import java.util.List;

public class PhotoDTO {
    private int photoNum;
    private String userId;
    private String place;
    private String subject;
    private String content;
    private String originalFilename;
    private String saveFilename;
    private String created;
    private List<TagDTO> tagList;

    public int getPhotoNum() {
        return photoNum;
    }

    public void setPhotoNum(int photoNum) {
        this.photoNum = photoNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSaveFilename() {
        return saveFilename;
    }

    public void setSaveFilename(String saveFilename) {
        this.saveFilename = saveFilename;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<TagDTO> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagDTO> tagList) {
        this.tagList = tagList;
    }
}
