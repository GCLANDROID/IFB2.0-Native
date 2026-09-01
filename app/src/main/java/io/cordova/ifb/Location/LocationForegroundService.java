package io.cordova.ifb.Location;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import io.cordova.ifb.R;
import io.cordova.ifb.RoomDB.AppDatabase;

public class LocationForegroundService extends Service {
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private Location lastSentLocation = null;
    private float totalDistance = 0f;

    private NotificationManager notificationManager;

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "location_channel";
    private static final String ACTION_STOP = "STOP_SERVICE";
    AppDatabase appDatabase;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        appDatabase = AppDatabase.getDatabaseInstance(this);
        if (intent != null && ACTION_STOP.equals(intent.getAction())) {
            stopSelf();
            return START_NOT_STICKY;
        }

        startMyForeground(); // MUST BE FIRST

        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        requestLocationUpdates();

        return START_STICKY;
    }

    private void startMyForeground() {

        createNotificationChannel();

        Intent stopIntent = new Intent(this, LocationForegroundService.class);
        stopIntent.setAction(ACTION_STOP);

        PendingIntent stopPendingIntent = PendingIntent.getService(
                this,
                0,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("Sales Message")
                        .setContentText("Dear Team, Kindly ensure that sales are punched on the same day of sale.")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setOnlyAlertOnce(true)
                        .addAction(android.R.drawable.ic_delete,
                                "Stop", stopPendingIntent)
                        .build();

        startForeground(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {

        notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Sales Message",
                            NotificationManager.IMPORTANCE_LOW
                    );
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void requestLocationUpdates() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setSmallestDisplacement(150f); // 150 meters

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;

                Location location = locationResult.getLastLocation();
                if (location != null) {
                    handleNewLocation(location);
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
        );
    }

    private void handleNewLocation(Location location) {

        // Ignore poor accuracy
        if (location.getAccuracy() > 25) {
            return;
        }

        // First location
        if (lastSentLocation == null) {
            lastSentLocation = location;
            sendLocationToServer(location);
            updateNotification();
            return;
        }

        float distance = lastSentLocation.distanceTo(location);

        // Ignore GPS noise
        if (distance < 30) {
            return;
        }



        if (distance >= 150f) {

            totalDistance += distance;
            lastSentLocation = location;

            sendLocationToServer(location);
            updateNotification();
        }
    }

    private void sendLocationToServer(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        Log.e("150M_TRACK", "Sending: " + lat + ", " + lng);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(new Date());
        appDatabase.LocationDao().insert(new LocationModel(0,timestamp,String.valueOf(lng),String.valueOf(lat)));
    }

    private void updateNotification() {

        float distanceInKm = totalDistance / 1000f;

        Notification notification =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("Sales Message")
                        .setContentText("Dear Team, Kindly ensure that sales are punched on the same day of sale.")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setOnlyAlertOnce(true)
                        .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
