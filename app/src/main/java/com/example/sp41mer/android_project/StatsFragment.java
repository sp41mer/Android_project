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
import android.widget.TextView;

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

        statsRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_photos);

        statsLayoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false);
        statsRecyclerView.setLayoutManager(statsLayoutManager);

        for (int i=0; i<5; i++) {
            final CardView card = new CardView(statsRecyclerView.getContext());
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            );
            card.setLayoutParams(params);

            // Set CardView corner radius
            card.setRadius(9);

            // Set cardView content padding
            card.setContentPadding(15, 15, 15, 15);

            // Set a background color for CardView
            card.setCardBackgroundColor(Color.parseColor("#FFC6D6C3"));

            // Set the CardView maximum elevation
            card.setMaxCardElevation(15);

            // Set CardView elevation
            card.setCardElevation(9);

            // Initialize a new TextView to put in CardView
            TextView tv = new TextView(statsRecyclerView.getContext());
            tv.setLayoutParams(params);
            tv.setText("CardView\nProgrammatically");
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            tv.setTextColor(Color.RED);

            // Put the TextView in CardView
            card.addView(tv);

            statsRecyclerView.addView(card);

            statsRecyclerView.setAdapter(new RecyclerView.Adapter() {
                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.album_card, parent, false);

                    return new ViewHolder(itemView);
                }

                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {

                }

                @Override
                public int getItemCount() {
                    return 10;
                }
            });
        }

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

}
