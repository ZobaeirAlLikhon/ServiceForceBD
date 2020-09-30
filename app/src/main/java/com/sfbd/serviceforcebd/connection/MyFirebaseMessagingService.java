package com.sfbd.serviceforcebd.connection;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.MainActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "TOKEN";
    Bitmap bitmap;
//    private static final String TAG = "MyFirebaseMsgService";


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
        String imageUri = remoteMessage.getNotification().getImageUrl().toString();
        bitmap =getBitmapfromUrl(imageUri);
        if(remoteMessage.getData().isEmpty())
        {


            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),bitmap);
        }
        else{
            showNotification(remoteMessage.getData(),bitmap);

        }
    }
    public void showNotification(Map<String,String> data,Bitmap bitmap)
    {
        String title=data.get("title").toString();
        String body=data.get("body").toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String notification_Channel_Id="SFBD";
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel=new NotificationChannel(notification_Channel_Id,"notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Code Des");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, notification_Channel_Id);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setSmallIcon(R.drawable.logo1);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);
        notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap));
        notificationBuilder.setLargeIcon(bitmap);
        notificationBuilder.setContentInfo("Info");
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(999, notificationBuilder.build());
    }

    public void showNotification(String title, String body, Bitmap bitmap)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String notification_Channel_Id="SFBD";
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel=new NotificationChannel(notification_Channel_Id,"notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Code Des");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, notification_Channel_Id);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setSmallIcon(R.drawable.logo1);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);
        notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap));
        notificationBuilder.setLargeIcon(bitmap);
        notificationBuilder.setContentInfo("Info");
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(999, notificationBuilder.build());

    }
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

    }
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {

            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

}



