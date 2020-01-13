package com.skittles.buyticket.param;



public class ConfirmOrderParam {
    private int cinemaId;
    private int sceneId;
    private String sitNumbers;
    private int movieId;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public String getSitNumbers() {
        return sitNumbers;
    }

    public void setSitNumbers(String sitNumbers) {
        this.sitNumbers = sitNumbers;
    }
}
