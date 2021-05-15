package com.mkg_dhbw.xcalc.ui.history;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mkg_dhbw.xcalc.R;
import com.mkg_dhbw.xcalc.models.RequestHistory;
import com.mkg_dhbw.xcalc.repositories.SQLiteRepository;

import java.time.format.DateTimeFormatter;
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
        historyList.setBackgroundColor(Color.WHITE);

        List<RequestHistory> requestList = new ArrayList<>();

        SQLiteRepository repository = new SQLiteRepository(getContext());
        requestList = repository.readEntries();

        HistoryArrayAdapter historyArray = new HistoryArrayAdapter(getContext(), requestList);
        historyList.setAdapter(historyArray);
        historyList.setOnItemClickListener((AdapterView.OnItemClickListener) (parent, view, position, id) -> {
            RequestHistory selectedItem = (RequestHistory) parent.getItemAtPosition(position);
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog);
            dialog.setTitle("Details");
            TextView dateView = (TextView) dialog.findViewById(R.id.dateView);
            dateView.setText("Datum: " + selectedItem.getTimestamp().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")) + " " + selectedItem.getDbId());
            TextView baseCurrencyView = (TextView) dialog.findViewById(R.id.baseCurrencyView);
            baseCurrencyView.setText("Eigenwährung: " + selectedItem.getBaseAmount() + " " + selectedItem.getBaseCurrency());
            TextView foreignCurrencyView = (TextView) dialog.findViewById(R.id.foreignCurrencyView);
            foreignCurrencyView.setText("Fremdwährung: " + selectedItem.getForeignAmount() + " " + selectedItem.getForeignCurrency());
            TextView exchangeRateView = (TextView) dialog.findViewById(R.id.exchangeRateView);
            exchangeRateView.setText("Wechselkurs: " + selectedItem.getExchangeRate());
            Button deleteButton = (Button) dialog.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repository.deleteEntry(id);
                    dialog.cancel();
                    repository.readEntries();
                }
            });
            dialog.show();
        });

        return root;
    }
}
