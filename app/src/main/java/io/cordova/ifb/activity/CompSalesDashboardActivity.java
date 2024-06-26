package io.cordova.ifb.activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import io.cordova.ifb.R;

public class CompSalesDashboardActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout llManage,llReport;
    boolean flag;
    ImageView imgBack,imgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_sales_dashboard);
        initView();
    }

    private void initView(){
        llManage=(LinearLayout) findViewById(R.id.llManage);
        llReport=(LinearLayout) findViewById(R.id.llReport);

        flag=getIntent().getBooleanExtra("flag",true);
        imgHome=(ImageView)findViewById(R.id.imgHome);
        imgBack=(ImageView)findViewById(R.id.imgBack);


        llManage.setOnClickListener(this);
        llReport.setOnClickListener(this);

        imgHome.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==llManage){
            if (flag) {
                Intent intent = new Intent(CompSalesDashboardActivity.this, CompetitorSaleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else {
                Intent intent = new Intent(CompSalesDashboardActivity.this, RefInfoManageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("manage",1);
                startActivity(intent);
            }
        }else if (view==llReport){
            if (flag) {
                Intent intent = new Intent(CompSalesDashboardActivity.this, CompSaleReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else {
                Intent intent = new Intent(CompSalesDashboardActivity.this, RefInfoManageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("manage",0);
                startActivity(intent);
            }
        }else if (view==imgBack){
            onBackPressed();
        }else if (view==imgHome){
            Intent intent = new Intent(CompSalesDashboardActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        }
    }
}