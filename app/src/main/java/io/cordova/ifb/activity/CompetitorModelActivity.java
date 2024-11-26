package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.AirConditionerDialogItemAdapter;
import io.cordova.ifb.adapter.CompetitorModelAdapter;
import io.cordova.ifb.module.CompetitorModelModule;
import io.cordova.ifb.module.DialogItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class CompetitorModelActivity extends AppCompatActivity {
    String categoryID,compid,financialyear,month,Category,Company;
    RecyclerView rvItem;
    ArrayList<CompetitorModelModule>itemList=new ArrayList<>();
    PrefManager prefManager;
    TextView tvToolBar;
    Button btnSave;
    int sumqty=0;
    String Flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitor_model);
        initView();
        onClick();
    }

    private void initView(){
        prefManager=new PrefManager(CompetitorModelActivity.this);
        btnSave=(Button)findViewById(R.id.btnSave);
        tvToolBar=(TextView)findViewById(R.id.tvToolBar);
        Flag=getIntent().getStringExtra("Flag");
        categoryID=getIntent().getStringExtra("categoryID");
        compid=getIntent().getStringExtra("compid");
        financialyear=getIntent().getStringExtra("financialyear");
        month=getIntent().getStringExtra("month");
        Category=getIntent().getStringExtra("Category");
        Company=getIntent().getStringExtra("Company");
        rvItem=(RecyclerView) findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(CompetitorModelActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);
        tvToolBar.setText(Category+" - "+Company);
        getItemList();
    }

    private void onClick(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makeJsonObject();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void getItemList() {
        ProgressDialog pd=new ProgressDialog(CompetitorModelActivity.this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        String surl =  AppController.APIURL+"api/get_EmployeeDisplayMatrixModelListV1?AEMEmployeeID="+prefManager.getUserId()+"&FinancialYear="+financialyear+"&Month="+month+"&CategoryID="+categoryID+"&CompetitorCompanyID="+compid+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);
                        pd.dismiss();

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
                                    int Qty=obj.optInt("Qty");
                                    CompetitorModelModule compModule=new CompetitorModelModule();
                                    compModule.setModelID(ModelCode);
                                    compModule.setModelName(ModelName);
                                    compModule.setQty(Qty);
                                    itemList.add(compModule);




                                }



                                setAdapter();
                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {


                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CompetitorModelActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(CompetitorModelActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setAdapter() {
        CompetitorModelAdapter compAdapter = new CompetitorModelAdapter(itemList, CompetitorModelActivity.this);
        rvItem.setAdapter(compAdapter);

    }


    private void makeJsonObject() throws JSONException {


        JSONArray jsonArray = new JSONArray();
        JSONObject finalJsonObject = new JSONObject();
        for (int i = 0; i < itemList.size(); i++) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("AEMEmployeeID",prefManager.getUserId());
                jsonObject.put("CompetitorCompanyID",compid);
                jsonObject.put("CategoryID",categoryID);
                jsonObject.put("CompetitorModelID",itemList.get(i).getModelID());
                jsonObject.put("Quantity",itemList.get(i).getQty());
                jsonArray.put(jsonObject);

        }

        finalJsonObject.put("TokenlistTicket",jsonArray);
        finalJsonObject.put("Operation","3");
        finalJsonObject.put("SecurityCode",prefManager.getSecurityCode());
        Log.e("Finalobj", "makeJsonObject: "+finalJsonObject.toString());
        savemodel(finalJsonObject);



    }

    private void sumofQty(JSONObject obj){

        JSONArray TokenlistTicket=obj.optJSONArray("TokenlistTicket");
        for (int i=0;i<TokenlistTicket.length();i++){
            JSONObject tookenobj=TokenlistTicket.optJSONObject(i);
            int Quantity=tookenobj.optInt("Quantity");
            sumqty=sumqty+Quantity;

        }
        if (Flag.equalsIgnoreCase("Air_LG")){
            AppController.air_lg=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_Samsung")){
            AppController.air_samsung=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_Daiken")){
            AppController.air_daiken=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_Carrier")){
            AppController.air_carrier=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_Bluestar")){
            AppController.air_bluestar=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_Voltas")){
            AppController.air_voltas=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_Onida")){
            AppController.air_onida=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_Panasonic")){
            AppController.air_panasonic=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_Whirlpool")){
            AppController.air_whirlpool=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_OG")){
            AppController.air_ogeneral=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_Godrej")){
            AppController.air_godrej=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_Haier")){
            AppController.air_Haier=sumqty;
        }else if (Flag.equalsIgnoreCase("Air_LLyods")){
            AppController.air_llyods=sumqty;
        }else if (Flag.equalsIgnoreCase("Cloths_Bosch")){
            AppController.cloths_bosch=sumqty;
        }else if (Flag.equalsIgnoreCase("Dish_Bosch")){
            AppController.dish_bosch=sumqty;
        }else if (Flag.equalsIgnoreCase("Dish_Lg")){
            AppController.dish_lg=sumqty;
        }else if (Flag.equalsIgnoreCase("Dish_Sam")){
            AppController.dish_sam=sumqty;
        }else if (Flag.equalsIgnoreCase("Micro_LG")){
            AppController.micro_lg=sumqty;
        }else if (Flag.equalsIgnoreCase("Micro_Sam")){
            AppController.micro_sam=sumqty;
        }else if (Flag.equalsIgnoreCase("Micro_Whirl")){
            AppController.micro_whirl=sumqty;
        }else if (Flag.equalsIgnoreCase("Micro_Pana")){
            AppController.micro_pana=sumqty;
        }else if (Flag.equalsIgnoreCase("Micro_Godrej")){
            AppController.micro_godrej=sumqty;
        }else if (Flag.equalsIgnoreCase("Micro_Onida")){
            AppController.micro_onida=sumqty;
        }else if (Flag.equalsIgnoreCase("KA_Faber")){
            AppController.ka_faber=sumqty;
        }else if (Flag.equalsIgnoreCase("KA_Sun")){
            AppController.ka_sun=sumqty;
        }else if (Flag.equalsIgnoreCase("KA_Elica")){
            AppController.ka_elica=sumqty;
        }else if (Flag.equalsIgnoreCase("KA_Kaff")){
            AppController.ka_kaff=sumqty;
        }else if (Flag.equalsIgnoreCase("KA_Bosch")){
            AppController.ka_bosch=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_FLU_LG")){
            AppController.wmflu_lg=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_FLU_Sam")){
            AppController.wmflu_sam=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_FLU_Bosch")){
            AppController.wmflu_bosch=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_FLU_Whirlpool")){
            AppController.wmflu_whirlpool=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_FLU_Beko")){
            AppController.wmflu_beko=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_TL_LG")){
            AppController.tl_lg=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_TL_Sam")){
            AppController.tl_sam=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_TL_Bosch")){
            AppController.tl_bosch=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_TL_Whirlpool")){
            AppController.tl_whirlpool=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_TL_Pana")){
            AppController.tl_pana=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_TL_Godrej")){
            AppController.tl_godrej=sumqty;
        }else if (Flag.equalsIgnoreCase("WM_TL_Onida")){
            AppController.tl_onida=sumqty;
        }else if (Flag.equalsIgnoreCase("Dryer_LG")){
            AppController.dryer_lg=sumqty;
        }else if (Flag.equalsIgnoreCase("Dryer_Samsung")){
            AppController.dryer_sam=sumqty;
        }else if (Flag.equalsIgnoreCase("Dryer_Whirlpool")){
            AppController.dryer_whirlpool=sumqty;
        }else if (Flag.equalsIgnoreCase("Dryer_Panasonic")){
            AppController.dryer_pansonic=sumqty;
        }else if (Flag.equalsIgnoreCase("Dryer_Godrej")){
            AppController.dryer_godrej=sumqty;
        }else if (Flag.equalsIgnoreCase("Dryer_Onida")){
            AppController.dryer_onida=sumqty;
        }else if (Flag.equalsIgnoreCase("DC_SAM")){
            AppController.dc_sam=sumqty;
        }else if (Flag.equalsIgnoreCase("DC_LG")){
            AppController.dc_lg=sumqty;
        }else if (Flag.equalsIgnoreCase("DC_WHIRLPOOL")){
            AppController.dc_whirlpool=sumqty;
        }else if (Flag.equalsIgnoreCase("DC_HAIER")){
            AppController.dc_haier=sumqty;
        }else if (Flag.equalsIgnoreCase("DC_Godrej")){
            AppController.dc_godrej=sumqty;
        }else if (Flag.equalsIgnoreCase("FF_Godrej")){
            AppController.ff_godrej=sumqty;
        }else if (Flag.equalsIgnoreCase("FF_HAIER")){
            AppController.ff_haier=sumqty;
        }

        onBackPressed();

    }


    private void savemodel(JSONObject jsonObject) {
        Log.e("LOGIN", "login: "+jsonObject.toString());
        final ProgressDialog pd = new ProgressDialog(CompetitorModelActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        AndroidNetworking.post(AppController.APIURL + "api/post_DisplayMatrixNonIFB")
                .addJSONObjectBody(jsonObject)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject job1 = response;
                        Log.e("LOGIN", "@@@@@@" + job1);
                        pd.dismiss();
                        boolean responseStatus=job1.optBoolean("responseStatus");
                        if (responseStatus){
                            sumofQty(jsonObject);
                        }

                        // boolean _status = job1.getBoolean("status");
                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("LOGIN", "onError: "+error );
                        pd.dismiss();
                    }
                });
    }
}