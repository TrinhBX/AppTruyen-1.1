package com.example.apptruyen.truyenchu.entities;

import java.io.Serializable;

public class Story implements Serializable {
    private int idStory;
    private String storyName;
    private String author;
    private String status;
    private String type;
    private String avatar;
    private String review;


    public Story(String storyName, String author, String status, String type) {
        this.storyName = storyName;
        this.author = author;
        this.status = status;
        this.type = type;
    }


    public Story(int idStory, String storyName, String author, String status, String type, String review) {
        this.idStory = idStory;
        this.storyName = storyName;
        this.author = author;
        this.status = status;
        this.type = type;
        this.review = review;
    }

    public Story(int idStory, String storyName, String author, String status, String type, String avatar, String review) {
        this.idStory = idStory;
        this.storyName = storyName;
        this.author = author;
        this.status = status;
        this.type = type;
        this.avatar = avatar;
        this.review = review;
    }

    public int getIdStory() {
        return idStory;
    }

    public void setIdStory(int idStory) {
        this.idStory = idStory;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String image) {
        this.avatar = image;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
