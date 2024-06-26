package io.cordova.ifb.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.DocumentAdapter;
import io.cordova.ifb.module.DocumentManageModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.NetworkConnectionCheck;
import io.cordova.ifb.utility.PrefManager;
import io.cordova.ifb.utility.RecyclerItemClickListener;


public class DocumentReportActivity extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener {
    RecyclerView rvDocument;
    ArrayList<DocumentManageModule> documentList = new ArrayList<>();
    DocumentAdapter documentAdapter;
    LinearLayout llLoader, llMain;
    ImageView imgBack, imgHome;
    PrefManager pref;
    String dLink;
    NetworkConnectionCheck connectionCheck;
    LinearLayout llNoadata;
    LinearLayout llAgain;
    ImageView imgAgain;
    String aempEmployeeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_report);
        initialize();
        if (connectionCheck.isNetworkAvailable()) {
            getDocList();
        } else {
            connectionCheck.getNetworkActiveAlert().show();
        }
        onClick();


    }

    private void initialize() {
        connectionCheck = new NetworkConnectionCheck(DocumentReportActivity.this);
        pref = new PrefManager(this);
        rvDocument = (RecyclerView) findViewById(R.id.rvDocument);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(DocumentReportActivity.this, LinearLayoutManager.VERTICAL, false);
        rvDocument.setLayoutManager(layoutManager);
        rvDocument.addOnItemTouchListener(new RecyclerItemClickListener(DocumentReportActivity.this, DocumentReportActivity.this));
        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        llNoadata = (LinearLayout) findViewById(R.id.llNodata);
        llAgain=(LinearLayout)findViewById(R.id.llAgain);
        imgAgain=(ImageView)findViewById(R.id.imgAgain);


    }

    private void onClick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(DocumentReportActivity.this, DashBoardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

               // finish();
            }
        });

        imgAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocList();
            }
        });
    }

    private void getDocList() {
        String surl = AppController.APIURL+"api/gcl_EmployeeDigitalDocumentReport?AEMEmployeeID="+pref.getUserId()+"&SecurityCode="+ pref.getSecurityCode();
        Log.d("manageinput",surl);
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llNoadata.setVisibility(View.GONE);
        llAgain.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);


                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("responsedocumentreport", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                            //    Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();
                                 JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String DocumentName = obj.optString("DocumentName");
                                    String DocumentType = obj.optString("DocumentType");
                                    String AEMStatusName = obj.optString("AEMStatusName");
                                    String CreatedOn = obj.optString("CreatedOn");
                                    String ApprovalRemarks = obj.optString("ApprovalRemarks");
                                    String DocLink = obj.optString("DocLink");
                                    DocumentManageModule dmodule = new DocumentManageModule(DocumentName, DocumentType, ApprovalRemarks, CreatedOn, AEMStatusName, DocLink);
                                    documentList.add(dmodule);


                                }


                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                llNoadata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);
                                documentAdapter = new DocumentAdapter(documentList);
                                rvDocument.setAdapter(documentAdapter);


                            } else {
                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                llNoadata.setVisibility(View.VISIBLE);
                                llAgain.setVisibility(View.GONE);

                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();

                           // Toast.makeText(DocumentReportActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                llNoadata.setVisibility(View.GONE);
                llAgain.setVisibility(View.VISIBLE);

              //  Toast.makeText(DocumentReportActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();

                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DocumentReportActivity.this);
        requestQueue.add(stringRequest);



    }

    @Override
    public void onItemClick(View childView, int position) {
        dLink = documentList.get(position).getDocLink();
        operBrowser();

    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }

    private void operBrowser() {
        Uri uri = Uri.parse(dLink); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (!dLink.equals("") && dLink != null) {
            startActivity(intent);
        } else {

        }
    }
}
