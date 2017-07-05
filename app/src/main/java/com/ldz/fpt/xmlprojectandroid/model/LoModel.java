package com.ldz.fpt.xmlprojectandroid.model;

/**
 * Created by linhdq on 7/2/17.
 */

public class LoModel {
    private String username;
    private int number;
    private float point;
    private String date;

    public LoModel() {
    }

    public LoModel(String username, int number, float point, String date) {
        this.username = username;
        this.number = number;
        this.point = point;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
