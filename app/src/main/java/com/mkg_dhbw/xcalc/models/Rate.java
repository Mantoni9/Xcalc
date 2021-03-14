package com.mkg_dhbw.xcalc.models;

import java.time.LocalDate;

public class Rate {

    private Currency currency;
    private double amount;
    private LocalDate date;

    public Rate(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Rate(Currency currency, double amount, LocalDate date) {
        this.currency = currency;
        this.amount = amount;
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }
}
