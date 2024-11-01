package io.cordova.ifb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.view.Gravity;
import android.view.View;

import java.util.Calendar;

import io.cordova.ifb.R;
import io.cordova.ifb.databinding.ActivityNewDashboardBinding;
import io.cordova.ifb.fragment.AttendanceFragment;
import io.cordova.ifb.fragment.HomeFragment;
import io.cordova.ifb.fragment.MoreFragment;
import io.cordova.ifb.fragment.SalesManagementFragment;
import io.cordova.ifb.utility.PrefManager;

public class NewDashboardActivity extends AppCompatActivity {
    ActivityNewDashboardBinding binding;
    PrefManager preference;
    boolean mslideState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_new_dashboard);
        initView();
        loadHomeFragment();
    }

    private void initView(){
        preference=new PrefManager(NewDashboardActivity.this);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            binding.tvCSRName.setText("Good Morning! "+preference.getEmpName());
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            binding.tvCSRName.setText("Good Afternoon! "+preference.getEmpName());
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            binding.tvCSRName.setText("Good Evening! "+preference.getEmpName());
        } else if (timeOfDay >= 21 && timeOfDay < 24) {

            binding.tvCSRName.setText("Good Evening! "+preference.getEmpName());
        }

        binding.tvStoreName.setText("("+preference.getCounter()+")");
        binding.lnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAttendanceFragment();
            }
        });
        binding.lnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHomeFragment();
            }
        });
        binding.lnSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSalesFragment();
            }
        });
        binding.lnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMoreFragment();
            }
        });
        binding.imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewDashboardActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.fbTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewDashboardActivity.this,IncentiveActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });




    }

    public void visibility(){
        binding.lnAttendance.setVisibility(View.VISIBLE);
        binding.lnSales.setVisibility(View.VISIBLE);
        binding.lnMore.setVisibility(View.VISIBLE);
        binding.lnHome.setVisibility(View.VISIBLE);
    }


    public void loadHomeFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        HomeFragment pfragment=new HomeFragment();
        transaction.replace(R.id.frameLayout, pfragment);
        transaction.commit();


        //home
        binding.imgHomeSelected.setVisibility(View.VISIBLE);
        binding.imgHomeUnSelected.setVisibility(View.GONE);
        binding.tvHome.setTextColor(Color.parseColor("#FFFFFF"));



        //attendance

        binding.imgAttendanceUnSelected.setVisibility(View.VISIBLE);
        binding.imgAttendanceSelected.setVisibility(View.GONE);
        binding.tvAttendance.setTextColor(Color.parseColor("#C1BDBC"));

        //sales
        binding.imgSaleUnSelected.setVisibility(View.VISIBLE);
        binding.imgSaleSelected.setVisibility(View.GONE);
        binding.tvSales.setTextColor(Color.parseColor("#C1BDBC"));

        //more
        binding.imgMoreUnSelected.setVisibility(View.VISIBLE);
        binding.imgMoreSelected.setVisibility(View.GONE);
        binding.tvMore.setTextColor(Color.parseColor("#C1BDBC"));





    }


    public void loadAttendanceFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AttendanceFragment pfragment=new AttendanceFragment();
        transaction.replace(R.id.frameLayout, pfragment);
        transaction.commit();


        //home
        binding.imgHomeSelected.setVisibility(View.GONE);
        binding.imgHomeUnSelected.setVisibility(View.VISIBLE);
        binding.tvHome.setTextColor(Color.parseColor("#C1BDBC"));



        //attendance

        binding.imgAttendanceUnSelected.setVisibility(View.GONE);
        binding.imgAttendanceSelected.setVisibility(View.VISIBLE);
        binding.tvAttendance.setTextColor(Color.parseColor("#FFFFFF"));

        //sales
        binding.imgSaleUnSelected.setVisibility(View.VISIBLE);
        binding.imgSaleSelected.setVisibility(View.GONE);
        binding.tvSales.setTextColor(Color.parseColor("#C1BDBC"));

        //more
        binding.imgMoreUnSelected.setVisibility(View.VISIBLE);
        binding.imgMoreSelected.setVisibility(View.GONE);
        binding.tvMore.setTextColor(Color.parseColor("#C1BDBC"));





    }

    public void loadSalesFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SalesManagementFragment pfragment=new SalesManagementFragment();
        transaction.replace(R.id.frameLayout, pfragment);
        transaction.commit();


        //home
        binding.imgHomeSelected.setVisibility(View.GONE);
        binding.imgHomeUnSelected.setVisibility(View.VISIBLE);
        binding.tvHome.setTextColor(Color.parseColor("#C1BDBC"));



        //attendance

        binding.imgAttendanceUnSelected.setVisibility(View.VISIBLE);
        binding.imgAttendanceSelected.setVisibility(View.GONE);
        binding.tvAttendance.setTextColor(Color.parseColor("#C1BDBC"));

        //sales
        binding.imgSaleUnSelected.setVisibility(View.GONE);
        binding.imgSaleSelected.setVisibility(View.VISIBLE);
        binding.tvSales.setTextColor(Color.parseColor("#FFFFFF"));

        //more
        binding.imgMoreUnSelected.setVisibility(View.VISIBLE);
        binding.imgMoreSelected.setVisibility(View.GONE);
        binding.tvMore.setTextColor(Color.parseColor("#C1BDBC"));





    }
    public void loadMoreFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MoreFragment pfragment=new MoreFragment();
        transaction.replace(R.id.frameLayout, pfragment);
        transaction.commit();

        //home
        binding.imgHomeSelected.setVisibility(View.GONE);
        binding.imgHomeUnSelected.setVisibility(View.VISIBLE);
        binding.tvHome.setTextColor(Color.parseColor("#C1BDBC"));



        //attendance

        binding.imgAttendanceUnSelected.setVisibility(View.VISIBLE);
        binding.imgAttendanceSelected.setVisibility(View.GONE);
        binding.tvAttendance.setTextColor(Color.parseColor("#C1BDBC"));

        //sales
        binding.imgSaleUnSelected.setVisibility(View.VISIBLE);
        binding.imgSaleSelected.setVisibility(View.GONE);
        binding.tvSales.setTextColor(Color.parseColor("#C1BDBC"));

        //more
        binding.imgMoreUnSelected.setVisibility(View.GONE);
        binding.imgMoreSelected.setVisibility(View.VISIBLE);
        binding.tvMore.setTextColor(Color.parseColor("#FFFFFF"));





    }


}