package com.example.sp41mer.android_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class DetailPhotoActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "id";

    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Для того, чтоб запихнуть кнопку назад. На стаке написано только
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        long id = getIntent().getLongExtra(EXTRA_ID, -1);

        Log.d("ID: ", String.valueOf(id));
        setContentView(R.layout.fragment_one_photo);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Кек", "Setting screen name: Детальная фотка");
        mTracker.setScreenName("Image~Детальная фотка");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
