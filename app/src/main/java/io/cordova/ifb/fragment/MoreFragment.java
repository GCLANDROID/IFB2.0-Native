package io.cordova.ifb.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.CSRIssueDashboardActivity;
import io.cordova.ifb.activity.DashBoardActivity;
import io.cordova.ifb.activity.DocDashBaordActivity;
import io.cordova.ifb.activity.ECatelogActivity;
import io.cordova.ifb.activity.ELearningActivity;
import io.cordova.ifb.activity.FeedBackRatingActivity;
import io.cordova.ifb.activity.IQueriesDashboardActivity;
import io.cordova.ifb.activity.IncentiveManualActivity;
import io.cordova.ifb.activity.IncentiveSubActivity;
import io.cordova.ifb.activity.LoginActivity;
import io.cordova.ifb.activity.QAReportActivity;
import io.cordova.ifb.activity.QueriesActivity;
import io.cordova.ifb.activity.ReferEarnActivity;
import io.cordova.ifb.adapter.ImeiReqAdapter;
import io.cordova.ifb.databinding.FragmentHomeBinding;
import io.cordova.ifb.databinding.FragmentMoreBinding;
import io.cordova.ifb.module.IMEIReqModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class MoreFragment extends Fragment {
    View view;
    FragmentMoreBinding binding;
    PrefManager prefManager;
    String android_id,androidID,oldIMEI;
    boolean changeScreen;
    ArrayList<IMEIReqModel> imeiList=new ArrayList<>();
    AlertDialog imeialert,imeireqalert;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_more, container, false);
        view = binding.getRoot();
        initView();
        return view;
    }

    private void initView(){
        prefManager=new PrefManager(getContext());
        if (prefManager.getSecurityCode().equalsIgnoreCase("GCL")){
            binding.llFeedback.setVisibility(View.VISIBLE);
        }else {
            binding.llFeedback.setVisibility(View.GONE);
        }
        binding.llChangeIMEI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.llReferEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ReferEarnActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        binding.llFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), FeedBackRatingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.llQA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CSRIssueDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        binding.llHelpDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelpDeskBrowser();
            }
        });


        binding.llQueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QueriesActivity.class);
                startActivity(intent);
            }
        });

        binding.llELearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ELearningActivity.class);
                startActivity(intent);
            }
        });

        binding.llIncentive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), IncentiveSubActivity.class);
                startActivity(intent);
            }
        });
        binding.llDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DocDashBaordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.llIQueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), IQueriesDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.llECatelougge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ECatelogActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }

    private void openHelpDeskBrowser() {
        Uri uri = Uri.parse(prefManager.getHRDeskURL()); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (prefManager.getHRDeskURL().equals("")) {

        } else {
            startActivity(intent);
        }
    }
}