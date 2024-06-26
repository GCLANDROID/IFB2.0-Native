package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import io.cordova.ifb.adapter.ReplenshiedPendingAdapter;
import io.cordova.ifb.module.ReplenshiedModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class ReplenishedItemActivity extends AppCompatActivity {
    RecyclerView rvItem;
    ArrayList<ReplenshiedModel>itemList=new ArrayList<>();
    String status;
    PrefManager prefManager;
    LinearLayout llReport;
    ImageView imgBack,imgHome;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replenished_pending);
        initView();
        getItemList();
        onClick();
    }

    private void initView(){
        prefManager=new PrefManager(ReplenishedItemActivity.this);
        status=getIntent().getStringExtra("status");

        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(ReplenishedItemActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);
        llReport=(LinearLayout)findViewById(R.id.llReport);
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);
        builder = new AlertDialog.Builder(ReplenishedItemActivity.this);
    }

    private void getItemList(){
        final ProgressDialog pd = new ProgressDialog(ReplenishedItemActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        String surl =  AppController.APIURL+"api/get_DisplaymatrixReplaced?DSR_ReferenceNo=0&AEMEmployeeID="+prefManager.getUserId()+"&Status="+status+"&Opertaion=1&SubOpertaion=2&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputCheck", surl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        itemList.clear();
                        pd.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i=0;i<responseData.length();i++) {
                                    JSONObject jobj = responseData.optJSONObject(i);
                                    String Entry_Date=jobj.optString("Entry_Date");
                                    String CategoryName=jobj.optString("CategoryName");
                                    String ModelName=jobj.optString("ModelName");
                                    String DSR_ReferenceNo=jobj.optString("DSR_ReferenceNo");
                                    String Replaced_Status=jobj.optString("Replaced_Status");
                                    String CategoryID=jobj.optString("CategoryID");
                                    String ModelCode=jobj.optString("ModelCode");
                                    ReplenshiedModel replenshiedModel =new ReplenshiedModel(Entry_Date,CategoryName,ModelName,DSR_ReferenceNo);
                                    replenshiedModel.setStatus(Replaced_Status);
                                    replenshiedModel.setCategoryID(CategoryID);
                                    replenshiedModel.setModelID(ModelCode);
                                    itemList.add(replenshiedModel);
                                }
                                setAdapter();
                            }else {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ReplenishedItemActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

                Toast.makeText(ReplenishedItemActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(ReplenishedItemActivity.this);
        requestQueue.add(stringRequest);


    }

    private void setAdapter(){
        ReplenshiedPendingAdapter replenshiedPendingAdapter=new ReplenshiedPendingAdapter(itemList,getApplicationContext());
        rvItem.setAdapter(replenshiedPendingAdapter);
    }

    private void onClick(){
        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReplenishedItemActivity.this,ReplenshedReportActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReplenishedItemActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}