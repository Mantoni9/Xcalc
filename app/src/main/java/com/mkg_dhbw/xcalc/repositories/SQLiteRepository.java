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
import com.mkg_dhbw.xcalc.models.RequestHistory;

import java.time.LocalDateTime;
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
                    HistoryRequestEntry.COLUMN_NAME_TIMESTAMP + " TEXT" + ");";
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
    public void insertEntry(RequestHistory requestHistory) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HistoryRequestEntry.COLUMN_NAME_FOREIGN_CURRENCY, requestHistory.getForeignCurrency().toString());
        values.put(HistoryRequestEntry.COLUMN_NAME_BASE_CURRENCY, requestHistory.getBaseCurrency().toString());
        values.put(HistoryRequestEntry.COLUMN_NAME_EXCHANGE_RATE, requestHistory.getExchangeRate());
        values.put(HistoryRequestEntry.COLUMN_NAME_BASE_AMOUNT, requestHistory.getBaseAmount());
        values.put(HistoryRequestEntry.COLUMN_NAME_FOREIGN_AMOUNT, requestHistory.getForeignAmount());
        values.put(HistoryRequestEntry.COLUMN_NAME_TIMESTAMP, LocalDateTime.now().toString());

        db.insert(HistoryRequestEntry.TABLE_NAME, null, values);
    }

    // delete Entry
    public void deleteEntry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int rows = db.delete(HistoryRequestEntry.TABLE_NAME, HistoryRequestEntry._ID + " = " + id, null);
        Log.i("SQLITE", "" + rows + "; ");
    }

    // read entries
    public List<RequestHistory> readEntries() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                HistoryRequestEntry.COLUMN_NAME_FOREIGN_CURRENCY,
                HistoryRequestEntry.COLUMN_NAME_BASE_CURRENCY,
                HistoryRequestEntry.COLUMN_NAME_EXCHANGE_RATE,
                HistoryRequestEntry.COLUMN_NAME_BASE_AMOUNT,
                HistoryRequestEntry.COLUMN_NAME_FOREIGN_AMOUNT,
                HistoryRequestEntry.COLUMN_NAME_TIMESTAMP,
        };

        String sortOrder = HistoryRequestEntry.COLUMN_NAME_TIMESTAMP + " DESC";
        Cursor cursor = db.query(
                HistoryRequestEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<RequestHistory> history = new ArrayList<>();

        while (cursor.moveToNext()) {
            int dbId = cursor.getInt(cursor.getColumnIndexOrThrow(HistoryRequestEntry._ID));
            Currency baseCurrency = Currency.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(HistoryRequestEntry.COLUMN_NAME_BASE_CURRENCY)));
            Currency foreignCurrency = Currency.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(HistoryRequestEntry.COLUMN_NAME_FOREIGN_CURRENCY)));
            double exchangeRate = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(HistoryRequestEntry.COLUMN_NAME_EXCHANGE_RATE)));
            double baseAmount = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(HistoryRequestEntry.COLUMN_NAME_BASE_AMOUNT)));
            double foreignAmount = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(HistoryRequestEntry.COLUMN_NAME_FOREIGN_AMOUNT)));
            LocalDateTime timestamp = LocalDateTime.parse(cursor.getString(cursor.getColumnIndexOrThrow(HistoryRequestEntry.COLUMN_NAME_TIMESTAMP)));
            history.add(new RequestHistory(dbId, timestamp, foreignCurrency, baseCurrency, exchangeRate, foreignAmount, baseAmount));
        }

        cursor.close();

        return history;
    }
}
