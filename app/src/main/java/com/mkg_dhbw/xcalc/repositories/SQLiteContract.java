package com.mkg_dhbw.xcalc.repositories;

import android.provider.BaseColumns;

public class SQLiteContract {

    private SQLiteContract() {}

    public static class ExchangeRateEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_CURRENCY = "currency";
        public static final String COLUMN_NAME_CURRENCY_AMOUNT = "amount";
    }
}
