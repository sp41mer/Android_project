package com.technopark.dreamteam.moneybox;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    public HomeFragment() {}
    View rootView;
    double sum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.updateMoney();
    }

    public void updateMoney() {
        Context context = getActivity().getApplicationContext();

        sum = DBHelper.calculateSum(context);
        int moneys = (int) sum;

        TextView moneymessage = (TextView) rootView.findViewById(R.id.money_summary_text);
        String newText;
        if (moneys != 0) {
            newText = String.format(getResources().getString(R.string.money_text), moneys);
        } else {
            newText = getResources().getString(R.string.no_money);
        }

        moneymessage.setText(newText);
    }
}
