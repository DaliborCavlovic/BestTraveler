package com.example.besttraveler;

public class Review {
    private String startLocation, date, review;

    public Review() {

    }

    public Review(String startLocation, String review, String date) {
        this.startLocation = startLocation;
        this.review = review;
        this.date = date;
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

}
