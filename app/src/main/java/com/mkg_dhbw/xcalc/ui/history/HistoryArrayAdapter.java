package com.mkg_dhbw.xcalc.ui.history;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.mkg_dhbw.xcalc.R;
import com.mkg_dhbw.xcalc.models.RequestHistory;

import java.time.format.DateTimeFormatter;
import java.util.List;
public class HistoryArrayAdapter extends ArrayAdapter<RequestHistory> {
    private List<RequestHistory> list;
    public HistoryArrayAdapter(@NonNull Context context, @NonNull List<RequestHistory> objects) {
        super(context, R.layout.listview_eintrag, R.id.textview_begriff, objects);
        list = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView nummerTextView = view.findViewById(R.id.textview_begriff);
        int nummer = position + 1;
        RequestHistory tmp = list.get(position);
        nummerTextView.setText( tmp.getTimestamp().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")) + "                   " + tmp.getBaseCurrency() + "  " +tmp.getBaseAmount());
        return view;
    }
}