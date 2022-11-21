package com.example.besttraveler;

public class Review {
    private String startLocation, date, review, user;

    public Review() {

    }

    public Review(String startLocation, String review, String date, String user) {
        this.startLocation = startLocation;
        this.review = review;
        this.date = date;
        this.user = user;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
