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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.RefInfoAdapter;
import io.cordova.ifb.module.RefInfoModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RefInfoManageActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvItem;
    ArrayList<RefInfoModel> itemList = new ArrayList<>();
    RefInfoModel refinfoModel;
    PrefManager prefManager;
    String monthname;
    String year, month, financialYear, nosaledate, finalcialchecking;
    RefInfoAdapter refAdapter;
    ArrayList<Integer> itemForDC = new ArrayList<>();
    ArrayList<Integer> itemForFF = new ArrayList<>();
    ArrayList<String> totalItem = new ArrayList<>();
    ImageView imgBack, imgHome;
    AlertDialog alerDialog1, alertDialog;
    LinearLayout llSureSave;
    TextView tvRemarks;
    EditText etRemarks;
    String showMonth;
    String premonth;
    LinearLayout llDate;
    TextView tvDate;
    String showYear;
    TextView tvSave;
    int manage;
    LinearLayout lnRemarks;
    TextView tvFFTotal, tvDCTotal;
    String ffvolume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref_info_manage);
        initialize();

    }


    private void initialize() {
        prefManager = new PrefManager(RefInfoManageActivity.this);
        lnRemarks = (LinearLayout) findViewById(R.id.lnRemarks);
        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(RefInfoManageActivity.this, LinearLayoutManager.VERTICAL, false);
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
            showMonth = "January" + "-" + showYear;
        } else if (month.equals("February")) {
            showMonth = "February" + "-" + year;

        } else if (month.equals("March")) {
            showMonth = "March" + "-" + year;

        } else if (month.equals("April")) {
            showMonth = "April" + "-" + year;

        } else if (month.equals("May")) {
            showMonth = "May" + "-" + year;

        } else if (month.equals("June")) {
            showMonth = "June" + "-" + year;

        } else if (month.equals("July")) {
            showMonth = "July" + "-" + year;

        } else if (month.equals("August")) {
            showMonth = "August" + "-" + year;

        } else if (month.equals("September")) {
            showMonth = "September" + "-" + year;

        } else if (month.equals("October")) {
            showMonth = "October" + "-" + year;

        } else if (month.equals("November")) {
            showMonth = "November" + "-" + year;

        } else if (month.equals("December")) {
            showMonth = "December" + "-" + year;

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

        tvSave = (TextView) findViewById(R.id.tvSave);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        llSureSave = (LinearLayout) findViewById(R.id.llSureSave);
        tvRemarks = (TextView) findViewById(R.id.tvRemarks);
        String next = "<font color='#EE0000'>*</font>";
        String remarkstext = "Please enter remarks :";
        tvRemarks.setText(Html.fromHtml(remarkstext + next));
        etRemarks = (EditText) findViewById(R.id.etRemarks);
        tvSave.setOnClickListener(this);

        tvDCTotal = (TextView) findViewById(R.id.tvDCTotal);
        tvFFTotal = (TextView) findViewById(R.id.tvFFTotal);

        getItem();


        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        etRemarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (itemForFF.size() == itemList.size()) {
                        if (itemForDC.size() == itemList.size()) {


                            postData();

                        } else {
                            Toast.makeText(RefInfoManageActivity.this, "Please Enter All DC Qty", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(RefInfoManageActivity.this, "Please Enter All FF Qty", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });


    }

    private void getItem() {
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
        progressBar.show();
        String surl =  AppController.APIURL+"api/get_RefrigeratorCompetitorSales?AEMEmployeeID=" + prefManager.getUserId() + "&FinYear=" + financialYear + "&Month=" + month + "&Operation=2&SecurityCode=" + prefManager.getSecurityCode();
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
                                String CompanyName = obj.optString("CompanyName");
                                String CompetitorCompanyID = obj.optString("CompetitorCompanyID");
                                String FF = obj.optString("FF");
                                String DC = obj.optString("DC");

                                refinfoModel = new RefInfoModel();
                                refinfoModel.setCompName(CompanyName);
                                refinfoModel.setCompID(CompetitorCompanyID);
                                refinfoModel.setFfEditVolume(FF);
                                refinfoModel.setDcEditVolume(DC);

                                itemList.add(refinfoModel);


                            }
                            progressBar.dismiss();


                            setAdapter();
                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RefInfoManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(RefInfoManageActivity.this);
        requestQueue.add(stringRequest);

    }

    private void getItemForReport() {
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
        progressBar.show();
        String surl =  AppController.APIURL+"api/get_RefrigeratorCompetitorSales?AEMEmployeeID=" + prefManager.getUserId() + "&FinYear=" + financialYear + "&Month=" + month + "&Operation=1&SecurityCode=" + prefManager.getSecurityCode();
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
                                String CompanyName = obj.optString("CompanyName");
                                String CompetitorCompanyID = obj.optString("CompetitorCompanyID");
                                String FF = obj.optString("FF");
                                String DC = obj.optString("DC");

                                refinfoModel = new RefInfoModel();
                                refinfoModel.setCompName(CompanyName);
                                refinfoModel.setCompID(CompetitorCompanyID);
                                refinfoModel.setFfEditVolume(FF);
                                refinfoModel.setDcEditVolume(DC);

                                itemList.add(refinfoModel);


                            }
                            progressBar.dismiss();


                            setAdapter();
                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RefInfoManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(RefInfoManageActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setAdapter() {
        refAdapter = new RefInfoAdapter(itemList, RefInfoManageActivity.this);
        rvItem.setAdapter(refAdapter);
    }

    public void updateItemStatusForFF(int position, boolean isSelected) {


        itemForFF.add(Integer.valueOf(itemList.get(position).getFfEditVolume()));

        double sum = 0;
        for (int i : itemForFF) {
            sum += i;
        }

        tvFFTotal.setText("" + sum);

    }

    public void updateItemStatusForDC(int position, boolean isSelected) {


        itemForDC.add(Integer.valueOf(itemList.get(position).getDcEditVolume()));
        double sum = 0;
        for (int i : itemForDC) {
            sum += i;
        }

        tvDCTotal.setText("" + sum);

    }

    private void postData() {
        if (itemForDC.size() > 0 && itemForFF.size() > 0) {
            for (int i = 0; i < itemForFF.size(); i++) {
                totalItem.add(itemList.get(i).getCompID() + "-" + itemForFF.get(i) + "#" + itemForDC.get(i));

            }
        } else if (itemForFF.size() > 0) {
            for (int i = 0; i < itemForFF.size(); i++) {
                totalItem.add(itemList.get(i).getCompID() + "-" + itemForFF.get(i) + "#" + "0");

            }
        } else if (itemForDC.size() > 0) {
            for (int i = 0; i < itemForDC.size(); i++) {
                totalItem.add(itemList.get(i).getCompID() + "-" + "0" + "#" + itemForDC.get(i));

            }
        }

        String TotalItem = totalItem.toString().replace("[", "").replace("]", "");

        final ProgressDialog pd = new ProgressDialog(RefInfoManageActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload( AppController.APIURL+"api/post_RefrigeratorCompetitorSales")
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("FinancialYear", financialYear)
                .addMultipartParameter("Month", month)
                .addMultipartParameter("CompetitorData", TotalItem)
                .addMultipartParameter("SecurityCode", prefManager.getSecurityCode())
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
                        Log.d("responseText", responseText);
                        boolean responseStatus = job1.optBoolean("responseStatus");
                        if (responseStatus) {
                            successAlert(responseText);
                            pd.dismiss();

                        } else {
                            pd.dismiss();
                            Toast.makeText(RefInfoManageActivity.this, responseText, Toast.LENGTH_LONG).show();

                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == tvSave) {
            etRemarks.requestFocus();


        } else if (view == imgBack) {
            onBackPressed();
        } else if (view == imgHome) {
            Intent intent = new Intent(RefInfoManageActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void successAlert(String text) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RefInfoManageActivity.this, R.style.CustomDialogNew);
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
                Intent intent = new Intent(RefInfoManageActivity.this, SalesDashboardActivity.class);
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
}