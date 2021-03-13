package com.mkg_dhbw.xcalc.utilities;

import android.os.AsyncTask;

import com.mkg_dhbw.xcalc.models.OutputResult;
import com.mkg_dhbw.xcalc.models.ToConvert;

public class CalculateConvertionTask extends AsyncTask<ToConvert, String, OutputResult> {

    @Override
    protected OutputResult doInBackground(ToConvert... toConverts) {
        return CurrencyConverter.Convert(toConverts[0]);
    }
}