package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.QueriesAdapter;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class QueriesActivity extends AppCompatActivity {
    RecyclerView rvQueries;
    ArrayList<QueriesModel> itemList = new ArrayList<>();
    LinearLayout llMain, llLoader, llAgain, llNoData;
    ProgressBar progressBar;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public static int mPageCount = 0;
    boolean mIsEndReached = false;
    private boolean loading = false;
    LinearLayoutManager layoutManager;
    QueriesAdapter qAdapter;
    PrefManager prefManager;
    LinearLayout llAdd;
    AlertDialog alertDialog;
    Spinner spIssue;
    ArrayList<SpinnerItemModule>modelIsuue=new ArrayList<>();
    ArrayList<String>issue=new ArrayList<>();
    String issueId="";
    ImageView imgBack,imgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
        mPageCount = 1;
        initialize();
        getItem();
        onClick();

    }

    private void initialize() {
        prefManager = new PrefManager(getApplicationContext());
        rvQueries = (RecyclerView) findViewById(R.id.rvQueries);
        layoutManager
                = new LinearLayoutManager(QueriesActivity.this, LinearLayoutManager.VERTICAL, false);
        rvQueries.setLayoutManager(layoutManager);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        llAgain = (LinearLayout) findViewById(R.id.llAgain);
        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        llNoData = (LinearLayout) findViewById(R.id.llNodata);
        progressBar = (ProgressBar) findViewById(R.id.WLpagination_loader);
        rvQueries.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = true;

                            progressBar.setVisibility(View.VISIBLE);
                            if (!mIsEndReached) {
                                mPageCount = mPageCount + 1;
                                getItem();
                            }

                        }
                    }
                }
            }
        });
        setAdapter();
        llAdd = (LinearLayout) findViewById(R.id.llAdd);
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgHome=(ImageView)findViewById(R.id.imgHome);

    }

    private void getItem() {
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        llAgain.setVisibility(View.GONE);
        String surl =  AppController.APIV2URL+"api/Feedback?ClientID=" + prefManager.getClintId() + "&FeedBackID=0&UserID=" + prefManager.getUserId() + "&Query=0&RepliedDetails=0&RepliedBy=0&ReplyStatus=4&IssueID=0&CurrentPage=" + mPageCount + "&Operation=1&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputQueries", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responsequeries", response);
                        loading = false;

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("responsequeries", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String SubmitedOn = obj.optString("SubmitedOn");
                                    String Query = obj.optString("Query");
                                    String IssueName = obj.optString("IssueName");
                                    String RepliedDetails = obj.optString("RepliedDetails");
                                    String ReplyStatus = obj.optString("ReplyStatus");
                                    QueriesModel qModel = new QueriesModel(SubmitedOn, IssueName, Query, RepliedDetails, ReplyStatus);
                                    itemList.add(qModel);


                                }

                                qAdapter.notifyDataSetChanged();
                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                llNoData.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);

                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

                            } else {
                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                llNoData.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(QueriesActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                llNoData.setVisibility(View.GONE);
                llAgain.setVisibility(View.VISIBLE);

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(QueriesActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(QueriesActivity.this);

        requestQueue.add(stringRequest);
    }

    private void setAdapter() {
        qAdapter = new QueriesAdapter(itemList);
        rvQueries.setAdapter(qAdapter);

    }


    private void onClick() {
        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIssue();
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(QueriesActivity.this, R.style.CustomDialogNew);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.dialog_feedback, null);
                dialogBuilder.setView(dialogView);
                spIssue = (Spinner) dialogView.findViewById(R.id.spIssue);
               /* SpinnerAdapter spinnerAdapter = new io.cordova.myapp00d753.adapter.SpinnerAdapter(QueriesActivity.this, spIssueList);
                spIssue.setAdapter(spinnerAdapter);*/

                ImageView imgCancel = (ImageView) dialogView.findViewById(R.id.imgCancel);
                imgCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                final EditText etFeedBack = (EditText) dialogView.findViewById(R.id.etFeedBAck);
                Button btnSubmit = (Button) dialogView.findViewById(R.id.btnSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (etFeedBack.getText().toString().length() > 0) {
                            String query=etFeedBack.getText().toString().replaceAll("\\s+","-");
                            if (!issueId.equals("")) {
                                postIsuue(query);
                            }else {
                                Toast.makeText(getApplicationContext(),"Please select Issue",Toast.LENGTH_LONG).show();
                            }


                        } else {
                            etFeedBack.setError("please enter");
                            etFeedBack.requestFocus();
                        }

                    }
                });

                spIssue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position>0){
                            issueId=modelIsuue.get(position).getItemId();
                            Log.d("issueId",issueId);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                alertDialog = dialogBuilder.create();
                alertDialog.setCancelable(true);
                Window window = alertDialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                alertDialog.show();
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
                Intent intent=new Intent(QueriesActivity.this,DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }


    private void getIssue(){
        String surl =  AppController.APIV2URL+"api/IssueList?AEMClientID=AEMCLI1010000480&SecurityCode=IFB";

        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressBar.dismiss();
                        issue.clear();
                        modelIsuue.clear();
                        issue.add("Please select");
                        modelIsuue.add(new SpinnerItemModule("0", "0"));

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String value = obj.optString("IssueName");
                                    String id = obj.optString("IssueID");
                                    issue.add(value);
                                    SpinnerItemModule spModel = new SpinnerItemModule(value, id);
                                    modelIsuue.add(spModel);

                                }




                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (QueriesActivity.this, android.R.layout.simple_spinner_item,
                                                issue); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spIssue.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(QueriesActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(QueriesActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(QueriesActivity.this);
        requestQueue.add(stringRequest);



    }


    private void postIsuue(String query){
        String surl =  AppController.APIV2URL+"api/post_Feedback?ClientID="+prefManager.getClintId()+"&FeedBackID=0&UserID="+prefManager.getUserId()+"&Query="+query+"&RepliedDetails=0&RepliedBy=0&ReplyStatus=4&IssueID="+issueId+"&CurrentPage=1&Operation=3&SecurityCode="+prefManager.getSecurityCode();
        Log.d("postissue",surl);

        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressBar.dismiss();
                        issue.clear();
                        modelIsuue.clear();
                        issue.add("Please select");
                        modelIsuue.add(new SpinnerItemModule("0", "0"));

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                Toast.makeText(getApplicationContext(),"Queries submitted successfully",Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                                mPageCount=1;
                                itemList.clear();
                                getItem();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(QueriesActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(getApplicationContext(), "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(QueriesActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(QueriesActivity.this);

        requestQueue.add(stringRequest);


    }





}
