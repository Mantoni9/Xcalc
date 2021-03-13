package com.mkg_dhbw.xcalc.models;

import java.time.LocalDate;
import java.util.List;

public class HistoryRates {

    private LocalDate date;
    private Currency baseCurrency;
    private List<Rate> rates;

    public HistoryRates(LocalDate date, Currency baseCurrency, List<Rate> rates) {
        this.date = date;
        this.baseCurrency = baseCurrency;
        this.rates = rates;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "HistoryRates{" +
                "date=" + date +
                ", baseCurrency=" + baseCurrency +
                ", rates=" + rates +
                '}';
    }
}
