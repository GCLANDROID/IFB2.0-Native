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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.DeliveyDetailsAdapter;
import io.cordova.ifb.module.DeliveryDetailsModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class DeliveryDetailsActivity extends AppCompatActivity {

    ArrayList<DeliveryDetailsModel> itemList=new ArrayList<>();
    RecyclerView rvItem;
    LinearLayout llMain,llLoader,llAgain,llNoData;
    int y;
    String year,month;
    String financialYear;
    PrefManager prefManager;
    AlertDialog alertDialog,alertDialog1,alertDialog2;
    TextView tvYear,tvMonth;
    ImageView imgBack,imgHome;
    LinearLayout llReport;
    AlertDialog alerDialog1;
    Spinner spFin,spMonth;
    ArrayList<String>monthList=new ArrayList<>();
    ArrayList<String>yearList=new ArrayList<>();
    Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);
        initView();

        onClick();
    }

    private void initView(){
        prefManager=new PrefManager(DeliveryDetailsActivity.this);
        rvItem=(RecyclerView)findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(DeliveryDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);
        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        llAgain = (LinearLayout) findViewById(R.id.llAgain);
        llNoData = (LinearLayout) findViewById(R.id.llNodata);
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
        Log.d("financialYear",financialYear);

        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);
        llReport=(LinearLayout)findViewById(R.id.llReport);
        spMonth=(Spinner) findViewById(R.id.spMonth);
        spFin=(Spinner) findViewById(R.id.spFin);

        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");

        yearList.add("2023-2024");
        yearList.add("2024-2025");
        yearList.add("2025-2026");
        yearList.add("2026-2027");

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>
                (DeliveryDetailsActivity.this, android.R.layout.simple_spinner_item,
                        monthList); //selected item will look like a spinner set from XML
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonth.setAdapter(monthAdapter);

        int pos=monthList.indexOf(month);
        spMonth.setSelection(pos);


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>
                (DeliveryDetailsActivity.this, android.R.layout.simple_spinner_item,
                        yearList); //selected item will look like a spinner set from XML
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFin.setAdapter(yearAdapter);

        int yearpos=yearList.indexOf(financialYear);
        spFin.setSelection(yearpos);

        btnShow=(Button) findViewById(R.id.btnShow);


    }

    private void onClick(){

        spFin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                financialYear=yearList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                month=monthList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemlist();
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
                Intent intent=new Intent(DeliveryDetailsActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeliveryDetailsActivity.this,SalesLeadReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



    }

    private void getItemlist(){
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        llAgain.setVisibility(View.GONE);
        String surl =  AppController.APIURL+"api/getEmployeeReferenceSalesDelivery?ReferenceNo=0&UserID="+prefManager.getUserId()+"&FinancialYear="+financialYear+"&Month="+month+"&Operation=1&SubOperation=1&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputSalesReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        // attendabceInfiList.clear();
                        itemList.clear();

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
                                    String SalesEntryDate=obj.optString("SalesEntryDate");
                                    String ReferenceNo=obj.optString("ReferenceNo");
                                    String CustomerName=obj.optString("CustomerName");
                                    String CategoryName=obj.optString("CategoryName");
                                    String CustomerPhNo=obj.optString("CustomerPhNo");
                                    String CustomerAlternateNumber=obj.optString("CustomerAlternateNumber");
                                    String ModelCode=obj.optString("ModelCode");
                                    String InstallationBy=obj.optString("InstallationBy");
                                    String WiFiDeviceStatus=obj.optString("WiFiDeviceStatus");
                                    String SalesType=obj.optString("SalesType");
                                    String DeliveryAddress=obj.optString("DeliveryAddress");
                                    String CustomerPinCode=obj.optString("CustomerPinCode");
                                    String Quantity=obj.optString("Quantity");
                                    String CategoryID=obj.optString("CategoryID");
                                    String UnderExchange=obj.optString("UnderExchange");
                                    String FinanceScheme=obj.optString("FinanceScheme");
                                    String FirstName=obj.optString("FirstName");
                                    String LastName=obj.optString("LastName");
                                    String Month=obj.optString("Month");
                                    String FinancialYear=obj.optString("FinancialYear");
                                    String InvoiceValue=obj.optString("InvoiceValue");



                                    DeliveryDetailsModel calModel=new DeliveryDetailsModel(ReferenceNo,SalesEntryDate,CustomerName,CategoryName);
                                    calModel.setContactNumber(CustomerPhNo);
                                    calModel.setAltNumber(CustomerAlternateNumber);
                                    calModel.setModelCode(ModelCode);
                                    calModel.setInstallationBy(InstallationBy);
                                    calModel.setWiFiDeviceStatus(WiFiDeviceStatus);
                                    calModel.setSalesType(SalesType);
                                    calModel.setQuantity(Quantity);
                                    calModel.setAddress(DeliveryAddress);
                                    calModel.setPincode(CustomerPinCode);
                                    calModel.setCategoryID(CategoryID);
                                    calModel.setUnderExchange(UnderExchange);
                                    calModel.setFinancialScheme(FinanceScheme);
                                    calModel.setFirstName(FirstName);
                                    calModel.setLastName(LastName);
                                    calModel.setMonth(Month);
                                    calModel.setFinancialyear(FinancialYear);
                                    calModel.setInvoiceValue(InvoiceValue);
                                    itemList.add(calModel);


                                }
                                setAdapter();

                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                llNoData.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);
                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {
                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.GONE);
                                llNoData.setVisibility(View.VISIBLE);
                                llAgain.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DeliveryDetailsActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                llNoData.setVisibility(View.GONE);
                llAgain.setVisibility(View.VISIBLE);

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryDetailsActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setAdapter(){
        DeliveyDetailsAdapter sAdpater=new DeliveyDetailsAdapter(itemList, DeliveryDetailsActivity.this);
        rvItem.setAdapter(sAdpater);
    }

    public void cancel(String refNo){

        final ProgressDialog pd = new ProgressDialog(DeliveryDetailsActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        AndroidNetworking.upload( AppController.APIURL+"api/post_EmployeeSalesManageV1")
                .addMultipartParameter("TransNo", "0")
                .addMultipartParameter("ReferenceNo", refNo)
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("SalesDate", "0")
                .addMultipartParameter("FinancialYear", "0")
                .addMultipartParameter("Month", "0")
                .addMultipartParameter("CategoryID", "0")
                .addMultipartParameter("Quantity", "0")
                .addMultipartParameter("UserID", prefManager.getUserId())
                .addMultipartParameter("BranchID", "0")
                .addMultipartParameter("ModelID", "0")
                .addMultipartParameter("CustomerName", "0")
                .addMultipartParameter("CustomerPhNo", "0")
                .addMultipartParameter("CustomerPinCode", "0")
                .addMultipartParameter("CustomerEmail", "0")
                .addMultipartParameter("InvoiceNo", "0")
                .addMultipartParameter("FinanceScheme", "0")
                .addMultipartParameter("DeliveryAddress", "0")
                .addMultipartParameter("FirstName", "0")
                .addMultipartParameter("LastName", "0")
                .addMultipartParameter("CustomerAlternateNumber", "0")
                .addMultipartParameter("HouseNo", "0")
                .addMultipartParameter("StreetName", "0")
                .addMultipartParameter("Landmark", "0")
                .addMultipartParameter("Title", "0")
                .addMultipartParameter("StateID", "0")
                .addMultipartParameter("City", "0")
                .addMultipartParameter("InvoiceValue", "0")
                .addMultipartParameter("Remarks", "0")
                .addMultipartParameter("UnderExchange", "0")
                .addMultipartParameter("Area", "0")
                .addMultipartParameter("SalesEntryFlag", "0")
                .addMultipartParameter("Invoicecopy", "")
                .addMultipartParameter("SerialNo", "0")
                .addMultipartParameter("Delivery_Date", "0")
                .addMultipartParameter("Delivery_Remarks", "0")
                .addMultipartParameter("Operation", "3")
                .addMultipartParameter("SubOperation", "3")
                .addMultipartParameter("DisplayMatrix_Sold", "")
                .addMultipartParameter("CSD_Sales", "")
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
                            Toast.makeText(DeliveryDetailsActivity.this, responseText, Toast.LENGTH_LONG).show();

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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DeliveryDetailsActivity.this, R.style.CustomDialogNew);
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
               getItemlist();
            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(false);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //getItemlist();
    }
}