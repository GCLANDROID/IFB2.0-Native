package io.cordova.ifb.activity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.ReplenshiedReportAdapter;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class ReplenshedReportActivity extends AppCompatActivity {
    RecyclerView rvItem;
    ArrayList<ReplashedReportModel> itemList=new ArrayList<>();
    String status;
    PrefManager prefManager;
    ImageView imgBack,imgHome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replenshed_report);
        initView();
        getItemList();
        onClick();
    }

    private void initView(){
        prefManager=new PrefManager(ReplenshedReportActivity.this);
        status=getIntent().getStringExtra("status");

        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(ReplenshedReportActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);

        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);

    }

    private void getItemList(){
        final ProgressDialog pd = new ProgressDialog(ReplenshedReportActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        String surl =  AppController.APIURL+"api/get_DisplaymatrixReplaced?DSR_ReferenceNo=0&AEMEmployeeID="+prefManager.getUserId()+"&Status=2&Opertaion=1&SubOpertaion=3&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputCheck", surl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        pd.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i=0;i<responseData.length();i++) {
                                    JSONObject jobj = responseData.optJSONObject(i);
                                    String ReplacedDate=jobj.optString("ReplacedDate");
                                    String CategoryName=jobj.optString("CategoryName");
                                    String ModelName=jobj.optString("ModelName");
                                    ReplashedReportModel replenshiedModel =new ReplashedReportModel(ReplacedDate,CategoryName,ModelName);
                                    itemList.add(replenshiedModel);
                                }
                                setAdapter();
                            }else {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ReplenshedReportActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

                Toast.makeText(ReplenshedReportActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(ReplenshedReportActivity.this);
        requestQueue.add(stringRequest);


    }

    private void setAdapter(){
        ReplenshiedReportAdapter replenshiedPendingAdapter=new ReplenshiedReportAdapter(itemList,getApplicationContext());
        rvItem.setAdapter(replenshiedPendingAdapter);
    }

    private void onClick(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReplenshedReportActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}