package com.example.sp41mer.android_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

class Item {

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

    private final Bitmap picture;

    Item(long id, Date date, String picture, int oneR, int twoR, int fiveR, int tenR,
         int oneK, int fiveK, int tenK, int fiftyK) {
        this.id = id;
        this.date = date;
        this.oneR = oneR;
        this.twoR = twoR;
        this.fiveR = fiveR;
        this.tenR = tenR;
        this.oneK = oneK;
        this.fiveK = fiveK;
        this.tenK = tenK;
        this.fiftyK = fiftyK;
        this.picture = BitmapFactory.decodeFile(picture);
    }

    String getDate() {
        return DateFormat.getDateTimeInstance().format(date);
    }

    int getCount() {
        return oneR + twoR + fiveR + tenR + oneK + fiveK + tenK + fiftyK;
    }

    String getSum() {
        return String.format(Locale.getDefault(), "%.2f", (oneR + twoR + fiveR + tenR) + (oneK + fiveK + tenK + fiftyK) / 100.) + " руб.";
    }

    Bitmap getPicture() {
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