package io.cordova.ifb.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.AttemdanceReportActivity;
import io.cordova.ifb.activity.AttendanceCalendarDialogActivity;
import io.cordova.ifb.activity.AttendanceCheckOutActivity;
import io.cordova.ifb.activity.AttendanceDashBoardActivity;
import io.cordova.ifb.activity.AttendanceManage2Activity;
import io.cordova.ifb.activity.AttendanceManageActivity;
import io.cordova.ifb.activity.DashBoardActivity;
import io.cordova.ifb.activity.ReferEarnActivity;
import io.cordova.ifb.activity.SurveyActivity;
import io.cordova.ifb.activity.VaccineDashboardActivity;
import io.cordova.ifb.adapter.CheckoutReportAdapter;
import io.cordova.ifb.module.CheckOutStatusModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.GPSTracker;
import io.cordova.ifb.utility.NetworkConnectionCheck;
import io.cordova.ifb.utility.PrefManager;

public class AttendanceFragment extends Fragment {

      View view;
      PrefManager prefManager;
    LinearLayout llReport, llManage;
    LinearLayout llLeave, llLeaveEnc, llVaccination, llCheckOut;
    String leaveurl = "";
    String leaveencurl = "";
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
    AlertDialog checkoutDialog;
    String counterLat = "0.00", counterLong = "0.00";
    int radius;
    int attFalg;
    String financialYear, year, month;
    LinearLayout llADD;
    TextView tvStatus,tvMonthAttendance,tvCheckOutTime;
    ArrayList<CheckOutStatusModel>checkoutList=new ArrayList<>();
    TextView tvCheckCount;
    LinearLayout llCheckOutCount,llCheckOutMessage;
    int minCheckInTime;
    int minCheckOutTime;
    int currentTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_attendance, container, false);
        initialize();
        checksale();
        attendenceCheck();
        onClick();
        return view;
    }

    private void initialize() {
        prefManager = new PrefManager(getContext());

         minCheckInTime  = prefManager.getCheckInHr() * 60 + prefManager.getCheckInMin();
         minCheckOutTime = prefManager.getCheckOutHr() * 60 + prefManager.getCheckOutMin();

        Calendar now = Calendar.getInstance();
         currentTime = now.get(Calendar.HOUR_OF_DAY) * 60
                + now.get(Calendar.MINUTE);

        if (prefManager.getIsFillCSRSurvey().equals("1")){
           showAlert();

        }

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

        tvCheckCount=(TextView)view.findViewById(R.id.tvCheckCount);

        connectionCheck = new NetworkConnectionCheck(getContext());
        llADD=(LinearLayout)view.findViewById(R.id.llADD);
        llReport = (LinearLayout) view.findViewById(R.id.llReport);
        llManage = (LinearLayout) view.findViewById(R.id.llManage);

        llCheckOut = (LinearLayout) view.findViewById(R.id.llCheckOut);
        updateManageLayoutStatusForCheckOut();
        updateManageLayoutStatus();
        llLeave = (LinearLayout) view.findViewById(R.id.llLeave);
        llLeaveEnc = (LinearLayout)view. findViewById(R.id.llLeaveEnc);
        llVaccination = (LinearLayout) view.findViewById(R.id.llVaccination);
        leaveurl = prefManager.getLeaveURL();
        leaveencurl = prefManager.getLeaveEncahURL();
        connectionCheck = new NetworkConnectionCheck(getContext());
        gps = new GPSTracker(getContext());
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
        llCheckOutCount=view.findViewById(R.id.llCheckOutCount);
        llCheckOutMessage=view.findViewById(R.id.llCheckOutMessage);
        address = getCompleteAddressString(latitude, longitude);
        tvStatus=(TextView) view.findViewById(R.id.tvStatus);
        tvMonthAttendance=(TextView) view.findViewById(R.id.tvMonthAttendance);
        tvCheckOutTime=(TextView) view.findViewById(R.id.tvCheckOutTime);

        if (getArguments() != null) {
            String Date = getArguments().getString("Date");
            String Time = getArguments().getString("Time");
            String Status = getArguments().getString("Status");
            String LogoutTime = getArguments().getString("LogoutTime");
            if (Time.equals("")) {
                llCheckOutMessage.setVisibility(View.GONE);

            }

            handleCheckoutTime(Time);
            if (Status.equalsIgnoreCase("P")){
                tvStatus.setText("Attendance Status of "+Date+" : Present (In : "+Time+" - Out : "+LogoutTime+" )");
            }else if (Status.equalsIgnoreCase("A")){
                tvStatus.setText("Attendance Status of "+Date+" : Absent");
            }else if (Status.equalsIgnoreCase("WO")){
                tvStatus.setText("Attendance Status of "+Date+" : Weekly OFF");
            }else if (Status.equalsIgnoreCase("H")){
                tvStatus.setText("Attendance Status of "+Date+" : Holiday");
            }else if (Status.equalsIgnoreCase("CL") || Status.equalsIgnoreCase("SL")||Status.equalsIgnoreCase("PL")||Status.equalsIgnoreCase("CO")){
                tvStatus.setText("Attendance Status of "+Date+" : On Leave");
            }else {
                tvStatus.setText("Attendance Status of "+Date+" :  Not Marked");
            }



        }

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

                Intent intent = new Intent(getContext(), AttemdanceReportActivity.class);
                startActivity(intent);

            }
        });

        llCheckOutCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              checkoutCountDialog();

            }
        });
        tvMonthAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AttendanceCalendarDialogActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        llVaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), VaccineDashboardActivity.class);
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

                    Toast.makeText(getContext(), "Please enable GPS location", Toast.LENGTH_LONG).show();

                }
            }
        });

        llCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionCheck.isGPSEnabled()) {

                    getCounetrCoordinatesForCheckOut();

                } else {

                    Toast.makeText(getContext(), "Please enable GPS location", Toast.LENGTH_LONG).show();

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

    public void checksale() {
        String surl =  AppController.APIURL+"api/get_Comp_DisplayMateix_Status?AEMEmployeeID=" + prefManager.getUserId() + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(getContext());
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
                            responseText = job1.optString("responseText");
                            responseCode = job1.optString("responseCode");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            responseData = job1.optBoolean("responseData");
                            checkCounterMap();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(getContext(), "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void checkCounterMap() {
        String surl =  AppController.APIURL+"api/get_EmployeeSalespointGeoInfo?UserID=" + prefManager.getUserId() + "&Operation=1&SubOperation=2&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(getContext());
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

                            } else {
                                counterMapDialog();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(getContext(), "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void checkNewUI() {
        String surl =  AppController.APIURL+"api/get_EmployeeLockdownAttendanceStatus?AEMEmployeeID=" + prefManager.getUserId() + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(getContext());
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
                                Intent intent = new Intent(getContext(), AttendanceManage2Activity.class);
                                intent.putExtra("counterlat", counterLat);
                                intent.putExtra("counterlong", counterLong);
                                intent.putExtra("attFlag", attFalg);
                                intent.putExtra("radius", radius);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getContext(), AttendanceManageActivity.class);
                                startActivity(intent);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(getContext(), "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    private void salecheckalert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    Toast.makeText(getContext(), "Please Upload Counter Image", Toast.LENGTH_LONG).show();
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
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
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
        imageUri = getActivity().getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
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

        final ProgressDialog pd = new ProgressDialog(getContext());
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
                            Toast.makeText(getContext(), responseText, Toast.LENGTH_LONG).show();

                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG);
                    }
                });
    }

    private void successAlert(String text) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        final ProgressDialog progressBar = new ProgressDialog(getContext());
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

                            checkNewUI();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(getContext(), "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void getCounetrCoordinatesForCheckOut() {
        String surl =  AppController.APIURL+"api/get_EmployeeSalespointLatLong?EmployeeID=" + prefManager.getUserId() + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(getContext());
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

                            Intent intent = new Intent(getContext(), AttendanceCheckOutActivity.class);
                            intent.putExtra("counterlat", counterLat);
                            intent.putExtra("counterlong", counterLong);
                            intent.putExtra("attFlag", attFalg);
                            intent.putExtra("radius", radius);
                            startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(getContext(), "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void attendenceCheck() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
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
                            Log.e("response12", "@@@@@@" + job1);
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


                           // cuurentAttendanceStatus();
                            attendanceCheckoutCount();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    private void attendanceCheckoutCount() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String surl = AppController.APIURL+"api/CheckOutAttendanceStatus?LoginID=" + prefManager.getUserId() + "&FinancialYear="+financialYear+"&Month="+month+"&SecurityCode=" + prefManager.getSecurityCode();
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
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus){
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i=0;i<responseData.length();i++){
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String Date = obj.optString("Date");
                                    String CheckInTime = obj.optString("CheckInTime");
                                    CheckOutStatusModel statusModel=new CheckOutStatusModel();
                                    statusModel.setDate(Date);
                                    statusModel.setCheckInTime(CheckInTime);
                                    checkoutList.add(statusModel);

                                }
                                if (checkoutList.size()>0){
                                    llCheckOutCount.setVisibility(View.VISIBLE);
                                }else {
                                    llCheckOutCount.setVisibility(View.GONE);
                                }

                                int count = checkoutList.size();

                                String text = "You have missed checkout for "
                                        + count
                                        + " day(s) of this month. Kindly ensure timely checkout. Tap here to view details.";

                                SpannableString spannable = new SpannableString(text);

// find start & end index of the number
                                int start = text.indexOf(String.valueOf(count));
                                int end = start + String.valueOf(count).length();

// make only the number bold
                                spannable.setSpan(
                                        new StyleSpan(Typeface.BOLD),
                                        start,
                                        end,
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                );

// set to TextView
                                tvCheckCount.setText(spannable);

                               // tvCheckCount.setText("You have missed checkout for the last "+checkoutList.size()+" day(s). Kindly ensure timely checkout.Tap here to view details.");



                            }







                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    private void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("We appreciate you! Please complete a quick survey so we can keep improving.");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Start",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        Intent intent=new Intent(getContext(), SurveyActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        alertDialogBuilder.show();


    }


    private void updateManageLayoutStatus() {



        // Enable at 10:00 AM and after


        if (currentTime >= minCheckInTime) {
            llManage.setEnabled(true);
            llManage.setAlpha(1.0f);
        } else {
            llManage.setEnabled(false);
            llManage.setAlpha(0.5f);
            showAlertForCheckin();
        }
    }


    private void updateManageLayoutStatusForCheckOut() {



        // Enable at 2:00 PM and after
        if (currentTime >= minCheckOutTime) {
            llCheckOut.setEnabled(true);
            llCheckOut.setAlpha(1.0f);
        } else {
            llCheckOut.setEnabled(false);
            llCheckOut.setAlpha(0.5f);
            showAlertForCheckOut();
        }
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
            checkoutLimitCal.add(Calendar.MINUTE, 15);

            // Current time
            Calendar now = Calendar.getInstance();

            // Display format
            SimpleDateFormat displayFormat =
                    new SimpleDateFormat("hh:mm a", Locale.getDefault());

            String checkoutLimitTime =
                    displayFormat.format(checkoutLimitCal.getTime());

            if (now.before(checkoutLimitCal)) {
                // ✅ Checkout allowed
                tvCheckOutTime.setText(
                        "You can check out until " + checkoutLimitTime +
                                " today. Post " + checkoutLimitTime +
                                ", the checkout option will be automatically disabled."
                );


            } else {
                // ❌ Checkout blocked
                tvCheckOutTime.setText(
                        "The stipulated checkout time has passed. Your checkout is now blocked."
                );
                llCheckOut.setEnabled(false);
                llCheckOut.setAlpha(0.5f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkoutCountDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_checkout, null);
        dialogBuilder.setView(dialogView);
        ImageView imgCheckout=dialogView.findViewById(R.id.imgCheckout);
        RecyclerView rvItem=dialogView.findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);
        CheckoutReportAdapter reportAdapter=new CheckoutReportAdapter(checkoutList);
        rvItem.setAdapter(reportAdapter);
        imgCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkoutDialog.dismiss();
            }
        });



        checkoutDialog = dialogBuilder.create();
        checkoutDialog.setCancelable(true);
        Window window = checkoutDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        checkoutDialog.show();
    }

    private void showAlertForCheckin() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Check-in is allowed from "+prefManager.getCheckInHr()+":"+prefManager.getCheckInMin()+"0 AM onwards.");
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        alertDialogBuilder.show();


    }

    private void showAlertForCheckOut() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Check-out is allowed from "+prefManager.getCheckOutHr()+":"+prefManager.getCheckOutMin()+"0 onwards.");
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        alertDialogBuilder.show();


    }


}