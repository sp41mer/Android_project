package com.example.sp41mer.android_project;


import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        client = new OkHttpClient();
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
            //String response = post("URL", makeJSON(baseFile)); //TODO

            Random r = new Random();

            ContentValues values = new ContentValues();
            values.put("oneR", r.nextInt(5));
            values.put("twoR", r.nextInt(5));
            values.put("fiveR", r.nextInt(5));
            values.put("tenR", r.nextInt(5));
            values.put("oneK", r.nextInt(5));
            values.put("fiveK", r.nextInt(5));
            values.put("tenK", r.nextInt(5));
            values.put("fiftyK", r.nextInt(5));
            values.put("picture", photoPath);
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
