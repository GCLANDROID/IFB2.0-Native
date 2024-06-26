package io.cordova.ifb.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.ImeiReqAdapter;
import io.cordova.ifb.adapter.NotificationAdapter;
import io.cordova.ifb.module.IMEIReqModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.NetworkConnectionCheck;
import io.cordova.ifb.utility.PrefManager;

public class DashBoardActivity extends AppCompatActivity {
    LinearLayout llAttendance, llSales, llQA, llMatrix, llHelpDesk, llECatelougge, llQueries,llELearning,llCsrIssue;
    PrefManager prefManager;
    TextView tvUserName, tvLoginTime;
    int y;
    String year, month, financialYear;
    String helpdeskurl = "";
    String manual = "";
    ImageView imgLogout, imageview;
    GoogleApiClient googleApiClient;
    NetworkConnectionCheck connectionCheck;
    AlertDialog alertDialog;
    LinearLayout llCVisit;
    LinearLayout llDocument, llDailyLog, llTeleCalling, llLogout,llIQueries,llChangeIMEI,llReferEarn;
    String playversion, version;
    LinearLayout llCSR;
    Toolbar toolbar;
    DrawerLayout dlMain;
    boolean mslideState;
    TextView tvCounter;
    AlertDialog al1,al2,imeialert,imeireqalert,cusSOPalert,notifyALert;
    RecyclerView rvItem;
    LinearLayout llNotificaton;
    JSONArray itemList;
    int scrollCount;
    NotificationAdapter aAdapter;
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE=100;
    TextView tvExistingNumber,tvCurrentNumber;
    String android_id,androidID,oldIMEI;
    boolean changeScreen;
    ArrayList<IMEIReqModel>imeiList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        initialize();
        Intent intent = new Intent(DashBoardActivity.this, FeedbackAprilActivity.class);
        startActivity(intent);
        onClick();

    }

    private void initialize() {
        mAppUpdateManager= AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE,DashBoardActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        connectionCheck = new NetworkConnectionCheck(DashBoardActivity.this);
        prefManager = new PrefManager(DashBoardActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        dlMain = (DrawerLayout) findViewById(R.id.dlMain);
        llAttendance = (LinearLayout) findViewById(R.id.llAttendance);
        llSales = (LinearLayout) findViewById(R.id.llSales);
        llQA = (LinearLayout) findViewById(R.id.llQA);
        llMatrix = (LinearLayout) findViewById(R.id.llMatrix);
        llECatelougge = (LinearLayout) findViewById(R.id.llECatelougge);
        llIQueries = (LinearLayout) findViewById(R.id.llIQueries);
        llCsrIssue=(LinearLayout)findViewById(R.id.llCsrIssue);

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvUserName.setText(prefManager.getEmpName());
        tvLoginTime = (TextView) findViewById(R.id.tvLoginTime);

        y = Calendar.getInstance().get(Calendar.YEAR);
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

        llHelpDesk = (LinearLayout) findViewById(R.id.llHelpDesk);
        llQueries = (LinearLayout) findViewById(R.id.llQueries);
        helpdeskurl = prefManager.getHRDeskURL();
        manual = prefManager.getManualURL();

        imgLogout = (ImageView) findViewById(R.id.imgLogout);
        imageview = (ImageView) findViewById(R.id.imageview);
        if (prefManager.getUserTypeId().equals("IFBMM1000011")) {

            Intent intent = new Intent(DashBoardActivity.this, SaleDialogActivity.class);
            startActivity(intent);

        } else {

        }

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;

            Log.d("sddk", version);
            Log.d("sdkl", String.valueOf(version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tvLoginTime.setText("APP version: "+version );


        llDocument = (LinearLayout) findViewById(R.id.llDocument);
        if (prefManager.getDocFlag().equals("1")) {
            llDocument.setVisibility(View.VISIBLE);
        } else {
            llDocument.setVisibility(View.GONE);
        }
        llDailyLog = (LinearLayout) findViewById(R.id.llDailyLog);
        llLogout = (LinearLayout) findViewById(R.id.llLogout);
        if (prefManager.getDailyLogFlag().equals("1")) {
            llDailyLog.setVisibility(View.VISIBLE);
        } else {
            llDailyLog.setVisibility(View.GONE);
        }
//IFBUT1000133


        tvCounter = (TextView) findViewById(R.id.tvCounter);
        tvCounter.setText(prefManager.getCounter());
        llELearning=(LinearLayout)findViewById(R.id.llELearning);
        llChangeIMEI=(LinearLayout)findViewById(R.id.llChangeIMEI);
        llReferEarn=(LinearLayout)findViewById(R.id.llReferEarn);

        tvExistingNumber = (TextView) findViewById(R.id.tvExistingNumber);
        tvCurrentNumber = (TextView) findViewById(R.id.tvCurrentNumber);


        SharedPreferences prefs = getSharedPreferences("io.cordova.myapp3b4e11", MODE_PRIVATE);

        int launch_count = prefs.getInt("launch_count", 0);

        if(launch_count>=3){
            // third time launch
            // Toast.makeText(DashBoardActivity.this,"3 time",Toast.LENGTH_LONG).show();
            RateApp(DashBoardActivity.this);

        } else {
            prefs.edit()
                    .putInt("launch_count", launch_count+1)
                    .apply();
        }

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
        }else {
            androidID=android_id;
        }

        getIMEINumber();
        if (!prefManager.getCustomerSOPImage().equals("")){
            cusSOPAlert();
        }else {

        }

        if (!prefManager.getNotify().equals("")){
            NotifyDialog();
        }else {

        }







        //



    }

    private void onClick() {
        llChangeIMEI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imeiAlert();
            }
        });

        llReferEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashBoardActivity.this,ReferEarnActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        llAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCheckforAttendance();
            }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlMain.openDrawer(Gravity.LEFT);
            }
        });

        dlMain.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                mslideState = true;

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                mslideState = false;

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        llSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefManager.getUserTypeId().equals("IFBMM1000011") || prefManager.getUserTypeId().equals("IFBUT1000135") || prefManager.getUserTypeId().equals("IFBUT1000134") || prefManager.getUserTypeId().equals("IFBUT1000133")|| prefManager.getUserTypeId().equals("FBMM1000004")|| prefManager.getUserTypeId().equals("IFBUT1000136")) {

                    loginCheckforSale();

                } else if (prefManager.getUserTypeId().equals("IFBUT1000127")) {
                    if (connectionCheck.isGPSEnabled()) {
                        Intent intent = new Intent(DashBoardActivity.this, TLSalesDashBoardActivity.class);
                        startActivity(intent);
                    } else {
                        connectionCheck.getNetworkActiveAlert();
                        Toast.makeText(getApplicationContext(), "Please enable your GPS loaction", Toast.LENGTH_LONG).show();
                    }
                } else if (prefManager.getUserTypeId().equals("IFBMM1000007")) {
                    if (connectionCheck.isGPSEnabled()) {
                        Intent intent = new Intent(DashBoardActivity.this, TSRSalesDashboardActivity.class);
                        startActivity(intent);
                    } else {
                        connectionCheck.getNetworkActiveAlert();
                        Toast.makeText(getApplicationContext(), "Please enable your GPS loaction", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (connectionCheck.isGPSEnabled()) {
                        Intent intent = new Intent(DashBoardActivity.this, OtherSalesDashBoardActivity.class);
                        startActivity(intent);
                    } else {
                        connectionCheck.getNetworkActiveAlert();
                        Toast.makeText(getApplicationContext(), "Please enable your GPS loaction", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });



        llQA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, QAReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        llCsrIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, CSRIssueDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        llMatrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCheckforDisplay();
            }
        });

        llHelpDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelpDeskBrowser();
            }
        });


        llQueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, QueriesActivity.class);
                startActivity(intent);
            }
        });

        llELearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, ELearningActivity.class);
                startActivity(intent);
            }
        });
        llDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, DocDashBaordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        llIQueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, IQueriesDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        llECatelougge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, ECatelogActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        llDailyLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, DailyLogDashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                prefManager.saveRemberFlag("2");
            }
        });
    }


    public void loginCheckforAttendance() {
        byte[] data = new byte[0];
        try {
            data = prefManager.getPassword().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT).replaceAll("\\s+", "");;

        String surl =  AppController.APIURL+"api/GCLAuthenticateWithEncryption?LoginID=" +prefManager.getUserCode() + "&password=" +base64+"&IMEI=0&SecurityCode=" + prefManager.getSecurityCode() + "&DeviceID=0&DeviceType="+version;
        Log.d("inputLogin", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
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
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                // Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String UserName = obj.optString("UserName");
                                    prefManager.saveEmpName(UserName);
                                    String LastLogin = obj.optString("LastLogin");
                                    prefManager.saveLoginTime(LastLogin);
                                    String Counter= obj.optString("Counter");
                                    prefManager.saveCounter(Counter);
                                    String BranchId = obj.optString("BranchId");
                                    prefManager.saveBranchId(BranchId);
                                    String UserTypeId = obj.optString("UserTypeId");
                                    prefManager.saveUserTypeId(UserTypeId);
                                    String ClientID = obj.optString("ClientID");
                                    prefManager.saveClintId(ClientID);
                                    String ConsultantID = obj.optString("ConsultantID");
                                    String UserID = obj.optString("UserID");
                                    prefManager.saveUserId(UserID);
                                    String MasterID = obj.optString("MasterID");
                                    prefManager.saveMasterId(MasterID);
                                    String Target = obj.optString("Target");
                                    prefManager.saveTarget(Target);
                                    String Pending = obj.optString("Pending");
                                    prefManager.savePending(Pending);
                                    String MonthlyTarget = obj.optString("MonthlyTarget");
                                    prefManager.saveMonthlyTarget(MonthlyTarget);
                                    String Sold=obj.optString("Sold");
                                    prefManager.saveSold(Sold);
                                    String Approved=obj.optString("Approved");
                                    prefManager.saveApproved(Approved);
                                    String SecurityCode = obj.optString("SecurityCode");
                                    prefManager.saveSecurityCode(SecurityCode);
                                    String Password = obj.optString("Password");
                                    prefManager.savePassword(Password);
                                    String WebSalesURL = obj.optString("WebSalesURL");
                                    prefManager.saveWebSales(WebSalesURL);
                                    String Code=obj.optString("Code");
                                    prefManager.saveUserCode(Code);
                                    String ZoneID=obj.optString("ZoneID");
                                    prefManager.saveZoneId(ZoneID);
                                    String HRDeskURL=obj.optString("HRDeskURL");
                                    prefManager.saveHRDeskURL(HRDeskURL);
                                    String ManualURL=obj.optString("ManualURL");
                                    prefManager.saveManualURL(ManualURL);
                                    String LeaveURL=obj.optString("LeaveURL");
                                    prefManager.saveLeaveURL(LeaveURL);
                                    String LeaveEncahURL=obj.optString("LeaveEncahURL");
                                    prefManager.saveLeaveEncahURL(LeaveEncahURL);
                                    String DigitalDocFlag=obj.optString("DigitalDocFlag");
                                    prefManager.saveDocFlag(DigitalDocFlag);
                                    String DailyActivityFlag=obj.optString("DailyActivityFlag");
                                    prefManager.saveDailyLogFlag(DailyActivityFlag);
                                    String CustomerVisitFlag=obj.optString("CustomerVisitFlag");
                                    prefManager.saveCVFlag(CustomerVisitFlag);
                                    String SalesInvCopyImgFlag=obj.optString("SalesInvCopyImgFlag");
                                    prefManager.saveInvoiceFlag(SalesInvCopyImgFlag);
                                    prefManager.saveRemberFlag("1");
                                    String SubDearlerType=obj.optString("SubDearlerType");
                                    prefManager.saveSubDealerType(SubDearlerType);



                                }
                               checkBersionForAttenDance();

                            } else {
                                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                //  Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    public void loginCheckforSale() {
        byte[] data = new byte[0];
        try {
            data = prefManager.getPassword().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT).replaceAll("\\s+", "");;

        String surl =  AppController.APIURL+"api/GCLAuthenticateWithEncryption?LoginID=" +prefManager.getUserCode() + "&password=" +base64+"&IMEI=0&SecurityCode=" + prefManager.getSecurityCode() + "&DeviceID=0&DeviceType="+version;
        Log.d("inputLogin", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
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
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                // Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String UserName = obj.optString("UserName");
                                    prefManager.saveEmpName(UserName);
                                    String LastLogin = obj.optString("LastLogin");
                                    prefManager.saveLoginTime(LastLogin);
                                    String Counter= obj.optString("Counter");
                                    prefManager.saveCounter(Counter);
                                    String BranchId = obj.optString("BranchId");
                                    prefManager.saveBranchId(BranchId);
                                    String UserTypeId = obj.optString("UserTypeId");
                                    prefManager.saveUserTypeId(UserTypeId);
                                    String ClientID = obj.optString("ClientID");
                                    prefManager.saveClintId(ClientID);
                                    String ConsultantID = obj.optString("ConsultantID");
                                    String UserID = obj.optString("UserID");
                                    prefManager.saveUserId(UserID);
                                    String MasterID = obj.optString("MasterID");
                                    prefManager.saveMasterId(MasterID);
                                    String Target = obj.optString("Target");
                                    prefManager.saveTarget(Target);
                                    String Pending = obj.optString("Pending");
                                    prefManager.savePending(Pending);
                                    String MonthlyTarget = obj.optString("MonthlyTarget");
                                    prefManager.saveMonthlyTarget(MonthlyTarget);
                                    String Sold=obj.optString("Sold");
                                    prefManager.saveSold(Sold);
                                    String Approved=obj.optString("Approved");
                                    prefManager.saveApproved(Approved);
                                    String SecurityCode = obj.optString("SecurityCode");
                                    prefManager.saveSecurityCode(SecurityCode);
                                    String Password = obj.optString("Password");
                                    prefManager.savePassword(Password);
                                    String WebSalesURL = obj.optString("WebSalesURL");
                                    prefManager.saveWebSales(WebSalesURL);
                                    String Code=obj.optString("Code");
                                    prefManager.saveUserCode(Code);
                                    String ZoneID=obj.optString("ZoneID");
                                    prefManager.saveZoneId(ZoneID);
                                    String HRDeskURL=obj.optString("HRDeskURL");
                                    prefManager.saveHRDeskURL(HRDeskURL);
                                    String ManualURL=obj.optString("ManualURL");
                                    prefManager.saveManualURL(ManualURL);
                                    String LeaveURL=obj.optString("LeaveURL");
                                    prefManager.saveLeaveURL(LeaveURL);
                                    String LeaveEncahURL=obj.optString("LeaveEncahURL");
                                    prefManager.saveLeaveEncahURL(LeaveEncahURL);
                                    String DigitalDocFlag=obj.optString("DigitalDocFlag");
                                    prefManager.saveDocFlag(DigitalDocFlag);
                                    String DailyActivityFlag=obj.optString("DailyActivityFlag");
                                    prefManager.saveDailyLogFlag(DailyActivityFlag);
                                    String CustomerVisitFlag=obj.optString("CustomerVisitFlag");
                                    prefManager.saveCVFlag(CustomerVisitFlag);
                                    String SalesInvCopyImgFlag=obj.optString("SalesInvCopyImgFlag");
                                    prefManager.saveInvoiceFlag(SalesInvCopyImgFlag);
                                    prefManager.saveRemberFlag("1");
                                    String SubDearlerType=obj.optString("SubDearlerType");
                                    prefManager.saveSubDealerType(SubDearlerType);
                                    String SalesPartyCode=obj.optString("SalesPartyCode");
                                    prefManager.saveSalesPartyCode(SalesPartyCode);


                                }
                                checkBersionForSale();

                            } else {
                                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                //  Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashBoardActivity.this);
        requestQueue.add(stringRequest);

    }


    public void getIMEINumber() {

        String surl =  AppController.APIURL+"api/get_EmployeeMobileIMEI?Code=" +prefManager.getUserCode() + "&Operation=1&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputLogin", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loding.....");
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
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            JSONArray responseData=job1.optJSONArray("responseData");
                            JSONObject imeiOBJ=responseData.optJSONObject(0);
                            oldIMEI=imeiOBJ.optString("IMEI");

                            getIMEIRequestDetails();


                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                //  Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    public void getIMEIRequestDetails() {

        String surl =  AppController.APIURL+"api/get_EmployeeMobileIMEI?Code=" +prefManager.getUserCode() + "&Operation=2&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputLogin", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loding.....");
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
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus){
                                changeScreen=true;
                                JSONArray responseData=job1.optJSONArray("responseData");
                                for (int i=0;i<responseData.length();i++){
                                    JSONObject imeiOBJ=responseData.optJSONObject(i);
                                    String IMEI=imeiOBJ.optString("IMEI");
                                    String RequestedOn=imeiOBJ.optString("RequestedOn");
                                    String StatusName=imeiOBJ.optString("StatusName");
                                    IMEIReqModel imeiReqModel=new IMEIReqModel();
                                    imeiReqModel.setReqDetails(RequestedOn);
                                    imeiReqModel.setApprovalStatus(StatusName);
                                    imeiReqModel.setImeiNumber(IMEI);
                                    imeiList.add(imeiReqModel);

                                }

                            }else {
                                changeScreen=false;
                            }



                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                //  Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    public void loginCheckforDisplay() {
        byte[] data = new byte[0];
        try {
            data = prefManager.getPassword().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT).replaceAll("\\s+", "");;

        String surl =  AppController.APIURL+"api/GCLAuthenticateWithEncryption?LoginID=" +prefManager.getUserCode() + "&password=" +base64+"&IMEI=0&SecurityCode=" + prefManager.getSecurityCode() + "&DeviceID=0&DeviceType="+version;
        Log.d("inputLogin", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
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
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                // Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String UserName = obj.optString("UserName");
                                    prefManager.saveEmpName(UserName);
                                    String LastLogin = obj.optString("LastLogin");
                                    prefManager.saveLoginTime(LastLogin);
                                    String Counter= obj.optString("Counter");
                                    prefManager.saveCounter(Counter);
                                    String BranchId = obj.optString("BranchId");
                                    prefManager.saveBranchId(BranchId);
                                    String UserTypeId = obj.optString("UserTypeId");
                                    prefManager.saveUserTypeId(UserTypeId);
                                    String ClientID = obj.optString("ClientID");
                                    prefManager.saveClintId(ClientID);
                                    String ConsultantID = obj.optString("ConsultantID");
                                    String UserID = obj.optString("UserID");
                                    prefManager.saveUserId(UserID);
                                    String MasterID = obj.optString("MasterID");
                                    prefManager.saveMasterId(MasterID);
                                    String Target = obj.optString("Target");
                                    prefManager.saveTarget(Target);
                                    String Pending = obj.optString("Pending");
                                    prefManager.savePending(Pending);
                                    String MonthlyTarget = obj.optString("MonthlyTarget");
                                    prefManager.saveMonthlyTarget(MonthlyTarget);
                                    String Sold=obj.optString("Sold");
                                    prefManager.saveSold(Sold);
                                    String Approved=obj.optString("Approved");
                                    prefManager.saveApproved(Approved);
                                    String SecurityCode = obj.optString("SecurityCode");
                                    prefManager.saveSecurityCode(SecurityCode);
                                    String Password = obj.optString("Password");
                                    prefManager.savePassword(Password);
                                    String WebSalesURL = obj.optString("WebSalesURL");
                                    prefManager.saveWebSales(WebSalesURL);
                                    String Code=obj.optString("Code");
                                    prefManager.saveUserCode(Code);
                                    String ZoneID=obj.optString("ZoneID");
                                    prefManager.saveZoneId(ZoneID);
                                    String HRDeskURL=obj.optString("HRDeskURL");
                                    prefManager.saveHRDeskURL(HRDeskURL);
                                    String ManualURL=obj.optString("ManualURL");
                                    prefManager.saveManualURL(ManualURL);
                                    String LeaveURL=obj.optString("LeaveURL");
                                    prefManager.saveLeaveURL(LeaveURL);
                                    String LeaveEncahURL=obj.optString("LeaveEncahURL");
                                    prefManager.saveLeaveEncahURL(LeaveEncahURL);
                                    String DigitalDocFlag=obj.optString("DigitalDocFlag");
                                    prefManager.saveDocFlag(DigitalDocFlag);
                                    String DailyActivityFlag=obj.optString("DailyActivityFlag");
                                    prefManager.saveDailyLogFlag(DailyActivityFlag);
                                    String CustomerVisitFlag=obj.optString("CustomerVisitFlag");
                                    prefManager.saveCVFlag(CustomerVisitFlag);
                                    String SalesInvCopyImgFlag=obj.optString("SalesInvCopyImgFlag");
                                    prefManager.saveInvoiceFlag(SalesInvCopyImgFlag);
                                    prefManager.saveRemberFlag("1");
                                    String SubDearlerType=obj.optString("SubDearlerType");
                                    prefManager.saveSubDealerType(SubDearlerType);



                                }
                                checkBersionForDisplay();

                            } else {
                                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                //  Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    public void loginCheckforCall() {
        byte[] data = new byte[0];
        try {
            data = prefManager.getPassword().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT).replaceAll("\\s+", "");;

        String surl =  AppController.APIURL+"api/GCLAuthenticateWithEncryption?LoginID=" +prefManager.getUserCode() + "&password=" +base64+"&IMEI=0&SecurityCode=" + prefManager.getSecurityCode() + "&DeviceID=0&DeviceType="+version;
        Log.d("inputLogin", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
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
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                // Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String UserName = obj.optString("UserName");
                                    prefManager.saveEmpName(UserName);
                                    String LastLogin = obj.optString("LastLogin");
                                    prefManager.saveLoginTime(LastLogin);
                                    String Counter= obj.optString("Counter");
                                    prefManager.saveCounter(Counter);
                                    String BranchId = obj.optString("BranchId");
                                    prefManager.saveBranchId(BranchId);
                                    String UserTypeId = obj.optString("UserTypeId");
                                    prefManager.saveUserTypeId(UserTypeId);
                                    String ClientID = obj.optString("ClientID");
                                    prefManager.saveClintId(ClientID);
                                    String ConsultantID = obj.optString("ConsultantID");
                                    String UserID = obj.optString("UserID");
                                    prefManager.saveUserId(UserID);
                                    String MasterID = obj.optString("MasterID");
                                    prefManager.saveMasterId(MasterID);
                                    String Target = obj.optString("Target");
                                    prefManager.saveTarget(Target);
                                    String Pending = obj.optString("Pending");
                                    prefManager.savePending(Pending);
                                    String MonthlyTarget = obj.optString("MonthlyTarget");
                                    prefManager.saveMonthlyTarget(MonthlyTarget);
                                    String Sold=obj.optString("Sold");
                                    prefManager.saveSold(Sold);
                                    String Approved=obj.optString("Approved");
                                    prefManager.saveApproved(Approved);
                                    String SecurityCode = obj.optString("SecurityCode");
                                    prefManager.saveSecurityCode(SecurityCode);
                                    String Password = obj.optString("Password");
                                    prefManager.savePassword(Password);
                                    String WebSalesURL = obj.optString("WebSalesURL");
                                    prefManager.saveWebSales(WebSalesURL);
                                    String Code=obj.optString("Code");
                                    prefManager.saveUserCode(Code);
                                    String ZoneID=obj.optString("ZoneID");
                                    prefManager.saveZoneId(ZoneID);
                                    String HRDeskURL=obj.optString("HRDeskURL");
                                    prefManager.saveHRDeskURL(HRDeskURL);
                                    String ManualURL=obj.optString("ManualURL");
                                    prefManager.saveManualURL(ManualURL);
                                    String LeaveURL=obj.optString("LeaveURL");
                                    prefManager.saveLeaveURL(LeaveURL);
                                    String LeaveEncahURL=obj.optString("LeaveEncahURL");
                                    prefManager.saveLeaveEncahURL(LeaveEncahURL);
                                    String DigitalDocFlag=obj.optString("DigitalDocFlag");
                                    prefManager.saveDocFlag(DigitalDocFlag);
                                    String DailyActivityFlag=obj.optString("DailyActivityFlag");
                                    prefManager.saveDailyLogFlag(DailyActivityFlag);
                                    String CustomerVisitFlag=obj.optString("CustomerVisitFlag");
                                    prefManager.saveCVFlag(CustomerVisitFlag);
                                    String SalesInvCopyImgFlag=obj.optString("SalesInvCopyImgFlag");
                                    prefManager.saveInvoiceFlag(SalesInvCopyImgFlag);
                                    prefManager.saveRemberFlag("1");




                                }
                                checkBersionForCall();

                            } else {
                                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                //  Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    private void openHelpDeskBrowser() {
        Uri uri = Uri.parse(helpdeskurl); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (helpdeskurl.equals("")) {

        } else {
            startActivity(intent);
        }
    }


    private void openManualBrowser() {
        Uri uri = Uri.parse(manual); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (manual.equals("")) {

        } else {
            startActivity(intent);
        }
    }

    private void checkBersionForAttenDance() {
        String surl =  AppController.APIURL+"api/ApkVersionChecking";
        final ProgressDialog pd = new ProgressDialog(DashBoardActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLeave", response);
                        pd.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            JSONArray responseData = job1.optJSONArray("responseData");
                            for (int i = 0; i < responseData.length(); i++) {
                                JSONObject obj = responseData.getJSONObject(i);
                                String IFBVersion = obj.optString("IFBVersion");
                                String IFBMandatory = obj.optString("IFBMandatory");
                                if (IFBVersion.equals(version)){
                                   todoAlert();

                                } else {
                                    if (IFBMandatory.equals("Y")) {
                                        upDateAlert(IFBVersion);
                                    }else {
                                        todoAlert();
                                    }
                                }


                            }


                            // boolean _status = job1.getBoolean("status")

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DashBoardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                //Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();

                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    private void checkBersionForSale() {
        String surl =  AppController.APIURL+"api/ApkVersionChecking";
        final ProgressDialog pd = new ProgressDialog(DashBoardActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLeave", response);
                        pd.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            JSONArray responseData = job1.optJSONArray("responseData");
                            for (int i = 0; i < responseData.length(); i++) {
                                JSONObject obj = responseData.getJSONObject(i);
                                String IFBVersion = obj.optString("IFBVersion");
                                String IFBMandatory = obj.optString("IFBMandatory");
                                if (IFBVersion.equals(version)){
                                    Intent intent = new Intent(DashBoardActivity.this, SalesDashboardActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                } else {
                                    if (IFBMandatory.equals("Y")) {
                                        upDateAlert(IFBVersion);
                                    }else {
                                        Intent intent = new Intent(DashBoardActivity.this, SalesDashboardActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }


                            }

                            // boolean _status = job1.getBoolean("status")

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DashBoardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                //Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();

                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    private void checkBersionForDisplay() {
        String surl =  AppController.APIURL+"api/ApkVersionChecking";
        final ProgressDialog pd = new ProgressDialog(DashBoardActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLeave", response);
                        pd.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            JSONArray responseData = job1.optJSONArray("responseData");
                            for (int i = 0; i < responseData.length(); i++) {
                                JSONObject obj = responseData.getJSONObject(i);
                                String IFBVersion = obj.optString("IFBVersion");
                                String IFBMandatory = obj.optString("IFBMandatory");
                                if (IFBVersion.equals(version)){
                                    Intent intent = new Intent(DashBoardActivity.this, DisplayMatrixDashBoardActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                } else {
                                    upDateAlert(IFBVersion);
                                }


                            }

                            // boolean _status = job1.getBoolean("status")

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DashBoardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                //Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();

                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    private void checkBersionForCall() {
        String surl =  AppController.APIURL+"api/ApkVersionChecking";
        final ProgressDialog pd = new ProgressDialog(DashBoardActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLeave", response);
                        pd.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            JSONArray responseData = job1.optJSONArray("responseData");
                            for (int i = 0; i < responseData.length(); i++) {
                                JSONObject obj = responseData.getJSONObject(i);
                                String IFBVersion = obj.optString("IFBVersion");
                                String IFBMandatory = obj.optString("IFBMandatory");
                                if (IFBVersion.equals(version)){


                                } else {
                                    upDateAlert(IFBVersion);
                                }


                            }

                            // boolean _status = job1.getBoolean("status")

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DashBoardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                //Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();

                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DashBoardActivity.this);
        requestQueue.add(stringRequest);

    }

    private void upDateAlert(String updateVersion){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DashBoardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_update_alert, null);
        dialogBuilder.setView(dialogView);
        Button btnOk=(Button)dialogView.findViewById(R.id.btnOk);
        TextView tvUpdateVersion=(TextView)dialogView.findViewById(R.id.tvUpdateVersion);
        tvUpdateVersion.setText("New Version "+updateVersion+" is available in Play Store");
        TextView tvCurrentVersion=(TextView)dialogView.findViewById(R.id.tvCurrentVersion);
        tvCurrentVersion.setText("Current App Version is "+version);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
                al1.dismiss();



            }
        });



        al1 = dialogBuilder.create();
        al1.setCancelable(false);
        Window window = al1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        al1.show();
    }

    private void todoAlert(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DashBoardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_attendnace_todo, null);
        dialogBuilder.setView(dialogView);
        Button btnOk=(Button)dialogView.findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, AttendanceDashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                al2.dismiss();
            }
        });


        al2 = dialogBuilder.create();
        al2.setCancelable(true);
        Window window = al2.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        al2.show();
    }

    private void imeiAlert(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DashBoardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_imei, null);
        dialogBuilder.setView(dialogView);
        TextView tvExistingNumber=(TextView)dialogView.findViewById(R.id.tvExistingNumber);
        tvExistingNumber.setText(oldIMEI);
        TextView tvCurrentNumber=(TextView)dialogView.findViewById(R.id.tvCurrentNumber);
        tvCurrentNumber.setText(androidID);
        ImageView imgCancel=(ImageView)dialogView.findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imeialert.dismiss();
            }
        });

        final EditText etReason=(EditText)dialogView.findViewById(R.id.etReason);
        Button btnSend=(Button)dialogView.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etReason.getText().toString().length()>0){
                    sendIMEIReq(etReason.getText().toString());
                    imeialert.dismiss();
                }else {
                    Toast.makeText(DashBoardActivity.this,"Please Enter Your Reason",Toast.LENGTH_LONG).show();
                }

            }
        });

        Button btnReqDetails=(Button)dialogView.findViewById(R.id.btnReqDetails);
        if (changeScreen){
            btnReqDetails.setVisibility(View.VISIBLE);
        }else {
            btnReqDetails.setVisibility(View.GONE);
        }

        btnReqDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imeiReqAlert();
                imeialert.dismiss();

            }
        });





        imeialert = dialogBuilder.create();
        imeialert.setCancelable(true);
        Window window = imeialert.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        imeialert.show();
    }

    private void imeiReqAlert(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DashBoardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_request_imei, null);
        dialogBuilder.setView(dialogView);

        ImageView imgCancel=(ImageView)dialogView.findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imeireqalert.dismiss();
            }
        });

        RecyclerView rvIMEI=(RecyclerView)dialogView.findViewById(R.id.rvIMEI);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.VERTICAL, false);
        rvIMEI.setLayoutManager(layoutManager);

        ImeiReqAdapter imeiAdapter=new ImeiReqAdapter(imeiList,DashBoardActivity.this);
        rvIMEI.setAdapter(imeiAdapter);








        imeireqalert = dialogBuilder.create();
        imeireqalert.setCancelable(true);
        Window window = imeireqalert.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        imeireqalert.show();
    }


    private void cusSOPAlert(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DashBoardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_customer_sop, null);
        dialogBuilder.setView(dialogView);

        ImageView imgCancel=(ImageView)dialogView.findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cusSOPalert.dismiss();
            }
        });


        ImageView imgSOP=(ImageView)dialogView.findViewById(R.id.imgSOP);


        try {
            Picasso.with(DashBoardActivity.this)
                    .load(prefManager.getCustomerSOPImage()).error(R.drawable.noimage)
                    .into(imgSOP);
        } catch (Exception e) {
            e.printStackTrace();
        }






        cusSOPalert = dialogBuilder.create();
        cusSOPalert.setCancelable(true);
        Window window = cusSOPalert.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        cusSOPalert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStop() {
        if (mAppUpdateManager!=null){
            // mAppUpdateManager.registerListener(installStateUpdatedListener);
        }
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==RC_APP_UPDATE && resultCode !=RESULT_OK){

        }
        super.onActivityResult(requestCode, resultCode, data);
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
                        mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,DashBoardActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        super.onResume();
    }

    public void RateApp(final Context mContext) {
        try {
            final ReviewManager manager = ReviewManagerFactory.create(mContext);
            manager.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
                @Override
                public void onComplete(@NonNull Task<ReviewInfo> task) {
                    if(task.isSuccessful()){
                        ReviewInfo reviewInfo = task.getResult();
                        manager.launchReviewFlow((Activity) mContext, reviewInfo).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                //  Toast.makeText(mContext, "Rating Failed", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // Toast.makeText(mContext, "Review Completed, Thank You!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    //  Toast.makeText(mContext, "In-App Request Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void sendIMEIReq(String reason) {

        final ProgressDialog pd = new ProgressDialog(DashBoardActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload( AppController.APIURL+"api/post_EmployeeMobileIMEIChnageRequest")
                .addMultipartParameter("Code", prefManager.getUserCode())
                .addMultipartParameter("IMEI", androidID)
                .addMultipartParameter("OLDIMEI", oldIMEI)
                .addMultipartParameter("UserID", prefManager.getUserId())
                .addMultipartParameter("Remarks", reason)
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

                            pd.dismiss();
                            Toast.makeText(DashBoardActivity.this,responseText,Toast.LENGTH_LONG).show();
                        }else {
                            pd.dismiss();
                            Toast.makeText(DashBoardActivity.this,responseText,Toast.LENGTH_LONG).show();

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


    private void NotifyDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DashBoardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_notify, null);
        dialogBuilder.setView(dialogView);
        TextView tvNotifyRemarks=(TextView)dialogView.findViewById(R.id.tvNotifyRemarks);
        TextView tvNotifyURL=(TextView)dialogView.findViewById(R.id.tvNotifyURL);
        tvNotifyRemarks.setText(prefManager.getNotify());
        tvNotifyURL.setText(prefManager.getNotifyUrl());
        tvNotifyURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prefManager.getNotifyUrl().equalsIgnoreCase("")){

                }else {
                    Uri uri = Uri.parse(prefManager.getNotifyUrl()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });
        ImageView imgCancel=(ImageView)dialogView.findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyALert.dismiss();
            }
        });



        notifyALert = dialogBuilder.create();
        notifyALert.setCancelable(true);
        Window window = notifyALert.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        notifyALert.show();
    }
}
