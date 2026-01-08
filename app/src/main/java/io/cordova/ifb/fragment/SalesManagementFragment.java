package io.cordova.ifb.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.CompSalesDashboardActivity;
import io.cordova.ifb.activity.CustomerCallingDashboardActivity;
import io.cordova.ifb.activity.DWDashboardActivity;
import io.cordova.ifb.activity.DailyCounterSaleDashboardActivity;
import io.cordova.ifb.activity.DashBoardActivity;
import io.cordova.ifb.activity.DummySaleDashBoardActivity;
import io.cordova.ifb.activity.ModelExchangeActivity;
import io.cordova.ifb.activity.NoSalesActivity;
import io.cordova.ifb.activity.RefInfoManageActivity;
import io.cordova.ifb.activity.ReplenishedActivity;
import io.cordova.ifb.activity.SalesDashboardActivity;
import io.cordova.ifb.activity.SalesLeadDashboardActivity;
import io.cordova.ifb.activity.SalesManageDashboardActivity;
import io.cordova.ifb.activity.SalesReportActivity;
import io.cordova.ifb.activity.SalesReportDownldActivity;
import io.cordova.ifb.activity.SalesReturnActivity;
import io.cordova.ifb.activity.SalesTargetActivity;
import io.cordova.ifb.activity.TeamCollaborationActivity;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;


public class SalesManagementFragment extends Fragment {



