package com.mkg_dhbw.xcalc.models;

public class Rate {

    private Currency currency;
    private double amount;

    public Rate(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
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

    @Override
    public String toString() {
        return "Rate{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }
}
