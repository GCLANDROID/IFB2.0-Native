package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.IncentiveCalculationAdapter;
import io.cordova.ifb.databinding.ActivityIncentiveCalculationBinding;
import io.cordova.ifb.module.IncentiveCalculationModule;

public class IncentiveCalculationActivity extends AppCompatActivity {
    ActivityIncentiveCalculationBinding binding;
    ArrayList<IncentiveCalculationModule>itemList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_incentive_calculation);
        initView();
    }

    private void initView(){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(IncentiveCalculationActivity.this, LinearLayoutManager.VERTICAL, false);
       binding. rvItem.setLayoutManager(layoutManager);
       setAdapter();
    }

    private void setAdapter(){
        IncentiveCalculationAdapter adapter=new IncentiveCalculationAdapter(itemList,IncentiveCalculationActivity.this);
        binding.rvItem.setAdapter(adapter);
    }
}