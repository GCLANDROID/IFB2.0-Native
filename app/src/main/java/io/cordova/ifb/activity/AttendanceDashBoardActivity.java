package io.cordova.ifb.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.GPSTracker;
import io.cordova.ifb.utility.NetworkConnectionCheck;
import io.cordova.ifb.utility.PrefManager;

public class AttendanceDashBoardActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    LinearLayout llReport, llManage;
    ImageView imgBack;
    LinearLayout llLeave, llLeaveEnc, llVaccination, llCheckOut;
    String leaveurl = "";
    String leaveencurl = "";
    PrefManager prefManager;
    ImageView imgHome;
    NetworkConnectionCheck connectionCheck;
    GoogleApiClient googleApiClient;
    String responseText, responseCode;
    AlertDialog alet1;
    boolean responseData;
    AlertDialog alerDialog1;
    ImageView imgPic;
    private Uri imageUri, imageUri1, imageUri2;
    private static final int CAMERA_REQUEST = 1;
    File file;
    String encodedImage;
    String stringFile;
    int pic1Flag = 0;
    GPSTracker gps;
    Double latitude, longitude;
    String address;
    String latt, longt;
    AlertDialog alerDialog2;
    String counterLat = "0.00", counterLong = "0.00";
    int radius;
    int attFalg;
    String financialYear, year, month;
    LinearLayout llADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_dash_board);
        initialize();
        checksale();
        attendenceCheck();
        onClick();
    }

    private void initialize() {
        prefManager = new PrefManager(AttendanceDashBoardActivity.this);

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

        connectionCheck = new NetworkConnectionCheck(AttendanceDashBoardActivity.this);
        llADD=(LinearLayout)findViewById(R.id.llADD);
        llReport = (LinearLayout) findViewById(R.id.llReport);
        llManage = (LinearLayout) findViewById(R.id.llManage);
        llCheckOut = (LinearLayout) findViewById(R.id.llCheckOut);
        llLeave = (LinearLayout) findViewById(R.id.llLeave);
        llLeaveEnc = (LinearLayout) findViewById(R.id.llLeaveEnc);
        llVaccination = (LinearLayout) findViewById(R.id.llVaccination);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        leaveurl = prefManager.getLeaveURL();
        leaveencurl = prefManager.getLeaveEncahURL();
        imgHome = (ImageView) findViewById(R.id.imgHome);
        connectionCheck = new NetworkConnectionCheck(AttendanceDashBoardActivity.this);
        gps = new GPSTracker(AttendanceDashBoardActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            latt = String.valueOf(latitude);
            Log.d("saikatdas", String.valueOf(latitude));
            longitude = gps.getLongitude();
            longt = String.valueOf(longitude);
        } else {
// can't get location
// GPS or Network is not enabled
// Ask user to enable GPS/network in settings

        }
        address = getCompleteAddressString(latitude, longitude);


    }

    private void onClick() {
        llADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(AttendanceDashBoardActivity.this,FaceRecogntization.class);
                intent.putExtra("flag","1");
                startActivity(intent);*/
            }
        });
        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AttendanceDashBoardActivity.this, AttemdanceReportActivity.class);
                startActivity(intent);

            }
        });
        llVaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AttendanceDashBoardActivity.this, VaccineDashboardActivity.class);
                startActivity(intent);

            }
        });
        llManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionCheck.isGPSEnabled()) {
                    if (responseCode.equals("1")) {
                        getCounetrCoordinates();
                    } else {
                        salecheckalert();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Please enable GPS location", Toast.LENGTH_LONG).show();
                    turnGPSOn();
                }
            }
        });

        llCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionCheck.isGPSEnabled()) {

                    getCounetrCoordinatesForCheckOut();

                } else {

                    Toast.makeText(getApplicationContext(), "Please enable GPS location", Toast.LENGTH_LONG).show();
                    turnGPSOn();
                }
            }
        });

        llLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (responseCode.equals("1")) {
                    openLeaveBrowser();
                } else {
                    salecheckalert();
                }
            }
        });

        llLeaveEnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (responseCode.equals("1")) {
                    openLeaveEncBrowser();
                } else {
                    salecheckalert();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceDashBoardActivity.this, NewDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void openLeaveBrowser() {
        Uri uri = Uri.parse(leaveurl); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (leaveurl.equals("")) {

        } else {
            startActivity(intent);
        }
    }


    private void openLeaveEncBrowser() {
        Uri uri = Uri.parse(leaveencurl); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (leaveencurl.equals("")) {

        } else {
            startActivity(intent);
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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

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
                                    status.startResolutionForResult(AttendanceDashBoardActivity.this, 1000);
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


    public void checksale() {
        String surl =  AppController.APIURL+"api/get_Comp_DisplayMateix_Status?AEMEmployeeID=" + prefManager.getUserId() + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        progressBar.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@ 2" + job1);
                            responseText = job1.optString("responseText");
                            responseCode = job1.optString("responseCode");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            responseData = job1.optBoolean("responseData");
                            checkCounterMap();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AttendanceDashBoardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(AttendanceDashBoardActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(AttendanceDashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    public void checkCounterMap() {
        String surl =  AppController.APIURL+"api/get_EmployeeSalespointGeoInfo?UserID=" + prefManager.getUserId() + "&Operation=1&SubOperation=2&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        progressBar.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@ 3" + job1);
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {

                            } else {
                                counterMapDialog();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AttendanceDashBoardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(AttendanceDashBoardActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(AttendanceDashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    public void checkNewUI() {
        String surl =  AppController.APIURL+"api/get_EmployeeLockdownAttendanceStatus?AEMEmployeeID=" + prefManager.getUserId() + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        progressBar.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@ 4" + job1);
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                Intent intent = new Intent(AttendanceDashBoardActivity.this, AttendanceManage2Activity.class);
                                intent.putExtra("counterlat", counterLat);
                                intent.putExtra("counterlong", counterLong);
                                intent.putExtra("attFlag", attFalg);
                                intent.putExtra("radius", radius);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(AttendanceDashBoardActivity.this, AttendanceManageActivity.class);
                                startActivity(intent);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AttendanceDashBoardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(AttendanceDashBoardActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(AttendanceDashBoardActivity.this);
        requestQueue.add(stringRequest);

    }


    private void salecheckalert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AttendanceDashBoardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_salecheck, null);
        dialogBuilder.setView(dialogView);

        TextView tvItem = (TextView) dialogView.findViewById(R.id.tvItem);
        tvItem.setText(responseText);

        Button btnOK = (Button) dialogView.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alet1.dismiss();

            }
        });

        Button btnSkip = (Button) dialogView.findViewById(R.id.btnSkip);
        if (responseData) {
            btnSkip.setVisibility(View.VISIBLE);
        } else {
            btnSkip.setVisibility(View.GONE);
        }

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(AttendanceDashBoardActivity.this,AttendanceManageActivity.class);
                startActivity(intent);
                finish();*/
                getCounetrCoordinates();
                alet1.dismiss();
            }
        });


        alet1 = dialogBuilder.create();
        alet1.setCancelable(false);
        Window window = alet1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alet1.show();
    }

    private void counterMapDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AttendanceDashBoardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_geofence, null);
        dialogBuilder.setView(dialogView);
        ImageView imgCamera = (ImageView) dialogView.findViewById(R.id.imgCamera);
        imgPic = (ImageView) dialogView.findViewById(R.id.imgPic);
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntentforPic1();
            }
        });
        TextView tvAddress = (TextView) dialogView.findViewById(R.id.tvAddress);
        String addressT = "<font color='#EE0000'>Address: </font>";
        tvAddress.setText(Html.fromHtml(addressT + " " + address));
        String latT = "<font color='#EE0000'>Latitude: </font>";
        String longT = "<font color='#EE0000'>Longitude: </font>";
        TextView tvLatLong = (TextView) dialogView.findViewById(R.id.tvLatLong);
        tvLatLong.setText(Html.fromHtml(latT + latt + "   " + longT + longt));
        ImageView imgCancel = (ImageView) dialogView.findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerDialog1.dismiss();
            }
        });
        Button btnSave = (Button) dialogView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pic1Flag == 1) {
                    alerDialog1.dismiss();
                    postCounterImage();
                } else {
                    Toast.makeText(AttendanceDashBoardActivity.this, "Please Upload Counter Image", Toast.LENGTH_LONG).show();
                }
            }
        });


        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
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

                            BitmapFactory.Options o = new BitmapFactory.Options();
                            o.inSampleSize = 2;
                            Bitmap bm = cropToSquare(BitmapFactory.decodeFile(imageurl, o));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            imgPic.setImageBitmap(bm);
                            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                            String contentType = "image/jpg";
                            String[] brkDown = imageurl.split("/");
                            String name = brkDown[5];
                            stringFile = name + "_" + encodedImage + "_" + contentType;
                            pic1Flag = 1;


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

    private void cameraIntentforPic1() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Profile Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
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

    private void postCounterImage() {

        final ProgressDialog pd = new ProgressDialog(AttendanceDashBoardActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload( AppController.APIURL+"api/Post_EmployeeSalespointGeoInfo")
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("SalesPointID", "0")
                .addMultipartParameter("Longitude", longt)
                .addMultipartParameter("Latitude", latt)
                .addMultipartParameter("Address", address)
                .addMultipartParameter("GeoInfocopy", stringFile)
                .addMultipartParameter("Operation", "1")
                .addMultipartParameter("SubOperation", "1")
                .addMultipartParameter("SecurityCode", prefManager.getSecurityCode())

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
                        Log.d("responseText", responseText);
                        boolean responseStatus = job1.optBoolean("responseStatus");
                        if (responseStatus) {
                            successAlert(responseText);
                            pd.dismiss();

                        } else {
                            pd.dismiss();
                            Toast.makeText(AttendanceDashBoardActivity.this, responseText, Toast.LENGTH_LONG).show();

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

    private void successAlert(String text) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AttendanceDashBoardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText(text);

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog2.dismiss();

            }
        });

        alerDialog2 = dialogBuilder.create();
        alerDialog2.setCancelable(false);
        Window window = alerDialog2.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog2.show();
    }

    public void getCounetrCoordinates() {
        String surl =  AppController.APIURL+"api/get_EmployeeSalespointLatLong?EmployeeID=" + prefManager.getUserId() + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        progressBar.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@ 5" + job1);
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                JSONArray responseData = job1.optJSONArray("responseData");
                                JSONObject obj = responseData.optJSONObject(0);
                                counterLat = obj.optString("Latitude");
                                counterLong = obj.optString("Longitude");
                                radius = obj.optInt("Radius");
                                attFalg = obj.optInt("Flag");


                            } else {
                                counterLat = "0.00";
                                counterLong = "0.00";
                                radius = 0;
                                attFalg = 0;
                            }

                            checkNewUI();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AttendanceDashBoardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(AttendanceDashBoardActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(AttendanceDashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    public void getCounetrCoordinatesForCheckOut() {
        String surl =  AppController.APIURL+"api/get_EmployeeSalespointLatLong?EmployeeID=" + prefManager.getUserId() + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        progressBar.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                JSONArray responseData = job1.optJSONArray("responseData");
                                JSONObject obj = responseData.optJSONObject(0);
                                counterLat = obj.optString("Latitude");
                                counterLong = obj.optString("Longitude");
                                radius = obj.optInt("Radius");
                                attFalg = obj.optInt("Flag");


                            } else {
                                counterLat = "0.00";
                                counterLong = "0.00";
                                radius = 0;
                                attFalg = 0;
                            }

                            Intent intent = new Intent(AttendanceDashBoardActivity.this, AttendanceCheckOutActivity.class);
                            intent.putExtra("counterlat", counterLat);
                            intent.putExtra("counterlong", counterLong);
                            intent.putExtra("attFlag", attFalg);
                            intent.putExtra("radius", radius);
                            startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AttendanceDashBoardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(AttendanceDashBoardActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(AttendanceDashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    private void attendenceCheck() {
        final ProgressDialog progressDialog = new ProgressDialog(AttendanceDashBoardActivity.this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String surl = AppController.APIURL+"api/SelfAttendance?LoginID=" + prefManager.getUserId() + "&FinancialYear=" + financialYear + "&Month=" + month + "&ReportType=2&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputcheck", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);
                        progressDialog.dismiss();

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@ 1" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");

                            //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                            JSONArray responseData = job1.optJSONArray("responseData");

                            JSONObject obj = responseData.getJSONObject(0);
                            String ReportType = obj.optString("ReportType");
                            if (ReportType.equals("1")) {
                                if (prefManager.getUserTypeId().equals("IFBMM1000011") ||prefManager.getUserTypeId().equals("IFBUT1000136") ) {
                                    llCheckOut.setVisibility(View.VISIBLE);
                                }else {
                                    llCheckOut.setVisibility(View.GONE);
                                }
                            } else {
                                llCheckOut.setVisibility(View.GONE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AttendanceDashBoardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(AttendanceDashBoardActivity.this);
        requestQueue.add(stringRequest);
    }


}
