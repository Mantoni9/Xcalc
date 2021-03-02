package com.mkg_dhbw.xcalc.models;

public class CalculatedRate {
    private Currency currency;
    private double oldAmount;
    private double newAmount;

    public CalculatedRate(Currency currency, double oldAmount, double newAmount) {
        this.currency = currency;
        this.oldAmount = oldAmount;
        this.newAmount = newAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(double oldAmount) {
        this.oldAmount = oldAmount;
    }

    public double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(double newAmount) {
        this.newAmount = newAmount;
    }

    @Override
    public String toString() {
        return "CalculatedRate{" +
                "currency=" + currency +
                ", oldAmount=" + oldAmount +
                ", newAmount=" + newAmount +
                "}\n";
    }
}