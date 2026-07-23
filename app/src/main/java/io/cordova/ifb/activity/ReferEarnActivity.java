package io.cordova.ifb.activity;

import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.ReferEarnReportAdapter;
import io.cordova.ifb.module.ReferEarnModule;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.FindDocumentInformation;
import io.cordova.ifb.utility.PrefManager;
import io.cordova.ifb.utility.RealPathUtil;
import okhttp3.OkHttpClient;

public class ReferEarnActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ReferEarnActivity";
    EditText etCanName, etCanMobNumber,etCanAadhaar;
    ImageView imgAttach, imgAttachPDF;
    Button btnSave;
    private static final int PICK_PDF_REQUEST = 10;
    String pdfFilePath, pdfFileName;
    int pdfflag = 0;
    File pdfFile;
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    AlertDialog alerDialog1;
    PrefManager prefManager;
    ImageView imgBack, imgHome;
    LinearLayout llLoader, llMain, llNoData;
    ArrayList<ReferEarnModule> itemList = new ArrayList<>();
    RecyclerView rvItem;
    FloatingActionButton fbAdd;
    LinearLayout llReferReport,lnRefer;
    SingleSpinnerSearch spBranch;
    ArrayList<SpinnerItemModule>moduleBranchList=new ArrayList<>();
    ArrayList<KeyPairBoolData> keyBranchList = new ArrayList<>();
    String branchID="";
    Spinner spYear,spMonth;
    Button btnShow;
    ArrayList<String>monthList=new ArrayList<>();
    ArrayList<String>yearList=new ArrayList<>();
    TextView tvRank,tvMTD,tvYTD;
    String financialYear,month,year;
    int y;
    String mID,yID;
    LinearLayout llYTD,llMTD,llMTDActive,llMTDInActive,llYTDActive,llYTDInActive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_earn);
        initView();
        setBranchList();
    }

    private void initView() {
        prefManager = new PrefManager(ReferEarnActivity.this);
        OkHttpClient okHttpClient =
                AppController.getUnsafeOkHttpClient();

        AndroidNetworking.initialize(
                getApplicationContext(),
                okHttpClient
        );
        spMonth=(Spinner)findViewById(R.id.spMonth);
        spYear=(Spinner)findViewById(R.id.spYear);
        btnShow=(Button)findViewById(R.id.btnShow);
        tvRank=(TextView)findViewById(R.id.tvRank);
        tvYTD=(TextView)findViewById(R.id.tvYTD);
        tvMTD=(TextView)findViewById(R.id.tvMTD);

        llYTD=(LinearLayout)findViewById(R.id.llYTD);
        llMTD=(LinearLayout)findViewById(R.id.llMTD);
        llMTDActive=(LinearLayout)findViewById(R.id.llMTDActive);
        llMTDInActive=(LinearLayout)findViewById(R.id.llMTDInActive);
        llYTDActive=(LinearLayout)findViewById(R.id.llYTDActive);
        llYTDInActive=(LinearLayout)findViewById(R.id.llYTDInActive);

        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");
        yearList.add("2024-2025");
        yearList.add("2025-2026");
        yearList.add("2026-2027");
        yearList.add("2027-2028");
        yearList.add("2028-2029");

        etCanName = (EditText) findViewById(R.id.etCanName);
        etCanMobNumber = (EditText) findViewById(R.id.etCanMobNumber);
        etCanAadhaar=(EditText)findViewById(R.id.etCanAadhaar);
        imgAttach = (ImageView) findViewById(R.id.imgAttach);
        imgAttachPDF = (ImageView) findViewById(R.id.imgAttachPDF);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);

        btnSave = (Button) findViewById(R.id.btnSave);

        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        llNoData = (LinearLayout) findViewById(R.id.llNoData);
        llReferReport = (LinearLayout) findViewById(R.id.llReferReport);
        lnRefer = (LinearLayout) findViewById(R.id.lnRefer);


        fbAdd=(FloatingActionButton)findViewById(R.id.fbAdd);

        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(ReferEarnActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);


        imgAttach.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        fbAdd.setOnClickListener(this);
        llMTD.setOnClickListener(this);
        llYTD.setOnClickListener(this);

        spBranch=(SingleSpinnerSearch) findViewById(R.id.spBranch);


        spBranch.setItems(keyBranchList, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {

                        branchID = items.get(i).getId();




                    }
                }
            }


        });

        y = Calendar.getInstance().get(Calendar.YEAR);
        year = String.valueOf(y);
        Log.d("year", year);

        int m = Calendar.getInstance().get(Calendar.MONTH) + 1;
        Log.d("month", String.valueOf(m));
        if (m == 1) {
            month = "January";
        } else if (m == 2) {
            month = "February";
        } else if (m == 3) {
            month = "March";
        } else if (m == 4) {
            month = "April";
        } else if (m == 5) {
            month = "May";
        } else if (m == 6) {
            month = "June";
        } else if (m == 7) {
            month = "July";
        } else if (m == 8) {
            month = "August";
        } else if (m == 9) {
            month = "September";
        } else if (m == 10) {
            month = "October";
        } else if (m == 11) {
            month = "November";
        } else if (m == 12) {
            month = "December";
        }
        if(month.equals("January")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else if (month.equals("February")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else if (month.equals("March")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else {
            int futureyear = y + 1;
            financialYear = year+"-"+futureyear;
        }

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>
                (ReferEarnActivity.this, android.R.layout.simple_spinner_item,
                        monthList); //selected item will look like a spinner set from XML
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonth.setAdapter(monthAdapter);

        int pos=monthList.indexOf(month);
        spMonth.setSelection(pos);


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>
                (ReferEarnActivity.this, android.R.layout.simple_spinner_item,
                        yearList); //selected item will look like a spinner set from XML
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(yearAdapter);

        int yearpos=yearList.indexOf(financialYear);
        spYear.setSelection(yearpos);

        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mID=monthList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yID=yearList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnShow.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        if (view == imgAttach) {
            showFileChooser();
        } else if (view == btnSave) {
            if (etCanName.getText().toString().length() > 0) {
                if (etCanMobNumber.getText().toString().length() == 10) {

                    if (pdfflag == 1) {
                        if (!branchID.equals("")){
                            aadhaarValidation();
                        }else {
                            Toast.makeText(ReferEarnActivity.this, "Please select your referred branch", Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        Toast.makeText(ReferEarnActivity.this, "Please Enter Candidate's Resume/CV", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(ReferEarnActivity.this, "Please Enter Candidate's Valid Mobile Number", Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(ReferEarnActivity.this, "Please Enter Candidate's Name", Toast.LENGTH_SHORT).show();
            }
        } else if (view == imgBack) {
            onBackPressed();
        } else if (view == imgHome) {
            Intent intent = new Intent(ReferEarnActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        }else if (view == fbAdd) {
          llReferReport.setVisibility(View.GONE);
          lnRefer.setVisibility(View.VISIBLE);
        }else if (view == btnShow) {
           getCoonolidatedItem();
        }else if (view == llMTD) {
            getMTDItem();
        }else if (view == llYTD) {
            getYTDItem();
        }

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        //intent.setType("application/pdf");

        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {
                "application/pdf",                // PDF
                "image/*",                        // Images (jpg, png, etc.)
                "application/msword",            // .doc
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document" // .docx
        });
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //intent.setAction(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case PICK_PDF_REQUEST:
                if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uri = data.getData();
                  /*  pdfFilePath = getRealPath(ReferEarnActivity.this, uri);
                    Log.e(TAG, "pdfFilePath: "+pdfFilePath);
                    pdfFileName = FindDocumentInformation.FileNameFromURL(pdfFilePath);
                    Log.e(TAG, "pdfFileName: "+pdfFileName);*/
                    //if (pdfFileName.isEmpty() || pdfFileName == null) {
                        Log.e(TAG, "onActivityResult: " + uri.getPath());
                        if (uri != null) {
                            try {
                                //  Get file name
                                String fileName = getFileName(uri);
                                //  Open InputStream from URI
                                ///InputStream inputStream = getContentResolver().openInputStream(uri);
                                //  Read bytes if needed
                                //byte[] fileBytes = readBytes(inputStream);
                                //Log.d("File Info", "Name: " + fileName + ", Size: " + fileBytes.length);
                                // Do whatever: upload, preview, save locally, etc.
                                pdfFile = convertInputStreamToFile(uri, fileName);
                                Log.e(TAG, "onActivityResult: "+pdfFile.getAbsolutePath());
                                pdfFileName = fileName;
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
                            }
                        }
                    /*} else {
                        pdfFile = convertInputStreamToFile(uri, pdfFileName);
                        Log.e(TAG, "onActivityResult: "+pdfFile.getAbsolutePath());
                    }*/
                    if (pdfFileName.contains("pdf")){
                        imgAttachPDF.setImageResource(R.drawable.pdf);
                        imgAttachPDF.setImageTintList(ContextCompat.getColorStateList(ReferEarnActivity.this, R.color.red));
                    } else if(pdfFileName.contains("docx")){
                        imgAttachPDF.setImageResource(R.drawable.docx_file);
                        imgAttachPDF.setImageTintList(null);
                    } else if(pdfFileName.contains("doc")){
                        imgAttachPDF.setImageResource(R.drawable.doc_1);
                        imgAttachPDF.setImageTintList(null);
                    } else {
                        imgAttachPDF.setImageResource(R.drawable.img);
                        imgAttachPDF.setImageTintList(null);
                    }
                    imgAttachPDF.setVisibility(View.VISIBLE);
                    pdfflag = 1;
                }
                break;


        }


    }

    private byte[] readBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private String getFileName(Uri uri) {
        String result = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            result = cursor.getString(nameIndex);
            cursor.close();
        }
        return result != null ? result : "unknown_file";
    }

    public static String getRealPath(Context context, Uri fileUri) {
        String realPath;
        Log.e("SDK_INT", "= " + SDK_INT);
        // SDK < API11
        if (SDK_INT < 11) {
            realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(context, fileUri);
        }
        // SDK >= 11 && SDK < 19
        else if (SDK_INT < 19) {
            realPath = RealPathUtil.getRealPathFromURI_API11to18(context, fileUri);
        }
        // SDK > 19 (Android 4.4) and up
        else {
            realPath = RealPathUtil.getRealPathFromURI_API19(context, fileUri);
        }
        return realPath;
    }

    private File convertInputStreamToFile(Uri uri, String fileNme) {
        InputStream inputStream;
        try {
            inputStream = ReferEarnActivity.this.getContentResolver().openInputStream(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File(ReferEarnActivity.this.getExternalFilesDir("/").getAbsolutePath(), fileNme);

        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            Log.e("TAG", "convertInputStreamToFile: file: " + file.getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public void uploadCV() {

        final ProgressDialog pd = new ProgressDialog(ReferEarnActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.dismiss();
        pd.show();

        AndroidNetworking.upload(AppController.APIV2URL + "api/post_EmployeeReferalV2")
                .addMultipartParameter("EmployeeID", prefManager.getUserId())
                .addMultipartParameter("CandidateName", etCanName.getText().toString())
                .addMultipartParameter("CandidateMobile", etCanMobNumber.getText().toString())
                .addMultipartParameter("Aadhar", etCanAadhaar.getText().toString())
                .addMultipartParameter("BranchID", branchID)
                .addMultipartParameter("SecurityCode", prefManager.getSecurityCode())
                .addMultipartFile("CV", pdfFile)
                .addHeaders("Authorization", "Bearer " + prefManager.getAccessToken())
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
                        pd.dismiss();


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        String responseText = job1.optString("responseText");
                        Log.d("responseText", responseText);
                        boolean responseStatus = job1.optBoolean("responseStatus");
                        if (responseStatus) {
                            successAlert();
                            pd.dismiss();

                        } else {
                            pd.dismiss();
                            Toast.makeText(ReferEarnActivity.this, responseText, Toast.LENGTH_LONG).show();

                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG);
                    }
                });

    }

    private void successAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ReferEarnActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText("Your data has been submitted successfully");

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                Intent intent=new Intent(ReferEarnActivity.this,DashBoardActivity.class);
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

    private void getCoonolidatedItem() {
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        String surl = AppController.APIV2URL + "api/get_EmployeeReferralReport?FinancialYear="+yID+"&Month="+mID+"&AEMEmployeeID="+prefManager.getUserId()+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);



                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONObject ReferralDetails = job1.optJSONObject("ReferralDetails");
                                String ReferralRank=ReferralDetails.optString("ReferralRank");
                                String CSR_Referral_YTD=ReferralDetails.optString("CSR_Referral_YTD");
                                String CSR_Referral_MTD=ReferralDetails.optString("CSR_Referral_MTD");
                                tvRank.setText(ReferralRank);
                                tvMTD.setText(CSR_Referral_MTD);
                                tvYTD.setText(CSR_Referral_YTD);


                                llLoader.setVisibility(View.VISIBLE);
                                llMain.setVisibility(View.GONE);
                                llNoData.setVisibility(View.GONE);

                                getMTDItem();




                            } else {
                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.GONE);
                                llNoData.setVisibility(View.VISIBLE);


                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ReferEarnActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                llNoData.setVisibility(View.VISIBLE);


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
//        RequestQueue requestQueue = Volley.newRequestQueue(ReferEarnActivity.this);
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(ReferEarnActivity.this);

        requestQueue.add(stringRequest);

    }


    private void getMTDItem() {
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        String surl = AppController.APIV2URL + "api/get_EmployeeReferralDetailsReport?FinancialYear="+yID+"&Month="+mID+"&AEMEmployeeID="+prefManager.getUserId()+"&Operation=3&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        itemList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                JSONArray ReferralEmployee=job1.optJSONArray("ReferralEmployee");
                                for (int i=0;i<ReferralEmployee.length();i++){
                                    JSONObject referalEmpObj=ReferralEmployee.optJSONObject(i);
                                    String Reffered_CSR_Name=referalEmpObj.optString("Reffered_CSR_Name");
                                    String Reffered_CSR_Number=referalEmpObj.optString("Reffered_CSR_Number");
                                    String CSR_ONboarded_Date=referalEmpObj.optString("CSR_ONboarded_Date");
                                    String ReferralEligabledate=referalEmpObj.optString("ReferralEligabledate");
                                    String CSR_Current_Status=referalEmpObj.optString("CSR_Current_Status");
                                    String Referral_Amount=referalEmpObj.optString("Referral_Amount");
                                    String Referral_Amount_Paid_date=referalEmpObj.optString("Referral_Amount_Paid_date");
                                    String CSR_EXIT_Date=referalEmpObj.optString("CSR_EXIT_Date");
                                    String CandidateAadhar=referalEmpObj.optString("CandidateAadhar");
                                    ReferEarnModule earnModule=new ReferEarnModule();
                                    earnModule.setReferCanName(Reffered_CSR_Name);
                                    earnModule.setReferCanMob(Reffered_CSR_Number);
                                    earnModule.setCSR_ONboarded_Date(CSR_ONboarded_Date);
                                    earnModule.setReferralEligabledate(ReferralEligabledate);
                                    earnModule.setCSR_Current_Status(CSR_Current_Status);
                                    earnModule.setReferral_Amount(Referral_Amount);
                                    earnModule.setReferral_Amount_Paid_date(Referral_Amount_Paid_date);
                                    earnModule.setCSR_EXIT_Date(CSR_EXIT_Date);
                                    earnModule.setCandidateAadhar(CandidateAadhar);
                                    earnModule.setReffered_Month(mID);
                                    itemList.add(earnModule);

                                }

                                llMTDActive.setVisibility(View.VISIBLE);
                                llMTDInActive.setVisibility(View.GONE);

                                llYTDActive.setVisibility(View.GONE);
                                llYTDInActive.setVisibility(View.VISIBLE);
                                setAdapter();



                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                llNoData.setVisibility(View.GONE);




                            } else {
                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.GONE);
                                llNoData.setVisibility(View.VISIBLE);


                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ReferEarnActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                llNoData.setVisibility(View.VISIBLE);


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
//        RequestQueue requestQueue = Volley.newRequestQueue(ReferEarnActivity.this);
//        requestQueue.add(stringRequest);
        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(ReferEarnActivity.this);

        requestQueue.add(stringRequest);

    }


    private void getYTDItem() {
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        String surl = AppController.APIV2URL + "api/get_EmployeeReferralDetailsReport?FinancialYear="+yID+"&Month=%&AEMEmployeeID="+prefManager.getUserId()+"&Operation=2&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputReport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        itemList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                JSONArray ReferralEmployee=job1.optJSONArray("ReferralEmployee");
                                for (int i=0;i<ReferralEmployee.length();i++){
                                    JSONObject referalEmpObj=ReferralEmployee.optJSONObject(i);
                                    String Reffered_CSR_Name=referalEmpObj.optString("Reffered_CSR_Name");
                                    String Reffered_CSR_Number=referalEmpObj.optString("Reffered_CSR_Number");
                                    String CSR_ONboarded_Date=referalEmpObj.optString("CSR_ONboarded_Date");
                                    String ReferralEligabledate=referalEmpObj.optString("ReferralEligabledate");
                                    String CSR_Current_Status=referalEmpObj.optString("CSR_Current_Status");
                                    String Referral_Amount=referalEmpObj.optString("Referral_Amount");
                                    String Referral_Amount_Paid_date=referalEmpObj.optString("Referral_Amount_Paid_date");
                                    String CSR_EXIT_Date=referalEmpObj.optString("CSR_EXIT_Date");
                                    String Reffered_Month=referalEmpObj.optString("Reffered_Month");
                                    String CandidateAadhar=referalEmpObj.optString("CandidateAadhar");
                                    ReferEarnModule earnModule=new ReferEarnModule();
                                    earnModule.setReferCanName(Reffered_CSR_Name);
                                    earnModule.setReferCanMob(Reffered_CSR_Number);
                                    earnModule.setCSR_ONboarded_Date(CSR_ONboarded_Date);
                                    earnModule.setReferralEligabledate(ReferralEligabledate);
                                    earnModule.setCSR_Current_Status(CSR_Current_Status);
                                    earnModule.setReferral_Amount(Referral_Amount);
                                    earnModule.setReferral_Amount_Paid_date(Referral_Amount_Paid_date);
                                    earnModule.setCSR_EXIT_Date(CSR_EXIT_Date);
                                    earnModule.setReffered_Month(Reffered_Month);
                                    earnModule.setCandidateAadhar(CandidateAadhar);
                                    itemList.add(earnModule);

                                }
                                setAdapter();


                                llMTDActive.setVisibility(View.GONE);
                                llMTDInActive.setVisibility(View.VISIBLE);

                                llYTDActive.setVisibility(View.VISIBLE);
                                llYTDInActive.setVisibility(View.GONE);


                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                llNoData.setVisibility(View.GONE);




                            } else {
                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.GONE);
                                llNoData.setVisibility(View.VISIBLE);


                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ReferEarnActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                llNoData.setVisibility(View.VISIBLE);


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
//        RequestQueue requestQueue = Volley.newRequestQueue(ReferEarnActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(ReferEarnActivity.this);

        requestQueue.add(stringRequest);
    }

    private void setAdapter() {
        ReferEarnReportAdapter aAdapter = new ReferEarnReportAdapter(itemList,ReferEarnActivity.this);
        rvItem.setAdapter(aAdapter);

    }


    private void setBranchList() {
        String surl = AppController.APIV2URL + "api/CommonDDL?ModuleNo=333&ID=0&ID1=0&ID2=" + prefManager.getBranchId() + "&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("modelinput", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseModel", response);
                        progressBar.dismiss();

                        moduleBranchList.clear();
                        keyBranchList.clear();

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
                                    String value = obj.optString("value");
                                    String id = obj.optString("id");

                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleBranchList.add(itemModule);

                                }

                                for (int j = 0; j < moduleBranchList.size(); j++) {
                                    KeyPairBoolData h = new KeyPairBoolData();
                                    h.setName(moduleBranchList.get(j).getItem());
                                    h.setId(moduleBranchList.get(j).getItemId());
                                    h.setSelected(false);
                                    keyBranchList.add(h);

                                }


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ReferEarnActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "model");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+prefManager.getAccessToken());
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(ReferEarnActivity.this);
//        requestQueue.add(stringRequest);

        RequestQueue requestQueue =
                AppController.getUnsafeOkHttpQueue(ReferEarnActivity.this);

        requestQueue.add(stringRequest);
    }

    private void aadhaarValidation(){
        if (etCanAadhaar.getText().toString().length()>0){
            if (isValidAadhaar(etCanAadhaar.getText().toString())) {
                uploadCV();
            } else {
                Toast.makeText(ReferEarnActivity.this, "Please Enter 12 digits valid Aadhaar Number", Toast.LENGTH_SHORT).show();
            }
        }else {
            uploadCV();
        }

    }

    public boolean isValidAadhaar(String aadhaarNumber) {
        // Check if it's a 12-digit number and doesn't start with 0 or 1
        return aadhaarNumber != null && aadhaarNumber.matches("^[2-9]{1}[0-9]{11}$");
    }
}