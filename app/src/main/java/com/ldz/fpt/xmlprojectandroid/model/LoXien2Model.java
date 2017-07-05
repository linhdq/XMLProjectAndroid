package com.ldz.fpt.xmlprojectandroid.model;

/**
 * Created by linhdq on 7/2/17.
 */

public class LoXien2Model {
    private String username;
    private int number1;
    private int number2;
    private float point;
    private String date;

    public LoXien2Model() {
    }

    public LoXien2Model(String username, int number1, int number2, float point, String date) {
        this.username = username;
        this.number1 = number1;
        this.number2 = number2;
        this.point = point;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
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
