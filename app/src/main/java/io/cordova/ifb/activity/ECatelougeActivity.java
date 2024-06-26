package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import io.cordova.ifb.adapter.ECatelougeAdapter;
import io.cordova.ifb.module.ECatelogModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;


public class ECatelougeActivity extends AppCompatActivity {
    ArrayList<ECatelogModel> itemList=new ArrayList<>();
    RecyclerView rvReport;
    LinearLayout llMain,llLoader,llAgain,llNoData,llSearch;
    int y;
    String year,month;
    String financialYear;
    PrefManager prefManager;
    AlertDialog alertDialog,alertDialog1,alertDialog2;
    TextView tvYear,tvMonth;
    ImageView imgBack,imgHome;
    String catId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecatelouge);
        initialize();
        getItemlist();

        onClick();
    }

    private void initialize(){
        prefManager=new PrefManager(ECatelougeActivity.this);
        rvReport=(RecyclerView)findViewById(R.id.rvReport);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(ECatelougeActivity.this, LinearLayoutManager.VERTICAL, false);
        rvReport.setLayoutManager(layoutManager);
        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        llAgain = (LinearLayout) findViewById(R.id.llAgain);
        llNoData = (LinearLayout) findViewById(R.id.llNodata);

        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);
        catId=getIntent().getStringExtra("catId");
    }

    private void getItemlist(){
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        llAgain.setVisibility(View.GONE);
        String surl =  AppController.APIURL+"api/get_ProductCatalog?ECatalogID="+catId+"&CategoryId=0&Operation=2";
        Log.d("inputSalesReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        // attendabceInfiList.clear();

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
                                    String CategoryID=obj.optString("CategoryID");
                                    String CategoryName=obj.optString("CategoryName");

                                    ECatelogModel obj2 = new ECatelogModel(CategoryID,CategoryName);
                                     itemList.add(obj2);


                                }

                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                llNoData.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);
                                setAdapter();
                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {
                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.GONE);
                                llNoData.setVisibility(View.VISIBLE);
                                llAgain.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ECatelougeActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                llNoData.setVisibility(View.GONE);
                llAgain.setVisibility(View.VISIBLE);

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(ECatelougeActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setAdapter(){
        ECatelougeAdapter sAdpater=new ECatelougeAdapter(itemList, ECatelougeActivity.this,catId);
        rvReport.setAdapter(sAdpater);
    }

    private void onClick(){


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ECatelougeActivity.this,DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }




}
