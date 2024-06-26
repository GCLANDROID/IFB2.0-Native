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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class ModelExchangeActivity extends AppCompatActivity {
    TextView tvTicketText, tvCategoryName, tvModelName, tvCustomer_NameName, tvCustomer_PhnName, tvPinCodeName, tvDateName, tvSchemeName;
    TextView tvCategory, tvModel, tvCustomerName, tvCustomerPhn, tvCustomerEmail, tvPinCode, tvDate, tvInvoiceNo, tvInvoiceValue;
    EditText etTicket;
    Button btnSubmit;
    LinearLayout llLoader, llMain, llNoData, llYes, llNo;
    PrefManager prefManager;
    AlertDialog alerDialog1;
    String responseText;
    EditText etRemarks;
    Button btnSave;
    String CategoryID;
    String ModelID;
    String SalesDate;
    String CustomerName;
    String CustomerPhNo;
    String CustomerPinCode;
    String CustomerEmail = "NA";
    String InvoiceNo = "NA";
    String FinanceScheme;
    String InvoiceValue;
    String CategoryName;
    String invoiceNumber="NA";
    String customerEmail;
    String name;
    ImageView imgBack,imgHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_exchange);
        initialize();
        onClick();
    }

    private void initialize() {
        prefManager = new PrefManager(ModelExchangeActivity.this);
        tvTicketText = (TextView) findViewById(R.id.tvTicketText);
        String color = "<font color='#EE0000'>*</font>";
        String text = "Returned Ticket No:";
        tvTicketText.setText(Html.fromHtml(text + color));

        tvCategoryName = (TextView) findViewById(R.id.tvCategoryName);
        String categorytext = "Product Category: ";
        tvCategoryName.setText(Html.fromHtml(categorytext + color));

        tvModelName = (TextView) findViewById(R.id.tvModelName);
        String modeltext = "Product Model: ";
        tvModelName.setText(Html.fromHtml(modeltext + color));

        tvCustomer_NameName = (TextView) findViewById(R.id.tvCustomer_NameName);
        String customernameText = "Customer Name: ";
        tvCustomer_NameName.setText(Html.fromHtml(customernameText + color));

        tvCustomer_PhnName = (TextView) findViewById(R.id.tvCustomer_PhnName);
        String customerohnname = "Customer Phone: ";
        tvCustomer_PhnName.setText(Html.fromHtml(customerohnname + color));

        tvPinCodeName = (TextView) findViewById(R.id.tvPinCodeName);
        String pincodeName = "Delivery Pincode: ";
        tvPinCodeName.setText(Html.fromHtml(pincodeName + color));

        tvDateName = (TextView) findViewById(R.id.tvDateName);
        String dateName = "Invoice Date: ";
        tvDateName.setText(Html.fromHtml(dateName + color));

        tvSchemeName = (TextView) findViewById(R.id.tvSchemeName);
        String schemename = "Under Finance Scheme: ";
        tvSchemeName.setText(Html.fromHtml(schemename + color));

        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvModel = (TextView) findViewById(R.id.tvModel);
        tvCustomerName = (TextView) findViewById(R.id.tvCustomerName);
        tvCustomerPhn = (TextView) findViewById(R.id.tvCustomerPhn);
        tvCustomerEmail = (TextView) findViewById(R.id.tvCustomerEmail);
        tvPinCode = (TextView) findViewById(R.id.tvPinCode);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvInvoiceNo = (TextView) findViewById(R.id.tvInvoiceNo);
        tvInvoiceValue = (TextView) findViewById(R.id.tvInvoiceValue);

        etTicket = (EditText) findViewById(R.id.etTicket);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        llNoData = (LinearLayout) findViewById(R.id.llNoData);

        llYes = (LinearLayout) findViewById(R.id.llYes);
        llNo = (LinearLayout) findViewById(R.id.llNo);
        etRemarks = (EditText) findViewById(R.id.etRemarks);

        btnSave = (Button) findViewById(R.id.btnSave);
        invoiceNumber="NA";
        Log.d("invoiceNumber",invoiceNumber);

        imgHome=(ImageView)findViewById(R.id.imgHome);
        imgBack=(ImageView)findViewById(R.id.imgBack);

    }

    private void getItemList() {
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        String surl =  AppController.APIURL+"api/ModelExchange?TicketNo=" + etTicket.getText().toString() + "&AEMEmployeeID=" + prefManager.getUserId() + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputSalesReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

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
                                    JSONObject obj = responseData.getJSONObject(i);
                                    SalesDate = obj.optString("_SalesDate");
                                    tvDate.setText(SalesDate);
                                    Log.d("SalesDate",SalesDate);

                                    CustomerName = obj.optString("CustomerName");
                                    tvCustomerName.setText(CustomerName);
                                    name=CustomerName.replaceAll("\\s+","-");


                                    CustomerPhNo = obj.optString("CustomerPhNo");
                                    tvCustomerPhn.setText(CustomerPhNo);

                                    CustomerPinCode = obj.optString("CustomerPinCode");
                                    tvPinCode.setText(CustomerPinCode);

                                    CustomerEmail = obj.optString("CustomerEmail");
                                    if (!CustomerEmail.equals("")) {
                                        tvCustomerEmail.setText(CustomerEmail);
                                        customerEmail=CustomerEmail;
                                    } else {
                                        tvCustomerEmail.setText("");
                                        customerEmail="NA";
                                    }

                                    InvoiceNo = obj.optString("InvoiceNo");
                                    if (InvoiceNo.equals("")) {
                                        tvInvoiceNo.setText("");

                                    } else {
                                        tvInvoiceNo.setText(InvoiceNo);



                                    }


                                    FinanceScheme = obj.optString("FinanceScheme");
                                    if (FinanceScheme.equals("0")) {
                                        llYes.setVisibility(View.GONE);
                                        llNo.setVisibility(View.VISIBLE);
                                    } else {
                                        llYes.setVisibility(View.VISIBLE);
                                        llNo.setVisibility(View.GONE);
                                    }

                                    InvoiceValue = obj.optString("InvoiceValue");
                                    tvInvoiceValue.setText(InvoiceValue);

                                    CategoryID = obj.getString("CategoryID");
                                    ModelID = obj.optString("ModelID");
                                    CategoryName = obj.optString("CategoryName");
                                    tvCategory.setText(CategoryName);
                                    String ModelName = obj.optString("ModelName");
                                    tvModel.setText(ModelName);


                                }

                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                llNoData.setVisibility(View.GONE);

                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {
                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.GONE);
                                llNoData.setVisibility(View.VISIBLE);


                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ModelExchangeActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                llNoData.setVisibility(View.VISIBLE);

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(ModelExchangeActivity.this);
        requestQueue.add(stringRequest);
    }

    private void onClick() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItemList();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  postModelExchng();
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
                Intent intent=new Intent(ModelExchangeActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    private void postModelExchng(){
        String surl =  AppController.APIURL+"api/post_ModelExchange?AEMEmployeeID="+prefManager.getUserId()+"&SalesDate="+SalesDate+"&CategoryID="+CategoryID+"&ModelID="+ModelID+"&CustomerName="+name+"&CustomerPhNo="+CustomerPhNo+"&CustomerPinCode="+CustomerPinCode+"&CustomerEmail="+customerEmail+"&InvoiceNo="+invoiceNumber+"&FinanceScheme="+FinanceScheme+"&InvoiceValue="+InvoiceValue+"&TicketNo="+etTicket.getText().toString()+"&Remarks=test&UserID="+prefManager.getUserId()+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("modelinput",surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLeave", response);
                        progressBar.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();
                                successAlert();

                            }


                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ModelExchangeActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(ModelExchangeActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();

                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(ModelExchangeActivity.this);
        requestQueue.add(stringRequest);

    }


    private void successAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ModelExchangeActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText(responseText);

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();


            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }

}
