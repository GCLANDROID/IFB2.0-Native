package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.PrefManager;

public class INDDashbaordActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout llAttendance,llHelpDesk,llLeave;
    PrefManager prefManager;
    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inddashbaord);
        initView();
    }

    private void initView(){

        prefManager=new PrefManager(INDDashbaordActivity.this);

        tvUserName=(TextView)findViewById(R.id.tvUserName);
        tvUserName.setText(prefManager.getEmpName());
        llAttendance=(LinearLayout) findViewById(R.id.llAttendance);
        llHelpDesk=(LinearLayout) findViewById(R.id.llHelpDesk);
        llLeave=(LinearLayout) findViewById(R.id.llLeave);

        llAttendance.setOnClickListener(this);
        llHelpDesk.setOnClickListener(this);
        llLeave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==llAttendance){
            Intent intent = new Intent(INDDashbaordActivity.this, AttendanceManageActivity.class);
            startActivity(intent);
            finish();
        }else if (view==llHelpDesk){
            Uri uri = Uri.parse(prefManager.getHRDeskURL()); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if (view==llLeave){
            Uri uri = Uri.parse(prefManager.getLeaveURL()); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }
}