package io.cordova.ifb.activity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.cordova.ifb.R;

public class SalesManageDashboardActivity extends AppCompatActivity {
    LinearLayout llManage,llDelivery,llReport,llSerialNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_manage_dashboard);
        initView();
    }
    private void initView(){

        String data = "Iam-20yrs-";
        Pattern p = Pattern.compile( "[0-9]{2}" );
        Matcher m = p.matcher( data );
        String s = "";
        if ( m.find() ) {
            s = m.group(0);
        }

        Log.d("match",s);
        llManage=(LinearLayout)findViewById(R.id.llManage);
        llDelivery=(LinearLayout)findViewById(R.id.llDelivery);
        llReport=(LinearLayout)findViewById(R.id.llReport);
        llSerialNo=(LinearLayout) findViewById(R.id.llSerialNo);

        llManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesManageDashboardActivity.this,SalesManage2Activity.class);
                startActivity(intent);
            }
        });

        llDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesManageDashboardActivity.this,DeliveryDetailsActivity.class);
                startActivity(intent);
            }
        });

        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesManageDashboardActivity.this,ConsolidateSalesReportActivity.class);
                startActivity(intent);
            }
        });

        llSerialNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalesManageDashboardActivity.this,SerialInvoiceActivity.class);
                startActivity(intent);
            }
        });
    }
}