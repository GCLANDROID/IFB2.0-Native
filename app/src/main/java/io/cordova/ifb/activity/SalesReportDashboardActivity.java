package io.cordova.ifb.activity;

import android.content.Intent;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import io.cordova.ifb.R;
import io.cordova.ifb.databinding.ActivitySalesReportDashboardBinding;

public class SalesReportDashboardActivity extends AppCompatActivity {

    ActivitySalesReportDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sales_report_dashboard);
        initView();
    }

    private void initView(){
        binding.llConsolidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesReportDashboardActivity.this,ConsolidateSalesReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesReportDashboardActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.llDetailsReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesReportDashboardActivity.this,RefNoReportActivity.class);
                intent.putExtra("subOperation","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}