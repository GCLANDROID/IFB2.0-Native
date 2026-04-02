package io.cordova.ifb.activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import io.cordova.ifb.R;

public class DisplayMatrixDashBoardActivity extends AppCompatActivity {
    ImageView imgBack,imgHome;
    LinearLayout llManage,llReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_matrix_dash_board);
        initView();
        onClick();
    }
    private void initView(){
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);
        llManage=(LinearLayout)findViewById(R.id.llManage);
        llReport=(LinearLayout)findViewById(R.id.llReport);
    }

    private void onClick(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DisplayMatrixDashBoardActivity.this,NewDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        llManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(DisplayMatrixDashBoardActivity.this,DisplayMatrixDynamicActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/


                Intent intent=new Intent(DisplayMatrixDashBoardActivity.this,NewCompetitorDisplayMatrixActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DisplayMatrixDashBoardActivity.this,DisplayMatrixReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
}
