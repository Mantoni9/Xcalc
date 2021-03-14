package com.mkg_dhbw.xcalc.utilities;

import com.mkg_dhbw.xcalc.models.CalculatedRate;
import com.mkg_dhbw.xcalc.models.Currency;
import com.mkg_dhbw.xcalc.models.OutputResult;
import com.mkg_dhbw.xcalc.models.Rate;
import com.mkg_dhbw.xcalc.models.ToConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyConverter {

    /*public static OutputResult Convert(ToConvert toConvert){

        List<CalculatedRate> convertedRates = new ArrayList<CalculatedRate>();
        for (Rate rate : toConvert.getLatestRates().getRates()) {
            double multiplicator = rate.getAmount();

            double newAmount = multiplicator * toConvert.getAmount();
            convertedRates.add(new CalculatedRate(rate.getCurrency(), toConvert.getAmount(), newAmount));
        }

        return new OutputResult(convertedRates);
    }*/

    public static OutputResult ConvertToCurrency(ToConvert convert) {
        // get currency to convert to
        Currency foreignCurrency = convert.getForeignCurrency();

        // get exchange rate
        List<Rate> exchangeRates = convert.getLatestRates().getRates();
        Optional<Rate> rateResult = exchangeRates.stream().filter(r -> r.getCurrency().equals(foreignCurrency)).findFirst();
        Rate exchangeRate = rateResult.get();
        double rate = exchangeRate.getAmount();

        // calculate
        double foreignAmount = convert.getAmount() * rate;

        // return
        return new OutputResult(foreignCurrency, foreignAmount, rate);
    }
}