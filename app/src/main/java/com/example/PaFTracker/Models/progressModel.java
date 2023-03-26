package com.example.PaFTracker.Models;

public class progressModel {
   String PID, PASSPORT, D0_time,D0_date,D1_time,D1_date,D2_time,D2_date,D3_time,
           D3_date,D4_time,D4_date,D5_time, D5_date;

    public progressModel() {
    }

    public progressModel(String PID, String PASSPORT, String d0_time, String d0_date, String d1_time, String d1_date, String d2_time, String d2_date, String d3_time, String d3_date, String d4_time, String d4_date, String d5_time, String d5_date) {
        this.PID = PID;
        this.PASSPORT = PASSPORT;
        D0_time = d0_time;
        D0_date = d0_date;
        D1_time = d1_time;
        D1_date = d1_date;
        D2_time = d2_time;
        D2_date = d2_date;
        D3_time = d3_time;
        D3_date = d3_date;
        D4_time = d4_time;
        D4_date = d4_date;
        D5_time = d5_time;
        D5_date = d5_date;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getPASSPORT() {
        return PASSPORT;
    }

    public void setPASSPORT(String PASSPORT) {
        this.PASSPORT = PASSPORT;
    }

    public String getD0_time() {
        return D0_time;
    }

    public void setD0_time(String d0_time) {
        D0_time = d0_time;
    }

    public String getD0_date() {
        return D0_date;
    }

    public void setD0_date(String d0_date) {
        D0_date = d0_date;
    }

    public String getD1_time() {
        return D1_time;
    }

    public void setD1_time(String d1_time) {
        D1_time = d1_time;
    }

    public String getD1_date() {
        return D1_date;
    }

    public void setD1_date(String d1_date) {
        D1_date = d1_date;
    }

    public String getD2_time() {
        return D2_time;
    }

    public void setD2_time(String d2_time) {
        D2_time = d2_time;
    }

    public String getD2_date() {
        return D2_date;
    }

    public void setD2_date(String d2_date) {
        D2_date = d2_date;
    }

    public String getD3_time() {
        return D3_time;
    }

    public void setD3_time(String d3_time) {
        D3_time = d3_time;
    }

    public String getD3_date() {
        return D3_date;
    }

    public void setD3_date(String d3_date) {
        D3_date = d3_date;
    }

    public String getD4_time() {
        return D4_time;
    }

    public void setD4_time(String d4_time) {
        D4_time = d4_time;
    }

    public String getD4_date() {
        return D4_date;
    }

    public void setD4_date(String d4_date) {
        D4_date = d4_date;
    }

    public String getD5_time() {
        return D5_time;
    }

    public void setD5_time(String d5_time) {
        D5_time = d5_time;
    }

    public String getD5_date() {
        return D5_date;
    }

    public void setD5_date(String d5_date) {
        D5_date = d5_date;
    }
}
