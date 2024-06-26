package io.cordova.ifb.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.cordova.ifb.R;
import io.cordova.ifb.module.ModelSpinnerModel;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PostDisplayMatrixService;
import io.cordova.ifb.utility.PrefManager;
import io.cordova.ifb.utility.ValidUtils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SalesUpdateActivity extends AppCompatActivity {
    TextView tvCategory, tvModel, tvTitle, tvFname, tvLname, tvMob, tvAltMob, tvEmail, tvPinCode, tvState, tvCity, tvArea, tvHouse, tvStreet, tvLand, tvDateTitle;
    TextView tvDate, tvInvoiceValue, tvScheme;
    LinearLayout llExYes, llExYesD, llExNo, llExNoD, llSchYes, llSchYesD, llSchNo, llSchNoD, llScheme;
    Spinner spCategory, spTitle, spState, spCity, spArea, spScheme;
    SingleSpinnerSearch spModel;

    ArrayList<SpinnerItemModule> moduleCategory = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();

    ArrayList<ModelSpinnerModel> moduleModel = new ArrayList<>();
    ArrayList<String> model = new ArrayList<>();
    ArrayList<String> modelID = new ArrayList<>();

    ArrayList<SpinnerItemModule> moduleTitle = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();

    ArrayList<SpinnerItemModule> moduleState = new ArrayList<>();
    ArrayList<String> state = new ArrayList<>();

    ArrayList<SpinnerItemModule> moduleCity = new ArrayList<>();
    ArrayList<String> city = new ArrayList<>();

    ArrayList<SpinnerItemModule> moduleArea = new ArrayList<>();
    ArrayList<String> area = new ArrayList<>();

    ArrayList<SpinnerItemModule> moduleScheme = new ArrayList<>();
    ArrayList<String> scheme = new ArrayList<>();

    LinearLayout llReset;
    String stateId = "";
    String STATENAME;
    String REGIONNAME;
    TextView tvCityName;
    int y;
    String year, month, financialYear;
    String customerName;
    String modelId = "";
    String titleId = "";
    String schemeId = "0";
    String areaName = "";
    ImageView imgBack;
    LinearLayout llSubmit;
    String quantity = "1";
    String salesDate;
    String underExchange = "1";
    String remarks = "0";
    AlertDialog alerDialog1;
    String responseText;
    float invoicevalue;
    ImageView imgHome;
    String frstUppercase, lastUppercase;
    String PINCODE;
    String mrp;
    float mrpPrice;
    String monthname;
    AlertDialog alertDialog;
    LinearLayout llLoader, llMain;
    String invalidEmail;
    AlertDialog alet1;
    TextView tvStateName;
    String altmob, invalidemailresponse;
    float valuePut;
    AlertDialog alert1;
    //IMAGE PARAMETER
    private String encodedImage;
    private Uri imageUri;
    private static final int CAMERA_REQUEST = 1;
    File file, compressedImageFile, file1;
    File dFile;
    private static final int REQUEST_GALLERY_CODE = 200;
    String mobNumber, emailId, pinCode, invoiceNumber, delivaryAddress, houseNo, landMark, fName, lName, altNumber, streetname, cityName, invoiceValue;
    PrefManager prefManager;
    String categoryId = "";
    EditText etQuantity;
    EditText etFirstName, etPinCode, etRemark, etInvoiceValue, etInvoiceNumber, etLandMark, etStreetName, etHouse, etEmailId, etPhnNumber, etMobNumber, etLastName;
    int MY_SOCKET_TIMEOUT_MS = 10000;
    ImageView imgCamera;
    LinearLayout llImage;
    Uri uri;
    ImageView imgPic;
    private static final String SERVER_PATH =  AppController.APIURL+"api/";
    private PostDisplayMatrixService uploadService;
    ProgressDialog progressDialog;
    int imageTypeFlag;
    String userId, branchId, secirityCode, transNo;
    String saleFlag;
    String stringFile = "";
    LinearLayout llSerialNumber;
    List<EditText> allEds = new ArrayList<EditText>();
    ArrayList<String> serialNumberList = new ArrayList<>();
    EditText b;
    String refNo;
    String CategoryName,ModelID,TitleId,FirstName,City;
    ArrayList<String>titleID=new ArrayList<>();

    ArrayList<KeyPairBoolData> keyModelList = new ArrayList<>();
    Spinner spDisplay,spCSD;
    String displaySoldID,csdSales;

    ArrayList<SpinnerItemModule> moduleDisplaySold = new ArrayList<>();
    ArrayList<String> displaySold = new ArrayList<>();
    String contactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_manage);
        initialize();
        getDetails();
        onClick();
    }

    private void initialize() {
        prefManager = new PrefManager(SalesUpdateActivity.this);
        String next = "<font color='#EE0000'>*</font>";

        tvCategory = (TextView) findViewById(R.id.tvCategorySale);
        String category = "CATEGORY:";
        tvCategory.setText(Html.fromHtml(category + next));

        tvModel = (TextView) findViewById(R.id.tvModel);
        String model = "MODEL";
        tvModel.setText(Html.fromHtml(model + next));

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        String title = "TITLE";
        tvTitle.setText(Html.fromHtml(title + next));

        tvFname = (TextView) findViewById(R.id.tvFname);
        String fname = "FIRST NAME:";
        tvFname.setText(Html.fromHtml(fname + next));

        tvLname = (TextView) findViewById(R.id.tvLname);
        String lname = "LAST NAME:";
        tvLname.setText(Html.fromHtml(lname + next));

        tvMob = (TextView) findViewById(R.id.tvMob);
        String mob = "10 DIGITS MOBILE NUMBER:";
        tvMob.setText(Html.fromHtml(mob + next));

        tvAltMob = (TextView) findViewById(R.id.tvAltMob);
        String altmob = "ALTERNATIVE NUMBER:";
        tvAltMob.setText(altmob);

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        String email = "EMAIL ";
        tvEmail.setText(Html.fromHtml(email + next));

        tvPinCode = (TextView) findViewById(R.id.tvPinCode);
        String pin = "DELIVERY PIN CODE:";
        tvPinCode.setText(Html.fromHtml(pin + next));

        tvState = (TextView) findViewById(R.id.tvState);
        String state = "STATE";
        tvState.setText(Html.fromHtml(state + next));

        tvCity = (TextView) findViewById(R.id.tvCity);
        String city = "CITY ";
        tvCity.setText(Html.fromHtml(city + next));

        tvArea = (TextView) findViewById(R.id.tvArea);
        String area = "AREA ";
        tvArea.setText(Html.fromHtml(area + next));

        tvHouse = (TextView) findViewById(R.id.tvHouse);
        String house = "HOUSE/FLAT/PLOT NO ";
        tvHouse.setText(Html.fromHtml(house + next));

        tvStreet = (TextView) findViewById(R.id.tvStreet);
        String street = "STREET NAME ";
        tvStreet.setText(Html.fromHtml(street + next));

        tvLand = (TextView) findViewById(R.id.tvLand);
        String land = "LANDMARK ";
        tvLand.setText(Html.fromHtml(land + next));

        tvDateTitle = (TextView) findViewById(R.id.tvDateTitle);
        String date = "INVOICE DATE ";
        tvDateTitle.setText(Html.fromHtml(date + next));

        tvDate = (TextView) findViewById(R.id.tvDate);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        salesDate = df.format(c);
        tvDate.setText(salesDate);

        tvInvoiceValue = (TextView) findViewById(R.id.tvInvoiceValue);
        String voice = "INVOICE VALUE ";
        tvInvoiceValue.setText(Html.fromHtml(voice + next));

        llExYes = (LinearLayout) findViewById(R.id.llExYes);
        llExYesD = (LinearLayout) findViewById(R.id.llExYesD);
        llExNo = (LinearLayout) findViewById(R.id.llExNo);
        llExNoD = (LinearLayout) findViewById(R.id.llExNoD);

        llSchYes = (LinearLayout) findViewById(R.id.llSchYes);
        llSchYesD = (LinearLayout) findViewById(R.id.llSchYesD);
        llSchNo = (LinearLayout) findViewById(R.id.llSchNo);
        llSchNoD = (LinearLayout) findViewById(R.id.llSchNoD);

        llScheme = (LinearLayout) findViewById(R.id.llScheme);

        tvScheme = (TextView) findViewById(R.id.tvScheme);
        String scheme = "SELECT FINANCE SCHEME ";
        tvScheme.setText(Html.fromHtml(scheme + next));

        spCategory = (Spinner) findViewById(R.id.spCategory);
        spModel = (SingleSpinnerSearch) findViewById(R.id.spModel);
        spTitle = (Spinner) findViewById(R.id.spTitle);
        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spCity);
        spArea = (Spinner) findViewById(R.id.spArea);
        spScheme = (Spinner) findViewById(R.id.spScheme);

        etPinCode = (EditText) findViewById(R.id.etPinCode);
        etRemark = (EditText) findViewById(R.id.etRemark);
        etInvoiceValue = (EditText) findViewById(R.id.etInvoiceValue);
        etInvoiceNumber = (EditText) findViewById(R.id.etInvoiceNumber);
        etLandMark = (EditText) findViewById(R.id.etLandMark);
        etStreetName = (EditText) findViewById(R.id.etStreetName);
        etHouse = (EditText) findViewById(R.id.etHouse);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        etPhnNumber = (EditText) findViewById(R.id.etPhnNumber);
        etMobNumber = (EditText) findViewById(R.id.etMobNumber);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etFirstName = (EditText) findViewById(R.id.etFirstName);

        tvCityName = (TextView) findViewById(R.id.tvCityName);

        llReset = (LinearLayout) findViewById(R.id.llReset);

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);


        y = Calendar.getInstance().get(Calendar.YEAR);
        year = String.valueOf(y);
        Log.d("year", year);

        int m = Calendar.getInstance().get(Calendar.MONTH) + 1;
        Log.d("month", String.valueOf(m));
        if (m == 1) {
            monthname = "January";
        } else if (m == 2) {
            monthname = "February";
        } else if (m == 3) {
            monthname = "March";
        } else if (m == 4) {
            monthname = "April";
        } else if (m == 5) {
            monthname = "May";
        } else if (m == 6) {
            monthname = "June";
        } else if (m == 7) {
            monthname = "July";
        } else if (m == 8) {
            monthname = "August";
        } else if (m == 9) {
            monthname = "September";
        } else if (m == 10) {
            monthname = "October";
        } else if (m == 11) {
            monthname = "November";
        } else if (m == 12) {
            monthname = "December";
        }

        if (monthname.equals("January")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else if (monthname.equals("February")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else if (monthname.equals("March")) {
            int futureyear = y - 1;
            financialYear = futureyear + "-" + year;
        } else {
            int futureyear = y + 1;
            financialYear = year + "-" + futureyear;
        }


        llSubmit = (LinearLayout) findViewById(R.id.llSubmit);

        etQuantity = (EditText) findViewById(R.id.etQuantity);
        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        tvStateName = (TextView) findViewById(R.id.tvStateName);
        imgCamera = (ImageView) findViewById(R.id.imgCamera);
        imgPic = (ImageView) findViewById(R.id.imgPic);
        llImage = (LinearLayout) findViewById(R.id.llImage);
        if (prefManager.getInvoiceFlag().equals("1")) {
            llImage.setVisibility(View.VISIBLE);
        } else {
            llImage.setVisibility(View.GONE);
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(660, TimeUnit.SECONDS)
                .connectTimeout(660, TimeUnit.SECONDS)
                .build();

        // Change base URL to your upload server URL.
        uploadService = (PostDisplayMatrixService) new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PostDisplayMatrixService.class);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        llSerialNumber = (LinearLayout) findViewById(R.id.llSerialNumber);
        refNo = getIntent().getStringExtra("refNo");

        displaySold.add("NO");
        displaySold.add("YES");
        moduleDisplaySold.add(new SpinnerItemModule("NO","N"));
        moduleDisplaySold.add(new SpinnerItemModule("YES","Y"));
        spDisplay=(Spinner)findViewById(R.id.spDisplay);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (SalesUpdateActivity.this, android.R.layout.simple_spinner_item,
                        displaySold); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDisplay.setAdapter(spinnerArrayAdapter);

        spCSD=(Spinner)findViewById(R.id.spCSD);

        ArrayAdapter<String> spinnerCSDArrayAdapter = new ArrayAdapter<String>
                (SalesUpdateActivity.this, android.R.layout.simple_spinner_item,
                        displaySold); //selected item will look like a spinner set from XML
        spinnerCSDArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCSD.setAdapter(spinnerCSDArrayAdapter);




    }

    private void onClick() {
        spDisplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                displaySoldID=moduleDisplaySold.get(i).getItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCSD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                csdSales=moduleDisplaySold.get(i).getItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        llExYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llExYesD.setVisibility(View.VISIBLE);
                llExNoD.setVisibility(View.GONE);
                underExchange = "1";
            }
        });

        llExNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llExYesD.setVisibility(View.GONE);
                llExNoD.setVisibility(View.VISIBLE);
                underExchange = "0";
            }
        });


        llSchYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSchYesD.setVisibility(View.VISIBLE);
                llSchNoD.setVisibility(View.GONE);
                llScheme.setVisibility(View.VISIBLE);
                setScheme();
            }
        });

        llSchNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSchYesD.setVisibility(View.GONE);
                llSchNoD.setVisibility(View.VISIBLE);
                llScheme.setVisibility(View.GONE);
            }
        });

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = "";
                if (position > 0) {
                    categoryId = moduleCategory.get(position).getItemId();
                    Log.d("categoryId", categoryId);
                    setModel(categoryId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etFirstName.getText().toString().length() > 0) {
                    frstUppercase = etFirstName.getText().toString().toUpperCase();
                }

            }
        });

        etPinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPinCode.getText().toString().length() == 6) {
                    String pincode = etPinCode.getText().toString();
                    pincodecheck(pincode);
                    if (pincode.equals("491001")) {
                        spState.setSelection(6);
                    }

                } else {

                }

            }
        });


        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etQuantity.getText().toString().length() > 0) {
                    quantity = etQuantity.getText().toString();
                    int p = Integer.parseInt(quantity);
                    ScrollView sv = new ScrollView(SalesUpdateActivity.this);

                    sv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    LinearLayout ll = new LinearLayout(SalesUpdateActivity.this);
                    ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    ll.setOrientation(LinearLayout.VERTICAL);
                    sv.addView(ll);
                    for (int i = 0; i < p; i++) {
                        b = new EditText(SalesUpdateActivity.this);
                        b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        allEds.add(b);
                        b.setHint("please enter serial number ");
                        b.setTextSize(14);
                        b.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                        b.setId(i);
                        b.setSingleLine();
                        b.setInputType(InputType.TYPE_CLASS_NUMBER);
                        ll.addView(b);
                    }

                    llSerialNumber.addView(sv);
                }

            }
        });


        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etLastName.getText().toString().length() > 0) {
                    customerName = etFirstName.getText().toString() + etLastName.getText().toString();
                    Log.d("cusnamr", customerName);
                    lastUppercase = etLastName.getText().toString().toUpperCase();
                }

            }
        });

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateId = moduleState.get(position).getItemId();
                Log.d("stateId", stateId);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spModel.setItems(keyModelList, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {

                        modelId = items.get(i).getId();
                        Log.d("modelId", modelId);
                        mrp = items.get(i).getMrp();
                        Log.d("mrp", mrp);
                        mrpPrice = Float.parseFloat(mrp);
                        valuePut = mrpPrice / 2;
                        Log.d("valueput", String.valueOf(valuePut));


                    }
                }
            }


        });

        spTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                titleId = "";
                if (position > 0) {
                    titleId = moduleTitle.get(position).getItemId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaName = moduleArea.get(position).getItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etRemark.getText().toString().length() > 1) {
                    remarks = etRemark.getText().toString();
                }

            }
        });


        spScheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                schemeId = moduleScheme.get(position).getItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etInvoiceValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etInvoiceValue.getText().toString().length() > 0) {
                    String invoice = etInvoiceValue.getText().toString();
                    invoicevalue = Float.parseFloat(invoice);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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
                Intent intent = new Intent(SalesUpdateActivity.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        etEmailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etEmailId.getText().toString().length() > 0) {
                    invalidEmail = etEmailId.getText().toString().toLowerCase();
                }

            }
        });
        etPhnNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPhnNumber.getText().toString().length() > 0) {
                    altmob = etPhnNumber.getText().toString();
                } else {
                    altmob = "0000000000";
                }

            }
        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialog();
            }
        });

        llSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!categoryId.equals("")) {
                    if (!modelId.equals("")) {
                        if (!titleId.equals("")) {
                            if (etFirstName.getText().toString().length() > 0) {
                                if (etLastName.getText().toString().length() > 0) {
                                    if (etMobNumber.getText().toString().length() > 9) {

                                        if (!etPhnNumber.getText().toString().equals(etMobNumber.getText().toString())) {
                                            if (etEmailId.getText().toString().length() > 0) {
                                                if (ValidUtils.isValidEmail(etEmailId.getText().toString())) {
                                                    if (etPinCode.getText().toString().length() > 5) {
                                                        if (!REGIONNAME.equals("null")) {
                                                            if (etHouse.getText().toString().length() > 0) {
                                                                if (etStreetName.getText().toString().length() > 0) {
                                                                    if (etLandMark.getText().toString().length() > 0) {
                                                                        if (etInvoiceValue.getText().toString().length() > 0) {
                                                                            if (etQuantity.getText().toString().equals("1") || etQuantity.getText().toString().equals("2") || etQuantity.getText().toString().equals("3") || etQuantity.getText().toString().equals("4") || etQuantity.getText().toString().equals("5") || etQuantity.getText().toString().equals("")) {
                                                                                if (invoicevalue < mrpPrice || invoicevalue == mrpPrice) {
                                                                                    if (!frstUppercase.equals(lastUppercase)) {
                                                                                        if (invoicevalue > valuePut || invoicevalue == valuePut) {
                                                                                            if (!etMobNumber.getText().toString().equals("0000000000")) {
                                                                                                if (!etMobNumber.getText().toString().equals("1111111111")) {
                                                                                                    if (!etMobNumber.getText().toString().equals("2222222222")) {
                                                                                                        if (!etMobNumber.getText().toString().equals("3333333333")) {
                                                                                                            if (!etMobNumber.getText().toString().equals("4444444444")) {
                                                                                                                if (!etMobNumber.getText().toString().contains("5555555555")) {
                                                                                                                    if (!etMobNumber.getText().toString().contains("6666666666")) {
                                                                                                                        if (!etMobNumber.getText().toString().contains("7777777777")) {
                                                                                                                            if (!etMobNumber.getText().toString().contains("8888888888")) {
                                                                                                                                if (!etMobNumber.getText().toString().contains("9999999999")) {
                                                                                                                                    if (etQuantity.getText().toString().length() > 0) {
                                                                                                                                        if (etQuantity.getText().toString().equals("2") || etQuantity.getText().toString().equals("3") || etQuantity.getText().toString().equals("4") || etQuantity.getText().toString().equals("5")) {


                                                                                                                                            quatityalert();
                                                                                                                                        } else {
                                                                                                                                            emailcheck1();
                                                                                                                                        }


                                                                                                                                    } else {
                                                                                                                                        etQuantity.setError("Please enter quantity");
                                                                                                                                        etQuantity.requestFocus();
                                                                                                                                    }


                                                                                                                                } else {
                                                                                                                                    etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                                    etMobNumber.requestFocus();
                                                                                                                                }

                                                                                                                            } else {
                                                                                                                                etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                                etMobNumber.requestFocus();
                                                                                                                            }

                                                                                                                        } else {
                                                                                                                            etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                            etMobNumber.requestFocus();
                                                                                                                        }

                                                                                                                    } else {
                                                                                                                        etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                        etMobNumber.requestFocus();
                                                                                                                    }

                                                                                                                } else {
                                                                                                                    etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                    etMobNumber.requestFocus();
                                                                                                                }

                                                                                                            } else {
                                                                                                                etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                etMobNumber.requestFocus();
                                                                                                            }

                                                                                                        } else {
                                                                                                            etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                            etMobNumber.requestFocus();
                                                                                                        }

                                                                                                    } else {
                                                                                                        etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                        etMobNumber.requestFocus();
                                                                                                    }

                                                                                                } else {
                                                                                                    etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                    etMobNumber.requestFocus();
                                                                                                }

                                                                                            } else {
                                                                                                etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                etMobNumber.requestFocus();
                                                                                            }


                                                                                        } else {
                                                                                            Toast.makeText(getApplicationContext(), "Invoice value should be greater than 50% of MRP of product", Toast.LENGTH_LONG).show();

                                                                                        }


                                                                                    } else {
                                                                                        Toast.makeText(getApplicationContext(), "First name and Last name Should be diiferent", Toast.LENGTH_LONG).show();
                                                                                    }


                                                                                } else {
                                                                                    Toast.makeText(getApplicationContext(), "Invoice value should not be greater than  MRP price", Toast.LENGTH_LONG).show();
                                                                                }

                                                                            } else {
                                                                                Toast.makeText(getApplicationContext(), "Please enter valid Quantity", Toast.LENGTH_LONG).show();

                                                                            }

                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), "Please enter Invoice value", Toast.LENGTH_LONG).show();

                                                                        }

                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(), "Please enter Land Mark", Toast.LENGTH_LONG).show();
                                                                    }

                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Please enter Street Name", Toast.LENGTH_LONG).show();

                                                                }

                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Please enter House/Flat/Plot No", Toast.LENGTH_LONG).show();

                                                            }

                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Please enter Valid Pincode", Toast.LENGTH_LONG).show();

                                                        }

                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Please enter Valid Pincode", Toast.LENGTH_LONG).show();

                                                    }

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Please enter Valid Email Id", Toast.LENGTH_LONG).show();

                                                }


                                            } else {
                                                Toast.makeText(getApplicationContext(), "Please enter Email Id", Toast.LENGTH_LONG).show();

                                            }

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Mobile Number and Alternative Number should be different", Toast.LENGTH_LONG).show();

                                        }


                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please enter Mobile Number", Toast.LENGTH_LONG).show();

                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "Please enter Last Name", Toast.LENGTH_LONG).show();

                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Please enter First Name", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Please select Title", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Please select Model", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please select Category", Toast.LENGTH_LONG).show();
                }
            }
        });


        llReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
                etFirstName.setText("");
                etLastName.setText("");
                etMobNumber.setText("");
                etPhnNumber.setText("");
                etHouse.setText("");
                etLandMark.setText("");
                etRemark.setText("");
                etPinCode.setText("");
                etInvoiceNumber.setText("");
                etInvoiceValue.setText("");
                etEmailId.setText("");
                spArea.setSelection(0);
                spState.setSelection(0);
                spCity.setSelection(0);
                spCategory.setSelection(0);
                spModel.setSelection(0);
                spTitle.setSelection(0);
                if (llExYesD.getVisibility() == View.VISIBLE) {
                    llExYesD.setVisibility(View.GONE);
                }

                if (llExNoD.getVisibility() == View.VISIBLE) {
                    llExNoD.setVisibility(View.GONE);
                }

                if (llSchYesD.getVisibility() == View.VISIBLE) {
                    llSchYesD.setVisibility(View.GONE);
                }

                if (llSchNoD.getVisibility() == View.VISIBLE) {
                    llSchNoD.setVisibility(View.GONE);
                }

                if (llScheme.getVisibility() == View.VISIBLE) {
                    llScheme.setVisibility(View.GONE);
                }

            }
        });
    }

    private void showDateDialog() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                StringBuffer strBuf = new StringBuffer();
                strBuf.append("Select date is ");
                strBuf.append(year);
                strBuf.append("-");
                strBuf.append(month + 1);
                strBuf.append("-");
                strBuf.append(dayOfMonth);


            }
        };

        // Get current year, month and day.
        Calendar now = Calendar.getInstance();
        final int year2 = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(SalesUpdateActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                String sdate = (m + 1) + "/" + d + "/" + y;
                int s = (m + 1) + d + y;

                int month = (m + 1);
                if (month == 1) {
                    monthname = "Jan";

                } else if (month == 2) {
                    monthname = "Feb";
                } else if (month == 3) {
                    monthname = "March";
                } else if (month == 4) {
                    monthname = "April";
                } else if (month == 5) {
                    monthname = "May";
                } else if (month == 6) {
                    monthname = "June";
                } else if (month == 7) {
                    monthname = "July";
                } else if (month == 8) {
                    monthname = "August";
                } else if (month == 9) {
                    monthname = "Sep";
                } else if (month == 10) {
                    monthname = "Oct";
                } else if (month == 11) {
                    monthname = "Nov";
                } else if (month == 12) {
                    monthname = "Dec";
                }

                salesDate = d + "-" + monthname + "-" + y;

                tvDate.setText(salesDate);

                //  pref.saveDOJ(sdate);


            }
        }, year2, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clockicon);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis() - 1000));

        // Popup the dialog.

        dialog.show();
    }

    private void getDetails() {
        Log.d("hitr", "1");

        String surl = AppController.APIURL+"api/get_EmployeeSalesRefDetails?ReferenceNo=" + refNo + "&UserID=" + prefManager.getUserId() + "&FinancialYear=" + financialYear + "&Month=" + monthname + "&Operation=1&SubOperation=1&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        llLoader.setVisibility(View.VISIBLE);
                        llMain.setVisibility(View.GONE);

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");

                                JSONObject obj = responseData.getJSONObject(0);
                                CategoryName = obj.optString("CategoryName");
                                City = obj.optString("City");
                                ModelID = obj.optString("ModelID");
                                TitleId = obj.optString("TitleId");
                                FirstName=obj.optString("FirstName");
                                etFirstName.setText(FirstName);
                                String LastName=obj.optString("LastName");
                                etLastName.setText(LastName);
                                String CustomerPhNo=obj.optString("CustomerPhNo");
                                etMobNumber.setText(CustomerPhNo);
                                String CustomerAlternateNumber=obj.optString("CustomerAlternateNumber");
                                etPhnNumber.setText(CustomerAlternateNumber);
                                String CustomerEmail=obj.optString("CustomerEmail");
                                etEmailId.setText(CustomerEmail);
                                String CustomerPinCode=obj.optString("CustomerPinCode");
                                etPinCode.setText(CustomerPinCode);
                                String HouseNo=obj.optString("HouseNo");
                                etHouse.setText(HouseNo);
                                String StreetName=obj.optString("StreetName");
                                etStreetName.setText(StreetName);
                                String Landmark=obj.optString("Landmark");
                                etLandMark.setText(Landmark);
                                String InvoiceNo=obj.optString("InvoiceNo");
                                etInvoiceNumber.setText(InvoiceNo);
                                String InvoiceValue=obj.optString("InvoiceValue");
                                etInvoiceValue.setText(InvoiceValue);
                                String Remarks=obj.optString("Remarks");
                                etRemark.setText(Remarks);
                                String Quantity=obj.optString("Quantity");
                                etQuantity.setText(Quantity);
                                String UnderExchange=obj.optString("UnderExchange");
                                underExchange=UnderExchange;
                                if (underExchange.equals("1")){
                                    llExYesD.setVisibility(View.VISIBLE);
                                    llExNoD.setVisibility(View.GONE);
                                }else {
                                    llExYesD.setVisibility(View.GONE);
                                    llExNoD.setVisibility(View.VISIBLE);
                                }

                                setCategory();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setCategory() {
        Log.d("hitr", "1");

        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=4&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        llLoader.setVisibility(View.VISIBLE);
                        llMain.setVisibility(View.GONE);
                        category.clear();
                        moduleCategory.clear();
                        category.add("Please select");
                        moduleCategory.add(new SpinnerItemModule("0", "0"));

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
                                    category.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleCategory.add(itemModule);

                                }

                                setTitle();


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (SalesUpdateActivity.this, android.R.layout.simple_spinner_item,
                                                category); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCategory.setAdapter(spinnerArrayAdapter);
                                int pos=category.indexOf(CategoryName);
                                spCategory.setSelection(pos);




                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setModel(String categoryId) {
        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=18&ID=" + categoryId + "&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
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
                        model.clear();
                        moduleModel.clear();
                        model.add("Please select");
                        moduleModel.add(new ModelSpinnerModel("0", "0", "0"));
                        modelID.clear();
                        modelID.add("0");

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
                                    String MRP = obj.optString("MRP");
                                    model.add(value);
                                    modelID.add(id);

                                    ModelSpinnerModel itemModule = new ModelSpinnerModel(value, id, MRP);
                                    moduleModel.add(itemModule);

                                }

                                for (int j = 0; j < moduleModel.size(); j++) {
                                    KeyPairBoolData h = new KeyPairBoolData();
                                    h.setName(moduleModel.get(j).getValue());
                                    h.setId(moduleModel.get(j).getId());
                                    h.setMrp(moduleModel.get(j).getMrp());
                                    h.setSelected(false);
                                    keyModelList.add(h);

                                }

                                


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setTitle() {
        Log.d("hitr", "2");
        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=42&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseTitle", response);
                        llLoader.setVisibility(View.VISIBLE);
                        llMain.setVisibility(View.GONE);
                        title.clear();
                        moduleTitle.clear();
                        title.add("Please select");
                        moduleTitle.add(new SpinnerItemModule("0", "0"));
                        titleID.clear();
                        titleID.add("0");


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
                                    title.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleTitle.add(itemModule);
                                    titleID.add(id);

                                }
                                setState();


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (SalesUpdateActivity.this, android.R.layout.simple_spinner_item,
                                                title); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spTitle.setAdapter(spinnerArrayAdapter);
                                int pos=titleID.indexOf(TitleId);
                                spTitle.setSelection(pos);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "title");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }


    private void pincodecheck(final String pincode) {
        Log.d("hitr", "6");
        String surl = "https://cloud.geniusconsultant.com/GeniusPinCodeApi/api/PinCode?id=" + pincode;
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responsepincode", response);
                        progressBar.dismiss();


                        try {
                            JSONArray job1 = new JSONArray(response);

                            for (int i = 0; i < job1.length(); i++) {
                                JSONObject obj = job1.getJSONObject(i);
                                STATENAME = obj.getString("STATENAME");
                                Log.d("statename", STATENAME);
                                PINCODE = obj.optString("PINCODE");
                                REGIONNAME = obj.optString("REGIONNAME");


                            }

                            int index = state.indexOf(STATENAME);
                            Log.d("inderc", String.valueOf(index));
                            spState.setSelection(index);
                            tvCityName.setVisibility(View.VISIBLE);
                            spCity.setVisibility(View.GONE);
                            tvCityName.setText(REGIONNAME);
                            spState.setEnabled(false);
                            setArea(pincode);

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setArea(String pincode) {
        Log.d("hhjjk", "kkkk");
        String surl = "https://cloud.geniusconsultant.com/GeniusPinCodeApi/api/PinCode?id=" + pincode;
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responsearea", response);
                        progressBar.dismiss();
                        area.clear();
                        moduleArea.clear();


                        try {
                            JSONArray job1 = new JSONArray(response);

                            for (int i = 0; i < job1.length(); i++) {
                                JSONObject obj = job1.getJSONObject(i);
                                String OFFICENAME = obj.optString("OFFICENAME");
                                String PINCODE = obj.optString("PINCODE");
                                area.add(OFFICENAME);

                                SpinnerItemModule itemModule = new SpinnerItemModule(OFFICENAME, PINCODE);
                                moduleArea.add(itemModule);

                            }


                            spArea.setVisibility(View.VISIBLE);


                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                    (SalesUpdateActivity.this, android.R.layout.simple_spinner_item,
                                            area); //selected item will look like a spinner set from XML
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spArea.setAdapter(spinnerArrayAdapter);

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setScheme() {
        Log.d("hitr", "5");
        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=35&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseScheme", response);
                        progressBar.dismiss();
                        scheme.clear();
                        moduleScheme.clear();
                        scheme.add("Please select");
                        moduleScheme.add(new SpinnerItemModule("0", "0"));

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
                                    scheme.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleScheme.add(itemModule);

                                }

                                //setTitle();


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (SalesUpdateActivity.this, android.R.layout.simple_spinner_item,
                                                scheme); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spScheme.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setState() {
        Log.d("hitr", "3");
        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=2&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("stateinput", surl);
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responsestate", response);
                        llLoader.setVisibility(View.VISIBLE);
                        llMain.setVisibility(View.GONE);
                        state.clear();
                        moduleState.clear();


                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("responseState", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String value = obj.optString("value");
                                    String id = obj.optString("id");
                                    state.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleState.add(itemModule);

                                }
                                setCity();

                                spState.setVisibility(View.VISIBLE);
                                spCity.setVisibility(View.VISIBLE);


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (SalesUpdateActivity.this, android.R.layout.simple_spinner_item,
                                                state); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spState.setAdapter(spinnerArrayAdapter);
                                spState.setSelection(42);


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "state");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setCity() {
        Log.d("hitr", "4");
        tvCityName.setVisibility(View.GONE);
        String surl = AppController.APIURL+"api/CommonDDL?ModuleNo=14&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseCategory", response);
                        llLoader.setVisibility(View.GONE);
                        llMain.setVisibility(View.VISIBLE);
                        city.clear();
                        moduleCity.clear();


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
                                    city.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleCity.add(itemModule);

                                }


                                spCity.setVisibility(View.VISIBLE);
                                tvCityName.setVisibility(View.GONE);


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (SalesUpdateActivity.this, android.R.layout.simple_spinner_item,
                                                city); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCity.setAdapter(spinnerArrayAdapter);
                                spCity.setSelection(2200);
                                int pos=city.indexOf(City);
                                spCity.setSelection(pos);


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.VISIBLE);
                llMain.setVisibility(View.GONE);

                Toast.makeText(SalesUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "city");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    private void setSalesEntry() {
        String surl = AppController.APIURL+"api/post_SalesEntry?TransNo=0&AEMEmployeeID=" + prefManager.getUserId() + "&_SalesDate=" + salesDate + "&FinancialYear=" + financialYear + "&Month=" + monthname + "&CategoryID=" + categoryId + "&Quantity=" + quantity + "&xmldata=0&UserID=" + prefManager.getUserId() + "&BranchID=" + prefManager.getBranchId() + "&ModelID=" + modelId + "&CustomerName=" + customerName.replaceAll("\\s+", "-") + "&CustomerPhNo=" + etMobNumber.getText().toString() + "&CustomerPinCode=" + etPinCode.getText().toString() + "&CustomerEmail=" + etEmailId.getText().toString() + "&InvoiceNo=" + etInvoiceNumber.getText().toString() + "&FinanceScheme=" + schemeId + "&DeliveryAddress=" + etHouse.getText().toString() + "-" + etLandMark.getText().toString().replaceAll("\\s+", "-") + "&FirstName=" + etFirstName.getText().toString().replaceAll("\\s+", "-") + "&LastName=" + etLastName.getText().toString().replaceAll("\\s+", "-") + "&CustomerAlternateNumber=" + altmob + "&HouseNo=" + etHouse.getText().toString().replaceAll("\\s+", "-") + "&StreetName=" + etStreetName.getText().toString().replaceAll("\\s+", "-") + "&Landmark=" + etLandMark.getText().toString().replaceAll("\\s+", "-") + "&Title=" + titleId + "&StateID=" + stateId + "&City=" + tvCityName.getText().toString().replaceAll("\\s+", "-") + "&InvoiceValue=" + etInvoiceValue.getText().toString() + "&Remarks=" + remarks + "&UnderExchange=" + underExchange + "&SalesEntryFlag=-1&Area=" + areaName + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("salesentry", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseCategory", response);
                        progressBar.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();

                            }


                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(SalesUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }


    private void successAlert(String text) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SalesUpdateActivity.this, R.style.CustomDialogNew);
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
                Intent intent = new Intent(SalesUpdateActivity.this, SalesReportActivity.class);
                startActivity(intent);
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


    private void quatityalert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SalesUpdateActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_alerts, null);
        dialogBuilder.setView(dialogView);

        Button btnYes = (Button) dialogView.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                emailcheck1();

            }
        });

        Button btnNo = (Button) dialogView.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                etQuantity.setError("Check details");
                etQuantity.requestFocus();
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog.show();
    }

    private void invalidemailalert(String text) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SalesUpdateActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_inavalid_email, null);
        dialogBuilder.setView(dialogView);
        TextView tvInValidEmail = (TextView) dialogView.findViewById(R.id.tvInValidEmail);
        tvInValidEmail.setText(text);

        Button btnYes = (Button) dialogView.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alet1.dismiss();

            }
        });


        alet1 = dialogBuilder.create();
        alet1.setCancelable(false);
        Window window = alet1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alet1.show();
    }


    private void emailcheck1() {
        String surl = AppController.APIURL+"api/CheckInvalidEmailID?EmailID=" + etEmailId.getText().toString();
        Log.d("emailcheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseCategory", response);
                        progressBar.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean emailstatus2 = job1.optBoolean("responseStatus");
                            if (emailstatus2) {
                                // ssaleFunction();
                                mobNumbercheck();
                            } else {
                                invalidemailalert(responseText);
                            }

                            //boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(SalesUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }

    private void mobNumbercheck() {
        String surl = AppController.APIURL+"api/CheckInvalidMobileNo?MobileNo=" + etMobNumber.getText().toString();
        Log.d("phnnumbercheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseCategory", response);
                        progressBar.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean emailstatus2 = job1.optBoolean("responseStatus");
                            if (emailstatus2) {

                                ssaleFunction();

                            } else {
                                invalidemailalert(responseText);
                            }

                            //boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(SalesUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }

    private void altNumbercheck() {
        String surl = AppController.APIURL+"api/CheckInvalidMobileNo?MobileNo=" + etPhnNumber.getText().toString();
        Log.d("phnnumbercheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseCategory", response);
                        progressBar.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean emailstatus2 = job1.optBoolean("responseStatus");
                            if (emailstatus2) {
                                ssaleFunction();
                            } else {
                                invalidemailalert(responseText);
                            }

                            //boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(SalesUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(SalesUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SalesUpdateActivity.this);
        requestQueue.add(stringRequest);

    }

    private void cameraDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SalesUpdateActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.camera_dialog, null);
        dialogBuilder.setView(dialogView);
        LinearLayout llCamera = (LinearLayout) dialogView.findViewById(R.id.llCamera);
        LinearLayout llGallery = (LinearLayout) dialogView.findViewById(R.id.llGallery);
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();
            }
        });

        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent();

            }
        });


        alert1 = dialogBuilder.create();
        alert1.setCancelable(false);
        Window window = alert1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alert1.show();
    }

    private void galleryIntent() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }


    private void cameraIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Profile Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:

                if (resultCode == Activity.RESULT_OK) {
                    try {
                        try {
                            String imageurl = /*"file://" +*/ getRealPathFromURI(imageUri);
                            file = new File(imageurl);
                            long length = file.length();
                            double m = length / 1024.0;
                            Log.d("size", String.valueOf(m));

                            BitmapFactory.Options o = new BitmapFactory.Options();
                            o.inSampleSize = 2;
                            Bitmap bm = cropToSquare(BitmapFactory.decodeFile(imageurl, o));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                            Log.d("images", encodedImage);
                            imgPic.setImageBitmap(bm);
                            alert1.dismiss();
                            imageTypeFlag = 1;
                            String contentType = "image/jpg";
                            String[] brkDown = imageurl.split("/");
                            String name = brkDown[5];
                            stringFile = name + "_" + encodedImage + "_" + contentType;


                            // _pref.saveImage(encodedImage);
                            //saveImage(encodedImage);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }

                }
                break;
            case REQUEST_GALLERY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    InputStream imageStream = null;
                    try {
                        try {
                            uri = data.getData();
                            String filePath = getRealPathFromURIPath(uri, SalesUpdateActivity.this);
                            file = new File(filePath);
                            //  Log.d(TAG, "filePath=" + filePath);
                            imageStream = getContentResolver().openInputStream(uri);
                            Bitmap bm = cropToSquare(BitmapFactory.decodeStream(imageStream));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                            imgPic.setImageBitmap(bm);
                            alert1.dismiss();
                            imageTypeFlag = 2;
                            String contentType = "image/jpg";
                            String[] brkDown = filePath.split("/");
                            String name = brkDown[5];
                            stringFile = name + "_" + encodedImage + "_" + contentType;


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }

                }
                break;


        }


    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public static Bitmap cropToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width) ? height - (height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0) ? 0 : cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0) ? 0 : cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }


    private void postSaleWithImage(String serailNumber) {
        llSubmit.setEnabled(false);
        userId = prefManager.getUserId();
        secirityCode = prefManager.getSecurityCode();
        branchId = prefManager.getBranchId();
        mobNumber = etMobNumber.getText().toString();
        emailId = etEmailId.getText().toString();
        pinCode = etPinCode.getText().toString();
        invoiceNumber = etInvoiceNumber.getText().toString();
        delivaryAddress = etHouse.getText().toString() + "-" + etLandMark.getText().toString() + "-" + etStreetName.getText().toString();
        houseNo = etHouse.getText().toString();
        landMark = etLandMark.getText().toString();
        fName = etFirstName.getText().toString();
        lName = etLastName.getText().toString();
        altNumber = etPhnNumber.getText().toString();
        streetname = etStreetName.getText().toString();
        cityName = tvCityName.getText().toString();
        invoiceValue = etInvoiceValue.getText().toString();
        remarks = etRemark.getText().toString();
        transNo = "0";
        saleFlag = "1";
        final ProgressDialog pd = new ProgressDialog(SalesUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        AndroidNetworking.upload(AppController.APIURL+"api/post_EmployeeSalesManageV1")
                .addMultipartParameter("TransNo", transNo)
                .addMultipartParameter("ReferenceNo", refNo)
                .addMultipartParameter("AEMEmployeeID", userId)
                .addMultipartParameter("SalesDate", salesDate)
                .addMultipartParameter("FinancialYear", financialYear)
                .addMultipartParameter("Month", monthname)
                .addMultipartParameter("CategoryID", categoryId)
                .addMultipartParameter("Quantity", quantity)
                .addMultipartParameter("UserID", userId)
                .addMultipartParameter("BranchID", branchId)
                .addMultipartParameter("ModelID", modelId)
                .addMultipartParameter("CustomerName", customerName)
                .addMultipartParameter("CustomerPhNo", mobNumber)
                .addMultipartParameter("CustomerPinCode", pinCode)
                .addMultipartParameter("CustomerEmail", emailId)
                .addMultipartParameter("InvoiceNo", invoiceNumber)
                .addMultipartParameter("FinanceScheme", schemeId)
                .addMultipartParameter("DeliveryAddress", delivaryAddress)
                .addMultipartParameter("FirstName", fName)
                .addMultipartParameter("LastName", lName)
                .addMultipartParameter("CustomerAlternateNumber", altNumber)
                .addMultipartParameter("HouseNo", houseNo)
                .addMultipartParameter("StreetName", streetname)
                .addMultipartParameter("Landmark", landMark)
                .addMultipartParameter("Title", titleId)
                .addMultipartParameter("StateID", stateId)
                .addMultipartParameter("City", cityName)
                .addMultipartParameter("InvoiceValue", invoiceValue)
                .addMultipartParameter("Remarks", remarks)
                .addMultipartParameter("UnderExchange", underExchange)
                .addMultipartParameter("Area", areaName)
                .addMultipartParameter("SalesEntryFlag", saleFlag)
                .addMultipartParameter("Invoicecopy", stringFile)
                .addMultipartParameter("SerialNo", serailNumber)
                .addMultipartParameter("Delivery_Date", "0")
                .addMultipartParameter("Delivery_Remarks", "0")
                .addMultipartParameter("Operation", "3")
                .addMultipartParameter("SubOperation", "2")
                .addMultipartParameter("DisplayMatrix_Sold", displaySoldID)
                .addMultipartParameter("CSD_Sales", csdSales)
                .addMultipartParameter("SecurityCode", secirityCode)

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
                        llSubmit.setEnabled(true);


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
                            Toast.makeText(SalesUpdateActivity.this, responseText, Toast.LENGTH_LONG).show();

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

    private void ssaleFunction() {
        String serialNumber = "";
        for (int i = 0; i < allEds.size(); i++) {
            if (allEds.get(i).getText().toString().length() == 18) {
                serialNumberList.add(allEds.get(i).getText().toString());
                serialNumber = serialNumberList.toString().replace("[", "").replace("]", "").concat(",");
                Log.d("Value ", serialNumber);
            } else {

            }


        }

        postSaleWithImage(serialNumber);


    }


}
