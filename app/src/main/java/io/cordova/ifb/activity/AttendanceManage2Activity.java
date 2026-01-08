package io.cordova.ifb.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;

import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wajahatkarim3.longimagecamera.LongImageBackCameraActivity;
import com.wajahatkarim3.longimagecamera.LongImageCameraActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import io.cordova.ifb.R;
import io.cordova.ifb.module.ModelSpinnerModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.CameraActivity;
import io.cordova.ifb.utility.NetworkConnectionCheck;
import io.cordova.ifb.utility.PrefManager;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AttendanceManage2Activity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static final String TAG = AttendanceManage2Activity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    //  private MapView mapView;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    LatLng latLng;
    TextView tvAddress;
    NetworkConnectionCheck connectionCheck;
    String address = "";
    double currentLatitude, currentLongitude;
    String address1 = "0";
    String empId;
    String lat = "0";
    String longt = "0";
    LinearLayout llReport, llPunch;
    PrefManager prefManager;
    LinearLayout llMain, llLoader;
    String month, year;
    String ReportType = "";
    String Status = "";
    LinearLayout llStatus;
    TextView tvStatus;
    ImageView imgBack;
    AlertDialog alerDialog1, alertDialog2, noSalesDialog;
    String responseText = "";
    String showText;
    ImageView imgHome;
    String financialYear;
    GoogleApiClient googleApiClient;
    AlertDialog alertDialog;
    private final int[] MAP_TYPES = {MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    Location location;
    private static final int LOC_PERM_REQ_CODE = 1;
    ArrayList<String> workingStatusList = new ArrayList<>();
    ArrayList<String> numberList = new ArrayList<>();
    String workingStaus = "Own Mapped Counter";
    String number;
    String deviceName;
    String android_id;
    String refreshedToken;
    LatLng counterLatLng, currentLatLng;
    boolean flagt = false;
    double counterLat, counterLong;
    int attFlag, radius;
    double dis;
    LinearLayout lnCamera;
    ImageView imgCamera, imgImage;
    private String encodedImage;
    private Uri imageUri;
    private static final int CAMERA_REQUEST = 1;
    File file, compressedImageFile, file1;
    File dFile;
    private static final int REQUEST_GALLERY_CODE = 200;
    String stringFile = "";
    Uri uri;
    String imageFileName;
    ;
    File pictureFile;
    AlertDialog alert1, cameraAlert;
    String androidID;
    private static final int SELFIE_CAMERA_REQUEST = 3;
    ArrayList<ModelSpinnerModel> CounterList = new ArrayList<>();
    ArrayList<KeyPairBoolData> keyCounterList = new ArrayList<>();
    SingleSpinnerSearch spOtherCounter;
    String counterid = "";
    String workStatusFlag = "0";
    TextView tvCheckIN, tvCheckOut;
    LinearLayout llChekcinout, llPunchOut;
    int minCheckInTime;
    int minCheckOutTime;
    int currentTime;
    String soldValue = "";
    String yesNo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_manage);
        locationalerts();
        initialize();

        attendenceCheck();
        setUpMapIfNeeded();
        onClick();
    }

    @SuppressLint("RestrictedApi")
    private void initialize() {
        prefManager = new PrefManager(AttendanceManage2Activity.this);

        minCheckInTime = prefManager.getCheckInHr() * 60 + prefManager.getCheckInMin();
        minCheckOutTime = prefManager.getCheckOutHr() * 60 + prefManager.getCheckOutMin();

        Calendar now = Calendar.getInstance();
        currentTime = now.get(Calendar.HOUR_OF_DAY) * 60
                + now.get(Calendar.MINUTE);


        counterid = prefManager.getSalesPointID();
        connectionCheck = new NetworkConnectionCheck(AttendanceManage2Activity.this);
        mLocationRequest = new LocationRequest();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000);
        tvAddress = (TextView) findViewById(R.id.tvLocation);
        llReport = (LinearLayout) findViewById(R.id.llReport);
        llPunch = (LinearLayout) findViewById(R.id.llPunch);
        llStatus = (LinearLayout) findViewById(R.id.llStatus);
        lnCamera = (LinearLayout) findViewById(R.id.lnCamera);
        llChekcinout = findViewById(R.id.llChekcinout);
        llPunchOut = findViewById(R.id.llPunchOut);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        imgCamera = (ImageView) findViewById(R.id.imgCamera);
        imgImage = (ImageView) findViewById(R.id.imgImage);


        llMain = (LinearLayout) findViewById(R.id.llMain);
        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        int y = Calendar.getInstance().get(Calendar.YEAR);
        year = String.valueOf(y);
        Log.d("year", year);


        int m = Calendar.getInstance().get(Calendar.MONTH) + 1;
        Log.d("month", String.valueOf(m));
        if (m == 1) {
            month = "January";
        } else if (m == 2) {
            month = "February";
        } else if (m == 3) {
            month = "March";
        } else if (m == 4) {
            month = "April";
        } else if (m == 5) {
            month = "May";
        } else if (m == 6) {
            month = "June";
        } else if (m == 7) {
            month = "July";
        } else if (m == 8) {
            month = "August";
        } else if (m == 9) {
            month = "September";
        } else if (m == 10) {
            month = "October";
        } else if (m == 11) {
            month = "November";
        } else if (m == 12) {
            month = "December";
        }
        if (month.equals("January")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else if (month.equals("February")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else if (month.equals("March")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else {
            int futureyear = y + 1;
            financialYear = year + "-" + futureyear;
        }

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        if (connectionCheck.isGPSEnabled()) {

        } else {
            turnGPSOn();
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
            }
        }, 6000);

        //workingStatusList.add("Own Mapped Counter");
        //workingStatusList.add("Other Counter");
        workingStatusList.add("Please Select Working Status");
        workingStatusList.add("IFB Meet – Training");
        workingStatusList.add("Branch Office – Training");
        workingStatusList.add("IFB Exhibitions");


        numberList.add("15");
        numberList.add("35");
        deviceName = android.os.Build.MODEL.replaceAll("\\s+", "");
        Log.d("deviceName", deviceName);

        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (android_id.equals("")) {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            androidID = telephonyManager.getDeviceId();
        } else {
            androidID = android_id;
        }
        refreshedToken = androidID;
        counterLat = Double.parseDouble(getIntent().getStringExtra("counterlat"));
        counterLong = Double.parseDouble(getIntent().getStringExtra("counterlong"));
        radius = getIntent().getIntExtra("radius", 0);
        attFlag = getIntent().getIntExtra("attFlag", 0);
        tvCheckIN = findViewById(R.id.tvCheckIN);
        tvCheckOut = findViewById(R.id.tvCheckOut);
        updateManageLayoutStatusForCheckOut();


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(AttendanceManage2Activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } else {
                handleNewLocation(location);
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (ContextCompat.checkSelfPermission(AttendanceManage2Activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } else {
                handleNewLocation(location);
            }
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(AttendanceManage2Activity.this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            // other 'case' lines to check for other
            // permissions this app might request
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(AttendanceManage2Activity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(AttendanceManage2Activity.this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        //mMap.setMinZoomPreference(25);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setMinZoomPreference(15);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(AttendanceManage2Activity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
       /* mMap = googleMap;

        mMap = googleMap;
        mMap.setMapType( MAP_TYPES[MAP_TYPE_SATELLITE] );

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(15);

        showCurrentLocationOnMap();*/


    }

    @Override
    public void onPause() {
        super.onPause();

        // mapView.onPause();
       /* if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }*/
    }

    private void setUpMapIfNeeded() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //  mapView.getMapAsync(this);

    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(AttendanceManage2Activity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(AttendanceManage2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AttendanceManage2Activity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(AttendanceManage2Activity.this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AttendanceManage2Activity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(AttendanceManage2Activity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }


    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        currentLatitude = location.getLatitude();
        lat = String.valueOf(currentLatitude);
        currentLongitude = location.getLongitude();
        longt = String.valueOf(currentLongitude);

        latLng = new LatLng(currentLatitude, currentLongitude);
        address = getCompleteAddressString(currentLatitude, currentLongitude);
        Log.d("attenaddrsees", address);
        address1 = address.replaceAll("\\s+", "").replaceAll("\\s+", "").replaceAll("#", "-");

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(address)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker));
        tvAddress.setText(address);
        counterLatLng = new LatLng(counterLat, counterLong);
        currentLatLng = new LatLng(currentLatitude, currentLongitude);
        Double distance = CalculationByDistance(counterLatLng, latLng) * 1000;
        if (counterLat != 0.00) {
            dis = distance;
        } else {
            dis = 0;
        }

        if (distance < radius || distance == radius) {
            flagt = true;
            Log.d("desus", "1");
        } else {
            flagt = false;
            Log.d("desus", "0");
        }


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to location user
                .zoom(16)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        mMap.addMarker(options);

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current ", strReturnedAddress.toString());
            } else {
                Log.w("My Current", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current", "Canont get Address!");
        }
        return strAdd;
    }

    private void onClick() {
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraDialog();
            }
        });
        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceManage2Activity.this, AttemdanceReportActivity.class);
                startActivity(intent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        llPunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMockSettingsON(AttendanceManage2Activity.this)) {
                    Toast.makeText(AttendanceManage2Activity.this, "You are using mock location", Toast.LENGTH_LONG).show();

                } else {
                    // attendencePunch();
                    if (!stringFile.equals("")) {
                        postAttenWithImage();
                    } else {
                        Toast.makeText(AttendanceManage2Activity.this, "Please Capture Your Selfie Image", Toast.LENGTH_LONG).show();

                    }
                }


            }
        });

        llPunchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMockSettingsON(AttendanceManage2Activity.this)) {
                    Toast.makeText(AttendanceManage2Activity.this, "You are using mock location", Toast.LENGTH_LONG).show();

                } else {
                    // attendencePunch();
                    if (!stringFile.equals("")) {
                        //API with call to check open pop up or call post attendance
                        //noSalesAlert();
                        //postAttenWithImage();
                        checkSalesEntry();
                    } else {
                        Toast.makeText(AttendanceManage2Activity.this, "Please Capture Your Selfie Image", Toast.LENGTH_LONG).show();

                    }
                }


            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceManage2Activity.this, NewDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


    private void attendenceCheck() {
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        String surl = AppController.APIURL + "api/SelfAttendanceToDay?LoginID=" + prefManager.getUserId() + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputcheck", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");

                                JSONObject obj = responseData.getJSONObject(0);
                                String Date = obj.optString("Date");
                                Status = obj.optString("Status");
                                String Time = obj.optString("Time");
                                String LogoutTime = obj.optString("LogoutTime");

                                if (!Time.equals("")) {
                                    tvCheckIN.setText(Time);
                                    handleCheckoutTime(Time);
                                    llChekcinout.setVisibility(View.VISIBLE);
                                    llPunchOut.setVisibility(View.VISIBLE);
                                    llPunch.setVisibility(View.GONE);
                                } else {
                                    llChekcinout.setVisibility(View.GONE);
                                    llPunchOut.setVisibility(View.GONE);
                                    llPunch.setVisibility(View.VISIBLE);
                                }

                                if (!LogoutTime.equals("")) {
                                    tvCheckOut.setText(LogoutTime);
                                }


                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);


                            } else {
                                llLoader.setVisibility(View.VISIBLE);
                                llMain.setVisibility(View.GONE);
                                llChekcinout.setVisibility(View.GONE);
                                llPunchOut.setVisibility(View.GONE);
                                llPunch.setVisibility(View.VISIBLE);

                                //  Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }

                            //  setOtherCounter();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AttendanceManage2Activity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.VISIBLE);
                llMain.setVisibility(View.GONE);

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(AttendanceManage2Activity.this);
        requestQueue.add(stringRequest);
    }


    private void successAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AttendanceManage2Activity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText(showText);

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                attendenceCheck();

            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }

    private void wfhAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AttendanceManage2Activity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_wfh, null);
        dialogBuilder.setView(dialogView);
        Spinner spWorkingStatus = (Spinner) dialogView.findViewById(R.id.spWorkingStatus);
        final Spinner spNumber = (Spinner) dialogView.findViewById(R.id.spNumber);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (AttendanceManage2Activity.this, android.R.layout.simple_spinner_item,
                        workingStatusList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWorkingStatus.setAdapter(spinnerArrayAdapter);


        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>
                (AttendanceManage2Activity.this, android.R.layout.simple_spinner_item,
                        numberList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNumber.setAdapter(spinnerArrayAdapter1);
        LinearLayout llOtherCounter = (LinearLayout) dialogView.findViewById(R.id.llOtherCounter);

        spWorkingStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    workingStaus = workingStatusList.get(position);
                    workStatusFlag = String.valueOf(position);

                    if (workingStaus.equals("IFB Meet – Training")) {
                        workStatusFlag = "2";
                    } else if (workingStaus.equals("Branch Office – Training")) {
                        workStatusFlag = "3";
                    } else if (workingStaus.equals("IFB Exhibitions")) {
                        workStatusFlag = "4";
                    }
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                number = numberList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spOtherCounter = (SingleSpinnerSearch) dialogView.findViewById(R.id.spOtherCounter);

        spOtherCounter.setItems(keyCounterList, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {

                        counterid = items.get(i).getId();
                        Log.d("modelId", counterid);


                    }
                }
            }


        });


        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!stringFile.equals("")) {

                    postAttenWithImage();
                } else {
                    Toast.makeText(AttendanceManage2Activity.this, "Please Capture Your Selfie Image", Toast.LENGTH_LONG).show();

                }


                alertDialog2.dismiss();
            }
        });


        alertDialog2 = dialogBuilder.create();
        alertDialog2.setCancelable(true);
        Window window = alertDialog2.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog2.show();
    }

    private void turnGPSOn() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
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
                    final com.google.android.gms.common.api.Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:

                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                try {
                                    status.startResolutionForResult(AttendanceManage2Activity.this, 1000);
                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore the error.
                                }
                            } catch (Exception e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Toast.makeText(getApplicationContext(), "Location disbale", Toast.LENGTH_LONG).show();

                            break;
                    }
                }
            });
        }
    }


    private void locationalerts() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AttendanceManage2Activity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_location, null);
        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog.show();
    }


    @SuppressLint("MissingPermission")
    private void showCurrentLocationOnMap() {
        if (isLocationAccessPermitted()) {
            requestLocationAccessPermission();
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private boolean isLocationAccessPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestLocationAccessPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOC_PERM_REQ_CODE);
    }

    private void showMocAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("You are using mocking location");
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });


    }

    public static boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;
    }

    private void postWorkingStatus() {

        final ProgressDialog pd = new ProgressDialog(AttendanceManage2Activity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.dismiss();

        AndroidNetworking.upload(AppController.APIURL + "api/post_EmployeeWFHCounterManage")
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("Attendance_Type", workingStaus)
                .addMultipartParameter("Calling_Data", number)
                .addMultipartParameter("SecurityCode", prefManager.getSecurityCode())
                .addMultipartParameter("Longitude", longt)
                .addMultipartParameter("Latitude", lat)
                .addMultipartParameter("Address", address)

                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        pd.show();

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        String responseText = job1.optString("responseText");
                        Log.d("responseText", responseText);
                        boolean responseStatus = job1.optBoolean("responseStatus");
                        if (responseStatus) {
                            successAlert();

                            pd.dismiss();

                        } else {

                            pd.dismiss();
                            Toast.makeText(AttendanceManage2Activity.this, responseText, Toast.LENGTH_LONG).show();

                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG);
                    }
                });
    }


    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.d("RadiusValue", " KM " + kmInDec
                + " Meter " + meterInDec);
        String distance = String.format("%.3f", valueResult);
        final double ddis = Double.parseDouble(distance);
        Log.d("distance", String.valueOf(ddis));
        final Handler handler = new Handler();

        // Toast.makeText(getApplicationContext(),distance+"KM",Toast.LENGTH_LONG).show();
        return ddis;
    }

    private void attachDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AttendanceManage2Activity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.camera_dialog, null);
        dialogBuilder.setView(dialogView);
        LinearLayout llCamera = (LinearLayout) dialogView.findViewById(R.id.llCamera);
        LinearLayout llGallery = (LinearLayout) dialogView.findViewById(R.id.llGallery);
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialog();
            }
        });

        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent();

            }
        });


        alert1 = dialogBuilder.create();
        alert1.setCancelable(true);
        Window window = alert1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alert1.show();
    }

    private void cameraDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AttendanceManage2Activity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.camera_dialog, null);
        dialogBuilder.setView(dialogView);
        TextView tvCamera = (TextView) dialogView.findViewById(R.id.tvCamera);
        tvCamera.setText("Default Camera");
        LinearLayout llCamera = (LinearLayout) dialogView.findViewById(R.id.llCamera);
        LinearLayout llGallery = (LinearLayout) dialogView.findViewById(R.id.llGallery);
        LinearLayout llCustomCamera = (LinearLayout) dialogView.findViewById(R.id.llCustomCamera);
        llCustomCamera.setVisibility(View.VISIBLE);
        llGallery.setVisibility(View.GONE);
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();
            }
        });

        llCustomCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceManage2Activity.this, CameraActivity.class);
                startActivityForResult(intent, SELFIE_CAMERA_REQUEST);

            }
        });


        cameraAlert = dialogBuilder.create();
        cameraAlert.setCancelable(true);
        Window window = cameraAlert.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        cameraAlert.show();
    }

    private void galleryIntent() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }


    private void cameraIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Profile Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:

                if (resultCode == Activity.RESULT_OK) {
                    try {
                        try {
                            String imageurl = /*"file://" +*/ getRealPathFromURI(imageUri);
                            file = new File(imageurl);
                            try {
                                compressedImageFile = new Compressor(this).compressToFile(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            long length = file.length();
                            double m = length / 1024.0;
                            Log.d("size", String.valueOf(m));

                            BitmapFactory.Options o = new BitmapFactory.Options();
                            o.inSampleSize = 2;
                            Bitmap bm = cropToSquare(BitmapFactory.decodeFile(imageurl, o));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                            Log.d("images", encodedImage);
                            imgImage.setImageBitmap(bm);
                            cameraAlert.dismiss();
                            String contentType = "image/jpg";
                            String[] brkDown = imageurl.split("/");
                            String name = brkDown[5];
                            stringFile = name + "_" + encodedImage + "_" + contentType;


                            // _pref.saveImage(encodedImage);
                            //saveImage(encodedImage);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }

                }
                break;
            case REQUEST_GALLERY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    InputStream imageStream = null;
                    try {
                        try {
                            uri = data.getData();
                            String filePath = getRealPathFromURIPath(uri, AttendanceManage2Activity.this);
                            file = new File(filePath);
                            try {
                                compressedImageFile = new Compressor(this).compressToFile(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //  Log.d(TAG, "filePath=" + filePath);
                            imageStream = getContentResolver().openInputStream(uri);
                            Bitmap bm = cropToSquare(BitmapFactory.decodeStream(imageStream));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                            imgImage.setImageBitmap(bm);
                            cameraAlert.dismiss();
                            String contentType = "image/jpg";
                            String[] brkDown = filePath.split("/");
                            String name = brkDown[5];
                            stringFile = name + "_" + encodedImage + "_" + contentType;


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }

                }
                break;

            case LongImageBackCameraActivity.LONG_IMAGE_RESULT_CODE_BACK:


                if (resultCode == RESULT_OK && requestCode == LongImageBackCameraActivity.LONG_IMAGE_RESULT_CODE_BACK) {
                    imageFileName = data.getStringExtra(LongImageCameraActivity.IMAGE_PATH_KEY);
                    Log.d("imageFileName", imageFileName);
                    Bitmap d = BitmapFactory.decodeFile(imageFileName);
                    int newHeight = (int) (d.getHeight() * (512.0 / d.getWidth()));
                    Bitmap putImage = Bitmap.createScaledBitmap(d, 512, newHeight, true);
                    imgImage.setImageBitmap(putImage);
                    file = (File) data.getExtras().get("picture");
                    try {
                        compressedImageFile = new Compressor(this).compressToFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    putImage.compress(Bitmap.CompressFormat.PNG, 10, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    cameraAlert.dismiss();
                    String contentType = "image/png";
                    String[] brkDown = imageFileName.split("/");
                    String name = brkDown[6];
                    stringFile = name + "_" + encodedImage + "_" + contentType;
                    Log.d("stringFile", stringFile);


                }
                break;
            case SELFIE_CAMERA_REQUEST:

                if (requestCode == SELFIE_CAMERA_REQUEST && resultCode == RESULT_OK) {
                    imageUri = data.getParcelableExtra("imageUri");
                    file = new File(data.getStringExtra("imagePath"));
                    try {
                        compressedImageFile = new Compressor(this).compressToFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cameraAlert.dismiss();
                    imgImage.setImageURI(imageUri);
                    stringFile = imageUri + "_" + imageUri + "_" + "png";
                }
                break;

        }


    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public static Bitmap cropToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width) ? height - (height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0) ? 0 : cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0) ? 0 : cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }

    private void postAttenWithImage() {

        final ProgressDialog pd = new ProgressDialog(AttendanceManage2Activity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload(AppController.APIURL + "api/post_EmployeeAttendanceWithSelfy_V2")
                .addMultipartParameter("ClientID", prefManager.getClintId())
                .addMultipartParameter("LoginID", prefManager.getUserId())
                .addMultipartParameter("Password", prefManager.getPassword())
                .addMultipartParameter("Address", address)
                .addMultipartParameter("Longitude", longt)
                .addMultipartParameter("Latitude", lat)
                .addMultipartParameter("IMEI", androidID)
                .addMultipartParameter("DeviceID", refreshedToken)
                .addMultipartParameter("DeviceName", deviceName)
                .addMultipartParameter("SalesPoin_Longitude", "0")
                .addMultipartParameter("SalesPoint_Latitude", "0")
                .addMultipartParameter("Attendance_Distance_GAP", "0")
                .addMultipartParameter("SalesPointID", counterid)
                .addMultipartParameter("Counter_Type", workingStaus)
                .addMultipartParameter("Counter_Type_Flag", workStatusFlag)
                .addMultipartParameter("SecurityCode", prefManager.getSecurityCode())
                .addMultipartFile("Imagefile", compressedImageFile)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        pd.show();

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        String responseText = job1.optString("responseText");
                        showText = responseText;
                        Log.d("responseText", responseText);
                        boolean responseStatus = job1.optBoolean("responseStatus");
                        if (responseStatus) {

                            successAlert();


                            pd.dismiss();
                        } else {
                            pd.dismiss();

                            wfhAlert();
                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void setOtherCounter() {
        String surl = AppController.APIURL + "api/CommonDDL?ModuleNo=17SM&ID=" + prefManager.getUserId() + "&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("counterurl", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("counterurlresponse", response);
                        progressBar.dismiss();

                        CounterList.clear();
                        keyCounterList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String value = obj.optString("value");
                                    String id = obj.optString("id");
                                    String MRP = obj.optString("MRP");
                                    ModelSpinnerModel itemModule = new ModelSpinnerModel(value, id, MRP);
                                    CounterList.add(itemModule);

                                }

                                for (int j = 0; j < CounterList.size(); j++) {
                                    KeyPairBoolData h = new KeyPairBoolData();
                                    h.setName(CounterList.get(j).getValue());
                                    h.setId(CounterList.get(j).getId());
                                    h.setMrp(CounterList.get(j).getMrp());
                                    h.setSelected(false);
                                    keyCounterList.add(h);

                                }


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AttendanceManage2Activity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "model");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(AttendanceManage2Activity.this);
        requestQueue.add(stringRequest);

    }

    private void handleCheckoutTime(String apiTime) {

        try {
            // Parse only TIME from API
            SimpleDateFormat inputFormat =
                    new SimpleDateFormat("h:mma", Locale.getDefault());
            Date checkInDate = inputFormat.parse(apiTime);

            // Get today's date
            Calendar today = Calendar.getInstance();

            // Create calendar with TODAY + API TIME
            Calendar checkoutLimitCal = Calendar.getInstance();
            checkoutLimitCal.set(Calendar.YEAR, today.get(Calendar.YEAR));
            checkoutLimitCal.set(Calendar.MONTH, today.get(Calendar.MONTH));
            checkoutLimitCal.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH));

            // Set hour & minute from API time
            Calendar apiTimeCal = Calendar.getInstance();
            apiTimeCal.setTime(checkInDate);

            checkoutLimitCal.set(Calendar.HOUR_OF_DAY, apiTimeCal.get(Calendar.HOUR_OF_DAY));
            checkoutLimitCal.set(Calendar.MINUTE, apiTimeCal.get(Calendar.MINUTE));
            checkoutLimitCal.set(Calendar.SECOND, 0);

            // Add 9 hours 15 minutes
            checkoutLimitCal.add(Calendar.HOUR_OF_DAY, 9);
            checkoutLimitCal.add(Calendar.MINUTE, 00);

            // Current time
            Calendar now = Calendar.getInstance();

            // Display format
            SimpleDateFormat displayFormat =
                    new SimpleDateFormat("hh:mm a", Locale.getDefault());

            String checkoutLimitTime =
                    displayFormat.format(checkoutLimitCal.getTime());

            if (now.before(checkoutLimitCal)) {
                tvCheckOut.setText(checkoutLimitTime);
                // ✅ Checkout allowed
//                tvCheckOutTime.setText(
//                        "You can check out until " + checkoutLimitTime +
//                                " today. Post " + checkoutLimitTime +
//                                ", the checkout option will be automatically disabled."
//                );


            } else {



                // ❌ Checkout blocked


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateManageLayoutStatusForCheckOut() {


        // Enable at 2:00 PM and after
        if (currentTime >= minCheckOutTime) {
            llPunchOut.setEnabled(true);
            llPunchOut.setAlpha(1.0f);
        } else {
            llPunchOut.setEnabled(false);
            llPunchOut.setAlpha(0.5f);

        }
    }


    private void noSalesAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AttendanceManage2Activity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialogcheckoutsales, null);
        dialogBuilder.setView(dialogView);
        Spinner spSold = (Spinner) dialogView.findViewById(R.id.spSold);
        Spinner spNumber = (Spinner) dialogView.findViewById(R.id.spNumbers);
        LinearLayout llSoldDetails = dialogView.findViewById(R.id.llsoldDetails);
        ArrayList<String> yesnolist = new ArrayList<>();
        yesnolist.add("Please Select");
        yesnolist.add("Yes");
        yesnolist.add("No");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (AttendanceManage2Activity.this, android.R.layout.simple_spinner_item,
                        yesnolist); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSold.setAdapter(spinnerArrayAdapter);


        ArrayList<String> numberList = new ArrayList<>();
        numberList.add("Please Select");
        numberList.add("1");
        numberList.add("2");
        numberList.add("3");
        numberList.add("4");
        numberList.add("5");
        numberList.add("6");
        numberList.add("7");
        numberList.add("8");
        numberList.add("9");
        numberList.add("10");

        ArrayAdapter<String> spinnerNumberArrayAdapter = new ArrayAdapter<String>
                (AttendanceManage2Activity.this, android.R.layout.simple_spinner_item,
                        numberList); //selected item will look like a spinner set from XML
        spinnerNumberArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNumber.setAdapter(spinnerNumberArrayAdapter);

        spSold.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    yesNo= yesnolist.get(i);
                    if (i == 1) {
                        llSoldDetails.setVisibility(View.VISIBLE);
                        soldValue = "";
                    } else {
                        // set send value 0
                        soldValue = "0";
                        llSoldDetails.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {

                    // set send value 0
                    soldValue = numberList.get(i);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button btnSubmit = (Button) dialogView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!yesNo.equals("")){
                    if (!soldValue.equals("")){
                        noSalesDialog.dismiss();
                        postSalesEntry(soldValue);

                    }else {
                        Toast.makeText(AttendanceManage2Activity.this, "Please select how many product you sold today", Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Toast.makeText(AttendanceManage2Activity.this, "Please select have you sold any IFB product", Toast.LENGTH_SHORT).show();
                }
            }
        });


        noSalesDialog = dialogBuilder.create();
        noSalesDialog.setCancelable(false);
        Window window = noSalesDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        noSalesDialog.show();
    }


    private void checkSalesEntry() {

        final ProgressDialog pd = new ProgressDialog(AttendanceManage2Activity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.get(AppController.APIURL + "api/EmployeeCheckOutDailySales")
                .addQueryParameter("Code", prefManager.getMasterId())
                .addQueryParameter("SalesCount", "0")
                .addQueryParameter("Operation", "1")
                .addQueryParameter("SecurityCode", prefManager.getSecurityCode())

                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        pd.show();

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        try {
                            JSONObject job1 = response;
                            JSONArray responseData = job1.optJSONArray("responseData");
                            JSONObject frstOBJ = responseData.getJSONObject(0);
                            String ErrorCode = frstOBJ.getString("ErrorCode");
                            String Msg = frstOBJ.getString("Msg");
                            if (ErrorCode.equals("0")){
                                noSalesAlert();
                            }else {
                                postAttenWithImage();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }





                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {

                        pd.dismiss();
                        Toast.makeText(AttendanceManage2Activity.this, "Error Occured 1", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void postSalesEntry(String count) {

        final ProgressDialog pd = new ProgressDialog(AttendanceManage2Activity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.get(AppController.APIURL + "api/EmployeeCheckOutDailySales")
                .addQueryParameter("Code", prefManager.getMasterId())
                .addQueryParameter("SalesCount", count)
                .addQueryParameter("Operation", "2")
                .addQueryParameter("SecurityCode", prefManager.getSecurityCode())

                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        pd.show();

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        try {
                            JSONObject job1 = response;
                            JSONArray responseData = job1.optJSONArray("responseData");
                            JSONObject frstOBJ = responseData.getJSONObject(0);
                            String ErrorCode = frstOBJ.getString("ErrorCode");
                            String Msg = frstOBJ.getString("Msg");
                            if (ErrorCode.equals("1")){

                                Toast.makeText(AttendanceManage2Activity.this, Msg, Toast.LENGTH_LONG).show();
                            }else {
                                postAttenWithImage();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }





                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {

                        pd.dismiss();
                        Toast.makeText(AttendanceManage2Activity.this, "Error Occured 1", Toast.LENGTH_LONG).show();
                    }
                });
    }


}
