package io.cordova.ifb.activity;

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
import java.util.Calendar;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.AirConditionerDialogItemForDataAdapter;
import io.cordova.ifb.adapter.RefrigeratorFFDialogItemAdapter;
import io.cordova.ifb.module.DialogItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class RefregeratorFFDialogActivity extends AppCompatActivity {
    ArrayList<DialogItemModule> itemList = new ArrayList<>();
    ArrayList<DialogItemModule> itemListForData = new ArrayList<>();
    RecyclerView rvItem;
    RefrigeratorFFDialogItemAdapter itemAdapter;
    LinearLayout llCancel;
    LinearLayout llLoader, llMain, llAgain, llSave;
    PrefManager prefManager;
    ArrayList<String> item = new ArrayList<>();
    String itemId = "";
    String categoryId;
    ArrayList<String> sendAcModel=new ArrayList<>();
    String flag="0";
    String year,month,finalcialchecking;
    String preMonth;
    String previousmonthStatus;
    RecyclerView rvGetItem;
    LinearLayout llEdit;
    ArrayList<String>previousitem=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_conditioner_dialog);
        initialize();


        onClick();
    }

    private void initialize() {
        previousmonthStatus=getIntent().getStringExtra("previousmonthStatus");
        prefManager = new PrefManager(RefregeratorFFDialogActivity.this);

        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(RefregeratorFFDialogActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);

        rvGetItem = (RecyclerView) findViewById(R.id.rvGetItem);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(RefregeratorFFDialogActivity.this, LinearLayoutManager.VERTICAL, false);
        rvGetItem.setLayoutManager(layoutManager1);


        llCancel = (LinearLayout) findViewById(R.id.llCancel);
        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        llSave = (LinearLayout) findViewById(R.id.llSave);
        llAgain = (LinearLayout) findViewById(R.id.llAgain);
        categoryId="IFBPC1000040";



        int y = Calendar.getInstance().get(Calendar.YEAR);
        year = String.valueOf(y);
        Log.d("year", year);

        int m = Calendar.getInstance().get(Calendar.MONTH) + 1;
        Log.d("month", String.valueOf(m));
        if (m == 1) {
            month = "January";
            preMonth="December";

        } else if (m == 2) {
            month = "February";
            preMonth="January";
        } else if (m == 3) {
            month = "March";
            preMonth="February";
        } else if (m == 4) {
            month = "April";
            preMonth="March";
        } else if (m == 5) {
            month = "May";
            preMonth="April";
        } else if (m == 6) {
            month = "June";
            preMonth="May";
        } else if (m == 7) {
            month = "July";
            preMonth="June";
        } else if (m == 8) {
            month = "August";
            preMonth="July";
        } else if (m == 9) {
            month = "September";
            preMonth="August";
        } else if (m == 10) {
            month = "October";
            preMonth="September";
        } else if (m == 11) {
            month = "November";
            preMonth="October";
        } else if (m == 12) {
            month = "December";
            preMonth="Novemeber";
        }



        if (previousmonthStatus.equals("true")){
            if (preMonth.equals("January")) {
                int futureyear = y - 1;
                finalcialchecking = futureyear + "-" + year;
            } else if (preMonth.equals("February")) {
                int futureyear = y - 1;
                finalcialchecking = futureyear + "-" + year;
            } else if (preMonth.equals("March")) {
                int futureyear = y - 1;
                finalcialchecking = futureyear + "-" + year;
            } else {
                int futureyear = y + 1;
                finalcialchecking = year + "-" + futureyear;
            }
            getDialogItemList(preMonth,finalcialchecking);
        }else {
            if (month.equals("January")) {
                int futureyear = y - 1;
                finalcialchecking = futureyear + "-" + year;
            } else if (month.equals("February")) {
                int futureyear = y - 1;
                finalcialchecking = futureyear + "-" + year;
            } else if (month.equals("March")) {
                int futureyear = y - 1;
                finalcialchecking = futureyear + "-" + year;
            } else {
                int futureyear = y + 1;
                finalcialchecking = year + "-" + futureyear;
            }
            getDialogItemList(month,finalcialchecking);
        }

        llEdit=(LinearLayout)findViewById(R.id.llEdit);


    }

    private void getDialogItemList(String month,String financialYear) {
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llAgain.setVisibility(View.GONE);
        String surl =  AppController.APIURL+"api/get_EmployeeDisplayMatrixModelList?CategoryID="+categoryId+"&SecurityCode="+prefManager.getSecurityCode()+"&FinancialYear="+financialYear+"&Month="+month+"&AEMEmployeeID="+prefManager.getUserId();
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
                                    String Mapped_Flag=obj.optString("Mapped_Flag");
                                    DialogItemModule itemModel = new DialogItemModule(ModelName, ModelCode);
                                    itemList.add(itemModel);
                                    if (Mapped_Flag.equals("1")){
                                        itemListForData.add(itemModel);
                                    }



                                }

                                for (int j=0;j<itemListForData.size();j++){
                                    previousitem.add("IFBPC1000013-"+itemListForData.get(j).getItemId());
                                }


                                if (itemListForData.size()>0){
                                    rvItem.setVisibility(View.GONE);
                                    rvGetItem.setVisibility(View.VISIBLE);
                                    itemId=previousitem.toString().replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                }else {
                                    rvItem.setVisibility(View.VISIBLE);
                                    rvGetItem.setVisibility(View.GONE);
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
                            Toast.makeText(RefregeratorFFDialogActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(RefregeratorFFDialogActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setAdapter() {
        itemAdapter = new RefrigeratorFFDialogItemAdapter(itemList, RefregeratorFFDialogActivity.this);
        rvItem.setAdapter(itemAdapter);
        setAdapterForData();
    }

    private void setAdapterForData() {
       AirConditionerDialogItemForDataAdapter itemAdapter = new AirConditionerDialogItemForDataAdapter(itemListForData, RefregeratorFFDialogActivity.this);
        rvGetItem.setAdapter(itemAdapter);
    }


    public void updateItemStatus(int position, boolean status) {
        itemList.get(position).setSelected(status);
        if (itemList.get(position).isSelected() == true) {
            item.add("IFBPC1000040" + "-" + itemList.get(position).getItemId());
            int size=item.size();

            prefManager.saveRefIfbSize(size);
            Log.d("airifbsize", String.valueOf(prefManager.getAirIfbSize()));
        } else {
            item.clear();
        }


        Log.d("arpan", item.toString());
        String i = item.toString();
        String d = i.replace("[", "").replace("]", "");
        itemId = d.replaceAll("\\s+", "");
        Log.d("commas", itemId);
        prefManager.saveRefrigeratorId(itemId);

        itemAdapter.notifyDataSetChanged();
    }

    private void onClick() {
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                item.clear();
                AppController.ifbrefffsize=0;
                AppController.refffid="0";
            }
        });

        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.size()>0){
                    AppController.ifbrefffsize=item.size();
                    AppController.refffid=itemId;
                }else {
                    if (itemListForData.size()>0){
                        AppController.ifbrefffsize=itemListForData.size();
                        AppController.refffid=itemId;
                    }else {
                        AppController.ifbrefffsize=0;
                        AppController.refffid="0";
                    }


                }

                finish();
            }
        });

        llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.clear();
                rvGetItem.setVisibility(View.GONE);
                rvItem.setVisibility(View.VISIBLE);
            }
        });
    }
}
