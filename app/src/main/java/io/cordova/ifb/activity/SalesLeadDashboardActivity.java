package io.cordova.ifb.activity;

import android.content.Intent;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import io.cordova.ifb.R;
import io.cordova.ifb.databinding.ActivitySalesLeadDashboardBinding;

public class SalesLeadDashboardActivity extends AppCompatActivity implements View.OnClickListener {
     ActivitySalesLeadDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sales_lead_dashboard);
        initView();
    }

    private void initView(){
        binding.llLeadReport.setOnClickListener(this);
        binding.llManage.setOnClickListener(this);
        binding.imgBack.setOnClickListener(this);
        binding.imgHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==binding.llManage){
            Intent intent=new Intent(SalesLeadDashboardActivity.this,SalesLeadActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (v==binding.llLeadReport){
            Intent intent=new Intent(SalesLeadDashboardActivity.this,SalesLeadReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (v==binding.imgBack){
          onBackPressed();
        }else if (v==binding.imgHome){
            finish();
            Intent intent=new Intent(SalesLeadDashboardActivity.this,DashBoardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

        }

    }
}