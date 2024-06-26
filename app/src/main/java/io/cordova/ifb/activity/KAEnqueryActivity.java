package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.cordova.ifb.R;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class KAEnqueryActivity extends AppCompatActivity {
    Spinner spStoreManagement, spStock, spIFBDisplay, spCompetitor, spTeamMeet, spTraining, spCollaborate;
    MultiSpinnerSearch spCategory, spTraningCatory;
    EditText etOthers, etCompetitor, etCollaborate, etSuggestion;
    LinearLayout llCategory, llCometitor, llTraningCategory;

    Button btnSubmit;

    LinearLayout llLoader;
    ScrollView scMain;
    PrefManager prefManager;

    AlertDialog alerDialog1;
    ArrayList<SpinnerItemModule> ItemList = new ArrayList<>();
    ArrayList<SpinnerItemModule> ItemList1 = new ArrayList<>();

    ArrayList<String> yesnoList = new ArrayList<>();
    ArrayList<String> displayList = new ArrayList<>();


    ArrayList<KeyPairBoolData> bCompanyList = new ArrayList<>();
    ArrayList<KeyPairBoolData> tCompanyList = new ArrayList<>();
    ArrayList<String> compList = new ArrayList<>();
    ArrayList<String> tCompList = new ArrayList<>();
    ArrayList<String> teamMeetList = new ArrayList<>();
    String version;
    AlertDialog al1;
    String compId="";
    String traningCompId="";
    String stockIssue = "";
    String competitor = "";
    String storeMangement = "";
    String ifbDisplay = "";
    String teamMeet = "";
    String collaboration = "";
    String traning = "";
    String IFBMandatory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_query);
        initView();
        checkBersion();
        onclick();
    }

    private void initView() {
        prefManager = new PrefManager(KAEnqueryActivity.this);
        spStoreManagement = (Spinner) findViewById(R.id.spStoreManagement);
        spStock = (Spinner) findViewById(R.id.spStock);
        spIFBDisplay = (Spinner) findViewById(R.id.spIFBDisplay);
        spCompetitor = (Spinner) findViewById(R.id.spCompetitor);
        spTeamMeet = (Spinner) findViewById(R.id.spTeamMeet);
        spTraining = (Spinner) findViewById(R.id.spTraining);
        spCollaborate = (Spinner) findViewById(R.id.spCollaborate);

        spCategory = (MultiSpinnerSearch) findViewById(R.id.spCategory);
        spTraningCatory = (MultiSpinnerSearch) findViewById(R.id.spTraningCatory);

        etOthers = (EditText) findViewById(R.id.etOthers);
        etCompetitor = (EditText) findViewById(R.id.etCompetitor);
        etCollaborate = (EditText) findViewById(R.id.etCollaborate);
        etSuggestion = (EditText) findViewById(R.id.etSuggestion);

        llCategory = (LinearLayout) findViewById(R.id.llCategory);
        llCometitor = (LinearLayout) findViewById(R.id.llCometitor);
        llTraningCategory = (LinearLayout) findViewById(R.id.llTraningCategory);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        yesnoList.add("Please select");
        yesnoList.add("YES");
        yesnoList.add("NO");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (KAEnqueryActivity.this, android.R.layout.simple_spinner_item,
                        yesnoList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStoreManagement.setAdapter(spinnerArrayAdapter);

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>
                (KAEnqueryActivity.this, android.R.layout.simple_spinner_item,
                        yesnoList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStock.setAdapter(spinnerArrayAdapter1);

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>
                (KAEnqueryActivity.this, android.R.layout.simple_spinner_item,
                        yesnoList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCompetitor.setAdapter(spinnerArrayAdapter2);

        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>
                (KAEnqueryActivity.this, android.R.layout.simple_spinner_item,
                        yesnoList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTraining.setAdapter(spinnerArrayAdapter3);

        ArrayAdapter<String> spinnerArrayAdapter4 = new ArrayAdapter<String>
                (KAEnqueryActivity.this, android.R.layout.simple_spinner_item,
                        yesnoList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCollaborate.setAdapter(spinnerArrayAdapter4);


        displayList.add("Please Select");
        displayList.add("Good");
        displayList.add("Average");
        displayList.add("Bad");

        ArrayAdapter<String> spinnerArrayAdapter5 = new ArrayAdapter<String>
                (KAEnqueryActivity.this, android.R.layout.simple_spinner_item,
                        displayList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIFBDisplay.setAdapter(spinnerArrayAdapter5);

        teamMeetList.add("Please Select");
        teamMeetList.add("Not Met");
        teamMeetList.add("1 Time");
        teamMeetList.add("2 Time");
        teamMeetList.add("3 Time and More");

        ArrayAdapter<String> spinnerArrayAdapter6 = new ArrayAdapter<String>
                (KAEnqueryActivity.this, android.R.layout.simple_spinner_item,
                        teamMeetList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTeamMeet.setAdapter(spinnerArrayAdapter6);


        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        scMain = (ScrollView) findViewById(R.id.scMain);


        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;

            Log.d("sddk", version);
            Log.d("sdkl", String.valueOf(version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }


    private void checkBersion() {
        String surl =  AppController.APIURL+"api/ApkVersionChecking";
        llLoader.setVisibility(View.VISIBLE);
        scMain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLeave", response);


                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            boolean responseStatus=job1.optBoolean("responseStatus");
                            if (responseStatus){
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String IFBVersion = obj.optString("IFBVersion");
                                    IFBMandatory = obj.optString("IFBMandatory");
                                    if (IFBVersion.equals(version)) {
                                        llLoader.setVisibility(View.GONE);
                                        scMain.setVisibility(View.GONE);
                                        Intent intent = new Intent(KAEnqueryActivity.this, NotificationActivity.class);
                                        startActivity(intent);


                                    } else {
                                        llLoader.setVisibility(View.GONE);
                                        scMain.setVisibility(View.GONE);
                                        if (IFBMandatory.equals("Y")) {
                                            upDateAlert(IFBVersion);
                                        }else {
                                            Intent intent = new Intent(KAEnqueryActivity.this, NotificationActivity.class);
                                            startActivity(intent);
                                        }
                                    }


                                }
                            }



                            // boolean _status = job1.getBoolean("status")

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(KAEnqueryActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(LoginActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();

                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(KAEnqueryActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setSubmitChecking() {
        Log.d("hitr", "1");

        String surl =  AppController.APIURL+"api/get_EmployeeCSRQuestionOct?AEMEmployeeID=" + prefManager.getUserId() + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        llLoader.setVisibility(View.VISIBLE);
        scMain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                                llLoader.setVisibility(View.VISIBLE);
                                scMain.setVisibility(View.GONE);
                                Intent intent = new Intent(KAEnqueryActivity.this, DashBoardActivity.class);
                                startActivity(intent);
                                finish();


                            } else {
                                setCompList();

                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(KAEnqueryActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(KAEnqueryActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setCompList() {
        Log.d("hitr", "1");

        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=4&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        llLoader.setVisibility(View.VISIBLE);
        scMain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        llLoader.setVisibility(View.GONE);
                        scMain.setVisibility(View.VISIBLE);
                        ItemList.clear();
                        ItemList1.clear();


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
                                    String ModelName = obj.optString("value");
                                    String ModelCode = obj.optString("id");
                                    SpinnerItemModule spModel = new SpinnerItemModule(ModelName, ModelCode);
                                    ItemList.add(spModel);
                                    ItemList1.add(spModel);


                                }


                                for (int j = 0; j < ItemList.size(); j++) {
                                    KeyPairBoolData h = new KeyPairBoolData();
                                    h.setName(ItemList.get(j).getItem());
                                    h.setId(ItemList.get(j).getItemId());
                                    h.setSelected(false);
                                    bCompanyList.add(h);

                                }

                                for (int j = 0; j < ItemList1.size(); j++) {
                                    KeyPairBoolData h = new KeyPairBoolData();
                                    h.setName(ItemList1.get(j).getItem());
                                    h.setId(ItemList1.get(j).getItemId());
                                    h.setSelected(false);
                                    tCompanyList.add(h);
                                }


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(KAEnqueryActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(KAEnqueryActivity.this);
        requestQueue.add(stringRequest);

    }

    private void postSubmit() {

        String q1CID = storeMangement + "&" + stockIssue + "&" + ifbDisplay + "&" + "OTHER-" + etOthers.getText().toString();
        String q2CID;
        if (competitor.equals("YES")) {
            q2CID = competitor + "&" + etCompetitor.getText().toString();
        } else {
            q2CID = competitor + "&";
        }
        String q3CID = teamMeet;
        String q4CID;
        if (traning.equals("YES")) {
            q4CID = traningCompId;
        } else {
            q4CID = traning;
        }
        String q5CID = collaboration + "&" + etCollaborate.getText().toString();
        String q6CID = etSuggestion.getText().toString();
        String q12CID;
        if (stockIssue.equals("YES")) {
            q12CID = compId;
        } else {
            q12CID = "";
        }


        final ProgressDialog pd = new ProgressDialog(KAEnqueryActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload( AppController.APIURL+"api/post_EmployeeCSRQuestionOct")
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("Q1CID", q1CID)
                .addMultipartParameter("Q2CID", q2CID)
                .addMultipartParameter("Q3CID", q3CID)
                .addMultipartParameter("Q4CID", q4CID)
                .addMultipartParameter("Q5CID", q5CID)
                .addMultipartParameter("Q6CID", q6CID)
                .addMultipartParameter("Q12CID", q12CID)
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
                            successAlert(responseText);
                            pd.dismiss();

                        } else {
                            pd.dismiss();
                            Toast.makeText(KAEnqueryActivity.this, responseText, Toast.LENGTH_LONG).show();

                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void onclick() {
        spStock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    stockIssue = yesnoList.get(position);
                    if (stockIssue.equals("YES")) {
                        llCategory.setVisibility(View.VISIBLE);
                    } else {
                        llCategory.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCompetitor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    competitor = yesnoList.get(position);
                    if (competitor.equals("YES")) {
                        llCometitor.setVisibility(View.VISIBLE);
                    } else {
                        llCometitor.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spStoreManagement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    storeMangement = yesnoList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spIFBDisplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    ifbDisplay = displayList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spTeamMeet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    teamMeet = teamMeetList.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCollaborate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    collaboration = yesnoList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCategory.setItems(bCompanyList, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        String comName = "YES-" + items.get(i).getId();
                        compList.add(comName);
                        String comp = compList.toString();
                        compId = comp.replace("[", "").replace("]", "").replace("[", "").replaceAll(",", "&");
                        Log.d("comName", compId);


                    }
                }
            }


        });
        spTraining.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    traning = yesnoList.get(position);
                    if (traning.equals("YES")) {
                        llTraningCategory.setVisibility(View.VISIBLE);
                    } else {
                        llTraningCategory.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spTraningCatory.setItems(tCompanyList, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        String comName = "YES-" + items.get(i).getId();
                        tCompList.add(comName);
                        String comp = tCompList.toString();
                        traningCompId = comp.replace("[", "").replace("]", "").replace("[", "").replaceAll(",", "&");
                        Log.d("comName", traningCompId);


                    }
                }
            }


        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!storeMangement.equals("")) {
                    if (!stockIssue.equals("")) {
                        if (!competitor.equals("")) {
                            if (!teamMeet.equals("")) {
                                if (!traning.equals("")) {
                                    if (!collaboration.equals("")) {
                                        if (etSuggestion.getText().toString().length()>0){
                                            if (!ifbDisplay.equals("")) {
                                                stockChecking();
                                            }else {
                                                Toast.makeText(KAEnqueryActivity.this, "Please Select IFB Display", Toast.LENGTH_LONG).show();

                                            }

                                        }else {
                                            Toast.makeText(KAEnqueryActivity.this, "Please Enter Suggestion from your side for IFB Sales Improvement", Toast.LENGTH_LONG).show();

                                        }

                                    } else {
                                        Toast.makeText(KAEnqueryActivity.this, "Please Select Is using Collaborate beneficial", Toast.LENGTH_LONG).show();

                                    }

                                } else {
                                    Toast.makeText(KAEnqueryActivity.this, "Please Select Training required or Not  ", Toast.LENGTH_LONG).show();

                                }

                            } else {
                                Toast.makeText(KAEnqueryActivity.this, "Please Select How many time you met IFB Sales team last month ", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            Toast.makeText(KAEnqueryActivity.this, "Please Select Is competition doing any thing interesting for Product sale", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(KAEnqueryActivity.this, "Please Select Store Issue", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(KAEnqueryActivity.this, "Please Select Store Mangement is Supperotive or Not", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void successAlert(String text) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(KAEnqueryActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText(text);

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                Intent intent = new Intent(KAEnqueryActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();


            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }

    private void postFunction() {

    }


    private void upDateAlert(String updateVersion) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(KAEnqueryActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_update_alert, null);
        dialogBuilder.setView(dialogView);
        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        TextView tvUpdateVersion=(TextView)dialogView.findViewById(R.id.tvUpdateVersion);
        tvUpdateVersion.setText("New Version "+updateVersion+" is available in Play Store");
        TextView tvCurrentVersion=(TextView)dialogView.findViewById(R.id.tvCurrentVersion);
        tvCurrentVersion.setText("Current App Version is "+version);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
                al1.dismiss();
                deleteAppData();



            }
        });


        al1 = dialogBuilder.create();
        al1.setCancelable(false);
        Window window = al1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        al1.show();
    }

    private void stockChecking(){
        if (stockIssue.equals("YES")){
            if (!compId.equals("")){

                cometitorChecking();

            }else {
                Toast.makeText(KAEnqueryActivity.this,"Please Select Stock Category",Toast.LENGTH_LONG).show();
            }
        }else {
            cometitorChecking();
        }
    }

    private void cometitorChecking(){
        if (competitor.equals("YES")){
            if (etCompetitor.getText().toString().length()>0){
                traningChecking();
            }else {
                Toast.makeText(KAEnqueryActivity.this,"Please Enter Remarks for Competitor",Toast.LENGTH_LONG).show();
            }
        }else {
            traningChecking();
        }
    }

    private void traningChecking(){
        if (traning.equals("YES")){
            if (!traningCompId.equals("")){
                postSubmit();
            }else {
                Toast.makeText(KAEnqueryActivity.this,"Please Select Category for Traning",Toast.LENGTH_LONG).show();
            }

        }else {
            postSubmit();
        }
    }

    private void deleteAppData() {
        try {
            // clearing app data
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
