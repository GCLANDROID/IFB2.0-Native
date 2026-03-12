package io.cordova.ifb.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

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
import com.developers.imagezipper.ImageZipper;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

import io.cordova.ifb.AndroidXCamera.AndroidXCameraActivity;
import io.cordova.ifb.R;
import io.cordova.ifb.adapter.PlanopgrameHygeneAdapter;
import io.cordova.ifb.adapter.ProductDisplayAdapter;
import io.cordova.ifb.adapter.ScannedBarcodeAdapter;
import io.cordova.ifb.databinding.ActivityPlanogramBinding;
import io.cordova.ifb.module.PlanogramHygeneModel;
import io.cordova.ifb.module.ProductDisplayModel;
import io.cordova.ifb.module.ReportModule;
import io.cordova.ifb.module.ScannedPlanogramBarcodeModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;
import io.cordova.ifb.utility.Util;

public class PlanogramActivity extends AppCompatActivity {
    ActivityPlanogramBinding binding;
    private static final int SCANNER_REQUEST = 801;
    private static final int SCANNER_REQUEST_DISPLAY = 803;
    ArrayList<ScannedPlanogramBarcodeModel>itemList=new ArrayList<>();
    String nosaledate;
    ArrayList<PlanogramHygeneModel>reportitemList=new ArrayList<>();
    PlanopgrameHygeneAdapter adapter;
    ArrayList<String>monthList=new ArrayList<>();
    ArrayList<String>yearList=new ArrayList<>();
    String monthname;
    int y;
    String year,month;
    String financialYear;
    PrefManager prefManager;
    int count=0;
    ArrayList<ProductDisplayModel>productDisplayitemList=new ArrayList<>();
    String productCode="";
    ArrayList<String>totlaList=new ArrayList<>();
    ArrayList<String>doneList=new ArrayList<>();
    ArrayList<String>yesListList=new ArrayList<>();
    ArrayList<String>noListList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_planogram);
        initView();
    }

    private void initView(){
        prefManager=new PrefManager(PlanogramActivity.this);

        binding.llReportHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llReport.setVisibility(View.VISIBLE);
                binding.llManage.setVisibility(View.GONE);

                getReportList("2");

            }
        });


        binding.llManageHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llReport.setVisibility(View.GONE);
                binding.llManage.setVisibility(View.GONE);

                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Calendar cal = Calendar.getInstance();
                nosaledate = df.format(cal.getTime());;
                getReportList("2");
            }
        });

        binding.llOtherScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(PlanogramActivity.this, ODUScannerActivity.class);
                startActivityForResult(intent, SCANNER_REQUEST);*/
                //scanCode();

                Intent intent = new Intent(PlanogramActivity.this,BarcodeScannerActivity.class);
                startActivityForResult(intent, SCANNER_REQUEST);

            }
        });

        binding.llOtherScanReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanogramActivity.this,PlanogramScanReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(PlanogramActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvItem.setLayoutManager(layoutManager);


        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(PlanogramActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvProduct.setLayoutManager(layoutManager1);

        binding.llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.llSearchByMonth.getVisibility()==View.GONE){
                    binding.llSearchByMonth.setVisibility(View.VISIBLE);
                    binding.llSearchByDate.setVisibility(View.GONE);
                    binding.tvSearchTxt.setText("Search by Date");

                    getReportList("1");



                }else {
                    binding.llSearchByMonth.setVisibility(View.GONE);
                    binding.llSearchByDate.setVisibility(View.VISIBLE);
                    binding.tvSearchTxt.setText("Search by Month & Year");
                    getReportList("2");
                }
            }
        });

        binding.imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              showDateDialog();
            }
        });

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        nosaledate = df.format(cal.getTime());;
        binding.tvDate.setText(Util.changeAnyDateFormat(nosaledate,"MM/dd/yyyy","MMM dd,yyyy")+"  ");
        binding.tvScannedDate.setText("Scanned Barcode Details of "+Util.changeAnyDateFormat(nosaledate,"MM/dd/yyyy","MMM dd,yyyy")+" >>");

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

     //   getReportList("2");

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
                (PlanogramActivity.this, android.R.layout.simple_spinner_item,
                        monthList); //selected item will look like a spinner set from XML
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spMonth.setAdapter(monthAdapter);

        int pos=monthList.indexOf(month);
        binding.spMonth.setSelection(pos);


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>
                (PlanogramActivity.this, android.R.layout.simple_spinner_item,
                        yearList); //selected item will look like a spinner set from XML
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.SpYear.setAdapter(yearAdapter);

        int yearpos=yearList.indexOf(financialYear);
        binding.SpYear.setSelection(yearpos);

        LinearLayoutManager palogramlayoutManager
                = new LinearLayoutManager(PlanogramActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvPlanogram.setLayoutManager(palogramlayoutManager);
        binding.spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                month=monthList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.SpYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                financialYear=yearList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReportList("1");
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getDisplayProduct();

        binding.tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totlaList.size()==doneList.size()){
                    onBackPressed();
                    Toast.makeText(PlanogramActivity.this, "Data has been saved successfully", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(PlanogramActivity.this, "Please complete all the products", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void getReportList(String reporttype){
        binding.rvPlanogram.setVisibility(View.VISIBLE);
        binding.llNoData.setVisibility(View.GONE);
        ProgressDialog progressDialog=new ProgressDialog(PlanogramActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String surl =  AppController.APIURL+"api/BarcodePlanogramhygiene?LoginID="+prefManager.getUserId()+"&FinancialYear="+financialYear+"&Month="+month+"&Date="+nosaledate+"&ReportType="+reporttype+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseAttendance", response);
                        progressDialog.dismiss();
                        reportitemList.clear();
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
                                    String ProductBarCode = obj.optString("ProductBarCode");
                                    String ModelName = obj.optString("ModelName");
                                    String Date = obj.optString("Date");

                                   PlanogramHygeneModel model=new PlanogramHygeneModel(ModelName,"","",Date);
                                   model.setBarcode(ProductBarCode);
                                   reportitemList.add(model);

                                   ScannedPlanogramBarcodeModel barcodeModel=new ScannedPlanogramBarcodeModel();
                                   barcodeModel.setCount(i+1);
                                   barcodeModel.setBarcode(ProductBarCode);
                                   barcodeModel.setModel(ModelName);
                                   count=itemList.size()+1;
                                   itemList.add(barcodeModel);



                                }


                                adapter=new PlanopgrameHygeneAdapter(reportitemList,PlanogramActivity.this);
                                binding.rvPlanogram.setAdapter(adapter);

                                binding.llScanReport.setVisibility(View.VISIBLE);

                                ScannedBarcodeAdapter barcodeAdapter=new ScannedBarcodeAdapter(itemList,PlanogramActivity.this);
                                binding.rvItem.setAdapter(barcodeAdapter);
                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {
                                binding.llScanReport.setVisibility(View.GONE);
                                binding.rvPlanogram.setVisibility(View.GONE);
                                binding.llNoData.setVisibility(View.VISIBLE);
                                count=0;
                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PlanogramActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(PlanogramActivity.this);
        requestQueue.add(stringRequest);

    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if ((requestCode == SCANNER_REQUEST)) {


            if (data != null) {
                String resultData = data.getStringExtra("code");
                //Toast.makeText(this, "Result: " + resultData, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Scan Result");
                builder.setMessage(resultData);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        postBarcode(resultData);

                    }
                }).show();
            }



        }else if ((requestCode == SCANNER_REQUEST_DISPLAY)){
            if (data != null) {
                String resultData = data.getStringExtra("code");
                //Toast.makeText(this, "Result: " + resultData, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Scan Result");
                builder.setMessage(resultData);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //postBarcode(resultData);
                        if (resultData.length() >= 6 && productCode.length() >= 6) {

                            String scanFirst6 = resultData.substring(0, 6);
                            String productLast6 = productCode.substring(productCode.length() - 6);

                            if (scanFirst6.equals(productLast6)) {
                                postDisplayProduct("1",productCode);
                            } else {
                                Toast.makeText(PlanogramActivity.this, "Incorrect product scanned", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(PlanogramActivity.this, "Invalid code length", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).show();
            }
        }
    }


    public void updateStatus(int position,boolean status)
    {
        for (int i =0 ;i<reportitemList.size();i++)
        {
            if (i==position)
            {
                reportitemList.get(i).setExpanded(status);
            }
            else
            {
                reportitemList.get(i).setExpanded(false);
            }
        }
        adapter.notifyDataSetChanged();
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
        final DatePickerDialog dialog = new DatePickerDialog(PlanogramActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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

                nosaledate = month+"/"+d+"/"+y;
                binding.tvDate.setText(Util.changeAnyDateFormat(nosaledate,"MM/dd/yyyy","MMM dd,yyyy")+"  ");
                getReportList("2");


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


    private void postBarcode(String barcode) {

        final ProgressDialog pd = new ProgressDialog(PlanogramActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload( AppController.APIURL+"api/post_BarcodePlanogramhygiene")
                .addMultipartParameter("Barcode", barcode)
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("FinancialYear", financialYear)
                .addMultipartParameter("Month", month)
                .addMultipartParameter("BranchID", prefManager.getBranchId())
                .addMultipartParameter("SalesPointID", prefManager.getSalesPointID())
                .addMultipartParameter("Remarks", "QR")
                .addMultipartParameter("UserID", prefManager.getUserId())
                .addMultipartParameter("Operation", "3")

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
                        pd.dismiss();


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        String responseText = job1.optString("responseText");
                        String responseData = job1.optString("responseData");
                        boolean responseStatus = job1.optBoolean("responseStatus");
                        if (responseStatus) {
                            count=count+1;

                            JSONArray jsonArray=job1.optJSONArray("responseData");
                            JSONObject jsonObject=jsonArray.optJSONObject(0);
                            String ModelName=jsonObject.optString("ModelName");
                            String ProductBarCode=jsonObject.optString("ProductBarCode");
                            ScannedPlanogramBarcodeModel model=new ScannedPlanogramBarcodeModel();
                            model.setModel(ModelName);
                            model.setBarcode(ProductBarCode);
                            model.setCount(count);
                            itemList.add(model);

                            Toast.makeText(PlanogramActivity.this, responseText, Toast.LENGTH_LONG).show();


                            ScannedBarcodeAdapter barcodeAdapter=new ScannedBarcodeAdapter(itemList,PlanogramActivity.this);
                            binding.rvItem.setAdapter(barcodeAdapter);

                        } else {

                            Toast.makeText(PlanogramActivity.this, responseText, Toast.LENGTH_LONG).show();

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


    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a barcode");
        options.setTorchEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        scanLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> scanLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Scan Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    postBarcode(result.getContents());

                }
            }).show();
        }
    });



    private void getDisplayProduct() {

        final ProgressDialog pd = new ProgressDialog(PlanogramActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.post(AppController.APIURL + "api/post_IFBDisplayActual")
                .addQueryParameter("Code", prefManager.getMasterId())
                .addQueryParameter("SalesPartyCode", prefManager.getSalesPartyCode())
                .addQueryParameter("ModelCode", "")
                .addQueryParameter("updFlag", "")
                .addQueryParameter("Operation", "2")
                .addQueryParameter("SecurityCode", prefManager.getSecurityCode())

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
                        productDisplayitemList.clear();
                        totlaList.clear();
                        doneList.clear();
                        yesListList.clear();
                        noListList.clear();

                        try {
                            JSONObject job1 = response;
                            JSONArray responseData = job1.optJSONArray("responseData");

                                for (int i=0;i<responseData.length();i++){
                                    JSONObject object=responseData.optJSONObject(i);
                                    String ModelCode=object.optString("ModelCode");
                                    String ModelName=object.optString("ModelName");
                                    int IsScan=object.optInt("IsScan");
                                    int Display_Actual=object.optInt("Display_Actual");
                                    ProductDisplayModel model=new ProductDisplayModel();
                                    model.setModelName(ModelName);
                                    model.setModelCode(ModelCode);
                                    model.setIsScan(IsScan);
                                    model.setDisplay_Actual(Display_Actual);
                                    productDisplayitemList.add(model);
                                    if (Display_Actual==1 || Display_Actual==0){
                                        doneList.add(ModelName);
                                    }
                                    if (Display_Actual==1){
                                        yesListList.add(ModelName);
                                    }

                                    if (Display_Actual==0){
                                        noListList.add(ModelName);
                                    }

                                    totlaList.add(ModelName);
                                }

                                binding.tvTotal.setText("Total Products: \n"+totlaList.size()+"\n");
                                binding.tvDone.setText("Scanned Products: \n"+doneList.size()+"\n");
                                binding.tvPending.setText("Pending Products: \n"+(totlaList.size()-doneList.size())+"\n");
                                binding.tvPercentage.setText("Completion: \n"+(doneList.size()*100)/totlaList.size()+"%\n");
                                binding.tvDisplayAdherence.setText("Display Adherence: \n"+(yesListList.size()*100)/totlaList.size()+"%\n");
                                binding.tvNo.setText("Not Displayed: \n"+noListList.size()+"\n");

                                ProductDisplayAdapter adapter=new ProductDisplayAdapter(productDisplayitemList,PlanogramActivity.this);
                                binding.rvProduct.setAdapter(adapter);

                                if (productDisplayitemList.size()>0){
                                    binding.tvNoData.setVisibility(View.GONE);
                                    binding.rvProduct.setVisibility(View.VISIBLE);
                                    binding.tvFinish.setVisibility(View.VISIBLE);
                                    binding.llCount.setVisibility(View.VISIBLE);
                                }else {
                                    binding.tvNoData.setVisibility(View.VISIBLE);
                                    binding.rvProduct.setVisibility(View.GONE);
                                    binding.tvFinish.setVisibility(View.GONE);
                                    binding.llCount.setVisibility(View.GONE);
                                }





                        } catch (Exception e) {
                            e.printStackTrace();
                        binding.tvNoData.setVisibility(View.VISIBLE);
                        binding.rvProduct.setVisibility(View.GONE);
                        binding.llCount.setVisibility(View.GONE);
                        binding.tvFinish.setVisibility(View.GONE);
                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {

                        pd.dismiss();
                        Toast.makeText(PlanogramActivity.this, "Server is busy .Please try again after some time", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void scanning(int pos){
        Intent intent = new Intent(PlanogramActivity.this,BarcodeScannerActivity.class);
        startActivityForResult(intent, SCANNER_REQUEST_DISPLAY);
        productCode=productDisplayitemList.get(pos).getModelCode();

    }


    public void postDisplayProduct(String updateFlag,String modelCode) {

        final ProgressDialog pd = new ProgressDialog(PlanogramActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.post(AppController.APIURL + "api/post_IFBDisplayActual")
                .addQueryParameter("Code", prefManager.getMasterId())
                .addQueryParameter("SalesPartyCode", prefManager.getSalesPartyCode())
                .addQueryParameter("ModelCode", modelCode)
                .addQueryParameter("updFlag", updateFlag)
                .addQueryParameter("Operation", "1")
                .addQueryParameter("SecurityCode", prefManager.getSecurityCode())

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
                        try {
                            JSONObject job1 = response;
                            productCode="";
                            boolean responseStatus=job1.optBoolean("responseStatus");
                            if (responseStatus){
                                getDisplayProduct();
                            }else {
                                Toast.makeText(PlanogramActivity.this,job1.optString("responseText"),Toast.LENGTH_LONG).show();

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {

                        pd.dismiss();
                        Toast.makeText(PlanogramActivity.this, "Server is busy .Please try again after some time", Toast.LENGTH_LONG).show();
                    }
                });
    }

}