   View view;
    LinearLayout llManage,llReport,llTarget,llWebSales,llReturn,llModelExchange,llNoSales,llCompSale,llDummySale,llDownload,llACCampign,llDailyComp;
    PrefManager prefManager;
    String responseText,responseCode;
    AlertDialog alet1;
    boolean responseData;
    String flag;
    LinearLayout llLoader;
    LinearLayout llDW,llSalesLead,llCollaboration,llReplenished,llCallUpdation,llRefInfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sales_management, container, false);
        initialize();
        checksale();
        onClick();
        return view;
    }

    private void initialize(){
        prefManager=new PrefManager(getContext());
        llManage=(LinearLayout)view.findViewById(R.id.llManage);
        llReport=(LinearLayout)view.findViewById(R.id.llReport);
        llTarget=(LinearLayout)view.findViewById(R.id.llTarget);
        llWebSales=(LinearLayout)view.findViewById(R.id.llWebSales);
        llReturn=(LinearLayout)view.findViewById(R.id.llReturn);
        llNoSales=(LinearLayout)view.findViewById(R.id.llNoSales);
        llModelExchange=(LinearLayout)view.findViewById(R.id.llModelExchange);
        llCompSale=(LinearLayout)view.findViewById(R.id.llCompSale);
        llDummySale=(LinearLayout)view.findViewById(R.id.llDummySale);
        llDownload=(LinearLayout)view.findViewById(R.id.llDownload);
        llLoader=(LinearLayout)view.findViewById(R.id.llLoader);
        llDW=(LinearLayout)view.findViewById(R.id.llDW);
        llACCampign=(LinearLayout)view.findViewById(R.id.llAcCampign);
        llDailyComp=(LinearLayout)view.findViewById(R.id.llDailyComp);
        llSalesLead=(LinearLayout)view.findViewById(R.id.llSalesLead);
        llCollaboration=(LinearLayout)view.findViewById(R.id.llCollaboration);
        llReplenished=(LinearLayout)view.findViewById(R.id.llReplenished);
        llCallUpdation=(LinearLayout)view.findViewById(R.id.llCallUpdation);
        llRefInfo=(LinearLayout) view.findViewById(R.id.llRefInfo);

        if (getArguments() != null) {

            String Time = getArguments().getString("Time");

            if (!Time.equals("")) {

                //handleCheckoutTime(Time);
            }




        }

    }

    private void onClick(){
        llManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag="manage";
                if (responseCode.equals("1")) {
                    Intent intent = new Intent(getContext(), SalesManageDashboardActivity.class);
                    startActivity(intent);
                }else {
                    salecheckalert();
                }
            }
        });

        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SalesReportActivity.class);
                startActivity(intent);
            }
        });

        llCollaboration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), TeamCollaborationActivity.class);
                startActivity(intent);
            }
        });

        llDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SalesReportDownldActivity.class);
                startActivity(intent);
            }
        });
        llSalesLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SalesLeadDashboardActivity.class);
                startActivity(intent);
            }
        });


        llTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SalesTargetActivity.class);
                startActivity(intent);
            }
        });

        llReplenished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ReplenishedActivity.class);
                startActivity(intent);
            }
        });

        llACCampign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        llCallUpdation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CustomerCallingDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        llRefInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RefInfoManageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("manage",1);
                startActivity(intent);
            }
        });

        llDW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), DWDashboardActivity.class);
                startActivity(intent);
            }
        });

        llModelExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag="exchange";
                if (responseCode.equals("1")) {
                    Intent intent = new Intent(getContext(), ModelExchangeActivity.class);
                    startActivity(intent);
                }else {
                    salecheckalert();
                }
            }
        });



        llDummySale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag="dummysale";
                if (responseCode.equals("1")) {
                    Intent intent = new Intent(getContext(), DummySaleDashBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    salecheckalert();
                }
            }
        });

        llWebSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowser();
            }
        });





        llNoSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (responseCode.equals("1")) {
                    flag="nosale";
                    Intent intent = new Intent(getContext(), NoSalesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    salecheckalert();
                }
            }
        });

        llReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag="return";
                if (responseCode.equals("1")) {
                    Intent intent = new Intent(getContext(), SalesReturnActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    salecheckalert();
                }
            }
        });

        llCompSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CompSalesDashboardActivity.class);
                intent.putExtra("flag",true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        llDailyComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), DailyCounterSaleDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



    }


    public void checksale() {
        String surl =  AppController.APIURL+"api/get_Comp_DisplayMateix_Status?AEMEmployeeID="+prefManager.getUserId()+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        llLoader.setVisibility(View.VISIBLE);
        llManage.setEnabled(false);
        llReport.setEnabled(false);
        llTarget.setEnabled(false);
        llModelExchange.setEnabled(false);
        llDummySale.setEnabled(false);
        llWebSales.setEnabled(false);
        llNoSales.setEnabled(false);
        llCompSale.setEnabled(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        llLoader.setVisibility(View.GONE);
                        llManage.setEnabled(true);
                        llReport.setEnabled(true);
                        llTarget.setEnabled(true);
                        llModelExchange.setEnabled(true);
                        llDummySale.setEnabled(true);
                        llWebSales.setEnabled(true);
                        llNoSales.setEnabled(true);
                        llCompSale.setEnabled(true);
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            responseText = job1.optString("responseText");
                            responseCode=job1.optString("responseCode");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            responseData=job1.optBoolean("responseData");



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.VISIBLE);
                llManage.setEnabled(false);
                llReport.setEnabled(false);
                llTarget.setEnabled(false);
                llModelExchange.setEnabled(false);
                llDummySale.setEnabled(false);
                llWebSales.setEnabled(false);
                llNoSales.setEnabled(false);
                llCompSale.setEnabled(false);
                Toast.makeText(getContext(), "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    private void salecheckalert() {
        AlertDialog.Builder dialogBuilder = new  AlertDialog.Builder(getContext(), R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_salecheck, null);
        dialogBuilder.setView(dialogView);

        TextView tvItem=(TextView)dialogView.findViewById(R.id.tvItem);
        tvItem.setText(responseText);

        Button btnOK = (Button) dialogView.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alet1.dismiss();

            }
        });

        Button btnSkip=(Button)dialogView.findViewById(R.id.btnSkip);
        if (responseData){
            btnSkip.setVisibility(View.VISIBLE);
        }else {
            btnSkip.setVisibility(View.GONE);
        }

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("manage")){
                    Intent intent=new Intent(getContext(),SalesManageDashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else  if (flag.equals("exchange")){
                    Intent intent=new Intent(getContext(),ModelExchangeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else  if (flag.equals("dummysale")){
                    Intent intent=new Intent(getContext(),DummySaleDashBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else  if (flag.equals("nosale")){
                    Intent intent=new Intent(getContext(),NoSalesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else  if (flag.equals("return")){
                    Intent intent=new Intent(getContext(),SalesReturnActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {

                }

                alet1.dismiss();
            }
        });


        alet1 = dialogBuilder.create();
        alet1.setCancelable(false);
        Window window = alet1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alet1.show();
    }


    private void openBrowser(){
        Uri uri = Uri.parse(prefManager.getWebSales()); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
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
            checkoutLimitCal.add(Calendar.MINUTE, 15);

            // Current time
            Calendar now = Calendar.getInstance();

            // Display format
            SimpleDateFormat displayFormat =
                    new SimpleDateFormat("hh:mm a", Locale.getDefault());

            String checkoutLimitTime =
                    displayFormat.format(checkoutLimitCal.getTime());

            if (now.before(checkoutLimitCal)) {
                // ✅ Checkout allowed
                String message= "You can check out until " + checkoutLimitTime +
                        " today. Post " + checkoutLimitTime +
                        ", the checkout option will be automatically disabled.";
                showAlertForCheckOut(message);


            } else {
                // ❌ Checkout blocked

                String message= "The stipulated checkout time has passed. Your checkout is now blocked.";
                showAlertForCheckOut(message);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlertForCheckOut(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        alertDialogBuilder.show();


    }
}