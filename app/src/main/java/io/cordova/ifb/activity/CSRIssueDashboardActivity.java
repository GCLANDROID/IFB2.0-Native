package io.cordova.ifb.activity;

import android.content.Intent;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import io.cordova.ifb.R;
import io.cordova.ifb.databinding.ActivityCSRIssueDashboardBinding;

public class CSRIssueDashboardActivity extends AppCompatActivity {

    ActivityCSRIssueDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_c_s_r_issue_dashboard);
        onClick();
    }
    private void onClick(){
        binding.llManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CSRIssueDashboardActivity.this,CSRIssueDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        binding.llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CSRIssueDashboardActivity.this,CSRIssueReportActivity.class);
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
                Intent intent=new Intent(CSRIssueDashboardActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}