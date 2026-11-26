package io.cordova.ifb.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/*import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;*/

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import io.cordova.ifb.R;
import io.cordova.ifb.activity.AttendanceCalendarDialogActivity;
import io.cordova.ifb.activity.DeliveryDetailsActivity;
import io.cordova.ifb.activity.NewCompetitorDisplayMatrixActivity;
import io.cordova.ifb.activity.NewDashboardActivity;
import io.cordova.ifb.activity.PlanogramActivity;
import io.cordova.ifb.activity.RefNoReportActivity;
import io.cordova.ifb.databinding.FragmentHomeBinding;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;


public class HomeFragment extends Fragment  {
    private static final String TAG = "HomeFragment";
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
    TextView tvCheckOutTime;
    LinearLayout llCheckOutMessage,llChekcinout,llStarPerformer;
    TextView tvCheckIN,tvCheckOut;



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
        llStarPerformer=(LinearLayout)view.findViewById(R.id.llStarPerformer);
        tvCheckOutTime=view.findViewById(R.id.tvCheckOutTime);
        llCheckOutMessage=view.findViewById(R.id.llCheckOutMessage);
        llChekcinout=view.findViewById(R.id.llChekcinout);
        tvCheckIN=view.findViewById(R.id.tvCheckIN);
        tvCheckOut=view.findViewById(R.id.tvCheckOut);
        if (!prefManager.getMonthlyPerformerUR().equals("")){
            llStarPerformer.setVisibility(View.VISIBLE);

        }else {
            llStarPerformer.setVisibility(View.GONE);
        }


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

        binding.tvMonth.setText("Sales Information for the Month of "+month+" - "+financialYear);

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
                Intent intent = new Intent(getContext(), NewCompetitorDisplayMatrixActivity.class);
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


