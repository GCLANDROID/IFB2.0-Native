package io.cordova.ifb.activity;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
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
import io.cordova.ifb.adapter.OvenDialogItemAdapter;
import io.cordova.ifb.module.DialogItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class OvenDialogActivity extends AppCompatActivity {
    ArrayList<DialogItemModule> itemList = new ArrayList<>();
    RecyclerView rvItem;
    OvenDialogItemAdapter itemAdapter;
    LinearLayout llCancel;
    LinearLayout llMain, llLoader, llAgain,llSave;
    PrefManager prefManager;
    ArrayList<String> item = new ArrayList<>();
    ArrayList<DialogItemModule> itemListForData = new ArrayList<>();
    String previousmonthStatus;
    RecyclerView rvGetItem;
    LinearLayout llEdit;
    String preMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oven_dialog);
        initialize();
        getDialogItemList();
        onClick();
    }


    private void initialize() {
        prefManager = new PrefManager(OvenDialogActivity.this);
        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(OvenDialogActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);
        llCancel = (LinearLayout) findViewById(R.id.llCancel);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        llAgain = (LinearLayout) findViewById(R.id.llAgain);
        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        llSave = (LinearLayout) findViewById(R.id.llSave);
    }

    private void getDialogItemList() {
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llAgain.setVisibility(View.GONE);
        String surl =  AppController.APIURL+"api/ModelByCategory?CategoryID=IFBPC1000004&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("responseAir", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String ModelCode = obj.optString("ModelCode");
                                    String ModelName = obj.optString("ModelName");

                                    DialogItemModule itemModel = new DialogItemModule(ModelName, ModelCode);
                                    itemList.add(itemModel);


                                }

                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                llAgain.setVisibility(View.GONE);
                                setAdapter();
                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {
                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);


                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OvenDialogActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                llAgain.setVisibility(View.VISIBLE);

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(OvenDialogActivity.this);
        requestQueue.add(stringRequest);


    }

    private void setAdapter() {
        itemAdapter = new OvenDialogItemAdapter(itemList, OvenDialogActivity.this);
        rvItem.setAdapter(itemAdapter);
    }


    public void updateItemStatus(int position, boolean status) {
        itemList.get(position).setSelected(status);
        if (itemList.get(position).isSelected() == true) {
            item.add("IFBPC1000004"+"-"+itemList.get(position).getItemId());
            int size=item.size();
            prefManager.saveOvenIfbSize(size);
            Log.d("ovensize", String.valueOf(prefManager.getOvenIfbSize()));
        } else {
            item.clear();
        }


        Log.d("arpan", item.toString());
        String i = item.toString();
        String d = i.replace("[", "").replace("]", "");
        String ovenid = d.replaceAll("\\s+", "");
        Log.d("ovenid", ovenid);
        prefManager.saveBuiltInOvenId(ovenid);


        itemAdapter.notifyDataSetChanged();
    }

    private void onClick() {
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OvenDialogActivity.this,DisplayMatrixActivity.class);
                startActivity(intent);
                finish();
            }
        });

        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OvenDialogActivity.this,DisplayMatrixActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
