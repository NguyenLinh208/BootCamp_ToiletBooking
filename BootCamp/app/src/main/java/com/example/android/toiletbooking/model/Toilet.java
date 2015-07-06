package com.example.android.toiletbooking.model;

import java.io.Serializable;

/**
 * Created by usr0200475 on 15/06/29.
 */

/**
 * トイレの情報を格納する
 *
 */
public class Toilet extends Info implements Serializable {
    /**
     *
     */
    private boolean status;
    private static final long serialVersionUID = 1L;
    public Toilet(String number, String name, int floor, int waiting, boolean status) {
        super(number,name,floor,waiting);
        this.status = status;
    }

    public Toilet() {
        super();
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}

