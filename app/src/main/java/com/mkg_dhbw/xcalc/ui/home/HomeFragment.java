package com.mkg_dhbw.xcalc.ui.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.mkg_dhbw.xcalc.R;
import com.mkg_dhbw.xcalc.models.CalculatedRate;
import com.mkg_dhbw.xcalc.models.Currency;
import com.mkg_dhbw.xcalc.models.History;
import com.mkg_dhbw.xcalc.models.HistoryRates;
import com.mkg_dhbw.xcalc.models.HistoryRequest;
import com.mkg_dhbw.xcalc.models.LatestRates;
import com.mkg_dhbw.xcalc.models.OutputResult;
import com.mkg_dhbw.xcalc.models.Rate;
import com.mkg_dhbw.xcalc.models.RequestHistory;
import com.mkg_dhbw.xcalc.models.ToConvert;
import com.mkg_dhbw.xcalc.repositories.GetHistoryRepositoryTask;
import com.mkg_dhbw.xcalc.repositories.GetLatestRepositoryTask;
import com.mkg_dhbw.xcalc.repositories.SQLiteRepository;
import com.mkg_dhbw.xcalc.utilities.CalculateConvertionTask;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView textBanner;

    private TextView textViewOwnCurrency;
    private TextView textViewForeignCurrency;
    private TextView textViewDisclaimer;

    private Spinner eigenWaehrung;
    private Spinner fremdWaehrung;
    private TextView eigenBetrag;
    private TextView fremdBetrag;
    private Button calculateButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Banner
        // TODO: reduce banner speed
        textBanner = root.findViewById(R.id.textBanner);
        textBanner.setSelected(true);
        textBanner.setText(getBannerText(Currency.EUR));

        // Exchange Graph
        // TODO: Achsen richtig beschriften
        LineGraphSeries<DataPoint> chartData = getChartData(Currency.EUR, Currency.USD);
        GraphView graph = (GraphView) root.findViewById(R.id.graph);
        if (chartData != null) {
            graph.addSeries(chartData);
        }


        // Information text
        textViewOwnCurrency = root.findViewById(R.id.textViewOwnCurrency);
        textViewForeignCurrency = root.findViewById(R.id.textViewForeignCurrency);
        textViewDisclaimer = root.findViewById(R.id.disclaimer);
        textViewOwnCurrency.setText("");
        textViewForeignCurrency.setText("");
        textViewDisclaimer.setText("");

        // Waehrungsumrechnung
        eigenWaehrung = root.findViewById(R.id.eigenWaehrung);
        ArrayAdapter<CharSequence> eigenWaehrungAdapter = ArrayAdapter.createFromResource(getContext(), R.array.currencies, android.R.layout.simple_spinner_dropdown_item);
        eigenWaehrung.setAdapter(eigenWaehrungAdapter);
        eigenWaehrung.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String txt = (String) adapterView.getItemAtPosition(i);
                Currency selectedCurrency = Currency.valueOf(txt);

                Currency foreignCurrency = Currency.valueOf((String) fremdWaehrung.getSelectedItem());
                if (foreignCurrency.equals(selectedCurrency))
                {
                    showErrorMessage("Please use different currencies!");
                    return;
                }

                // Banner
                textBanner.setText(getBannerText(selectedCurrency));
                // Graphen
                graph.removeAllSeries();
                graph.addSeries(getChartData(selectedCurrency, foreignCurrency));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fremdWaehrung = root.findViewById(R.id.fremdWaehrung);
        ArrayAdapter<CharSequence> fremdWaehrungAdapter = ArrayAdapter.createFromResource(getContext(), R.array.currencies2, android.R.layout.simple_spinner_dropdown_item);
        fremdWaehrung.setAdapter(fremdWaehrungAdapter);
        fremdWaehrung.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String txt = (String) adapterView.getItemAtPosition(i);
                Currency selectedCurrency = Currency.valueOf(txt);

                Currency baseCurrency = Currency.valueOf((String) eigenWaehrung.getSelectedItem());
                if (selectedCurrency.equals(baseCurrency)) {
                    showErrorMessage("Please use different currencies!");
                    return;
                }

                // Graph
                graph.removeAllSeries();
                graph.addSeries(getChartData(baseCurrency, selectedCurrency));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        eigenBetrag = root.findViewById(R.id.eigenBetrag);
        fremdBetrag = root.findViewById(R.id.fremdBetrag);

        calculateButton = root.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (eigenBetrag.getText().length() == 0) {
                    showErrorMessage("Amount must be greater than 0!");
                    return;
                }

                double betrag = Double.parseDouble(eigenBetrag.getText().toString());
                Currency waehrung = Currency.valueOf(eigenWaehrung.getSelectedItem().toString());
                Currency toWaehrung = Currency.valueOf(fremdWaehrung.getSelectedItem().toString());

                if (waehrung.equals(toWaehrung)) {
                    showErrorMessage("Please use different currencies!");
                    return;
                }

                // basis Waehrung -> api request
                LatestRates latestRates = getLatestRates(waehrung);

                CalculateConvertionTask calculateConvertionTask = new CalculateConvertionTask();
                OutputResult outputResult = null;
                try {
                    outputResult = calculateConvertionTask.execute(new ToConvert(latestRates, betrag, toWaehrung)).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                saveToSqLite(
                        new RequestHistory(
                                LocalDateTime.now(),
                                toWaehrung,
                                waehrung,
                                outputResult.getExchangeRate(),
                                outputResult.getForeignAmount(),
                                betrag
                        )
                );

                // Set Output
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
                fremdBetrag.setText("" + outputResult.getForeignAmount());
                textViewOwnCurrency.setText("" + betrag + " " + waehrung.toString() + " entspricht");
                textViewForeignCurrency.setText("" + outputResult.getForeignAmount() + " " + toWaehrung.toString());
                textViewDisclaimer.setText("" + LocalDateTime.now().format(formatter) + ", Haftungsausschluss");
            }
        });


        return root;
    }

    private LatestRates getLatestRates(Currency baseCurrency) {
        GetLatestRepositoryTask getLatestRepositoryTask = new GetLatestRepositoryTask();
        LatestRates latestRates = null;
        try {
            latestRates = getLatestRepositoryTask.execute(baseCurrency).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return latestRates;
    }

    private String getBannerText(Currency baseCurrency) {
        LatestRates latestRates = getLatestRates(baseCurrency);

        String output = "";

        if (latestRates != null) {
            for (Rate rate :
                    latestRates.getRates()) {
                output += "" + rate.getCurrency().toString() + " " + rate.getAmount() + "   ";
            }
        }

        return output;
    }

    private LineGraphSeries<DataPoint> getChartData(Currency baseCurrency, Currency foreignCurrency) {

        GetHistoryRepositoryTask getHistoryRepositoryTask = new GetHistoryRepositoryTask();
        HistoryRequest historyRequest = new HistoryRequest(
                LocalDate.parse("2020-07-13"),
                LocalDate.parse("2021-03-13"),
                baseCurrency,
                foreignCurrency);

        History history = null;

        try {
            history = getHistoryRepositoryTask.execute(historyRequest).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();

        // Get Currency rates
        List<Rate> historyRatesOfBaseCurrency = new ArrayList<>();

        if (history == null)
            return series;

        // loop through all Rates of Date
        for (HistoryRates hRates : history.getRates()) {
            // Loop through Rates of specific day
            for (Rate rate : hRates.getRates()) {

                // wenn Currency der umrechnungs Currency entspricht abspeichern
                if (rate.getCurrency().equals(foreignCurrency)) {
                    rate.setDate(hRates.getDate());
                    historyRatesOfBaseCurrency.add(rate);
                }
            }
        }

        for (Rate rate : historyRatesOfBaseCurrency) {
            series.appendData(new DataPoint(convertToDateViaInstant(rate.getDate()), rate.getAmount()), true, historyRatesOfBaseCurrency.toArray().length);
        }

        return series;
    }

    private void saveToSqLite(RequestHistory historyList) {
        SQLiteRepository repository = new SQLiteRepository(getContext());

        repository.insertEntry(historyList);
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public void showErrorMessage(String text) {
        Toast.makeText(getActivity(), text,
                Toast.LENGTH_LONG).show();
    }
}