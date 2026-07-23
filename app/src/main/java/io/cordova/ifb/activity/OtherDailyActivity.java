package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.OtherItem1Adapter;
import io.cordova.ifb.adapter.OtherItem2Adapter;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.module.TSRSaleItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PostDisplayMatrixService;
import io.cordova.ifb.utility.PrefManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OtherDailyActivity extends AppCompatActivity {
    TextView tvDateName,tvCounter;
    RecyclerView rvItem1,rvItem2;
    ArrayList<TSRSaleItemModule> itemList1 = new ArrayList<>();
    ArrayList<TSRSaleItemModule> itemList2 = new ArrayList<>();
    LinearLayout llDate;
    TextView tvDate;
    String monthname;
    String year,month,financialYear;
    Spinner spLocation;
    ArrayList<SpinnerItemModule> moduleLocation = new ArrayList<>();
    ArrayList<String> locationName = new ArrayList<>();
    EditText etRemarks;
    String branchId="0";
    String transNo="0";
    String aemId="";
    String counterId="";
    String counter="ok";
    String userTypeid="";
    String perticipent="ok";
    String receipe="ok";
    String remarks="ok";
    String category2="ok";
    String locationId="0";
    String operation="0";
    String securityCode="";
    EditText etCounter;

    private PostDisplayMatrixService uploadService;
    ProgressDialog progressDialog;
    Button btnSubmit;
    String zoneId="0";
    String nosaledate="";
    AlertDialog alerDialog1;
    ImageView imgHome,imgBack;
    PrefManager prefManager;
    Spinner spCounter;
    ArrayList<SpinnerItemModule>moduleCounter=new ArrayList<>();
    ArrayList<String>counterName=new ArrayList<>();
    EditText etParticipent,etReceipe;
    String category1="";
    AlertDialog alertDialog;
    ArrayList<String>item=new ArrayList<>();
    ArrayList<String>item1=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_daily);
        shoeDialog();
        initialize();
        onClick();
    }

    private void initialize(){
        prefManager=new PrefManager(getApplicationContext());
        OkHttpClient okHttpClient =
                AppController.getUnsafeOkHttpClient();

        AndroidNetworking.initialize(
                getApplicationContext(),
                okHttpClient
        );
        tvDateName=(TextView)findViewById(R.id.tvDateName);
        String next = "<font color='#EE0000'>*</font>";
        String datename="Visited Date";
        tvDateName.setText(Html.fromHtml(datename + next));

        tvCounter=(TextView)findViewById(R.id.tvCounter);
        String counter="Counter Name";
        tvCounter.setText(Html.fromHtml(counter + next));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(OtherDailyActivity.this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(OtherDailyActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem1=(RecyclerView)findViewById(R.id.rvItem1);
        rvItem2=(RecyclerView)findViewById(R.id.rvItem2);
        rvItem1.setLayoutManager(layoutManager);
        rvItem2.setLayoutManager(layoutManager1);
        llDate=(LinearLayout)findViewById(R.id.llDate);
        tvDate=(TextView)findViewById(R.id.tvDate);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        nosaledate = df.format(c);
        tvDate.setText(nosaledate);
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
        spLocation=(Spinner)findViewById(R.id.spLocation);
        etRemarks=(EditText)findViewById(R.id.etRemarks);
        etParticipent=(EditText)findViewById(R.id.etParticipent);
        etReceipe=(EditText)findViewById(R.id.etReceipe);
        branchId=prefManager.getBranchId();
        transNo="0";
        aemId=prefManager.getUserId();
        etCounter=(EditText)findViewById(R.id.etCounter);
        userTypeid=prefManager.getUserTypeId();
        operation="4";
        securityCode=prefManager.getSecurityCode();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Change base URL to your upload server URL.

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        zoneId="0";
        imgHome=(ImageView)findViewById(R.id.imgHome);
        imgBack=(ImageView)findViewById(R.id.imgBack);
        spCounter=(Spinner)findViewById(R.id.spCounter);
        getItem1();
        String item1=prefManager.getOtherItem1();
        category1=item1.replace("[", "").replace("]", "");
        String item2=prefManager.getOtherItem2();
        category2=item2.replace("[", "").replace("]", "");


        }

    public void updateItemStatus(int position) {
        item.add(itemList1.get(position).getItemId()+"-"+itemList1.get(position).getEditvalue());
        String itemcomp=item.toString();
        category1=itemcomp.replace("[","").replace("]","");
        Log.d("aripitem",item.toString());
    }

    public void updateItem1Status(int position) {
        item1.add(itemList2.get(position).getItemId()+"-"+itemList2.get(position).getEditvalue());
        String itemcomp=item1.toString();
        category2=itemcomp.replace("[","").replace("]","");
        Log.d("aripitem",item1.toString());
    }


        private void onClick(){
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
                    if (etCounter.getText().toString().length()>0){
                        counter=etCounter.getText().toString();
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
                    if (etRemarks.getText().toString().length()>0){
                        remarks=etRemarks.getText().toString();
                    }

                }
            });
            etParticipent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (etParticipent.getText().toString().length()>0){
                        perticipent=etParticipent.getText().toString();
                    }

                }
            });

            etReceipe.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (etReceipe.getText().toString().length()>0){
                        receipe=etReceipe.getText().toString();
                    }

                }
            });
            spCounter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position>0){
                        counterId=moduleCounter.get(position).getItemId();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position>0){
                        locationId=moduleLocation.get(position).getItemId();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!counterId.equals("")) {
                        if (!locationId.equals("0")) {
                            postOtherSale();
                        }else {
                            Toast.makeText(getApplicationContext(),"Please select Location",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Please select Counter",Toast.LENGTH_LONG).show();
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
                    Intent intent=new Intent(OtherDailyActivity.this,DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    private void getItem1() {
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
        progressBar.show();
        String surl = AppController.APIV2URL+"api/CommonDDL?ModuleNo=4&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=IFB";
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("responseAir", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String value = obj.optString("value");
                                    String id = obj.optString("id");
                                    TSRSaleItemModule itemModel = new TSRSaleItemModule();
                                    itemModel.setItemId(id);
                                    itemModel.setItem(value);
                                    itemList1.add(itemModel);

                                }
                                getItem2();

                                progressBar.dismiss();
                                setItem1Adapter();

                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {
                                progressBar.dismiss();

                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OtherDailyActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
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
//        RequestQueue requestQueue = Volley.newRequestQueue(OtherDailyActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(OtherDailyActivity.this);

        requestQueue.add(stringRequest);


    }
    private void getItem2() {
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
        progressBar.show();
        String surl = AppController.APIV2URL+"api/CommonDDL?ModuleNo=4&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=IFB";
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("responseAir", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String value = obj.optString("value");
                                    String id = obj.optString("id");
                                    TSRSaleItemModule itemModel = new TSRSaleItemModule();
                                    itemModel.setItemId(id);
                                    itemModel.setItem(value);
                                    itemList2.add(itemModel);

                                }

                                progressBar.dismiss();
                                setItem2Adapter();
                                setCounter();

                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {
                                progressBar.dismiss();

                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OtherDailyActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
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
//        RequestQueue requestQueue = Volley.newRequestQueue(OtherDailyActivity.this);
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(OtherDailyActivity.this);

        requestQueue.add(stringRequest);


    }

    private void setItem1Adapter() {
       OtherItem1Adapter itemAdapter = new OtherItem1Adapter(itemList1, OtherDailyActivity.this);
        rvItem1.setAdapter(itemAdapter);
    }

    private void setItem2Adapter() {
        OtherItem2Adapter itemAdapter1 = new OtherItem2Adapter(itemList2, OtherDailyActivity.this);
        rvItem2.setAdapter(itemAdapter1);
    }

    private void setCounter() {

        String surl = AppController.APIV2URL+"api/CommonDDL?ModuleNo=24&ID=" + prefManager.getBranchId() + "&ID1=0&ID2=1&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
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
                                        (OtherDailyActivity.this, android.R.layout.simple_spinner_item,
                                                counterName); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCounter.setAdapter(spinnerArrayAdapter);
                                setLocation();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OtherDailyActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
//        RequestQueue requestQueue = Volley.newRequestQueue(OtherDailyActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(OtherDailyActivity.this);

        requestQueue.add(stringRequest);

    }

    private void setLocation() {

        String surl = AppController.APIV2URL+"api/CommonDDL?ModuleNo=47&ID=" + prefManager.getUserId() + "&ID1=" + nosaledate + "&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
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
                                        (OtherDailyActivity.this, android.R.layout.simple_spinner_item,
                                                locationName); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spLocation.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OtherDailyActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
//        RequestQueue requestQueue = Volley.newRequestQueue(OtherDailyActivity.this);
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(OtherDailyActivity.this);

        requestQueue.add(stringRequest);

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
        final DatePickerDialog dialog = new DatePickerDialog(OtherDailyActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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

   /* private void postOtherSale() {
        progressDialog.show();

        Call<UploadObject> fileUpload = uploadService.postTSR(zoneId,branchId,transNo,aemId,nosaledate,counterId,counter,userTypeid,financialYear,month,perticipent,receipe,remarks,category1,category2,locationId,operation,securityCode);
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

    private void postOtherSale() {
        final ProgressDialog pd=new ProgressDialog(OtherDailyActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        AndroidNetworking.upload(AppController.APIV2URL+"api/post_TSROtherSalesActivity")
                .addMultipartParameter("ZoneID", zoneId)
                .addMultipartParameter("BranchID", branchId)
                .addMultipartParameter("TransNo", transNo)
                .addMultipartParameter("AEMEmployeeID", aemId)
                .addMultipartParameter("SalesDate", nosaledate)
                .addMultipartParameter("SalesPointID", counterId)
                .addMultipartParameter("SalesPointName", counter)
                .addMultipartParameter("UserTypeID", userTypeid)
                .addMultipartParameter("FinancialYear", financialYear)
                .addMultipartParameter("Month", month)
                .addMultipartParameter("Percipient", perticipent)
                .addMultipartParameter("Recipe_Demonstrate",receipe)
                .addMultipartParameter("Remarks",remarks)
                .addMultipartParameter("Category1",category1)
                .addMultipartParameter("Category2",category2)
                .addMultipartParameter("LocationID",locationId)
                .addMultipartParameter("Operation",operation)
                .addMultipartParameter("SecurityCode",securityCode)
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
                            Toast.makeText(OtherDailyActivity.this,responseText,Toast.LENGTH_LONG).show();

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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OtherDailyActivity.this, R.style.CustomDialogNew);
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
                Intent intent=new Intent(OtherDailyActivity.this,TLSalesReportActivity.class);
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

    private void shoeDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OtherDailyActivity.this, R.style.CustomDialogNew);
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
                Intent intent = new Intent(OtherDailyActivity.this, DashBoardActivity.class);
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



}
