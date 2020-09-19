package com.sfbd.serviceforcebd.connection;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sfbd.serviceforcebd.R;

public class FirebaseNotificationReciver extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification()!=null){
        showNotificatiom(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());}
    }

    public void showNotificatiom(String title,String mess){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"MyNotification")
                .setContentText("Hellow")
                .setSmallIcon(R.drawable.logo1)
                .setAutoCancel(true)
                .setContentText(mess);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManagerCompat.notify(999,builder.build());
    }
}
