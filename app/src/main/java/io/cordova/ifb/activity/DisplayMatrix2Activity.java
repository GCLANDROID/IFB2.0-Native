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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.DisplayMatrixAdapterAdapter;
import io.cordova.ifb.module.DisplayMatrixModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PostDisplayMatrixService;
import io.cordova.ifb.utility.PrefManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayMatrix2Activity extends AppCompatActivity {

    RecyclerView rvItem;
    ArrayList<DisplayMatrixModel> itemList = new ArrayList<>();
    DisplayMatrixModel compModel;
    PrefManager prefManager;
    LinearLayout llDate;
    TextView tvDate;
    String monthname;
    String year, month, financialYear, nosaledate, finalcialchecking;
    String zoneId = "0";
    String branchId = "0";
    String securitycode;
    String compItem = "";
    private static final String SERVER_PATH =  AppController.APIURL+"api/";
    private PostDisplayMatrixService uploadService;
    ProgressDialog progressDialog;
    String aemId;
    TextView tvSave;
    String tsrItemWithoutBracket;
    DisplayMatrixAdapterAdapter compAdapter;
    ArrayList<String> item = new ArrayList<>();
    ImageView imgBack, imgHome;
    AlertDialog alerDialog1, alertDialog;
    LinearLayout llSureSave;
    TextView tvRemarks;
    EditText etRemarks;
    String showMonth;
    String premonth;
    String showYear;
    String responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_matrix2);
        initialize();
        displayMatrixChecking();
    }

    private void initialize() {
        prefManager = new PrefManager(DisplayMatrix2Activity.this);
        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(DisplayMatrix2Activity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);
        String tsrItem = prefManager.getTSRItem();
        llDate = (LinearLayout) findViewById(R.id.llDate);
        tvDate = (TextView) findViewById(R.id.tvDate);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        nosaledate = df.format(c);

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
            showYear = String.valueOf(y - 1);
            showMonth = "December" + "-" + showYear;
        } else if (month.equals("February")) {
            showMonth = "January" + "-" + year;

        } else if (month.equals("March")) {
            showMonth = "February" + "-" + year;

        } else if (month.equals("April")) {
            showMonth = "March" + "-" + year;

        } else if (month.equals("May")) {
            showMonth = "April" + "-" + year;

        } else if (month.equals("June")) {
            showMonth = "May" + "-" + year;

        } else if (month.equals("July")) {
            showMonth = "June" + "-" + year;

        } else if (month.equals("August")) {
            showMonth = "July" + "-" + year;

        } else if (month.equals("September")) {
            showMonth = "August" + "-" + year;

        } else if (month.equals("October")) {
            showMonth = "September" + "-" + year;

        } else if (month.equals("November")) {
            showMonth = "October" + "-" + year;

        } else if (month.equals("December")) {
            showMonth = "November" + "-" + year;

        }


        if (month.equals("January")) {
            showYear = String.valueOf(y - 1);
            premonth = "December";
        } else if (month.equals("February")) {
            premonth = "January";

        } else if (month.equals("March")) {
            premonth = "February";

        } else if (month.equals("April")) {
            premonth = "March";

        } else if (month.equals("May")) {
            premonth = "April";

        } else if (month.equals("June")) {
            premonth = "May";

        } else if (month.equals("July")) {
            premonth = "June";

        } else if (month.equals("August")) {
            premonth = "July";

        } else if (month.equals("September")) {
            premonth = "August";

        } else if (month.equals("October")) {
            premonth = "September";

        } else if (month.equals("November")) {
            premonth = "October";
        } else if (month.equals("December")) {
            premonth = "November";

        }


        tvDate.setText("For the" + " " + showMonth);

        if (month.equals("January")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else if (month.equals("February")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else if (month.equals("March")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else {
            int futureyear = y + 1;
            financialYear = year + "-" + futureyear;
        }


        if (premonth.equals("January")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else if (premonth.equals("February")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else if (premonth.equals("March")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else {
            int futureyear = y + 1;
            finalcialchecking = year + "-" + futureyear;
        }
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
        securitycode = prefManager.getSecurityCode();
        zoneId = "0";
        branchId = "0";
        aemId = prefManager.getUserId();
        tvSave = (TextView) findViewById(R.id.tvSave);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        llSureSave = (LinearLayout) findViewById(R.id.llSureSave);
        tvRemarks = (TextView) findViewById(R.id.tvRemarks);
        String next = "<font color='#EE0000'>*</font>";
        String remarkstext = "Please enter remarks :";
        tvRemarks.setText(Html.fromHtml(remarkstext + next));
        etRemarks = (EditText) findViewById(R.id.etRemarks);


    }

    private void setAdapter() {
        Log.d("yuii", "opooopp");
        compAdapter = new DisplayMatrixAdapterAdapter(itemList, DisplayMatrix2Activity.this);
        rvItem.setAdapter(compAdapter);

    }

    private void getItem() {
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
        progressBar.show();
        String surl =  AppController.APIURL+"api/get_EmployeeDisplayMatrixReport?AEMEmployeeID="+prefManager.getUserId()+"&FinancialYear="+financialYear+"&Month="+month+"&SecurityCode="+prefManager.getSecurityCode();
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

                            //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                            JSONArray responseData = job1.optJSONArray("responseData");
                            for (int i = 0; i < responseData.length(); i++) {
                                JSONObject obj = responseData.getJSONObject(i);
                                String CompetitorCompanyID = obj.optString("CompetitorCompanyID");
                                String CompetitorCompany = obj.optString("CompanyName");
                                String CategoryID = obj.optString("CategoryID");
                                String CategoryName = obj.optString("CategoryName");
                                String Quantity = obj.optString("Quantity");
                                compModel = new DisplayMatrixModel();
                                compModel.setComapnyName(CompetitorCompany);
                                compModel.setEditVolume(Quantity);
                                compModel.setItemName(CategoryName);
                                compModel.setCompanyId(CompetitorCompanyID);
                                compModel.setCategoryId(CategoryID);
                                itemList.add(compModel);
                                if (!Quantity.equals("")){
                                    item.add(itemList.get(i).getCategoryId() + "-" + itemList.get(i).getCompanyId() + "#" + itemList.get(i).getEditVolume());
                                }


                            }
                            progressBar.dismiss();


                            setAdapter();
                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DisplayMatrix2Activity.this, "Volly Error", Toast.LENGTH_LONG).show();
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

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DisplayMatrix2Activity.this);
        requestQueue.add(stringRequest);
    }

    private void getModel() {
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
        progressBar.show();
        String surl =  AppController.APIURL+"api/get_EmployeeDisplayMatrixReport?AEMEmployeeID="+prefManager.getUserId()+"&FinancialYear="+financialYear+"&Month="+month+"&SecurityCode="+prefManager.getSecurityCode();
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

                            //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                            JSONArray responseData = job1.optJSONArray("responseData");
                            for (int i = 0; i < responseData.length(); i++) {
                                JSONObject obj = responseData.getJSONObject(i);
                                String CompetitorCompanyID = obj.optString("CompetitorCompanyID");
                                String CompetitorCompany = obj.optString("CompanyName");
                                String CategoryID = obj.optString("CategoryID");
                                String CategoryName = obj.optString("CategoryName");
                                String Quantity = obj.optString("Quantity");
                                compModel = new DisplayMatrixModel();
                                compModel.setComapnyName(CompetitorCompany);
                                compModel.setEditVolume(Quantity);
                                compModel.setItemName(CategoryName);
                                compModel.setCompanyId(CompetitorCompanyID);
                                compModel.setCategoryId(CategoryID);
                                itemList.add(compModel);
                                if (!Quantity.equals("")){
                                    item.add(itemList.get(i).getCategoryId() + "-" + itemList.get(i).getCompanyId() + "#" + itemList.get(i).getEditVolume());
                                }


                            }
                            progressBar.dismiss();


                            setAdapter();
                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DisplayMatrix2Activity.this, "Volly Error", Toast.LENGTH_LONG).show();
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

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DisplayMatrix2Activity.this);
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
        final DatePickerDialog dialog = new DatePickerDialog(DisplayMatrix2Activity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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

    public void updateItemStatus(int position, boolean isSelected) {



        item.add(itemList.get(position).getCategoryId() + "-" + itemList.get(position).getCompanyId() + "#" + itemList.get(position).getEditVolume());




        String itemcomp = item.toString();
        compItem = itemcomp.replace("[", "").replace("]", "");
        Log.d("aripitem", compItem);


    }

   /* private void postCompetorSale() {
        progressDialog.show();

        Call<UploadObject> fileUpload = uploadService.postCompetitor(zoneId,branchId,aemId,nosaledate,financialYear,month,compItem,securitycode,etRemarks.getText().toString());
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

    /*private void postCompetorSale() {
        final ProgressDialog pd=new ProgressDialog(CompetitorSaleActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        AndroidNetworking.upload(AppController.APIURL+"api/post_CompitetorSales")
                .addMultipartParameter("ZoneID", zoneId)
                .addMultipartParameter("BranchID", branchId)
                .addMultipartParameter("AEMEmployeeID", aemId)
                .addMultipartParameter("SalesDate", nosaledate)
                .addMultipartParameter("FinancialYear", financialYear)
                .addMultipartParameter("Month", month)
                .addMultipartParameter("Category", compItem)
                .addMultipartParameter("SecurityCode", securitycode)
                .addMultipartParameter("CSRRemarks", etRemarks.getText().toString())



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
                            Toast.makeText(CompetitorSaleActivity.this,responseText,Toast.LENGTH_LONG).show();

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
        Log.d("item", compItem);
    }*/

    private void onClick() {
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etRemarks.getText().toString().length() > 3) {
                   // postCompetorSale();


                    prefManager.saveCompetitorItem("");
                } else {
                    etRemarks.requestFocus();
                    etRemarks.setError("Please enter remarks");
                }
            }
        });

        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Intent intent = new Intent(DisplayMatrix2Activity.this, NewDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }

    private void displayMatrixChecking() {
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
        progressBar.show();
        String surl =  AppController.APIURL+"api/get_DisplayMatrixReport?AEMEmployeeID=" + prefManager.getUserId() + "&FinancialYear=" + finalcialchecking + "&Month=" + month + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputtlreport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.dismiss();

                        Log.d("responsetlreport", response);

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                                displayMatrixAlert();


                            } else {
                               getItem();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DisplayMatrix2Activity.this, "Volly Error", Toast.LENGTH_LONG).show();
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

        };


        RequestQueue requestQueue = Volley.newRequestQueue(DisplayMatrix2Activity.this);
        requestQueue.add(stringRequest);

    }


    private void displayMatrixAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrix2Activity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_compsale, null);
        dialogBuilder.setView(dialogView);
        Button btnNow = (Button) dialogView.findViewById(R.id.btnNow);
        TextView tvResponse = (TextView) dialogView.findViewById(R.id.tvResponse);
        tvResponse.setText(responseText + " .Do you want to update ?");
        btnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                getItem();

            }
        });

        Button btnLate = (Button) dialogView.findViewById(R.id.btnLate);
        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrix2Activity.this, DashBoardActivity.class);
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

    private void successAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrix2Activity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText("You deatils submitted Successfully");

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                Intent intent = new Intent(DisplayMatrix2Activity.this, CompSaleReportActivity.class);
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



    private void post() {
        Log.d("item", compItem);
    }
}