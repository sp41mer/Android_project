package com.technopark.dreamteam.moneybox;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by sp41mer on 26.11.16.
 */

public class NotificationBar extends Activity {
    /** Called when the activity is first created. */

//    private static final int NOTIFY_ID = 1; // Уникальный индификатор вашего уведомления в пределах класса
//    @Override
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); // Создаем экземпляр менеджера уведомлений
//        int icon = android.R.drawable.sym_action_email; // Иконка для уведомления, я решил воспользоваться стандартной иконкой для Email
//        CharSequence tickerText = "Hello Habrahabr"; // Подробнее под кодом
//        long when = System.currentTimeMillis(); // Выясним системное время
//        Notification notification = new Notification(icon, tickerText, when); // Создаем экземпляр уведомления, и передаем ему наши параметры
//        Context context = getApplicationContext();
//        CharSequence contentTitle = "Habrahabr"; // Текст заголовка уведомления при развернутой строке статуса
//        CharSequence contentText = "Пример простого уведомления"; //Текст под заголовком уведомления при развернутой строке статуса
//        Intent notificationIntent = new Intent(this, NotificationBar.class); // Создаем экземпляр Intent
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);class); // Подробное описание в UPD к статье
//        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent); // Передаем в наше уведомление параметры вида при развернутой строке состояния
//        mNotificationManager.notify(NOTIFY_ID, notification); // И наконец показываем наше уведомление через менеджер передав его ID
//    }
}

