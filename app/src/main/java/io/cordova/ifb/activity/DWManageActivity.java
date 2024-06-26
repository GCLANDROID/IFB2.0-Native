package io.cordova.ifb.activity;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class DWManageActivity extends AppCompatActivity {
    Spinner spCategory,spModel,spAdvanced;
    ArrayList<SpinnerItemModule>mCatList=new ArrayList<>();
    ArrayList<String>catList=new ArrayList<>();
    ArrayList<SpinnerItemModule>mModelList=new ArrayList<>();
    ArrayList<String>modelList=new ArrayList<>();
    ArrayList<String>advancedList=new ArrayList<>();
    ProgressDialog pd;
    PrefManager prefManager;
    String catId="";
    String modelId="";
    String advanced="";
    EditText etCusName,etCusMob,etCusAddress,etAmt,etRemarks;
    AlertDialog alerDialog1;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_w_manage);
        initView();
        setCategory();
        onClick();
    }

    private void initView(){
        prefManager=new PrefManager(DWManageActivity.this);
        pd=new ProgressDialog(DWManageActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        spCategory=(Spinner)findViewById(R.id.spCategory);
        spModel=(Spinner)findViewById(R.id.spModel);
        spAdvanced=(Spinner)findViewById(R.id.spAdvanced);

        etCusName=(EditText)findViewById(R.id.etCusName);
        etCusMob=(EditText)findViewById(R.id.etCusMob);
        etCusAddress=(EditText)findViewById(R.id.etCusAddress);
        etAmt=(EditText)findViewById(R.id.etAmt);
        etRemarks=(EditText)findViewById(R.id.etRemarks);

        btnSave=(Button)findViewById(R.id.btnSave);


    }

    private void onClick(){
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catId=mCatList.get(i).getItemId();
                setModel(catId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                modelId=mModelList.get(i).getItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spAdvanced.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){
                    advanced=advancedList.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etCusName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    if (etCusName.getText().toString().equals(".")){
                        etCusName.setText("");
                        etCusName.setError("Please enter valid Customer Name");
                    }
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               advancedCheck();
            }
        });
    }

    private void setCategory() {
        Log.d("hitr", "1");

        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=40004&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        pd.dismiss();
                        catList.clear();
                        mCatList.clear();


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
                                    catList.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    mCatList.add(itemModule);

                                }




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (DWManageActivity.this, android.R.layout.simple_spinner_item,
                                                catList); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCategory.setAdapter(spinnerArrayAdapter);
                                setAdavanced();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DWManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DWManageActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setModel(String catId) {
        Log.d("hitr", "1");

        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=1808&ID="+catId+"&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        pd.dismiss();
                        mModelList.clear();
                        modelList.clear();


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
                                    modelList.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    mModelList.add(itemModule);

                                }




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (DWManageActivity.this, android.R.layout.simple_spinner_item,
                                                modelList); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spModel.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DWManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DWManageActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setAdavanced(){
        advancedList.add("Please select");
        advancedList.add("Yes");
        advancedList.add("No");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (DWManageActivity.this, android.R.layout.simple_spinner_item,
                        advancedList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAdvanced.setAdapter(spinnerArrayAdapter);
    }


    private void Mobcheck() {
        String surl = AppController.APIURL+"api/CheckInvalidMobileNo?MobileNo=" + etCusMob.getText().toString();
        Log.d("phnnumbercheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseCategory", response);
                        progressBar.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean emailstatus2 = job1.optBoolean("responseStatus");
                            if (emailstatus2) {
                                postDW();
                            } else {
                                Toast.makeText(DWManageActivity.this,responseText,Toast.LENGTH_LONG).show();
                            }

                            //boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DWManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(DWManageActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DWManageActivity.this);
        requestQueue.add(stringRequest);

    }


    private void postDW() {

        final ProgressDialog pd = new ProgressDialog(DWManageActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        AndroidNetworking.upload(AppController.APIURL+"api/post_EmployeeCampaign")
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("CustomerName", etCusName.getText().toString())
                .addMultipartParameter("CustomerPhone", etCusMob.getText().toString())
                .addMultipartParameter("CustomerAddress", etCusAddress.getText().toString())
                .addMultipartParameter("ModelID", modelId)
                .addMultipartParameter("AdvanceReceived", advanced)
                .addMultipartParameter("BookingAmount", etAmt.getText().toString())
                .addMultipartParameter("Remarks", etRemarks.getText().toString())
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
                            Toast.makeText(DWManageActivity.this, responseText, Toast.LENGTH_LONG).show();

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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DWManageActivity.this, R.style.CustomDialogNew);
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
                Intent intent = new Intent(DWManageActivity.this, DWDashboardActivity.class);
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

    private void advancedCheck(){
        if (advanced.equals("Yes")){

            if (etCusName.getText().toString().length()>1){
                if (etCusMob.getText().toString().length()>9){
                    if (etCusAddress.getText().toString().length()>2){
                        if (!modelId.equals("")){
                            if (!advanced.equals("")){
                                if (etAmt.getText().toString().length()>0){
                                    if (etRemarks.getText().toString().length()>0){
                                        Mobcheck();

                                    }else {
                                        Toast.makeText(DWManageActivity.this,"Please enter Remarks",Toast.LENGTH_LONG).show();
                                    }

                                }else {
                                    Toast.makeText(DWManageActivity.this,"Please enter Amount",Toast.LENGTH_LONG).show();
                                }

                            }else {
                                Toast.makeText(DWManageActivity.this,"Please select Advanced option",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(DWManageActivity.this,"Please select Model",Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(DWManageActivity.this,"Please enter Customer Address",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(DWManageActivity.this,"Please enter Customer Mobile Numbe",Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(DWManageActivity.this,"Please enter Customer Name",Toast.LENGTH_LONG).show();
            }

        }else {


            if (etCusName.getText().toString().length()>1){
                if (etCusMob.getText().toString().length()>9){
                    if (etCusAddress.getText().toString().length()>2){
                        if (!modelId.equals("")){
                            if (!advanced.equals("")){

                                    if (etRemarks.getText().toString().length()>0){
                                        Mobcheck();

                                    }else {
                                        Toast.makeText(DWManageActivity.this,"Please enter Remarks",Toast.LENGTH_LONG).show();
                                    }



                            }else {
                                Toast.makeText(DWManageActivity.this,"Please select Advanced option",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(DWManageActivity.this,"Please select Model",Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(DWManageActivity.this,"Please enter Customer Address",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(DWManageActivity.this,"Please enter Customer Mobile Numbe",Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(DWManageActivity.this,"Please enter Customer Name",Toast.LENGTH_LONG).show();
            }

        }
    }
}
