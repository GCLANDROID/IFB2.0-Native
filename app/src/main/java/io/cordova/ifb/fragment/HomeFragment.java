package io.cordova.ifb.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.AttemdanceReportActivity;
import io.cordova.ifb.activity.DashBoardActivity;
import io.cordova.ifb.activity.DisplayMatrixDashBoardActivity;
import io.cordova.ifb.activity.NewDashboardActivity;
import io.cordova.ifb.activity.NotificationActivity;
import io.cordova.ifb.activity.PlanogramActivity;
import io.cordova.ifb.databinding.FragmentHomeBinding;
import io.cordova.ifb.module.ReportModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;
import io.cordova.ifb.utility.Util;


public class HomeFragment extends Fragment implements OnNavigationButtonClickedListener {
    View view;
    FragmentHomeBinding binding;
    PrefManager prefManager;

    String financialYear,month;
    int y;
    String year;
    int date;
    ArrayList<String>presentCount=new ArrayList<>();
    ArrayList<String>absentCount=new ArrayList<>();
    String monthlYSold,monthlyPending;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        view = binding.getRoot();
        initView();
        return view;
    }

    private void initView(){
         prefManager=new PrefManager(getContext());

        HashMap<Object, Property> descHashMap = new HashMap<>();

        // Initialize default property
        Property defaultProperty = new Property();

        // Initialize default resource
        defaultProperty.layoutResource = R.layout.default_view;

        // Initialize and assign variable
        defaultProperty.dateTextViewResource = R.id.text_view;

        // Put object and property
        descHashMap.put("default", defaultProperty);

        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_view;
        presentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("P", presentProperty);

        // For absent
        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_view;
        absentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("A", absentProperty);

        //wo
        Property woProperty = new Property();
        woProperty.layoutResource = R.layout.wo_view;
        woProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("WO", woProperty);

        //holiday
        Property hProperty = new Property();
        hProperty.layoutResource = R.layout.ho_view;
        hProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("H", hProperty);


        //Leave
        Property naProperty = new Property();
        naProperty.layoutResource = R.layout.na_view;
        naProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("CL", naProperty);
        descHashMap.put("SL", naProperty);
        descHashMap.put("PL", naProperty);
        descHashMap.put("CO", naProperty);




        binding.customCalendar.setMapDescToProp(descHashMap);
        binding.customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);
        binding.customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);
        y = Calendar.getInstance().get(Calendar.YEAR);
        year = String.valueOf(y);
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

        String sold=prefManager.getSold().replaceAll("\\s+", "").replace("(","-");
        String soldArray[]=sold.split("-");
        if (soldArray.length>0){
            monthlYSold=soldArray[0];
        }


        String pending=prefManager.getPending().replaceAll("\\s+", "").replace("(","-");
        String pendingArray[]=pending.split("-");
        if (pendingArray.length>0){
            monthlyPending=pendingArray[0];
        }

        binding.tvTarget.setText("Target- "+prefManager.getMonthlyTarget());
        binding.tvSold.setText("Sold- "+prefManager.getSold());
        binding.tvPending.setText("Pending- "+prefManager.getPending());
        binding.tvApprved.setText("Approved- "+prefManager.getApproved());
        binding.tvRejected.setText("Rejected- "+prefManager.getRejected());
        binding.tvInformation2.setText(prefManager.getNotify());


        binding.btnDisplayMatrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DisplayMatrixDashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        binding.btnPalnogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PlanogramActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.tvInformation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!prefManager.getNotifyUrl().equals("")){
                    Uri uri = Uri.parse(prefManager.getNotifyUrl()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });








        getAttendance();
        setPieData();


    }


    private void setPieData()
    {

        // Set the percentage of language used


        // Set the data and color to the pie chart
        binding.piechart.addPieSlice(
                new PieModel(
                        "Target",
                        Integer.parseInt(prefManager.getMonthlyTarget()),
                        Color.parseColor("#0a0aa7")));
        binding.piechart.addPieSlice(
                new PieModel(
                        "Sold",
                        Integer.parseInt(monthlYSold),
                        Color.parseColor("#66BB6A")));

        binding.piechart.addPieSlice(
                new PieModel(
                        "Approved",
                        Integer.parseInt(prefManager.getApproved()),
                        Color.parseColor("#04ABFF")));

        binding.piechart.addPieSlice(
                new PieModel(
                        "Rejected",
                        Integer.parseInt(prefManager.getRejected()),
                        Color.parseColor("#FF0000")));
        binding.piechart.addPieSlice(
                new PieModel(
                        "Pending",
                        Integer.parseInt(monthlyPending),
                        Color.parseColor("#F8E225")));




        // To animate the pie chart
        binding.piechart.startAnimation();
    }

    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
        Map<Integer, Object>[] arr = new Map[2];
        arr[0] = new HashMap<>();
        switch(newMonth.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                Calendar calendar=Calendar.getInstance();
                calendar.set(newMonth.get(Calendar.YEAR),0,1);
                int futureyear = newMonth.get(Calendar.YEAR) - 1;
                financialYear = futureyear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"January",calendar);

                break;
            case Calendar.FEBRUARY:
                Calendar calendar1=Calendar.getInstance();
                calendar1.set(newMonth.get(Calendar.YEAR),1,1);
                int year=Calendar.YEAR;

                int futureoneyear = newMonth.get(Calendar.YEAR) - 1;
                financialYear = futureoneyear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"February",calendar1);


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),2,calendar1);
                break;
            case Calendar.MARCH:
                Calendar calendar2=Calendar.getInstance();
                calendar2.set(newMonth.get(Calendar.YEAR),2,1);

                int futuretwoyear = newMonth.get(Calendar.YEAR) - 1;
                financialYear = futuretwoyear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"March",calendar2);


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),3,calendar2);

                break;
            case  Calendar.APRIL:
                Calendar calendar3=Calendar.getInstance();
                calendar3.set(newMonth.get(Calendar.YEAR),3,1);

                int futurethreeyear = newMonth.get(Calendar.YEAR)+ 1;
                financialYear = futurethreeyear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"April",calendar3);


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),4,calendar3);
                break;
            case Calendar.MAY:
                Calendar calendar4=Calendar.getInstance();
                calendar4.set(newMonth.get(Calendar.YEAR),4,1);

                int futurefouryear = newMonth.get(Calendar.YEAR) + 1;
                financialYear = futurefouryear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"May",calendar4);


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),5,calendar4);
                break;
            case Calendar.JUNE:
                Calendar calendar5=Calendar.getInstance();
                calendar5.set(newMonth.get(Calendar.YEAR),5,1);

                int futurefiveyear = newMonth.get(Calendar.YEAR) + 1;
                financialYear = futurefiveyear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"June",calendar5);


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),6,calendar5);
                break;
            case Calendar.JULY:
                Calendar calendar6=Calendar.getInstance();
                calendar6.set(newMonth.get(Calendar.YEAR),6,1);

                int futuresixyear = newMonth.get(Calendar.YEAR) + 1;
                financialYear = futuresixyear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"July",calendar6);

                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),7,calendar6);
                break;
            case Calendar.AUGUST:
                Calendar calendar7=Calendar.getInstance();
                calendar7.set(newMonth.get(Calendar.YEAR),7,1);

                int futuresevenear = newMonth.get(Calendar.YEAR) + 1;
                financialYear = futuresevenear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"August",calendar7);


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),8,calendar7);
                break;
            case Calendar.SEPTEMBER:
                Calendar calendar8=Calendar.getInstance();
                calendar8.set(newMonth.get(Calendar.YEAR),8,1);

                int futureeightyear = newMonth.get(Calendar.YEAR) + 1;
                financialYear = futureeightyear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"September",calendar8);

                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),9,calendar8);
                break;
            case Calendar.OCTOBER:
                Calendar calendar9=Calendar.getInstance();
                calendar9.set(newMonth.get(Calendar.YEAR),9,1);

                int futurnineyear = newMonth.get(Calendar.YEAR) + 1;
                financialYear = futurnineyear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"October",calendar9);


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),10,calendar9);
                break;
            case Calendar.NOVEMBER:
                Calendar calendar10=Calendar.getInstance();
                calendar10.set(newMonth.get(Calendar.YEAR),10,1);

                int futurtenyear = newMonth.get(Calendar.YEAR) + 1;
                financialYear = futurtenyear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"October",calendar10);


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),11,calendar10);
                break;
            case Calendar.DECEMBER:

                Calendar calendar11=Calendar.getInstance();

                calendar11.set(newMonth.get(Calendar.YEAR),11,1);

                int futureleveyear = newMonth.get(Calendar.YEAR) + 1;
                financialYear = futureleveyear+"-"+newMonth.get(Calendar.YEAR);
                getAttendanceNav(financialYear,"October",calendar11);


                //getAttendanceListForNav(newMonth.get(Calendar.YEAR),12,calendar11);
                break;
        }
        return arr;
    }
    private void getAttendance() {
        ProgressDialog pd=new ProgressDialog(getContext());
        pd.setCancelable(false);
        pd.setMessage("Loading");
        pd.show();
        HashMap<Integer, Object> dateHashmap = new HashMap<>();

        // initialize calendar
        Calendar calendar = Calendar.getInstance();


        String surl =  AppController.APIURL+"api/SelfAttendance?LoginID=" + prefManager.getUserId() + "&FinancialYear=" + financialYear + "&Month=" + month + "&ReportType=1&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        pd.dismiss();
                        presentCount=new ArrayList<>();
                        absentCount=new ArrayList<>();
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
                                    String sDate = Util.changeAnyDateFormat(obj.optString("Month"),"dd-MM-yyyy","dd");
                                    try {
                                        date = Integer.parseInt(sDate);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                    String Status = obj.optString("Status");
                                    if (Status.equalsIgnoreCase("P")){
                                        presentCount.add(sDate);
                                    }else if (Status.equalsIgnoreCase("A")){
                                        absentCount.add(sDate);
                                    }

                                    dateHashmap.put(date, Status);


                                }
                                binding.tvPresent.setText(""+presentCount.size());
                                binding.tvAbsent.setText(""+absentCount.size());

                                binding.customCalendar.setDate(calendar, dateHashmap);
                                getItemForNotification();


                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {


                                Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             pd.dismiss();

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void getAttendanceNav(String finYear,String m,Calendar calendar) {
        ProgressDialog pd=new ProgressDialog(getContext());
        pd.setCancelable(false);
        pd.setMessage("Loading");
        pd.show();
        HashMap<Integer, Object> dateHashmap = new HashMap<>();

        // initialize calendar



        String surl =  AppController.APIURL+"api/SelfAttendance?LoginID=" + prefManager.getUserId() + "&FinancialYear=" + finYear + "&Month=" + m + "&ReportType=1&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        pd.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            presentCount=new ArrayList<>();
                            absentCount=new ArrayList<>();
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String sDate = Util.changeAnyDateFormat(obj.optString("Date"),"dd MMM yyyy","dd");
                                    try {
                                        date = Integer.parseInt(sDate);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                    String Status = obj.optString("Status");
                                    if (Status.equalsIgnoreCase("P")){
                                        presentCount.add(sDate);
                                    }else if (Status.equalsIgnoreCase("A")){
                                        absentCount.add(sDate);
                                    }

                                    dateHashmap.put(date, Status);


                                }
                                binding.tvPresent.setText(""+presentCount.size());
                                binding.tvAbsent.setText(""+absentCount.size());

                                binding.customCalendar.setDate(calendar, dateHashmap);



                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {


                                Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    private void getItemForNotification() {
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String surl =  AppController.APIURL+"api/get_EmployeeNotificationInfo?AEMEmployeeID="+prefManager.getUserId()+"&SecurityCode="+prefManager.getSecurityCode()+"&Operation=1";
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        progressDialog.dismiss();


                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");

                                if (responseData.length()>0){

                                    JSONObject object=responseData.optJSONObject(0);
                                    String HeaderTitle=object.optString("HeaderTitle");
                                    String Remarks=object.optString("Remarks");
                                    binding.tvHeaderTitle.setText(HeaderTitle);
                                    binding.tvInformation.setText(Remarks);


                                }else {

                                }




                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {





                            }
                            ((NewDashboardActivity)getActivity()).visibility();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}