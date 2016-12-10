package com.technopark.dreamteam.moneybox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

public class DetailPhotoActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "id";

    Tracker mTracker;

    TextView oneR, twoR, fiveR, tenR, oneK, fiveK, tenK, fiftyK;
    TextView count, sum;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MoneyboxApplication application = (MoneyboxApplication) getApplication();
        mTracker = application.getDefaultTracker();

        setContentView(R.layout.activity_details);

        long id = getIntent().getLongExtra(EXTRA_ID, -1);
        Log.d("ID: ", String.valueOf(id));

        oneR = (TextView) findViewById(R.id.textOneR);
        twoR = (TextView) findViewById(R.id.textTwoR);
        fiveR = (TextView) findViewById(R.id.textFiveR);
        tenR = (TextView) findViewById(R.id.textTenR);
        oneK = (TextView) findViewById(R.id.textOneK);
        fiveK = (TextView) findViewById(R.id.textFiveK);
        tenK = (TextView) findViewById(R.id.textTenK);
        fiftyK = (TextView) findViewById(R.id.textFiftyK);
        count = (TextView) findViewById(R.id.textCount);
        sum = (TextView) findViewById(R.id.textSum);
        photo = (ImageView) findViewById(R.id.imageDetails);

        loadData(id);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2156340139005509/3523101475");
        final AdView mAdView = (AdView) findViewById(R.id.adView);
        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mAdView.loadAd(adRequest);
                    }
                }, 500
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Кек", "Setting screen name: Детальная фотка");
        mTracker.setScreenName("Image~Детальная фотка");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private void loadData(long id) {
        Item item = DataSource.getInstance().getItemById(id);

        oneR.setText(String.valueOf(item.getOneR()));
        twoR.setText(String.valueOf(item.getTwoR()));
        fiveR.setText(String.valueOf(item.getFiveR()));
        tenR.setText(String.valueOf(item.getTenR()));
        oneK.setText(String.valueOf(item.getOneK()));
        fiveK.setText(String.valueOf(item.getFiveK()));
        tenK.setText(String.valueOf(item.getTenK()));
        fiftyK.setText(String.valueOf(item.getFiftyK()));
        count.setText(String.valueOf(item.getCount()));
        sum.setText(item.getSum());
        Picasso.with(this).load(item.getPicture()).fit().centerInside().into(photo);
    }
}
