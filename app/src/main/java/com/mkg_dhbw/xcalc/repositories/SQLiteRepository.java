package com.mkg_dhbw.xcalc.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.mkg_dhbw.xcalc.models.Currency;
import com.mkg_dhbw.xcalc.models.LatestRates;
import com.mkg_dhbw.xcalc.models.Rate;

import java.util.ArrayList;
import java.util.List;

public class SQLiteRepository extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SQLiteContract.ExchangeRateEntry.TABLE_NAME + " (" +
                    SQLiteContract.ExchangeRateEntry._ID + " INTEGER PRIMARY KEY," +
                    SQLiteContract.ExchangeRateEntry.COLUMN_NAME_CURRENCY + " TEXT," +
                    SQLiteContract.ExchangeRateEntry.COLUMN_NAME_CURRENCY + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SQLiteContract.ExchangeRateEntry.TABLE_NAME;
    public static final String DATABASE_NAME = "ExchangeRateEntries.db";
    public static final int DATABASE_VERSION = 1;

    public SQLiteRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void SaveEntry(LatestRates latestRates) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Rate rate : latestRates.getRates()) {
            ContentValues values = new ContentValues();
            values.put(SQLiteContract.ExchangeRateEntry.COLUMN_NAME_CURRENCY, rate.getCurrency().toString());
            values.put(SQLiteContract.ExchangeRateEntry.COLUMN_NAME_CURRENCY_AMOUNT, rate.getAmount());

            db.insert(SQLiteContract.ExchangeRateEntry.TABLE_NAME, null, values);
        }
    }

    public LatestRates ReadEntry() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                SQLiteContract.ExchangeRateEntry.COLUMN_NAME_CURRENCY,
                SQLiteContract.ExchangeRateEntry.COLUMN_NAME_CURRENCY_AMOUNT
        };

        Cursor cursor = db.query(
                SQLiteContract.ExchangeRateEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<Rate> rates = new ArrayList<>();
        while (cursor.moveToNext()){
            Currency tmpCurrency = Currency.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(SQLiteContract.ExchangeRateEntry._ID)));
            double tmpAmount = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(SQLiteContract.ExchangeRateEntry._ID)));
            rates.add(new Rate(tmpCurrency, tmpAmount));
        }

        cursor.close();

        return new LatestRates(rates);
    }
}
