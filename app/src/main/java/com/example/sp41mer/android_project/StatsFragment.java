package com.example.sp41mer.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatsFragment extends Fragment {

    private RecyclerView statsRecyclerView;

    public StatsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        final DataSource dataSource = new DataSource();
        statsRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_photos);
        statsRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false);
                return new ItemViewHolder(v);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), DetailPhotoActivity.class);

                        intent.putExtra("text1", itemViewHolder.text1.getText());
                        intent.putExtra("text2", itemViewHolder.text2.getText());
                        startActivity(intent);
                    }
                });

                Item item = dataSource.getItem(position);
                itemViewHolder.bind(item);
            }

            @Override
            public int getItemCount() {
                return dataSource.getCount();
            }
        });

        for (int i=0; i<5; i++) {
            dataSource.addItem(new Item("a"+i, DateFormat.getDateTimeInstance().format(new Date()), "a"+i));
        }

        statsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        return rootView;
    }

    private static class Item {

        private final String text1;
        private final String text2;
        private final String picture;

        Item(String cost, String date, String picturePath) {
            this.text1 = cost;
            this.text2 = date;
            this.picture = picturePath;
        }

        String getText1() {
            return text1;
        }

        String getText2() {
            return text2;
        }
    }

    private class DataSource {

        private final List<Item> items = new ArrayList<>();

        int getCount() {
            return items.size();
        }

        Item getItem(int position) {
            return items.get(position);
        }

        void addItem(Item item) {
            items.add(item);
            statsRecyclerView.getAdapter().notifyItemInserted(items.size() - 1);
        }

    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        private final TextView text1;
        private final TextView text2;
        private final ImageView picture;

        ItemViewHolder(final View itemView) {
            super(itemView);

            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.card_layout);
            this.text1 = (TextView) itemView.findViewById(R.id.card_text_1);
            this.text2 = (TextView) itemView.findViewById(R.id.card_text_2);
            this.picture = (ImageView) itemView.findViewById(R.id.card_image);
        }

        void bind(Item item) {
            text1.setText(item.getText1());
            text2.setText(item.getText2());
        }

    }

}
