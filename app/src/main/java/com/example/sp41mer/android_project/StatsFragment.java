package com.example.sp41mer.android_project;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import static android.support.v7.widget.LinearLayoutManager.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {

    private RecyclerView statsRecyclerView;
    private RecyclerView.LayoutManager statsLayoutManager;

    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        final DataSource dataSource = new DataSource();
        statsRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_photos);

        for (int i=0; i<5; i++) {

            dataSource.addItem(new Item("a"+i, "a"+i, "a"+i));
//            final CardView card = new CardView(statsRecyclerView.getContext());
//            final RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
//                    RecyclerView.LayoutParams.WRAP_CONTENT,
//                    RecyclerView.LayoutParams.WRAP_CONTENT
//            );
//            card.setLayoutParams(params);
//
//            // Set CardView corner radius
//            card.setRadius(9);
//
//            // Set cardView content padding
//            card.setContentPadding(15, 15, 15, 15);
//
//            // Set a background color for CardView
//            card.setCardBackgroundColor(Color.parseColor("#FFC6D6C3"));
//
//            // Set the CardView maximum elevation
//            card.setMaxCardElevation(15);
//
//            // Set CardView elevation
//            card.setCardElevation(9);
//
//            // Initialize a new TextView to put in CardView
//            TextView tv = new TextView(statsRecyclerView.getContext());
//            tv.setLayoutParams(params);
//            tv.setText("CardView\nProgrammatically");
//            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
//            tv.setTextColor(Color.RED);
//
//            // Put the TextView in CardView
//            card.addView(tv);
//
//            statsRecyclerView.addView(card);

            statsRecyclerView.setAdapter(new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false);
                    return new ItemViewHolder(v);
                }

                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                    Item item = dataSource.getItem(position);
                    ((ItemViewHolder) holder).bind(item);
                }

                @Override
                public int getItemCount() {
                    return dataSource.getCount();
                }
            });
        }

        statsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

//        Button button= (Button) rootView.findViewById(R.id.more_info);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                OnePhotoFragment fragment = new OnePhotoFragment();
//                Не уверен что так можно, но индус сказал делать так
//                android.support.v4.app.FragmentTransaction fragmentTransaction =
//                        getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.commit();
//            }
//
//        });

        // Inflate the layout for this fragment
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

        public String getText1() {
            return text1;
        }

        public String getText2() {
            return text2;
        }
    }

    private class DataSource {

        private final List<Item> items = new ArrayList<>();

        public int getCount() {
            return items.size();
        }

        public Item getItem(int position) {
            return items.get(position);
        }

        public void addItem(Item item) {
            items.add(item);
            statsRecyclerView.getAdapter().notifyItemInserted(items.size() - 1);
        }

        public void removeFirst() {
            if (!items.isEmpty()) {
                items.remove(0);
                statsRecyclerView.getAdapter().notifyItemRemoved(0);
            }
        }

    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView text1;
        private final TextView text2;
        private final ImageView picture;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.text1 = (TextView) itemView.findViewById(R.id.card_text_1);
            this.text2 = (TextView) itemView.findViewById(R.id.card_text_2);
            this.picture = (ImageView) itemView.findViewById(R.id.card_image);
        }

        public void bind(Item item) {
            text1.setText(item.getText1());
            text2.setText(item.getText2());
        }

    }

}
