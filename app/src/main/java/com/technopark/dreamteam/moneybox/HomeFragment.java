package com.technopark.dreamteam.moneybox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView moneybox = (ImageView) rootView.findViewById(R.id.picture);
        Picasso.with(getContext()).load(R.drawable.ic_money_bag).fit().into(moneybox);

        return rootView;
    }

}
