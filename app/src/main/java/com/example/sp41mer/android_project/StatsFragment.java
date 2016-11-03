package com.example.sp41mer.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatsFragment extends Fragment {

    private static final String SCROLL_STATE = "scroll";
    private static final String EXTRA_ID = "id";

    RecyclerView statsRecyclerView;
    FloatingActionButton fab;

    public StatsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        final DataSource dataSource = DataSource.getInstance();
        statsRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_photos);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        statsRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_card, parent, false);
                return new ItemViewHolder(v);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), DetailPhotoActivity.class);

                        intent.putExtra(EXTRA_ID, itemViewHolder.id);
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

        statsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    fab.hide();
                } else if (dy < 0) {
                    fab.show();
                }
            }
        });

        statsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        return rootView;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        long id;

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
            id = item.getId();

            text1.setText(String.valueOf(item.getSum()));
            text2.setText(item.getDate());
            picture.setImageBitmap(item.getPicture());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCROLL_STATE, statsRecyclerView.getScrollY());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            statsRecyclerView.setScrollY(savedInstanceState.getInt(SCROLL_STATE, 0));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fab.show();
    }
}