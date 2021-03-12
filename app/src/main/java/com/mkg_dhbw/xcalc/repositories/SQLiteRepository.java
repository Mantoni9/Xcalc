package com.mkg_dhbw.xcalc.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.mkg_dhbw.xcalc.models.Currency;
import com.mkg_dhbw.xcalc.models.LatestRates;
import com.mkg_dhbw.xcalc.models.Rate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLiteRepository extends SQLiteOpenHelper {

    public static class HistoryRequestEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_FOREIGN_CURRENCY = "foreign_currency";
        public static final String COLUMN_NAME_BASE_CURRENCY = "base_currency";
        public static final String COLUMN_NAME_EXCHANGE_RATE = "exchange_rate";
        public static final String COLUMN_NAME_BASE_AMOUNT = "base_amount";
        public static final String COLUMN_NAME_FOREIGN_AMOUNT = "foreign_amount";
        public static final String COLUMN_NAME_TIMESTAMP = "zeitstempel";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " +
                    HistoryRequestEntry.TABLE_NAME + " (" +
                    HistoryRequestEntry._ID + " INTEGER PRIMARY KEY," +
                    HistoryRequestEntry.COLUMN_NAME_FOREIGN_CURRENCY + " TEXT," +
                    HistoryRequestEntry.COLUMN_NAME_BASE_CURRENCY + " TEXT," +
                    HistoryRequestEntry.COLUMN_NAME_EXCHANGE_RATE + " REAL," +
                    HistoryRequestEntry.COLUMN_NAME_BASE_AMOUNT + " REAL," +
                    HistoryRequestEntry.COLUMN_NAME_FOREIGN_AMOUNT + " REAL," +
                    HistoryRequestEntry.COLUMN_NAME_TIMESTAMP + " TEXT" +");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HistoryRequestEntry.TABLE_NAME;
    public static final String DATABASE_NAME = "HistoryRequestEntries.db";
    public static final int DATABASE_VERSION = 1;

    public SQLiteRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(SQL_CREATE_ENTRIES);
        } catch (SQLException ex) {
            Log.i("SQLITE", SQL_CREATE_ENTRIES);
        }

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // Save Entry to the Datbase
    public void SaveEntry(LatestRates latestRates) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Rate rate : latestRates.getRates()) {
            ContentValues values = new ContentValues();
            values.put(HistoryRequestEntry._ID, 2);
            values.put(HistoryRequestEntry.COLUMN_NAME_BASE_CURRENCY, rate.getCurrency().toString());
            values.put(HistoryRequestEntry.COLUMN_NAME_BASE_AMOUNT, rate.getAmount());
            values.put(HistoryRequestEntry.COLUMN_NAME_TIMESTAMP, LocalDate.now().toString());

            db.insert(HistoryRequestEntry.TABLE_NAME, null, values);
        }
    }

    public void DeleteEntry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = HistoryRequestEntry._ID + " = ?";
        String[] selectionArgs = { ""+id };
        int rows = db.delete(HistoryRequestEntry.TABLE_NAME, selection, selectionArgs);

    }

    // read entries
    public LatestRates ReadEntry() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                HistoryRequestEntry.COLUMN_NAME_BASE_CURRENCY,
                HistoryRequestEntry.COLUMN_NAME_BASE_AMOUNT
        };

        Cursor cursor = db.query(
                HistoryRequestEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<Rate> rates = new ArrayList<>();
        while (cursor.moveToNext()){
            Currency tmpCurrency = Currency.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(HistoryRequestEntry._ID)));
            double tmpAmount = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(HistoryRequestEntry._ID)));
            rates.add(new Rate(tmpCurrency, tmpAmount));
        }

        cursor.close();

        return new LatestRates(rates);
    }
}
