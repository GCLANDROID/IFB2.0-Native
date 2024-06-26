package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

import io.cordova.ifb.R;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class SalesDashboardActivity extends AppCompatActivity {
    LinearLayout llManage,llReport,llTarget,llWebSales,llReturn,llModelExchange,llNoSales,llCompSale,llDummySale,llDownload,llACCampign,llDailyComp;
    PrefManager prefManager;
    ImageView imgBack,imgHome;
    String responseText,responseCode;
    AlertDialog alet1;
    boolean responseData;
    String flag;
    LinearLayout llLoader;
    LinearLayout llDW,llSalesLead,llCollaboration,llReplenished,llCallUpdation,llRefInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_dashboard);
        initialize();
        checksale();
        onClick();
    }

    private void initialize(){
        prefManager=new PrefManager(SalesDashboardActivity.this);
        llManage=(LinearLayout)findViewById(R.id.llManage);
        llReport=(LinearLayout)findViewById(R.id.llReport);
        llTarget=(LinearLayout)findViewById(R.id.llTarget);
        llWebSales=(LinearLayout)findViewById(R.id.llWebSales);
        llReturn=(LinearLayout)findViewById(R.id.llReturn);
        llNoSales=(LinearLayout)findViewById(R.id.llNoSales);
        llModelExchange=(LinearLayout)findViewById(R.id.llModelExchange);
        llCompSale=(LinearLayout)findViewById(R.id.llCompSale);
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);
        llDummySale=(LinearLayout)findViewById(R.id.llDummySale);
        llDownload=(LinearLayout)findViewById(R.id.llDownload);
        llLoader=(LinearLayout)findViewById(R.id.llLoader);
        llDW=(LinearLayout)findViewById(R.id.llDW);
        llACCampign=(LinearLayout)findViewById(R.id.llAcCampign);
        llDailyComp=(LinearLayout)findViewById(R.id.llDailyComp);
        llSalesLead=(LinearLayout)findViewById(R.id.llSalesLead);
        llCollaboration=(LinearLayout)findViewById(R.id.llCollaboration);
        llReplenished=(LinearLayout)findViewById(R.id.llReplenished);
        llCallUpdation=(LinearLayout)findViewById(R.id.llCallUpdation);
        llRefInfo=(LinearLayout) findViewById(R.id.llRefInfo);

    }

    private void onClick(){


        llManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    flag="manage";
                if (responseCode.equals("1")) {
                    Intent intent = new Intent(SalesDashboardActivity.this, SalesManageDashboardActivity.class);
                    startActivity(intent);
                }else {
                    salecheckalert();
                }
            }
        });

        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesDashboardActivity.this,SalesReportActivity.class);
                startActivity(intent);
            }
        });

        llCollaboration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesDashboardActivity.this,TeamCollaborationActivity.class);
                startActivity(intent);
            }
        });

        llDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesDashboardActivity.this,SalesReportDownldActivity.class);
                startActivity(intent);
            }
        });
        llSalesLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesDashboardActivity.this,SalesLeadDashboardActivity.class);
                startActivity(intent);
            }
        });


        llTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesDashboardActivity.this,SalesTargetActivity.class);
                startActivity(intent);
            }
        });

        llReplenished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesDashboardActivity.this,ReplenishedActivity.class);
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
                Intent intent = new Intent(SalesDashboardActivity.this, CustomerCallingDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        llRefInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SalesDashboardActivity.this, RefInfoManageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("manage",1);
                startActivity(intent);
            }
        });

        llDW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesDashboardActivity.this,DWDashboardActivity.class);
                startActivity(intent);
            }
        });

        llModelExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag="exchange";
                if (responseCode.equals("1")) {
                    Intent intent = new Intent(SalesDashboardActivity.this, ModelExchangeActivity.class);
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
                    Intent intent = new Intent(SalesDashboardActivity.this, DummySaleDashBoardActivity.class);
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

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesDashboardActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        llNoSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (responseCode.equals("1")) {
                    flag="nosale";
                    Intent intent = new Intent(SalesDashboardActivity.this, NoSalesActivity.class);
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
                    Intent intent = new Intent(SalesDashboardActivity.this, SalesReturnActivity.class);
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
                Intent intent=new Intent(SalesDashboardActivity.this,CompSalesDashboardActivity .class);
                intent.putExtra("flag",true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        llDailyComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesDashboardActivity.this,DailyCounterSaleDashboardActivity .class);
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
                            Toast.makeText(SalesDashboardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
                Toast.makeText(SalesDashboardActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesDashboardActivity.this);
        requestQueue.add(stringRequest);

    }


    private void salecheckalert() {
         AlertDialog.Builder dialogBuilder = new  AlertDialog.Builder(SalesDashboardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    Intent intent=new Intent(SalesDashboardActivity.this,SalesManageDashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else  if (flag.equals("exchange")){
                    Intent intent=new Intent(SalesDashboardActivity.this,ModelExchangeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else  if (flag.equals("dummysale")){
                    Intent intent=new Intent(SalesDashboardActivity.this,DummySaleDashBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else  if (flag.equals("nosale")){
                    Intent intent=new Intent(SalesDashboardActivity.this,NoSalesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else  if (flag.equals("return")){
                    Intent intent=new Intent(SalesDashboardActivity.this,SalesReturnActivity.class);
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

    @Override
    protected void onPause() {
        super.onPause();
        checksale();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checksale();
    }
}
