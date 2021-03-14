package com.mkg_dhbw.xcalc.models;

import java.util.List;

public class OutputResult {
    private Currency foreignCurrency;
    private double foreignAmount;
    private double exchangeRate;

    public OutputResult(Currency foreignCurrency, double foreignAmount, double exchangeRate) {
        this.foreignCurrency = foreignCurrency;
        this.foreignAmount = foreignAmount;
        this.exchangeRate = exchangeRate;
    }

    public Currency getForeignCurrency() {
        return foreignCurrency;
    }

    public void setForeignCurrency(Currency foreignCurrency) {
        this.foreignCurrency = foreignCurrency;
    }

    public double getForeignAmount() {
        return foreignAmount;
    }

    public void setForeignAmount(double foreignAmount) {
        this.foreignAmount = foreignAmount;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}