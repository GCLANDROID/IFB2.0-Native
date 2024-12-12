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
                imeiAlert();
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
                Intent intent = new Intent(getContext(), IncentiveManualActivity.class);
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

        getIMEINumber();
    }

    public void getIMEINumber() {

        String surl =  AppController.APIURL+"api/get_EmployeeMobileIMEI?Code=" +prefManager.getUserCode() + "&Operation=1&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputLogin", surl);
        final ProgressDialog progressBar = new ProgressDialog(getContext());
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loding.....");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        progressBar.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            JSONArray responseData=job1.optJSONArray("responseData");
                            JSONObject imeiOBJ=responseData.optJSONObject(0);
                            oldIMEI=imeiOBJ.optString("IMEI");

                            getIMEIRequestDetails();


                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                //  Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void getIMEIRequestDetails() {

        String surl =  AppController.APIURL+"api/get_EmployeeMobileIMEI?Code=" +prefManager.getUserCode() + "&Operation=2&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputLogin", surl);
        final ProgressDialog progressBar = new ProgressDialog(getContext());
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loding.....");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        progressBar.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus){
                                changeScreen=true;
                                JSONArray responseData=job1.optJSONArray("responseData");
                                for (int i=0;i<responseData.length();i++){
                                    JSONObject imeiOBJ=responseData.optJSONObject(i);
                                    String IMEI=imeiOBJ.optString("IMEI");
                                    String RequestedOn=imeiOBJ.optString("RequestedOn");
                                    String StatusName=imeiOBJ.optString("StatusName");
                                    IMEIReqModel imeiReqModel=new IMEIReqModel();
                                    imeiReqModel.setReqDetails(RequestedOn);
                                    imeiReqModel.setApprovalStatus(StatusName);
                                    imeiReqModel.setImeiNumber(IMEI);
                                    imeiList.add(imeiReqModel);

                                }

                            }else {
                                changeScreen=false;
                            }



                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                //  Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void imeiAlert(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_imei, null);
        dialogBuilder.setView(dialogView);
        TextView tvExistingNumber=(TextView)dialogView.findViewById(R.id.tvExistingNumber);
        tvExistingNumber.setText(oldIMEI);
        TextView tvCurrentNumber=(TextView)dialogView.findViewById(R.id.tvCurrentNumber);
        tvCurrentNumber.setText(androidID);
        ImageView imgCancel=(ImageView)dialogView.findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imeialert.dismiss();
            }
        });

        final EditText etReason=(EditText)dialogView.findViewById(R.id.etReason);
        Button btnSend=(Button)dialogView.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etReason.getText().toString().length()>0){
                    sendIMEIReq(etReason.getText().toString());
                    imeialert.dismiss();
                }else {
                    Toast.makeText(getContext(),"Please Enter Your Reason",Toast.LENGTH_LONG).show();
                }

            }
        });

        Button btnReqDetails=(Button)dialogView.findViewById(R.id.btnReqDetails);
        if (changeScreen){
            btnReqDetails.setVisibility(View.VISIBLE);
        }else {
            btnReqDetails.setVisibility(View.GONE);
        }

        btnReqDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imeiReqAlert();
                imeialert.dismiss();

            }
        });





        imeialert = dialogBuilder.create();
        imeialert.setCancelable(true);
        Window window = imeialert.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        imeialert.show();
    }

    private void imeiReqAlert(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_request_imei, null);
        dialogBuilder.setView(dialogView);

        ImageView imgCancel=(ImageView)dialogView.findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imeireqalert.dismiss();
            }
        });

        RecyclerView rvIMEI=(RecyclerView)dialogView.findViewById(R.id.rvIMEI);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvIMEI.setLayoutManager(layoutManager);

        ImeiReqAdapter imeiAdapter=new ImeiReqAdapter(imeiList,getContext());
        rvIMEI.setAdapter(imeiAdapter);








        imeireqalert = dialogBuilder.create();
        imeireqalert.setCancelable(true);
        Window window = imeireqalert.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        imeireqalert.show();
    }

    private void sendIMEIReq(String reason) {

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload( AppController.APIURL+"api/post_EmployeeMobileIMEIChnageRequest")
                .addMultipartParameter("Code", prefManager.getUserCode())
                .addMultipartParameter("IMEI", androidID)
                .addMultipartParameter("OLDIMEI", oldIMEI)
                .addMultipartParameter("UserID", prefManager.getUserId())
                .addMultipartParameter("Remarks", reason)
                .addMultipartParameter("SecurityCode", prefManager.getSecurityCode())
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        pd.show();

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        String responseText = job1.optString("responseText");
                        Log.d("responseText", responseText);
                        boolean responseStatus = job1.optBoolean("responseStatus");
                        if (responseStatus) {

                            pd.dismiss();
                            Toast.makeText(getContext(),responseText,Toast.LENGTH_LONG).show();
                        }else {
                            pd.dismiss();
                            Toast.makeText(getContext(),responseText,Toast.LENGTH_LONG).show();

                        }




                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
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