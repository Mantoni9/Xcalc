package com.mkg_dhbw.xcalc.ui.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.mkg_dhbw.xcalc.models.HistoryRequest;
import com.mkg_dhbw.xcalc.models.LatestRates;
import com.mkg_dhbw.xcalc.models.OutputResult;
import com.mkg_dhbw.xcalc.models.Rate;
import com.mkg_dhbw.xcalc.models.ToConvert;
import com.mkg_dhbw.xcalc.repositories.GetHistoryRepositoryTask;
import com.mkg_dhbw.xcalc.repositories.GetLatestRepositoryTask;
import com.mkg_dhbw.xcalc.repositories.InsertHistoryRequestInDatabaseTask;
import com.mkg_dhbw.xcalc.repositories.SQLiteRepository;
import com.mkg_dhbw.xcalc.utilities.CalculateConvertionTask;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView textBanner;

    private Button calculateExchangeButton;
    private EditText exchangeAmountInput;
    private TextView resultText;
    private Button debugButtonLatest;
    private Button debugButtonHistory;
    private Button debugButtonSqlite;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        resultText = root.findViewById(R.id.result_text);
        exchangeAmountInput = root.findViewById(R.id.input_currency_value);
        calculateExchangeButton = root.findViewById(R.id.btn_get_latest);
        calculateExchangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // value of the input
                String value = String.valueOf(exchangeAmountInput.getText());
                double currencyAmount = Integer.parseInt(value);

                LatestRates latestRates = null;
                GetLatestRepositoryTask task = new GetLatestRepositoryTask();
                try {
                    latestRates = task.execute(Currency.EUR).get();

                } catch (ExecutionException e) {
                    resultText.setText("Something went wrong!");
                } catch (InterruptedException e) {
                    resultText.setText("Something went wrong!");
                }


                CalculateConvertionTask calculateConvertionTask = new CalculateConvertionTask();
                try{
                    OutputResult outputResult = calculateConvertionTask.execute(new ToConvert(latestRates, currencyAmount)).get();
                    resultText.setText(outputResult.toString());
                } catch (InterruptedException e) {
                    resultText.setText("Something went wrong!");
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    resultText.setText("Something went wrong!");
                    e.printStackTrace();
                }

                //Context context = getContext();
                //Toast toast = Toast.makeText(context, value, Toast.LENGTH_LONG);
                //toast.show();
            }
        });

        debugButtonLatest = root.findViewById(R.id.debug_button_latest);
        debugButtonLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();

                GetLatestRepositoryTask task = new GetLatestRepositoryTask();
                task.execute(Currency.DKK);
                Toast toast = Toast.makeText(context, "test", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        debugButtonHistory = root.findViewById(R.id.debug_button_history);
        debugButtonHistory.setOnClickListener(new View.OnClickListener() {
          
        final TextView textView = root.findViewById(R.id.textBanner);
        textView.setSelected(true);

        GraphView graph = (GraphView) root.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onClick(View view) {
                Context context = getContext();

                GetHistoryRepositoryTask task = new GetHistoryRepositoryTask();
                task.execute(new HistoryRequest(LocalDate.parse("2021-01-01"), LocalDate.parse("2021-02-01"), Currency.EUR));

                Toast toast = Toast.makeText(context, "test", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        debugButtonSqlite = root.findViewById(R.id.debug_button_sqlite);
        debugButtonSqlite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Context context = getContext();

                InsertHistoryRequestInDatabaseTask task = new InsertHistoryRequestInDatabaseTask(context);

                List<Rate> list = new ArrayList<Rate>();
                list.add(new Rate(Currency.USD, 10.0));
                task.execute(new LatestRates(Currency.EUR, list));

                //SQLiteRepository repository = new SQLiteRepository(context);

                //repository.ReadEntry();
                //repository.SaveEntry(new LatestRates(Currency.EUR, list));

                Toast toast = Toast.makeText(context, "sql", Toast.LENGTH_LONG);
                toast.show();
            }
        });
        }*//*);*/

        return root;
    }
}