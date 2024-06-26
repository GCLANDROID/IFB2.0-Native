package io.cordova.ifb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import io.cordova.ifb.R;
import io.cordova.ifb.databinding.ActivityVaccineDashboardBinding;

public class VaccineDashboardActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityVaccineDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_vaccine_dashboard);
        binding.llManage.setOnClickListener(this);
        binding.llReport.setOnClickListener(this);
        binding.imgBack.setOnClickListener(this);
        binding.imgHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==binding.llManage){
            Intent intent=new Intent(VaccineDashboardActivity.this,VaccineStausActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }else if (v==binding.llReport){
            Intent intent=new Intent(VaccineDashboardActivity.this,VaccineReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (v==binding.imgBack){

            onBackPressed();

        }else if (v==binding.imgHome){
            Intent intent=new Intent(VaccineDashboardActivity.this,DashBoardActivity.class);
            startActivity(intent);
            finish();
        }
    }
}