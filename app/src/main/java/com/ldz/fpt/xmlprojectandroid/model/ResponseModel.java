package com.ldz.fpt.xmlprojectandroid.model;

/**
 * Created by linhdq on 7/5/17.
 */

public class ResponseModel {
    private String message;
    private boolean status;

    public ResponseModel() {
    }

    public ResponseModel(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
