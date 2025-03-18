package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.Html;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class CustomerCallingManageManualActivity extends AppCompatActivity {

    TextView tvTitle,tvFname,tvLname,tvMob,tvCalStatus,tvAction,tvRemarks;
    Spinner spTitle,spCallStatus,spAction;
    ArrayList<String>callStatusList=new ArrayList<>();
    ArrayList<String>titleList=new ArrayList<>();
    ArrayList<SpinnerItemModule>mTitleList=new ArrayList<>();
    ArrayList<String>actionList=new ArrayList<>();
    ArrayList<SpinnerItemModule>mActionList=new ArrayList<>();

    LinearLayout llLoader,llMain;

    EditText etFirstName,etLastName,etMobNumber,etRemarks;
    PrefManager prefManager;

    String titleId="";
    String actionId="";
    String callConnectedStatus="";

    Button btnSubmit;

    AlertDialog alerDialog1;

    ImageView imgBack,imgHome;

    String phn;

    EditText etEmailId;
    TextView tvCategory,tvModel,tvMachineStatus;
    Spinner spCategory,spModel,spMachineStatus;
    ArrayList<String>category=new ArrayList<>();
    ArrayList<SpinnerItemModule>moduleCategory=new ArrayList<>();

    ArrayList<String>machineStatus=new ArrayList<>();
    String modelId="";
    String machineStatusId="";
    ArrayList<String>model=new ArrayList<>();
    ArrayList<SpinnerItemModule>moduleModel=new ArrayList<>();
    Spinner spIFBProduct;
    LinearLayout llMachineStatus;
    ArrayList<String>yesnoList=new ArrayList<>();
    String serialNo=".";
    String ifbProduct="";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_calling_manage_manual);
        initVIEW();
        setStatus();
        onClick();
    }

    private void initVIEW(){
        prefManager=new PrefManager(CustomerCallingManageManualActivity.this);
        tvTitle=(TextView)findViewById(R.id.tvTitle);
        tvFname=(TextView)findViewById(R.id.tvFname);
        tvLname=(TextView)findViewById(R.id.tvLname);
        tvMob=(TextView)findViewById(R.id.tvMob);
        tvCalStatus=(TextView)findViewById(R.id.tvCalStatus);
        tvAction=(TextView)findViewById(R.id.tvAction);
        tvRemarks=(TextView)findViewById(R.id.tvRemarks);
        tvCategory=(TextView)findViewById(R.id.tvCategory);
        tvModel=(TextView)findViewById(R.id.tvModel);
        tvMachineStatus=(TextView)findViewById(R.id.tvMachineStatus);

        String next = "<font color='#EE0000'>*</font>";
        String next1 = "<font color='#EE0000'>(Enter additional Cust.-Machine information)</font>";

        tvTitle.setText(Html.fromHtml("Customer's Tile:"));
        tvFname.setText(Html.fromHtml("Customer's Name:"));
        tvLname.setText(Html.fromHtml("Customer's Last Name:" ));
        tvMob.setText(Html.fromHtml("Customer's Mobile Number:"));
        tvCalStatus.setText(Html.fromHtml("Call Connected Status:" + next));
        tvAction.setText(Html.fromHtml("Action:" + next));
        tvRemarks.setText(Html.fromHtml("Remarks:" + next+ next1));
        tvCategory.setText(Html.fromHtml("Select Category:" + next));
        tvModel.setText(Html.fromHtml("Select Model:" + next));
        tvMachineStatus.setText(Html.fromHtml("Select Machine Status:" + next));


        spTitle=(Spinner) findViewById(R.id.spTitle);
        spCallStatus=(Spinner) findViewById(R.id.spCallStatus);
        spAction=(Spinner) findViewById(R.id.spAction);

        llLoader=(LinearLayout)findViewById(R.id.llLoader);
        llMain=(LinearLayout)findViewById(R.id.llMain);

        etFirstName=(EditText) findViewById(R.id.etFirstName);
        etLastName=(EditText) findViewById(R.id.etLastName);
        etMobNumber=(EditText) findViewById(R.id.etMobNumber);
        etRemarks=(EditText) findViewById(R.id.etRemarks);

        btnSubmit=(Button)findViewById(R.id.btnSubmit);

        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);

        etEmailId=(EditText)findViewById(R.id.etEmailId);
        spCategory=(Spinner)findViewById(R.id.spCategory);
        spModel=(Spinner)findViewById(R.id.spModel);
        spMachineStatus=(Spinner)findViewById(R.id.spMachineStatus);
        spIFBProduct=(Spinner)findViewById(R.id.spIFBProduct);
        llMachineStatus=(LinearLayout)findViewById(R.id.llMachineStatus);
        yesnoList.add("Please Select");
        yesnoList.add("Yes");
        yesnoList.add("No");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (CustomerCallingManageManualActivity.this, android.R.layout.simple_spinner_item,
                        yesnoList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIFBProduct.setAdapter(spinnerArrayAdapter);






    }




    private void setAction(String status) {
        Log.d("hitr", "2");
        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=63&ID=0&ID1=0&ID2="+status+"&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        final ProgressDialog pd=new ProgressDialog(CustomerCallingManageManualActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseTitle", response);
                        pd.dismiss();
                        actionList.clear();
                        mActionList.clear();
                        actionList.add("Please select");
                        mActionList.add(new SpinnerItemModule("0", "0"));

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
                                    actionList.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    mActionList.add(itemModule);

                                }



                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (CustomerCallingManageManualActivity.this, android.R.layout.simple_spinner_item,
                                                actionList); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spAction.setAdapter(spinnerArrayAdapter);




                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CustomerCallingManageManualActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "title");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerCallingManageManualActivity.this);
        requestQueue.add(stringRequest);

    }




    private void setStatus(){
        callStatusList.add("Please select");
        callStatusList.add("Yes");
        callStatusList.add("No");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (CustomerCallingManageManualActivity.this, android.R.layout.simple_spinner_item,
                        callStatusList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCallStatus.setAdapter(spinnerArrayAdapter);
        setCategory();
    }

    private void onClick(){
        spCallStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){
                    callConnectedStatus=callStatusList.get(i);
                }
                if (callConnectedStatus.equals("Yes")){
                    setAction("Y");
                }else if (callConnectedStatus.equals("No")){
                    setAction("N");
                }
               
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){
                    titleId=mTitleList.get(i).getItemId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){
                    actionId=mActionList.get(i).getItemId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spIFBProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){
                    ifbProduct=yesnoList.get(i);
                }
                if (ifbProduct.equals("Yes")){
                    llMachineStatus.setVisibility(View.VISIBLE);
                }else {
                    llMachineStatus.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    String catId=moduleCategory.get(position).getItemId();
                    setModel(catId);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    modelId=moduleModel.get(position).getItemId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spMachineStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    machineStatusId=machineStatus.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (etFirstName.getText().toString().length()>0){

                            if (etMobNumber.getText().toString().length()>9){
                                if (!callConnectedStatus.equals("")){
                                    if (!actionId.equals("")){
                                        if (etRemarks.getText().toString().length()>4){
                                            if (!ifbProduct.equals("")){

                                                postChecking();

                                            }else {
                                                Toast.makeText(CustomerCallingManageManualActivity.this,"Please Select Customer Purchased IFB Product or Not",Toast.LENGTH_LONG).show();
                                            }

                                        }else {
                                            Toast.makeText(CustomerCallingManageManualActivity.this,"Please enter atleast 5 words",Toast.LENGTH_LONG).show();
                                        }





                                    }else {
                                        Toast.makeText(getApplicationContext(),"Please select acion",Toast.LENGTH_LONG).show();
                                    }

                                }else {
                                    Toast.makeText(getApplicationContext(),"Please select call connected status",Toast.LENGTH_LONG).show();
                                }

                            }else {
                                Toast.makeText(getApplicationContext(),"Please enter mobile number",Toast.LENGTH_LONG).show();
                            }

                    }else {
                        Toast.makeText(getApplicationContext(),"Please enter first name",Toast.LENGTH_LONG).show();
                    }


            }
        });
    }


    private void postCallUpdation() {
        String fName=etFirstName.getText().toString();
        String lName=etLastName.getText().toString();
        String mob=etMobNumber.getText().toString();
        String remarks=etRemarks.getText().toString();
        String useriD=prefManager.getUserId();
         serialNo=modelId+"-"+machineStatusId;
        Log.d("serialno",serialNo);

        final ProgressDialog pd = new ProgressDialog(CustomerCallingManageManualActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        AndroidNetworking.upload( AppController.APIURL+"api/Post_EmployeeCustomerCallTracker")
                .addMultipartParameter("CallToken", "ME")
                .addMultipartParameter("AEMEmployeeID", useriD)
                .addMultipartParameter("Title", "1")
                .addMultipartParameter("FirstName", fName)
                .addMultipartParameter("LastName", "")
                .addMultipartParameter("CustomerPhNo",  etMobNumber.getText().toString())
                .addMultipartParameter("CallConnectedStatus", callConnectedStatus)
                .addMultipartParameter("ActionId", actionId)
                .addMultipartParameter("Remark", remarks)
                .addMultipartParameter("UserId", useriD)
                .addMultipartParameter("SerialNo", serialNo)
                .addMultipartParameter("CustomerEmail", etEmailId.getText().toString())
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
                        boolean responseStatus=job1.optBoolean("responseStatus");
                        Log.d("responseText", responseText);
                        if (responseStatus) {
                            successAlert(responseText);
                            pd.dismiss();

                        } else {
                            pd.dismiss();
                            Toast.makeText(CustomerCallingManageManualActivity.this, responseText, Toast.LENGTH_LONG).show();

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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CustomerCallingManageManualActivity.this, R.style.CustomDialogNew);
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
                Intent intent = new Intent(CustomerCallingManageManualActivity.this, CallingReportActivity.class);
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




    private void postFun(){
        if (etEmailId.getText().toString().length()>0){
            if (etEmailId.getText().toString().contains("@")){
                postCallUpdation();
            }else {
                Toast.makeText(CustomerCallingManageManualActivity.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
            }

        }else {
            postCallUpdation();
        }

    }

    private void setCategory() {
        Log.d("hitr", "1");

        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=4&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        final ProgressDialog pd=new ProgressDialog(CustomerCallingManageManualActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        pd.dismiss();
                        category.clear();
                        moduleCategory.clear();
                        category.add("Please select");
                        moduleCategory.add(new SpinnerItemModule("0", "0"));

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
                                    category.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleCategory.add(itemModule);

                                }
                                setMachineStatus();




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (CustomerCallingManageManualActivity.this, android.R.layout.simple_spinner_item,
                                                category); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCategory.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CustomerCallingManageManualActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pd.dismiss();
                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerCallingManageManualActivity.this);
        requestQueue.add(stringRequest);

    }
    private void setMachineStatus() {
        Log.d("hitr", "1");

        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=65&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        final ProgressDialog pd=new ProgressDialog(CustomerCallingManageManualActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        pd.dismiss();
                        machineStatus.clear();

                        machineStatus.add("Please select");


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
                                    machineStatus.add(value);


                                }




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (CustomerCallingManageManualActivity.this, android.R.layout.simple_spinner_item,
                                                machineStatus); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spMachineStatus.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CustomerCallingManageManualActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pd.dismiss();
                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerCallingManageManualActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setModel(String categoryId) {
        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=18&ID=" + categoryId + "&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("modelinput", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseModel", response);
                        progressBar.dismiss();
                        model.clear();
                        moduleModel.clear();
                        model.add("Please select");
                        moduleModel.add(new SpinnerItemModule("0", "0" ));

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
                                    model.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleModel.add(itemModule);

                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (CustomerCallingManageManualActivity.this, android.R.layout.simple_spinner_item,
                                                model); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spModel.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CustomerCallingManageManualActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerCallingManageManualActivity.this);
        requestQueue.add(stringRequest);

    }

    private void postChecking() {
        if (ifbProduct.equals("Yes")) {
            if (!modelId.equals("")) {
                if (!machineStatusId.equals("")) {
                    postFun();
                } else {
                    Toast.makeText(CustomerCallingManageManualActivity.this, "Please select Machiene Status", Toast.LENGTH_LONG).show();

                }
            } else {
                Toast.makeText(CustomerCallingManageManualActivity.this, "Please select Model", Toast.LENGTH_LONG).show();
            }
        }else {
            postFun();
        }
    }

}
