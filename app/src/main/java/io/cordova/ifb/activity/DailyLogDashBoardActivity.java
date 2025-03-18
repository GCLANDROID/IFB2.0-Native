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


public class DailyLogDashBoardActivity extends AppCompatActivity {
    LinearLayout llManage, llReport, llLog;
    LinearLayout llManageD, llReportD, llLogD;
    LinearLayout llManageD1, llReportD1, llLogD1;
    ImageView imgBack, imgHome;
    NetworkConnectionCheck connectionCheck;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_dash_board);
        initialize();
        onClick();
    }

    private void initialize() {

        connectionCheck = new NetworkConnectionCheck(DailyLogDashBoardActivity.this);
        llManage = (LinearLayout) findViewById(R.id.llManage);
        llReport = (LinearLayout) findViewById(R.id.llReport);



        llLog = (LinearLayout) findViewById(R.id.llLogBook);

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    private void onClick() {
        llManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 progressDialog.show();


                Intent intent = new Intent(DailyLogDashBoardActivity.this, VisitLocationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });


        llLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(DailyLogDashBoardActivity.this, NumberTourActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

            }
        });


        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(DailyLogDashBoardActivity.this, AttemdanceReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyLogDashBoardActivity.this, NewDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.dismiss();
    }
}
