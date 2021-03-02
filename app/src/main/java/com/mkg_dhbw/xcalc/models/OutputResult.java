package com.mkg_dhbw.xcalc.models;

import java.util.List;

public class OutputResult {
    private List<CalculatedRate> calculatedRates;

    public OutputResult(List<CalculatedRate> calculatedRates) {
        this.calculatedRates = calculatedRates;
    }

    public List<CalculatedRate> getCalculatedRates() {
        return calculatedRates;
    }

    public void setCalculatedRates(List<CalculatedRate> calculatedRates) {
        this.calculatedRates = calculatedRates;
    }

    @Override
    public String toString() {
        return "OutputResult{" +
                "calculatedRates=" + calculatedRates +
                '}';
    }
}