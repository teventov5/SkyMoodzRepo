package com.T_Y.model;

import java.io.Serializable;

public class ForecastResult implements Serializable {
    private String date;
    private String headline;
    private String temperature;
    private String iconNumber;
    private long time;
    private HangoutsResult[] hangoutsResultsArr = new HangoutsResult[12];


    public ForecastResult(){};
    public HangoutsResult[] getHangoutsResultsArr() {
        return hangoutsResultsArr;
    }

    public void setHangoutsResultsArr(HangoutsResult[] hangoutsResultsArr) {
        this.hangoutsResultsArr = hangoutsResultsArr;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setTemperature(String temperature) {
        this.temperature =temperature;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getIconNumber() {
        return iconNumber;
    }

    public void setIconNumber(String iconNumber) {
        this.iconNumber = iconNumber;
    }

    public void print() {
        System.out.println(this.date);
        System.out.println(this.headline);
        System.out.println(this.temperature);
        System.out.println(this.getIconNumber());

    }

}
