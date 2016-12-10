package com.technopark.dreamteam.moneybox;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by sp41mer on 10.12.16.
 */

public class SettingsFragment extends Fragment {
    public SettingsFragment() {}

    double sum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        final Button button = (Button) rootView.findViewById(R.id.goal_button);
        final TextView money_goal = (TextView) rootView.findViewById(R.id.goal_text);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                EditText edit_text = (EditText) findViewById(R.id.goal_input);
                String goal = edit_text.getText().toString();
                int num_goal = Integer.parseInt(goal);

                money_goal.setText(num_goal);
            }
        });
        return rootView;
    }
}
