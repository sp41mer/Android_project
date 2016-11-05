package com.technopark.dreamteam.moneybox;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

class DataSource {

    @SuppressLint("StaticFieldLeak")
    private static final DataSource dataSource = new DataSource();

    static DataSource getInstance() {
        return dataSource;
    }

    private DataSource() {}

    private final List<Item> items = new ArrayList<>();
    @SuppressLint("UseSparseArrays")
    private final Map<Long, Item> idToItem = new HashMap<>();

    private RecyclerView recyclerView;

    int getCount() {
        return items.size();
    }

    Item getItem(int position) {
        return items.get(position);
    }

    Item getItemById(long id) {
        return idToItem.get(id);
    }

    private void addItem(Item item) {
        addItem(items.size() - 1, item);
    }

    private void addItem(int pos, Item item) {
        items.add(pos, item);
        idToItem.put(item.getId(), item);

        if (recyclerView != null) { //TODO
            recyclerView.getAdapter().notifyItemInserted(pos);
            recyclerView.scrollToPosition(pos);
        }
    }

    void removeItem(Item item) {
        items.remove(item);
    }

    static void readData(Cursor cursor) {
        while (!cursor.isLast()) {
            cursor.moveToNext();

            final long id = cursor.getLong(0);
            Date date;

            DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                date = iso8601Format.parse(cursor.getString(1));
            } catch (ParseException ignored) {
                return;
            }

            String picture = cursor.getString(2);

            final int oneR = cursor.getInt(3);
            final int twoR = cursor.getInt(4);
            final int fiveR = cursor.getInt(5);
            final int tenR = cursor.getInt(6);
            final int oneK = cursor.getInt(7);
            final int fiveK = cursor.getInt(8);
            final int tenK = cursor.getInt(9);
            final int fiftyK = cursor.getInt(10);

            Item item = new Item(id, date, picture, oneR, twoR, fiveR, tenR, oneK, fiveK, tenK, fiftyK);
            dataSource.addItem(0, item);
        }
    }

    void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
