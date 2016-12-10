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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        final Context context = getActivity().getApplicationContext();

        final Button button = (Button) rootView.findViewById(R.id.goal_button);
        long my_goal = DBHelper.readGoal(context);
        final TextView money_goal = (TextView) rootView.findViewById(R.id.goal_text);
        money_goal.setText(Long.toString(my_goal));
        final EditText edit_text = (EditText) rootView.findViewById(R.id.goal_input);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String goal = edit_text.getText().toString();
                long num_goal = Long.parseLong(goal);
                DBHelper.AddGoal(context,num_goal);
                money_goal.setText(Long.toString(num_goal));
            }
        });

        return rootView;
    }
}
