package io.cordova.ifb.activity;

import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.UUID;

import id.zelory.compressor.Compressor;
import io.cordova.ifb.R;
import io.cordova.ifb.adapter.AttedanceReportAdapter;
import io.cordova.ifb.adapter.ReferEarnReportAdapter;
import io.cordova.ifb.module.ReferEarnModule;
import io.cordova.ifb.module.ReportModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.FindDocumentInformation;
import io.cordova.ifb.utility.PrefManager;
import io.cordova.ifb.utility.RealPathUtil;

public class ReferEarnActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etCanName, etCanMobNumber;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_earn);
        initView();
        getItem();
    }

    private void initView() {
        prefManager = new PrefManager(ReferEarnActivity.this);
        etCanName = (EditText) findViewById(R.id.etCanName);
        etCanMobNumber = (EditText) findViewById(R.id.etCanMobNumber);

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


    }


    @Override
    public void onClick(View view) {
        if (view == imgAttach) {
            showFileChooser();
        } else if (view == btnSave) {
            if (etCanName.getText().toString().length() > 0) {
                if (etCanMobNumber.getText().toString().length() == 10) {
                    if (pdfflag == 1) {
                        uploadCV();

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
        }

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case PICK_PDF_REQUEST:
                if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uri = data.getData();
                    pdfFilePath = getRealPath(ReferEarnActivity.this, uri);
                    pdfFileName = FindDocumentInformation.FileNameFromURL(pdfFilePath);
                    pdfFile = convertInputStreamToFile(uri, pdfFileName);

                    imgAttachPDF.setVisibility(View.VISIBLE);

                    pdfflag = 1;

                }


                break;


        }


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

        AndroidNetworking.upload(AppController.APIURL + "api/post_EmployeeReferal")
                .addMultipartParameter("EmployeeID", prefManager.getUserId())
                .addMultipartParameter("CandidateName", etCanName.getText().toString())
                .addMultipartParameter("CandidateMobile", etCanMobNumber.getText().toString())
                .addMultipartParameter("SecurityCode", prefManager.getSecurityCode())
                .addMultipartFile("CV", pdfFile)

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

    private void getItem() {
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
        String surl = AppController.APIURL + "api/gclEmployeeReferal?AEMEmployeeID=" + prefManager.getUserId() + "&SecurityCode=" + prefManager.getSecurityCode();
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
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String CandidateName = obj.optString("CandidateName");
                                    String CandidateMobile = obj.optString("CandidateMobile");
                                    String Approval_Status = obj.optString("Approval_Status");
                                    String Doc_URL = obj.optString("Doc_URL");

                                    ReferEarnModule earnModule = new ReferEarnModule();
                                    earnModule.setReferCanMob(CandidateMobile);
                                    earnModule.setReferCanName(CandidateName);
                                    earnModule.setStatus(Approval_Status);
                                    earnModule.setDocLink(Doc_URL);
                                    itemList.add(earnModule);


                                }

                                llLoader.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                llNoData.setVisibility(View.GONE);

                                setAdapter();
                                /*llNodata.setVisibility(View.GONE);
                                llAgain.setVisibility(View.GONE);*/

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

        };
        RequestQueue requestQueue = Volley.newRequestQueue(ReferEarnActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setAdapter() {
        ReferEarnReportAdapter aAdapter = new ReferEarnReportAdapter(itemList,ReferEarnActivity.this);
        rvItem.setAdapter(aAdapter);

    }
}