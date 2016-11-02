package com.example.sp41mer.android_project;

import java.text.DateFormat;
import java.util.Date;

public class Item {

    private final long id;
    private final Date date;
    private final int oneR;
    private final int twoR;
    private final int fiveR;
    private final int tenR;
    private final int oneK;
    private final int fiveK;
    private final int tenK;
    private final int fiftyK;

    private final String picture;

    Item(long id, Date date, String picture, int oneR, int twoR, int fiveR, int tenR,
         int oneK, int fiveK, int tenK, int fiftyK) {
        this.id = id;
        this.date = date;
        this.picture = picture;
        this.oneR = oneR;
        this.twoR = twoR;
        this.fiveR = fiveR;
        this.tenR = tenR;
        this.oneK = oneK;
        this.fiveK = fiveK;
        this.tenK = tenK;
        this.fiftyK = fiftyK;
    }

    String getDate() {
        return DateFormat.getDateTimeInstance().format(date);
    }

    int getCount() {
        return oneR + twoR + fiveR + tenR + oneK + fiveK + tenK + fiftyK;
    }

    String getSum() {
        return String.valueOf((oneR + twoR + fiveR + tenR) + (oneK + fiveK + tenK + fiftyK) / 100.) + " руб.";
    }

    String getPicture() {
        return picture;
    }

    public int getOneR() {
        return oneR;
    }

    public int getFiftyK() {
        return fiftyK;
    }

    public int getFiveK() {
        return fiveK;
    }

    public int getFiveR() {
        return fiveR;
    }

    public int getOneK() {
        return oneK;
    }

    public int getTenK() {
        return tenK;
    }

    public int getTenR() {
        return tenR;
    }

    public int getTwoR() {
        return twoR;
    }

    public long getId() {
        return id;
    }
}