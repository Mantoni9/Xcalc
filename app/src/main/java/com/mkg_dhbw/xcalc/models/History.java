package com.mkg_dhbw.xcalc.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class History {

    private Currency baseCurrency;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<HistoryRates> rates;

    public History(Currency baseCurrency, LocalDate startDate, LocalDate endDate, List<HistoryRates> rates) {
        this.baseCurrency = baseCurrency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rates = rates;
    }

    public History() {
    }

    /**
     * Decodes a JSON String into a History object
     * @param encodedJson String - JSON
     */
    public static History decodeJSON(String encodedJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(encodedJson);

        // Base
        String baseCurrency = jsonObject.getString("base");
        // Start & End Date
        String startAt = jsonObject.getString("start_at");
        String endAt = jsonObject.getString("end_at");
        LocalDate startAtDate = LocalDate.parse(startAt);
        LocalDate endAtDate = LocalDate.parse(endAt);

        // rates
        JSONObject rates = jsonObject.getJSONObject("rates");

        // loop through dates
        //long diffInDays = Duration.between(startAtDate, endAtDate).toDays();
        long diffInDays = ChronoUnit.DAYS.between(startAtDate, endAtDate);

        // extract Currencies
        List<HistoryRates> history = new ArrayList<HistoryRates>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < diffInDays; i++) {
            LocalDate date = startAtDate.plusDays(i);
            // get date object
            JSONObject tmp = null;
            try {
                tmp = rates.getJSONObject(date.format(formatter));
            } catch (JSONException e){

            }

            if (tmp != null) {
                // rates
                List<Rate> rateList = new ArrayList<Rate>();

                for (Currency cur : Currency.values()) {
                    if (!cur.toString().contains(baseCurrency))
                        rateList.add(new Rate(cur, tmp.getDouble(cur.toString())));
                }

                history.add(new HistoryRates(date, Currency.valueOf(baseCurrency), rateList));
            }

        }

        return new History(Currency.valueOf(baseCurrency), startAtDate, endAtDate, history);
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
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

    public List<HistoryRates> getRates() {
        return rates;
    }

    public void setRates(List<HistoryRates> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "History{" +
                "baseCurrency=" + baseCurrency +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", rates=" + rates +
                '}';
    }
}
