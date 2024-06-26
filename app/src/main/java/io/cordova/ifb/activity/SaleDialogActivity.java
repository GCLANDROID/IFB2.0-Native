package io.cordova.ifb.activity;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.PrefManager;

public class SaleDialogActivity extends AppCompatActivity {
    TextView tvApproved,tvTarget,tvPending,tvSold,tvMonthlyTarget;
    PrefManager prefManager;
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sale_dialog);
        this.setFinishOnTouchOutside(false);
        initialize();
    }

    private void initialize(){
        prefManager=new PrefManager(SaleDialogActivity.this);
        tvMonthlyTarget=(TextView)findViewById(R.id.tvMonthlyTarget);
        tvMonthlyTarget.setText(prefManager.getMonthlyTarget());
        tvSold=(TextView)findViewById(R.id.tvSold);
        tvSold.setText(prefManager.getSold());
        tvPending=(TextView)findViewById(R.id.tvPending);
        tvPending.setText(prefManager.getPending());
        tvApproved=(TextView)findViewById(R.id.tvApproved);
        tvApproved.setText(prefManager.getApproved());
        btnExit=(Button)findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
