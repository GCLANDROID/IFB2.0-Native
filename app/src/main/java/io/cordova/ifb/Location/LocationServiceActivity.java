package io.cordova.ifb.Location;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

import java.util.List;

import io.cordova.ifb.RoomDB.AppDatabase;
import io.cordova.ifb.databinding.ActivityLoactionServiceBinding;

public class LocationServiceActivity extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    ActivityLoactionServiceBinding binding;
    LocationAdapter locationAdapter;
    AppDatabase appDatabase;
    List<LocationModel> itemLocation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoactionServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        btnClick();
    }

    private void initView() {
        binding.rvRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        appDatabase = AppDatabase.getDatabaseInstance(this);
        itemLocation = appDatabase.LocationDao().getAllLocation();
        locationAdapter = new LocationAdapter(this,itemLocation);
        binding.rvRecyclerView.setAdapter(locationAdapter);
    }

    private void btnClick() {
        binding.btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationServiceActivity.this, LocationForegroundService.class);
                ContextCompat.startForegroundService(LocationServiceActivity.this, intent);
            }
        });
        binding.btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationServiceActivity.this, LocationForegroundService.class);
                stopService(intent);
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public interface OnItemListener{
        void onClick(double lat,double lng);
    }
}
