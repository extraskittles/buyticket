package com.skittles.buyticket.detailModel;

import com.skittles.buyticket.model.Scene;

public class SceneDetail extends Scene {
    private Integer id;
    private int movieLength;
    private String movieName;



    private Double price;

    private String description;

    private String cinemaName;

    private Integer sitNumber;

    private String hallName;

    private String leftSit;

    public int getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(int movieLength) {
        this.movieLength = movieLength;
    }

    public String getLeftSit() {
        return leftSit;
    }

    public void setLeftSit(String leftSit) {
        this.leftSit = leftSit;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public Integer getSitNumber() {
        return sitNumber;
    }

    public void setSitNumber(Integer sitNumber) {
        this.sitNumber = sitNumber;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }
}