        binding.btnAttendanceCalendarReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AttendanceCalendarDialogActivity.class);
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

        llStarPerformer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!prefManager.getMonthlyPerformerUR().equals("")){
                    Uri uri = Uri.parse(prefManager.getMonthlyPerformerUR()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });

        binding.llDeliveryPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DeliveryDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.llTotalSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), RefNoReportActivity.class);
                intent.putExtra("subOperation","1");
                intent.putExtra("month",month);
                intent.putExtra("year",financialYear);
                intent.putExtra("report","Total Sales");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        binding.llApprovedSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),RefNoReportActivity.class);
                intent.putExtra("subOperation","6");
                intent.putExtra("month",month);
                intent.putExtra("year",financialYear);
                intent.putExtra("report","Ticket Generated");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        getItemForNotification();
        setPieData();
        getShowNotification();

        if (getArguments() != null) {

            String Time = getArguments().getString("Time");

            if (Time.equals("")) {
                llCheckOutMessage.setVisibility(View.GONE);
                llChekcinout.setVisibility(View.GONE);

            }else {
                tvCheckIN.setText(Time);
            }


            handleCheckoutTime(Time);

        }


    }

    private void getShowNotification() {
        AndroidNetworking.get(AppController.APIV2URL+"api/ShowNotification/Get")
                .addHeaders("Authorization", "Bearer "+prefManager.getAccessToken())
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e(TAG, "SHOW_NOTIFICATION: "+response.toString(4));
                            try {
                                JSONObject jsonObject = response;
                                boolean responseStatus = jsonObject.optBoolean("responseStatus", false);
                                if (responseStatus) {
                                    JSONObject responseData = jsonObject.optJSONObject("responseData");
                                    if (responseData != null) {
                                        JSONArray tableArray = responseData.optJSONArray("Table");
                                        if (tableArray != null && tableArray.length() > 0) {
                                            JSONObject tableObject = tableArray.optJSONObject(0);
                                            if (tableObject != null) {
                                                String filePath = tableObject.optString("FilePath", "").trim();
                                                String title = tableObject.optString("Title", "").trim();
                                                String text = tableObject.optString("Text", "").trim();
                                                // Show Image Alert
                                                if (!filePath.isEmpty() && !filePath.equalsIgnoreCase("null")) {
                                                    // Call your image alert method
                                                    showImageAlert(filePath);
                                                    return;
                                                }

                                                // Show Text Alert
                                                if (!title.isEmpty() && !text.isEmpty()) {
                                                    // Call your text alert method
                                                    showTextAlert(title, text);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    // API response failed
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: "+anError.getErrorBody());
                    }
                });
    }

    private void showTextAlert(String title, String text) {
        Dialog dialog = new Dialog(getActivity(),R.style.CustomDialogNew2);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notification_popup_text_layout);
        dialog.setCancelable(false);

        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        TextView tvNotificationTitle = dialog.findViewById(R.id.tvNotificationTitle);
        TextView tvNotificationMessage = dialog.findViewById(R.id.tvNotificationMessage);
        TextView btnNotificationOk = dialog.findViewById(R.id.btnNotificationOk);

        tvNotificationTitle.setText(title);
        tvNotificationMessage.setText(text);
        tvNotificationMessage.setText(text);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnNotificationOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // Window settings
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.TOP;
            params.y = dpToPx(40);
            window.setAttributes(params);
        }
        dialog.show();
    }

    private void showImageAlert(String filePath) {
        Dialog dialog = new Dialog(getActivity(),R.style.CustomDialogNew2);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ganesh_festival_popup_layout);
        dialog.setCancelable(false);
        // Close button
        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        PhotoView imageView2 = dialog.findViewById(R.id.imageView2);
        Glide.with(this)
                .load(filePath)
                .into(imageView2);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // Window settings
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.TOP;
            params.y = dpToPx(40);
            window.setAttributes(params);
        }
        dialog.show();
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

        getReport();
    }





    private void getItemForNotification() {
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String surl =  AppController.APIV2URL+"api/get_EmployeeNotificationInfo?AEMEmployeeID="+prefManager.getUserId()+"&SecurityCode="+prefManager.getSecurityCode()+"&Operation=1";
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }

        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(getContext());

        requestQueue.add(stringRequest);
    }

    public void  deleteALert(int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Set the message show for the Alert time
        builder.setMessage("Do you want to delete ?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false for when the user clicks
        // on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda
        // OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {





        });

        // Set the Negative button with No name Lambda
        // OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {

            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();

    }


    public void getReport() {
        String surl =  AppController.APIV2URL+"api/get_EmployeeSalesRefDetails?ReferenceNo=0&UserID="+prefManager.getUserId()+"&FinancialYear="+financialYear+"&Month="+month+"&Operation=1&SubOperation=3&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseSalesRefDetails", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("responseSalesRefDetails", "@@@@@@" + job1);

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus){
                                JSONArray responseData=job1.optJSONArray("responseData");
                                for (int i=0;i<responseData.length();i++){
                                    JSONObject jsonObject=responseData.optJSONObject(i);
                                    String Total_Sales=jsonObject.optString("Total_Sales");
                                    binding.tvTotalSales.setText("Total Sales\n\n"+Total_Sales);


                                    String Delivery_Pending=jsonObject.optString("Delivery_Pending");
                                    binding.tvTotalDeliveryPending.setText("Delivery pending\n\n"+Delivery_Pending);


                                    String Ticket_Generated=jsonObject.optString("Ticket_Generated");
                                    binding.tvTotalApprovedSales.setText("Ticket Generated\n\n"+Ticket_Generated);


                                }
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
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
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(getContext());

        requestQueue.add(stringRequest);
    }

    private void handleCheckoutTime(String apiTime) {

        try {
            // Parse only TIME from API
            SimpleDateFormat inputFormat =
                    new SimpleDateFormat("h:mma", Locale.getDefault());
            Date checkInDate = inputFormat.parse(apiTime);

            // Get today's date
            Calendar today = Calendar.getInstance();

            // Create calendar with TODAY + API TIME
            Calendar checkoutLimitCal = Calendar.getInstance();
            checkoutLimitCal.set(Calendar.YEAR, today.get(Calendar.YEAR));
            checkoutLimitCal.set(Calendar.MONTH, today.get(Calendar.MONTH));
            checkoutLimitCal.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH));

            // Set hour & minute from API time
            Calendar apiTimeCal = Calendar.getInstance();
            apiTimeCal.setTime(checkInDate);

            checkoutLimitCal.set(Calendar.HOUR_OF_DAY, apiTimeCal.get(Calendar.HOUR_OF_DAY));
            checkoutLimitCal.set(Calendar.MINUTE, apiTimeCal.get(Calendar.MINUTE));
            checkoutLimitCal.set(Calendar.SECOND, 0);

            // Add 9 hours 15 minutes
            checkoutLimitCal.add(Calendar.HOUR_OF_DAY, 9);
            checkoutLimitCal.add(Calendar.MINUTE, 00);

            // Current time
            Calendar now = Calendar.getInstance();

            // Display format
            SimpleDateFormat displayFormat =
                    new SimpleDateFormat("hh:mm a", Locale.getDefault());

            String checkoutLimitTime =
                    displayFormat.format(checkoutLimitCal.getTime());
            tvCheckOut.setText(checkoutLimitTime);
            if (now.before(checkoutLimitCal)) {

                // ✅ Checkout allowed
                llCheckOutMessage.setVisibility(View.GONE);
                llChekcinout.setVisibility(View.VISIBLE);


            } else {
                llCheckOutMessage.setVisibility(View.VISIBLE);
                llChekcinout.setVisibility(View.VISIBLE);

                // ❌ Checkout blocked
                tvCheckOutTime.setText(
                        "Late check-out does not constitute approved overtime or additional compensation"
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }
}