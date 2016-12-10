package com.technopark.dreamteam.moneybox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "Database";

    static DBHelper getInstance(Context context) {
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
        db.execSQL("CREATE TABLE Goal (id INTEGER PRIMARY KEY AUTOINCREMENT,goal LONG);");
        db.execSQL("INSERT INTO Goal (goal) VALUES (100);");
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

    static long readGoal(Context context) {
        SQLiteDatabase database = getInstance(context).getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT goal FROM Goal WHERE id=1",null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return -1;
        }
        cursor.moveToNext();
        long goal = cursor.getLong(0);
        cursor.close();

        return goal;
    }

    static void deleteOne(Context context, long id){
        SQLiteDatabase database = getInstance(context).getReadableDatabase();
        int delCount = database.delete("Data", "id = " + id, null);
    }

    static void AddGoal(Context context, long new_goal){
        SQLiteDatabase database = getInstance(context).getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("goal", new_goal);
        String where = "id=1";
        database.update("goal", cv, where, null);
    }

    static double calculateSum(Context context){
        SQLiteDatabase database = getInstance(context).getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT SUM((oneR+twoR+fiveR+tenR+(oneK+fiveK+tenK+fiftyK)/100.)) FROM Data", null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return -1;
        }
        cursor.moveToNext();
        double sum = cursor.getDouble(0);

        cursor.close();

        return sum;
    }
}
