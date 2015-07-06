package com.example.android.toiletbooking.model;

import java.io.Serializable;


/**
 * Created by usr0200475 on 15/06/30.
 * このクラスは社員と部署の情報を保存る
 */

    public class Info implements Serializable {
    private static final long serialVersionUID = 1L;
    private String number;
    private String name;
    private int floor;
    private int waiting;
    public Info(String number, String name, int floor, int waiting) {
        super();
        this.number = number;
        this.name = name;
        this.floor = floor;
        this.waiting = waiting;
    }
    public Info() {
        super();
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String Number) {
        this.number = Number;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getFloor() { return floor; }
    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.name;
    }
}
