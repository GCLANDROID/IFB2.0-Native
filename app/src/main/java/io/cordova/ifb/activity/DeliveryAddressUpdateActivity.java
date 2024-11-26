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
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.developers.imagezipper.ImageZipper;
import com.wajahatkarim3.longimagecamera.LongImageCameraActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import id.zelory.compressor.Compressor;
import io.cordova.ifb.AndroidXCamera.AndroidXCameraActivity;
import io.cordova.ifb.R;
import io.cordova.ifb.module.RcnModel;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;
import io.cordova.ifb.utility.Util;
import io.cordova.ifb.utility.ValidUtils;

public class DeliveryAddressUpdateActivity extends AppCompatActivity {
    LinearLayout llDate, lnWIFI, lnPedestal, llScheme, llSalesType, llSalesDate;
    int MY_SOCKET_TIMEOUT_MS = 10000;
    TextView tvDate, tvSalesDate;
    String salesDate = "", monthname, deliveryDate = "";
    String refNo;
    PrefManager prefManager;
    EditText etRemark;
    AlertDialog alerDialog1;
    Button btnUpdate;
    AlertDialog alert1;
    private String encodedImage;
    private Uri imageUri;
    private static final int CAMERA_REQUEST = 1;
    File file, compressedImageFile, file1;
    File dFile;
    private static final int REQUEST_GALLERY_CODE = 200;
    ImageView imgCamera;
    LinearLayout llImage;
    Uri uri;
    ImageView imgPic;
    String stringFile;
    String fileImage;
    String imageFileName;
    File pictureFile;
    String contactNumber;
    String Ticketno;
    ArrayList<RcnModel> rcnList = new ArrayList<>();
    JSONObject outerObject;
    JSONArray jsonArray;
    JSONArray refArray;
    String modelCode;
    String altNumber;
    String tokenno;
    JSONObject informationOBJ = new JSONObject();
    String product;
    LinearLayout lnInstallation;
    String InstallationBy = "", category, WiFiDeviceStatus, SalesType = "";
    Spinner spInstallation;
    ArrayList<SpinnerItemModule> moduleInstallation = new ArrayList<>();
    ArrayList<String> installation = new ArrayList<>();
    LinearLayout lnODUNumber;
    EditText etSerailNumber, etODUNumber, etPhnNumber;
    ImageView imgIDUScanner, imgODUScanner;
    private static final int IDU_REQUEST = 0;
    private static final int IDU_REQUEST_2 = 2;
    private static final int IDU_REQUEST_3 = 3;
    private static final int IDU_REQUEST_4 = 4;
    private static final int IDU_REQUEST_5 = 5;
    private static final int ODU_REQUEST = 6;
    private static final int ODU_REQUEST_2 = 7;
    private static final int ODU_REQUEST_3 = 8;
    private static final int ODU_REQUEST_4 = 9;
    private static final int ODU_REQUEST_5 = 10;
    LinearLayout lnSerialNumberOp2, lnSerialNumberOp3, lnSerialNumberOp4, lnSerialNumberOp5;
    ImageView imgIDUScannerTwo, imgIDUScannerThree, imgIDUScannerFour, imgIDUScannerFive;
    EditText etSerailNumberFive, etSerailNumberFour, etSerailNumberThree, etSerailNumberTwo;
    LinearLayout lnODUNumberTwo, lnODUNumberThree, lnODUNumberFour, lnODUNumberFive;

    ImageView imgODUScannerFive, imgODUScannerFour, imgODUScannerThree, imgODUScannerTwo;

    EditText etODUNumberTwo, etODUNumberThree, etODUNumberFour, etODUNumberFive, etEmailId, etPinCode, etHouse, etStreetName, etLandMark, etInvoiceNumber;

    String qty, cusname, address, pincode;

    String categoryId, underexchange, financescheme;

    ArrayList<SpinnerItemModule> moduleDisplaySold = new ArrayList<>();
    ArrayList<String> displaySold = new ArrayList<>();

    Spinner spWIFI, spPedestal, spArea;

    String wifi = "N";
    String selectedWIFI;
    String selectedpedestial;
    String pedestial = "N";
    String altmob;
    TextView tvAltMob, tvEmail, tvPinCode, tvState, tvStateName, tvCityName, tvArea, tvStreet, tvLand, tvScheme, tvCity, tvHouse;
    AlertDialog alet1;
    String invalidEmail;

    Spinner spState, spCity, spScheme, spSalesType, spCSD, spDisplay;

    String STATENAME, PINCODE, REGIONNAME;

