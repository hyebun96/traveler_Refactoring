package com.travel;

public class TravelDTO {
    private int num;
    private int listNum;
    private String userName;
    private String userId;
    private String place;
    private String information;
    private String created;
    private int likeNum;
    private String imageFilename[];
    private String type;
    private int travelLike;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getListNum() {
        return listNum;
    }

    public void setListNum(int listNum) {
        this.listNum = listNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String[] getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String[] imageFilename) {
        this.imageFilename = imageFilename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTravelLike() {
        return travelLike;
    }

    public void setTravelLike(int travelLike) {
        this.travelLike = travelLike;
    }
}
