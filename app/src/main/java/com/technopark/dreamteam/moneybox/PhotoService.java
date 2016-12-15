package com.technopark.dreamteam.moneybox;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PhotoService extends Service {

    private static final String PHOTO_PARAM = "photo";
    private static final String ACTION_SERVER_RESPONSE = "server_response";
    private static final String EXTRA_ROW_ID = "newRowId";

    ExecutorService executorService;
    Future<?> future;
    String photoPath;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client;

    @Override
    public void onCreate() {
        super.onCreate();

        executorService = Executors.newSingleThreadExecutor();
        client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            photoPath = intent.getStringExtra(PHOTO_PARAM);

            future = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    sendPhoto();
                    stopSelf();
                    Log.d("Service", "StopSelf");
                }
            });
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (future != null)
            future.cancel(true);
        executorService.shutdown();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void sendPhoto() {
        RandomAccessFile file;
        try {
            file = new RandomAccessFile(photoPath, "r");
            byte[] fileBytes = new byte[(int) file.length()];
            file.readFully(fileBytes);
            String baseFile = Base64.encodeToString(fileBytes, Base64.DEFAULT);
            String response = post("http://95.30.251.238:5000/sendphoto", makeJSON(baseFile)); //TODO

            ContentValues values = new ContentValues();

            try {
                JSONObject jsonObject = new JSONObject(response);

                values.put("oneR", jsonObject.getInt("oneR"));
                values.put("twoR", jsonObject.getInt("twoR"));
                values.put("fiveR", jsonObject.getInt("fiveR"));
                values.put("tenR", jsonObject.getInt("tenR"));
                values.put("fiveK", jsonObject.getInt("fiveK"));
                values.put("tenK", jsonObject.getInt("tenK"));
                values.put("fiftyK", jsonObject.getInt("fiftyK"));
                values.put("picture", photoPath);
            } catch (JSONException ignored) {}

            long id = DBHelper.getInstance(this).getWritableDatabase().insert("Data", null, values);

            Intent intent = new Intent(ACTION_SERVER_RESPONSE);
            intent.putExtra(EXTRA_ROW_ID, id);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } catch (IOException ignored) {}

    }

    String makeJSON(String basePhoto) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("photo", basePhoto);
        } catch (JSONException ignored) {}
        return jsonObject.toString();
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }
}
