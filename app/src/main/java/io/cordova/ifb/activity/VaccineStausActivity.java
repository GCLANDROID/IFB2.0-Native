package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;


import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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

import java.util.ArrayList;
import java.util.Calendar;

import io.cordova.ifb.R;
import io.cordova.ifb.databinding.ActivityVaccineStausBinding;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class VaccineStausActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityVaccineStausBinding binding;
    ProgressDialog progressDialog;
    ArrayList<String>vaccineStausList=new ArrayList<>();
    ArrayList<SpinnerItemModule>mVaccineStatusList=new ArrayList<>();

    ArrayList<String>vaccineThroughList=new ArrayList<>();
    ArrayList<SpinnerItemModule>mVaccineThroughList=new ArrayList<>();

    ArrayList<String>scndvaccineThroughList=new ArrayList<>();
    ArrayList<SpinnerItemModule>mscndVaccineThroughList=new ArrayList<>();

    ArrayList<String>vaccineTypeList=new ArrayList<>();
    ArrayList<SpinnerItemModule>mVaccineTypeList=new ArrayList<>();

    ArrayList<String>frstVAccineReasonList=new ArrayList<>();
    ArrayList<SpinnerItemModule>mfrstVAccineReasonList=new ArrayList<>();

    ArrayList<String>scndVAccineReasonList=new ArrayList<>();
    ArrayList<SpinnerItemModule>mscndVAccineReasonList=new ArrayList<>();

    PrefManager prefManager;

    String vaccinationStatusID="";

    ArrayList<String>scndVaccineDelayedList=new ArrayList<>();

    String monthname,firstVaccineDate="",scndVaccineDate="",scndVaccinemonth;
    String scndVaccineDueDate="";
    String scndVaccineDueMonth;
    String scndVaccinedelayStatus="";
    String vaccineType="";
    String frstVaccinemedium="";
    String scndVaccinemedium="";
    String scndVaccineDelayed="";
    String frstVaccineDelayed="";
    String vaccinationStatus="";

    AlertDialog alerDialog1;

    String VaccinationStatus,Vaccination_OneDose_Date,Vaccinated_OneDose_By,Vaccine_OneDose_Type,Vaccine_OneDose_Delay_Reason,Vaccine_TwoDose_Delay_Status,Vaccination_TwoDose_DueDate,Vaccine_TwoDose_Delay_Reason,Vaccinated_TwoDose_By,Vaccination_TwoDose_Date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_vaccine_staus);
        initView();
        onCLick();
    }

    private void initView(){
        prefManager=new PrefManager(VaccineStausActivity.this);
        progressDialog=new ProgressDialog(VaccineStausActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);


        getItemList();

        binding.llfrstVaccineDate.setOnClickListener(this);
        binding.llScndVaccineDate.setOnClickListener(this);
        binding.llScndVaccineDueDate.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        binding.imgBack.setOnClickListener(this);
        binding.imgHome.setOnClickListener(this);


    }

    private void onCLick(){

        binding.spVaccineType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    vaccineType=mVaccineTypeList.get(position).getItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        binding.spFrstVaccineMedium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    frstVaccinemedium=mVaccineThroughList.get(position).getItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spScndVaccineMedium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    scndVaccinemedium=mscndVaccineThroughList.get(position).getItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spScndDoseDelayedReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    scndVaccineDelayed=mscndVAccineReasonList.get(position).getItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spFrstDoseDelayedReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    frstVaccineDelayed=frstVAccineReasonList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spScndDoseDelayed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    binding.llScndVaccineDelayedReason.setVisibility(View.GONE);
                }else {
                    binding.llScndVaccineDelayedReason.setVisibility(View.VISIBLE);
                }
                scndVaccinedelayStatus=scndVaccineDelayedList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spVaccineStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    vaccinationStatusID=mVaccineStatusList.get(position).getItemId();
                    vaccinationStatus = mVaccineStatusList.get(position).getItem();
                    if (vaccinationStatusID.equalsIgnoreCase("1")){
                        binding.llScndVaccineDueDate.setVisibility(View.GONE);
                        binding.llScndVaccineDelayedReason.setVisibility(View.GONE);
                        binding.llFrstVaccineDelayed.setVisibility(View.GONE);
                        binding.llScndVaccineDelayed.setVisibility(View.GONE);
                        binding.llVaccineType.setVisibility(View.VISIBLE);
                        binding.llFrstVaccineMedium.setVisibility(View.VISIBLE);
                        binding.llScndVaccineMedium.setVisibility(View.VISIBLE);
                        binding.llfrstVaccineDate.setVisibility(View.VISIBLE);
                        binding.llScndVaccineDate.setVisibility(View.VISIBLE);
                        scndVaccineDueDate="";
                        scndVaccinedelayStatus="";
                        frstVaccineDelayed="";
                        scndVaccineDelayed="";

                    }else if (vaccinationStatusID.equalsIgnoreCase("2")){
                        binding.llScndVaccineDate.setVisibility(View.GONE);
                        binding.llFrstVaccineDelayed.setVisibility(View.GONE);
                        binding.llFrstVaccineMedium.setVisibility(View.VISIBLE);
                        binding.llScndVaccineMedium.setVisibility(View.GONE);
                        binding.llVaccineType.setVisibility(View.VISIBLE);
                        binding.llfrstVaccineDate.setVisibility(View.VISIBLE);
                        binding.llScndVaccineDueDate.setVisibility(View.VISIBLE);
                        binding.llScndVaccineDelayed.setVisibility(View.VISIBLE);
                        scndVaccineDate = "";
                        frstVaccineDelayed="";
                        scndVaccinemedium = "";

                    }else if (vaccinationStatusID.equalsIgnoreCase("3")){
                        binding.llFrstVaccineMedium.setVisibility(View.GONE);
                        binding.llScndVaccineMedium.setVisibility(View.GONE);
                        binding.llVaccineType.setVisibility(View.GONE);
                        binding.llfrstVaccineDate.setVisibility(View.GONE);
                        binding.llScndVaccineDate.setVisibility(View.GONE);
                        binding.llScndVaccineDueDate.setVisibility(View.GONE);
                        binding.llScndVaccineDelayedReason.setVisibility(View.GONE);
                        binding.llFrstVaccineDelayed.setVisibility(View.GONE);
                        binding.llFrstVaccineDelayed.setVisibility(View.VISIBLE);
                        binding.llScndVaccineDelayed.setVisibility(View.GONE);
                        vaccineType="";
                        scndVaccineDelayed="";
                        scndVaccineDate="";
                        firstVaccineDate="";
                        scndVaccineDueDate="";
                        frstVaccinemedium="";
                        scndVaccinemedium="";
                        firstVaccineDate="";


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

    private void getVaccinationStatus() {
        Log.d("hitr", "1");
        progressDialog.show();
        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=V01&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=IFB";
        Log.d("ctegoryinput", surl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressDialog.show();
                        vaccineStausList.clear();
                        mVaccineStatusList.clear();
                        vaccineStausList.add("Please select");
                        mVaccineStatusList.add(new SpinnerItemModule("0", "0"));

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
                                    vaccineStausList.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    mVaccineStatusList.add(itemModule);


                                }




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (VaccineStausActivity.this, android.R.layout.simple_spinner_item,
                                                vaccineStausList); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spVaccineStatus.setAdapter(spinnerArrayAdapter);
                                int pos=vaccineStausList.indexOf(VaccinationStatus);
                                binding.spVaccineStatus.setSelection(pos);
                                getVaccinationThrough();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(VaccineStausActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(VaccineStausActivity.this);
        requestQueue.add(stringRequest);

    }

    private void getVaccinationThrough() {
        Log.d("hitr", "1");
        progressDialog.show();
        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=V02&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=IFB" ;
        Log.d("ctegoryinput", surl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressDialog.show();
                        vaccineThroughList.clear();
                        mVaccineThroughList.clear();
                        vaccineThroughList.add("Please select");
                        mVaccineThroughList.add(new SpinnerItemModule("0", "0"));

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
                                    vaccineThroughList.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    mVaccineThroughList.add(itemModule);


                                }




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (VaccineStausActivity.this, android.R.layout.simple_spinner_item,
                                                vaccineThroughList); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spFrstVaccineMedium.setAdapter(spinnerArrayAdapter);
                                int pos=vaccineThroughList.indexOf(Vaccinated_OneDose_By);
                                binding.spFrstVaccineMedium.setSelection(pos);
                                getScndVaccinationThrough();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(VaccineStausActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(VaccineStausActivity.this);
        requestQueue.add(stringRequest);

    }

    private void getScndVaccinationThrough() {
        Log.d("hitr", "1");
        progressDialog.show();
        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=V02&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=IFB";
        Log.d("ctegoryinput", surl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressDialog.show();
                        scndvaccineThroughList.clear();
                        mscndVaccineThroughList.clear();
                        scndvaccineThroughList.add("Please select");
                        mscndVaccineThroughList.add(new SpinnerItemModule("0", "0"));

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
                                    scndvaccineThroughList.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    mscndVaccineThroughList.add(itemModule);


                                }




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (VaccineStausActivity.this, android.R.layout.simple_spinner_item,
                                                scndvaccineThroughList); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spScndVaccineMedium.setAdapter(spinnerArrayAdapter);
                                int pos=scndvaccineThroughList.indexOf(Vaccinated_TwoDose_By);
                                binding.spScndVaccineMedium.setSelection(pos);
                                getVaccinationType();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(VaccineStausActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(VaccineStausActivity.this);
        requestQueue.add(stringRequest);

    }

    private void getVaccinationType() {
        Log.d("hitr", "1");
        progressDialog.show();
        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=V03&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=IFB" ;
        Log.d("ctegoryinput", surl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressDialog.show();
                        vaccineTypeList.clear();
                        mVaccineTypeList.clear();
                        vaccineTypeList.add("Please select");
                        mVaccineTypeList.add(new SpinnerItemModule("0", "0"));

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
                                    vaccineTypeList.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    mVaccineTypeList.add(itemModule);

                                }




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (VaccineStausActivity.this, android.R.layout.simple_spinner_item,
                                                vaccineTypeList); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spVaccineType.setAdapter(spinnerArrayAdapter);
                                int pos=vaccineTypeList.indexOf(Vaccine_OneDose_Type);
                                binding.spVaccineType.setSelection(pos);
                                getFrstVaccinationDelay();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(VaccineStausActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(VaccineStausActivity.this);
        requestQueue.add(stringRequest);

    }

    private void getFrstVaccinationDelay() {
        Log.d("hitr", "1");
        progressDialog.show();
        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=V04&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=IFB" ;
        Log.d("ctegoryinput", surl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressDialog.show();
                        frstVAccineReasonList.clear();
                        mfrstVAccineReasonList.clear();
                        frstVAccineReasonList.add("Please select");
                        mfrstVAccineReasonList.add(new SpinnerItemModule("0", "0"));

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
                                    frstVAccineReasonList.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    mfrstVAccineReasonList.add(itemModule);

                                }




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (VaccineStausActivity.this, android.R.layout.simple_spinner_item,
                                                frstVAccineReasonList); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spFrstDoseDelayedReason.setAdapter(spinnerArrayAdapter);

                                int pos=frstVAccineReasonList.indexOf(Vaccine_OneDose_Delay_Reason);
                                binding.spFrstDoseDelayedReason.setSelection(pos);
                                getScndVaccinationDelay();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(VaccineStausActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(VaccineStausActivity.this);
        requestQueue.add(stringRequest);

    }

    private void getScndVaccinationDelay() {
        Log.d("hitr", "1");
        progressDialog.show();
        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=V05&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=IFB" ;
        Log.d("ctegoryinput", surl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressDialog.dismiss();
                        scndVAccineReasonList.clear();
                        mscndVAccineReasonList.clear();
                        scndVAccineReasonList.add("Please select");
                        mscndVAccineReasonList.add(new SpinnerItemModule("0", "0"));

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
                                    scndVAccineReasonList.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    mscndVAccineReasonList.add(itemModule);

                                }




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (VaccineStausActivity.this, android.R.layout.simple_spinner_item,
                                                scndVAccineReasonList); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spScndDoseDelayedReason.setAdapter(spinnerArrayAdapter);
                                int pos=scndVAccineReasonList.indexOf(Vaccine_TwoDose_Delay_Reason);
                                binding.spScndDoseDelayedReason.setSelection(pos);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(VaccineStausActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(VaccineStausActivity.this);
        requestQueue.add(stringRequest);

    }

    private void showFirstVAccineDateDialog() {
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
        final int year2 = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(VaccineStausActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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

                firstVaccineDate = d + "-" + monthname + "-" + y;

                binding.tvFrstDoseDate.setText(firstVaccineDate);

                //  pref.saveDOJ(sdate);


            }
        }, year2, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clockicon);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis() - 1000));

        // Popup the dialog.

        dialog.show();
    }

    private void showScndVAccineDateDialog() {
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
        final int year2 = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(VaccineStausActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                String sdate = (m + 1) + "/" + d + "/" + y;
                int s = (m + 1) + d + y;

                int month = (m + 1);
                if (month == 1) {
                    scndVaccinemonth = "Jan";

                } else if (month == 2) {
                    scndVaccinemonth = "Feb";
                } else if (month == 3) {
                    scndVaccinemonth = "March";
                } else if (month == 4) {
                    scndVaccinemonth = "April";
                } else if (month == 5) {
                    scndVaccinemonth = "May";
                } else if (month == 6) {
                    scndVaccinemonth = "June";
                } else if (month == 7) {
                    scndVaccinemonth = "July";
                } else if (month == 8) {
                    scndVaccinemonth = "August";
                } else if (month == 9) {
                    scndVaccinemonth = "Sep";
                } else if (month == 10) {
                    scndVaccinemonth = "Oct";
                } else if (month == 11) {
                    scndVaccinemonth = "Nov";
                } else if (month == 12) {
                    scndVaccinemonth = "Dec";
                }

                scndVaccineDate = d + "-" + scndVaccinemonth + "-" + y;

                binding.tvScndDoseDate.setText(scndVaccineDate);

                //  pref.saveDOJ(sdate);


            }
        }, year2, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clockicon);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis() - 1000));

        // Popup the dialog.

        dialog.show();
    }

    private void showScndVAccineDueDateDialog() {
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
        final int year2 = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(VaccineStausActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                String sdate = (m + 1) + "/" + d + "/" + y;
                int s = (m + 1) + d + y;

                int month = (m + 1);
                if (month == 1) {
                    scndVaccineDueMonth = "Jan";

                } else if (month == 2) {
                    scndVaccineDueMonth = "Feb";
                } else if (month == 3) {
                    scndVaccineDueMonth = "March";
                } else if (month == 4) {
                    scndVaccineDueMonth = "April";
                } else if (month == 5) {
                    scndVaccineDueMonth = "May";
                } else if (month == 6) {
                    scndVaccineDueMonth = "June";
                } else if (month == 7) {
                    scndVaccineDueMonth = "July";
                } else if (month == 8) {
                    scndVaccineDueMonth = "August";
                } else if (month == 9) {
                    scndVaccineDueMonth = "Sep";
                } else if (month == 10) {
                    scndVaccineDueMonth = "Oct";
                } else if (month == 11) {
                    scndVaccineDueMonth = "Nov";
                } else if (month == 12) {
                    scndVaccineDueMonth = "Dec";
                }

                scndVaccineDueDate = d + "-" + scndVaccineDueMonth + "-" + y;

                binding.tvScndDoseDueDate.setText(scndVaccineDueDate);

                //  pref.saveDOJ(sdate);


            }
        }, year2, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clockicon);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker();

        // Popup the dialog.

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v==binding.llfrstVaccineDate){
            showFirstVAccineDateDialog();
        }else if (v==binding.llScndVaccineDate){
            showScndVAccineDateDialog();
        }else if (v==binding.llScndVaccineDueDate){
            showScndVAccineDueDateDialog();
        }else if (v==binding.btnSubmit){
            postVaccineStaus();
        }else if (v==binding.imgBack){
            onBackPressed();
        }else if (v==binding.imgHome) {
            Intent intent = new Intent(VaccineStausActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void postVaccineStaus() {

        final ProgressDialog pd = new ProgressDialog(VaccineStausActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload(AppController.APIURL+"api/post_EmployeeVaccinationStatus")
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("VaccinationStatus", vaccinationStatus)
                .addMultipartParameter("Vaccination_OneDose_Status", vaccinationStatus)
                .addMultipartParameter("Vaccinated_OneDose_By", frstVaccinemedium)
                .addMultipartParameter("Vaccine_OneDose_Type", vaccineType)
                .addMultipartParameter("Vaccination_OneDose_Date", firstVaccineDate)
                .addMultipartParameter("Vaccine_OneDose_Delay_Reason", frstVaccineDelayed)
                .addMultipartParameter("Vaccine_OneDose_Remarks", "ok")
                .addMultipartParameter("Vaccine_TwoDose_Delay_Status", scndVaccinedelayStatus)
                .addMultipartParameter("Vaccination_TwoDose_DueDate", scndVaccineDueDate)
                .addMultipartParameter("Vaccine_TwoDose_Delay_Reason", scndVaccineDelayed)
                .addMultipartParameter("Vaccine_TwoDose_Remarks", "ok")
                .addMultipartParameter("Vaccination_TwoDose_Status", vaccinationStatus)
                .addMultipartParameter("Vaccinated_TwoDose_By", scndVaccinemedium)
                .addMultipartParameter("Vaccine_TwoDose_Type", vaccineType)
                .addMultipartParameter("Vaccination_TwoDose_Date", scndVaccineDate)
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
                        boolean responseStatus=job1.optBoolean("responseStatus");
                        if (responseStatus) {
                            successAlert(responseText);
                            pd.dismiss();

                        } else {
                            pd.dismiss();
                            Toast.makeText(VaccineStausActivity.this, responseText, Toast.LENGTH_LONG).show();

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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(VaccineStausActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText(text);

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                Intent intent=new Intent(VaccineStausActivity.this,AttendanceDashBoardActivity.class);
                startActivity(intent);
                finish();

            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(false);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }

    private void getItemList(){

       progressDialog.show();
        String surl = AppController.APIURL+"api/get_EmployeeVaccinationStatus?AEMEmployeeID="+prefManager.getUserId()+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputSalesReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);
                        progressDialog.show();

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(0);
                                    VaccinationStatus=obj.optString("VaccinationStatus");
                                    Vaccinated_OneDose_By=obj.optString("Vaccinated_OneDose_By");
                                    Vaccine_OneDose_Type=obj.optString("Vaccine_OneDose_Type");
                                    firstVaccineDate=obj.optString("Vaccination_OneDose_Date");
                                    Vaccine_OneDose_Delay_Reason=obj.optString("Vaccine_OneDose_Delay_Reason");
                                    Vaccine_TwoDose_Delay_Status=obj.optString("Vaccine_TwoDose_Delay_Status");
                                    scndVaccineDueDate=obj.optString("Vaccination_TwoDose_DueDate");
                                    Vaccine_TwoDose_Delay_Reason=obj.optString("Vaccine_TwoDose_Delay_Reason");
                                    Vaccinated_TwoDose_By=obj.optString("Vaccinated_TwoDose_By");
                                    scndVaccineDate=obj.optString("Vaccination_TwoDose_Date");
                                    binding.tvFrstDoseDate.setText(firstVaccineDate);
                                    binding.tvScndDoseDueDate.setText(scndVaccineDueDate);
                                    binding.tvScndDoseDate.setText(scndVaccineDate);





                                }







                            } else {

                               // Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }
                            scndVaccineDelayedList.add("NO");
                            scndVaccineDelayedList.add("YES");

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                    (VaccineStausActivity.this, android.R.layout.simple_spinner_item,
                                            scndVaccineDelayedList); //selected item will look like a spinner set from XML
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.spScndDoseDelayed.setAdapter(spinnerArrayAdapter);
                            int pos=scndVaccineDelayedList.indexOf(Vaccine_TwoDose_Delay_Status);
                            binding.spScndDoseDelayed.setSelection(pos);

                            getVaccinationStatus();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(VaccineStausActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(VaccineStausActivity.this);
        requestQueue.add(stringRequest);
    }
}