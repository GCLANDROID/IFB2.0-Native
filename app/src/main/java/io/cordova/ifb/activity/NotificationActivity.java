package io.cordova.ifb.activity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.NotificationAdapter;
import io.cordova.ifb.databinding.ActivityNotificationpopupBinding;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    PrefManager prefManager;
    ActivityNotificationpopupBinding binding;
    JSONArray itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_notificationpopup);
        this.setFinishOnTouchOutside(false);
        initialize();

    }

    private void initialize() {
        prefManager = new PrefManager(getApplicationContext());
        binding.rvItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getItemForNotification();
        binding.btnSkip.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v==binding.btnSkip){

            Intent intent=new Intent(NotificationActivity.this,DashBoardActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void getItemForNotification() {
        final ProgressDialog progressDialog=new ProgressDialog(NotificationActivity.this);
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
                        itemList=new JSONArray();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                itemList=responseData;
                                if (responseData.length()>0){

                                    JSONObject object=responseData.optJSONObject(0);
                                    String HeaderTitle=object.optString("HeaderTitle");
                                    binding.tvTitle.setText(HeaderTitle);
                                    setAdapter();

                                }else {

                                }




                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {



                                Intent intent=new Intent(NotificationActivity.this,DashBoardActivity.class);
                                startActivity(intent);
                                finish();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NotificationActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(NotificationActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setAdapter() {
        final NotificationAdapter aAdapter = new NotificationAdapter(itemList);
        binding.rvItem.setAdapter(aAdapter);


        //Auto scroll

    }




}
