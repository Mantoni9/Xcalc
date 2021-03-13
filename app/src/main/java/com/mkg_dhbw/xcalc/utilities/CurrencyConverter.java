package com.mkg_dhbw.xcalc.utilities;

import com.mkg_dhbw.xcalc.models.CalculatedRate;
import com.mkg_dhbw.xcalc.models.OutputResult;
import com.mkg_dhbw.xcalc.models.Rate;
import com.mkg_dhbw.xcalc.models.ToConvert;

import java.util.ArrayList;
import java.util.List;

public class CurrencyConverter {

    public static OutputResult Convert(ToConvert toConvert){

        List<CalculatedRate> convertedRates = new ArrayList<CalculatedRate>();
        for (Rate rate : toConvert.getLatestRates().getRates()) {
            double multiplicator = rate.getAmount();

            double newAmount = multiplicator * toConvert.getAmount();
            convertedRates.add(new CalculatedRate(rate.getCurrency(), toConvert.getAmount(), newAmount));
        }

        return new OutputResult(convertedRates);
    }
}