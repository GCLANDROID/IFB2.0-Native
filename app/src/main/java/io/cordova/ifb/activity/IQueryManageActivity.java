package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;
import okhttp3.OkHttpClient;

public class IQueryManageActivity extends AppCompatActivity {
    LinearLayout llDate,llOther,llLoader,llCatOther;
    TextView tvDate;
    Spinner spProduct,spCategory;
    EditText etOther,etCatOther,etContactPerson,etContactNumber,etLandLine,etEmailId,etName,etAddress,etPincode,etRemarks;
    ScrollView scMain;
    ArrayList<SpinnerItemModule>mProductList=new ArrayList<>();
    ArrayList<String>productList=new ArrayList<>();
    ArrayList<SpinnerItemModule>mCatList=new ArrayList<>();
    ArrayList<String>catList=new ArrayList<>();
    PrefManager prefManager;
    Button btnSubmit;
    String productId="";
    String catId="";
    String productName = "";
    TextView tvDateTitle,tvProductTitle,tvContactPersonTitle,tvContactNumberTitle,tvCustomerTitle,tvNameTitle;
    String date="";
    AlertDialog alerDialog1;
    String catName="";
    String finalcialchecking;
    String month,year;
    TextView tvSDate;
    ImageView imgBack,imgHome;
    TextView tvIQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_query_manage);
        initView();
        setProductList();
        onClick();
    }

    private void initView(){
        prefManager=new PrefManager(IQueryManageActivity.this);
        OkHttpClient okHttpClient =
                AppController.getUnsafeOkHttpClient();

        AndroidNetworking.initialize(
                getApplicationContext(),
                okHttpClient
        );
        llDate=(LinearLayout)findViewById(R.id.llDate);
        llOther=(LinearLayout)findViewById(R.id.llOther);
        llCatOther=(LinearLayout)findViewById(R.id.llCatOther);
        llLoader=(LinearLayout)findViewById(R.id.llLoader);

        tvDate=(TextView)findViewById(R.id.tvDate);
        tvDateTitle=(TextView)findViewById(R.id.tvDateTitle);
        tvProductTitle=(TextView)findViewById(R.id.tvProductTitle);
        tvContactPersonTitle=(TextView)findViewById(R.id.tvContactPersonTitle);
        tvContactNumberTitle=(TextView)findViewById(R.id.tvContactNumberTitle);
        tvCustomerTitle=(TextView)findViewById(R.id.tvCustomerTitle);
        tvNameTitle=(TextView)findViewById(R.id.tvNameTitle);



        spProduct=(Spinner)findViewById(R.id.spProduct);
        spCategory=(Spinner)findViewById(R.id.spCategory);

        etOther=(EditText)findViewById(R.id.etOther);
        etCatOther=(EditText)findViewById(R.id.etCatOther);
        etContactPerson=(EditText)findViewById(R.id.etContactPerson);
        etContactNumber=(EditText)findViewById(R.id.etContactNumber);
        etLandLine=(EditText)findViewById(R.id.etLandLine);
        etEmailId=(EditText)findViewById(R.id.etEmailId);
        etName=(EditText)findViewById(R.id.etName);
        etAddress=(EditText)findViewById(R.id.etAddress);
        etPincode=(EditText)findViewById(R.id.etPincode);
        etRemarks=(EditText)findViewById(R.id.etRemarks);

        scMain=(ScrollView)findViewById(R.id.scMain);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);

        String next = "<font color='#EE0000'>*</font>";

        tvDateTitle.setText(Html.fromHtml("Date(mm/ddd/yyyy):" + next));
        tvProductTitle.setText(Html.fromHtml("Product:" + next));
        tvContactPersonTitle.setText(Html.fromHtml("Contact Person:" + next));
        tvContactNumberTitle.setText(Html.fromHtml("Contact Number:" + next));
        tvCustomerTitle.setText(Html.fromHtml("Customer Category:" + next));
        tvNameTitle.setText(Html.fromHtml("Name of the Organisation:" + next));

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
            finalcialchecking = futureyear + "-" + year;
        } else if (month.equals("February")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else if (month.equals("March")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else {
            int futureyear = y + 1;
            finalcialchecking = year + "-" + futureyear;
        }

        tvSDate=(TextView)findViewById(R.id.tvSDate);
        tvSDate.setText("For the month of "+month+"("+finalcialchecking+")");

        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);

        tvIQuery=(TextView)findViewById(R.id.tvIQuery);

    }

    private void setProductList() {
        Log.d("hitr", "1");

        String surl =  AppController.APIV2URL+"api/CommonDDL?ModuleNo=45&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        llLoader.setVisibility(View.VISIBLE);
        scMain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        llLoader.setVisibility(View.VISIBLE);
                        scMain.setVisibility(View.GONE);
                        productList.clear();
                        mProductList.clear();
                        productList.add("Please select");
                        mProductList.add(new SpinnerItemModule("0", "0"));

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
                                    productList.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    mProductList.add(itemModule);

                                }

                                setTotalIQuery();


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (IQueryManageActivity.this, android.R.layout.simple_spinner_item,
                                                productList); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spProduct.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(IQueryManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(IQueryManageActivity.this);
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(IQueryManageActivity.this);

        requestQueue.add(stringRequest);

    }

    private void setTotalIQuery() {
        Log.d("hitr", "1");

        String surl =  AppController.APIV2URL+"api/get_EmployeeiQuery?UserID="+prefManager.getUserId()+"&FinancialYear="+finalcialchecking+"&Month="+month+"&Operation=2&SecurityCode="+prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        llLoader.setVisibility(View.VISIBLE);
        scMain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        llLoader.setVisibility(View.VISIBLE);
                        scMain.setVisibility(View.GONE);


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
                                    String Details = obj.optString("Details");
                                    tvSDate.setText(Details);


                                }

                                setCategoryList();





                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(IQueryManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(IQueryManageActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(IQueryManageActivity.this);

        requestQueue.add(stringRequest);

    }

    private void setCategoryList() {
        Log.d("hitr", "2");
        String surl =  AppController.APIV2URL+"api/CommonDDL?ModuleNo=44&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        llLoader.setVisibility(View.VISIBLE);
        scMain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseTitle", response);
                        llLoader.setVisibility(View.GONE);
                        scMain.setVisibility(View.VISIBLE);
                        catList.clear();
                        mCatList.clear();
                        catList.add("Please select");
                        mCatList.add(new SpinnerItemModule("0", "0"));

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
                                        (IQueryManageActivity.this, android.R.layout.simple_spinner_item,
                                                catList); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCategory.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(IQueryManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "title");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(IQueryManageActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(IQueryManageActivity.this);

        requestQueue.add(stringRequest);

    }

    private void onClick(){
        spProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i>0){
                    productId=mProductList.get(i).getItemId();
                    productName=mProductList.get(i).getItem();
                }
                if (productName.equals("OTHER")){
                    llOther.setVisibility(View.VISIBLE);
                }else {
                    llOther.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0){
                    catId=mCatList.get(i).getItemId();
                    catName=mCatList.get(i).getItem();
                }
                if (catName.equals("Others")){
                    llCatOther.setVisibility(View.VISIBLE);
                }else {
                    llCatOther.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
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
                Intent intent=new Intent(IQueryManageActivity.this,DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        tvSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IQueryManageActivity.this,IQueriesReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!date.equals("")){
                    if (!productId.equals("")){
                        if (etContactPerson.getText().toString().length()>0){
                            if (etContactNumber.getText().toString().length()>9){
                                if (!catId.equals("")){
                                    if (etName.getText().toString().length()>0){
                                        postFunc();

                                    }else {
                                        Toast.makeText(IQueryManageActivity.this,"Please enter Organisation name",Toast.LENGTH_LONG).show();
                                    }

                                }else {
                                    Toast.makeText(IQueryManageActivity.this,"Please select Customer Category",Toast.LENGTH_LONG).show();
                                }

                            }else {
                                Toast.makeText(IQueryManageActivity.this,"Please enter contact number",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(IQueryManageActivity.this,"Please enter contact person",Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(IQueryManageActivity.this,"Please Select Product",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(IQueryManageActivity.this,"Please select date",Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private void postEnquery() {

        final ProgressDialog pd = new ProgressDialog(IQueryManageActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        AndroidNetworking.upload( AppController.APIV2URL+"api/post_EmployeeiQuery")
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("QueryDate", date)
                .addMultipartParameter("CategoryID", productId)
                .addMultipartParameter("OtherProduct", etOther.getText().toString())
                .addMultipartParameter("ContactPerson", etContactPerson.getText().toString())
                .addMultipartParameter("ContactNo", etContactNumber.getText().toString())
                .addMultipartParameter("LandLineNo", etLandLine.getText().toString())
                .addMultipartParameter("EmailID", etEmailId.getText().toString())
                .addMultipartParameter("CustomerCatId", catId)
                .addMultipartParameter("OtherCategory", etCatOther.getText().toString())
                .addMultipartParameter("NameOfOrganisation", etName.getText().toString())
                .addMultipartParameter("Address", etAddress.getText().toString())
                .addMultipartParameter("Pincode", etPincode.getText().toString())
                .addMultipartParameter("Remarks", etRemarks.getText().toString())
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
                            Toast.makeText(IQueryManageActivity.this, responseText, Toast.LENGTH_LONG).show();

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
        final int year2 = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(IQueryManageActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {





                date = (m + 1) + "/" + d + "/" + y;

                tvDate.setText(date);

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

    private void successAlert(String text) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(IQueryManageActivity.this, R.style.CustomDialogNew);
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
                Intent intent = new Intent(IQueryManageActivity.this, IQueriesReportActivity.class);
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

    private void postFunc(){
        if (productName.equals("OTHER")){
            if (etOther.getText().toString().length()>0){
                postEnquery();
            }else {
                Toast.makeText(IQueryManageActivity.this,"Please enter Other product details",Toast.LENGTH_LONG).show();

            }
        }else if (catName.equals("Others")){
            if (etCatOther.getText().toString().length()>0){
                postEnquery();
            }else {
                Toast.makeText(IQueryManageActivity.this,"Please enter Other Customer Category  details",Toast.LENGTH_LONG).show();

            }
        }else if (catName.equals("Others")||productName.equals("OTHER")){
            if (etOther.getText().toString().length()>0){
                if (etCatOther.getText().toString().length()>0){
                    postEnquery();
                }else {
                    Toast.makeText(IQueryManageActivity.this,"Please enter Other Customer Category  details",Toast.LENGTH_LONG).show();

                }

            }else {
                Toast.makeText(IQueryManageActivity.this,"Please enter Other product details",Toast.LENGTH_LONG).show();
            }

        }else {
            postEnquery();
        }
    }

}
