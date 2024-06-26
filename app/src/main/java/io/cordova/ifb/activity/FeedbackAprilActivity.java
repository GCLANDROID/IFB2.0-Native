package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.FeedbackAdapter;
import io.cordova.ifb.module.FeedBackModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PostDisplayMatrixService;
import io.cordova.ifb.utility.PrefManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedbackAprilActivity extends AppCompatActivity {
    RecyclerView rvItem;
    FeedbackAdapter madapter;

    ArrayList<FeedBackModel> questionList = new ArrayList<>();
    LinearLayoutManager layoutManager;
    LinearLayout llLoder;
    LinearLayout llMain;
    ArrayList<String> item = new ArrayList<>();
    String month, financialYear, year;
    int y;
    LinearLayout llSkip;
    PrefManager prefManager;
    private static final String SERVER_PATH =  AppController.APIURL+"api/";
    private PostDisplayMatrixService uploadService;
    ProgressDialog progressDialog;
    String userid, securitycode;
    String qustionId;
    Button btnSave, btnSkip;
    AlertDialog alerDialog1;
    String SkipFlag;
    EditText etRemarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_feedback_april);
        this.setFinishOnTouchOutside(false);
        initialize();
        getQuestionlist();
        onClick();

    }

    private void initialize() {
        prefManager = new PrefManager(getApplicationContext());
        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        layoutManager
                = new LinearLayoutManager(FeedbackAprilActivity.this, LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        llLoder = (LinearLayout) findViewById(R.id.llWLLoader);
        llSkip = (LinearLayout) findViewById(R.id.llSkip);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        Log.d("formattedDate", formattedDate);
        String[] separated = formattedDate.split("-");
        String s1 = separated[0];
        int dates1 = Integer.parseInt(s1);
        String s2 = separated[1];
        Log.d("s1", String.valueOf(dates1));


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
        if (month.equals("January")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else if (month.equals("February")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else if (month.equals("March")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else {
            int futureyear = y + 1;
            financialYear = year + "-" + futureyear;
        }


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Change base URL to your upload server URL.
        uploadService = (PostDisplayMatrixService) new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PostDisplayMatrixService.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        userid = prefManager.getUserId();
        securitycode = prefManager.getSecurityCode();
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSkip = (Button) findViewById(R.id.btnSkip);
        etRemarks=(EditText)findViewById(R.id.etRemarks);


    }

    private void getQuestionlist() {
        llLoder.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);

        String surl =  AppController.APIURL+"api/get_EmployeeCategorywiseQuestion?EmployeeId=" + prefManager.getUserId() + "&FinancialYear=" + financialYear + "&Month=" + month + "&Operation=1&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("input", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("responseAttendance", response);

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            //    Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                // Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String Question = obj.optString("Question");
                                    String Q_Option = obj.optString("Q_Option");
                                    String Q_OptionId = obj.optString("Q_OptionId");
                                    SkipFlag = obj.optString("SkipFlag");

                                    FeedBackModel mModule = new FeedBackModel(Question, Q_Option, Q_OptionId);
                                    questionList.add(mModule);

                                }

                                llLoder.setVisibility(View.GONE);
                                llMain.setVisibility(View.VISIBLE);
                                setAdapter();

                                if (SkipFlag.equals("1")) {
                                    llSkip.setVisibility(View.GONE);
                                } else {
                                    llSkip.setVisibility(View.VISIBLE);
                                }


                            } else {

                                finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(FeedbackAprilActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoder.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                finish();


                // Toast.makeText(FeedbackAprilActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(FeedbackAprilActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setAdapter() {
        madapter = new FeedbackAdapter(questionList, FeedbackAprilActivity.this);
        rvItem.setAdapter(madapter);
    }


    public void updateAttendanceStatus(int position, boolean status) {
        questionList.get(position).setSelected(status);
        if (questionList.get(position).isSelected() == true) {
            item.add(questionList.get(position).getQ_OptionId() + "-" + "Yes" + "#" + "OK");
        } else {
            item.clear();
        }
        Log.d("arpan", item.toString());
        String i = item.toString();
        String d = i.replace("[", "").replace("]", "");
        qustionId = d.replaceAll("\\s+", "");

        madapter.notifyDataSetChanged();
    }

    private void onClick() {
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (item.size()>0) {
                        if (etRemarks.getText().toString().length()>0) {
                            submitAnswer();
                        }else {
                            Toast.makeText(getApplicationContext(),"Please enter remarks",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Please select answer",Toast.LENGTH_LONG).show();
                    }

            }
        });
    }


  /*  private void submitAnswer() {

        progressDialog.show();

        Call<UploadObject> fileUpload = uploadService.postAprilAnswer(userid, financialYear, month, qustionId, securitycode,etRemarks.getText().toString());
        fileUpload.enqueue(new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, retrofit2.Response<UploadObject> response) {
                progressDialog.dismiss();
                UploadObject extraWorkingDayModel = response.body();
                if (extraWorkingDayModel.isResponseStatus()) {

                    successAlert();

                } else {
                    Toast.makeText(getApplicationContext(), extraWorkingDayModel.getResponseText(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadObject> call, Throwable t) {
                progressDialog.dismiss();

                Log.e("error", "Error " + t.getMessage());
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();

                //   Toast.makeText(AttendanceManageActivity.this,"attendance saved without image",Toast.LENGTH_LONG).show();
            }

        });
    }*/


    private void successAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FeedbackAprilActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText("Feedback submitted successfully");

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                finish();
            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(false);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }

    private void submitAnswer() {

        final ProgressDialog pd=new ProgressDialog(FeedbackAprilActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        AndroidNetworking.upload( AppController.APIURL+"api/post_EmployeeCategorywiseQuestion")
                .addMultipartParameter("AEMEmployeeId", userid)
                .addMultipartParameter("FinacialYear", financialYear)
                .addMultipartParameter("Month", month)
                .addMultipartParameter("Question", qustionId)
                .addMultipartParameter("SecurityCode", securitycode)
                .addMultipartParameter("Remarks", etRemarks.getText().toString())

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

                        boolean responseStatus = job1.optBoolean("responseStatus");
                        Log.d("responseText", responseText);
                        if (responseStatus) {

                            successAlert();
                            pd.dismiss();

                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(FeedbackAprilActivity.this,responseText,Toast.LENGTH_LONG).show();

                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG);
                    }
                });
    }
}
