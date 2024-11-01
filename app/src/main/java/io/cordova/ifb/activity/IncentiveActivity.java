package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.InsuranceTipsAdapter;
import io.cordova.ifb.adapter.PerformanceAchvAdapter;
import io.cordova.ifb.databinding.ActivityIncentiveBinding;

public class IncentiveActivity extends AppCompatActivity {
    ActivityIncentiveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_incentive);
        initView();
    }

    private void initView(){

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(IncentiveActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvItem.setLayoutManager(layoutManager);

        InsuranceTipsAdapter adapter=new InsuranceTipsAdapter();
        binding.rvItem.setAdapter(adapter);


        LinearLayoutManager achvlayoutManager
                = new LinearLayoutManager(IncentiveActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvAchv.setLayoutManager(achvlayoutManager);


        PerformanceAchvAdapter achvAdapter=new PerformanceAchvAdapter(IncentiveActivity.this);
        binding.rvAchv.setAdapter(achvAdapter);

    }
}