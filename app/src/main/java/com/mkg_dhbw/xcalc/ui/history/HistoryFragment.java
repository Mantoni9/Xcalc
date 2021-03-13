package com.mkg_dhbw.xcalc.ui.history;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SyncContext;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mkg_dhbw.xcalc.MainActivity;
import com.mkg_dhbw.xcalc.R;
import com.mkg_dhbw.xcalc.models.RequestHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private ListView historyList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        final TextView textView = root.findViewById(R.id.historyView);
        historyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        historyList = root.findViewById(R.id.list_history);
        List<RequestHistory> timmsList = new ArrayList<>();
        timmsList.add(new RequestHistory(LocalDate.parse("2021-02-21"), "USD", "EUR", 1.16, 23.2, 20.0));

        HistoryArrayAdapter historyArray = new HistoryArrayAdapter(getContext(), timmsList);
        historyList.setAdapter(historyArray);
        historyList.setOnItemClickListener((AdapterView.OnItemClickListener) (parent, view, position, id) -> {
            RequestHistory selectedItem = (RequestHistory) parent.getItemAtPosition(position);
            final Dialog dialog = new Dialog( getContext());
            dialog.setContentView(R.layout.dialog);
            dialog.setTitle("Details");

            TextView dateView = (TextView) dialog.findViewById(R.id.dateView);
            dateView.setText("14.04.1997");
            TextView baseCurrencyView = (TextView) dialog.findViewById(R.id.baseCurrencyView);
            baseCurrencyView.setText("20 Euro");
            TextView foreignCurrencyView = (TextView) dialog.findViewById(R.id.foreignCurrencyView);
            foreignCurrencyView.setText("50 Dollar");
            TextView exchangeRateView = (TextView) dialog.findViewById(R.id.exchangeRateView);
            exchangeRateView.setText("2.5");
            dialog.show();
        });

        return root;
    }
}