    ArrayList<SpinnerItemModule> moduleArea = new ArrayList<>();
    ArrayList<String> area = new ArrayList<>();
    ArrayList<SpinnerItemModule> moduleState = new ArrayList<>();
    ArrayList<String> state = new ArrayList<>();
    ArrayList<SpinnerItemModule> moduleCity = new ArrayList<>();
    ArrayList<String> city = new ArrayList<>();
    String stateId, areaName;
    ArrayList<SpinnerItemModule> moduleScheme = new ArrayList<>();
    ArrayList<String> scheme = new ArrayList<>();
    String schemeId;
    TextView tvSalesType;
    ArrayList<SpinnerItemModule> moduleSalesType = new ArrayList<>();
    ArrayList<String> salestype = new ArrayList<>();
    String salesType = "";
    String csdSales = "";
    String displaySoldID = "";
    String firstname, lastname, month, financilayear, invoicevalue;
    JSONObject csrOBJ = new JSONObject();
    String currentDate, sucessText;
    String Ref_Status = "N";
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address_update);
        initView();
        setState();
        onClick();
    }

    private void initView() {
        prefManager = new PrefManager(DeliveryAddressUpdateActivity.this);

        invoicevalue = getIntent().getStringExtra("invoicevalue");
        month = getIntent().getStringExtra("month");
        financilayear = getIntent().getStringExtra("financilayear");
        lastname = getIntent().getStringExtra("lastname");
        firstname = getIntent().getStringExtra("firstname");
        categoryId = getIntent().getStringExtra("categoryId");
        financescheme = getIntent().getStringExtra("financescheme");
        underexchange = getIntent().getStringExtra("underexchange");
        String next = "<font color='#EE0000'>*</font>";
        llDate = (LinearLayout) findViewById(R.id.llDate);
        llSalesDate = (LinearLayout) findViewById(R.id.llSalesDate);
        lnWIFI = (LinearLayout) findViewById(R.id.lnWIFI);
        lnPedestal = (LinearLayout) findViewById(R.id.lnPedestal);
        lnInstallation = (LinearLayout) findViewById(R.id.lnInstallation);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvSalesDate = (TextView) findViewById(R.id.tvSalesDate);
        refNo = getIntent().getStringExtra("refNo");
        etRemark = (EditText) findViewById(R.id.etRemark);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        imgCamera = (ImageView) findViewById(R.id.imgCamera);
        imgPic = (ImageView) findViewById(R.id.imgPic);
        contactNumber = getIntent().getStringExtra("contactNumber");
        qty = getIntent().getStringExtra("qty");
        cusname = getIntent().getStringExtra("cusname");
        address = getIntent().getStringExtra("address");
        pincode = getIntent().getStringExtra("pincode");
        altNumber = getIntent().getStringExtra("altNumber");
        modelCode = getIntent().getStringExtra("modelcode");
        category = getIntent().getStringExtra("product");
        SalesType = getIntent().getStringExtra("SalesType");
        spInstallation = (Spinner) findViewById(R.id.spInstallation);
        lnODUNumber = (LinearLayout) findViewById(R.id.lnODUNumber);

        etODUNumber = (EditText) findViewById(R.id.etODUNumber);
        etSerailNumber = (EditText) findViewById(R.id.etSerailNumber);
        imgODUScanner = (ImageView) findViewById(R.id.imgODUScanner);
        imgIDUScanner = (ImageView) findViewById(R.id.imgIDUScanner);

        lnSerialNumberOp2 = (LinearLayout) findViewById(R.id.lnSerialNumberOp2);
        lnSerialNumberOp3 = (LinearLayout) findViewById(R.id.lnSerialNumberOp3);
        lnSerialNumberOp4 = (LinearLayout) findViewById(R.id.lnSerialNumberOp4);
        lnSerialNumberOp5 = (LinearLayout) findViewById(R.id.lnSerialNumberOp5);

        imgIDUScannerTwo = (ImageView) findViewById(R.id.imgIDUScannerTwo);
        imgIDUScannerThree = (ImageView) findViewById(R.id.imgIDUScannerThree);
        imgIDUScannerFour = (ImageView) findViewById(R.id.imgIDUScannerFour);
        imgIDUScannerFive = (ImageView) findViewById(R.id.imgIDUScannerFive);

        etSerailNumberTwo = (EditText) findViewById(R.id.etSerailNumberTwo);
        etSerailNumberThree = (EditText) findViewById(R.id.etSerailNumberThree);
        etSerailNumberFour = (EditText) findViewById(R.id.etSerailNumberFour);
        etSerailNumberFive = (EditText) findViewById(R.id.etSerailNumberFive);

        lnODUNumberTwo = (LinearLayout) findViewById(R.id.lnODUNumberTwo);
        lnODUNumberThree = (LinearLayout) findViewById(R.id.lnODUNumberThree);
        lnODUNumberFour = (LinearLayout) findViewById(R.id.lnODUNumberFour);
        lnODUNumberFive = (LinearLayout) findViewById(R.id.lnODUNumberFive);

        imgODUScannerFive = (ImageView) findViewById(R.id.imgODUScannerFive);
        imgODUScannerFour = (ImageView) findViewById(R.id.imgODUScannerFour);
        imgODUScannerThree = (ImageView) findViewById(R.id.imgODUScannerThree);
        imgODUScannerTwo = (ImageView) findViewById(R.id.imgODUScannerTwo);

        etODUNumberTwo = (EditText) findViewById(R.id.etODUNumberTwo);
        etODUNumberThree = (EditText) findViewById(R.id.etODUNumberThree);
        etODUNumberFour = (EditText) findViewById(R.id.etODUNumberFour);
        etODUNumberFive = (EditText) findViewById(R.id.etODUNumberFive);

        if (qty.equalsIgnoreCase("2")) {
            lnSerialNumberOp2.setVisibility(View.VISIBLE);
        } else if (qty.equalsIgnoreCase("3")) {
            lnSerialNumberOp2.setVisibility(View.VISIBLE);
            lnSerialNumberOp3.setVisibility(View.VISIBLE);
        } else if (qty.equalsIgnoreCase("4")) {
            lnSerialNumberOp2.setVisibility(View.VISIBLE);
            lnSerialNumberOp3.setVisibility(View.VISIBLE);
            lnSerialNumberOp4.setVisibility(View.VISIBLE);
        } else if (qty.equalsIgnoreCase("5")) {
            lnSerialNumberOp2.setVisibility(View.VISIBLE);
            lnSerialNumberOp3.setVisibility(View.VISIBLE);
            lnSerialNumberOp4.setVisibility(View.VISIBLE);
            lnSerialNumberOp5.setVisibility(View.VISIBLE);
        }

        if (category.equalsIgnoreCase("AIR CONDITIONER")) {
            lnInstallation.setVisibility(View.VISIBLE);
            lnODUNumber.setVisibility(View.VISIBLE);
            lnODUNumberTwo.setVisibility(View.VISIBLE);
            lnODUNumberThree.setVisibility(View.VISIBLE);
            lnODUNumberFour.setVisibility(View.VISIBLE);
            lnODUNumberFive.setVisibility(View.VISIBLE);
            setInstallation();
        } else {
            lnInstallation.setVisibility(View.GONE);
            lnODUNumber.setVisibility(View.GONE);
        }


        displaySold.add("NO");
        displaySold.add("YES");
        moduleDisplaySold.add(new SpinnerItemModule("NO", "N"));
        moduleDisplaySold.add(new SpinnerItemModule("YES", "Y"));


        spWIFI = (Spinner) findViewById(R.id.spWIFI);
        ArrayAdapter<String> spinnerWifiArrayAdapter = new ArrayAdapter<String>
                (DeliveryAddressUpdateActivity.this, android.R.layout.simple_spinner_item,
                        displaySold); //selected item will look like a spinner set from XML
        spinnerWifiArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWIFI.setAdapter(spinnerWifiArrayAdapter);


        spPedestal = (Spinner) findViewById(R.id.spPedestal);
        ArrayAdapter<String> spinnerPedestalAdapter = new ArrayAdapter<String>
                (DeliveryAddressUpdateActivity.this, android.R.layout.simple_spinner_item,
                        displaySold); //selected item will look like a spinner set from XML
        spinnerPedestalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPedestal.setAdapter(spinnerPedestalAdapter);

        if (categoryId.equals("IFBPC1000001")) {
            lnWIFI.setVisibility(View.GONE);

            lnPedestal.setVisibility(View.GONE);


        } else if (categoryId.equals("IFBPC1000013") || categoryId.equals("IFBPC1000040")) {
            lnPedestal.setVisibility(View.VISIBLE);
            lnWIFI.setVisibility(View.GONE);
        } else {
            lnWIFI.setVisibility(View.GONE);
            lnPedestal.setVisibility(View.GONE);
        }


        tvAltMob = (TextView) findViewById(R.id.tvAltMob);
        String altmob = "ALTERNATIVE NUMBER:";
        tvAltMob.setText(altmob);

        etPhnNumber = (EditText) findViewById(R.id.etPhnNumber);

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        String email = "EMAIL ";
        tvEmail.setText(Html.fromHtml(email + next));

        etEmailId = (EditText) findViewById(R.id.etEmailId);


        tvPinCode = (TextView) findViewById(R.id.tvPinCode);
        String pin = "DELIVERY PIN CODE:";
        tvPinCode.setText(Html.fromHtml(pin + next));

        etPinCode = (EditText) findViewById(R.id.etPinCode);


        tvState = (TextView) findViewById(R.id.tvState);
        String state = "STATE";
        tvState.setText(Html.fromHtml(state + next));
        tvStateName = (TextView) findViewById(R.id.tvStateName);
        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spCity);
        tvCityName = (TextView) findViewById(R.id.tvCityName);

        tvCity = (TextView) findViewById(R.id.tvCity);
        String city = "CITY ";
        tvCity.setText(Html.fromHtml(city + next));

        tvArea = (TextView) findViewById(R.id.tvArea);
        String area = "AREA ";
        tvArea.setText(Html.fromHtml(area + next));

        spArea = (Spinner) findViewById(R.id.spArea);

        etHouse = (EditText) findViewById(R.id.etHouse);

        tvStreet = (TextView) findViewById(R.id.tvStreet);
        String street = "STREET NAME ";
        tvStreet.setText(Html.fromHtml(street + next));

        etStreetName = (EditText) findViewById(R.id.etStreetName);

        tvLand = (TextView) findViewById(R.id.tvLand);
        String land = "LANDMARK ";
        tvLand.setText(Html.fromHtml(land + next));

        etLandMark = (EditText) findViewById(R.id.etLandMark);
        etInvoiceNumber = (EditText) findViewById(R.id.etInvoiceNumber);

        llScheme = (LinearLayout) findViewById(R.id.llScheme);

        tvScheme = (TextView) findViewById(R.id.tvScheme);
        String scheme = "SELECT FINANCE SCHEME ";
        tvScheme.setText(Html.fromHtml(scheme + next));


        tvHouse = (TextView) findViewById(R.id.tvHouse);
        String house = "HOUSE NO. ";
        tvHouse.setText(Html.fromHtml(house + next));

        spScheme = (Spinner) findViewById(R.id.spScheme);
        llSalesType = (LinearLayout) findViewById(R.id.llSalesType);

        tvSalesType = (TextView) findViewById(R.id.tvSalesType);
        String salestype = "SALES TYPE";
        tvSalesType.setText(Html.fromHtml(salestype + next));

        spSalesType = (Spinner) findViewById(R.id.spSalesType);


        spCSD = (Spinner) findViewById(R.id.spCSD);

        ArrayAdapter<String> spinnerCSDArrayAdapter = new ArrayAdapter<String>
                (DeliveryAddressUpdateActivity.this, android.R.layout.simple_spinner_item,
                        displaySold); //selected item will look like a spinner set from XML
        spinnerCSDArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCSD.setAdapter(spinnerCSDArrayAdapter);

        spDisplay = (Spinner) findViewById(R.id.spDisplay);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (DeliveryAddressUpdateActivity.this, android.R.layout.simple_spinner_item,
                        displaySold); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDisplay.setAdapter(spinnerArrayAdapter);

        if (financescheme.equals("0") || financescheme.equals("")) {
            llScheme.setVisibility(View.GONE);
            llSalesType.setVisibility(View.GONE);
        } else {


            llScheme.setVisibility(View.GONE);
            setSalesType();
            llSalesType.setVisibility(View.VISIBLE);
        }

        currentDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault()).format(new Date());
        getCusDetail();

    }

    private void onClick() {

        spDisplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                displaySoldID = moduleDisplaySold.get(i).getItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCSD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                csdSales = moduleDisplaySold.get(i).getItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spSalesType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SalesType = moduleSalesType.get(i).getItemId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spScheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {

                    schemeId = moduleScheme.get(position).getItemId();
                    // finMobAlert();
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


        spWIFI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedWIFI = displaySold.get(i);
                if (selectedWIFI.equalsIgnoreCase("NO")) {
                    wifi = "N";
                } else if (selectedWIFI.equalsIgnoreCase("YES")) {
                    wifi = "Y";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spPedestal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedpedestial = displaySold.get(i);
                if (selectedpedestial.equalsIgnoreCase("NO")) {
                    pedestial = "N";
                } else if (selectedpedestial.equalsIgnoreCase("YES")) {
                    pedestial = "Y";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        imgIDUScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryAddressUpdateActivity.this, ScannerActivity.class);
                startActivityForResult(intent, IDU_REQUEST);
            }
        });

        imgIDUScannerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryAddressUpdateActivity.this, ScannerActivity.class);
                startActivityForResult(intent, IDU_REQUEST_2);
            }
        });

        imgIDUScannerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryAddressUpdateActivity.this, ScannerActivity.class);
                startActivityForResult(intent, IDU_REQUEST_3);
            }
        });


        imgIDUScannerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryAddressUpdateActivity.this, ScannerActivity.class);
                startActivityForResult(intent, IDU_REQUEST_4);
            }
        });


        imgIDUScannerFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryAddressUpdateActivity.this, ScannerActivity.class);
                startActivityForResult(intent, IDU_REQUEST_5);
            }
        });

        imgODUScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryAddressUpdateActivity.this, ODUScannerActivity.class);
                startActivityForResult(intent, ODU_REQUEST);
            }
        });

        imgODUScannerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryAddressUpdateActivity.this, ODUScannerActivity.class);
                startActivityForResult(intent, ODU_REQUEST_2);
            }
        });

        imgODUScannerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryAddressUpdateActivity.this, ODUScannerActivity.class);
                startActivityForResult(intent, ODU_REQUEST_3);
            }
        });


        imgODUScannerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryAddressUpdateActivity.this, ODUScannerActivity.class);
                startActivityForResult(intent, ODU_REQUEST_4);
            }
        });

        imgODUScannerFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryAddressUpdateActivity.this, ODUScannerActivity.class);
                startActivityForResult(intent, ODU_REQUEST_5);
            }
        });


        spInstallation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InstallationBy = moduleInstallation.get(i).getItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialog();
            }
        });
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        llSalesDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSalesDateDialog();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!deliveryDate.equals("")) {
                    if (etRemark.getText().toString().length() > 0) {
                        if (etEmailId.getText().toString().length() > 0) {
                            if (ValidUtils.isValidEmail(etEmailId.getText().toString())) {
                                if (etPinCode.getText().toString().length() > 0) {
                                    if (!REGIONNAME.equals("null")) {
                                        if (etHouse.getText().toString().replaceAll(" ", "").length() > 0) {
                                            if (etStreetName.getText().toString().replaceAll(" ", "").length() > 0) {
                                                if (etLandMark.getText().toString().replaceAll(" ", "").length() > 0) {

                                                    emailcheck1();
                                                } else {
                                                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter Landmark.", Toast.LENGTH_LONG).show();

                                                }

                                            } else {
                                                Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter Street Name.", Toast.LENGTH_LONG).show();

                                            }

                                        } else {
                                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter House/Flat/Plot No.", Toast.LENGTH_LONG).show();

                                        }

                                    } else {
                                        Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter Valid Pincode", Toast.LENGTH_LONG).show();

                                    }

                                } else {
                                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter Pincode", Toast.LENGTH_LONG).show();

                                }

                            } else {
                                Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter Valid Email ID", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter Email ID", Toast.LENGTH_LONG).show();

                        }
                        //  oduChecking();


                    } else {
                        Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter Remarks", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Select Delivery Date", Toast.LENGTH_LONG).show();
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
        final DatePickerDialog dialog = new DatePickerDialog(DeliveryAddressUpdateActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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

                deliveryDate = d + "-" + monthname + "-" + y;
                salesDate = deliveryDate;
                tvSalesDate.setText(salesDate);

                tvDate.setText(deliveryDate);

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


    private void showSalesDateDialog() {
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
        final DatePickerDialog dialog = new DatePickerDialog(DeliveryAddressUpdateActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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

                tvSalesDate.setText(salesDate);

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

    private void updateDeliveryAddress() {
        if (TextUtils.isEmpty(stringFile)) {
            fileImage = "0";
        } else {
            fileImage = stringFile;
        }

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        String serailNumber = etSerailNumber.getText().toString() + "," + etSerailNumberTwo.getText().toString() + "," + etSerailNumberThree.getText().toString() + "," + etSerailNumberFour.getText().toString() + "," + etSerailNumberFive.getText().toString();
        String odunumber = etODUNumber.getText().toString() + "," + etODUNumberTwo.getText().toString() + "," + etODUNumberThree.getText().toString() + "," + etODUNumberFour.getText().toString() + "," + etODUNumberFive.getText().toString();

        AndroidNetworking.upload(AppController.APIURL + "api/post_EmployeeSalesManageV7")
                .addMultipartParameter("TransNo", "0")
                .addMultipartParameter("ReferenceNo", refNo)
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("SalesDate", salesDate)
                .addMultipartParameter("FinancialYear", financilayear)
                .addMultipartParameter("Month", month)
                .addMultipartParameter("CategoryID", categoryId)
                .addMultipartParameter("Quantity", qty)
                .addMultipartParameter("UserID", prefManager.getUserId())
                .addMultipartParameter("BranchID", prefManager.getBranchId())
                .addMultipartParameter("ModelID", modelCode)
                .addMultipartParameter("CustomerName", cusname)
                .addMultipartParameter("CustomerPhNo", contactNumber)
                .addMultipartParameter("CustomerPinCode", etPinCode.getText().toString())
                .addMultipartParameter("CustomerEmail", etEmailId.getText().toString())
                .addMultipartParameter("InvoiceNo", etInvoiceNumber.getText().toString())
                .addMultipartParameter("FinanceScheme", "0")
                .addMultipartParameter("DeliveryAddress", address)
                .addMultipartParameter("FirstName", firstname)
                .addMultipartParameter("LastName", lastname)
                .addMultipartParameter("CustomerAlternateNumber", "")
                .addMultipartParameter("HouseNo", etHouse.getText().toString())
                .addMultipartParameter("StreetName", etStreetName.getText().toString())
                .addMultipartParameter("Landmark", etLandMark.getText().toString())
                .addMultipartParameter("Title", "0")
                .addMultipartParameter("StateID", stateId)
                .addMultipartParameter("City", tvCityName.getText().toString())
                .addMultipartParameter("InvoiceValue", invoicevalue)
                .addMultipartParameter("Remarks", etRemark.getText().toString())
                .addMultipartParameter("UnderExchange", underexchange)
                .addMultipartParameter("Area", areaName)
                .addMultipartParameter("SalesEntryFlag", "1")
                .addMultipartFile("Invoicecopy", compressedImageFile)
                .addMultipartParameter("SerialNo", serailNumber)
                .addMultipartParameter("SerialNo1", odunumber)
                .addMultipartParameter("InstallationBy", InstallationBy)
                .addMultipartParameter("SalesType", SalesType)
                .addMultipartParameter("WiFiDeviceStatus", wifi)
                .addMultipartParameter("Delivery_Date", deliveryDate)
                .addMultipartParameter("Delivery_Remarks", etRemark.getText().toString())
                .addMultipartParameter("Operation", "3")
                .addMultipartParameter("SubOperation", "4")
                .addMultipartParameter("DisplayMatrix_Sold", displaySoldID)
                .addMultipartParameter("CSD_Sales", csdSales)
                .addMultipartParameter("PedestalSales", pedestial)
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
                        boolean responseStatus = job1.optBoolean("responseStatus");
                        String responseText = job1.optString("responseText");
                        if (responseStatus) {
                            JSONArray responseData = job1.optJSONArray("responseData");
                            JSONObject obj = responseData.optJSONObject(0);
                            sucessText = obj.optString("Remarks");

                            getToken();
                            pd.dismiss();

                        } else {
                            pd.dismiss();
                            if (responseText.contains("MIME")) {
                                updateDeliveryAddressWithoutInvoice();
                            } else {
                                successAlert(responseText, "2");
                            }


                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        updateDeliveryAddressWithoutInvoice();
                    }
                });
    }


    private void updateDeliveryAddressWithoutInvoice() {


        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        String serailNumber = etSerailNumber.getText().toString() + "," + etSerailNumberTwo.getText().toString() + "," + etSerailNumberThree.getText().toString() + "," + etSerailNumberFour.getText().toString() + "," + etSerailNumberFive.getText().toString();
        String odunumber = etODUNumber.getText().toString() + "," + etODUNumberTwo.getText().toString() + "," + etODUNumberThree.getText().toString() + "," + etODUNumberFour.getText().toString() + "," + etODUNumberFive.getText().toString();

        AndroidNetworking.upload(AppController.APIURL + "api/post_EmployeeSalesManageV6")
                .addMultipartParameter("TransNo", "0")
                .addMultipartParameter("ReferenceNo", refNo)
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("SalesDate", salesDate)
                .addMultipartParameter("FinancialYear", financilayear)
                .addMultipartParameter("Month", month)
                .addMultipartParameter("CategoryID", categoryId)
                .addMultipartParameter("Quantity", qty)
                .addMultipartParameter("UserID", prefManager.getUserId())
                .addMultipartParameter("BranchID", prefManager.getBranchId())
                .addMultipartParameter("ModelID", modelCode)
                .addMultipartParameter("CustomerName", cusname)
                .addMultipartParameter("CustomerPhNo", contactNumber)
                .addMultipartParameter("CustomerPinCode", etPinCode.getText().toString())
                .addMultipartParameter("CustomerEmail", etEmailId.getText().toString())
                .addMultipartParameter("InvoiceNo", etInvoiceNumber.getText().toString())
                .addMultipartParameter("FinanceScheme", "0")
                .addMultipartParameter("DeliveryAddress", address)
                .addMultipartParameter("FirstName", firstname)
                .addMultipartParameter("LastName", lastname)
                .addMultipartParameter("CustomerAlternateNumber", "")
                .addMultipartParameter("HouseNo", etHouse.getText().toString())
                .addMultipartParameter("StreetName", etStreetName.getText().toString())
                .addMultipartParameter("Landmark", etLandMark.getText().toString())
                .addMultipartParameter("Title", "0")
                .addMultipartParameter("StateID", stateId)
                .addMultipartParameter("City", tvCityName.getText().toString())
                .addMultipartParameter("InvoiceValue", invoicevalue)
                .addMultipartParameter("Remarks", etRemark.getText().toString())
                .addMultipartParameter("UnderExchange", underexchange)
                .addMultipartParameter("Area", areaName)
                .addMultipartParameter("SalesEntryFlag", "1")
                .addMultipartParameter("Invoicecopy", "0")
                .addMultipartParameter("SerialNo", serailNumber)
                .addMultipartParameter("SerialNo1", odunumber)
                .addMultipartParameter("InstallationBy", InstallationBy)
                .addMultipartParameter("SalesType", SalesType)
                .addMultipartParameter("WiFiDeviceStatus", wifi)
                .addMultipartParameter("Delivery_Date", deliveryDate)
                .addMultipartParameter("Delivery_Remarks", etRemark.getText().toString())
                .addMultipartParameter("Operation", "3")
                .addMultipartParameter("SubOperation", "4")
                .addMultipartParameter("DisplayMatrix_Sold", displaySoldID)
                .addMultipartParameter("CSD_Sales", csdSales)
                .addMultipartParameter("PedestalSales", pedestial)
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
                        boolean responseStatus = job1.optBoolean("responseStatus");
                        String responseText = job1.optString("responseText");
                        if (responseStatus) {
                            JSONArray responseData = job1.optJSONArray("responseData");
                            JSONObject obj = responseData.optJSONObject(0);
                            sucessText = obj.optString("Remarks");

                            getToken();
                            pd.dismiss();

                        } else {
                            pd.dismiss();
                            successAlert(responseText, "2");

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

    /*private void updateDeliveryAddress() {

        if (TextUtils.isEmpty(stringFile)) {
            fileImage = "0";
        } else {
            fileImage = stringFile;
        }

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        String serailNumber=etSerailNumber.getText().toString()+","+etSerailNumberTwo.getText().toString()+","+etSerailNumberThree.getText().toString()+","+etSerailNumberFour.getText().toString()+","+etSerailNumberFive.getText().toString();
        String odunumber=etODUNumber.getText().toString()+","+etODUNumberTwo.getText().toString()+","+etODUNumberThree.getText().toString()+","+etODUNumberFour.getText().toString()+","+etODUNumberFive.getText().toString();

        AndroidNetworking.upload( AppController.APIURL+"api/post_EmployeeSalesManageV6")
                .addMultipartParameter("TransNo", "0")
                .addMultipartParameter("ReferenceNo", refNo)
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("SalesDate", "0")
                .addMultipartParameter("FinancialYear", "0")
                .addMultipartParameter("Month", "0")
                .addMultipartParameter("CategoryID", "0")
                .addMultipartParameter("Quantity", "0")
                .addMultipartParameter("UserID", prefManager.getUserId())
                .addMultipartParameter("BranchID", prefManager.getBranchId())
                .addMultipartParameter("ModelID", "0")
                .addMultipartParameter("CustomerName", "0")
                .addMultipartParameter("CustomerPhNo","0" )
                .addMultipartParameter("CustomerPinCode", "0")
                .addMultipartParameter("CustomerEmail", "0")
                .addMultipartParameter("InvoiceNo", "0")
                .addMultipartParameter("FinanceScheme", "0")
                .addMultipartParameter("DeliveryAddress", "0")
                .addMultipartParameter("FirstName", "0")
                .addMultipartParameter("LastName", "0")
                .addMultipartParameter("CustomerAlternateNumber", "0")
                .addMultipartParameter("HouseNo", "0")
                .addMultipartParameter("StreetName","0")
                .addMultipartParameter("Landmark", "0")
                .addMultipartParameter("Title", "0")
                .addMultipartParameter("StateID", "0")
                .addMultipartParameter("City", "0")
                .addMultipartParameter("InvoiceValue","0")
                .addMultipartParameter("Remarks", "0")
                .addMultipartParameter("UnderExchange", "0")
                .addMultipartParameter("Area", "0")
                .addMultipartParameter("SalesEntryFlag", "1")
                .addMultipartParameter("Invoicecopy", fileImage)
                .addMultipartParameter("SerialNo", serailNumber)
                .addMultipartParameter("SerialNo1", odunumber)
                .addMultipartParameter("InstallationBy", InstallationBy)
                .addMultipartParameter("SalesType", "0")
                .addMultipartParameter("WiFiDeviceStatus", "0")
                .addMultipartParameter("Delivery_Date", salesDate)
                .addMultipartParameter("Delivery_Remarks", etRemark.getText().toString())
                .addMultipartParameter("Operation", "3")
                .addMultipartParameter("SubOperation", "4")
                .addMultipartParameter("DisplayMatrix_Sold", "0")
                .addMultipartParameter("CSD_Sales", "0")
                .addMultipartParameter("PedestalSales", "0")
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
                        sucessText=responseText;
                        Log.d("responseText", responseText);
                        boolean responseStatus = job1.optBoolean("responseStatus");
                        if (responseStatus) {
                            getToken();
                            pd.dismiss();

                        } else {
                            pd.dismiss();
                            Toast.makeText(DeliveryAddressUpdateActivity.this, responseText, Toast.LENGTH_LONG).show();

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
    }*/

    private void successAlert(String text, String flag) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DeliveryAddressUpdateActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText(text);

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag.equals("2")) {
                    alerDialog1.dismiss();
                    onBackPressed();
                } else {
                    alerDialog1.dismiss();
                    if (Ref_Status.equalsIgnoreCase("N")) {
                        getTicketNumber();
                    } else {
                        setRefArray();
                    }
                }


            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(false);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }

    public void getToken() {
        String surl = AppController.APIURL + "api/get_CRMDummyTokenByReference?ReferenceNo=" + refNo + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputCheck", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        progressBar.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                JSONArray responseData = job1.optJSONArray("responseData");
                                csrOBJ = new JSONObject();
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject object = responseData.optJSONObject(i);
                                    String TokenNo = object.optString("TokenNo");
                                    String CategoryShortName = object.optString("CategoryShortName");
                                    String SerialNo = object.optString("SerialNo");
                                    String ModelCode = object.optString("ModelCode");
                                    String FirstName = object.optString("FirstName");
                                    String LastName = object.optString("LastName");
                                    String DeliveryAddress = object.optString("DeliveryAddress");
                                    String StreetName = object.optString("StreetName");
                                    String CustomerPinCode = object.optString("CustomerPinCode");
                                    String City = object.optString("City");
                                    String StateName = object.optString("StateName");
                                    String SerialNo2 = object.optString("SerialNo2");
                                    String CustomerPhNo = object.optString("CustomerPhNo");
                                    String AlternateNumber = object.optString("AlternateNumber");
                                    String CustomerEmail = object.optString("CustomerEmail");
                                    String SalesDate = object.optString("SalesDate");
                                    String ShipPartyCode = object.optString("ShipPartyCode");
                                    String MultipleProduct = object.optString("MultipleProduct");
                                    String installationBy = object.optString("InstallationBy");
                                    String WiFiDeviceStatus = object.optString("WiFiDeviceStatus");
                                    String RELIANCEFLAG = object.optString("RELIANCEFLAG");
                                    RcnModel rcnModel = new RcnModel();
                                    rcnModel.setToken(TokenNo);
                                    rcnModel.setSerNumber(SerialNo);
                                    rcnModel.setShortName(CategoryShortName);

                                    rcnList.add(rcnModel);
                                    csrOBJ.put("MODEL", ModelCode);
                                    csrOBJ.put("PRODUCT", CategoryShortName);
                                    csrOBJ.put("CUSTOMERFIRSTNAME", FirstName);
                                    csrOBJ.put("CUSTOMERLASTNAME", LastName);
                                    csrOBJ.put("ADDRESS", DeliveryAddress);
                                    csrOBJ.put("STREET", StreetName);
                                    csrOBJ.put("PINCODE", CustomerPinCode);
                                    csrOBJ.put("CITY", City);
                                    csrOBJ.put("STATE", StateName);
                                    csrOBJ.put("MOBILENO", CustomerPhNo);
                                    csrOBJ.put("ALTMOBNO", AlternateNumber);
                                    csrOBJ.put("EMAIL", CustomerEmail);
                                    csrOBJ.put("PURCHASEDATE", SalesDate);
                                    csrOBJ.put("DEALER", ShipPartyCode);
                                    csrOBJ.put("TOKENNO", TokenNo);
                                    csrOBJ.put("CREATEDBY", prefManager.getUserCode());
                                    csrOBJ.put("RELIANCEFRANCH", "");
                                    csrOBJ.put("RELIANCEFLAG", RELIANCEFLAG);
                                    csrOBJ.put("MULTIPLEQUANTITY", MultipleProduct);
                                    csrOBJ.put("TOKENCREATED", currentDate);
                                    csrOBJ.put("INSTALLATIONBY", installationBy);
                                    csrOBJ.put("IDUSERIAL", SerialNo);
                                    csrOBJ.put("ODUSERIAL", SerialNo2);
                                    csrOBJ.put("WIFI", WiFiDeviceStatus);
                                    csrOBJ.put("FILECREATED", currentDate);
                                    if (prefManager.getUserCode().equals("IFBAPPL00001")) {
                                        successAlert(sucessText, "2");
                                    } else {
                                        sendCSRData(csrOBJ, TokenNo);
                                    }


                                }

                                JSONObject obj = responseData.optJSONObject(0);
                                tokenno = obj.optString("TokenNo");
                                product = obj.optString("CategoryShortName");
                                Ref_Status = obj.optString("Ref_Status");


                            } else {
                                successAlert(sucessText, "2");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(DeliveryAddressUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
        requestQueue.add(stringRequest);

    }

    private void cameraDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DeliveryAddressUpdateActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.camera_dialog, null);
        dialogBuilder.setView(dialogView);
        LinearLayout llCamera = (LinearLayout) dialogView.findViewById(R.id.llCamera);
        LinearLayout llGallery = (LinearLayout) dialogView.findViewById(R.id.llGallery);
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidXCameraActivity.launch(DeliveryAddressUpdateActivity.this, CAMERA_REQUEST);
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 2000 && resultCode == CAMERA_REQUEST) {
            Log.e("TAG", "onActivityResult: " + data.getExtras().get("picture"));
            Log.e("TAG", "onActivityResult: " + data.getExtras().get(AndroidXCameraActivity.IMAGE_PATH_KEY));
            image_uri = Uri.parse(String.valueOf(data.getExtras().get("picture")));
            //image_uri = (Uri) data.getExtras().get(AndroidXCameraActivity.IMAGE_PATH_KEY);
            File imageFile = new File(String.valueOf(data.getExtras().get("picture")));
            Log.e("TAG", "image_uri: " + image_uri);
            if (image_uri != null) {


                try {
                    compressedImageFile = new ImageZipper(DeliveryAddressUpdateActivity.this)
                            .setQuality(80)
                            .setMaxWidth(250)
                            .setMaxHeight(250)
                            .compressToFile(imageFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    encodedImage = Util.fileToBase64(compressedImageFile).replaceAll("\n", "");
                    //Log.e(TAG, "base64Image: ==================="+base64image );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                imgPic.setImageURI(image_uri);
                String contentType = "image/jpg";
                stringFile = tokenno + "_" + encodedImage + "_" + contentType;
                alert1.dismiss();
            }
        } else if ((requestCode == REQUEST_GALLERY_CODE)) {
            InputStream imageStream = null;
            try {
                try {
                    uri = data.getData();
                    String filePath = getRealPathFromURIPath(uri, DeliveryAddressUpdateActivity.this);
                    file = new File(filePath);

                    imageStream = getContentResolver().openInputStream(uri);
                    Bitmap bm = cropToSquare(BitmapFactory.decodeStream(imageStream));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    imgPic.setImageBitmap(bm);
                    boolean isImageTooLarge = Util.isImageGreaterThan2MB(this, uri);
                    if (isImageTooLarge) {
                        try {
                            compressedImageFile = new ImageZipper(DeliveryAddressUpdateActivity.this)
                                    .setQuality(80)
                                    .setMaxWidth(250)
                                    .setMaxHeight(250)
                                    .compressToFile(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        compressedImageFile = file;

                    }

                    try {
                        encodedImage = Util.fileToBase64(compressedImageFile).replaceAll("\n", "");
                        //Log.e(TAG, "base64Image: ==================="+base64image );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    String contentType = "image/jpg";
                    stringFile = tokenno + "_" + encodedImage + "_" + contentType;
                    alert1.dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }

        } else if ((requestCode == IDU_REQUEST)) {
            String message = data.getStringExtra("MESSAGE");
            etSerailNumber.setText(message);

        } else if ((requestCode == IDU_REQUEST_2)) {
            String message2 = data.getStringExtra("MESSAGE");
            etSerailNumberTwo.setText(message2);
        } else if ((requestCode == IDU_REQUEST_3)) {
            String message3 = data.getStringExtra("MESSAGE");
            etSerailNumberThree.setText(message3);
        } else if ((requestCode == IDU_REQUEST_4)) {
            String message4 = data.getStringExtra("MESSAGE");
            etSerailNumberFour.setText(message4);
        } else if ((requestCode == IDU_REQUEST_5)) {
            String message5 = data.getStringExtra("MESSAGE");
            etSerailNumberFive.setText(message5);
        } else if ((requestCode == ODU_REQUEST)) {
            String message1 = data.getStringExtra("MESSAGE");
            etODUNumber.setText(message1);
        } else if ((requestCode == ODU_REQUEST_2)) {
            String message6 = data.getStringExtra("MESSAGE");
            etODUNumberTwo.setText(message6);
        } else if ((requestCode == ODU_REQUEST_3)) {
            String message7 = data.getStringExtra("MESSAGE");
            etODUNumberThree.setText(message7);
        } else if ((requestCode == ODU_REQUEST_4)) {
            String message8 = data.getStringExtra("MESSAGE");
            etODUNumberFour.setText(message8);
        } else if ((requestCode == ODU_REQUEST_5)) {
            String message9 = data.getStringExtra("MESSAGE");
            etODUNumberFive.setText(message9);
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

    private void getTicketNumber() {

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rcn", contactNumber);
            jsonObject.put("MatGrp", product);
            jsonObject.put("modelcode", modelCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://crmapi.ifbsupport.com/api/v.1/csr/search/ticket")

                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", "Bearer ZW55dXNlcjplbnl1JGVy")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        String status = job1.optString("status");


                        if (status.equalsIgnoreCase("200")) {

                            pd.dismiss();
                            JSONArray TicketDetails = job1.optJSONArray("TicketDetails");
                            for (int i = 0; i < TicketDetails.length(); i++) {
                                JSONObject obj = TicketDetails.optJSONObject(i);
                                String Ticketno = obj.optString("Ticketno");
                                String dealername = obj.optString("dealername");
                                String DealerPhone = obj.optString("DealerPhone");
                                String CustomerCode = obj.optString("CustomerCode");
                                String CustomerName = obj.optString("CustomerName");
                                String Address = obj.optString("Address");
                                String Pincode = obj.optString("Pincode");
                                String CustomerMobile = obj.optString("CustomerMobile");
                                String CustomerEmail = obj.optString("CustomerEmail");
                                String DOP = obj.optString("DOP");
                                String ProductDOI = obj.optString("ProductDOI");
                                String TicketStatusCode = obj.optString("TicketStatusCode");
                                String CancelledReason = obj.optString("CancelledReason");
                                String CancelledReasonDescription = obj.optString("CancelledReasonDescription");
                                String BranchCode = obj.optString("BranchCode");
                                String BranchName = obj.optString("BranchName");
                                String TicketCallType = obj.optString("TicketCallType");
                                String CallBookDate = obj.optString("CallBookDate");
                                String CallClosedDateValue = obj.optString("CallClosedDateValue");
                                String FGCode = obj.optString("FGCode");
                                String zzserial_numb = obj.optString("zzserial_numb");
                                String sold_to_party = obj.optString("sold_to_party");
                                String zzmachine_status = obj.optString("zzmachine_status");
                                String description = obj.optString("description");
                                String orgin = obj.optString("orgin");
                                RcnModel rcnModel = new RcnModel();
                                rcnModel.setTicket(Ticketno);
                                rcnModel.setDelearName(dealername);
                                rcnModel.setDelearPhone(DealerPhone);
                                rcnModel.setCustomerCode(CustomerCode);
                                rcnModel.setCustomerName(CustomerName);
                                rcnModel.setAddress(Address);
                                rcnModel.setPincode(Pincode);
                                rcnModel.setCustomerMobile(CustomerMobile);
                                rcnModel.setCustomerEmail(CustomerEmail);
                                rcnModel.setDop(DOP);
                                rcnModel.setDoi(ProductDOI);
                                rcnModel.setStatusCode(TicketStatusCode);
                                rcnModel.setCancelledReason(CancelledReason);
                                rcnModel.setCancelledReasonDescription(CancelledReasonDescription);
                                rcnModel.setBranch(BranchCode);
                                rcnModel.setBranchName(BranchName);
                                rcnModel.setCallType(TicketCallType);
                                rcnModel.setCallBookDate(CallBookDate);
                                rcnModel.setCallClosedDate(CallClosedDateValue);
                                rcnModel.setModelcode(FGCode);
                                rcnModel.setSoldToParty(sold_to_party);
                                rcnModel.setOrigin(orgin);
                                rcnModel.setDescription(description);
                                rcnModel.setMachineStatus(zzmachine_status);
                                rcnModel.setSerailNumber(zzserial_numb);
                                rcnList.add(rcnModel);
                            }
                            setRcnArray();


                        } else {
                            pd.dismiss();


                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Intent intent = new Intent(DeliveryAddressUpdateActivity.this, SalesDashboardActivity.class);
                        startActivity(intent);
                        finish();
                        // getInformationFromToken();
                    }
                });
    }


    /*private void sendSMS() {

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("TokenNo", tokenno);
            jsonObject.put("SMSType", "CSR");
            jsonObject.put("Destination", contactNumber);
            jsonObject.put("Product", product);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://crm.ifbsupport.com/technician/api/csr/sms")

                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", "Bearer Q1NSVVNFUjpjc3JVc2Vy")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        boolean status = job1.optBoolean("status");
                        if (status) {



                        } else {
                            pd.dismiss();
                            getTicketNumber();


                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Intent intent = new Intent(DeliveryAddressUpdateActivity.this, SalesManageDashboardActivity.class);
                        startActivity(intent);
                        finish();
                        // getInformationFromToken();
                    }
                });
    }*/

    private void setRcnArray() {
        ArrayList<String> delaernameList = new ArrayList<>();
        ArrayList<String> tokenList = new ArrayList<>();
        ArrayList<String> ticketList = new ArrayList<>();
        ArrayList<String> refList = new ArrayList<>();
        ArrayList<String> dealerPhnList = new ArrayList<>();
        ArrayList<String> customerCodeList = new ArrayList<>();
        ArrayList<String> customerNameList = new ArrayList<>();
        ArrayList<String> addressList = new ArrayList<>();
        ArrayList<String> pincodeList = new ArrayList<>();
        ArrayList<String> cusMobList = new ArrayList<>();
        ArrayList<String> cusEmailList = new ArrayList<>();
        ArrayList<String> modelcodeList = new ArrayList<>();
        ArrayList<String> dopList = new ArrayList<>();
        ArrayList<String> doiList = new ArrayList<>();
        ArrayList<String> calltypeList = new ArrayList<>();
        ArrayList<String> statuscodeList = new ArrayList<>();
        ArrayList<String> cancelledListList = new ArrayList<>();
        ArrayList<String> cancelledDescListList = new ArrayList<>();
        ArrayList<String> branchList = new ArrayList<>();
        ArrayList<String> branchNameList = new ArrayList<>();
        ArrayList<String> callBookList = new ArrayList<>();
        ArrayList<String> callclosedList = new ArrayList<>();
        ArrayList<String> serialNumberlist = new ArrayList<>();
        ArrayList<String> machinestatusList = new ArrayList<>();
        ArrayList<String> soldPartyList = new ArrayList<>();
        ArrayList<String> descriptionList = new ArrayList<>();
        ArrayList<String> originList = new ArrayList<>();


        outerObject = new JSONObject();
        JSONObject innerObj = new JSONObject();
        jsonArray = new JSONArray();


        //token

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getToken();
            if (rcnList.get(i).getToken() != null) {
                tokenList.add(customername);
            }

        }

        for (int j = tokenList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("TokenNo", tokenList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        //ref
        for (int i = 0; i < rcnList.size(); i++) {

            refList.add(refNo);


        }

        for (int j = 0; j < refList.size(); j++) {
            try {
                innerObj.put("ReferenceNo", refList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        //Dealername

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getDelearName();
            if (rcnList.get(i).getDelearName() != null) {
                delaernameList.add(customername);
            }

        }
        for (int j = delaernameList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("DealerName", delaernameList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        //ticketNumber
        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getTicket();
            if (rcnList.get(i).getTicket() != null) {
                ticketList.add(customername);
            }

        }

        for (int j = ticketList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("TicketNumber", ticketList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        //dealerPhn

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getDelearPhone();
            if (rcnList.get(i).getDelearPhone() != null) {
                dealerPhnList.add(customername);
            }

        }

        for (int j = dealerPhnList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("DealerPhone", dealerPhnList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        //CustomerCode
        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getCustomerCode();
            if (rcnList.get(i).getCustomerCode() != null) {
                customerCodeList.add(customername);
            }

        }

        for (int j = customerCodeList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("CustomerCode", customerCodeList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //customername
        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getCustomerName();
            if (rcnList.get(i).getCustomerName() != null) {
                customerNameList.add(customername);
            }

        }

        for (int j = customerNameList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("CustomerName", customerNameList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //address

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getAddress();
            if (rcnList.get(i).getAddress() != null) {
                addressList.add(customername);
            }

        }

        for (int j = addressList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("CustomerAddress", addressList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Pincode

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getPincode();
            if (rcnList.get(i).getPincode() != null) {
                pincodeList.add(customername);
            }

        }

        for (int j = pincodeList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("Pincode", pincodeList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //CustomerMobile

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getCustomerMobile();
            if (rcnList.get(i).getCustomerMobile() != null) {
                cusMobList.add(customername);
            }

        }

        for (int j = cusMobList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("CustomerMobile", cusMobList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //CustomerEmail

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getCustomerEmail();
            if (rcnList.get(i).getCustomerEmail() != null) {
                cusEmailList.add(customername);
            }

        }

        for (int j = cusEmailList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("CustomerEmail", cusEmailList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //ModelCode

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getModelcode();
            if (rcnList.get(i).getModelcode() != null) {
                modelcodeList.add(customername);
            }

        }

        for (int j = modelcodeList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("ModelCode", modelcodeList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //DOP
        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getDop();
            if (rcnList.get(i).getDop() != null) {
                dopList.add(customername);
            }

        }

        for (int j = dopList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("DOP", dopList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //DOI

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getDoi();
            if (rcnList.get(i).getDoi() != null) {
                doiList.add(customername);
            }

        }

        for (int j = doiList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("DOI", doiList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //CallType

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getCallType();
            if (rcnList.get(i).getCallType() != null) {
                calltypeList.add(customername);
            }

        }

        for (int j = calltypeList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("CallType", calltypeList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //StatusCode

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getStatusCode();
            if (rcnList.get(i).getStatusCode() != null) {
                statuscodeList.add(customername);
            }

        }

        for (int j = statuscodeList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("StatusCode", statuscodeList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //CancelledReason
        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getCancelledReason();
            if (rcnList.get(i).getCancelledReason() != null) {
                cancelledListList.add(customername);
            }

        }

        for (int j = cancelledListList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("CancelledReason", cancelledListList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //CancelledReasonDescription

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getCancelledReasonDescription();
            if (rcnList.get(i).getCancelledReasonDescription() != null) {
                cancelledDescListList.add(customername);
            }

        }

        for (int j = cancelledDescListList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("CancelledReasonDescription", cancelledDescListList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Branch

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getBranch();
            if (rcnList.get(i).getBranch() != null) {
                branchList.add(customername);
            }

        }

        for (int j = branchList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("Branch", branchList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //BranchName

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getBranchName();
            if (rcnList.get(i).getBranchName() != null) {
                branchNameList.add(customername);
            }

        }

        for (int j = branchNameList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("BranchName", branchNameList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //CallBookDate

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getCallBookDate();
            if (rcnList.get(i).getCallBookDate() != null) {
                callBookList.add(customername);
            }

        }

        for (int j = callBookList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("CallBookDate", callBookList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //CallClosedDate

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getCallClosedDate();
            if (rcnList.get(i).getCallClosedDate() != null) {
                callclosedList.add(customername);
            }

        }

        for (int j = callclosedList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("CallClosedDate", callclosedList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //orgin


        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getOrigin();
            if (rcnList.get(i).getOrigin() != null) {
                originList.add(customername);
            }

        }

        for (int j = originList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("Orgin", originList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //soldtoparty


        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getSoldToParty();
            if (rcnList.get(i).getSoldToParty() != null) {
                soldPartyList.add(customername);
            }

        }

        for (int j = soldPartyList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("Sold_To_Party", soldPartyList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //description

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getDescription();
            if (rcnList.get(i).getDescription() != null) {
                descriptionList.add(customername);
            }

        }

        for (int j = descriptionList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("CRM_Description", descriptionList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ////machine status

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getMachineStatus();
            if (rcnList.get(i).getMachineStatus() != null) {
                machinestatusList.add(customername);
            }

        }

        for (int j = machinestatusList.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("Machine_Status", machinestatusList.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //serialnumber

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getSerailNumber();
            if (rcnList.get(i).getSerailNumber() != null) {
                serialNumberlist.add(customername);
            }

        }

        for (int j = serialNumberlist.size() - 1; j >= 0; j--) {
            try {
                innerObj.put("Serial_Number", serialNumberlist.get(j));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        jsonArray.put(innerObj);
        try {
            outerObject.put("CRMData", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postTicketNo(outerObject.toString());
    }


    private void sendCSRData(JSONObject jsonObject, String TokenNo) {

        String credentials = "Genius" + ":" + "genius@345&";
        String auth = "Basic "
                + Base64.encodeToString(credentials.getBytes(),
                Base64.NO_WRAP);

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.post("https://ifbapi.ifbsupport.com/api/CSRDATA")

                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", auth)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();


                        String Message = response.optString("Message");
                        Toast.makeText(DeliveryAddressUpdateActivity.this, Message, Toast.LENGTH_LONG).show();
                        postTokenStatus(TokenNo, Message);


                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        successAlert(sucessText, "2");
                        String errormessgae=error.getMessage();
                        postTokenStatus(TokenNo, errormessgae);
                        Toast.makeText(DeliveryAddressUpdateActivity.this, "Wrong", Toast.LENGTH_LONG).show();

                    }
                });


    }


    private void postTokenStatus(String token, String status) {


        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload(AppController.APIURL + "api/post_CRMTokenPushedStatus")

                .addMultipartParameter("TokenNo", token)
                .addMultipartParameter("Remarks", status)
                .addMultipartParameter("SecurityCode", prefManager.getSecurityCode())
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();


                        successAlert(sucessText, "1");

                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        onBackPressed();
                        Toast.makeText(DeliveryAddressUpdateActivity.this, "Wrong", Toast.LENGTH_LONG).show();

                    }
                });


    }

    private void setRefArray() {

        String dop = AppController.changeAnyDateFormat(salesDate, "dd-MMM-yyyy", "yyyy-MM-dd");

        ArrayList<String> tokenList = new ArrayList<>();
        ArrayList<String> serialList = new ArrayList<>();
        ArrayList<String> shortNameList = new ArrayList<>();

        JSONObject innerObj = new JSONObject();
        refArray = new JSONArray();


        //token

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getToken();
            if (rcnList.get(i).getToken() != null) {
                tokenList.add(customername);
            }

        }


        //serial


        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getSerNumber();
            if (rcnList.get(i).getToken() != null) {
                serialList.add(customername);
            }

        }


        //shortname

        for (int i = 0; i < rcnList.size(); i++) {
            String customername = rcnList.get(i).getShortName();
            if (rcnList.get(i).getToken() != null) {
                shortNameList.add(customername);
            }

        }

        for (int j = 0; j < shortNameList.size(); j++) {
            try {
                innerObj.put("serial", rcnList.get(j).getSerNumber());
                innerObj.put("token", rcnList.get(j).getToken());
                innerObj.put("prod_category", shortNameList.get(j));
                innerObj.put("Method", "I");
                innerObj.put("csrid", prefManager.getUserCode());
                innerObj.put("mobile", contactNumber);
                innerObj.put("model", modelCode);
                innerObj.put("dealer_code", prefManager.getSalesPartyCode());
                innerObj.put("DOP", dop);
                innerObj.put("Customer_name", cusname);
                innerObj.put("Customer_address", address);
                innerObj.put("customer_pincode", pincode);
                refArray.put(innerObj);
                innerObj = new JSONObject();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.post("https://api.ifbanalytics.com/v1/CRM/ref_cust")

                .addJSONArrayBody(refArray)
                .addHeaders("auth-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiQWNndXcwMSIsImV4cCI6MTc0MzI2NDg5MSwicGFzc3dvcmQiOiJBY0AzMDQwIyJ9.4lixw3FSbyvPL4tXKdroKYRSvWR8C5LeY7xLwC7uQd8")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();


                        getTicketNumber();


                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        getTicketNumber();
                        // getInformationFromToken();
                    }
                });


    }

    private void postTicketNo(String CRMData) {

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();


        AndroidNetworking.upload(AppController.APIURL + "api/post_CRMDummyReferenceTicket_V2")
                .addMultipartParameter("CRMData", CRMData)
                .addMultipartParameter("UserID", prefManager.getUserId())
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
                            //  getInformationFromToken();
                            Intent intent = new Intent(DeliveryAddressUpdateActivity.this, SalesDashboardActivity.class);
                            startActivity(intent);
                            finish();

                            pd.dismiss();

                        } else {
                            pd.dismiss();
                            Toast.makeText(DeliveryAddressUpdateActivity.this, responseText, Toast.LENGTH_LONG).show();

                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Intent intent = new Intent(DeliveryAddressUpdateActivity.this, SalesDashboardActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG);
                    }
                });
    }


    public void getInformationFromToken() {
        String surl = AppController.APIURL + "api/get_EmployeeSalesByToken?TokenNo=" + tokenno + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputCheckIformation", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseLogin", response);
                        progressBar.dismiss();
                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                JSONArray responseData = job1.optJSONArray("responseData");

                                JSONObject object = responseData.optJSONObject(0);
                                String TokenNo = object.optString("TokenNo");
                                String CustomerName = object.optString("CustomerName");
                                String MobileNo = object.optString("MobileNo");
                                String Product = object.optString("Product");
                                String PurchaseDate = object.optString("PurchaseDate");
                                String Dealer = object.optString("Dealer");
                                String CSRID = object.optString("CSRID");
                                String State = object.optString("State");
                                String MoreThanFifty = object.optString("MoreThanFifty");
                                try {
                                    informationOBJ.put("TokenNo", TokenNo);
                                    informationOBJ.put("CustomerName", CustomerName);
                                    informationOBJ.put("MobileNo", MobileNo);
                                    informationOBJ.put("Product", Product);
                                    informationOBJ.put("PurchaseDate", PurchaseDate);
                                    informationOBJ.put("Dealer", Dealer);
                                    informationOBJ.put("CSRID", CSRID);
                                    informationOBJ.put("State", State);
                                    informationOBJ.put("MoreThanFifty", MoreThanFifty);

                                    postInformation(informationOBJ);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(DeliveryAddressUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
        requestQueue.add(stringRequest);

    }


    private void postInformation(JSONObject jsonObject) {
        String credentials = "Genius" + ":" + "ifb@321";
        String auth = "Basic "
                + Base64.encodeToString(credentials.getBytes(),
                Base64.NO_WRAP);
        Log.d("auth", auth);
        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.post("https://cc.ifbsupport.com/CSRSales/api/CSR")

                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", auth)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        boolean Status = job1.optBoolean("Status");
                        String Message = job1.optString("Message");

                        if (Status) {


                            Intent intent = new Intent(DeliveryAddressUpdateActivity.this, SalesDashboardActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(DeliveryAddressUpdateActivity.this, Message, Toast.LENGTH_LONG).show();


                        } else {
                            pd.dismiss();
                            Toast.makeText(DeliveryAddressUpdateActivity.this, Message, Toast.LENGTH_LONG).show();


                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        Toast.makeText(DeliveryAddressUpdateActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setInstallation() {
        String surl = AppController.APIURL + "api/CommonDDL?ModuleNo=SITY&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
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


                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String value = obj.optString("value");
                                    String id = obj.optString("id");
                                    installation.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleInstallation.add(itemModule);

                                }
                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (DeliveryAddressUpdateActivity.this, android.R.layout.simple_spinner_item,
                                                installation); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spInstallation.setAdapter(spinnerArrayAdapter);
                                if (!prefManager.getSubDealerType().equals("")) {
                                    int index = installation.indexOf(prefManager.getSubDealerType());
                                    spInstallation.setSelection(index);
                                    spInstallation.setEnabled(false);
                                } else {
                                    spInstallation.setSelection(0);
                                }


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
        requestQueue.add(stringRequest);

    }

    private void oduChecking() {
        if (category.equalsIgnoreCase("AIR CONDITIONER")) {

            if (InstallationBy.equalsIgnoreCase("IFB Franchisee")) {
                qtyCheckingNoSID();


            } else {
                qtyChecking();
            }


        } else {

            qtyCheckingNoSID();


        }
    }


    private void checkSerialNumber() {

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SerialNumber", etSerailNumber.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://crmapi.ifbsupport.com/api/csr/serialNumberFinder")

                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", "Bearer Q1NSVVNFUjpjc3JVc2Vy")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        boolean status = job1.optBoolean("status");
                        pd.dismiss();

                        if (status) {

                            JSONArray product = response.optJSONArray("product");
                            JSONObject productOBJ = product.optJSONObject(0);
                            String ModelCode = productOBJ.optString("ModelCode");

                            if (etODUNumber.getText().toString().length() > 0) {
                                checkODUNumber();

                            } else {

                                if (modelCode.equalsIgnoreCase(ModelCode)) {

                                    updateDeliveryAddress();
                                } else {
                                    updateDeliveryAddress();
                                    // Toast.makeText(DeliveryAddressUpdateActivity.this, "Model does not match with serial number", Toast.LENGTH_LONG).show();
                                }
                            }

                        } else {
                            updateDeliveryAddress();
                            //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();


                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        updateDeliveryAddress();
                        // Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();

                        // getInformationFromToken();
                    }
                });
    }

    private void checkSerialNumberForIDU() {

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SerialNumber", etSerailNumber.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://crmapi.ifbsupport.com/api/csr/serialNumberFinder")

                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", "Bearer Q1NSVVNFUjpjc3JVc2Vy")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        boolean status = job1.optBoolean("status");
                        pd.dismiss();

                        if (status) {

                            JSONArray product = response.optJSONArray("product");
                            JSONObject productOBJ = product.optJSONObject(0);
                            String ModelCode = productOBJ.optString("ModelCode");
                            String MatlGroup = productOBJ.optString("MatlGroup");


                            if (MatlGroup.equalsIgnoreCase("IDU") || MatlGroup.equalsIgnoreCase("AC")) {
                                if (modelCode.equalsIgnoreCase(ModelCode)) {

                                    if (etSerailNumberTwo.getText().toString().length() > 0) {
                                        checkSerialSecondNumberForIDU();
                                    } else {
                                        updateDeliveryAddress();
                                    }


                                } else {
                                    updateDeliveryAddress();
                                    //Toast.makeText(DeliveryAddressUpdateActivity.this, "Model does not match with serial number", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                updateDeliveryAddress();
                                //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct IDU Number", Toast.LENGTH_LONG).show();


                            }


                        } else {
                            //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();
                            updateDeliveryAddress();

                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();
                        updateDeliveryAddress();
                        // getInformationFromToken();
                    }
                });
    }


    private void checkSerialSecondNumberForIDU() {

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SerialNumber", etSerailNumberTwo.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://crmapi.ifbsupport.com/api/csr/serialNumberFinder")

                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", "Bearer Q1NSVVNFUjpjc3JVc2Vy")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        boolean status = job1.optBoolean("status");
                        pd.dismiss();

                        if (status) {

                            JSONArray product = response.optJSONArray("product");
                            JSONObject productOBJ = product.optJSONObject(0);
                            String ModelCode = productOBJ.optString("ModelCode");
                            String MatlGroup = productOBJ.optString("MatlGroup");


                            if (MatlGroup.equalsIgnoreCase("IDU") || MatlGroup.equalsIgnoreCase("AC")) {
                                if (modelCode.equalsIgnoreCase(ModelCode)) {

                                    if (etSerailNumberThree.getText().toString().length() > 0) {
                                        checkSerialThirdNumberForIDU();
                                    } else {
                                        updateDeliveryAddress();
                                    }


                                } else {
                                    updateDeliveryAddress();
                                    // Toast.makeText(DeliveryAddressUpdateActivity.this, "Model does not match with serial number", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                updateDeliveryAddress();
                                //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct IDU Number", Toast.LENGTH_LONG).show();


                            }


                        } else {
                            //  Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();

                            updateDeliveryAddress();
                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();
                        updateDeliveryAddress();
                        // getInformationFromToken();
                    }
                });
    }

    private void checkSerialThirdNumberForIDU() {

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SerialNumber", etSerailNumberThree.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://crmapi.ifbsupport.com/api/csr/serialNumberFinder")

                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", "Bearer Q1NSVVNFUjpjc3JVc2Vy")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        boolean status = job1.optBoolean("status");
                        pd.dismiss();

                        if (status) {

                            JSONArray product = response.optJSONArray("product");
                            JSONObject productOBJ = product.optJSONObject(0);
                            String ModelCode = productOBJ.optString("ModelCode");
                            String MatlGroup = productOBJ.optString("MatlGroup");


                            if (MatlGroup.equalsIgnoreCase("IDU") || MatlGroup.equalsIgnoreCase("AC")) {
                                if (modelCode.equalsIgnoreCase(ModelCode)) {

                                    if (etSerailNumberFour.getText().toString().length() > 0) {
                                        checkSerialFourNumberForIDU();
                                    } else {
                                        updateDeliveryAddress();
                                    }


                                } else {
                                    updateDeliveryAddress();
                                    // Toast.makeText(DeliveryAddressUpdateActivity.this, "Model does not match with serial number", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                updateDeliveryAddress();
                                //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct IDU Number", Toast.LENGTH_LONG).show();


                            }


                        } else {
                            //  Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();

                            updateDeliveryAddress();
                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        // Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();
                        updateDeliveryAddress();
                        // getInformationFromToken();
                    }
                });
    }

    private void checkSerialFourNumberForIDU() {

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SerialNumber", etSerailNumberFour.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://crmapi.ifbsupport.com/api/csr/serialNumberFinder")

                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", "Bearer Q1NSVVNFUjpjc3JVc2Vy")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        boolean status = job1.optBoolean("status");
                        pd.dismiss();

                        if (status) {

                            JSONArray product = response.optJSONArray("product");
                            JSONObject productOBJ = product.optJSONObject(0);
                            String ModelCode = productOBJ.optString("ModelCode");
                            String MatlGroup = productOBJ.optString("MatlGroup");


                            if (MatlGroup.equalsIgnoreCase("IDU") || MatlGroup.equalsIgnoreCase("AC")) {
                                if (modelCode.equalsIgnoreCase(ModelCode)) {

                                    if (etSerailNumberFive.getText().toString().length() > 0) {
                                        checkSerialFiveNumberForIDU();
                                    } else {
                                        updateDeliveryAddress();
                                    }


                                } else {
                                    updateDeliveryAddress();
                                    // Toast.makeText(DeliveryAddressUpdateActivity.this, "Model does not match with serial number", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                updateDeliveryAddress();
                                //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct IDU Number", Toast.LENGTH_LONG).show();


                            }


                        } else {
                            updateDeliveryAddress();
                            //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();


                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        updateDeliveryAddress();
                        // Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();

                        // getInformationFromToken();
                    }
                });
    }

    private void checkSerialFiveNumberForIDU() {

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SerialNumber", etSerailNumberFive.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://crmapi.ifbsupport.com/api/csr/serialNumberFinder")

                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", "Bearer Q1NSVVNFUjpjc3JVc2Vy")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        boolean status = job1.optBoolean("status");
                        pd.dismiss();

                        if (status) {

                            JSONArray product = response.optJSONArray("product");
                            JSONObject productOBJ = product.optJSONObject(0);
                            String ModelCode = productOBJ.optString("ModelCode");
                            String MatlGroup = productOBJ.optString("MatlGroup");


                            if (MatlGroup.equalsIgnoreCase("IDU") || MatlGroup.equalsIgnoreCase("AC")) {
                                if (modelCode.equalsIgnoreCase(ModelCode)) {


                                    updateDeliveryAddress();
                                } else {
                                    updateDeliveryAddress();
                                    //Toast.makeText(DeliveryAddressUpdateActivity.this, "Model does not match with serial number", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                updateDeliveryAddress();
                                // Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct IDU Number", Toast.LENGTH_LONG).show();


                            }


                        } else {
                            //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();
                            updateDeliveryAddress();

                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        // Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct Serial Number.", Toast.LENGTH_LONG).show();
                        updateDeliveryAddress();
                        // getInformationFromToken();
                    }
                });
    }

    private void checkODUNumber() {

        final ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SerialNumber", etODUNumber.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://crmapi.ifbsupport.com/api/csr/serialNumberFinder")

                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", "Bearer Q1NSVVNFUjpjc3JVc2Vy")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONObject job1 = response;
                        Log.e("response12", "@@@@@@" + job1);
                        boolean status = job1.optBoolean("status");
                        pd.dismiss();

                        if (status) {
                            JSONArray product = response.optJSONArray("product");
                            JSONObject productOBJ = product.optJSONObject(0);
                            String ModelCode = productOBJ.optString("ModelCode");
                            String MatlGroup = productOBJ.optString("MatlGroup");

                            if (MatlGroup.equalsIgnoreCase("ODU")) {

                                updateDeliveryAddress();


                            } else {
                                //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct ODU Number.", Toast.LENGTH_LONG).show();
                                updateDeliveryAddress();
                            }


                        } else {
                            //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct ODU Number.", Toast.LENGTH_LONG).show();

                            updateDeliveryAddress();
                        }


                        // boolean _status = job1.getBoolean("status");


                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        pd.dismiss();
                        //Toast.makeText(DeliveryAddressUpdateActivity.this, "Please enter correct ODU Number.", Toast.LENGTH_LONG).show();
                        updateDeliveryAddress();
                        // getInformationFromToken();
                    }
                });
    }

    private void qtyChecking() {
        if (qty.equalsIgnoreCase("1")) {
            if (etSerailNumber.getText().toString().length() == 18) {
                if (etODUNumber.getText().toString().length() > 0) {
                    if (!etODUNumber.getText().toString().equals(etSerailNumber.getText().toString())) {
                        checkSerialNumberForIDU();
                    } else {
                        Toast.makeText(DeliveryAddressUpdateActivity.this, "ODU Number and Serial Number can't be same", Toast.LENGTH_LONG).show();
                    }
                } else {
                    checkSerialNumberForIDU();
                }


            } else {
                Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();
            }
        } else if (qty.equalsIgnoreCase("2")) {
            if (etSerailNumber.getText().toString().length() == 18) {
                if (etSerailNumberTwo.getText().toString().length() == 18) {
                    if (etODUNumber.getText().toString().length() > 0) {
                        if (!etODUNumber.getText().toString().equals(etSerailNumber.getText().toString())) {
                            checkSerialNumberForIDU();
                        } else {
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "ODU Number and Serial Number can't be same", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        checkSerialNumberForIDU();
                    }
                } else {
                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                }


            } else {
                Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();
            }
        } else if (qty.equalsIgnoreCase("3")) {
            if (etSerailNumber.getText().toString().length() == 18) {
                if (etSerailNumberTwo.getText().toString().length() == 18) {
                    if (etSerailNumberThree.getText().toString().length() == 18) {
                        if (etODUNumber.getText().toString().length() > 0) {
                            if (!etODUNumber.getText().toString().equals(etSerailNumber.getText().toString())) {
                                checkSerialNumberForIDU();
                            } else {
                                Toast.makeText(DeliveryAddressUpdateActivity.this, "ODU Number and Serial Number can't be same", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            checkSerialNumberForIDU();
                        }
                    } else {
                        Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                }


            } else {
                Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();
            }
        } else if (qty.equalsIgnoreCase("4")) {
            if (etSerailNumber.getText().toString().length() == 18) {
                if (etSerailNumberTwo.getText().toString().length() == 18) {
                    if (etSerailNumberThree.getText().toString().length() == 18) {
                        if (etSerailNumberFour.getText().toString().length() == 18) {
                            if (etODUNumber.getText().toString().length() > 0) {
                                if (!etODUNumber.getText().toString().equals(etSerailNumber.getText().toString())) {
                                    checkSerialNumberForIDU();
                                } else {
                                    Toast.makeText(DeliveryAddressUpdateActivity.this, "ODU Number and Serial Number can't be same", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                checkSerialNumberForIDU();
                            }
                        } else {
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                }


            } else {
                Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();
            }
        } else if (qty.equalsIgnoreCase("5")) {
            if (etSerailNumber.getText().toString().length() == 18) {
                if (etSerailNumberTwo.getText().toString().length() == 18) {
                    if (etSerailNumberThree.getText().toString().length() == 18) {
                        if (etSerailNumberFour.getText().toString().length() == 18) {
                            if (etSerailNumberFive.getText().toString().length() == 18) {
                                if (etODUNumber.getText().toString().length() > 0) {
                                    if (!etODUNumber.getText().toString().equals(etSerailNumber.getText().toString())) {
                                        checkSerialNumberForIDU();
                                    } else {
                                        Toast.makeText(DeliveryAddressUpdateActivity.this, "ODU Number and Serial Number can't be same", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    checkSerialNumberForIDU();
                                }
                            } else {
                                Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                }


            } else {
                Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void qtyCheckingNoSID() {
        if (qty.equalsIgnoreCase("1")) {
            if (etSerailNumber.getText().toString().length() > 0) {
                if (etSerailNumber.getText().toString().length() == 18) {
                    if (etODUNumber.getText().toString().length() > 0) {
                        if (!etODUNumber.getText().toString().equals(etSerailNumber.getText().toString())) {
                            updateDeliveryAddress();
                        } else {
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "ODU Number and Serial Number can't be same", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        updateDeliveryAddress();
                    }


                } else {
                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();
                }
            } else {
                updateDeliveryAddress();
            }

        } else if (qty.equalsIgnoreCase("2")) {
            if (etSerailNumber.getText().toString().length() > 0) {
                if (etSerailNumber.getText().toString().length() == 18) {
                    if (etSerailNumberTwo.getText().toString().length() == 18) {
                        if (etODUNumber.getText().toString().length() > 0) {
                            if (!etODUNumber.getText().toString().equals(etSerailNumber.getText().toString())) {
                                updateDeliveryAddress();
                            } else {
                                Toast.makeText(DeliveryAddressUpdateActivity.this, "ODU Number and Serial Number can't be same", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            updateDeliveryAddress();
                        }
                    } else {
                        Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                    }


                } else {
                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();
                }
            } else {
                updateDeliveryAddress();
            }

        } else if (qty.equalsIgnoreCase("3")) {
            if (etSerailNumber.getText().length() > 0) {
                if (etSerailNumber.getText().toString().length() == 18) {
                    if (etSerailNumberTwo.getText().toString().length() == 18) {
                        if (etSerailNumberThree.getText().toString().length() == 18) {
                            if (etODUNumber.getText().toString().length() > 0) {
                                if (!etODUNumber.getText().toString().equals(etSerailNumber.getText().toString())) {
                                    updateDeliveryAddress();
                                } else {
                                    Toast.makeText(DeliveryAddressUpdateActivity.this, "ODU Number and Serial Number can't be same", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                updateDeliveryAddress();
                            }
                        } else {
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                    }


                } else {
                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();
                }
            } else {
                updateDeliveryAddress();
            }

        } else if (qty.equalsIgnoreCase("4")) {
            if (etSerailNumber.getText().toString().length() > 0) {
                if (etSerailNumber.getText().toString().length() == 18) {
                    if (etSerailNumberTwo.getText().toString().length() == 18) {
                        if (etSerailNumberThree.getText().toString().length() == 18) {
                            if (etSerailNumberFour.getText().toString().length() == 18) {
                                if (etODUNumber.getText().toString().length() > 0) {
                                    if (!etODUNumber.getText().toString().equals(etSerailNumber.getText().toString())) {
                                        updateDeliveryAddress();
                                    } else {
                                        Toast.makeText(DeliveryAddressUpdateActivity.this, "ODU Number and Serial Number can't be same", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    updateDeliveryAddress();
                                }
                            } else {
                                Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                    }


                } else {
                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();
                }
            } else {
                updateDeliveryAddress();
            }

        } else if (qty.equalsIgnoreCase("5")) {
            if (etSerailNumber.getText().toString().length() > 0) {
                if (etSerailNumber.getText().toString().length() == 18) {
                    if (etSerailNumberTwo.getText().toString().length() == 18) {
                        if (etSerailNumberThree.getText().toString().length() == 18) {
                            if (etSerailNumberFour.getText().toString().length() == 18) {
                                if (etSerailNumberFive.getText().toString().length() == 18) {
                                    if (etODUNumber.getText().toString().length() > 0) {
                                        if (!etODUNumber.getText().toString().equals(etSerailNumber.getText().toString())) {
                                            updateDeliveryAddress();
                                        } else {
                                            Toast.makeText(DeliveryAddressUpdateActivity.this, "ODU Number and Serial Number can't be same", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        updateDeliveryAddress();
                                    }
                                } else {
                                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();

                    }


                } else {
                    Toast.makeText(DeliveryAddressUpdateActivity.this, "Please Enter 18 digits serial number", Toast.LENGTH_LONG).show();
                }
            } else {
                updateDeliveryAddress();
            }

        }
    }

    private void emailcheck1() {
        String surl = AppController.APIURL + "api/CheckInvalidEmailID?EmailID=" + etEmailId.getText().toString();
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
                                if (etSerailNumber.getText().toString().length() > 0) {
                                    getSerailNumber();
                                } else {
                                    oduChecking();
                                }

                            } else {
                                invalidemailalert(responseText);
                            }

                            //boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(DeliveryAddressUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
        requestQueue.add(stringRequest);

    }


    private void invalidemailalert(String text) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DeliveryAddressUpdateActivity.this, R.style.CustomDialogNew);
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

    private void pincodecheck(final String pincode) {
        Log.d("hitr", "6");
        String surl = "https://cloud.geniusconsultant.com/GeniusPinCodeApi/api/PinCode?id=" + pincode;
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();
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
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
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
                                    (DeliveryAddressUpdateActivity.this, android.R.layout.simple_spinner_item,
                                            area); //selected item will look like a spinner set from XML
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spArea.setAdapter(spinnerArrayAdapter);

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setState() {
        Log.d("hitr", "3");
        String surl = AppController.APIURL + "api/CommonDDL?ModuleNo=2&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("stateinput", surl);
        ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading");
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responsestate", response);
                        pd.dismiss();
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

                                //  getInformationForFiftySales();

                                spState.setVisibility(View.VISIBLE);
                                spCity.setVisibility(View.VISIBLE);


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (DeliveryAddressUpdateActivity.this, android.R.layout.simple_spinner_item,
                                                state); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spState.setAdapter(spinnerArrayAdapter);
                                spState.setSelection(0);


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setCity() {
        Log.d("hitr", "4");
        tvCityName.setVisibility(View.GONE);
        String surl = AppController.APIURL + "api/CommonDDL?ModuleNo=14&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        ProgressDialog pd = new ProgressDialog(DeliveryAddressUpdateActivity.this);
        pd.setMessage("Loading");
        pd.show();
        pd.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseCategory", response);
                        pd.dismiss();
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
                                        (DeliveryAddressUpdateActivity.this, android.R.layout.simple_spinner_item,
                                                city); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCity.setAdapter(spinnerArrayAdapter);
                                spCity.setSelection(0);


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

                Toast.makeText(DeliveryAddressUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "city");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    private void setSalesType() {
        String surl = AppController.APIURL + "api/CommonDDL?ModuleNo=SISY&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
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
                        salestype.clear();
                        moduleSalesType.clear();


                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String value = obj.optString("value");
                                    String id = obj.optString("id");
                                    salestype.add(value);
                                    SpinnerItemModule itemModule = new SpinnerItemModule(value, id);
                                    moduleSalesType.add(itemModule);

                                }
                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (DeliveryAddressUpdateActivity.this, android.R.layout.simple_spinner_item,
                                                salestype); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spSalesType.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
        requestQueue.add(stringRequest);

    }


    private void getCusDetail() {

        String surl = "https://crmapi.ifbsupport.com/api/v1/customers/search?contact=" + contactNumber;
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
                            Boolean Status = job1.optBoolean("Status");

                            JSONArray Data = job1.optJSONArray("Data");
                            if (Data != null) {
                                if (Data.length() > 0) {
                                    JSONObject jobj = Data.getJSONObject(0);
                                    String emailID = jobj.optString("zzemail");
                                    etEmailId.setText(emailID);
                                    String postalCode = jobj.optString("zzpost_code1");
                                    etPinCode.setText(postalCode);
                                    String landMark = jobj.optString("zzstr_suppl1");
                                    etLandMark.setText(landMark);
                                    String street = jobj.optString("zzstreet");
                                    etStreetName.setText(street);

                                    String House_num1 = jobj.optString("House_num1");
                                    etHouse.setText(House_num1);
                                }
                            }


                            //boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(DeliveryAddressUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer ZW55dXNlcjplbnl1JGVy");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private void getSerailNumber() {

        String surl = "https://nonfss.geniusconsultant.com/IFBiOSApi/api/Get_SalesSerialNumber?SerialNo=" + etSerailNumber.getText().toString() + "&SecurityCode=GCL";
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
                            Boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                Toast.makeText(DeliveryAddressUpdateActivity.this, "This Serial Number already exists", Toast.LENGTH_LONG).show();
                            } else {
                                if (etODUNumber.getText().toString().length() > 0) {
                                    getODUNumber();
                                } else {
                                    oduChecking();
                                }

                            }


                            //boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(DeliveryAddressUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private void getODUNumber() {

        String surl = "https://nonfss.geniusconsultant.com/IFBiOSApi/api/Get_SalesSerialNumber?SerialNo=" + etODUNumber.getText().toString() + "&SecurityCode=GCL";
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
                            Boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                Toast.makeText(DeliveryAddressUpdateActivity.this, "This Serial Number already exists", Toast.LENGTH_LONG).show();
                            } else {
                                oduChecking();
                            }


                            //boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DeliveryAddressUpdateActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(DeliveryAddressUpdateActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryAddressUpdateActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


}