package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.cordova.ifb.R;
import io.cordova.ifb.module.ModelSpinnerModel;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.NetworkConnectionCheck;
import io.cordova.ifb.utility.PrefManager;
import io.cordova.ifb.utility.ValidUtils;

public class DummySaleManageActivity extends AppCompatActivity {
    Button btnContinue, btnHide, btnSave;
    LinearLayout llDetails2, llDetaits;
    TextView tvCategory, tvModel, tvTitle, tvFname, tvLname, tvMob, tvAltMob, tvEmail, tvPinCode, tvState, tvCity, tvArea, tvHouse, tvStreet, tvLand, tvDateTitle;
    TextView tvDate, tvInvoiceValue, tvScheme;
    LinearLayout llExYes, llExYesD, llExNo, llExNoD, llSchYes, llSchYesD, llSchNo, llSchNoD, llScheme;
    Spinner spCategory, spModel, spTitle, spState, spCity, spArea, spScheme;

    ArrayList<SpinnerItemModule> moduleCategory = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();

    ArrayList<ModelSpinnerModel> moduleModel = new ArrayList<>();
    ArrayList<String> model = new ArrayList<>();

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
    AlertDialog alertDialog, aletdialog2;
    PrefManager prefManager;
    String categoryId = "";
    EditText etQuantity;
    EditText etFirstName, etPinCode, etRemark, etInvoiceValue, etInvoiceNumber, etLandMark, etStreetName, etHouse, etEmailId, etPhnNumber, etMobNumber, etLastName;
    LinearLayout llHide;
    LinearLayout llLoader, llMain;
    NetworkConnectionCheck connectionCheck;
    String invalidEmail;
    AlertDialog alet1;
    boolean emailstatus;
    String altmob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_sale_manage);
        initialize();
        if (connectionCheck.isNetworkAvailable()) {
            setCategory();
        } else {
            Toast.makeText(getApplicationContext(), "Your internet connection is slow", Toast.LENGTH_LONG).show();
        }
        onClick();
    }

    private void initialize() {
        btnContinue = (Button) findViewById(R.id.btnContinue);
        connectionCheck = new NetworkConnectionCheck(DummySaleManageActivity.this);
        btnHide = (Button) findViewById(R.id.btnHide);
        btnSave = (Button) findViewById(R.id.btnSave);
        llDetails2 = (LinearLayout) findViewById(R.id.llDetails2);
        llDetaits = (LinearLayout) findViewById(R.id.llDetaits);
        prefManager = new PrefManager(DummySaleManageActivity.this);
        String next = "<font color='#EE0000'>*</font>";

        tvCategory = (TextView) findViewById(R.id.tvCategory);
        String category = "CATEGORY ";
        tvCategory.setText(Html.fromHtml(category + next));

        tvModel = (TextView) findViewById(R.id.tvModel);
        String model = "MODEL";
        tvModel.setText(Html.fromHtml(model + next));

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        String title = "TITLE ";
        tvTitle.setText(Html.fromHtml(title + next));

        tvFname = (TextView) findViewById(R.id.tvFname);
        String fname = "FIRST NAME ";
        tvFname.setText(Html.fromHtml(fname + next));

        tvLname = (TextView) findViewById(R.id.tvLname);
        String lname = "LAST NAME ";
        tvLname.setText(Html.fromHtml(lname + next));

        tvMob = (TextView) findViewById(R.id.tvMob);
        String mob = "10 DIGITS MOBILE NUMBER ";
        tvMob.setText(Html.fromHtml(mob + next));

        tvAltMob = (TextView) findViewById(R.id.tvAltMob);
        String altmob = "ALTERNATIVE NUMBER ";
        tvAltMob.setText(altmob );

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        String email = "EMAIL ";
        tvEmail.setText(Html.fromHtml(email + next));

        tvPinCode = (TextView) findViewById(R.id.tvPinCode);
        String pin = "DELIVERY PIN CODE ";
        tvPinCode.setText(Html.fromHtml(pin + next));

        tvState = (TextView) findViewById(R.id.tvState);
        String state = "STATE ";
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
        spModel = (Spinner) findViewById(R.id.spModel);
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
        if(monthname.equals("January")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else if (monthname.equals("February")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else if (monthname.equals("March")){
            int futureyear = y - 1;
            financialYear = futureyear+"-"+year;
        }else {
            int futureyear = y + 1;
            financialYear = year+"-"+futureyear;
        }


        llSubmit = (LinearLayout) findViewById(R.id.llSubmit);

        etQuantity = (EditText) findViewById(R.id.etQuantity);

        llHide = (LinearLayout) findViewById(R.id.llHide);
        llLoader = (LinearLayout) findViewById(R.id.llLoader);
        llMain = (LinearLayout) findViewById(R.id.llMain);
    }

    private void onClick() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDetails2.setVisibility(View.VISIBLE);
                llDetaits.setVisibility(View.GONE);

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
                if (etPhnNumber.getText().toString().length()>0){
                    altmob=etPhnNumber.getText().toString();
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etEmailId.clearFocus();
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

                                                                            if (!etMobNumber.getText().toString().equals("0000000000")){
                                                                                if (!etMobNumber.getText().toString().equals("1111111111")){
                                                                                    if (!etMobNumber.getText().toString().equals("2222222222")){
                                                                                        if (!etMobNumber.getText().toString().equals("3333333333")){
                                                                                            if (!etMobNumber.getText().toString().equals("4444444444")){
                                                                                                if (!etMobNumber.getText().toString().contains("5555555555")){
                                                                                                    if (!etMobNumber.getText().toString().contains("6666666666")){
                                                                                                        if (!etMobNumber.getText().toString().contains("7777777777")){
                                                                                                            if (!etMobNumber.getText().toString().contains("8888888888")){
                                                                                                                if (!etMobNumber.getText().toString().contains("9999999999")){

                                                                                                                        emailcheck();

                                                                                                                }else {
                                                                                                                    etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                    etMobNumber.requestFocus();
                                                                                                                }

                                                                                                            }else {
                                                                                                                etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                etMobNumber.requestFocus();
                                                                                                            }

                                                                                                        }else {
                                                                                                            etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                            etMobNumber.requestFocus();
                                                                                                        }

                                                                                                    }else {
                                                                                                        etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                        etMobNumber.requestFocus();
                                                                                                    }

                                                                                                }else {
                                                                                                    etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                    etMobNumber.requestFocus();
                                                                                                }

                                                                                            }else {
                                                                                                etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                etMobNumber.requestFocus();
                                                                                            }

                                                                                        }else {
                                                                                            etMobNumber.setError("Please neter Valid Phone Number");
                                                                                            etMobNumber.requestFocus();
                                                                                        }

                                                                                    }else {
                                                                                        etMobNumber.setError("Please neter Valid Phone Number");
                                                                                        etMobNumber.requestFocus();
                                                                                    }

                                                                                }else {
                                                                                    etMobNumber.setError("Please neter Valid Phone Number");
                                                                                    etMobNumber.requestFocus();
                                                                                }

                                                                            }else {
                                                                                etMobNumber.setError("Please neter Valid Phone Number");
                                                                                etMobNumber.requestFocus();
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
                                                                                            if (invoicevalue > 4500.00 || invoicevalue == 4500.00) {

                                                                                                if (!etMobNumber.getText().toString().equals("0000000000")){
                                                                                                    if (!etMobNumber.getText().toString().equals("1111111111")){
                                                                                                        if (!etMobNumber.getText().toString().equals("2222222222")){
                                                                                                            if (!etMobNumber.getText().toString().equals("3333333333")){
                                                                                                                if (!etMobNumber.getText().toString().equals("4444444444")){
                                                                                                                    if (!etMobNumber.getText().toString().contains("5555555555")){
                                                                                                                        if (!etMobNumber.getText().toString().contains("6666666666")){
                                                                                                                            if (!etMobNumber.getText().toString().contains("7777777777")){
                                                                                                                                if (!etMobNumber.getText().toString().contains("8888888888")){
                                                                                                                                    if (!etMobNumber.getText().toString().contains("9999999999")){
                                                                                                                                        if (etQuantity.getText().toString().equals("2")||etQuantity.getText().toString().equals("3")||etQuantity.getText().toString().equals("4")||etQuantity.getText().toString().equals("5")){

                                                                                                                                            quatityalert();
                                                                                                                                        }else {
                                                                                                                                            emailcheck1();
                                                                                                                                        }

                                                                                                                                    }else {
                                                                                                                                        etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                                        etMobNumber.requestFocus();
                                                                                                                                    }

                                                                                                                                }else {
                                                                                                                                    etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                                    etMobNumber.requestFocus();
                                                                                                                                }

                                                                                                                            }else {
                                                                                                                                etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                                etMobNumber.requestFocus();
                                                                                                                            }

                                                                                                                        }else {
                                                                                                                            etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                            etMobNumber.requestFocus();
                                                                                                                        }

                                                                                                                    }else {
                                                                                                                        etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                        etMobNumber.requestFocus();
                                                                                                                    }

                                                                                                                }else {
                                                                                                                    etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                    etMobNumber.requestFocus();
                                                                                                                }

                                                                                                            }else {
                                                                                                                etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                                etMobNumber.requestFocus();
                                                                                                            }

                                                                                                        }else {
                                                                                                            etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                            etMobNumber.requestFocus();
                                                                                                        }

                                                                                                    }else {
                                                                                                        etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                        etMobNumber.requestFocus();
                                                                                                    }

                                                                                                }else {
                                                                                                    etMobNumber.setError("Please neter Valid Phone Number");
                                                                                                    etMobNumber.requestFocus();
                                                                                                }



                                                                                            } else {
                                                                                                Toast.makeText(getApplicationContext(), "Invoice value should be 4500.00 to 75000.00", Toast.LENGTH_LONG).show();

                                                                                            }


                                                                                        } else {
                                                                                            Toast.makeText(getApplicationContext(), "First name and Last name Should be diiferent", Toast.LENGTH_LONG).show();
                                                                                        }


                                                                                    } else {
                                                                                        Toast.makeText(getApplicationContext(), "Invoice value should not be greater than MRP price", Toast.LENGTH_LONG).show();
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

        spModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelId = "";
                if (position > 0) {
                    modelId = moduleModel.get(position).getId();
                    Log.d("modelId", modelId);
                    mrp = moduleModel.get(position).getMrp();
                    Log.d("mrp", mrp);
                    mrpPrice = Float.parseFloat(mrp);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                areaName = moduleArea.get(position).getItem().replaceAll("\\s+", "-");
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
                    remarks = etRemark.getText().toString().replaceAll("\\s+", "-");
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
                Intent intent = new Intent(DummySaleManageActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        llHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDetails2.setVisibility(View.GONE);
                llDetaits.setVisibility(View.VISIBLE);

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
        final int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(DummySaleManageActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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
        }, year, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clockicon);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker().setMaxDate((long) (System.currentTimeMillis() - 1000));

        // Popup the dialog.

        dialog.show();
    }


    private void setCategory() {

        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=4&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
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
                                        (DummySaleManageActivity.this, android.R.layout.simple_spinner_item,
                                                category); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCategory.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Your internet connection is slow", Toast.LENGTH_LONG).show();


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setModel(String categoryId) {
        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=18&ID=" + categoryId + "&ID1=0&ID2=0&ID3=0&SecurityCode=IFB";

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
                                    ModelSpinnerModel itemModule = new ModelSpinnerModel(value, id, MRP);
                                    moduleModel.add(itemModule);

                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (DummySaleManageActivity.this, android.R.layout.simple_spinner_item,
                                                model); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spModel.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setTitle() {
        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=42&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=IFB";
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

                                }
                                setState();


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (DummySaleManageActivity.this, android.R.layout.simple_spinner_item,
                                                title); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spTitle.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Your internet connection is slow", Toast.LENGTH_LONG).show();


                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
        requestQueue.add(stringRequest);

    }


    private void pincodecheck(final String pincode) {
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
                            int index=state.indexOf(STATENAME);
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
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
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
                                    (DummySaleManageActivity.this, android.R.layout.simple_spinner_item,
                                            area); //selected item will look like a spinner set from XML
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spArea.setAdapter(spinnerArrayAdapter);

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setScheme() {
        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=35&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
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
                                        (DummySaleManageActivity.this, android.R.layout.simple_spinner_item,
                                                scheme); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spScheme.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setState() {
        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=2&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
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
                                        (DummySaleManageActivity.this, android.R.layout.simple_spinner_item,
                                                state); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spState.setAdapter(spinnerArrayAdapter);
                                spState.setSelection(42);


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Your internet connection is slow", Toast.LENGTH_LONG).show();

                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setCity() {
        tvCityName.setVisibility(View.GONE);
        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=14&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
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
                                        (DummySaleManageActivity.this, android.R.layout.simple_spinner_item,
                                                city); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCity.setAdapter(spinnerArrayAdapter);
                                spCity.setSelection(2200);


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                llLoader.setVisibility(View.VISIBLE);
                llMain.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Your internet connection is slow", Toast.LENGTH_LONG).show();

                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
        requestQueue.add(stringRequest);

    }

    private void posttempSale() {
        String surl =  AppController.APIURL+"api/post_EmployeeTemporarySales?TransNo=0&AEMEmployeeID=" + prefManager.getUserId() + "&_SalesDate=" + salesDate + "&FinancialYear=" + financialYear + "&Month=" + monthname + "&CategoryID=" + categoryId + "&Quantity=1&xmldata=0&UserID=" + prefManager.getUserId() + "&BranchID=" + prefManager.getBranchId() + "&ModelID=" + modelId + "&CustomerName=" + customerName.replaceAll("\\s+", "-") + "&CustomerPhNo=" + etMobNumber.getText().toString() + "&CustomerPinCode=" + etPinCode.getText().toString() + "&CustomerEmail=" + etEmailId.getText().toString() + "&InvoiceNo=0&FinanceScheme=0&DeliveryAddress=" + etLandMark.getText().toString().replaceAll("\\s+", "-") + "&FirstName=" + etFirstName.getText().toString().replaceAll("\\s+", "-") + "&LastName=" + etLastName.getText().toString().replaceAll("\\s+", "-") + "&CustomerAlternateNumber=" + altmob + "&HouseNo=" + etHouse.getText().toString().replaceAll("\\s+", "-") + "&StreetName=" + etStreetName.getText().toString().replaceAll("\\s+", "-") + "&Landmark=" + etLandMark.getText().toString().replaceAll("\\s+", "-") + "&Title=" + titleId + "&StateID=" + stateId + "&City=" + tvCityName.getText().toString().replaceAll("\\s+", "-") + "&InvoiceValue=0&Remarks=0&UnderExchange=0&SalesEntryFlag=-1&Area=" + areaName + "&SecurityCode=" + prefManager.getSecurityCode() + "&TempNo=0&Token=0&IsActive=1&Operation=3";
        Log.d("posttempsale", surl);
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
                                successAlert1();
                                btnContinue.setEnabled(false);
                                btnHide.setVisibility(View.VISIBLE);
                                btnHide.setEnabled(false);
                                btnContinue.setVisibility(View.GONE);


                            } else {
                                Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();

                            }


                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(DummySaleManageActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setSalesEntry() {
        String surl =  AppController.APIURL+"api/post_SalesEntry?TransNo=0&AEMEmployeeID=" + prefManager.getUserId() + "&_SalesDate=" + salesDate + "&FinancialYear=" + financialYear + "&Month=" + monthname + "&CategoryID=" + categoryId + "&Quantity=" + quantity + "&xmldata=0&UserID=" + prefManager.getUserId() + "&BranchID=" + prefManager.getBranchId() + "&ModelID=" + modelId + "&CustomerName=" + customerName.replaceAll("\\s+", "-") + "&CustomerPhNo=" + etMobNumber.getText().toString() + "&CustomerPinCode=" + etPinCode.getText().toString() + "&CustomerEmail=" + etEmailId.getText().toString() + "&InvoiceNo=" + etInvoiceNumber.getText().toString() + "&FinanceScheme=" + schemeId + "&DeliveryAddress=" + etLandMark.getText().toString().replaceAll("\\s+", "-") + "&FirstName=" + etFirstName.getText().toString().replaceAll("\\s+", "-") + "&LastName=" + etLastName.getText().toString().replaceAll("\\s+", "-") + "&CustomerAlternateNumber=" + altmob + "&HouseNo=" + etHouse.getText().toString().replaceAll("\\s+", "-") + "&StreetName=" + etStreetName.getText().toString().replaceAll("\\s+", "-") + "&Landmark=" + etLandMark.getText().toString().replaceAll("\\s+", "-") + "&Title=" + titleId + "&StateID=" + stateId + "&City=" + tvCityName.getText().toString().replaceAll("\\s+", "-") + "&InvoiceValue=" + etInvoiceValue.getText().toString() + "&Remarks=" + remarks + "&UnderExchange=" + underExchange + "&SalesEntryFlag=-1&Area=" + areaName + "&SecurityCode=" + prefManager.getSecurityCode();
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
                                successAlert();


                            } else {
                                Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();

                            }


                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(DummySaleManageActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
        requestQueue.add(stringRequest);

    }


    private void successAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DummySaleManageActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText(responseText);

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                Intent intent = new Intent(DummySaleManageActivity.this, SalesReportActivity.class);
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

    private void successAlert1() {
       AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DummySaleManageActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText(responseText);

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aletdialog2.dismiss();
                Intent intent = new Intent(DummySaleManageActivity.this, DummySaleReportActivity.class);
                startActivity(intent);
                finish();
            }
        });

        aletdialog2 = dialogBuilder.create();
        aletdialog2.setCancelable(false);
        Window window = aletdialog2.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        aletdialog2.show();
    }


    private void quatityalert() {
       AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DummySaleManageActivity.this, R.style.CustomDialogNew);
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


    private void invalidemailalert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DummySaleManageActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_inavalid_email, null);
        dialogBuilder.setView(dialogView);

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


    private void emailcheck() {
        String surl =  AppController.APIURL+"api/CheckInvalidEmailID?EmailID="+etEmailId.getText().toString();
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
                            responseText = job1.optString("responseText");
                             emailstatus = job1.optBoolean("responseStatus");
                             if (emailstatus){
                                 posttempSale();
                             }else {
                                 invalidemailalert();
                             }

                            //boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(DummySaleManageActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
        requestQueue.add(stringRequest);

    }

    private void emailcheck1() {
        String surl =  AppController.APIURL+"api/CheckInvalidEmailID?EmailID="+etEmailId.getText().toString();
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
                            responseText = job1.optString("responseText");
                          boolean emailstatus2 = job1.optBoolean("responseStatus");
                            if (emailstatus2){
                                setSalesEntry();
                            }else {
                                invalidemailalert();
                            }

                            //boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(DummySaleManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                Toast.makeText(DummySaleManageActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DummySaleManageActivity.this);
        requestQueue.add(stringRequest);

    }

}
