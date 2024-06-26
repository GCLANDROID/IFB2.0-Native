package io.cordova.ifb.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class NoSalesActivity extends AppCompatActivity {
    TextView tvDate;
    String salesDate;
    String monthname;
    PrefManager prefManager;
    LinearLayout llPunch;
    ImageView imgBack,imgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_sales);
        initialize();
        onClick();
    }

    private void initialize(){
        prefManager=new PrefManager(getApplicationContext());
        tvDate=(TextView)findViewById(R.id.tvDate);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        salesDate = df.format(c);
        tvDate.setText(salesDate);
        llPunch=(LinearLayout)findViewById(R.id.llPunch);
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);

    }

    private void onClick(){
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        llPunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postNoSale();
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
                Intent intent=new Intent(NoSalesActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
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
        final int year = now.get(Calendar.YEAR);
        final int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);


        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(NoSalesActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(NoSalesActivity.this,android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

               String sdate = (m + 1) + "-" + d + "-" + y;
               int month=(m+1);
               if (month==1){
                   monthname="Jan";

               }else if (month==2){
                   monthname="Feb";
               }else if (month==3){
                   monthname="March";
               }else if (month==4){
                   monthname="April";
               }else if (month==5){
                   monthname="May";
               }else if (month==6){
                   monthname="June";
               }
               else if (month==7){
                   monthname="July";
               }else if (month==8){
                   monthname="August";
               }else if (month==9){
                   monthname="Sep";
               }else if (month==10){
                   monthname="Oct";
               }else if (month==11){
                   monthname="Nov";
               }else if (month==12){
                   monthname="Dec";
               }

                String nosaledate=d+"-"+monthname+"-"+y;

                tvDate.setText(nosaledate);
              //  pref.saveDOJ(sdate);


            }
        }, year, month, day);

        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clockicon);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis()-1000));

        // Popup the dialog.

        dialog.show();
    }


    private void postNoSale(){
        String surl = AppController.APIURL+"api/post_NoSales?UserID="+prefManager.getUserId()+"&SecurityCode="+prefManager.getSecurityCode()+"&SalesDate="+tvDate.getText().toString();

        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressBar.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NoSalesActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                  Toast.makeText(NoSalesActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(NoSalesActivity.this);
        requestQueue.add(stringRequest);


    }
}
