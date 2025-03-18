package io.cordova.ifb.activity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.NetworkConnectionCheck;

public class CVisitDashboardActivity extends AppCompatActivity {
    LinearLayout llManage,llReport;
    ImageView imgBack,imgHome;
    NetworkConnectionCheck connectionCheck;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvisit_dashboard);
        initView();
        onClick();
    }
    private void initView(){
        llManage=(LinearLayout)findViewById(R.id.llManage);
        llReport=(LinearLayout)findViewById(R.id.llReport);
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);
        connectionCheck=new NetworkConnectionCheck(CVisitDashboardActivity.this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading......");
        progressDialog.setCancelable(false);
    }

    private void onClick(){
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        llManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionCheck.isGPSEnabled()) {
                    progressDialog.show();
                    Intent intent = new Intent(getApplicationContext(), CVisitManageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    connectionCheck.getSettingsAlert().show();
                }
            }
        });

        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CVisitReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        progressDialog.dismiss();
    }
}
