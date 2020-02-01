package com.skittles.buyticket.model;

public class Hall {
    private Integer id;

    private Integer sitNumber;

    private String name;

    private Integer cinemaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSitNumber() {
        return sitNumber;
    }

    public void setSitNumber(Integer sitNumber) {
        this.sitNumber = sitNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }
}