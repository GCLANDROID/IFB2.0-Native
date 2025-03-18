package io.cordova.ifb.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developers.imagezipper.ImageZipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.cordova.ifb.R;
import io.cordova.ifb.module.ModelSpinnerModel;
import io.cordova.ifb.module.SpinnerItemModule;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.GPSTracker;
import io.cordova.ifb.utility.PostDisplayMatrixService;
import io.cordova.ifb.utility.PrefManager;
import io.cordova.ifb.utility.UploadObject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CVisitManageActivity extends AppCompatActivity {
    EditText etCusName, etCusPhn, etEngName, etEngPhn, etRemarks, etCusLName, etCusEmail, etCusMob;
    Spinner spYesNo, spCategory, spModel;
    ImageView imgBack, imgHome, imgCamera, imgPic;
    ArrayList<String> yesList = new ArrayList<>();
    ArrayList<SpinnerItemModule> moduleCategory = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();

    ArrayList<ModelSpinnerModel> moduleModel = new ArrayList<>();
    ArrayList<String> model = new ArrayList<>();

    ProgressDialog progressDialog;
    PrefManager prefManager;
    String categoryId = "";
    String modelId = "";
    private String encodedImage;
    private Uri imageUri;
    private static final int CAMERA_REQUEST = 1;
    File file, compressedImageFile, file1;
    private static final int REQUEST_GALLERY_CODE = 200;
    Spinner spState, spArea, spTitle;
    TextView tvCity;
    EditText etHouseNo, etLandMark, etStreetName, etEngLName, etEngMob, etPinCode;

    ArrayList<SpinnerItemModule> moduleTitle = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();

    ArrayList<SpinnerItemModule> moduleState = new ArrayList<>();
    ArrayList<String> state = new ArrayList<>();


    ArrayList<SpinnerItemModule> moduleArea = new ArrayList<>();
    ArrayList<String> area = new ArrayList<>();

    String STATENAME, PINCODE, REGIONNAME;

    String vdate, date;
    TextView tvDate;

    private static final String SERVER_PATH =  AppController.APIURL+"api/";
    private PostDisplayMatrixService uploadService;
    ProgressDialog progressDialog1;
    String titleId = "";
    String stateId = "";
    String areaName = "";
    String withEng = "";
    GPSTracker gps;
    ;
    double latitude, longitude;
    String cuuaddress;
    AlertDialog alerDialog1;
    Button btnSubmit;
    int picflag;
    TextView tvTitle,tvCusFName,tvCusLName,tvCusMob,tvPinCode,tvState,tvCityName,tvArea,tvRemark,tvWith,tvSFname,tvSLname,tvSMob,tvCategory,tvModel,tvImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvisit_manage);
        initView();
        setCategory();
        onClick();
    }


    private void initView() {
        prefManager = new PrefManager(CVisitManageActivity.this);
        etCusName = (EditText) findViewById(R.id.etCusName);
        etCusPhn = (EditText) findViewById(R.id.etCusPhn);
        etEngName = (EditText) findViewById(R.id.etEngName);
        etEngPhn = (EditText) findViewById(R.id.etEngPhn);
        etRemarks = (EditText) findViewById(R.id.etRemarks);
        etCusLName = (EditText) findViewById(R.id.etCusLName);
        etCusEmail = (EditText) findViewById(R.id.etCusEmail);
        etHouseNo = (EditText) findViewById(R.id.etHouseNo);
        etLandMark = (EditText) findViewById(R.id.etLandMark);
        etStreetName = (EditText) findViewById(R.id.etStreetName);
        etEngLName = (EditText) findViewById(R.id.etEngLName);
        etEngMob = (EditText) findViewById(R.id.etEngMob);
        etCusMob = (EditText) findViewById(R.id.etCusMob);
        etPinCode = (EditText) findViewById(R.id.etPinCode);


        spYesNo = (Spinner) findViewById(R.id.spYesNo);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spModel = (Spinner) findViewById(R.id.spModel);
        spState = (Spinner) findViewById(R.id.spState);
        spArea = (Spinner) findViewById(R.id.spArea);
        spTitle = (Spinner) findViewById(R.id.spTitle);


        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgCamera = (ImageView) findViewById(R.id.imgCamera);
        imgPic = (ImageView) findViewById(R.id.imgPic);


        //yesno set
        yesList.add("Please Select");
        yesList.add("YES");
        yesList.add("NO");
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yesList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYesNo.setAdapter(aa);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);


        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setMessage("Loading...");
        progressDialog1.setCancelable(false);

        tvCity = (TextView) findViewById(R.id.tvCity);


        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDateTimeString = sdf.format(d);

        Date dof = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(dof);
        vdate = formattedDate + " " + "-" + " " + currentDateTimeString;
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDate.setText(vdate);


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        // Change base URL to your upload server URL.
        uploadService = (PostDisplayMatrixService) new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PostDisplayMatrixService.class);


        gps = new GPSTracker(CVisitManageActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            Log.d("saikatdas", String.valueOf(latitude));
            longitude = gps.getLongitude();

        } else {
// can't get location
// GPS or Network is not enabled
// Ask user to enable GPS/network in settings
            // gps.showSettingsAlert();
        }

        cuuaddress = getAddress(latitude, longitude);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        String next = "<font color='#EE0000'>*</font>";

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        String title = "Title:";
        tvTitle.setText(Html.fromHtml(title + next));

        tvCusFName = (TextView) findViewById(R.id.tvCusFName);
        String cusfname = "First Name:";
        tvCusFName.setText(Html.fromHtml(cusfname + next));


        tvCusLName = (TextView) findViewById(R.id.tvCusLName);
        String cuslname = "Last Name: ";
        tvCusLName.setText(Html.fromHtml(cuslname + next));

        tvCusMob = (TextView) findViewById(R.id.tvCusMob);
        String cusmob = "Mobile No.: ";
        tvCusMob.setText(Html.fromHtml(cusmob + next));

        tvPinCode = (TextView) findViewById(R.id.tvPinCode);
        String pincode = "Pincode.: ";
        tvPinCode.setText(Html.fromHtml(pincode + next));


        tvState = (TextView) findViewById(R.id.tvState);
        String state = "State.: ";
        tvState.setText(Html.fromHtml(state + next));


        tvCityName = (TextView) findViewById(R.id.tvCityName);
        String city = "City.: ";
        tvCityName.setText(Html.fromHtml(city + next));

        tvArea = (TextView) findViewById(R.id.tvArea);
        String area = "Area.: ";
        tvArea.setText(Html.fromHtml(area + next));

        tvRemark = (TextView) findViewById(R.id.tvRemark);
        String remark = "Remark.: ";
        tvRemark.setText(Html.fromHtml(remark + next));


        tvWith = (TextView) findViewById(R.id.tvWith);
        String with = "With Service Engineer?: ";
        tvWith.setText(Html.fromHtml(with + next));



        tvSFname = (TextView) findViewById(R.id.tvSFname);
        String sfname = "First Name: ";
        tvSFname.setText(Html.fromHtml(sfname + next));


        tvSLname = (TextView) findViewById(R.id.tvSLname);
        String slname = "Last Name: ";
        tvSLname.setText(Html.fromHtml(slname + next));



        tvSMob = (TextView) findViewById(R.id.tvSMob);
        String smob = "Mobile No.: ";
        tvSMob.setText(Html.fromHtml(smob + next));


        tvCategory = (TextView) findViewById(R.id.tvCategory);
        String category = "Category: ";
        tvCategory.setText(Html.fromHtml(category + next));


        tvModel = (TextView) findViewById(R.id.tvModel);
        String model = "Model: ";
        tvModel.setText(Html.fromHtml(model + next));


        tvImage = (TextView) findViewById(R.id.tvImage);
        String image = "Upload Image:";
        tvImage.setText(Html.fromHtml(image + next));

    }

    private void onClick() {
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    categoryId = moduleCategory.get(position).getItemId();
                    setModel(categoryId);
                }
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();
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
                Intent intent = new Intent(getApplicationContext(), NewDashboardActivity.class);
                startActivity(intent);
                finish();
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
                    pincodecheck(etPinCode.getText().toString());
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
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateId = "";
                if (position > 0) {
                    stateId = moduleState.get(position).getItemId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                areaName = moduleArea.get(position).getItemId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spYesNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                withEng = yesList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!titleId.equals("")) {
                    if (etCusLName.getText().toString().length() > 2) {
                        if (etCusLName.getText().toString().length() > 2) {

                                        if (etCusMob.getText().toString().length() > 9) {
                                            if (etPinCode.getText().toString().length() > 5) {
                                                if (!stateId.equals("")) {
                                                    if (!tvCity.getText().toString().equals("null")) {
                                                        if (!areaName.equals("")) {

                                                                    if (etRemarks.getText().toString().length() > 0) {
                                                                        if (!withEng.equals("")) {
                                                                            if (!modelId.equals("")) {
                                                                                if (picflag == 1) {
                                                                                    if (withEng.equals("YES")) {
                                                                                        serEng();
                                                                                    } else {
                                                                                        postFunction();
                                                                                    }

                                                                                } else {
                                                                                    Toast.makeText(getApplicationContext(), "Please upload visiting image", Toast.LENGTH_LONG).show();

                                                                                }

                                                                            } else {
                                                                                Toast.makeText(getApplicationContext(), "Please select model", Toast.LENGTH_LONG).show();

                                                                            }

                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), "Please select service engineer is with you or not", Toast.LENGTH_LONG).show();

                                                                        }

                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(), "Please provide customer remark", Toast.LENGTH_LONG).show();

                                                                    }





                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Please select area name", Toast.LENGTH_LONG).show();

                                                        }

                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Please enter valid pin code", Toast.LENGTH_LONG).show();

                                                    }

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Please select state", Toast.LENGTH_LONG).show();

                                                }

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Please provide pin code", Toast.LENGTH_LONG).show();

                                            }

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please provide customer mobile number", Toast.LENGTH_LONG).show();
                                        }






                        } else {
                            Toast.makeText(getApplicationContext(), "Please provide customer last name", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Please provide customer firstname", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please select title", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void setCategory() {
        Log.d("hitr", "1");

        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=4&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("ctegoryinput", surl);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseIFBCategory", response);
                        progressDialog.show();
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


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (CVisitManageActivity.this, android.R.layout.simple_spinner_item,
                                                category); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCategory.setAdapter(spinnerArrayAdapter);
                                setState();


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(CVisitManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                //   Toast.makeText(DocumentManageActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.d("errort", "category");
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(CVisitManageActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setModel(String categoryId) {
        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=18&ID=" + categoryId + "&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
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
                                        (CVisitManageActivity.this, android.R.layout.simple_spinner_item,
                                                model); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spModel.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CVisitManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(CVisitManageActivity.this);
        requestQueue.add(stringRequest);

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
                            compressedImageFile =  new ImageZipper(CVisitManageActivity.this)
                                    .setQuality(50)
                                    .setMaxWidth(80)
                                    .setMaxHeight(80)
                                    .compressToFile(file);


                            BitmapFactory.Options o = new BitmapFactory.Options();
                            Log.d("imagesize",getReadableFileSize(compressedImageFile.length()));
                            o.inSampleSize = 2;
                            Bitmap bm = new ImageZipper(CVisitManageActivity.this).compressToBitmap(file);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.PNG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                            Log.d("images", encodedImage);
                            imgPic.setImageBitmap(bm);
                            picflag = 1;


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

    private void setState() {
        Log.d("hitr", "3");
        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=2&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("stateinput", surl);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responsestate", response);
                        progressDialog.show();
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


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (CVisitManageActivity.this, android.R.layout.simple_spinner_item,
                                                state); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spState.setAdapter(spinnerArrayAdapter);
                                spState.setSelection(42);
                                setTitle();


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(CVisitManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(CVisitManageActivity.this);
        requestQueue.add(stringRequest);

    }


    private void pincodecheck(final String pincode) {
        Log.d("hitr", "6");
        String surl =  AppController.APIURL+"api/PinCode?id=" + pincode;
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
                            tvCity.setText(REGIONNAME);
                            spState.setEnabled(false);
                            setArea(pincode);

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(CVisitManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(CVisitManageActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setArea(String pincode) {
        Log.d("hhjjk", "kkkk");
        String surl =  AppController.APIURL+"api/PinCode?id=" + pincode;
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
                                    (CVisitManageActivity.this, android.R.layout.simple_spinner_item,
                                            area); //selected item will look like a spinner set from XML
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spArea.setAdapter(spinnerArrayAdapter);

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errort", e.toString());
                            Toast.makeText(CVisitManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(CVisitManageActivity.this);
        requestQueue.add(stringRequest);

    }


    private void setTitle() {
        Log.d("hitr", "2");
        String surl =  AppController.APIURL+"api/CommonDDL?ModuleNo=42&ID=0&ID1=0&ID2=0&ID3=0&SecurityCode=" + prefManager.getSecurityCode();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseTitle", response);
                        progressDialog.dismiss();
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


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (CVisitManageActivity.this, android.R.layout.simple_spinner_item,
                                                title); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spTitle.setAdapter(spinnerArrayAdapter);


                            } else {


                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CVisitManageActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(CVisitManageActivity.this);
        requestQueue.add(stringRequest);

    }


    private void postFunction() {

        String address = etHouseNo.getText().toString() + "-" + etLandMark.getText().toString();
        progressDialog1.show();
        MultipartBody.Part fileToUpload;
        RequestBody mFile;

        mFile = RequestBody.create(MediaType.parse(".png"), compressedImageFile);
        fileToUpload = MultipartBody.Part.createFormData("file", compressedImageFile.getName(), mFile);
        mFile = RequestBody.create(MediaType.parse("image/*"), file);
        fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);


        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Call<UploadObject> fileUpload = uploadService.customerVisit(fileToUpload, prefManager.getUserId(), date, modelId, titleId, etCusName.getText().toString(), etCusLName.getText().toString(), etCusMob.getText().toString(), etCusPhn.getText().toString(), etCusEmail.getText().toString(), PINCODE, stateId, tvCity.getText().toString(), address, areaName, etHouseNo.getText().toString(), etStreetName.getText().toString(), etLandMark.getText().toString(), etRemarks.getText().toString(), withEng, etEngName.getText().toString(), etEngLName.getText().toString(), etEngMob.getText().toString(), etEngMob.getText().toString(), cuuaddress, prefManager.getSecurityCode());
        fileUpload.enqueue(new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, retrofit2.Response<UploadObject> response) {
                progressDialog1.dismiss();
                UploadObject extraWorkingDayModel = response.body();
                if (extraWorkingDayModel.isResponseStatus()) {
                    String text = extraWorkingDayModel.responseText;
                    successAlert();
                    Toast.makeText(getApplicationContext(),extraWorkingDayModel.responseText, Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(),extraWorkingDayModel.responseText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadObject> call, Throwable t) {
                progressDialog1.dismiss();

                Log.e("error", "Error " + t.getMessage());
                Toast.makeText(getApplicationContext(),"time error", Toast.LENGTH_SHORT).show();
                showAlert();

            }

        });

    }

    public String getAddress(double lat, double lng) {
        String address = null;
        Geocoder geocoder = new Geocoder(CVisitManageActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            address = add;
            ;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return address;
    }

    private void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Somthing went wrong");
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        postFunction();
                    }
                });
        alertDialogBuilder.show();


    }

    private void successAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CVisitManageActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText("Information submitted successfully");

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                Intent intent = new Intent(CVisitManageActivity.this, CVisitReportActivity.class);
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

    private void serEng() {
        if (etEngName.getText().toString().length() > 0) {
            if (etEngLName.getText().toString().length() > 0) {
                if (etEngMob.getText().toString().length() > 9) {
                    postFunction();

                } else {
                    Toast.makeText(getApplicationContext(), "please provide service engineer mobile number", Toast.LENGTH_LONG).show();

                }

            } else {
                Toast.makeText(getApplicationContext(), "please provide service engineer last name", Toast.LENGTH_LONG).show();

            }

        } else {
            Toast.makeText(getApplicationContext(), "please provide service engineer first name", Toast.LENGTH_LONG).show();
        }
    }

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }



}
