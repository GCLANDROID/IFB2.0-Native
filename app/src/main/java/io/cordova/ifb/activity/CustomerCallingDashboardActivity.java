package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.NetworkConnectionCheck;

public class CustomerCallingDashboardActivity extends AppCompatActivity {
    LinearLayout llManage,llReport;
    ImageView imgBack,imgHome;
    NetworkConnectionCheck connectionCheck;
    ProgressDialog progressDialog;
    AlertDialog alerDialog1;

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
        connectionCheck=new NetworkConnectionCheck(CustomerCallingDashboardActivity.this);
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
                    alert();

                }else {
                    connectionCheck.getSettingsAlert().show();
                }
            }
        });

        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CustomerCallingConsolidateReportActivity.class);
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

    private void alert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CustomerCallingDashboardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_callupdation, null);
        dialogBuilder.setView(dialogView);


        Button btnCRM = (Button) dialogView.findViewById(R.id.btnCRM);
        btnCRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                Intent intent = new Intent(getApplicationContext(), CustomerDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Button btnManual = (Button) dialogView.findViewById(R.id.btnManual);
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                Intent intent = new Intent(getApplicationContext(), CustomerCallingManageManualActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(false);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }
}
