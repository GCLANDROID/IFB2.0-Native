package io.cordova.ifb.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


import io.cordova.ifb.R;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.NetworkConnectionCheck;
import io.cordova.ifb.utility.PrefManager;

public class LoginActivity extends AppCompatActivity {
    TextView llLogin;
    EditText etSceurityCode, etUserName, etPassword;
    String version = "";
    AlertDialog alertDialog,al1;
    NetworkConnectionCheck connectionCheck;
    PrefManager prefManager;
    String year;
    String month;
    CheckBox ckRemeber;
    String refreshedToken;
    String playversion;
    String secerutycodde;
    LinearLayout llLoader,llMain,llAgain;
    String phnonenumber;
    boolean responseStatus;
    String android_id;
    int verCode;
    String IFBMandatory,IFBVersion;
    TextView tvShow, tvHide;


    //  नाम
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        initialize();
        checkBersion();
        onClick();
    }

    @SuppressLint("MissingPermission")
    private void initialize() {
        prefManager = new PrefManager(LoginActivity.this);
        connectionCheck = new NetworkConnectionCheck(LoginActivity.this);
        llLogin = (TextView) findViewById(R.id.btnLogin);
        etSceurityCode = (EditText) findViewById(R.id.etSceurityCode);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
             verCode = pInfo.versionCode;
            Log.d("sddk", version);
            Log.d("sdkl", String.valueOf(version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ckRemeber = (CheckBox) findViewById(R.id.ckRemember);

            etUserName.setText(prefManager.getUserCode());
            etPassword.setText(prefManager.getPassword());
            etSceurityCode.setText(prefManager.getSecurityCode());


        secerutycodde=etSceurityCode.getText().toString().toUpperCase();
        llAgain=(LinearLayout)findViewById(R.id.llAgain);
        llLoader=(LinearLayout)findViewById(R.id.llLoader);
        llMain=(LinearLayout)findViewById(R.id.llMain);
        phnonenumber = "okkk";
        refreshedToken="1000";




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
            android_id = telephonyManager.getDeviceId();
        }else {
            android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }

        tvShow = (TextView) findViewById(R.id.tvShow);
        tvHide = (TextView) findViewById(R.id.tvHide);




    }

    private void onClick() {
        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHide.setVisibility(View.VISIBLE);
                tvShow.setVisibility(View.GONE);
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            }
        });

        tvHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvShow.setVisibility(View.VISIBLE);
                tvHide.setVisibility(View.GONE);
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        });

        etSceurityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etSceurityCode.getText().toString().length()>0){
                    secerutycodde=etSceurityCode.getText().toString().toUpperCase();
                    Log.d("secerutycodde",secerutycodde);
                }

            }
        });



        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUserName.getText().toString().length() > 0) {
                    if (etPassword.getText().toString().length() > 0) {
                        if (etSceurityCode.getText().toString().length() > 0) {
                            if (connectionCheck.isNetworkAvailable()) {
                                if (version.equals(IFBVersion)) {

                                        loginFunction();


                                }else {
                                    if (IFBMandatory.equals("Y")) {
                                        upDateAlert(IFBVersion);
                                        Toast.makeText(getApplicationContext(), "Please update your app", Toast.LENGTH_LONG).show();
                                    }else {
                                        loginFunction();
                                    }
                                }
                            } else {
                                connectionCheck.getNetworkActiveAlert().show();
                            }

                        } else {
                            etSceurityCode.requestFocus();
                            etSceurityCode.setError("Please enter securitycode");
                        }

                    } else {
                        etPassword.requestFocus();
                        etPassword.setError("Please enter password");
                    }

                } else {
                    etUserName.requestFocus();
                    etUserName.setError("Please enter User Name");
                }

            }
        });

        ckRemeber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    prefManager.saveRemberFlag("1");
                } else {
                    prefManager.saveRemberFlag("2");
                }
            }
        });
    }

    public void loginFunction() {
        byte[] data = new byte[0];
        try {
            data = etPassword.getText().toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT).replaceAll("\\s+", "");;

        String surl = AppController.APIURL+ "api/GCLAuthenticateWithEncryption?LoginID=" + etUserName.getText().toString() + "&password=" +base64+"&IMEI="+android_id +"&SecurityCode=" + secerutycodde + "&DeviceID="+refreshedToken +"&DeviceType="+version;
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
                                    prefManager.saveMasterId(etUserName.getText().toString());
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

                                    String Rejected=obj.optString("Rejected");
                                    prefManager.saveRejected(Rejected);

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

                                    String CustomerSop=obj.optString("CustomerSop");
                                    prefManager.saveCustomerSOPImage(CustomerSop);


                                    String Notify_Remarks=obj.optString("Notify_Remarks");
                                    prefManager.saveNotify(Notify_Remarks);


                                    String Notify_URL=obj.optString("Notify_URL");
                                    prefManager.saveNotifyUrl(Notify_URL);

                                    String SalesPointID=obj.optString("SalesPointID");
                                    prefManager.saveSalesPointID(SalesPointID);
                                   // Firebase(UserName);
                                }
                                if (prefManager.getSecurityCode().equalsIgnoreCase("IND") || prefManager.getSecurityCode().equalsIgnoreCase("NAPS") ){
                                    Intent intent = new Intent(LoginActivity.this, INDDashbaordActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else if (prefManager.getUserTypeId().equals("IFBUT1000127")){
                                    /*Intent intent = new Intent(LoginActivity.this, KAEnqueryActivity.class);
                                    startActivity(intent);
                                    finish();*/

                                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else if (prefManager.getUserTypeId().equals("IFBMM1000011") || prefManager.getUserTypeId().equals("IFBUT1000135") || prefManager.getUserTypeId().equals("IFBUT1000134") || prefManager.getUserTypeId().equals("IFBUT1000133")|| prefManager.getUserTypeId().equals("FBMM1000004")|| prefManager.getUserTypeId().equals("IFBUT1000136")){
                                    /*Intent intent = new Intent(LoginActivity.this, KAEnqueryActivity.class);
                                    startActivity(intent);
                                    finish();*/

                                    /*Intent intent = new Intent(LoginActivity.this, NewDashboardActivity.class);
                                    startActivity(intent);
                                    finish();*/
                                    if (etPassword.getText().toString().equalsIgnoreCase("password")){
                                        Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Intent intent = new Intent(LoginActivity.this, NewDashboardActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }


                                }else {
                                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                shoeDialog();

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
                Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                showAlert();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);

    }



    private void shoeDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.credential_dialog, null);
        dialogBuilder.setView(dialogView);
        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog.show();
    }

    private void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Somthing went wrong");
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        alertDialogBuilder.show();


    }

    private void checkBersion() {
        String surl = AppController.APIURL+"api/ApkVersionChecking";
       llLoader.setVisibility(View.VISIBLE);
       llMain.setVisibility(View.GONE);
       llAgain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLeave", response);
                        llLoader.setVisibility(View.GONE);
                        llMain.setVisibility(View.VISIBLE);
                        llAgain.setVisibility(View.GONE);

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            boolean responseStatus=job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    IFBVersion = obj.optString("IFBVersion");
                                    IFBMandatory = obj.optString("IFBMandatory");


                                }
                            }





                            // boolean _status = job1.getBoolean("status")

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                llAgain.setVisibility(View.VISIBLE);
                //Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();

                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);

    }

    private void upDateAlert(String updateVersion){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this, R.style.CustomDialogNew);
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
                deleteAppData();



            }
        });

        Button btnSkip=(Button)dialogView.findViewById(R.id.btnSkip);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFunction();
            }
        });

        al1 = dialogBuilder.create();
        al1.setCancelable(false);
        Window window = al1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        al1.show();
    }

    private void deleteAppData() {
        try {
            // clearing app data
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
