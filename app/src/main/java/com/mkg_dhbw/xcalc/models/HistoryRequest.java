package com.mkg_dhbw.xcalc.models;

import java.time.LocalDate;

public class HistoryRequest {

    private LocalDate startDate;
    private LocalDate endDate;
    private Currency baseCurrency;

    public HistoryRequest(LocalDate startDate, LocalDate endDate, Currency baseCurrency) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.baseCurrency = baseCurrency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }
}
