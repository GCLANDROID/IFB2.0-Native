package io.cordova.ifb.activity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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
import io.cordova.ifb.databinding.ActivityVaccineReportBinding;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class VaccineReportActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityVaccineReportBinding binding;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_vaccine_report);
        initView();
    }

    private void initView(){
        prefManager=new PrefManager(VaccineReportActivity.this);
        getItemList();
        binding.imgBack.setOnClickListener(this);
        binding.imgHome.setOnClickListener(this);
    }

    private void getItemList(){
        final ProgressDialog progressDialog=new ProgressDialog(VaccineReportActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String surl = AppController.APIURL+"api/get_EmployeeVaccinationStatus?AEMEmployeeID="+prefManager.getUserId()+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputSalesReport", surl);
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
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(0);
                                    String VaccinationStatus=obj.optString("VaccinationStatus");
                                    String Vaccinated_OneDose_By=obj.optString("Vaccinated_OneDose_By");
                                    String Vaccine_OneDose_Type=obj.optString("Vaccine_OneDose_Type");
                                    String firstVaccineDate=obj.optString("Vaccination_OneDose_Date");
                                    String Vaccine_OneDose_Delay_Reason=obj.optString("Vaccine_OneDose_Delay_Reason");
                                    String Vaccine_TwoDose_Delay_Status=obj.optString("Vaccine_TwoDose_Delay_Status");
                                    String scndVaccineDueDate=obj.optString("Vaccination_TwoDose_DueDate");
                                    String Vaccine_TwoDose_Delay_Reason=obj.optString("Vaccine_TwoDose_Delay_Reason");
                                    String Vaccinated_TwoDose_By=obj.optString("Vaccinated_TwoDose_By");
                                    String scndVaccineDate=obj.optString("Vaccination_TwoDose_Date");
                                   binding.tvVaccineStaus.setText("Ans. "+VaccinationStatus);
                                   binding.tvVaccineType.setText("Ans. "+Vaccine_OneDose_Type);
                                   binding.tvVaccineFirstDoseDate.setText("Ans. "+firstVaccineDate);
                                   binding.tvVaccineFirstDoseTaken.setText("Ans. "+Vaccinated_OneDose_By);
                                   binding.tvVaccineFirstDoseDelayed.setText("Ans. "+Vaccine_OneDose_Delay_Reason);
                                   binding.tvVaccineSecondDueDate.setText("Ans. "+scndVaccineDueDate);
                                   binding.tvVaccineSecondDoseDate.setText("Ans. "+scndVaccineDate);
                                   binding.tvVaccineSecondDoseTaken.setText("Ans. "+Vaccinated_TwoDose_By);
                                   binding.tvVaccineSecondDoseDelayed.setText("Ans. "+Vaccine_TwoDose_Delay_Reason);

                                   if (TextUtils.isEmpty(Vaccine_OneDose_Type)){
                                       binding.llVaccineType.setVisibility(View.GONE);
                                   }

                                   if (TextUtils.isEmpty(Vaccinated_OneDose_By)){
                                       binding.llFirstDoseTaken.setVisibility(View.GONE);
                                   }

                                   if (TextUtils.isEmpty(firstVaccineDate)){
                                       binding.llFirstDoseDate.setVisibility(View.GONE);
                                   }

                                   if (TextUtils.isEmpty(Vaccine_OneDose_Delay_Reason)){
                                       binding.llFirstDoseDelayed.setVisibility(View.GONE);
                                   }

                                   if (TextUtils.isEmpty(scndVaccineDueDate)){
                                       binding.llSeocndDueDoseDate.setVisibility(View.GONE);
                                   }

                                   if (TextUtils.isEmpty(Vaccine_TwoDose_Delay_Reason)){
                                       binding.llSeocndDoseDelayed.setVisibility(View.GONE);
                                   }

                                   if (TextUtils.isEmpty(Vaccinated_TwoDose_By)){
                                       binding.llSecondDoseTaken.setVisibility(View.GONE);
                                   }

                                   if (TextUtils.isEmpty(scndVaccineDate)){
                                       binding.llSecondDoseDate.setVisibility(View.GONE);
                                   }





                                }






                            } else {

                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(VaccineReportActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(VaccineReportActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v==binding.imgBack){
            onBackPressed();
        }else if (v==binding.imgHome){
            Intent intent=new Intent(VaccineReportActivity.this,DashBoardActivity.class);
            startActivity(intent);
            finish();
        }

    }
}