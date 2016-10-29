package com.example.sp41mer.android_project;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {


    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);
        Button button= (Button) rootView.findViewById(R.id.more_info);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OnePhotoFragment fragment = new OnePhotoFragment();
                //Не уверен что так можно, но индус сказал делать так
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }

        });

        // Inflate the layout for this fragment
        return rootView;


    }

}
