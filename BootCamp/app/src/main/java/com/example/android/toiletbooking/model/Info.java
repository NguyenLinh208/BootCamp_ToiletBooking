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
    public Info(String number, String name) {
        super();
        this.number = number;
        this.name = name;
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
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.number+" - "+this.name;
    }
}
