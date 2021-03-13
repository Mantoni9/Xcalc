package com.mkg_dhbw.xcalc.models;

public class ToConvert {

    private LatestRates latestRates;
    private double amount;

    public ToConvert(LatestRates latestRates, double amount) {
        this.latestRates = latestRates;
        this.amount = amount;
    }

    public LatestRates getLatestRates() {
        return latestRates;
    }

    public void setLatestRates(LatestRates latestRates) {
        this.latestRates = latestRates;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}