package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import im.delight.android.webview.AdvancedWebView;
import io.cordova.ifb.R;
import io.cordova.ifb.databinding.ActivitySurveyBinding;
import io.cordova.ifb.module.ReportModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class SurveyActivity extends AppCompatActivity implements AdvancedWebView.Listener {
    ActivitySurveyBinding binding;
    PrefManager prefManager;
    String imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_survey);
        initView();
    }

    private void initView(){
        prefManager=new PrefManager(SurveyActivity.this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        imageurl=prefManager.getCSRSurveyURL();

        binding.wbUrl.setListener(this, this);
        binding.wbUrl.loadUrl(imageurl);
        binding.wbUrl.getSettings().setUseWideViewPort(false);
        binding.wbUrl.getSettings().setDisplayZoomControls(false);


        binding.wbUrl.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();

            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                //Your code to do
                view.loadUrl(imageurl);
                progressDialog.dismiss();
            }
        });

        binding.lnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surveyCheck();
            }
        });


    }


    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        binding.wbUrl.onResume();

        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        binding.wbUrl.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        binding.wbUrl.onDestroy();
        // ...
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        binding.wbUrl.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (! binding.wbUrl.onBackPressed()) { return; }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) { }

    @Override
    public void onPageFinished(String url) { }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) { }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) { }

    @Override
    public void onExternalPageRequest(String url) { }


    private void surveyCheck() {
       ProgressDialog pd=new ProgressDialog(SurveyActivity.this);
       pd.setCancelable(false);
       pd.setMessage("Loading...");
       pd.show();
        String surl =  AppController.APIURL+"api/EmployeeSurveyFeedbackCheck?AEMEmployeeID="+prefManager.getUserId()+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseAttendance", response);
                        pd.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SurveyActivity.this);
                                alertDialogBuilder.setMessage(responseText);
                                alertDialogBuilder.setCancelable(false);

                                alertDialogBuilder.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                arg0.dismiss();
                                                Intent intent=new Intent(SurveyActivity.this,NewDashboardActivity.class);
                                                startActivity(intent);
                                                finish();
                                                prefManager.saveIsFillCSRSurvey("0");

                                            }
                                        });
                                alertDialogBuilder.show();

                            } else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SurveyActivity.this);
                                alertDialogBuilder.setMessage(responseText);
                                alertDialogBuilder.setCancelable(false);

                                alertDialogBuilder.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                arg0.dismiss();

                                            }
                                        });
                                alertDialogBuilder.show();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            pd.dismiss();
                            Toast.makeText(SurveyActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SurveyActivity.this);
        requestQueue.add(stringRequest);
    }
}