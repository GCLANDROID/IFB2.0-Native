package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.IncentiveCategoryBlockOneAdapter;
import io.cordova.ifb.adapter.IncentiveCategoryBlockTwoAdapter;
import io.cordova.ifb.databinding.ActivityIncentiveCalculationDetailsBinding;
import io.cordova.ifb.module.IncentiveCategoryDetailsModel;

public class IncentiveCalculationDetailsActivity extends AppCompatActivity {

    ActivityIncentiveCalculationDetailsBinding binding;
    ArrayList<IncentiveCategoryDetailsModel>blockOneList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil. setContentView(this,R.layout.activity_incentive_calculation_details);
       initView();
    }

    private void initView(){
        LinearLayoutManager onelayoutManager
                = new LinearLayoutManager(IncentiveCalculationDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        binding. rvBlockOne.setLayoutManager(onelayoutManager);

        LinearLayoutManager twolayoutManager
                = new LinearLayoutManager(IncentiveCalculationDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        binding. rvBlockTwo.setLayoutManager(twolayoutManager);
        blockOneAdapter();
        blockTwoAdapter();

    }

    private void blockOneAdapter(){
        IncentiveCategoryBlockOneAdapter oneAdapter=new IncentiveCategoryBlockOneAdapter(blockOneList);
        binding.rvBlockOne.setAdapter(oneAdapter);
    }

    private void blockTwoAdapter(){
        IncentiveCategoryBlockTwoAdapter oneAdapter=new IncentiveCategoryBlockTwoAdapter(blockOneList);
        binding.rvBlockTwo.setAdapter(oneAdapter);
    }
}