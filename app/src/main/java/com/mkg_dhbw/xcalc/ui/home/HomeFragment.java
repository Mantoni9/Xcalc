package com.mkg_dhbw.xcalc.ui.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mkg_dhbw.xcalc.R;
import com.mkg_dhbw.xcalc.models.Currency;
import com.mkg_dhbw.xcalc.models.LatestRates;
import com.mkg_dhbw.xcalc.repositories.GetLatestRepositoryTask;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button debugButtonLatest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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

        return root;
    }
}