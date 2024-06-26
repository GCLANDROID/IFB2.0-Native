package io.cordova.ifb.activity;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
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
import io.cordova.ifb.adapter.RefrigeratorDialogItemAdapter;
import io.cordova.ifb.module.DialogItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class RefrigeratorDialogActivity extends AppCompatActivity {
    ArrayList<DialogItemModule> itemList=new ArrayList<>();
    RecyclerView rvItem;
    RefrigeratorDialogItemAdapter itemAdapter;
    LinearLayout llCancel;
    LinearLayout llMain,llLoader,llAgain,llSave;
    PrefManager prefManager;
    ArrayList<String>item=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_refrigerator_dialog);
        this.setFinishOnTouchOutside(false);
        initialize();
        getDialogItemList();
        onClick();
    }

    private void initialize(){
        prefManager=new PrefManager(getApplicationContext());
        rvItem=(RecyclerView)findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(RefrigeratorDialogActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);
        llCancel=(LinearLayout) findViewById(R.id.llCancel);
        llMain=(LinearLayout) findViewById(R.id.llMain);
        llLoader=(LinearLayout) findViewById(R.id.llLoader);
        llAgain=(LinearLayout) findViewById(R.id.llAgain);
        llSave=(LinearLayout) findViewById(R.id.llSave);
    }

    private void getDialogItemList(){
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llAgain.setVisibility(View.GONE);
        String surl =  AppController.APIURL+"api/ModelByCategory?CategoryID=IFBPC1000013&SecurityCode=" + prefManager.getSecurityCode();
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
                                for (int i = 0; i <responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String ModelCode=obj.optString("ModelCode");
                                    String ModelName=obj.optString("ModelName");

                                    DialogItemModule itemModel=new DialogItemModule(ModelName,ModelCode);
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
                            Toast.makeText(RefrigeratorDialogActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(RefrigeratorDialogActivity.this);
        requestQueue.add(stringRequest);



    }

    private void setAdapter(){
        itemAdapter=new RefrigeratorDialogItemAdapter(itemList,RefrigeratorDialogActivity.this);
        rvItem.setAdapter(itemAdapter);
    }


    public void updateItemStatus(int position, boolean status) {
        itemList.get(position).setSelected(status);
        if (itemList.get(position).isSelected()==true) {
            item.add("IFBPC1000013"+"-"+itemList.get(position).getItemId());
            prefManager.saveRefIfbSize(item.size());
        }else {
            item.clear();
        }


        Log.d("arpan", item.toString());
        String i = item.toString();
        String d = i.replace("[", "").replace("]", "");
        String refId = d.replaceAll("\\s+", "");
        Log.d("refId", refId);
        prefManager.saveRefrigeratorId(refId);

        itemAdapter.notifyDataSetChanged();
    }

    private void onClick(){
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RefrigeratorDialogActivity.this,DisplayMatrixActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
