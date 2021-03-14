package com.mkg_dhbw.xcalc.models;

public class ToConvert {

    private LatestRates latestRates;
    private double amount;
    private Currency foreignCurrency;

    public ToConvert(LatestRates latestRates, double amount, Currency foreignCurrency) {
        this.latestRates = latestRates;
        this.amount = amount;
        this.foreignCurrency = foreignCurrency;
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

    public Currency getForeignCurrency() {
        return foreignCurrency;
    }

    public void setForeignCurrency(Currency foreignCurrency) {
        this.foreignCurrency = foreignCurrency;
    }
}