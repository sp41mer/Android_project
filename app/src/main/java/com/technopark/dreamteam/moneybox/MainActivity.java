package com.technopark.dreamteam.moneybox;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//TODO: Верстка едет при повороте
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_CAMERA = 2;
    private static final String PHOTO_PARAM = "photo";
    private static final String MENU_POSITION_PARAM = "menu_position";
    private static final String ACTION_SERVER_RESPONSE = "server_response";
    private static final String DIALOG_TAG = "waiting_dialog";
    private static final String EXTRA_ROW_ID = "newRowId";

    NavigationView navigationView;
    Toolbar toolbar;
    Tracker mTracker;
    String mCurrentPhotoPath;

    BroadcastReceiver broadcastReceiver;

    ProgressDialogFragment dialogFragment;
    HomeFragment homeFragment;
    StatsFragment statsFragment;

    private int menuPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        statsFragment = new StatsFragment();

        homeFragment.setRetainInstance(true);
        statsFragment.setRetainInstance(true);

        MoneyboxApplication application = (MoneyboxApplication) getApplication();
        mTracker = application.getDefaultTracker();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Кек", "Setting screen name: Копилка");
        mTracker.setScreenName("Image~Копилка");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        onNavigationItemSelected(navigationView.getMenu().getItem(menuPosition));

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ACTION_SERVER_RESPONSE)) {
                    long id = intent.getLongExtra(EXTRA_ROW_ID, -1);
                    DBHelper.readOne(MainActivity.this, id);

                    Dialog dialog = dialogFragment.getDialog();
                    if (dialog != null) {
                        dialog.cancel();
                    }
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(ACTION_SERVER_RESPONSE);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_home:
                Log.d("Нажатие в навигаторе", "Нажал на копилку");
                fragment = homeFragment;
                menuPosition = 0;
                break;
            case R.id.nav_stat:
                Log.d("Нажатие в навигаторе", "Нажал на статистику");
                fragment = statsFragment;
                menuPosition = 1;
                break;
            case R.id.nav_manage:
                fragment = homeFragment; //TODO
                menuPosition = 2;
                Log.d("Нажатие в навигаторе", "Нажал на настройки");

                break;
        }

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();

        ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
        return true;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Вы запретили приложению фотографировать", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                return;
            }
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.technopark.dreamteam.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PHOTO_PARAM, mCurrentPhotoPath);
        outState.putInt(MENU_POSITION_PARAM, menuPosition);
        try {
            getSupportFragmentManager().putFragment(outState, "stats", statsFragment);
        } catch (IllegalStateException | NullPointerException ignored) {}
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentPhotoPath = savedInstanceState.getString(PHOTO_PARAM);
        menuPosition = savedInstanceState.getInt(MENU_POSITION_PARAM);
        Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, "stats");
        if (fragment != null)
            statsFragment = (StatsFragment) fragment;

        dialogFragment = (ProgressDialogFragment) getFragmentManager().findFragmentByTag(DIALOG_TAG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            dialogFragment = new ProgressDialogFragment();
            dialogFragment.show(getFragmentManager(), DIALOG_TAG);

            Intent intent = new Intent(this, PhotoService.class);
            intent.putExtra(PHOTO_PARAM, mCurrentPhotoPath);
            startService(intent);
        }
    }

}
