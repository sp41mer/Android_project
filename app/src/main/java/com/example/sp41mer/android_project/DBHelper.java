package com.example.sp41mer.android_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "Database";

    public static DBHelper getInstance(Context context) {
        return new DBHelper(context);
    }

    private DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Creating database");

        db.execSQL("CREATE TABLE Data " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "picture TEXT," +
                "oneR INTEGER," +
                "twoR INTEGER," +
                "fiveR INTEGER," +
                "tenR INTEGER," +
                "oneK INTEGER," +
                "fiveK INTEGER," +
                "tenK INTEGER," +
                "fiftyK INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    static void readAll(Context context) {
        SQLiteDatabase database = getInstance(context).getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM Data", null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return;
        }

        DataSource.readData(cursor);
        cursor.close();
    }

    static void readOne(Context context, long id) {
        SQLiteDatabase database = getInstance(context).getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM Data WHERE id=?", new String[]{ String.valueOf(id) });
        if (cursor.getCount() == 0) {
            cursor.close();
            return;
        }

        DataSource.readData(cursor);
        cursor.close();
    }
}
