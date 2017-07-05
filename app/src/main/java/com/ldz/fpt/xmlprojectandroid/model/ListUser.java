package com.ldz.fpt.xmlprojectandroid.model;

import java.util.List;

/**
 * Created by linhdq on 7/4/17.
 */

public class ListUser {
    private List<User> list;

    public ListUser() {
    }

    public ListUser(List<User> list) {
        this.list = list;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
