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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import io.cordova.ifb.module.ModelSpinnerModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;
import okhttp3.OkHttpClient;

public class ReplenshedUpdateActivity extends AppCompatActivity {
    TextView tvSalesDate,tvProductName,tvModelName,tvReplaceMentDate;
    LinearLayout llCalender;
    EditText etRemarks;
    Button btnSubmit;
    String replacementDate;
    String salesDate,productName,ModelName,id;
    String m;
    AlertDialog alerDialog1;
    PrefManager prefManager;
    LinearLayout llReport;
    ImageView imgBack,imgHome;
    LinearLayout llDate,llRemraks,llReplenishedModel;
    String flag;
    String categoryid;
    Spinner spReplenishedModel,spModel;
    ArrayList<String>replenishedModelList=new ArrayList<>();
    LinearLayout llChangeModel,llOldModel;
    String replenishedModel="";
    ArrayList<ModelSpinnerModel> moduleModel = new ArrayList<>();
    ArrayList<String> model = new ArrayList<>();
    String modelcode="";
    int replenshidClick=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replenshed_update);
        initView();
        onClick();
    }

    private void initView(){
        prefManager=new PrefManager(ReplenshedUpdateActivity.this);
        OkHttpClient okHttpClient =
                AppController.getUnsafeOkHttpClient();
        AndroidNetworking.initialize(
                getApplicationContext(),
                okHttpClient
        );

        modelcode=getIntent().getStringExtra("modelcode");
        llOldModel=(LinearLayout)findViewById(R.id.llOldModel);
        llChangeModel=(LinearLayout)findViewById(R.id.llChangeModel);
        replenishedModelList.add("Old Model");
        replenishedModelList.add("Changed Model");
        salesDate=getIntent().getStringExtra("salesDate");
        categoryid=getIntent().getStringExtra("categoryid");
        productName=getIntent().getStringExtra("productName");
        ModelName=getIntent().getStringExtra("ModelName");
        id=getIntent().getStringExtra("id");
        tvSalesDate=(TextView)findViewById(R.id.tvSalesDate);
        tvSalesDate.setText(salesDate);
        tvProductName=(TextView)findViewById(R.id.tvProductName);
        tvProductName.setText(productName);
        tvModelName=(TextView)findViewById(R.id.tvModelName);
        tvModelName.setText(ModelName);
        tvReplaceMentDate=(TextView)findViewById(R.id.tvReplaceMentDate);

        llCalender=(LinearLayout) findViewById(R.id.llCalender);
        etRemarks=(EditText)findViewById(R.id.etRemarks);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = df.format(c);
        replacementDate=currentDate;
        tvReplaceMentDate.setText(currentDate);

        llReport=(LinearLayout)findViewById(R.id.llReport);

        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);
        llReplenishedModel=(LinearLayout)findViewById(R.id.llReplenishedModel);

        llRemraks=(LinearLayout)findViewById(R.id.llRemraks);
        llDate=(LinearLayout)findViewById(R.id.llDate);
        flag=getIntent().getStringExtra("flag");

        if (flag.equals("1")){
            llRemraks.setVisibility(View.VISIBLE);
            llDate.setVisibility(View.VISIBLE);
            btnSubmit.setText("Update");
            llReplenishedModel.setVisibility(View.VISIBLE);
        }else {
            llRemraks.setVisibility(View.GONE);
            llDate.setVisibility(View.GONE);
            llReplenishedModel.setVisibility(View.GONE);
            btnSubmit.setText("DELETE");
        }
        spModel=(Spinner)findViewById(R.id.spModel);
        spReplenishedModel=(Spinner)findViewById(R.id.spReplenishedModel);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (ReplenshedUpdateActivity.this, android.R.layout.simple_spinner_item,
                        replenishedModelList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spReplenishedModel.setAdapter(spinnerArrayAdapter);

        setModel(categoryid);


    }

    private void onClick(){
        spReplenishedModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    replenishedModel=replenishedModelList.get(i);
                    if (replenishedModel.equalsIgnoreCase("Old Model")){
                        llOldModel.setVisibility(View.VISIBLE);
                        llChangeModel.setVisibility(View.GONE);
                        replenshidClick=0;

                    }else {
                        llOldModel.setVisibility(View.GONE);
                        llChangeModel.setVisibility(View.VISIBLE);
                        replenshidClick=1;
                        modelcode="";
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){
                    modelcode=moduleModel.get(i).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        llCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag.equals("1")) {
                    if (replenshidClick==1){
                        if (!modelcode.equals("")){
                            postFunc();
                        }else {
                            Toast.makeText(ReplenshedUpdateActivity.this,"Please Select Model",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        postFunc();
                    }

                }else {
                    deleteFunc();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReplenshedUpdateActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(ReplenshedUpdateActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        int enddate = dayOfMonth + monthOfYear + year;
                        int month = (monthOfYear + 1);
                        if (month==1){
                            m="Jan";
                        }else if (month==2){
                            m="Feb";
                        }else if (month==3){
                            m="Mar";
                        }else if (month==4){
                            m="Apr";
                        }else if (month==5){
                            m="May";
                        }else if (month==6){
                            m="Jun";
                        }else if (month==7){
                            m="Jul";
                        }else if (month==8){
                            m="Aug";
                        }else if (month==9){
                            m="Sep";
                        }else if (month==10){
                            m="Oct";
                        }else if (month==11){
                            m="Nov";
                        }else if (month==12){
                            m="Dec";
                        }

                        replacementDate = dayOfMonth + "-" + m + "-" + year;
                        tvReplaceMentDate.setText(replacementDate);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis() - 1000));
        datePickerDialog.show();

    }

    private void postFunc() {

        final ProgressDialog pd = new ProgressDialog(ReplenshedUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload(AppController.APIV2URL+"api/Post_DisplaymatrixReplaced_V1")
                .addMultipartParameter("Replace_Date", replacementDate)
                .addMultipartParameter("Remarks", etRemarks.getText().toString())
                .addMultipartParameter("DSR_ReferenceNo", id)
                .addMultipartParameter("ModelID", modelcode)
                .addMultipartParameter("Operation", "3")
                .addMultipartParameter("SubOperation", "1")
                .addMultipartParameter("SecurityCode", prefManager.getSecurityCode())
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
                        pd.dismiss();


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
                            Toast.makeText(ReplenshedUpdateActivity.this, responseText, Toast.LENGTH_LONG).show();

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

    private void deleteFunc() {

        final ProgressDialog pd = new ProgressDialog(ReplenshedUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload(AppController.APIV2URL+"api/Post_DisplaymatrixReplaced")
                .addMultipartParameter("Replace_Date", "0")
                .addMultipartParameter("Remarks", "0")
                .addMultipartParameter("DSR_ReferenceNo", id)
                .addMultipartParameter("Operation", "3")
                .addMultipartParameter("SubOperation", "2")
                .addMultipartParameter("SecurityCode", prefManager.getSecurityCode())
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
                        pd.dismiss();


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
                            Toast.makeText(ReplenshedUpdateActivity.this, responseText, Toast.LENGTH_LONG).show();

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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ReplenshedUpdateActivity.this, R.style.CustomDialogNew);
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
                if (flag.equals("1")) {
                    Intent intent = new Intent(ReplenshedUpdateActivity.this, ReplenshedReportActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(ReplenshedUpdateActivity.this, ReplenishedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(false);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }

    private void setModel(String categoryId) {
        String surl = AppController.APIV2URL+"api/CommonDDL?ModuleNo=18&ID=" + categoryId + "&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
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

                        moduleModel.clear();
                        model.clear();
                        model.add("Please Select");
                        moduleModel.add(new ModelSpinnerModel("0","0","0"));


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
                                    ModelSpinnerModel itemModule = new ModelSpinnerModel(value, id, MRP);
                                    moduleModel.add(itemModule);

                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (ReplenshedUpdateActivity.this, android.R.layout.simple_spinner_item,
                                                model); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spModel.setAdapter(spinnerArrayAdapter);







                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ReplenshedUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(ReplenshedUpdateActivity.this);
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(ReplenshedUpdateActivity.this);
        requestQueue.add(stringRequest);



    }
}