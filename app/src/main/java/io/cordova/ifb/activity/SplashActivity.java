package io.cordova.ifb.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.CreativePermission;
import io.cordova.ifb.utility.NetworkConnectionCheck;
import io.cordova.ifb.utility.PrefManager;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int PERMISSION_ALL = 100;
    private CreativePermission myPermission;
    NetworkConnectionCheck connectionCheck;
    GoogleApiClient googleApiClient;
    PrefManager prefManager;
    TextView tvVersion;
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        CheckPermission();
    }

    private void showSplash() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefManager.getRemberFlag().equals("1")) {
                    if (prefManager.getSecurityCode().equalsIgnoreCase("IND")){
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }else {
                        if (prefManager.getUserTypeId().equals("IFBUT1000127")){

                            Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                            startActivity(intent);
                            finish();
                        }else if (prefManager.getUserTypeId().equals("IFBMM1000011") || prefManager.getUserTypeId().equals("IFBUT1000135") || prefManager.getUserTypeId().equals("IFBUT1000134") || prefManager.getUserTypeId().equals("IFBUT1000133")|| prefManager.getUserTypeId().equals("FBMM1000004")|| prefManager.getUserTypeId().equals("IFBUT1000136")){

                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();

                }
            }
        }, 2000);

    }


    private void CheckPermission() {
        if (!myPermission.hasPermissions()) {
            myPermission.reqPermisions();
        } else {
            setup();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_ALL) {
            setup();

        }else {

        }
    }

    private void initialize() {
        prefManager=new PrefManager(SplashActivity.this);
        mAppUpdateManager= AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE,SplashActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        myPermission = new CreativePermission(this,PERMISSION_ALL);
        connectionCheck=new NetworkConnectionCheck(SplashActivity.this);
        tvVersion=(TextView)findViewById(R.id.tvVersion);
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            tvVersion.setText("App Version : "+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                showSplash();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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

    private void setup(){
        if (connectionCheck.isGPSEnabled()) {
            if (connectionCheck.isNetworkAvailable()) {
                showSplash();
            }else {
                connectionCheck.getNetworkActiveAlert().show();
            }

        }else {
            turnGPSOn();
        }
    }

    private void turnGPSOn() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(SplashActivity.this).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            showSplash();
                            break;


                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                try {
                                    status.startResolutionForResult(SplashActivity.this, 1000);
                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore the error.
                                }
                            } catch (Exception e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Toast.makeText(getApplicationContext(),"NOT",Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void onStop() {
        if (mAppUpdateManager!=null){
            // mAppUpdateManager.registerListener(installStateUpdatedListener);
        }
        super.onStop();
    }


    private InstallStateUpdatedListener installStateUpdatedListener=new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull InstallState installState) {
            if (installState.installStatus()== InstallStatus.DOWNLOADED){
                showCompleteUpdate();
            }

        }

        private void showCompleteUpdate() {
            Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"New App is Ready!",Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Install", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAppUpdateManager.completeUpdate();
                }
            });
            snackbar.show();
        }
    };

    @Override
    protected void onResume() {
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,SplashActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        super.onResume();
    }
}
