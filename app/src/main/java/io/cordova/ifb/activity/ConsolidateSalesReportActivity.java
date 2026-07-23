package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;

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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.databinding.ActivityConsolidateSalesReportBinding;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class ConsolidateSalesReportActivity extends AppCompatActivity {

    ActivityConsolidateSalesReportBinding binding;
    PrefManager prefManager;
    int y;
    String year,month;
    String financialYear;
    AlertDialog alertDialog1,alertDialog,alertDialog2;
    TextView tvMonth,tvYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_consolidate_sales_report);
        initView();

        onCLick();
    }

    private void initView(){

        prefManager=new PrefManager(ConsolidateSalesReportActivity.this);
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


        getReport();
    }


    public void getReport() {
        String surl =  AppController.APIV2URL+"api/get_EmployeeSalesRefDetails?ReferenceNo=0&UserID="+prefManager.getUserId()+"&FinancialYear="+financialYear+"&Month="+month+"&Operation=1&SubOperation=3&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressDialog=new ProgressDialog(ConsolidateSalesReportActivity.this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus){
                                JSONArray responseData=job1.optJSONArray("responseData");
                                for (int i=0;i<responseData.length();i++){
                                    JSONObject jsonObject=responseData.optJSONObject(i);
                                    String Total_Sales=jsonObject.optString("Total_Sales");
                                    binding.tvTotalSales.setText(Total_Sales);

                                    String Delivery_update=jsonObject.optString("Delivery_update");
                                    binding.tvDeliveryUpdate.setText(Delivery_update);

                                    String Delivery_Pending=jsonObject.optString("Delivery_Pending");
                                    binding.tvDeliveryPending.setText(Delivery_Pending);

                                    String Token_Generated=jsonObject.optString("Token_Generated");
                                    binding.tvTokenGenerated.setText(Token_Generated);

                                    String Ticket_Generated=jsonObject.optString("Ticket_Generated");
                                    binding.tvTicketGenerated.setText(Ticket_Generated);

                                    String Ticket_Pending=jsonObject.optString("Ticket_Pending");
                                    binding.tvTicketPending.setText(Ticket_Pending);

                                    String Ticket_Approved=jsonObject.optString("Ticket_Approved");
                                    binding.tvTicketApproved.setText(Ticket_Approved);

                                    String Ticket_Rejected=jsonObject.optString("Ticket_Rejected");
                                    binding.tvTicketRejected.setText(Ticket_Rejected);

                                    String Ticket_Approval_Pending=jsonObject.optString("Ticket_Approval_Pending");
                                    binding.tvTicketApprovalPending.setText(Ticket_Approval_Pending);
                                }
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ConsolidateSalesReportActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ConsolidateSalesReportActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
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
//        RequestQueue requestQueue = Volley.newRequestQueue(ConsolidateSalesReportActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(ConsolidateSalesReportActivity.this);

        requestQueue.add(stringRequest);
    }

    private void onCLick(){
        binding.llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDialog();
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsolidateSalesReportActivity.this,NewDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.llTotalSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsolidateSalesReportActivity.this,RefNoReportActivity.class);
                intent.putExtra("subOperation","1");
                intent.putExtra("month",month);
                intent.putExtra("year",financialYear);
                intent.putExtra("report","Total Sales");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.llDeliveryUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsolidateSalesReportActivity.this,RefNoReportActivity.class);
                intent.putExtra("subOperation","2");
                intent.putExtra("month",month);
                intent.putExtra("year",financialYear);
                intent.putExtra("report","Delivery Update");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.llDeliveryPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsolidateSalesReportActivity.this,RefNoReportActivity.class);
                intent.putExtra("subOperation","3");
                intent.putExtra("month",month);
                intent.putExtra("year",financialYear);
                intent.putExtra("report","Delivery Pending");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        binding.llTokenGenerated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsolidateSalesReportActivity.this,RefNoReportActivity.class);
                intent.putExtra("subOperation","4");
                intent.putExtra("month",month);
                intent.putExtra("year",financialYear);
                intent.putExtra("report","Token Generated");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.llTicketGenerated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsolidateSalesReportActivity.this,RefNoReportActivity.class);
                intent.putExtra("subOperation","6");
                intent.putExtra("month",month);
                intent.putExtra("year",financialYear);
                intent.putExtra("report","Ticket Generated");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        binding.llTicketApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsolidateSalesReportActivity.this,RefNoReportActivity.class);
                intent.putExtra("subOperation","8");
                intent.putExtra("month",month);
                intent.putExtra("year",financialYear);
                intent.putExtra("report","Ticket Approved");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.llTicketRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsolidateSalesReportActivity.this,RefNoReportActivity.class);
                intent.putExtra("subOperation","9");
                intent.putExtra("month",month);
                intent.putExtra("year",financialYear);
                intent.putExtra("report","Ticket Rejected");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.llTicketApprovalPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsolidateSalesReportActivity.this,RefNoReportActivity.class);
                intent.putExtra("subOperation","10");
                intent.putExtra("month",month);
                intent.putExtra("year",financialYear);
                intent.putExtra("report","Ticket Approval Pending");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        binding.llTicketPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConsolidateSalesReportActivity.this,RefNoReportActivity.class);
                intent.putExtra("subOperation","7");
                intent.putExtra("month",month);
                intent.putExtra("year",financialYear);
                intent.putExtra("report","Ticket Pending");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });




    }

    private void searchDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ConsolidateSalesReportActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.attendancereportsearch, null);
        dialogBuilder.setView(dialogView);
        LinearLayout llYear = (LinearLayout) dialogView.findViewById(R.id.llYear);
        tvYear = (TextView) dialogView.findViewById(R.id.tvYear);
        tvMonth = (TextView) dialogView.findViewById(R.id.tvMonth);
        ImageView imgCancel = (ImageView) dialogView.findViewById(R.id.imgCancel);

        llYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showYearDialog();
            }
        });

        tvYear.setText(financialYear);
        LinearLayout llMonth = (LinearLayout) dialogView.findViewById(R.id.llMonth);
        llMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMonthDialog();

            }
        });
        tvMonth.setText(month);

        Button btnSubmit = (Button) dialogView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReport();
                alertDialog.dismiss();
            }
        });
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog.show();
    }


    private void showYearDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ConsolidateSalesReportActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_year, null);
        dialogBuilder.setView(dialogView);
        final TextView tvYear1=(TextView)dialogView.findViewById(R.id.tvYear1);
        final TextView tvYear2=(TextView)dialogView.findViewById(R.id.tvYear2);
        final TextView tvYear3=(TextView)dialogView.findViewById(R.id.tvYear3);
        LinearLayout llY1=(LinearLayout)dialogView.findViewById(R.id.llY1);
        LinearLayout llY2=(LinearLayout)dialogView.findViewById(R.id.llY2);
        LinearLayout llY3=(LinearLayout)dialogView.findViewById(R.id.llY3);
        int pastx2=y-1;
        int pastx1=y-2;
        String text1=pastx1+"-"+pastx2;
        String pasty1=String.valueOf(text1);
        tvYear1.setText(pasty1);


        String text2=pastx2+"-"+y;
        String pasty2=String.valueOf(text2);
        tvYear2.setText(text2);

        String pastx3=String.valueOf(y);
        int future3=y+1;
        String text3=pastx3+"-"+future3;
        tvYear3.setText(text3);

        ImageView imgCancel=(ImageView)dialogView.findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();


            }
        });


        llY3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                financialYear=tvYear3.getText().toString();
                Log.d("yrtrr",year);
                tvYear.setText(financialYear);
                alertDialog1.dismiss();

            }
        });

        llY2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                financialYear=tvYear2.getText().toString();
                alertDialog1.dismiss();
                tvYear.setText(financialYear);
                Log.d("ttt",year);
            }
        });

        llY1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                financialYear=tvYear1.getText().toString();
                alertDialog1.dismiss();
                tvYear.setText(financialYear);
                Log.d("ttt",year);
            }
        });

        alertDialog1= dialogBuilder.create();
        alertDialog1.setCancelable(true);
        Window window = alertDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog1.show();
    }

    private void showMonthDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ConsolidateSalesReportActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_month, null);
        dialogBuilder.setView(dialogView);
        LinearLayout llM1=(LinearLayout)dialogView.findViewById(R.id.llM1);
        LinearLayout llM2=(LinearLayout)dialogView.findViewById(R.id.llM2);
        LinearLayout llM3=(LinearLayout)dialogView.findViewById(R.id.llM3);
        LinearLayout llM4=(LinearLayout)dialogView.findViewById(R.id.llM4);
        LinearLayout llM5=(LinearLayout)dialogView.findViewById(R.id.llM5);
        LinearLayout llM6=(LinearLayout)dialogView.findViewById(R.id.llM6);
        LinearLayout llM7=(LinearLayout)dialogView.findViewById(R.id.llM7);
        LinearLayout llM8=(LinearLayout)dialogView.findViewById(R.id.llM8);
        LinearLayout llM9=(LinearLayout)dialogView.findViewById(R.id.llM9);
        LinearLayout llM10=(LinearLayout)dialogView.findViewById(R.id.llM10);
        LinearLayout llM11=(LinearLayout)dialogView.findViewById(R.id.llM111);
        LinearLayout llM112=(LinearLayout)dialogView.findViewById(R.id.llM12);

        final TextView tvJan=(TextView)dialogView.findViewById(R.id.tvJan);
        tvJan.setText("January");
        final TextView tvFeb=(TextView)dialogView.findViewById(R.id.tvFeb);
        final TextView tvMarch=(TextView)dialogView.findViewById(R.id.tvMarch);
        final TextView tvApril=(TextView)dialogView.findViewById(R.id.tvApril);
        final TextView tvMay=(TextView)dialogView.findViewById(R.id.tvMay);
        final TextView tvJune=(TextView)dialogView.findViewById(R.id.tvJune);
        final TextView tvJuly=(TextView)dialogView.findViewById(R.id.tvJuly);
        final TextView tvAugust=(TextView)dialogView.findViewById(R.id.tvAugust);
        final TextView tvSept=(TextView)dialogView.findViewById(R.id.tvSeptember);
        final TextView tvOct=(TextView)dialogView.findViewById(R.id.tvOct);
        final TextView tvNov=(TextView)dialogView.findViewById(R.id.tvNovember);
        final TextView tvDec=(TextView)dialogView.findViewById(R.id.tvDecember);

        llM1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvJan.getText().toString();
                Log.d("monnn",month);
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });
        llM2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvFeb.getText().toString();
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });

        llM3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvMarch.getText().toString();
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });
        llM4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvApril.getText().toString();
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });
        llM5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvMay.getText().toString();
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });

        llM6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvJune.getText().toString();
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });
        llM7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvJuly.getText().toString();
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });
        llM8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvAugust.getText().toString();
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });
        llM9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvSept.getText().toString();
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });
        llM10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvOct.getText().toString();
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });
        llM11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvNov.getText().toString();
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });
        llM112.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month=tvDec.getText().toString();
                tvMonth.setText(month);
                alertDialog2.dismiss();
            }
        });
        ImageView imgCancel=(ImageView)dialogView.findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog2.dismiss();
            }
        });


        alertDialog2 = dialogBuilder.create();
        alertDialog2.setCancelable(true);
        Window window = alertDialog2.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog2.show();

    }
}