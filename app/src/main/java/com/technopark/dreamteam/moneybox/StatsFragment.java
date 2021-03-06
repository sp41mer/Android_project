package com.technopark.dreamteam.moneybox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class StatsFragment extends Fragment {

    private static final String SCROLL_STATE = "scroll";
    private static final String EXTRA_ID = "id";

    RecyclerView statsRecyclerView;
    FloatingActionButton fab;
    Picasso picasso;

    public StatsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        picasso = Picasso.with(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        final DataSource dataSource = DataSource.getInstance();
        statsRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_photos);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        //DataSource.getInstance().setRecyclerView(statsRecyclerView);
        DataSource.getInstance().registerCallback(new DataSourceCallback() {
            @Override
            public void callBackToRecyclerView(int pos) {
                if (statsRecyclerView != null) {
                    statsRecyclerView.getAdapter().notifyItemInserted(pos);
                    statsRecyclerView.scrollToPosition(pos);
                }
            }
        });

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

                final Item item = dataSource.getItem(position);
                itemViewHolder.bind(item);

                final int id = (int)item.getId();
                itemViewHolder.delete_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DBHelper.deleteOne(getContext(), id);
                        DataSource.getInstance().removeItem(id - 1);
                        statsRecyclerView.getAdapter().notifyItemRemoved(id - 1);
                    }
                });
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

        final LinearLayout linearLayout;
        private final TextView text1;
        private final TextView text2;
        private final ImageView picture;
        final ImageView delete_button;

        ItemViewHolder(final View itemView) {
            super(itemView);

            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.card_layout);
            this.text1 = (TextView) itemView.findViewById(R.id.card_text_1);
            this.text2 = (TextView) itemView.findViewById(R.id.card_text_2);
            this.picture = (ImageView) itemView.findViewById(R.id.card_image);
            this.delete_button = (ImageView) itemView.findViewById(R.id.delete_button_id);
        }

        void bind(Item item) {
            id = item.getId();

            text1.setText(String.format("%.2f руб.", item.getSum()));
            text2.setText(item.getDate());
            picasso.load(item.getPicture()).fit().centerInside().into(picture);
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
        //DataSource.getInstance().setRecyclerView(null);
        DataSource.getInstance().registerCallback(null);
    }
}