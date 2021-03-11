package com.mkg_dhbw.xcalc.models;

import java.time.LocalDate;

public class RequestHistory {
    private LocalDate datum;
    private String foreignCurrency;
    private String baseCurrency;
    private double exchangeRate;
    private double foreignAmount;
    private double baseAmount;

    public RequestHistory(LocalDate datum, String foreignCurrency, String baseCurrency, double exchangeRate, double foreignAmount, double baseAmount) {
        this.datum = datum;
        this.foreignCurrency = foreignCurrency;
        this.baseCurrency = baseCurrency;
        this.exchangeRate = exchangeRate;
        this.foreignAmount = foreignAmount;
        this.baseAmount = baseAmount;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getForeignCurrency() {
        return foreignCurrency;
    }

    public void setForeignCurrency(String foreignCurrency) {
        this.foreignCurrency = foreignCurrency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getForeignAmount() {
        return foreignAmount;
    }

    public void setForeignAmount(double foreignAmount) {
        this.foreignAmount = foreignAmount;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
    }

    @Override
    public String toString() {
        return "RequestHistory{" +
                "datum=" + datum +
                ", foreignCurrency='" + foreignCurrency + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", foreignAmount=" + foreignAmount +
                ", baseAmount=" + baseAmount +
                '}';
    }
}
