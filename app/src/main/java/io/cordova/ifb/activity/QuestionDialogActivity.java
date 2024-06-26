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

public class QuestionDialogActivity extends AppCompatActivity {
    LinearLayout llFL,llTL,llMW,llDW,llCD,llAC,llKA,llGood,llNot,llYes,llNo,llDealerName;
    LinearLayout llQualGood,llQualAverage,llQualPoor,llSerViceGood,llServiceAverage,llServicePoor,llDesignGood,llDesignAverage,llDesignPoor;
    ImageView imgFL,imgTL,imgMW,imgDW,imgCD,imgAC,imgKA,imgGood,imgNot,imgYes,imgNo,imgQualGood,imgQualAverage,imgQualPoor,imgServiceGood,imgServiceAverage,imgServicePoor,imgDesignGood,imgDesignAverage,imgDesignPoor;
    LinearLayout llFeatureGood,llFeatureAverage,llFeaturePoor,llSechemeGood,llSchemeAverage,llSchemePoor;
    ImageView imgFeatureGood,imgFeatureAverage,imgFeaturePoor,imgSchemeGood,imgSchemeAverage,imgSchemePoor;
    EditText etFL,etTL,etMW,etDW,etCD,etAC,etKA;
    String ansfl="0";
    LinearLayout llSave;
    String anstl="0";
    String ansmw="0";
    String ansdw="0";
    String anscd="0";
    String ansac="0";
    String anska="0";
    String ans9,ans10,ans11,ans12,ans13,ans14,ans15;
    EditText etRemarks;
    String month,financialYear,year;
    int y;
    private static final String SERVER_PATH =  AppController.APIURL+"api/";
    private PostDisplayMatrixService uploadService;
    ProgressDialog progressDialog;
    String userid,securitycode;
    PrefManager prefManager;
    String  answer="";
    String ans1="";
    String ans2="";
    String ans3="";
    String ans4="";
    String ans5="";
    String ans6="";
    String ans7="";
    String ans8="";
    String ans16="";
    EditText etDealerName;
    LinearLayout llSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_question_dialog);
        this.setFinishOnTouchOutside(false);
        initialize();
        onClick();
    }

    private void initialize(){
        prefManager=new PrefManager(getApplicationContext());
        llFL=(LinearLayout)findViewById(R.id.llFL);
        llTL=(LinearLayout)findViewById(R.id.llTL);
        llMW=(LinearLayout)findViewById(R.id.llMW);
        llDW=(LinearLayout)findViewById(R.id.llDW);
        llCD=(LinearLayout)findViewById(R.id.llCD);
        llAC=(LinearLayout)findViewById(R.id.llAC);
        llKA=(LinearLayout)findViewById(R.id.llKA);
        llGood=(LinearLayout)findViewById(R.id.llGood);
        llNot=(LinearLayout)findViewById(R.id.llNot);
        llYes=(LinearLayout)findViewById(R.id.llYes);
        llNo=(LinearLayout)findViewById(R.id.llNo);
        llDealerName=(LinearLayout)findViewById(R.id.llDealerName);
        llQualGood=(LinearLayout)findViewById(R.id.llQualGood);
        llQualAverage=(LinearLayout)findViewById(R.id.llQualAverage);
        llQualPoor=(LinearLayout)findViewById(R.id.llQualPoor);
        llSerViceGood=(LinearLayout)findViewById(R.id.llSerViceGood);
        llServiceAverage=(LinearLayout)findViewById(R.id.llServiceAverage);
        llServicePoor=(LinearLayout)findViewById(R.id.llServicePoor);
        llDesignGood=(LinearLayout)findViewById(R.id.llDesignGood);
        llDesignAverage=(LinearLayout)findViewById(R.id.llDesignAverage);
        llDesignPoor=(LinearLayout)findViewById(R.id.llDesignPoor);
        llFeatureGood=(LinearLayout)findViewById(R.id.llFeatureGood);
        llFeatureAverage=(LinearLayout)findViewById(R.id.llFeatureAverage);
        llFeaturePoor=(LinearLayout)findViewById(R.id.llFeaturePoor);
        llSechemeGood=(LinearLayout)findViewById(R.id.llSechemeGood);
        llSchemeAverage=(LinearLayout)findViewById(R.id.llSchemeAverage);
        llSchemePoor=(LinearLayout)findViewById(R.id.llSchemePoor);
        llSave=(LinearLayout)findViewById(R.id.llSave);
        llSkip=(LinearLayout)findViewById(R.id.llSkip);



        imgFL=(ImageView)findViewById(R.id.imgFL);
        imgTL=(ImageView)findViewById(R.id.imgTL);
        imgMW=(ImageView)findViewById(R.id.imgMW);
        imgDW=(ImageView)findViewById(R.id.imgDW);
        imgCD=(ImageView)findViewById(R.id.imgCD);
        imgAC=(ImageView)findViewById(R.id.imgAC);
        imgKA=(ImageView)findViewById(R.id.imgKA);
        imgGood=(ImageView)findViewById(R.id.imgGood);
        imgNot=(ImageView)findViewById(R.id.imgNot);
        imgYes=(ImageView)findViewById(R.id.imgYes);
        imgNo=(ImageView)findViewById(R.id.imgNo);
        imgQualGood=(ImageView)findViewById(R.id.imgQualGood);
        imgQualAverage=(ImageView)findViewById(R.id.imgQualAverage);
        imgQualPoor=(ImageView)findViewById(R.id.imgQualPoor);
        imgServiceGood=(ImageView)findViewById(R.id.imgServiceGood);
        imgServiceAverage=(ImageView)findViewById(R.id.imgServiceAverage);
        imgServicePoor=(ImageView)findViewById(R.id.imgServicePoor);
        imgDesignGood=(ImageView)findViewById(R.id.imgDesignGood);
        imgDesignAverage=(ImageView)findViewById(R.id.imgDesignAverage);
        imgDesignPoor=(ImageView)findViewById(R.id.imgDesignPoor);
        imgFeatureGood=(ImageView)findViewById(R.id.imgFeatureGood);
        imgFeatureAverage=(ImageView)findViewById(R.id.imgFeatureAverage);
        imgFeaturePoor=(ImageView)findViewById(R.id.imgFeaturePoor);
        imgSchemeGood=(ImageView)findViewById(R.id.imgSchemeGood);
        imgSchemeAverage=(ImageView)findViewById(R.id.imgSchemeAverage);
        imgSchemePoor=(ImageView)findViewById(R.id.imgSchemePoor);

        etFL=(EditText)findViewById(R.id.etFL);
        etTL=(EditText)findViewById(R.id.etTL);
        etMW=(EditText)findViewById(R.id.etMW);
        etDW=(EditText)findViewById(R.id.etDW);
        etCD=(EditText)findViewById(R.id.etCD);
        etAC=(EditText)findViewById(R.id.etAC);
        etKA=(EditText)findViewById(R.id.etKA);
        etRemarks=(EditText)findViewById(R.id.etRemarks);
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

        etDealerName=(EditText)findViewById(R.id.etDealerName);
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




    }

    private void onClick(){
        //IFB PRODUCT
        llFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgFL.getVisibility()==View.GONE){
                    imgFL.setVisibility(View.VISIBLE);
                    ans9="IFBQM00005"+"-"+"FL"+"/"+"0";
                }else {
                    imgFL.setVisibility(View.GONE);
                }
            }
        });

        llTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgTL.getVisibility()==View.GONE){
                    imgTL.setVisibility(View.VISIBLE);
                    ans10="IFBQM00005"+"-"+"TL"+"/"+"0";
                }else {
                    imgTL.setVisibility(View.GONE);
                }
            }
        });

        llMW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgMW.getVisibility()==View.GONE){
                    imgMW.setVisibility(View.VISIBLE);
                    ans11="IFBQM00005"+"-"+"MW"+"/"+"0";
                }else {
                    imgMW.setVisibility(View.GONE);
                }
            }
        });

        llDW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgDW.getVisibility()==View.GONE){
                    imgDW.setVisibility(View.VISIBLE);
                    ans12="IFBQM00005"+"-"+"DW"+"/"+"0";
                }else {
                    imgDW.setVisibility(View.GONE);
                }
            }
        });

        llCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgCD.getVisibility()==View.GONE){
                    imgCD.setVisibility(View.VISIBLE);
                    ans13="IFBQM00005"+"-"+"CD"+"/"+"0";
                }else {
                    imgCD.setVisibility(View.GONE);
                }
            }
        });

        llAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgAC.getVisibility()==View.GONE){
                    imgAC.setVisibility(View.VISIBLE);
                    ans14="IFBQM00005"+"-"+"AC"+"/"+"0";
                }else {
                    imgAC.setVisibility(View.GONE);
                }
            }
        });

        llKA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgKA.getVisibility()==View.GONE){
                    imgKA.setVisibility(View.VISIBLE);
                    ans15="IFBQM00005"+"-"+"KA"+"/"+"0";
                }else {
                    imgKA.setVisibility(View.GONE);
                }
            }
        });

        //STRORE MANAGER
        llGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (imgGood.getVisibility()==View.GONE){
                   imgGood.setVisibility(View.VISIBLE);
                   imgNot.setVisibility(View.GONE);
                   ans2="IFBQM00002"+"-"+"Good"+"/"+"0";

               }else {
                   imgGood.setVisibility(View.GONE);
                   imgNot.setVisibility(View.VISIBLE);
               }
            }
        });
        llNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgNot.getVisibility()==View.GONE){
                    imgNot.setVisibility(View.VISIBLE);
                    imgGood.setVisibility(View.GONE);
                    ans2="IFBQM00002"+"-"+"NOT"+"/"+"0";
                }else {
                    imgNot.setVisibility(View.GONE);
                    imgGood.setVisibility(View.VISIBLE);
                }
            }
        });

        //MOP

        llYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgYes.getVisibility()==View.GONE){
                    imgYes.setVisibility(View.VISIBLE);
                    imgNo.setVisibility(View.GONE);
                    llDealerName.setVisibility(View.VISIBLE);


                }else {
                    imgYes.setVisibility(View.GONE);
                    llDealerName.setVisibility(View.GONE);
                }
            }
        });
        etDealerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etDealerName.getText().toString().length()>0){
                    ans3="IFBQM00003"+"-"+"Yes"+"/"+etDealerName.getText().toString();
                }

            }
        });

        llNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgNo.getVisibility()==View.GONE){
                    imgYes.setVisibility(View.GONE);
                    imgNo.setVisibility(View.VISIBLE);
                    llDealerName.setVisibility(View.GONE);
                    ans3="IFBQM00003"+"-"+"No"+"/"+"0";

                }else {
                    imgYes.setVisibility(View.VISIBLE);
                    llDealerName.setVisibility(View.VISIBLE);
                }
            }
        });

        //Quality
        llQualGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgQualGood.getVisibility()==View.GONE){
                    imgQualGood.setVisibility(View.VISIBLE);
                    imgQualAverage.setVisibility(View.GONE);
                    imgQualPoor.setVisibility(View.GONE);
                    ans4="IFBQM00004"+"-"+"Product Quality"+"/"+"Good";

                }else {
                    imgQualGood.setVisibility(View.GONE);
                }
            }
        });

        llQualAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgQualAverage.getVisibility()==View.GONE){
                    imgQualAverage.setVisibility(View.VISIBLE);
                    imgQualGood.setVisibility(View.GONE);
                    imgQualPoor.setVisibility(View.GONE);
                    ans4="IFBQM00004"+"-"+"Product Quality"+"/"+"Average";
                }else {
                    imgQualAverage.setVisibility(View.GONE);
                }
            }
        });

        llQualPoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgQualPoor.getVisibility()==View.GONE){
                    imgQualPoor.setVisibility(View.VISIBLE);
                    imgQualGood.setVisibility(View.GONE);
                    imgQualAverage.setVisibility(View.GONE);
                    ans4="IFBQM00004"+"-"+"Product Quality"+"/"+"Poor";
                }else {
                    imgQualPoor.setVisibility(View.GONE);
                }
            }
        });
        //service
        llSerViceGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgServiceGood.getVisibility()==View.GONE){
                    imgServiceGood.setVisibility(View.VISIBLE);
                    imgServiceAverage.setVisibility(View.GONE);
                    imgServicePoor.setVisibility(View.GONE);
                    ans5 ="IFBQM00004"+"-"+"Product Service"+"/"+"Good";
                }else {
                    imgServiceGood.setVisibility(View.GONE);
                }
            }
        });

        llServiceAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgServiceAverage.getVisibility()==View.GONE){
                    imgServiceAverage.setVisibility(View.VISIBLE);
                    imgServiceGood.setVisibility(View.GONE);
                    imgServicePoor.setVisibility(View.GONE);
                    ans5 ="IFBQM00004"+"-"+"Product Service"+"/"+"Average";
                }else {
                    imgServiceAverage.setVisibility(View.GONE);
                }
            }
        });

        llServicePoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgServicePoor.getVisibility()==View.GONE){
                    imgServiceAverage.setVisibility(View.GONE);
                    imgServiceGood.setVisibility(View.GONE);
                    imgServicePoor.setVisibility(View.VISIBLE);
                    ans5 ="IFBQM00004"+"-"+"Product Service"+"/"+"Poor";
                }else {
                    imgServicePoor.setVisibility(View.GONE);
                }
            }
        });

        //DESIGN

        llDesignGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgDesignGood.getVisibility()==View.GONE){
                    imgDesignPoor.setVisibility(View.GONE);
                    imgDesignAverage.setVisibility(View.GONE);
                    imgDesignGood.setVisibility(View.VISIBLE);
                    ans6 ="IFBQM00004"+"-"+"Product Design"+"/"+"Good";
                }else {
                    imgDesignGood.setVisibility(View.GONE);
                }
            }
        });

        llDesignAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgDesignAverage.getVisibility()==View.GONE){
                    imgDesignPoor.setVisibility(View.GONE);
                    imgDesignGood.setVisibility(View.GONE);
                    imgDesignAverage.setVisibility(View.VISIBLE);
                    ans6 ="IFBQM00004"+"-"+"Product Design"+"/"+"Average";
                }else {
                    imgDesignAverage.setVisibility(View.GONE);
                }
            }
        });

        llDesignPoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgDesignPoor.getVisibility()==View.GONE){
                    imgDesignPoor.setVisibility(View.VISIBLE);
                    imgDesignGood.setVisibility(View.GONE);
                    imgDesignAverage.setVisibility(View.GONE);
                    ans6 ="IFBQM00004"+"-"+"Product Design"+"/"+"Poor";
                }else {
                    imgDesignPoor.setVisibility(View.GONE);
                }
            }
        });

        //FEATURE
        llFeatureGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgFeatureGood.getVisibility()==View.GONE){
                    imgFeatureGood.setVisibility(View.VISIBLE);
                    imgFeaturePoor.setVisibility(View.GONE);
                    imgFeatureAverage.setVisibility(View.GONE);
                    ans7 ="IFBQM00004"+"-"+"Product Features"+"/"+"Good";
                }else {
                    imgFeatureGood.setVisibility(View.GONE);
                }
            }
        });
        llFeatureAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgFeatureAverage.getVisibility()==View.GONE){
                    imgFeatureGood.setVisibility(View.GONE);
                    imgFeaturePoor.setVisibility(View.GONE);
                    imgFeatureAverage.setVisibility(View.VISIBLE);
                    ans7 ="IFBQM00004"+"-"+"Product Features"+"/"+"Average";
                }else {
                    imgFeatureAverage.setVisibility(View.GONE);
                }
            }
        });

        llFeaturePoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgFeaturePoor.getVisibility()==View.GONE){
                    imgFeatureGood.setVisibility(View.GONE);
                    imgFeaturePoor.setVisibility(View.VISIBLE);
                    imgFeatureAverage.setVisibility(View.GONE);
                    ans7 ="IFBQM00004"+"-"+"Product Features"+"/"+"Poor";
                }else {
                    imgFeaturePoor.setVisibility(View.GONE);
                }
            }
        });

        //SCEHEME
        llSechemeGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgSchemeGood.getVisibility()==View.GONE){
                    imgSchemeGood.setVisibility(View.VISIBLE);
                    imgSchemeAverage.setVisibility(View.GONE);
                    imgSchemePoor.setVisibility(View.GONE);
                    ans8 ="IFBQM00004"+"-"+"Finance Scheme"+"/"+"Good";
                }else {
                    imgSchemeGood.setVisibility(View.GONE);
                }
            }
        });
        llSchemeAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgSchemeAverage.getVisibility()==View.GONE){
                    imgSchemeGood.setVisibility(View.GONE);
                    imgSchemeAverage.setVisibility(View.VISIBLE);
                    imgSchemePoor.setVisibility(View.GONE);
                    ans8 ="IFBQM00004"+"-"+"Finance Scheme"+"/"+"Average";
                }else {
                    imgSchemeAverage.setVisibility(View.GONE);
                }
            }
        });

        llSchemePoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgSchemePoor.getVisibility()==View.GONE){
                    imgSchemeGood.setVisibility(View.GONE);
                    imgSchemeAverage.setVisibility(View.GONE);
                    imgSchemePoor.setVisibility(View.VISIBLE);
                    ans8 ="IFBQM00004"+"-"+"Finance Scheme"+"/"+"Poor";
                }else {
                    imgSchemePoor.setVisibility(View.GONE);
                }
            }
        });

        etFL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etFL.getText().toString().length()>0){
                    ansfl="0"+","+"IFBQM00001"+"-"+"FL"+"/"+etFL.getText().toString();
                    ans1=ansfl;
                }

            }
        });

        etTL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTL.getText().toString().length()>0){
                    anstl="IFBQM00001"+"-"+"TL"+"/"+etTL.getText().toString();
                    ans1=ansfl+","+anstl;
                }
            }
        });

        etMW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMW.getText().toString().length()>0){
                    ansmw="IFBQM00001"+"-"+"MW"+"/"+etMW.getText().toString();
                    ans1=ansfl+","+anstl+","+ansmw;
                }
            }
        });

        etDW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etDW.getText().toString().length()>0){
                    ansdw="IFBQM00001"+"-"+"DW"+"/"+etDW.getText().toString();
                    ans1=ansfl+","+anstl+","+ansmw+","+ansdw;
                }
            }
        });

        etCD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCD.getText().toString().length()>0){
                    anscd="IFBQM00001"+"-"+"CD"+"/"+etCD.getText().toString();
                    ans1=ansfl+","+anstl+","+ansmw+","+ansdw+","+anscd;
                }
            }
        });

        etAC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAC.getText().toString().length()>0){
                    ansac="IFBQM00001"+"-"+"AC"+"/"+etAC.getText().toString();
                    ans1=ansfl+","+anstl+","+ansmw+","+ansdw+","+anscd+","+ansac;
                }
            }
        });

        etKA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etKA.getText().toString().length()>0){
                    anska="IFBQM00001"+"-"+"KA"+"/"+etKA.getText().toString();
                    ans1=ansfl+","+anstl+","+ansmw+","+ansdw+","+anscd+","+ansac+","+anska;
                }
            }
        });

        llSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ans1.equals("")){
                    if (!ans2.equals("")){
                        if (!ans3.equals("")){
                            if (!ans4.equals("")){
                                if (!ans5.equals("")){
                                    if (!ans6.equals("")){
                                        if (!ans7.equals("")){
                                            if (!ans8.equals("")){
                                                if (!ans16.equals("")){
                                                       submitanswer();
                                                }else {
                                                    Toast.makeText(getApplicationContext(),"Please enter Feedback",Toast.LENGTH_LONG).show();

                                                }

                                            }else {
                                                Toast.makeText(getApplicationContext(),"Please give customer feedback about Finance Scheme",Toast.LENGTH_LONG).show();

                                            }

                                        }else {
                                            Toast.makeText(getApplicationContext(),"Please give customer feedback about Product Features",Toast.LENGTH_LONG).show();

                                        }

                                    }else {
                                        Toast.makeText(getApplicationContext(),"Please give customer feedback about Product Design",Toast.LENGTH_LONG).show();

                                    }

                                }else {
                                    Toast.makeText(getApplicationContext(),"Please give customer feedback about Product Service",Toast.LENGTH_LONG).show();

                                }


                            }else {
                                Toast.makeText(getApplicationContext(),"Please give customer feedback about Product Quality",Toast.LENGTH_LONG).show();

                            }

                        }else {
                            Toast.makeText(getApplicationContext(),"Please attempt Question 3",Toast.LENGTH_LONG).show();

                        }

                    }else {
                        Toast.makeText(getApplicationContext(),"Please attempt Question 2",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Please attempt Question 1",Toast.LENGTH_LONG).show();
                }

            }
        });

        etRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etRemarks.getText().toString().length()>0){
                    ans16="IFBQM00006"+"-"+etRemarks.getText().toString()+"/"+"0";
                }

            }
        });

    }

    private void submitanswer(){

        ans1=ansfl+","+anstl+","+ansmw+","+ansdw+","+anscd+","+ansac+","+anska;
        answer=ans1+","+ans2+","+ans3+","+ans4+","+ans5+","+ans6+","+ans7+","+ans8+","+ans9+","+ans10+","+ans11+","+ans12+","+ans13+","+ans14+","+ans15+","+ans16;
        Log.d("ans1",answer);
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
       // super.onBackPressed();
    }
}
