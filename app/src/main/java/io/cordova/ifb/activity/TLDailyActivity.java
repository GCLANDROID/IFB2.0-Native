package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.GPSTracker;
import io.cordova.ifb.utility.PostDisplayMatrixService;
import io.cordova.ifb.utility.PrefManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TLDailyActivity extends AppCompatActivity {
    TextView tvDateName, tvCounter;
    AlertDialog alertDialog;
    LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8, ll9, ll10, ll11, ll12, ll13;
    ImageView imgTick1, imgTick2, imgTick3, imgTick4, imgTick5, imgTick6, imgTick7, imgTick8, imgTick9, imgTick10, imgTick11, imgTick12, imgTick13;
    String caption1 = "0";
    String caption2 = "0";
    String caption3 = "0";
    String caption4 = "0";
    String caption5 = "0";
    String captionid;
    EditText etDisplayMatrix, etSale, etPOP, etProduct, etTraing;
    String displaymatrix = "-" + "";
    String sale = "-" + "";
    String POP = "-" + "";
    String product = "-" + "";
    String training = "-" + "";
    Spinner spCounter;
    ArrayList<SpinnerItemModule> moduleCounter = new ArrayList<>();
    ArrayList<String> counterName = new ArrayList<>();
    PrefManager prefManager;
    TextView tvDate;
    LinearLayout llDate;
    String monthname;
    String nosaledate;
    Spinner spLocation;
    ArrayList<SpinnerItemModule> moduleLocation = new ArrayList<>();
    ArrayList<String> locationName = new ArrayList<>();
    String counterid = "";
    String locationid = "";
    String operation;
    String aemid;
    String countername = "";
    String remarks = "";
    EditText etCounter;
    EditText etRemarks;
    private static final String SERVER_PATH =  AppController.APIURL+"api/";
    private PostDisplayMatrixService uploadService;
    ProgressDialog progressDialog;
    Button btnSubmit;
    String month;
    String year,financialYear;
    String usertypeid;
    String securitycode;
    AlertDialog alerDialog1;
    ImageView imgBack,imgHome;
    int MY_SOCKET_TIMEOUT_MS=5000;
    GPSTracker gps;
    double latitude,longitude;
    String currlat,currlong,cuuaddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tldaily);
        shoeDialog();
        initialize();
        onClick();
    }

    private void initialize() {
        prefManager = new PrefManager(TLDailyActivity.this);
        OkHttpClient okHttpClient =
                AppController.getUnsafeOkHttpClient();
        AndroidNetworking.initialize(
                getApplicationContext(),
                okHttpClient
        );

        tvDateName = (TextView) findViewById(R.id.tvDateName);
        String next = "<font color='#EE0000'>*</font>";
        String datename = "Visited Date";
        tvDateName.setText(Html.fromHtml(datename + next));

        tvCounter = (TextView) findViewById(R.id.tvCounter);
        String counter = "Counter Name";
        tvCounter.setText(Html.fromHtml(counter + next));

        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        ll6 = (LinearLayout) findViewById(R.id.ll6);
        ll7 = (LinearLayout) findViewById(R.id.ll7);
        ll8 = (LinearLayout) findViewById(R.id.ll8);
        ll9 = (LinearLayout) findViewById(R.id.ll9);
        ll10 = (LinearLayout) findViewById(R.id.ll10);
        ll11 = (LinearLayout) findViewById(R.id.ll11);
        ll12 = (LinearLayout) findViewById(R.id.ll12);
        ll13 = (LinearLayout) findViewById(R.id.ll13);
        btnSubmit=(Button) findViewById(R.id.btnSubmit);


        imgTick1 = (ImageView) findViewById(R.id.imgTick1);
        imgTick2 = (ImageView) findViewById(R.id.imgTick2);
        imgTick3 = (ImageView) findViewById(R.id.imgTick3);
        imgTick4 = (ImageView) findViewById(R.id.imgTick4);
        imgTick5 = (ImageView) findViewById(R.id.imgTick5);
        imgTick6 = (ImageView) findViewById(R.id.imgTick6);
        imgTick7 = (ImageView) findViewById(R.id.imgTick7);
        imgTick8 = (ImageView) findViewById(R.id.imgTick8);
        imgTick9 = (ImageView) findViewById(R.id.imgTick9);
        imgTick10 = (ImageView) findViewById(R.id.imgTick10);
        imgTick11 = (ImageView) findViewById(R.id.imgTick11);
        imgTick12 = (ImageView) findViewById(R.id.imgTick12);
        imgTick13 = (ImageView) findViewById(R.id.imgTick13);


        etDisplayMatrix = (EditText) findViewById(R.id.etDisplayMatrix);
        etSale = (EditText) findViewById(R.id.etSale);
        etPOP = (EditText) findViewById(R.id.etPOP);
        etProduct = (EditText) findViewById(R.id.etProduct);
        etTraing = (EditText) findViewById(R.id.etTraing);

        llDate = (LinearLayout) findViewById(R.id.llDate);
        tvDate = (TextView) findViewById(R.id.tvDate);
        String dateStr = "04/05/2010";

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        nosaledate = df.format(c);
        tvDate.setText(nosaledate);


        captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
        Log.d("caption", captionid);

        spCounter = (Spinner) findViewById(R.id.spCounter);
        spLocation = (Spinner) findViewById(R.id.spLocation);
        setCounter();

        etCounter = (EditText) findViewById(R.id.etCounter);
        etRemarks = (EditText) findViewById(R.id.etRemarks);
        operation = "3";
        aemid = prefManager.getUserId();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Change base URL to your upload server URL.
        uploadService = (PostDisplayMatrixService) new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PostDisplayMatrixService.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");


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
        if(month.equals("January")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else if (month.equals("February")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else if (month.equals("March")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else {
            int futureyear = y + 1;
            financialYear = year+"-"+futureyear;
        }
        usertypeid=prefManager.getUserTypeId();
        securitycode=prefManager.getSecurityCode();

        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);




    }


    private void shoeDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TLDailyActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_approval, null);
        dialogBuilder.setView(dialogView);
        Button btnNow = (Button) dialogView.findViewById(R.id.btnNow);
        btnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        Button btnLate = (Button) dialogView.findViewById(R.id.btnLate);
        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TLDailyActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog.show();
    }

    private void onClick() {
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();

            }
        });

        etCounter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCounter.getText().toString().length() > 0) {
                    countername = etCounter.getText().toString();
                }

            }
        });

        etRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etRemarks.getText().toString().length() > 0) {
                    remarks = etRemarks.getText().toString();
                }

            }
        });

        spCounter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                counterid = moduleCounter.get(position).getItemId();
                Log.d("counterid", counterid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationid = moduleLocation.get(position).getItemId();
                Log.d("locationid", locationid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick1.setVisibility(View.VISIBLE);
                imgTick2.setVisibility(View.GONE);
                caption1 = "CDID00000001" + displaymatrix;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick1.setVisibility(View.GONE);
                imgTick2.setVisibility(View.VISIBLE);
                caption1 = "CDID00000002" + displaymatrix;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });

        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick3.setVisibility(View.VISIBLE);
                imgTick4.setVisibility(View.GONE);
                caption2 = "CDID00000003" + sale;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });

        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick3.setVisibility(View.GONE);
                imgTick4.setVisibility(View.VISIBLE);
                caption2 = "CDID00000004" + sale;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });

        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick5.setVisibility(View.VISIBLE);
                imgTick6.setVisibility(View.GONE);
                caption3 = "CDID00000005" + POP;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);

            }
        });

        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick5.setVisibility(View.GONE);
                imgTick6.setVisibility(View.VISIBLE);
                caption3 = "CDID00000006" + POP;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });

        ll7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick7.setVisibility(View.VISIBLE);
                imgTick8.setVisibility(View.GONE);
                imgTick9.setVisibility(View.GONE);
                imgTick10.setVisibility(View.GONE);
                caption4 = "CDID00000007" + product;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });

        ll8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick7.setVisibility(View.GONE);
                imgTick8.setVisibility(View.VISIBLE);
                imgTick9.setVisibility(View.GONE);
                imgTick10.setVisibility(View.GONE);
                caption4 = "CDID00000008" + product;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });

        ll9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick7.setVisibility(View.GONE);
                imgTick8.setVisibility(View.GONE);
                imgTick9.setVisibility(View.VISIBLE);
                imgTick10.setVisibility(View.GONE);
                caption4 = "CDID00000009" + product;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });

        ll10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick7.setVisibility(View.GONE);
                imgTick8.setVisibility(View.GONE);
                imgTick9.setVisibility(View.GONE);
                imgTick10.setVisibility(View.VISIBLE);
                caption4 = "CDID00000010" + product;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });

        ll11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick11.setVisibility(View.VISIBLE);
                imgTick12.setVisibility(View.GONE);
                imgTick13.setVisibility(View.GONE);
                caption5 = "CDID00000011" + training;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });

        ll12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick11.setVisibility(View.GONE);
                imgTick12.setVisibility(View.VISIBLE);
                imgTick13.setVisibility(View.GONE);
                caption5 = "CDID00000012" + training;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });

        ll13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick11.setVisibility(View.GONE);
                imgTick12.setVisibility(View.GONE);
                imgTick13.setVisibility(View.VISIBLE);
                caption5 = "CDID00000013" + training;
                captionid = caption1 + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                Log.d("caption", captionid);
            }
        });


        etDisplayMatrix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etDisplayMatrix.getText().toString().length() > 0) {
                    displaymatrix = etDisplayMatrix.getText().toString();
                    captionid = caption1 + displaymatrix + "," + caption2 + "," + caption3 + "," + caption4 + "," + caption5;
                    Log.d("caption", captionid);


                }

            }
        });

        etSale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etSale.getText().toString().length() > 0) {
                    sale = etSale.getText().toString();
                    captionid = caption1 + displaymatrix + "," + caption2 + sale + "," + caption3 + "," + caption4 + "," + caption5;
                    Log.d("caption", captionid);
                }

            }
        });

        etPOP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPOP.getText().toString().length() > 0) {
                    POP = etPOP.getText().toString();
                    captionid = caption1 + displaymatrix + "," + caption2 + sale + "," + caption3 + POP + "," + caption4 + "," + caption5;
                    Log.d("caption", captionid);
                }

            }
        });

        etProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etProduct.getText().toString().length() > 0) {
                    product = etProduct.getText().toString();
                    captionid = caption1 + displaymatrix + "," + caption2 + sale + "," + caption3 + POP + "," + caption4 + product + "," + caption5;
                    Log.d("caption", captionid);
                }

            }
        });

        etTraing.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTraing.getText().toString().length() > 0) {
                    training = etTraing.getText().toString();
                    captionid = caption1 + displaymatrix + "," + caption2 + sale + "," + caption3 + POP + "," + caption4 + product + "," + caption5 + training;
                    Log.d("caption", captionid);
                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTLSales();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TLDailyActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setCounter() {

        String surl =  AppController.APIV2URL+"api/CommonDDL?ModuleNo=24&ID=" + prefManager.getBranchId() + "&ID1=0&ID2=1&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressBar.dismiss();
                        counterName.clear();
                        moduleCounter.clear();
                        counterName.add("Please select");
                        moduleCounter.add(new SpinnerItemModule("0", "0"));

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
                                    counterName.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleCounter.add(itemModule);

                                }


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (TLDailyActivity.this, android.R.layout.simple_spinner_item,
                                                counterName); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCounter.setAdapter(spinnerArrayAdapter);
                                setLocation();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TLDailyActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                   Toast.makeText(TLDailyActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(TLDailyActivity.this);
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(TLDailyActivity.this);

        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    private void setLocation() {

        String surl =  AppController.APIV2URL+"api/CommonDDL?ModuleNo=47&ID=" + prefManager.getUserId() + "&ID1=" + nosaledate + "&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);

        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressBar.dismiss();
                        moduleLocation.clear();
                        locationName.clear();

                        moduleCounter.add(new SpinnerItemModule("0", "0"));

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
                                    locationName.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleLocation.add(itemModule);

                                }


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (TLDailyActivity.this, android.R.layout.simple_spinner_item,
                                                locationName); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spLocation.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TLDailyActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(TLDailyActivity.this);
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(TLDailyActivity.this);

        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    private void showDateDialog() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                StringBuffer strBuf = new StringBuffer();
                strBuf.append("Select date is ");
                strBuf.append(year);
                strBuf.append("-");
                strBuf.append(month + 1);
                strBuf.append("-");
                strBuf.append(dayOfMonth);


            }
        };

        // Get current year, month and day.
        Calendar now = Calendar.getInstance();
        final int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(TLDailyActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                String sdate = (m + 1) + "/" + d + "/" + y;
                int s = (m + 1) + d + y;

                int month = (m + 1);
                if (month == 1) {
                    monthname = "Jan";

                } else if (month == 2) {
                    monthname = "Feb";
                } else if (month == 3) {
                    monthname = "March";
                } else if (month == 4) {
                    monthname = "April";
                } else if (month == 5) {
                    monthname = "May";
                } else if (month == 6) {
                    monthname = "June";
                } else if (month == 7) {
                    monthname = "July";
                } else if (month == 8) {
                    monthname = "August";
                } else if (month == 9) {
                    monthname = "Sep";
                } else if (month == 10) {
                    monthname = "Oct";
                } else if (month == 11) {
                    monthname = "Nov";
                } else if (month == 12) {
                    monthname = "Dec";
                }

                nosaledate = d + "-" + monthname + "-" + y;

                tvDate.setText(nosaledate);
                setLocation();
                //  pref.saveDOJ(sdate);


            }
        }, year, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clockicon);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis() - 1000));

        // Popup the dialog.

        dialog.show();
    }


   /* private void postTLSales() {
        progressDialog.show();

        Call<UploadObject> fileUpload = uploadService.postTL(nosaledate, counterid, countername, captionid, remarks, locationid, aemid, operation,financialYear,month,usertypeid,securitycode);
        fileUpload.enqueue(new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, retrofit2.Response<UploadObject> response) {
                progressDialog.dismiss();
                UploadObject extraWorkingDayModel = response.body();
                if (extraWorkingDayModel.isResponseStatus()) {
                    String msg = extraWorkingDayModel.getResponseText();
                    Toast.makeText(getApplicationContext(), extraWorkingDayModel.getResponseText(), Toast.LENGTH_SHORT).show();
                    successAlert();


                } else {
                    Toast.makeText(getApplicationContext(), extraWorkingDayModel.getResponseText(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadObject> call, Throwable t) {
                progressDialog.dismiss();

                Log.e("error", "Error " + t.getMessage());
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();

                //   Toast.makeText(AttendanceManageActivity.this,"attendance saved without image",Toast.LENGTH_LONG).show();
            }

        });
    }*/

    private void postTLSales() {
        final ProgressDialog pd=new ProgressDialog(TLDailyActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        AndroidNetworking.upload( AppController.APIV2URL+"api/post_TLSalesActivity")
                .addMultipartParameter("SalesDate", nosaledate)
                .addMultipartParameter("SalesPointID", counterid)
                .addMultipartParameter("SalesPointName", countername)
                .addMultipartParameter("Caption", captionid)
                .addMultipartParameter("Remarks", remarks)
                .addMultipartParameter("LocationID", locationid)
                .addMultipartParameter("AEMEmployeeID", aemid)
                .addMultipartParameter("Operation", operation)
                .addMultipartParameter("FinancialYear", financialYear)
                .addMultipartParameter("Month", month)
                .addMultipartParameter("UsertTypeID", usertypeid)
                .addMultipartParameter("SecurityCode",securitycode)
                .addHeaders("Authorization", "Bearer " + prefManager.getAccessToken())


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

                        boolean responseStatus = job1.optBoolean("responseStatus");
                        Log.d("responseText", responseText);
                        if (responseStatus) {

                            successAlert();
                            pd.dismiss();

                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(TLDailyActivity.this,responseText,Toast.LENGTH_LONG).show();

                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG);
                    }
                });
    }



    private void successAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TLDailyActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText("You deatils submitted successfully");

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                Intent intent=new Intent(TLDailyActivity.this,TLSalesReportActivity.class);
                startActivity(intent);
                finish();



            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
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
}
