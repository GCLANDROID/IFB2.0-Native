package io.cordova.ifb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.Toast;

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

import io.cordova.ifb.R;
import io.cordova.ifb.databinding.ActivityNewDashboardBinding;
import io.cordova.ifb.fragment.AttendanceFragment;
import io.cordova.ifb.fragment.HomeFragment;
import io.cordova.ifb.fragment.MoreFragment;
import io.cordova.ifb.fragment.SalesManagementFragment;
import io.cordova.ifb.test.MainTestActivity;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class NewDashboardActivity extends AppCompatActivity {
    ActivityNewDashboardBinding binding;
    PrefManager preference;
    boolean mslideState;
    String Date,Status,Time,LogoutTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_new_dashboard);
        initView();
        cuurentAttendanceStatus();

    }

    private void initView(){

        preference=new PrefManager(NewDashboardActivity.this);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            binding.tvCSRName.setText("Good Morning! "+preference.getEmpName());
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            binding.tvCSRName.setText("Good Afternoon! "+preference.getEmpName());
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            binding.tvCSRName.setText("Good Evening! "+preference.getEmpName());
        } else if (timeOfDay >= 21 && timeOfDay < 24) {

            binding.tvCSRName.setText("Good Evening! "+preference.getEmpName());
        }

        binding.tvStoreName.setText("("+preference.getCounter()+")");
        binding.lnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAttendanceFragment();
            }
        });
        binding.lnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHomeFragment();
            }
        });
        binding.lnSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSalesFragment();
            }
        });
        binding.lnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMoreFragment();
            }
        });
        binding.imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewDashboardActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.fbTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewDashboardActivity.this, IncentiveActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });




    }

    public void visibility(){
        binding.lnAttendance.setVisibility(View.VISIBLE);
        binding.lnSales.setVisibility(View.VISIBLE);
        binding.lnMore.setVisibility(View.VISIBLE);
        binding.lnHome.setVisibility(View.VISIBLE);
    }


    public void loadHomeFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        HomeFragment pfragment=new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Time", Time);
        pfragment.setArguments(bundle);
        transaction.replace(R.id.frameLayout, pfragment);
        transaction.commit();


        //home
        binding.imgHomeSelected.setVisibility(View.VISIBLE);
        binding.imgHomeUnSelected.setVisibility(View.GONE);
        binding.tvHome.setTextColor(Color.parseColor("#FFFFFF"));



        //attendance

        binding.imgAttendanceUnSelected.setVisibility(View.VISIBLE);
        binding.imgAttendanceSelected.setVisibility(View.GONE);
        binding.tvAttendance.setTextColor(Color.parseColor("#C1BDBC"));

        //sales
        binding.imgSaleUnSelected.setVisibility(View.VISIBLE);
        binding.imgSaleSelected.setVisibility(View.GONE);
        binding.tvSales.setTextColor(Color.parseColor("#C1BDBC"));

        //more
        binding.imgMoreUnSelected.setVisibility(View.VISIBLE);
        binding.imgMoreSelected.setVisibility(View.GONE);
        binding.tvMore.setTextColor(Color.parseColor("#C1BDBC"));





    }


    public void loadAttendanceFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AttendanceFragment pfragment=new AttendanceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Date", Date);
        bundle.putString("Time", Time);
        bundle.putString("Status", Status);
        bundle.putString("LogoutTime", LogoutTime);

        pfragment.setArguments(bundle);
        transaction.replace(R.id.frameLayout, pfragment);
        transaction.commit();


        //home
        binding.imgHomeSelected.setVisibility(View.GONE);
        binding.imgHomeUnSelected.setVisibility(View.VISIBLE);
        binding.tvHome.setTextColor(Color.parseColor("#C1BDBC"));



        //attendance

        binding.imgAttendanceUnSelected.setVisibility(View.GONE);
        binding.imgAttendanceSelected.setVisibility(View.VISIBLE);
        binding.tvAttendance.setTextColor(Color.parseColor("#FFFFFF"));

        //sales
        binding.imgSaleUnSelected.setVisibility(View.VISIBLE);
        binding.imgSaleSelected.setVisibility(View.GONE);
        binding.tvSales.setTextColor(Color.parseColor("#C1BDBC"));

        //more
        binding.imgMoreUnSelected.setVisibility(View.VISIBLE);
        binding.imgMoreSelected.setVisibility(View.GONE);
        binding.tvMore.setTextColor(Color.parseColor("#C1BDBC"));





    }

    public void loadSalesFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SalesManagementFragment pfragment=new SalesManagementFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Time", Time);
        pfragment.setArguments(bundle);
        transaction.replace(R.id.frameLayout, pfragment);
        transaction.commit();


        //home
        binding.imgHomeSelected.setVisibility(View.GONE);
        binding.imgHomeUnSelected.setVisibility(View.VISIBLE);
        binding.tvHome.setTextColor(Color.parseColor("#C1BDBC"));



        //attendance

        binding.imgAttendanceUnSelected.setVisibility(View.VISIBLE);
        binding.imgAttendanceSelected.setVisibility(View.GONE);
        binding.tvAttendance.setTextColor(Color.parseColor("#C1BDBC"));

        //sales
        binding.imgSaleUnSelected.setVisibility(View.GONE);
        binding.imgSaleSelected.setVisibility(View.VISIBLE);
        binding.tvSales.setTextColor(Color.parseColor("#FFFFFF"));

        //more
        binding.imgMoreUnSelected.setVisibility(View.VISIBLE);
        binding.imgMoreSelected.setVisibility(View.GONE);
        binding.tvMore.setTextColor(Color.parseColor("#C1BDBC"));





    }
    public void loadMoreFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MoreFragment pfragment=new MoreFragment();
        transaction.replace(R.id.frameLayout, pfragment);
        transaction.commit();

        //home
        binding.imgHomeSelected.setVisibility(View.GONE);
        binding.imgHomeUnSelected.setVisibility(View.VISIBLE);
        binding.tvHome.setTextColor(Color.parseColor("#C1BDBC"));



        //attendance

        binding.imgAttendanceUnSelected.setVisibility(View.VISIBLE);
        binding.imgAttendanceSelected.setVisibility(View.GONE);
        binding.tvAttendance.setTextColor(Color.parseColor("#C1BDBC"));

        //sales
        binding.imgSaleUnSelected.setVisibility(View.VISIBLE);
        binding.imgSaleSelected.setVisibility(View.GONE);
        binding.tvSales.setTextColor(Color.parseColor("#C1BDBC"));

        //more
        binding.imgMoreUnSelected.setVisibility(View.GONE);
        binding.imgMoreSelected.setVisibility(View.VISIBLE);
        binding.tvMore.setTextColor(Color.parseColor("#FFFFFF"));





    }


    private void cuurentAttendanceStatus() {
        final ProgressDialog progressDialog = new ProgressDialog(NewDashboardActivity.this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String surl = AppController.APIURL+"api/SelfAttendanceToDay?LoginID=" + preference.getUserId() + "&SecurityCode=" + preference.getSecurityCode();
        Log.d("inputcheck", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);
                        progressDialog.dismiss();

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");

                            //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                            JSONArray responseData = job1.optJSONArray("responseData");

                            JSONObject obj = responseData.getJSONObject(0);
                             Date=obj.optString("Date");
                             Status=obj.optString("Status");
                             Time=obj.optString("Time");
                             LogoutTime=obj.optString("LogoutTime");


                            loadHomeFragment();




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NewDashboardActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(NewDashboardActivity.this);
        requestQueue.add(stringRequest);
    }


}