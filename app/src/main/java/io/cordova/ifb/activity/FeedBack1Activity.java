package io.cordova.ifb.activity;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sdsmdg.tastytoast.TastyToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PostDisplayMatrixService;
import io.cordova.ifb.utility.PrefManager;
import io.cordova.ifb.utility.UploadObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedBack1Activity extends AppCompatActivity {
    LinearLayout llTick1,llTick2,llTick3,llTick4,llTick5,llTick6,llQ2Y,llQ2N,llQ3Y,llQ3N,llQ4Y,llQ4N,llSave,llSkip;
    ImageView imgTick1,imgTick2,imgTick3,imgTick4,imgTick5,imgTick6,imgQ2Y,imgQ2N,imgQ3Y,imgQ3N,imgQ4Y,imgQ4N;
    EditText etQ2,etQ4,etQ5;
    String month,financialYear,year;
    int y;
    String answer1="";
    String answer2="";
    String answer3="";
    String answer4="";
    String answer5="";


    private static final String SERVER_PATH =  AppController.APIURL+"api/";
    private PostDisplayMatrixService uploadService;
    ProgressDialog progressDialog;
    String userid,securitycode;
    PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_feed_back1);
        this.setFinishOnTouchOutside(false);
        initialize();
        onClick();
    }

    private void initialize(){
        prefManager=new PrefManager(getApplicationContext());
        llTick1=(LinearLayout)findViewById(R.id.llTick1);
        llTick2=(LinearLayout)findViewById(R.id.llTick2);
        llTick3=(LinearLayout)findViewById(R.id.llTick3);
        llTick4=(LinearLayout)findViewById(R.id.llTick4);
        llTick5=(LinearLayout)findViewById(R.id.llTick5);
        llTick6=(LinearLayout)findViewById(R.id.llTick6);

        llQ2Y=(LinearLayout)findViewById(R.id.llQ2Y);
        llQ2N=(LinearLayout)findViewById(R.id.llQ2N);

        llQ3Y=(LinearLayout)findViewById(R.id.llQ3Y);
        llQ3N=(LinearLayout)findViewById(R.id.llQ3N);

        llQ4Y=(LinearLayout)findViewById(R.id.llQ4Y);
        llQ4N=(LinearLayout)findViewById(R.id.llQ4N);

        llSave=(LinearLayout)findViewById(R.id.llSave);
        llSkip=(LinearLayout)findViewById(R.id.llSkip);



        imgTick1=(ImageView)findViewById(R.id.imgTick1);
        imgTick2=(ImageView)findViewById(R.id.imgTick2);
        imgTick3=(ImageView)findViewById(R.id.imgTick3);
        imgTick4=(ImageView)findViewById(R.id.imgTick4);
        imgTick5=(ImageView)findViewById(R.id.imgTick5);
        imgTick6=(ImageView)findViewById(R.id.imgTick6);

        imgQ2Y=(ImageView)findViewById(R.id.imgQ2Y);
        imgQ2N=(ImageView)findViewById(R.id.imgQ2N);

        imgQ3Y=(ImageView)findViewById(R.id.imgQ3Y);
        imgQ3N=(ImageView)findViewById(R.id.imgQ3N);

        imgQ4Y=(ImageView)findViewById(R.id.imgQ4Y);
        imgQ4N=(ImageView)findViewById(R.id.imgQ4N);

        etQ2=(EditText)findViewById(R.id.etQ2);
        etQ4=(EditText)findViewById(R.id.etQ4);
        etQ5=(EditText)findViewById(R.id.etQ5);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        Log.d("formattedDate",formattedDate);
        String[] separated = formattedDate.split("-");
        String s1=separated[0];
        int dates1= Integer.parseInt(s1);
        String s2=separated[1];
        Log.d("s1", String.valueOf(dates1));
        if (dates1>3){
            llSkip.setVisibility(View.GONE);
        }else {
            llSkip.setVisibility(View.VISIBLE);
        }

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
        userid=prefManager.getUserId();
        securitycode=prefManager.getSecurityCode();





    }

    private void onClick(){
        llTick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick1.setVisibility(View.VISIBLE);
                imgTick2.setVisibility(View.GONE);
                imgTick3.setVisibility(View.GONE);
                imgTick4.setVisibility(View.GONE);
                imgTick5.setVisibility(View.GONE);
                imgTick6.setVisibility(View.GONE);
                answer1="IFBQM00007"+"-"+"1"+"/"+"0";
                Log.d("answer1",answer1);
            }
        });

        llTick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick2.setVisibility(View.VISIBLE);
                imgTick1.setVisibility(View.GONE);
                imgTick3.setVisibility(View.GONE);
                imgTick4.setVisibility(View.GONE);
                imgTick5.setVisibility(View.GONE);
                imgTick6.setVisibility(View.GONE);
                answer1="IFBQM00007"+"-"+"2"+"/"+"0";
                Log.d("answer1",answer1);
            }
        });

        llTick3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick3.setVisibility(View.VISIBLE);
                imgTick1.setVisibility(View.GONE);
                imgTick2.setVisibility(View.GONE);
                imgTick4.setVisibility(View.GONE);
                imgTick5.setVisibility(View.GONE);
                imgTick6.setVisibility(View.GONE);
                answer1="IFBQM00007"+"-"+"3"+"/"+"0";
                Log.d("answer1",answer1);
            }
        });

        llTick4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick4.setVisibility(View.VISIBLE);
                imgTick1.setVisibility(View.GONE);
                imgTick2.setVisibility(View.GONE);
                imgTick3.setVisibility(View.GONE);
                imgTick5.setVisibility(View.GONE);
                imgTick6.setVisibility(View.GONE);
                answer1="IFBQM00007"+"-"+"4"+"/"+"0";
                Log.d("answer1",answer1);
            }
        });

        llTick5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick5.setVisibility(View.VISIBLE);
                imgTick1.setVisibility(View.GONE);
                imgTick2.setVisibility(View.GONE);
                imgTick3.setVisibility(View.GONE);
                imgTick4.setVisibility(View.GONE);
                imgTick6.setVisibility(View.GONE);
                answer1="IFBQM00007"+"-"+"5"+"/"+"0";
                Log.d("answer1",answer1);
            }
        });
        llTick6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTick6.setVisibility(View.VISIBLE);
                imgTick1.setVisibility(View.GONE);
                imgTick2.setVisibility(View.GONE);
                imgTick3.setVisibility(View.GONE);
                imgTick4.setVisibility(View.GONE);
                imgTick5.setVisibility(View.GONE);
                answer1="IFBQM00007"+"-"+"above"+"/"+"0";
                Log.d("answer1",answer1);
            }
        });

        llQ2Y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgQ2Y.setVisibility(View.VISIBLE);
                imgQ2N.setVisibility(View.GONE);
                etQ2.setVisibility(View.VISIBLE);
                Log.d("answer2",answer2);
            }
        });

        llQ2N.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgQ2Y.setVisibility(View.GONE);
                imgQ2N.setVisibility(View.VISIBLE);
                etQ2.setVisibility(View.GONE);
                answer2="IFBQM00008"+"-"+"NO"+"/"+"0";
                Log.d("answer2",answer2);
            }
        });

        llQ3Y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgQ3Y.setVisibility(View.VISIBLE);
                imgQ3N.setVisibility(View.GONE);
                answer3="IFBQM00009"+"-"+"YES"+"/"+"0";
                Log.d("answer3",answer3);
            }
        });

        llQ3N.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgQ3Y.setVisibility(View.GONE);
                imgQ3N.setVisibility(View.VISIBLE);
                answer3="IFBQM00009"+"-"+"NO"+"/"+"0";
                Log.d("answer3",answer3);
            }
        });

        llQ4Y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgQ4Y.setVisibility(View.VISIBLE);
                imgQ4N.setVisibility(View.GONE);
                etQ4.setVisibility(View.VISIBLE);
            }
        });

        llQ4N.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgQ4Y.setVisibility(View.GONE);
                imgQ4N.setVisibility(View.VISIBLE);
                etQ4.setVisibility(View.GONE);
                answer4="IFBQM00010"+"-"+"NO"+"/"+"0";
                Log.d("answer4",answer4);
            }
        });

        etQ2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etQ2.getText().toString().length()>0){
                    answer2="IFBQM00008"+"-"+"YES"+"/"+etQ2.getText().toString();
                    Log.d("answer2",answer2);
                }

            }
        });

        etQ4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etQ4.getText().toString().length()>0){

                    answer4="IFBQM00010"+"-"+"YES"+"/"+etQ4.getText().toString();
                    Log.d("answer4",answer4);
                }

            }
        });

        etQ5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etQ5.getText().toString().length()>0){
                    answer5="IFBQM00011"+"-"+etQ5.getText().toString()+"/"+"0";
                }

            }
        });

        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answer1.equals("")) {
                    if (!answer2.equals("")) {
                        if (!answer3.equals("")) {
                            if (!answer4.equals("")) {
                                if (!answer5.equals("")) {
                                    submitAnswer();
                                }else {
                                    Toast.makeText(getApplicationContext(),"Please enter Question 5",Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),"Please enter Question 4",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Please enter Question 3",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Please enter Question 2",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Please enter Question 1",Toast.LENGTH_LONG).show();
                }
            }
        });

        llSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void submitAnswer(){
        String answer=answer1+","+answer2+","+answer3+","+answer4+","+answer5;
        Log.d("subanswer",answer);
        progressDialog.show();

        Call<UploadObject> fileUpload = uploadService.postquestionanswer(userid,financialYear,month,answer,userid,securitycode);
        fileUpload.enqueue(new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, retrofit2.Response<UploadObject> response) {
                progressDialog.dismiss();
                UploadObject extraWorkingDayModel = response.body();
                if (extraWorkingDayModel.isResponseStatus()) {
                    String msg = extraWorkingDayModel.getResponseText();
                    TastyToast.makeText(getApplicationContext(), msg, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    Log.d("riku", "withocamera");
                    finish();

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
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
