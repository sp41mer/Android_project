package com.example.sp41mer.android_project;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class DetailPhotoActivity extends AppCompatActivity {

    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Для того, чтоб запихнуть кнопку назад. На стаке написано только
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        //Получаем параметры карточки text1 и text2
        String text1 = getIntent().getExtras().getCharSequence("text1").toString();
        String text2 = getIntent().getExtras().getCharSequence("text2").toString();

        //Срем в логи
        Log.d("First text: ", text1);
        Log.d("Second text: ", text2);
        setContentView(R.layout.fragment_one_photo);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Кек", "Setting screen name: Детальная фотка");
        mTracker.setScreenName("Image~Детальная фотка");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public boolean onOptionsItemSelected(MenuItem item){
//        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivityForResult(myIntent, 0);
        finish();
        return true;

    }
}
