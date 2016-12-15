package com.technopark.dreamteam.moneybox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

    ListView rublesListView, groshiListView;

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

        rublesListView = (ListView) findViewById(R.id.rubles_list);
        groshiListView = (ListView) findViewById(R.id.groshi_list);

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
                }, 1500
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

        String[] rublesArray = {String.valueOf(item.getOneR()), String.valueOf(item.getTwoR()), String.valueOf(item.getFiveR()), String.valueOf(item.getTenR())};
        String[] groshiArray = {String.valueOf(item.getOneK()), String.valueOf(item.getFiveK()), String.valueOf(item.getTenK()), String.valueOf(item.getFiftyK())};

        for (int i = 0; i < rublesArray.length; i++ ) {
            switch (i) {
                case (0):
                    rublesArray[i] = rublesArray[i].concat(" " + " (1 рубль) ");
                    break;
                case (1):
                    rublesArray[i] = rublesArray[i].concat(" "+ " (2 рубля) ");
                    break;
                case (2):
                    rublesArray[i] = rublesArray[i].concat(" "+ " (5 рублей) ");
                    break;
                case (3):
                    rublesArray[i] = rublesArray[i].concat(" " + " (10 рублей) ");
                    break;
            }
        }

        for (int i = 0; i < groshiArray.length; i++ ) {
            switch (i) {
                case (0):
                    groshiArray[i] = groshiArray[i].concat(" " + " (1 копейка) " );
                    break;
                case (1):
                    groshiArray[i] = groshiArray[i].concat(" " + " (5 копеек) " );
                    break;
                case (2):
                    groshiArray[i] = groshiArray[i].concat(" " + " (10 копеек) ");
                    break;
                case (3):
                    groshiArray[i] = groshiArray[i].concat(" " + " (50 копеек) ");
                    break;
            }
        }


        ArrayAdapter<String> rublesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rublesArray);
        ArrayAdapter<String> groshiAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groshiArray);

        rublesListView.setClickable(false);
        groshiListView.setClickable(false);

        rublesListView.setAdapter(rublesAdapter);
        groshiListView.setAdapter(groshiAdapter);

        count.setText(getString(R.string.count_coins)+ " " + String.valueOf(item.getCount()) + "  " + getString(R.string.coins));
        sum.setText(item.getSum());

        Picasso.with(this).load(item.getPicture()).fit().centerInside().into(photo);
    }
}
