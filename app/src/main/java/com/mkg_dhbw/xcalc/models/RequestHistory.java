package com.mkg_dhbw.xcalc.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
public class RequestHistory {
    private LocalDateTime timestamp;
    private Currency foreignCurrency;
    private Currency baseCurrency;
    private double exchangeRate;
    private double foreignAmount;
    private double baseAmount;
    public RequestHistory(LocalDateTime timestamp, Currency foreignCurrency, Currency baseCurrency, double exchangeRate, double foreignAmount, double baseAmount) {
        this.timestamp = timestamp;
        this.foreignCurrency = foreignCurrency;
        this.baseCurrency = baseCurrency;
        this.exchangeRate = exchangeRate;
        this.foreignAmount = foreignAmount;
        this.baseAmount = baseAmount;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public Currency getForeignCurrency() {
        return foreignCurrency;
    }
    public void setForeignCurrency(Currency foreignCurrency) {
        this.foreignCurrency = foreignCurrency;
    }
    public Currency getBaseCurrency() {
        return baseCurrency;
    }
    public void setBaseCurrency(Currency baseCurrency) {
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
}
