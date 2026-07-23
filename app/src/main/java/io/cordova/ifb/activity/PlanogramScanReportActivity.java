package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.PlanopgrameHygeneAdapter;
import io.cordova.ifb.adapter.ScannedBarcodeAdapter;
import io.cordova.ifb.databinding.ActivityPlanogramScanReportBinding;
import io.cordova.ifb.module.PlanogramHygeneModel;
import io.cordova.ifb.module.ScannedPlanogramBarcodeModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;
import io.cordova.ifb.utility.Util;

public class PlanogramScanReportActivity extends AppCompatActivity {
    ActivityPlanogramScanReportBinding binding;
    String nosaledate;
    ArrayList<PlanogramHygeneModel> reportitemList=new ArrayList<>();
    PlanopgrameHygeneAdapter adapter;
    ArrayList<String>monthList=new ArrayList<>();
    ArrayList<String>yearList=new ArrayList<>();
    String monthname;
    int y;
    String year,month;
    String financialYear;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_planogram_scan_report);
        initVew();
    }

    private void initVew(){
        prefManager=new PrefManager(PlanogramScanReportActivity.this);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(PlanogramScanReportActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvPlanogram.setLayoutManager(layoutManager);

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

        getReportList("2");

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
                (PlanogramScanReportActivity.this, android.R.layout.simple_spinner_item,
                        monthList); //selected item will look like a spinner set from XML
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spMonth.setAdapter(monthAdapter);

        int pos=monthList.indexOf(month);
        binding.spMonth.setSelection(pos);


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>
                (PlanogramScanReportActivity.this, android.R.layout.simple_spinner_item,
                        yearList); //selected item will look like a spinner set from XML
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.SpYear.setAdapter(yearAdapter);

        int yearpos=yearList.indexOf(financialYear);
        binding.SpYear.setSelection(yearpos);

        LinearLayoutManager palogramlayoutManager
                = new LinearLayoutManager(PlanogramScanReportActivity.this, LinearLayoutManager.VERTICAL, false);
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

    }

    private void getReportList(String reporttype){
        binding.rvPlanogram.setVisibility(View.VISIBLE);
        binding.llNoData.setVisibility(View.GONE);
        ProgressDialog progressDialog=new ProgressDialog(PlanogramScanReportActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String surl =  AppController.APIV2URL+"api/BarcodePlanogramhygiene?LoginID="+prefManager.getUserId()+"&FinancialYear="+financialYear+"&Month="+month+"&Date="+nosaledate+"&ReportType="+reporttype+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseAttendance", response);
                        progressDialog.dismiss();
                        reportitemList.clear();

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





                                }


                                adapter=new PlanopgrameHygeneAdapter(reportitemList,PlanogramScanReportActivity.this);
                                binding.rvPlanogram.setAdapter(adapter);




                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PlanogramScanReportActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(PlanogramScanReportActivity.this);
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(PlanogramScanReportActivity.this);

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
        final int year2 = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(PlanogramScanReportActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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
}