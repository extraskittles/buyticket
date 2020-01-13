package com.skittles.buyticket.model;

public class Hall {
    private Integer id;

    private Integer sitNumber;

    private String name;

    private String leftSit;

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

    public String getLeftSit() {
        return leftSit;
    }

    public void setLeftSit(String leftSit) {
        this.leftSit = leftSit == null ? null : leftSit.trim();
    }
}