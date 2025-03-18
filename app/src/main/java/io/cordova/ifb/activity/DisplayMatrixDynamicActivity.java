package io.cordova.ifb.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.wajahatkarim3.longimagecamera.LongImageCameraActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PostDisplayMatrixService;
import io.cordova.ifb.utility.PrefManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayMatrixDynamicActivity extends AppCompatActivity {
    EditText etAirIFB, etAirLG, etAirSamSung, etAirDaikin, etCarrier, etAirBlueStar, etAirVoltas, etAirOnida, etAirPanaSonic, etAirWhirlPool, etAirOGenaral, etAirGodrej, etAirHaier, etAirLloyds, etAirOthers;
    //airconditoner
    //daikin
    String airDaikin = "IFBPC1000001" + "-" + "IFBCC000009" + "#" + "0";
    //ifb
    String airIfb = "IFBPC1000001" + "-" + "IFBCC000015" + "#" + "0";
    //LG
    String airLg = "IFBPC1000001" + "-" + "IFBCC000001" + "#" + "0";
    //LLYODS
    String airLloyds = "IFBPC1000001" + "-" + "IFBCC000010" + "#" + "0";
    //OTHERS
    String airOthers = "IFBPC1000001" + "-" + "IFBCC000004" + "#" + "0";
    //VOLTAS
    String airVoltas = "IFBPC1000001" + "-" + "IFBCC000008" + "#" + "0";
    //
    String airSAMSUNG = "IFBPC1000001" + "-" + "IFBCC000002" + "#" + "0";
    //CARRIER
    String airCARRIER = "IFBPC1000001" + "-" + "IFBCC000018" + "#" + "0";
    //BLUESTAR
    String airBLUESTAR = "IFBPC1000001" + "-" + "IFBCC000019" + "#" + "0";
    //ONIDA
    String airONIDA = "IFBPC1000001" + "-" + "IFBCC000017" + "#" + "0";
    //PANASONIC
    String airPANASONIC = "IFBPC1000001" + "-" + "IFBCC000007" + "#" + "0";
    //WHIRLPOOL
    String airWHIRLPOOL = "IFBPC1000001" + "-" + "IFBCC000005" + "#" + "0";
    //OGENERAL
    String airOGENERAL = "IFBPC1000001" + "-" + "IFBCC000020" + "#" + "0";
    //GODREJ
    String airGODREJ = "IFBPC1000001" + "-" + "IFBCC000006" + "#" + "0";
    //HAIER
    String airHAIER = "IFBPC1000001" + "-" + "IFBCC000021" + "#" + "0";

    //cloths
    EditText etClothsIFB, etClothsBosch;

    String clothsIFB = "IFBPC1000005" + "-" + "IFBCC000015" + "#" + "0";
    String clothsBOSCH = "IFBPC1000005" + "-" + "IFBCC000003" + "#" + "0";

    //dishwasher

    EditText etDishIFB, etDishBosch, etDishLg, etDishSamsung, etDishOther;

    String dishIfb = "IFBPC1000007" + "-" + "IFBCC000015" + "#" + "0";
    String dishBosch = "IFBPC1000007" + "-" + "IFBCC000003" + "#" + "0";
    String dishLg = "IFBPC1000007" + "-" + "IFBCC000001" + "#" + "0";
    String dishSamsung = "IFBPC1000007" + "-" + "IFBCC000002" + "#" + "0";
    String dishOthers = "IFBPC1000007" + "-" + "IFBCC000004" + "#" + "0";

    //MICROOVEN
    EditText etMicroIfb, etMicroLg, etMicroSamsung, etMicroWhirlPool, etMicroPanasonic, etMicroGodrej, etMicroOnida, etMicroOthers;

    String microIfb = "IFBPC1000011" + "-" + "IFBCC000015" + "#" + "0";
    String microLg = "IFBPC1000011" + "-" + "IFBCC000001" + "#" + "0";
    String microSamSung = "IFBPC1000011" + "-" + "IFBCC000002" + "#" + "0";
    String microWhirlPool = "IFBPC1000011" + "-" + "IFBCC000005" + "#" + "0";
    String microPanasonic = "IFBPC1000011" + "-" + "IFBCC000007" + "#" + "0";
    String microGodrej = "IFBPC1000011" + "-" + "IFBCC000006" + "#" + "0";
    String microOnida = "IFBPC1000011" + "-" + "IFBCC000017" + "#" + "0";
    String microOthers = "IFBPC1000011" + "-" + "IFBCC000004" + "#" + "0";

    //KA
    EditText etKAIfb, etKAFaber, etKASunFlame, etKAElica, etKAKaff, etKABosch, etKAOthers;
    String kaIfb = "IFBPC1000035" + "-" + "IFBCC000015" + "#" + "0";
    String KaFaber = "IFBPC1000035" + "-" + "IFBCC000013" + "#" + "0";
    String KaSunFlame = "IFBPC1000035" + "-" + "IFBCC000022" + "#" + "0";
    String KaElica = "IFBPC1000035" + "-" + "IFBCC000014" + "#" + "0";
    String KaKaff = "IFBPC1000035" + "-" + "IFBCC000012" + "#" + "0";
    String KaBosch = "IFBPC1000035" + "-" + "IFBCC000003" + "#" + "0";
    String KaOthers = "IFBPC1000035" + "-" + "IFBCC000004" + "#" + "0";

    //FLU
    EditText etFLUIfb, etFLULg, etFLUSamsung, etFLUBosch, etFLUWhirlPool, etFLUBeko, etFLUOthers;

    String FLUIfb = "IFBPC1000021" + "-" + "IFBCC000015" + "#" + "0";
    String FLULg = "IFBPC1000021" + "-" + "IFBCC000001" + "#" + "0";
    String FLUSamsung = "IFBPC1000021" + "-" + "IFBCC000002" + "#" + "0";
    String FLUBosch = "IFBPC1000021" + "-" + "IFBCC000003" + "#" + "0";
    String FLUWhirlPool = "IFBPC1000021" + "-" + "IFBCC000005" + "#" + "0";
    String FLUBeko = "IFBPC1000021" + "-" + "IFBCC000024" + "#" + "0";
    String FLUOthers = "IFBPC1000021" + "-" + "IFBCC000004" + "#" + "0";

    //TL
    EditText etTLIfb, etTLLg, etTLSamsung, etTLBosch, etTLWhirlPool, etTLPanasonic, etTLGodrej, etTLOnida, etTLOthers;

    String TLIfb = "IFBPC1000025" + "-" + "IFBCC000015" + "#" + "0";
    String TLLg = "IFBPC1000025" + "-" + "IFBCC000001" + "#" + "0";
    String TLSamsung = "IFBPC1000025" + "-" + "IFBCC000002" + "#" + "0";
    String TLBosch = "IFBPC1000025" + "-" + "IFBCC000003" + "#" + "0";
    String TLWhirlPool = "IFBPC1000025" + "-" + "IFBCC000005" + "#" + "0";
    String TLPanasonic = "IFBPC1000025" + "-" + "IFBCC000007" + "#" + "0";
    String TLGodrej = "IFBPC1000025" + "-" + "IFBCC000006" + "#" + "0";
    String TLOnida = "IFBPC1000025" + "-" + "IFBCC000017" + "#" + "0";
    String TLOthers = "IFBPC1000025" + "-" + "IFBCC000004" + "#" + "0";


    //Waher Disher

    EditText etWasherDisherOthers, etWasherDisherOnida, etWasherDisherGodrej, etWasherDisherPanasonic, etWasherDisherWhirlPool, etWasherDisherSamsung, etWasherDisherLg, etWasherDisherIfb;
    TextView tvWasherDisherAdd;

    String washerIfb = "IFBPC1000039" + "-" + "IFBCC000015" + "#" + "0";
    String washerLg = "IFBPC1000039" + "-" + "IFBCC000001" + "#" + "0";
    String washerSamSung = "IFBPC1000039" + "-" + "IFBCC000002" + "#" + "0";
    String washerWhirlPool = "IFBPC1000039" + "-" + "IFBCC000005" + "#" + "0";
    String washerPanasonic = "IFBPC1000039" + "-" + "IFBCC000007" + "#" + "0";
    String washerGodrej = "IFBPC1000039" + "-" + "IFBCC000006" + "#" + "0";
    String washerOnida = "IFBPC1000039" + "-" + "IFBCC000017" + "#" + "0";
    String washerOthers = "IFBPC1000039" + "-" + "IFBCC000004" + "#" + "0";


    //refregerator DC

    EditText etREFRIGERATOROthers, etREFRIGERATORGodrej, etREFRIGERATORHaier, etREFRIGERATORWhirlPool, etREFRIGERATORSamsung, etREFRIGERATORLg, etREFRIGERATORIfb;
    TextView tvREFRIGERATORAdd;

    String refregeratorIfb = "IFBPC1000013" + "-" + "IFBCC000015" + "#" + "0";
    String refregeratorLg = "IFBPC1000013" + "-" + "IFBCC000001" + "#" + "0";
    String refregeratorSamSung = "IFBPC1000013" + "-" + "IFBCC000002" + "#" + "0";
    String refregeratorWhirlPool = "IFBPC1000013" + "-" + "IFBCC000005" + "#" + "0";
    String refregeratorHaier = "IFBPC1000013" + "-" + "IFBCC000021" + "#" + "0";
    String refregeratorGodrej = "IFBPC1000013" + "-" + "IFBCC000006" + "#" + "0";
    String refregeratorOthers = "IFBPC1000013" + "-" + "IFBCC000004" + "#" + "0";


    //refregerator FF


    EditText etREFRIGERATORFFOthers, etREFRIGERATORFFGodrej, etREFRIGERATORFFHaier, etREFRIGERATORFFWhirlPool, etREFRIGERATORFFSamsung, etREFRIGERATORFFLg, etREFRIGERATORFFIfb;
    TextView tvREFRIGERATORFFAdd;

    String refregeratorFFIfb = "IFBPC1000040" + "-" + "IFBCC000015" + "#" + "0";
    String refregeratorFFLg = "IFBPC1000040" + "-" + "IFBCC000001" + "#" + "0";
    String refregeratorFFSamSung = "IFBPC1000040" + "-" + "IFBCC000002" + "#" + "0";
    String refregeratorFFWhirlPool = "IFBPC1000040" + "-" + "IFBCC000005" + "#" + "0";
    String refregeratorFFHaier = "IFBPC1000040" + "-" + "IFBCC000021" + "#" + "0";
    String refregeratorFFGodrej = "IFBPC1000040" + "-" + "IFBCC000006" + "#" + "0";
    String refregeratorFFOthers = "IFBPC1000040" + "-" + "IFBCC000004" + "#" + "0";


    private static final String SERVER_PATH = AppController.APIURL + "api/";
    private PostDisplayMatrixService uploadService;
    ProgressDialog progressDialog;
    String salesdate;
    String msg = "";
    String securitycode, userid;
    String formattedDate;

    String modelId = "";
    String category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airVoltas + "," + airSAMSUNG + "," + airCARRIER + "," + airBLUESTAR + "," + airONIDA + "," + airPANASONIC + "," + airWHIRLPOOL + "," + airOGENERAL + "," + airGODREJ + "," + airHAIER + "," + clothsIFB + "," + clothsBOSCH + "," + dishIfb + "," + dishBosch + "," + dishLg + "," + dishSamsung + "," + dishOthers + "," + microIfb + "," + microLg + "," + microSamSung + "," + microWhirlPool + "," + microPanasonic + "," + microGodrej + "," + microOnida + "," + microOthers + "," + kaIfb + "," + KaFaber + "," + KaSunFlame + "," + KaElica + "," + KaKaff + "," + KaBosch + "," + KaOthers + "," + FLUIfb + "," + FLULg + "," + FLUSamsung + "," + FLUBosch + "," + FLUWhirlPool + "," + FLUBeko + "," + FLUOthers + "," + TLIfb + "," + TLLg + "," + TLSamsung + "," + TLBosch + "," + TLWhirlPool + "," + TLPanasonic + "," + TLGodrej + "," + TLOnida + "," + TLOthers + "," + washerIfb + "," + washerLg + "," + washerSamSung + "," + washerWhirlPool + "," + washerPanasonic + "," + washerGodrej + "," + washerOnida + "," + washerOthers + "," + refregeratorIfb + "," + refregeratorLg + "," + refregeratorSamSung + "," + refregeratorWhirlPool + "," + refregeratorHaier + "," + refregeratorGodrej + "," + refregeratorOthers + "," + refregeratorFFIfb + "," + refregeratorFFLg + "," + refregeratorFFSamSung + "," + refregeratorFFWhirlPool + "," + refregeratorFFHaier + "," + refregeratorFFGodrej + "," + refregeratorFFOthers;
    String model = "0";
    PrefManager prefManager;

    TextView tvAirAdd, tvSave, tvClothsAdd, tvDishAdd, tvMicroAdd, tvKAAdd, tvFLUAdd, tvTLAdd;
    //ifbitemid
    String airItem = "0";
    String clothsItem = "0";
    String dishItem = "0";
    String microItem = "0";
    String KAItem = "0";
    String FLUItem = "0";
    String tlItem = "0";
    String washerDyerItem = "0";
    String refItem = "0";
    String refffItem = "0";


    String year, month;

    TextView tvDate;
    String showMonth, showYear;
    ImageView imgBack, imgHome;

    AlertDialog alerDialog1, alertDialog, alertDialog2, alertDialog3;

    String responseText, premonth, finalcialchecking;

    ArrayList<String> airConditionerModel = new ArrayList<>();
    ArrayList<String> clothsdryerModel = new ArrayList<>();
    ArrayList<String> dishwasherModel = new ArrayList<>();
    ArrayList<String> microOvenModel = new ArrayList<>();
    ArrayList<String> kitchenModel = new ArrayList<>();
    ArrayList<String> wmFluModel = new ArrayList<>();
    ArrayList<String> wmTLModel = new ArrayList<>();
    ArrayList<String> dryerModel = new ArrayList<>();
    ArrayList<String> refModel = new ArrayList<>();
    ArrayList<String> refFFModel = new ArrayList<>();

    ProgressDialog pd;
    ImageView imgPic1, imgPic2, imgPic3;
    private Uri imageUri, imageUri1, imageUri2;
    private static final int CAMERA_REQUEST = 1;
    private static final int CAMERA_REQUEST1 = 2;
    private static final int CAMERA_REQUEST2 = 3;
    File file, file1, file2;
    String encodedImage, encodedImage1, encodedImage2;
    String stringFile, stringFile1, stringFile2;
    int pic1Flag = 0;
    int pic2Flag = 0;
    int pic3Flag = 0;
    String acFlag;
    ArrayList<String> sendACModelList = new ArrayList<>();
    String previousMonthData = "false";
    int y;
    ArrayList<String> modelArray = new ArrayList<>();
    RecyclerView rvAirModelItem;
    private static final int ACREQUEST = 300;
    private static final int CLOTHSDRYERREQUEST = 400;
    private static final int DISHREQUEST = 401;
    private static final int WASHERDRYERREQUEST = 402;
    private static final int MICROOVENREQUEST = 403;
    private static final int KITCHENREQUEST = 404;
    private static final int FLUREQUEST = 405;
    private static final int TLREQUEST = 500;
    private static final int REFREQUEST = 900;
    private static final int REFFFREQUEST = 901;
    AlertDialog cameraAlert;
    String imageFileName;
    File pictureFile;

    TextView tvAirLgAdd, tvAirSamsungAdd, tvAirDaikenAdd, tvAirCarrierAdd, tvAirBluestarAdd, tvAirVoltasAdd, tvAirOnidaAdd, tvAirPanasonicAdd, tvAirWhirlpoolAdd, tvAiroGeneralAdd, tvAirGodrejAdd, tvAirHaierAdd, tvAirLloydsAdd;
    TextView tvClothsBoschAdd;
    TextView tvDishBoschAdd, tvDishLgAdd, tvDishSamsungAdd;
    TextView tvMicroLgAdd, tvMicroSamsungAdd, tvMicroWhirlpoolAdd, tvMicroPanasonicAdd, tvMicroGodrejAdd, tvMicroOnidaAdd;
    TextView tvKAFaberAdd, tvKASunflameAdd, tvKAElicaAdd, tvKAKaffAdd, tvKABoschAdd;
    TextView tvFLULgAdd, tvFLUSamsungAdd, tvFLUBoschAdd, tvFLUWhirlpoolAdd, tvFLUBekoAdd;
    TextView tvTLLgAdd, tvTLSamsungAdd, tvTLBoschAdd, tvTLWhirlpoolAdd, tvTLPanasonicAdd, tvTLGodrejAdd, tvTLOnidaAdd;
    TextView tvWasherDisherLgAdd, tvWasherDisherSamsungAdd, tvWasherDisherWhirlpoolAdd, tvWasherDisherPanasonicAdd, tvWasherDisherGodrejAdd, tvWasherDisherOnidaAdd;
    TextView tvREFRIGERATORSamsungAdd, tvREFRIGERATORLgAdd, tvREFRIGERATORWhirlpoolAdd, tvREFRIGERATORHaierAdd, tvREFRIGERATORGodrejAdd;
    TextView tvREFRIGERATORFFLgAdd, tvREFRIGERATORFFSamsungAdd, tvREFRIGERATORFFWhirlpoolAdd, tvREFRIGERATORFFHaierAdd, tvREFRIGERATORFFGodrejAdd;

    private static final int ACLG = 5000;
    private static final int ACSAMSUNG = 5001;
    private static final int ACDAIKEN = 5002;
    private static final int ACCARRIER = 5003;
    private static final int ACBLUESTAR = 5004;
    private static final int ACVOLTAS = 5005;
    private static final int ACONIDA = 5006;
    private static final int ACPANASONIC = 5007;
    private static final int ACWHIRLPOOL = 5008;
    private static final int ACOGENERAL = 5009;
    private static final int ACOGODREJ = 5010;
    private static final int ACHAIER = 5011;
    private static final int ACLLYODS = 5012;
    private static final int CLOTHSBOSCH = 5013;
    private static final int DISHBOSCH = 5014;
    private static final int DISHLG = 5015;
    private static final int DISHSAMSUNG = 5016;
    private static final int MICROLG = 5017;
    private static final int MICROSAMSUNG = 5018;
    private static final int MICROWHIRLPOOL = 5019;
    private static final int MICROPANASONIC = 5020;
    private static final int MICROGODREJ = 5021;
    private static final int MICROONIDA = 5022;
    private static final int KAFABER = 5023;
    private static final int KASUN = 5024;
    private static final int KAELICA = 5025;
    private static final int KAKAFF = 5026;
    private static final int KABOSCH = 5027;
    private static final int WMFLULG = 5028;
    private static final int WMFLUSAM = 5029;
    private static final int WMFLUBOSCH = 5030;
    private static final int WMFLUWHIRLPOOL = 5031;
    private static final int WMFLUWHIRLBEKO = 5032;
    private static final int TLLG = 5033;
    private static final int TLSAM = 5034;
    private static final int TLBOSCH = 5035;
    private static final int TLWHIRLPOOL = 5036;
    private static final int TLPANSONIC = 5037;
    private static final int TLGODREJ = 5038;
    private static final int TLONIDA = 5039;
    private static final int DRYERLG = 5040;
    private static final int DRYERSAM = 5041;
    private static final int DRYERWHIRLPOOL = 5042;
    private static final int DRYERPANASONIC = 5043;
    private static final int DRYERGODREJ = 5044;
    private static final int DRYERONIDA = 5045;
    private static final int DCSAM = 5046;
    private static final int DCLG = 5047;
    private static final int DCWHIRLPOOL = 5048;
    private static final int DCHAIER = 5049;
    private static final int DCGodrej = 5050;
    private static final int FFGodrej = 5051;
    private static final int FFHAIER = 5052;



    String microovenID = "IFBPC1000011";
    String KAID = "IFBPC1000035";
    String WFLUID = "IFBPC1000021";
    String WTLID = "IFBPC1000025";
    String WASHERDRYERID = "IFBPC1000039";
    String REFRDC = "IFBPC1000013";
    String REFFF = "IFBPC1000040";

    String LG = "IFBCC000001";
    String SamSung = "IFBCC000002";
    String Whirlpool = "IFBCC000005";
    String Panasonic = "IFBCC000007";
    String Godrej = "IFBCC000006";
    String Onida = "IFBCC000017";
    String Faber = "IFBCC000013";
    String Sunflame = "IFBCC000022";
    String ELICA = "IFBCC000014";
    String KAFF = "IFBCC000012";
    String Bosch = "IFBCC000003";
    String Beko = "IFBCC000024";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_matrix_dynamic);
        init();
        displayMatrixChecking();
        onClick();

    }


    private void init() {


        prefManager = new PrefManager(getApplicationContext());
        sendACModelList.clear();
        prefManager.saveAirConditionerId("");
        prefManager.saveClothsDryerId("");
        prefManager.saveMicroOvenId("");
        prefManager.saveDishWasherId("");
        prefManager.SaveKAItemId("");
        prefManager.saveWasherDryerId("");
        prefManager.saveWashingFLUId("");
        prefManager.saveWashingTLId("");

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

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

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
        securitycode = prefManager.getSecurityCode();
        userid = prefManager.getUserId();
        salesdate = formattedDate;

        //Air Conditioner
        etAirIFB = (EditText) findViewById(R.id.etAirIFB);


        etAirLG = (EditText) findViewById(R.id.etAirLG);
        etAirSamSung = (EditText) findViewById(R.id.etAirSamSung);
        etAirDaikin = (EditText) findViewById(R.id.etAirDaikin);
        etCarrier = (EditText) findViewById(R.id.etCarrier);
        etAirBlueStar = (EditText) findViewById(R.id.etAirBlueStar);
        etAirVoltas = (EditText) findViewById(R.id.etAirVoltas);
        etAirOnida = (EditText) findViewById(R.id.etAirOnida);
        etAirPanaSonic = (EditText) findViewById(R.id.etAirPanaSonic);
        etAirWhirlPool = (EditText) findViewById(R.id.etAirWhirlPool);
        etAirOGenaral = (EditText) findViewById(R.id.etAirOGenaral);
        etAirGodrej = (EditText) findViewById(R.id.etAirGodrej);
        etAirHaier = (EditText) findViewById(R.id.etAirHaier);
        etAirLloyds = (EditText) findViewById(R.id.etAirLloyds);
        etAirOthers = (EditText) findViewById(R.id.etAirOthers);

        tvAirAdd = (TextView) findViewById(R.id.tvAirAdd);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvClothsAdd = (TextView) findViewById(R.id.tvClothsAdd);
        tvDishAdd = (TextView) findViewById(R.id.tvDishAdd);
        tvMicroAdd = (TextView) findViewById(R.id.tvMicroAdd);
        tvKAAdd = (TextView) findViewById(R.id.tvKAAdd);
        tvFLUAdd = (TextView) findViewById(R.id.tvFLUAdd);
        tvTLAdd = (TextView) findViewById(R.id.tvTLAdd);
        tvREFRIGERATORAdd = (TextView) findViewById(R.id.tvREFRIGERATORAdd);
        tvREFRIGERATORFFAdd = (TextView) findViewById(R.id.tvREFRIGERATORFFAdd);


        tvAirLgAdd = (TextView) findViewById(R.id.tvAirLgAdd);
        tvAirSamsungAdd = (TextView) findViewById(R.id.tvAirSamsungAdd);
        tvAirDaikenAdd = (TextView) findViewById(R.id.tvAirDaikenAdd);
        tvAirCarrierAdd = (TextView) findViewById(R.id.tvAirCarrierAdd);
        tvAirBluestarAdd = (TextView) findViewById(R.id.tvAirBluestarAdd);
        tvAirVoltasAdd = (TextView) findViewById(R.id.tvAirVoltasAdd);
        tvAirOnidaAdd = (TextView) findViewById(R.id.tvAirOnidaAdd);
        tvAirPanasonicAdd = (TextView) findViewById(R.id.tvAirPanasonicAdd);
        tvAirWhirlpoolAdd = (TextView) findViewById(R.id.tvAirWhirlpoolAdd);
        tvAiroGeneralAdd = (TextView) findViewById(R.id.tvAiroGeneralAdd);
        tvAirGodrejAdd = (TextView) findViewById(R.id.tvAirGodrejAdd);
        tvAirHaierAdd = (TextView) findViewById(R.id.tvAirHaierAdd);
        tvAirLloydsAdd = (TextView) findViewById(R.id.tvAirLloydsAdd);

        tvClothsBoschAdd = (TextView) findViewById(R.id.tvClothsBoschAdd);

        tvDishBoschAdd = (TextView) findViewById(R.id.tvDishBoschAdd);
        tvDishLgAdd = (TextView) findViewById(R.id.tvDishLgAdd);
        tvDishSamsungAdd = (TextView) findViewById(R.id.tvDishSamsungAdd);

        tvMicroLgAdd = (TextView) findViewById(R.id.tvMicroLgAdd);
        tvMicroSamsungAdd = (TextView) findViewById(R.id.tvMicroSamsungAdd);
        tvMicroWhirlpoolAdd = (TextView) findViewById(R.id.tvMicroWhirlpoolAdd);
        tvMicroPanasonicAdd = (TextView) findViewById(R.id.tvMicroPanasonicAdd);
        tvMicroGodrejAdd = (TextView) findViewById(R.id.tvMicroGodrejAdd);
        tvMicroOnidaAdd = (TextView) findViewById(R.id.tvMicroOnidaAdd);


        tvKAFaberAdd = (TextView) findViewById(R.id.tvKAFaberAdd);
        tvKASunflameAdd = (TextView) findViewById(R.id.tvKASunflameAdd);
        tvKAElicaAdd = (TextView) findViewById(R.id.tvKAElicaAdd);
        tvKAKaffAdd = (TextView) findViewById(R.id.tvKAKaffAdd);
        tvKABoschAdd = (TextView) findViewById(R.id.tvKABoschAdd);

        tvFLULgAdd = (TextView) findViewById(R.id.tvFLULgAdd);
        tvFLUSamsungAdd = (TextView) findViewById(R.id.tvFLUSamsungAdd);
        tvFLUBoschAdd = (TextView) findViewById(R.id.tvFLUBoschAdd);
        tvFLUWhirlpoolAdd = (TextView) findViewById(R.id.tvFLUWhirlpoolAdd);
        tvFLUBekoAdd = (TextView) findViewById(R.id.tvFLUBekoAdd);

        tvTLLgAdd = (TextView) findViewById(R.id.tvTLLgAdd);
        tvTLSamsungAdd = (TextView) findViewById(R.id.tvTLSamsungAdd);
        tvTLBoschAdd = (TextView) findViewById(R.id.tvTLBoschAdd);
        tvTLWhirlpoolAdd = (TextView) findViewById(R.id.tvTLWhirlpoolAdd);
        tvTLPanasonicAdd = (TextView) findViewById(R.id.tvTLPanasonicAdd);
        tvTLGodrejAdd = (TextView) findViewById(R.id.tvTLGodrejAdd);
        tvTLOnidaAdd = (TextView) findViewById(R.id.tvTLOnidaAdd);

        tvWasherDisherLgAdd = (TextView) findViewById(R.id.tvWasherDisherLgAdd);
        tvWasherDisherSamsungAdd = (TextView) findViewById(R.id.tvWasherDisherSamsungAdd);
        tvWasherDisherWhirlpoolAdd = (TextView) findViewById(R.id.tvWasherDisherWhirlpoolAdd);
        tvWasherDisherPanasonicAdd = (TextView) findViewById(R.id.tvWasherDisherPanasonicAdd);
        tvWasherDisherGodrejAdd = (TextView) findViewById(R.id.tvWasherDisherGodrejAdd);
        tvWasherDisherOnidaAdd = (TextView) findViewById(R.id.tvWasherDisherOnidaAdd);


        tvREFRIGERATORSamsungAdd = (TextView) findViewById(R.id.tvREFRIGERATORSamsungAdd);
        tvREFRIGERATORLgAdd = (TextView) findViewById(R.id.tvREFRIGERATORLgAdd);
        tvREFRIGERATORWhirlpoolAdd = (TextView) findViewById(R.id.tvREFRIGERATORWhirlpoolAdd);
        tvREFRIGERATORHaierAdd = (TextView) findViewById(R.id.tvREFRIGERATORHaierAdd);
        tvREFRIGERATORGodrejAdd = (TextView) findViewById(R.id.tvREFRIGERATORGodrejAdd);

        tvREFRIGERATORFFLgAdd = (TextView) findViewById(R.id.tvREFRIGERATORFFLgAdd);
        tvREFRIGERATORFFSamsungAdd = (TextView) findViewById(R.id.tvREFRIGERATORFFSamsungAdd);
        tvREFRIGERATORFFWhirlpoolAdd = (TextView) findViewById(R.id.tvREFRIGERATORFFWhirlpoolAdd);
        tvREFRIGERATORFFHaierAdd = (TextView) findViewById(R.id.tvREFRIGERATORFFHaierAdd);
        tvREFRIGERATORFFGodrejAdd = (TextView) findViewById(R.id.tvREFRIGERATORFFGodrejAdd);


        airIfb = "IFBPC1000001" + "-" + "IFBCC000015" + "#" + prefManager.getAirIfbSize();
        clothsIFB = "IFBPC1000005" + "-" + "IFBCC000015" + "#" + prefManager.getClothsIfbSize();
        dishIfb = "IFBPC1000007" + "-" + "IFBCC000015" + "#" + prefManager.getDishIfbSize();
        microIfb = "IFBPC1000011" + "-" + "IFBCC000015" + "#" + prefManager.getMicroOvenIfbSize();
        kaIfb = "IFBPC1000035" + "-" + "IFBCC000015" + "#" + prefManager.getKAIfbSize();
        FLUIfb = "IFBPC1000021" + "-" + "IFBCC000015" + "#" + prefManager.getWMFLUIfbSize();
        TLIfb = "IFBPC1000025" + "-" + "IFBCC000015" + "#" + prefManager.getWMTLIFBSize();


        //clothes
        etClothsIFB = (EditText) findViewById(R.id.etClothsIFB);
        etClothsIFB.setEnabled(false);
        String clothsifbsize = String.valueOf(prefManager.getClothsIfbSize());
        Log.d("size", clothsifbsize);
        etClothsIFB.setText(clothsifbsize);

        etClothsBosch = (EditText) findViewById(R.id.etClothsBosch);

        //dishwasher
        etDishIFB = (EditText) findViewById(R.id.etDishIFB);
        String dishifbsize = String.valueOf(prefManager.getDishIfbSize());
        Log.d("size", clothsifbsize);
        etDishIFB.setText(dishifbsize);

        etDishBosch = (EditText) findViewById(R.id.etDishBosch);
        etDishLg = (EditText) findViewById(R.id.etDishLg);
        etDishSamsung = (EditText) findViewById(R.id.etDishSamsung);
        etDishOther = (EditText) findViewById(R.id.etDishOther);

        //micooven
        etMicroIfb = (EditText) findViewById(R.id.etMicroIfb);
        String microifbsize = String.valueOf(prefManager.getMicroOvenIfbSize());
        Log.d("size", microifbsize);
        etMicroIfb.setText(microifbsize);

        etMicroLg = (EditText) findViewById(R.id.etMicroLg);
        etMicroSamsung = (EditText) findViewById(R.id.etMicroSamsung);
        etMicroWhirlPool = (EditText) findViewById(R.id.etMicroWhirlPool);
        etMicroPanasonic = (EditText) findViewById(R.id.etMicroPanasonic);
        etMicroGodrej = (EditText) findViewById(R.id.etMicroGodrej);
        etMicroOnida = (EditText) findViewById(R.id.etMicroOnida);
        etMicroOthers = (EditText) findViewById(R.id.etMicroOthers);

        //KA
        etKAIfb = (EditText) findViewById(R.id.etKAIfb);
        String kaifbsize = String.valueOf(prefManager.getKAIfbSize());
        Log.d("size", kaifbsize);
        etKAIfb.setText(kaifbsize);

        etKAFaber = (EditText) findViewById(R.id.etKAFaber);
        etKASunFlame = (EditText) findViewById(R.id.etKASunFlame);
        etKAElica = (EditText) findViewById(R.id.etKAElica);
        etKAKaff = (EditText) findViewById(R.id.etKAKaff);
        etKABosch = (EditText) findViewById(R.id.etKABosch);
        etKAOthers = (EditText) findViewById(R.id.etKAOthers);


        //FLU
        etFLUIfb = (EditText) findViewById(R.id.etFLUIfb);
        String FLUIfbSize = String.valueOf(prefManager.getWMFLUIfbSize());
        Log.d("size", FLUIfbSize);
        etFLUIfb.setText(FLUIfbSize);


        etFLULg = (EditText) findViewById(R.id.etFLULg);
        etFLUSamsung = (EditText) findViewById(R.id.etFLUSamsung);
        etFLUBosch = (EditText) findViewById(R.id.etFLUBosch);
        etFLUWhirlPool = (EditText) findViewById(R.id.etFLUWhirlPool);
        etFLUBeko = (EditText) findViewById(R.id.etFLUBeko);
        etFLUOthers = (EditText) findViewById(R.id.etFLUOthers);

        //TL

        etTLIfb = (EditText) findViewById(R.id.etTLIfb);
        String tlIfbSize = String.valueOf(prefManager.getWMTLIFBSize());
        Log.d("size", tlIfbSize);
        etTLIfb.setText(tlIfbSize);
        etTLLg = (EditText) findViewById(R.id.etTLLg);
        etTLSamsung = (EditText) findViewById(R.id.etTLSamsung);
        etTLBosch = (EditText) findViewById(R.id.etTLBosch);
        etTLWhirlPool = (EditText) findViewById(R.id.etTLWhirlPool);
        etTLPanasonic = (EditText) findViewById(R.id.etTLPanasonic);
        etTLGodrej = (EditText) findViewById(R.id.etTLGodrej);
        etTLOnida = (EditText) findViewById(R.id.etTLOnida);
        etTLOthers = (EditText) findViewById(R.id.etTLOthers);

        //Washer Disher

        etWasherDisherIfb = (EditText) findViewById(R.id.etWasherDisherIfb);
        String washerdryerifbsize = String.valueOf(prefManager.getWasherDryerIfbSize());
        Log.d("washerdryerifbsize", washerdryerifbsize);
        etWasherDisherIfb.setText(washerdryerifbsize);

        etWasherDisherLg = (EditText) findViewById(R.id.etWasherDisherLg);
        etWasherDisherSamsung = (EditText) findViewById(R.id.etWasherDisherSamsung);
        etWasherDisherWhirlPool = (EditText) findViewById(R.id.etWasherDisherWhirlPool);
        etWasherDisherPanasonic = (EditText) findViewById(R.id.etWasherDisherPanasonic);
        etWasherDisherGodrej = (EditText) findViewById(R.id.etWasherDisherGodrej);
        etWasherDisherOnida = (EditText) findViewById(R.id.etWasherDisherOnida);
        etWasherDisherOthers = (EditText) findViewById(R.id.etWasherDisherOthers);

        //Refregerator DC

        etREFRIGERATORIfb = (EditText) findViewById(R.id.etREFRIGERATORIfb);
        etREFRIGERATORLg = (EditText) findViewById(R.id.etREFRIGERATORLg);
        etREFRIGERATORSamsung = (EditText) findViewById(R.id.etREFRIGERATORSamsung);
        etREFRIGERATORWhirlPool = (EditText) findViewById(R.id.etREFRIGERATORWhirlPool);
        etREFRIGERATORHaier = (EditText) findViewById(R.id.etREFRIGERATORHaier);
        etREFRIGERATORGodrej = (EditText) findViewById(R.id.etREFRIGERATORGodrej);
        etREFRIGERATOROthers = (EditText) findViewById(R.id.etREFRIGERATOROthers);


        //REF FF

        etREFRIGERATORFFIfb = (EditText) findViewById(R.id.etREFRIGERATORFFIfb);
        etREFRIGERATORFFLg = (EditText) findViewById(R.id.etREFRIGERATORFFLg);
        etREFRIGERATORFFSamsung = (EditText) findViewById(R.id.etREFRIGERATORFFSamsung);
        etREFRIGERATORFFWhirlPool = (EditText) findViewById(R.id.etREFRIGERATORFFWhirlPool);
        etREFRIGERATORFFHaier = (EditText) findViewById(R.id.etREFRIGERATORFFHaier);
        etREFRIGERATORFFGodrej = (EditText) findViewById(R.id.etREFRIGERATORFFGodrej);
        etREFRIGERATORFFOthers = (EditText) findViewById(R.id.etREFRIGERATORFFOthers);

        if (!prefManager.getAirConditionerId().equals("")) {
            airItem = prefManager.getAirConditionerId();

        } else {
            airItem = "0";

        }

        if (!prefManager.getClothsDryerId().equals("")) {
            clothsItem = prefManager.getClothsDryerId();
        } else {
            clothsItem = "0";
        }

        if (!prefManager.getDishWasherId().equals("")) {
            dishItem = prefManager.getDishWasherId();
        } else {
            dishItem = "0";
        }

        if (!prefManager.getMicroOvenId().equals("")) {
            microItem = prefManager.getMicroOvenId();
        } else {
            microItem = "0";
        }


        if (!prefManager.getKAItemId().equals("")) {
            KAItem = prefManager.getKAItemId();
        } else {
            KAItem = "0";
        }

        if (!prefManager.getWashingFLUId().equals("")) {
            FLUItem = prefManager.getWashingFLUId();
        } else {
            FLUItem = "0";
        }

        if (!prefManager.getWashingTLId().equals("")) {
            tlItem = prefManager.getWashingTLId();
        } else {
            tlItem = "0";
        }

        if (!prefManager.getWasherDryerId().equals("")) {
            washerDyerItem = prefManager.getWasherDryerId();
        } else {
            washerDyerItem = "0";
        }


        model = airItem + "," + clothsItem + "," + dishItem + "," + microItem + "," + KAItem + "," + FLUItem + "," + tlItem + "," + washerDyerItem;
        modelId = model.replaceAll("\\s+", "");


        tvDate = (TextView) findViewById(R.id.tvDate);
        tvWasherDisherAdd = (TextView) findViewById(R.id.tvWasherDisherAdd);

        if (month.equals("January")) {
            showYear = String.valueOf(y - 1);
            showMonth = "January" + "-" + year;
        } else if (month.equals("February")) {
            showMonth = "February" + "-" + year;

        } else if (month.equals("March")) {
            showMonth = "March" + "-" + year;

        } else if (month.equals("April")) {
            showMonth = "April" + "-" + year;

        } else if (month.equals("May")) {
            showMonth = "May" + "-" + year;

        } else if (month.equals("June")) {
            showMonth = "June" + "-" + year;

        } else if (month.equals("July")) {
            showMonth = "July" + "-" + year;

        } else if (month.equals("August")) {
            showMonth = "August" + "-" + year;

        } else if (month.equals("September")) {
            showMonth = "September" + "-" + year;

        } else if (month.equals("October")) {
            showMonth = "October" + "-" + year;

        } else if (month.equals("November")) {
            showMonth = "November" + "-" + year;

        } else if (month.equals("December")) {
            showMonth = "December" + "-" + year;

        }

        tvDate.setText("For the" + " " + showMonth);

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);


        if (month.equals("January")) {
            showYear = String.valueOf(y - 1);
            premonth = "December";
        } else if (month.equals("February")) {
            premonth = "January";

        } else if (month.equals("March")) {
            premonth = "February";

        } else if (month.equals("April")) {
            premonth = "March";

        } else if (month.equals("May")) {
            premonth = "April";

        } else if (month.equals("June")) {
            premonth = "May";

        } else if (month.equals("July")) {
            premonth = "June";

        } else if (month.equals("August")) {
            premonth = "July";

        } else if (month.equals("September")) {
            premonth = "August";

        } else if (month.equals("October")) {
            premonth = "September";

        } else if (month.equals("November")) {
            premonth = "October";
        } else if (month.equals("December")) {
            premonth = "November";

        }


        if (month.equals("January")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else if (month.equals("February")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else if (month.equals("March")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else {
            int futureyear = y + 1;
            finalcialchecking = year + "-" + futureyear;
        }
        Log.d("finalcialchecking", finalcialchecking);

        pd = new ProgressDialog(DisplayMatrixDynamicActivity.this);
        pd.setMessage("Loading");
        pd.setCancelable(false);

        rvAirModelItem = (RecyclerView) findViewById(R.id.rvAirModelItem);
        LinearLayoutManager airlayoutManager
                = new LinearLayoutManager(DisplayMatrixDynamicActivity.this, LinearLayoutManager.VERTICAL, false);
        rvAirModelItem.setLayoutManager(airlayoutManager);

        getAddButton();


    }

    private void onClick() {
        tvAirLgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000001");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_LG");
                intent.putExtra("Company", "LG");
                startActivityForResult(intent, ACLG);
            }
        });


        tvAirSamsungAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000002");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_Samsung");
                intent.putExtra("Company", "Samsung");
                startActivityForResult(intent, ACSAMSUNG);
            }
        });


        tvAirDaikenAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000009");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_Daiken");
                intent.putExtra("Company", "Daiken");
                startActivityForResult(intent, ACDAIKEN);
            }
        });


        tvAirCarrierAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000018");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_Carrier");
                intent.putExtra("Company", "Carrier");
                startActivityForResult(intent, ACCARRIER);
            }
        });


        tvAirBluestarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000019");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_Bluestar");
                intent.putExtra("Company", "Bluestar");
                startActivityForResult(intent, ACBLUESTAR);
            }
        });


        tvAirVoltasAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000008");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_Voltas");
                intent.putExtra("Company", "Voltas");
                startActivityForResult(intent, ACVOLTAS);
            }
        });


        tvAirOnidaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000017");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_Onida");
                intent.putExtra("Company", "Onida");
                startActivityForResult(intent, ACONIDA);
            }
        });


        tvAirPanasonicAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000007");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_Panasonic");
                intent.putExtra("Company", "Panasonic");
                startActivityForResult(intent, ACPANASONIC);
            }
        });

        tvAirWhirlpoolAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000005");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_Whirlpool");
                intent.putExtra("Company", "Whirlpool");
                startActivityForResult(intent, ACWHIRLPOOL);
            }
        });


        tvAiroGeneralAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000020");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_OG");
                intent.putExtra("Company", "O General");
                startActivityForResult(intent, ACOGENERAL);
            }
        });
        tvAirGodrejAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000006");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_Godrej");
                intent.putExtra("Company", "Godrej");
                startActivityForResult(intent, ACOGODREJ);
            }
        });


        tvAirHaierAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000021");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_Haier");
                intent.putExtra("Company", "Haier");
                startActivityForResult(intent, ACHAIER);
            }
        });


        tvAirLloydsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000001");
                intent.putExtra("compid", "IFBCC000010");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Air Conditioner");
                intent.putExtra("Flag", "Air_LLyods");
                intent.putExtra("Company", "Llyods");
                startActivityForResult(intent, ACLLYODS);
            }
        });


        tvClothsBoschAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000005");
                intent.putExtra("compid", "IFBCC000003");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Cloths Dryer");
                intent.putExtra("Flag", "Cloths_Bosch");
                intent.putExtra("Company", "Bosch & Simens");
                startActivityForResult(intent, CLOTHSBOSCH);
            }
        });


        tvDishBoschAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000007");
                intent.putExtra("compid", "IFBCC000003");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Dishwasher");
                intent.putExtra("Flag", "Dish_Bosch");
                intent.putExtra("Company", "Bosch & Simens");
                startActivityForResult(intent, DISHBOSCH);
            }
        });


        tvDishLgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000007");
                intent.putExtra("compid", "IFBCC000001");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Dishwasher");
                intent.putExtra("Flag", "Dish_Lg");
                intent.putExtra("Company", "Lg");
                startActivityForResult(intent, DISHLG);
            }
        });
        tvDishSamsungAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000007");
                intent.putExtra("compid", "IFBCC000002");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Dishwasher");
                intent.putExtra("Flag", "Dish_Sam");
                intent.putExtra("Company", "Samsung");
                startActivityForResult(intent, DISHSAMSUNG);
            }
        });
        tvMicroLgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000011");
                intent.putExtra("compid", "IFBCC000001");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Micro oven");
                intent.putExtra("Flag", "Micro_LG");
                intent.putExtra("Company", "LG");
                startActivityForResult(intent, MICROLG);
            }
        });
        tvMicroSamsungAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", "IFBPC1000011");
                intent.putExtra("compid", "IFBCC000002");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Micro oven");
                intent.putExtra("Flag", "Micro_Sam");
                intent.putExtra("Company", "Samsung");
                startActivityForResult(intent, MICROSAMSUNG);
            }
        });
        tvMicroWhirlpoolAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", microovenID);
                intent.putExtra("compid", Whirlpool);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Micro oven");
                intent.putExtra("Flag", "Micro_Whirl");
                intent.putExtra("Company", "WhirlPool");
                startActivityForResult(intent, MICROWHIRLPOOL);
            }
        });

        tvMicroPanasonicAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", microovenID);
                intent.putExtra("compid", Panasonic);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Micro oven");
                intent.putExtra("Flag", "Micro_Pana");
                intent.putExtra("Company", "Panasonic");
                startActivityForResult(intent, MICROPANASONIC);
            }
        });
        tvMicroGodrejAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", microovenID);
                intent.putExtra("compid", Godrej);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Micro oven");
                intent.putExtra("Flag", "Micro_Godrej");
                intent.putExtra("Company", "Godrej");
                startActivityForResult(intent, MICROGODREJ);
            }
        });
        tvMicroOnidaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", microovenID);
                intent.putExtra("compid", Onida);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Micro oven");
                intent.putExtra("Flag", "Micro_Onida");
                intent.putExtra("Company", "Onida");
                startActivityForResult(intent, MICROONIDA);
            }
        });
        tvKAFaberAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", KAID);
                intent.putExtra("compid", Faber);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Kitchen Appliance");
                intent.putExtra("Flag", "KA_Faber");
                intent.putExtra("Company", "Faber");
                startActivityForResult(intent, KAFABER);
            }
        });
        tvKASunflameAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", KAID);
                intent.putExtra("compid", Sunflame);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Kitchen Appliance");
                intent.putExtra("Flag", "KA_Sun");
                intent.putExtra("Company", "Sunflame");
                startActivityForResult(intent, KASUN);
            }
        });
        tvKAElicaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", KAID);
                intent.putExtra("compid", ELICA);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Kitchen Appliance");
                intent.putExtra("Flag", "KA_Elica");
                intent.putExtra("Company", "Elica");
                startActivityForResult(intent, KAELICA);
            }
        });
        tvKAKaffAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", KAID);
                intent.putExtra("compid", KAFF);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Kitchen Appliance");
                intent.putExtra("Flag", "KA_Kaff");
                intent.putExtra("Company", "KAFF");
                startActivityForResult(intent, KAKAFF);
            }
        });
        tvKABoschAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", KAID);
                intent.putExtra("compid", Bosch);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Kitchen Appliance");
                intent.putExtra("Flag", "KA_Bosch");
                intent.putExtra("Company", "Bosch & Simens");
                startActivityForResult(intent, KABOSCH);
            }
        });
        tvFLULgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WFLUID);
                intent.putExtra("compid", LG);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine FLU");
                intent.putExtra("Flag", "WM_FLU_LG");
                intent.putExtra("Company", "Lg");
                startActivityForResult(intent, WMFLULG);
            }
        });

        tvFLUSamsungAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WFLUID);
                intent.putExtra("compid", SamSung);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine FLU");
                intent.putExtra("Flag", "WM_FLU_Sam");
                intent.putExtra("Company", "Samsung");
                startActivityForResult(intent, WMFLUSAM);
            }
        });
        tvFLUBoschAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WFLUID);
                intent.putExtra("compid", Bosch);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine FLU");
                intent.putExtra("Flag", "WM_FLU_Bosch");
                intent.putExtra("Company", "Bosch & Simens");
                startActivityForResult(intent, WMFLUBOSCH);
            }
        });
        tvFLUWhirlpoolAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WFLUID);
                intent.putExtra("compid", Whirlpool);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine FLU");
                intent.putExtra("Flag", "WM_FLU_Whirlpool");
                intent.putExtra("Company", "Whirlpool");
                startActivityForResult(intent, WMFLUWHIRLPOOL);
            }
        });
        tvFLUBekoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WFLUID);
                intent.putExtra("compid", Beko);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine FLU");
                intent.putExtra("Flag", "WM_FLU_Beko");
                intent.putExtra("Company", "Beko");
                startActivityForResult(intent, WMFLUWHIRLBEKO);
            }
        });
        tvTLLgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WTLID);
                intent.putExtra("compid", LG);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine TL");
                intent.putExtra("Flag", "WM_TL_LG");
                intent.putExtra("Company", "Lg");
                startActivityForResult(intent, TLLG);
            }
        });
        tvTLSamsungAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WTLID);
                intent.putExtra("compid", SamSung);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine TL");
                intent.putExtra("Flag", "WM_TL_Sam");
                intent.putExtra("Company", "Samsung");
                startActivityForResult(intent, TLSAM);
            }
        });
        tvTLBoschAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WTLID);
                intent.putExtra("compid", Bosch);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine TL");
                intent.putExtra("Flag", "WM_TL_Bosch");
                intent.putExtra("Company", "Bosch & Simens");
                startActivityForResult(intent, TLBOSCH);
            }
        });
        tvTLWhirlpoolAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WTLID);
                intent.putExtra("compid", Whirlpool);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine TL");
                intent.putExtra("Flag", "WM_TL_Whirlpool");
                intent.putExtra("Company", "Whirlpool");
                startActivityForResult(intent, TLWHIRLPOOL);
            }
        });

        tvTLPanasonicAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WTLID);
                intent.putExtra("compid", Panasonic);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine TL");
                intent.putExtra("Flag", "WM_TL_Pana");
                intent.putExtra("Company", "Panasonic");
                startActivityForResult(intent, TLPANSONIC);
            }
        });
        tvTLGodrejAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WTLID);
                intent.putExtra("compid", Godrej);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine TL");
                intent.putExtra("Flag", "WM_TL_Godrej");
                intent.putExtra("Company", "Godrej");
                startActivityForResult(intent, TLGODREJ);
            }
        });
        tvTLOnidaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WTLID);
                intent.putExtra("compid", Onida);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washing Machine TL");
                intent.putExtra("Flag", "WM_TL_Onida");
                intent.putExtra("Company", "Onida");
                startActivityForResult(intent, TLONIDA);
            }
        });
        tvWasherDisherLgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WASHERDRYERID);
                intent.putExtra("compid", LG);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washer Dryer");
                intent.putExtra("Flag", "Dryer_LG");
                intent.putExtra("Company", "LG");
                startActivityForResult(intent, DRYERLG);
            }
        });

        tvWasherDisherSamsungAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WASHERDRYERID);
                intent.putExtra("compid", SamSung);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washer Dryer");
                intent.putExtra("Flag", "Dryer_Samsung");
                intent.putExtra("Company", "Samsung");
                startActivityForResult(intent, DRYERSAM);
            }
        });
        tvWasherDisherWhirlpoolAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WASHERDRYERID);
                intent.putExtra("compid", Whirlpool);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washer Dryer");
                intent.putExtra("Flag", "Dryer_Whirlpool");
                intent.putExtra("Company", "Whirlpool");
                startActivityForResult(intent, DRYERWHIRLPOOL);
            }
        });
        tvWasherDisherPanasonicAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WASHERDRYERID);
                intent.putExtra("compid", Panasonic);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washer Dryer");
                intent.putExtra("Flag", "Dryer_Panasonic");
                intent.putExtra("Company", "Panasonic");
                startActivityForResult(intent, DRYERPANASONIC);
            }
        });
        tvWasherDisherGodrejAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WASHERDRYERID);
                intent.putExtra("compid", Godrej);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washer Dryer");
                intent.putExtra("Flag", "Dryer_Godrej");
                intent.putExtra("Company", "Godrej");
                startActivityForResult(intent, DRYERGODREJ);
            }
        });
        tvWasherDisherOnidaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", WASHERDRYERID);
                intent.putExtra("compid", Onida);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Washer Dryer");
                intent.putExtra("Flag", "Dryer_Onida");
                intent.putExtra("Company", "Onida");
                startActivityForResult(intent, DRYERONIDA);
            }
        });


        tvREFRIGERATORSamsungAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", REFRDC);
                intent.putExtra("compid", SamSung);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Refrigerator DC");
                intent.putExtra("Flag", "DC_SAM");
                intent.putExtra("Company", "Samsung");
                startActivityForResult(intent, DCSAM);
            }
        });
        tvREFRIGERATORLgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", REFRDC);
                intent.putExtra("compid", LG);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Refrigerator DC");
                intent.putExtra("Flag", "DC_LG");
                intent.putExtra("Company", "LG");
                startActivityForResult(intent, DCLG);
            }
        });
        tvREFRIGERATORWhirlpoolAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", REFRDC);
                intent.putExtra("compid", Whirlpool);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Refrigerator DC");
                intent.putExtra("Flag", "DC_WHIRLPOOL");
                intent.putExtra("Company", "Whirlpool");
                startActivityForResult(intent, DCWHIRLPOOL);
            }
        });
        tvREFRIGERATORHaierAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", REFRDC);
                intent.putExtra("compid", "IFBCC000021");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Refrigerator DC");
                intent.putExtra("Flag", "DC_HAIER");
                intent.putExtra("Company", "Haier");
                startActivityForResult(intent, DCHAIER);
            }
        });
        tvREFRIGERATORGodrejAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", REFRDC);
                intent.putExtra("compid", Godrej);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Refrigerator DC");
                intent.putExtra("Flag", "DC_Godrej");
                intent.putExtra("Company", "Godrej");
                startActivityForResult(intent, DCGodrej);
            }
        });
        tvREFRIGERATORFFGodrejAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", REFFF);
                intent.putExtra("compid", Godrej);
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Refrigerator FF");
                intent.putExtra("Flag", "FF_Godrej");
                intent.putExtra("Company", "Godrej");
                startActivityForResult(intent, FFGodrej);
            }
        });
        tvREFRIGERATORHaierAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, CompetitorModelActivity.class);
                intent.putExtra("categoryID", REFFF);
                intent.putExtra("compid", "IFBCC000021");
                intent.putExtra("financialyear", finalcialchecking);
                intent.putExtra("month", month);
                intent.putExtra("Category", "Refrigerator FF");
                intent.putExtra("Flag", "FF_HAIER");
                intent.putExtra("Company", "Haier");
                startActivityForResult(intent, FFHAIER);
            }
        });

        tvAirAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, AirConditionerDialogActivity.class);
                intent.putExtra("previousmonthStatus", previousMonthData);
                startActivityForResult(intent, ACREQUEST);


            }
        });
        tvREFRIGERATORAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, RefregeratorDialogActivity.class);
                intent.putExtra("previousmonthStatus", previousMonthData);
                startActivityForResult(intent, REFREQUEST);


            }
        });

        tvREFRIGERATORFFAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, RefregeratorFFDialogActivity.class);
                intent.putExtra("previousmonthStatus", previousMonthData);
                startActivityForResult(intent, REFFFREQUEST);


            }
        });

        tvClothsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, ClothsDryerDialogActivity.class);
                intent.putExtra("previousmonthStatus", previousMonthData);
                startActivityForResult(intent, CLOTHSDRYERREQUEST);

            }
        });
        tvWasherDisherAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, WasherDryerDialogActivity.class);
                intent.putExtra("previousmonthStatus", previousMonthData);
                startActivityForResult(intent, WASHERDRYERREQUEST);

            }
        });
        tvDishAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, DishwasherDialogActivity.class);
                intent.putExtra("previousmonthStatus", previousMonthData);
                startActivityForResult(intent, DISHREQUEST);
            }
        });
        tvMicroAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, MicroOvenDialogActivity.class);
                intent.putExtra("previousmonthStatus", previousMonthData);
                startActivityForResult(intent, MICROOVENREQUEST);
            }
        });

        tvKAAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, KADialogActivity.class);
                intent.putExtra("previousmonthStatus", previousMonthData);
                startActivityForResult(intent, KITCHENREQUEST);
            }
        });

        tvFLUAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, WMFLUDialogActivity.class);
                intent.putExtra("previousmonthStatus", previousMonthData);
                startActivityForResult(intent, FLUREQUEST);
            }
        });

        tvTLAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, WMTLDialogActivity.class);
                intent.putExtra("previousmonthStatus", previousMonthData);
                startActivityForResult(intent, TLREQUEST);
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
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, NewDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });


        etAirLG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirLG.getText().toString().length() > 0) {
                    prefManager.saveAirLG(etAirLG.getText().toString());
                    airLg = "IFBPC1000001" + "-" + "IFBCC000001" + "#" + etAirLG.getText().toString();
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airVoltas + "," + airSAMSUNG + "," + airCARRIER + "," + airBLUESTAR + "," + airONIDA + "," + airPANASONIC + "," + airWHIRLPOOL + "," + airOGENERAL + "," + airGODREJ + "," + airHAIER + "," + clothsIFB + "," + clothsBOSCH + "," + dishIfb + "," + dishBosch + "," + dishLg + "," + dishSamsung + "," + dishOthers + "," + microIfb + "," + microLg + "," + microSamSung + "," + microWhirlPool + "," + microPanasonic + "," + microGodrej + "," + microOnida + "," + microOthers + "," + kaIfb + "," + KaFaber + "," + KaSunFlame + "," + KaElica + "," + KaKaff + "," + KaBosch + "," + KaOthers;
                    Log.d("categoryyy", category);
                }

            }
        });

        etAirSamSung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirSamSung.getText().toString().length() > 0) {

                    airSAMSUNG = "IFBPC1000001" + "-" + "IFBCC000002" + "#" + etAirSamSung.getText().toString();
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airVoltas + "," + airSAMSUNG + "," + airCARRIER + "," + airBLUESTAR + "," + airONIDA + "," + airPANASONIC + "," + airWHIRLPOOL + "," + airOGENERAL + "," + airGODREJ + "," + airHAIER + "," + clothsIFB + "," + clothsBOSCH + "," + dishIfb + "," + dishBosch + "," + dishLg + "," + dishSamsung + "," + dishOthers + "," + microIfb + "," + microLg + "," + microSamSung + "," + microWhirlPool + "," + microPanasonic + "," + microGodrej + "," + microOnida + "," + microOthers + "," + kaIfb + "," + KaFaber + "," + KaSunFlame + "," + KaElica + "," + KaKaff + "," + KaBosch + "," + KaOthers;
                    Log.d("categoryyy", category);
                }

            }
        });

        etAirDaikin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirDaikin.getText().toString().length() > 0) {
                    airDaikin = "IFBPC1000001" + "-" + "IFBCC000009" + "#" + etAirDaikin.getText().toString();
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airVoltas + "," + airSAMSUNG + "," + airCARRIER + "," + airBLUESTAR + "," + airONIDA + "," + airPANASONIC + "," + airWHIRLPOOL + "," + airOGENERAL + "," + airGODREJ + "," + airHAIER + "," + clothsIFB + "," + clothsBOSCH + "," + dishIfb + "," + dishBosch + "," + dishLg + "," + dishSamsung + "," + dishOthers + "," + microIfb + "," + microLg + "," + microSamSung + "," + microWhirlPool + "," + microPanasonic + "," + microGodrej + "," + microOnida + "," + microOthers + "," + kaIfb + "," + KaFaber + "," + KaSunFlame + "," + KaElica + "," + KaKaff + "," + KaBosch + "," + KaOthers;
                }

            }
        });

        etCarrier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCarrier.getText().toString().length() > 0) {
                    airCARRIER = "IFBPC1000001" + "-" + "IFBCC000018" + "#" + etCarrier.getText().toString();
                }

            }
        });

        etAirBlueStar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirBlueStar.getText().toString().length() > 0) {
                    airBLUESTAR = "IFBPC1000001" + "-" + "IFBCC000019" + "#" + etAirBlueStar.getText().toString();
                }

            }
        });
        etAirVoltas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirVoltas.getText().toString().length() > 0) {
                    airVoltas = "IFBPC1000001" + "-" + "IFBCC000008" + "#" + etAirVoltas.getText().toString();
                }

            }
        });

        etAirOnida.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirOnida.getText().toString().length() > 0) {
                    airONIDA = "IFBPC1000001" + "-" + "IFBCC000017" + "#" + etAirOnida.getText().toString();

                }

            }
        });

        etAirPanaSonic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirPanaSonic.getText().toString().length() > 0) {
                    airPANASONIC = "IFBPC1000001" + "-" + "IFBCC000007" + "#" + etAirPanaSonic.getText().toString();
                }

            }
        });

        etAirWhirlPool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirWhirlPool.getText().toString().length() > 0) {
                    airWHIRLPOOL = "IFBPC1000001" + "-" + "IFBCC000005" + "#" + etAirWhirlPool.getText().toString();
                }

            }
        });

        etAirOGenaral.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirOGenaral.getText().toString().length() > 0) {
                    airOGENERAL = "IFBPC1000001" + "-" + "IFBCC000020" + "#" + etAirOGenaral.getText().toString();

                }

            }
        });

        etAirGodrej.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirGodrej.getText().toString().length() > 0) {
                    airGODREJ = "IFBPC1000001" + "-" + "IFBCC000006" + "#" + etAirGodrej.getText().toString();
                }

            }
        });

        etAirHaier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirHaier.getText().toString().length() > 0) {
                    airHAIER = "IFBPC1000001" + "-" + "IFBCC000021" + "#" + etAirHaier.getText().toString();
                }

            }
        });

        etAirLloyds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirLloyds.getText().toString().length() > 0) {
                    airLloyds = "IFBPC1000001" + "-" + "IFBCC000010" + "#" + etAirLloyds.getText().toString();
                }

            }
        });

        etAirOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirOthers.getText().toString().length() > 0) {
                    airOthers = "IFBPC1000001" + "-" + "IFBCC000004" + "#" + etAirOthers.getText().toString();
                }

            }
        });

        //cloths
        etClothsBosch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etClothsBosch.getText().toString().length() > 0) {
                    clothsBOSCH = "IFBPC1000005" + "-" + "IFBCC000003" + "#" + etClothsBosch.getText().toString();
                }

            }
        });


        etDishBosch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etDishBosch.getText().toString().length() > 0) {
                    dishBosch = "IFBPC1000007" + "-" + "IFBCC000003" + "#" + etDishBosch.getText().toString();
                }

            }
        });

        etDishLg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etDishLg.getText().toString().length() > 0) {
                    dishLg = "IFBPC1000007" + "-" + "IFBCC000001" + "#" + etDishLg.getText().toString();
                    Log.d("categoryyy", category);
                }
            }
        });

        etDishSamsung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etDishSamsung.getText().toString().length() > 0) {
                    dishSamsung = "IFBPC1000007" + "-" + "IFBCC000002" + "#" + etDishSamsung.getText().toString();
                    Log.d("categoryyy", category);

                }
            }
        });

        etDishOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etDishOther.getText().toString().length() > 0) {
                    dishOthers = "IFBPC1000007" + "-" + "IFBCC000004" + "#" + etDishOther.getText().toString();
                    Log.d("categoryyy", category);


                }
            }
        });

        etMicroLg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMicroLg.getText().toString().length() > 0) {
                    microLg = "IFBPC1000011" + "-" + "IFBCC000001" + "#" + etMicroLg.getText().toString();

                }

            }
        });

        etMicroSamsung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMicroLg.getText().toString().length() > 0) {
                    microSamSung = "IFBPC1000011" + "-" + "IFBCC000002" + "#" + etMicroSamsung.getText().toString();


                }

            }
        });

        etMicroWhirlPool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMicroLg.getText().toString().length() > 0) {
                    microWhirlPool = "IFBPC1000011" + "-" + "IFBCC000005" + "#" + etMicroWhirlPool.getText().toString();


                }

            }
        });

        etMicroPanasonic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMicroLg.getText().toString().length() > 0) {
                    microPanasonic = "IFBPC1000011" + "-" + "IFBCC000007" + "#" + etMicroPanasonic.getText().toString();

                }

            }
        });

        etMicroGodrej.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMicroLg.getText().toString().length() > 0) {
                    microGodrej = "IFBPC1000011" + "-" + "IFBCC000006" + "#" + etMicroGodrej.getText().toString();

                }

            }
        });

        etMicroOnida.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMicroLg.getText().toString().length() > 0) {
                    microOnida = "IFBPC1000011" + "-" + "IFBCC000017" + "#" + etMicroOnida.getText().toString();

                }

            }
        });

        etMicroOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMicroLg.getText().toString().length() > 0) {
                    microOthers = "IFBPC1000011" + "-" + "IFBCC000004" + "#" + etMicroOthers.getText().toString();

                }

            }
        });

        etKAFaber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etKAFaber.getText().toString().length() > 0) {
                    KaFaber = "IFBPC1000035" + "-" + "IFBCC000013" + "#" + etKAFaber.getText().toString();

                }

            }
        });

        etKASunFlame.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etKASunFlame.getText().toString().length() > 0) {
                    KaSunFlame = "IFBPC1000035" + "-" + "IFBCC000022" + "#" + etKASunFlame.getText().toString();

                }

            }
        });

        etKAElica.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etKAElica.getText().toString().length() > 0) {
                    KaElica = "IFBPC1000035" + "-" + "IFBCC000014" + "#" + etKAElica.getText().toString();

                }

            }
        });

        etKAKaff.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etKAKaff.getText().toString().length() > 0) {
                    KaKaff = "IFBPC1000035" + "-" + "IFBCC000012" + "#" + etKAKaff.getText().toString();

                }

            }
        });
        etKABosch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etKABosch.getText().toString().length() > 0) {
                    KaBosch = "IFBPC1000035" + "-" + "IFBCC000003" + "#" + etKABosch.getText().toString();

                }

            }
        });

        etKAOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etKAOthers.getText().toString().length() > 0) {
                    KaOthers = "IFBPC1000035" + "-" + "IFBCC000004" + "#" + etKAOthers.getText().toString();

                }

            }
        });

        etFLULg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etFLULg.getText().toString().length() > 0) {
                    FLULg = "IFBPC1000021" + "-" + "IFBCC000001" + "#" + etFLULg.getText().toString();

                }

            }
        });

        etFLUSamsung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etFLUSamsung.getText().toString().length() > 0) {
                    FLUSamsung = "IFBPC1000021" + "-" + "IFBCC000002" + "#" + etFLUSamsung.getText().toString();

                }

            }
        });

        etFLUBosch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etFLUBosch.getText().toString().length() > 0) {
                    FLUBosch = "IFBPC1000021" + "-" + "IFBCC000003" + "#" + etFLUBosch.getText().toString();

                }

            }
        });

        etFLUWhirlPool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etFLUWhirlPool.getText().toString().length() > 0) {
                    FLUWhirlPool = "IFBPC1000021" + "-" + "IFBCC000005" + "#" + etFLUWhirlPool.getText().toString();

                }

            }
        });

        etFLUBeko.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etFLUBeko.getText().toString().length() > 0) {
                    FLUBeko = "IFBPC1000021" + "-" + "IFBCC000024" + "#" + etFLUBeko.getText().toString();

                }

            }
        });

        etFLUOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etFLUOthers.getText().toString().length() > 0) {
                    FLUOthers = "IFBPC1000021" + "-" + "IFBCC000004" + "#" + etFLUOthers.getText().toString();

                }

            }
        });

        etTLLg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTLLg.getText().toString().length() > 0) {
                    TLLg = "IFBPC1000025" + "-" + "IFBCC000001" + "#" + etTLLg.getText().toString();

                }

            }
        });

        etTLSamsung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTLSamsung.getText().toString().length() > 0) {
                    TLSamsung = "IFBPC1000025" + "-" + "IFBCC000002" + "#" + etTLSamsung.getText().toString();

                }

            }
        });

        etTLBosch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTLBosch.getText().toString().length() > 0) {
                    TLBosch = "IFBPC1000025" + "-" + "IFBCC000003" + "#" + etTLBosch.getText().toString();

                }

            }
        });

        etTLWhirlPool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTLWhirlPool.getText().toString().length() > 0) {
                    TLWhirlPool = "IFBPC1000025" + "-" + "IFBCC000005" + "#" + etTLWhirlPool.getText().toString();

                }

            }
        });

        etTLPanasonic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTLPanasonic.getText().toString().length() > 0) {
                    TLPanasonic = "IFBPC1000025" + "-" + "IFBCC000007" + "#" + etTLPanasonic.getText().toString();

                }

            }
        });

        etTLGodrej.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTLGodrej.getText().toString().length() > 0) {
                    TLGodrej = "IFBPC1000025" + "-" + "IFBCC000006" + "#" + etTLGodrej.getText().toString();

                }

            }
        });

        etTLOnida.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTLOnida.getText().toString().length() > 0) {
                    TLOnida = "IFBPC1000025" + "-" + "IFBCC000017" + "#" + etTLOnida.getText().toString();

                }

            }
        });

        etTLOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTLOthers.getText().toString().length() > 0) {
                    TLOthers = "IFBPC1000025" + "-" + "IFBCC000004" + "#" + etTLOthers.getText().toString();

                }

            }
        });


        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAirIFB.getText().toString().length() > 0) {
                    if (etAirLG.getText().toString().length() > 0) {
                        if (etAirSamSung.getText().toString().length() > 0) {
                            if (etAirDaikin.getText().toString().length() > 0) {
                                if (etCarrier.getText().toString().length() > 0) {
                                    if (etAirBlueStar.getText().toString().length() > 0) {
                                        if (etAirVoltas.getText().toString().length() > 0) {
                                            if (etAirOnida.getText().toString().length() > 0) {
                                                if (etAirPanaSonic.getText().toString().length() > 0) {
                                                    if (etAirWhirlPool.getText().toString().length() > 0) {
                                                        if (etAirOGenaral.getText().toString().length() > 0) {
                                                            if (etAirGodrej.getText().toString().length() > 0) {
                                                                if (etAirHaier.getText().toString().length() > 0) {
                                                                    if (etAirLloyds.getText().toString().length() > 0) {
                                                                        if (etAirOthers.getText().toString().length() > 0) {
                                                                            if (etClothsIFB.getText().toString().length() > 0) {
                                                                                if (etClothsBosch.getText().toString().length() > 0) {
                                                                                    if (etDishIFB.getText().toString().length() > 0) {
                                                                                        if (etDishBosch.getText().toString().length() > 0) {
                                                                                            if (etDishLg.getText().toString().length() > 0) {
                                                                                                if (etDishSamsung.getText().toString().length() > 0) {
                                                                                                    if (etDishOther.getText().toString().length() > 0) {
                                                                                                        if (etMicroIfb.getText().toString().length() > 0) {
                                                                                                            if (etMicroLg.getText().toString().length() > 0) {
                                                                                                                if (etMicroSamsung.getText().toString().length() > 0) {
                                                                                                                    if (etMicroWhirlPool.getText().toString().length() > 0) {
                                                                                                                        if (etMicroPanasonic.getText().toString().length() > 0) {
                                                                                                                            if (etMicroGodrej.getText().toString().length() > 0) {
                                                                                                                                if (etMicroOnida.getText().toString().length() > 0) {
                                                                                                                                    if (etMicroOthers.getText().toString().length() > 0) {
                                                                                                                                        if (etKAIfb.getText().toString().length() > 0) {
                                                                                                                                            if (etKAFaber.getText().toString().length() > 0) {
                                                                                                                                                if (etKASunFlame.getText().toString().length() > 0) {
                                                                                                                                                    if (etKAElica.getText().toString().length() > 0) {
                                                                                                                                                        if (etKAKaff.getText().toString().length() > 0) {
                                                                                                                                                            if (etKABosch.getText().toString().length() > 0) {
                                                                                                                                                                if (etKAOthers.getText().toString().length() > 0) {
                                                                                                                                                                    if (etFLUIfb.getText().toString().length() > 0) {
                                                                                                                                                                        if (etFLULg.getText().toString().length() > 0) {
                                                                                                                                                                            if (etFLUSamsung.getText().toString().length() > 0) {
                                                                                                                                                                                if (etFLUBosch.getText().toString().length() > 0) {
                                                                                                                                                                                    if (etFLUWhirlPool.getText().toString().length() > 0) {
                                                                                                                                                                                        if (etFLUBeko.getText().toString().length() > 0) {
                                                                                                                                                                                            if (etFLUOthers.getText().toString().length() > 0) {
                                                                                                                                                                                                if (etTLIfb.getText().toString().length() > 0) {
                                                                                                                                                                                                    if (etTLLg.getText().toString().length() > 0) {
                                                                                                                                                                                                        if (etTLSamsung.getText().toString().length() > 0) {
                                                                                                                                                                                                            if (etTLBosch.getText().toString().length() > 0) {
                                                                                                                                                                                                                if (etTLWhirlPool.getText().toString().length() > 0) {
                                                                                                                                                                                                                    if (etTLPanasonic.getText().toString().length() > 0) {
                                                                                                                                                                                                                        if (etTLGodrej.getText().toString().length() > 0) {
                                                                                                                                                                                                                            if (etTLOnida.getText().toString().length() > 0) {
                                                                                                                                                                                                                                if (etTLOthers.getText().toString().length() > 0) {
                                                                                                                                                                                                                                    if (!etAirIFB.getText().toString().equals("0") || !etClothsIFB.getText().toString().equals("0") || !etDishIFB.getText().toString().equals("0") || !etMicroIfb.getText().toString().equals("0") || !etKAIfb.getText().toString().equals("0") || !etFLUIfb.getText().toString().equals("0") || !etTLIfb.getText().toString().equals("0") || !etWasherDisherIfb.getText().toString().equals("0")) {


                                                                                                                                                                                                                                        postDisplaymatrix();
                                                                                                                                                                                                                                    } else {
                                                                                                                                                                                                                                        ifbAlert();

                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                } else {
                                                                                                                                                                                                                                    etTLOthers.setError("Please enter quantity");
                                                                                                                                                                                                                                    etTLOthers.requestFocus();
                                                                                                                                                                                                                                }


                                                                                                                                                                                                                            } else {
                                                                                                                                                                                                                                etTLOnida.setError("Please enter quantity");
                                                                                                                                                                                                                                etTLOnida.requestFocus();
                                                                                                                                                                                                                            }


                                                                                                                                                                                                                        } else {
                                                                                                                                                                                                                            etTLGodrej.setError("Please enter quantity");
                                                                                                                                                                                                                            etTLGodrej.requestFocus();
                                                                                                                                                                                                                        }


                                                                                                                                                                                                                    } else {
                                                                                                                                                                                                                        etTLPanasonic.setError("Please enter quantity");
                                                                                                                                                                                                                        etTLPanasonic.requestFocus();
                                                                                                                                                                                                                    }


                                                                                                                                                                                                                } else {
                                                                                                                                                                                                                    etTLWhirlPool.setError("Please enter quantity");
                                                                                                                                                                                                                    etTLWhirlPool.requestFocus();
                                                                                                                                                                                                                }


                                                                                                                                                                                                            } else {
                                                                                                                                                                                                                etTLBosch.setError("Please enter quantity");
                                                                                                                                                                                                                etTLBosch.requestFocus();
                                                                                                                                                                                                            }


                                                                                                                                                                                                        } else {
                                                                                                                                                                                                            etTLSamsung.setError("Please enter quantity");
                                                                                                                                                                                                            etTLSamsung.requestFocus();
                                                                                                                                                                                                        }


                                                                                                                                                                                                    } else {
                                                                                                                                                                                                        etTLLg.setError("Please enter quantity");
                                                                                                                                                                                                        etTLLg.requestFocus();
                                                                                                                                                                                                    }


                                                                                                                                                                                                } else {
                                                                                                                                                                                                    etTLIfb.setError("Please enter quantity");
                                                                                                                                                                                                    etTLIfb.requestFocus();
                                                                                                                                                                                                }

                                                                                                                                                                                            } else {
                                                                                                                                                                                                etFLUOthers.setError("Please enter quantity");
                                                                                                                                                                                                etFLUOthers.requestFocus();
                                                                                                                                                                                            }

                                                                                                                                                                                        } else {
                                                                                                                                                                                            etFLUBeko.setError("Please enter quantity");
                                                                                                                                                                                            etFLUBeko.requestFocus();
                                                                                                                                                                                        }

                                                                                                                                                                                    } else {
                                                                                                                                                                                        etFLUWhirlPool.setError("Please enter quantity");
                                                                                                                                                                                        etFLUWhirlPool.requestFocus();
                                                                                                                                                                                    }

                                                                                                                                                                                } else {
                                                                                                                                                                                    etFLUBosch.setError("Please enter quantity");
                                                                                                                                                                                    etFLUBosch.requestFocus();
                                                                                                                                                                                }

                                                                                                                                                                            } else {
                                                                                                                                                                                etFLUSamsung.setError("Please enter quantity");
                                                                                                                                                                                etFLUSamsung.requestFocus();
                                                                                                                                                                            }

                                                                                                                                                                        } else {
                                                                                                                                                                            etFLULg.setError("Please enter quantity");
                                                                                                                                                                            etFLULg.requestFocus();
                                                                                                                                                                        }

                                                                                                                                                                    } else {
                                                                                                                                                                        etFLUIfb.setError("Please enter quantity");
                                                                                                                                                                        etFLUIfb.requestFocus();
                                                                                                                                                                    }

                                                                                                                                                                } else {
                                                                                                                                                                    etKAOthers.setError("Please enter quantity");
                                                                                                                                                                    etKAOthers.requestFocus();
                                                                                                                                                                }

                                                                                                                                                            } else {
                                                                                                                                                                etKABosch.setError("Please enter quantity");
                                                                                                                                                                etKABosch.requestFocus();
                                                                                                                                                            }

                                                                                                                                                        } else {
                                                                                                                                                            etKAKaff.setError("Please enter quantity");
                                                                                                                                                            etKAKaff.requestFocus();
                                                                                                                                                        }

                                                                                                                                                    } else {
                                                                                                                                                        etKAElica.setError("Please enter quantity");
                                                                                                                                                        etKAElica.requestFocus();
                                                                                                                                                    }

                                                                                                                                                } else {
                                                                                                                                                    etKASunFlame.setError("Please enter quantity");
                                                                                                                                                    etKASunFlame.requestFocus();
                                                                                                                                                }

                                                                                                                                            } else {
                                                                                                                                                etKAFaber.setError("Please enter quantity");
                                                                                                                                                etKAFaber.requestFocus();
                                                                                                                                            }

                                                                                                                                        } else {
                                                                                                                                            etKAIfb.setError("Please enter quantity");
                                                                                                                                            etKAIfb.requestFocus();
                                                                                                                                        }

                                                                                                                                    } else {
                                                                                                                                        etMicroOthers.setError("Please enter quantity");
                                                                                                                                        etMicroOthers.requestFocus();
                                                                                                                                    }

                                                                                                                                } else {
                                                                                                                                    etMicroOnida.setError("Please enter quantity");
                                                                                                                                    etMicroOnida.requestFocus();
                                                                                                                                }

                                                                                                                            } else {
                                                                                                                                etMicroGodrej.setError("Please enter quantity");
                                                                                                                                etMicroGodrej.requestFocus();
                                                                                                                            }

                                                                                                                        } else {
                                                                                                                            etMicroPanasonic.setError("Please enter quantity");
                                                                                                                            etMicroPanasonic.requestFocus();
                                                                                                                        }

                                                                                                                    } else {
                                                                                                                        etMicroWhirlPool.setError("Please enter quantity");
                                                                                                                        etMicroWhirlPool.requestFocus();
                                                                                                                    }


                                                                                                                } else {
                                                                                                                    etMicroSamsung.setError("Please enter quantity");
                                                                                                                    etMicroSamsung.requestFocus();
                                                                                                                }

                                                                                                            } else {
                                                                                                                etMicroLg.setError("Please enter quantity");
                                                                                                                etMicroLg.requestFocus();
                                                                                                            }

                                                                                                        } else {
                                                                                                            etMicroIfb.setError("Please enter quantity");
                                                                                                            etMicroIfb.requestFocus();
                                                                                                        }


                                                                                                    } else {
                                                                                                        etDishOther.setError("Please enter quantity");
                                                                                                        etDishOther.requestFocus();
                                                                                                    }

                                                                                                } else {
                                                                                                    etDishSamsung.setError("Please enter quantity");
                                                                                                    etDishSamsung.requestFocus();
                                                                                                }

                                                                                            } else {
                                                                                                etDishLg.setError("Please enter quantity");
                                                                                                etDishLg.requestFocus();
                                                                                            }


                                                                                        } else {
                                                                                            etDishBosch.setError("Please enter quantity");
                                                                                            etDishBosch.requestFocus();
                                                                                        }


                                                                                    } else {
                                                                                        etDishIFB.setError("Please enter quantity");
                                                                                        etDishIFB.requestFocus();
                                                                                    }


                                                                                } else {
                                                                                    etClothsBosch.setError("Please enter quantity");
                                                                                    etClothsBosch.requestFocus();
                                                                                }

                                                                            } else {
                                                                                etClothsIFB.setError("Please enter quantity");
                                                                                etClothsIFB.requestFocus();
                                                                            }

                                                                        } else {
                                                                            etAirOthers.setError("Please enter quantity");
                                                                            etAirOthers.requestFocus();
                                                                        }

                                                                    } else {
                                                                        etAirLloyds.setError("Please enter quantity");
                                                                        etAirLloyds.requestFocus();
                                                                    }

                                                                } else {
                                                                    etAirHaier.setError("Please enter quantity");
                                                                    etAirHaier.requestFocus();
                                                                }

                                                            } else {
                                                                etAirGodrej.setError("Please enter quantity");
                                                                etAirGodrej.requestFocus();
                                                            }

                                                        } else {
                                                            etAirOGenaral.setError("Please enter quantity");
                                                            etAirOGenaral.requestFocus();
                                                        }

                                                    } else {
                                                        etAirWhirlPool.setError("Please enter quantity");
                                                        etAirWhirlPool.requestFocus();
                                                    }

                                                } else {
                                                    etAirPanaSonic.setError("Please enter quantity");
                                                    etAirPanaSonic.requestFocus();
                                                }

                                            } else {
                                                etAirOnida.setError("Please enter quantity");
                                                etAirOnida.requestFocus();
                                            }

                                        } else {
                                            etAirVoltas.setError("Please enter quantity");
                                            etAirVoltas.requestFocus();
                                        }

                                    } else {
                                        etAirBlueStar.setError("Please enter quantity");
                                        etAirBlueStar.requestFocus();
                                    }


                                } else {
                                    etCarrier.setError("Please enter quantity");
                                    etCarrier.requestFocus();
                                }

                            } else {
                                etAirDaikin.setError("Please enter quantity");
                                etAirDaikin.requestFocus();
                            }

                        } else {
                            etAirSamSung.setError("Please enter quantity");
                            etAirSamSung.requestFocus();
                        }

                    } else {
                        etAirLG.setError("Please enter quantity");
                        etAirLG.requestFocus();
                    }

                } else {
                    etAirIFB.setError("Please enter quantity");
                    etAirIFB.requestFocus();
                }

            }
        });
    }


   /* private void postDisplaymatrix() {
        category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airVoltas + "," + airSAMSUNG + "," + airCARRIER + "," + airBLUESTAR + "," + airONIDA + "," + airPANASONIC + "," + airWHIRLPOOL + "," + airOGENERAL + "," + airGODREJ + "," + airHAIER + "," + clothsIFB + "," + clothsBOSCH + "," + dishIfb + "," + dishBosch + "," + dishLg + "," + dishSamsung + "," + dishOthers + "," + microIfb + "," + microLg + "," + microSamSung + "," + microWhirlPool + "," + microPanasonic + "," + microGodrej + "," + microOnida + "," + microOthers + "," + kaIfb + "," + KaFaber + "," + KaSunFlame + "," + KaElica + "," + KaKaff + "," + KaBosch + "," + KaOthers + "," + FLUIfb + "," + FLULg + "," + FLUSamsung + "," + FLUBosch + "," + FLUWhirlPool + "," + FLUBeko + "," + FLUOthers + "," + TLIfb + "," + TLLg + "," + TLSamsung + "," + TLBosch + "," + TLWhirlPool + "," + TLPanasonic + "," + TLGodrej + "," + TLOnida + "," + TLOthers;
        Log.d("discategory", category);
        progressDialog.show();

        Call<UploadObject> fileUpload = uploadService.postdisplaymatrix(salesdate, category, modelId, userid, securitycode);
        fileUpload.enqueue(new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, retrofit2.Response<UploadObject> response) {
                progressDialog.dismiss();
                UploadObject extraWorkingDayModel = response.body();
                if (extraWorkingDayModel.isResponseStatus()) {
                    msg = extraWorkingDayModel.getResponseText();
                    // Toast.makeText(getApplicationContext(), extraWorkingDayModel.getResponseText(), Toast.LENGTH_SHORT).show();
                    Log.d("riku", "withocamera");
                    prefManager.saveAirConditionerId("0");
                    prefManager.saveDishWasherId("0");
                    prefManager.saveClothsDryerId("0");
                    prefManager.saveAirIfbSize(0);
                    prefManager.saveClothsIfbSize(0);
                    prefManager.saveDishIfbSize(0);
                    prefManager.saveMicroOvenId("0");
                    prefManager.saveMicroOvenIfbSize(0);
                    prefManager.SaveKAItemId("0");
                    prefManager.saveKAItemSize(0);
                    prefManager.saveWashingFLUId("0");
                    prefManager.saveWMFLUIfbSize(0);
                    prefManager.saveWashingTLId("0");
                    prefManager.saveWMTLIFBSize(0);
                    successAlert();

                } else {
                    //  Toast.makeText(getApplicationContext(), extraWorkingDayModel.getResponseText(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadObject> call, Throwable t) {
                progressDialog.dismiss();

                Log.e("error", "Error " + t.getMessage());
                //  Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();

                //   Toast.makeText(AttendanceManageActivity.this,"attendance saved without image",Toast.LENGTH_LONG).show();
            }

        });
    }*/

    private void postDisplaymatrix() {
        airIfb = "IFBPC1000001" + "-" + "IFBCC000015" + "#" + etAirIFB.getText().toString();
        clothsIFB = "IFBPC1000005" + "-" + "IFBCC000015" + "#" + etClothsIFB.getText().toString();
        dishIfb = "IFBPC1000007" + "-" + "IFBCC000015" + "#" + etDishIFB.getText().toString();
        microIfb = "IFBPC1000011" + "-" + "IFBCC000015" + "#" + etMicroIfb.getText().toString();
        kaIfb = "IFBPC1000035" + "-" + "IFBCC000015" + "#" + etKAIfb.getText().toString();
        FLUIfb = "IFBPC1000021" + "-" + "IFBCC000015" + "#" + etFLUIfb.getText().toString();
        TLIfb = "IFBPC1000025" + "-" + "IFBCC000015" + "#" + etTLIfb.getText().toString();
        refregeratorIfb = "IFBPC1000013" + "-" + "IFBCC000015" + "#" + etREFRIGERATORIfb.getText().toString();
        refregeratorFFIfb = "IFBPC1000040" + "-" + "IFBCC000015" + "#" + etREFRIGERATORFFIfb.getText().toString();


        washerIfb = "IFBPC1000039" + "-" + "IFBCC000015" + "#" + etWasherDisherIfb.getText().toString();
        washerLg = "IFBPC1000039" + "-" + "IFBCC000001" + "#" + etWasherDisherLg.getText().toString();
        washerSamSung = "IFBPC1000039" + "-" + "IFBCC000002" + "#" + etWasherDisherSamsung.getText().toString();
        washerWhirlPool = "IFBPC1000039" + "-" + "IFBCC000005" + "#" + etWasherDisherWhirlPool.getText().toString();
        washerPanasonic = "IFBPC1000039" + "-" + "IFBCC000007" + "#" + etWasherDisherPanasonic.getText().toString();
        washerGodrej = "IFBPC1000039" + "-" + "IFBCC000006" + "#" + etWasherDisherGodrej.getText().toString();
        washerOnida = "IFBPC1000039" + "-" + "IFBCC000017" + "#" + etWasherDisherOnida.getText().toString();
        washerOthers = "IFBPC1000039" + "-" + "IFBCC000004" + "#" + etWasherDisherOthers.getText().toString();

        refregeratorLg = "IFBPC1000013" + "-" + "IFBCC000001" + "#" + etREFRIGERATORLg.getText().toString();
        refregeratorSamSung = "IFBPC1000013" + "-" + "IFBCC000002" + "#" + etREFRIGERATORSamsung.getText().toString();
        refregeratorWhirlPool = "IFBPC1000013" + "-" + "IFBCC000005" + "#" + etREFRIGERATORWhirlPool.getText().toString();
        refregeratorHaier = "IFBPC1000013" + "-" + "IFBCC000021" + "#" + etREFRIGERATORHaier.getText().toString();
        refregeratorGodrej = "IFBPC1000013" + "-" + "IFBCC000006" + "#" + etREFRIGERATORGodrej.getText().toString();
        refregeratorOthers = "IFBPC1000013" + "-" + "IFBCC000004" + "#" + etREFRIGERATOROthers.getText().toString();


        refregeratorFFLg = "IFBPC1000040" + "-" + "IFBCC000001" + "#" + etREFRIGERATORFFLg.getText().toString();
        refregeratorFFSamSung = "IFBPC1000040" + "-" + "IFBCC000002" + "#" + etREFRIGERATORFFSamsung.getText().toString();
        refregeratorFFWhirlPool = "IFBPC1000040" + "-" + "IFBCC000005" + "#" + etREFRIGERATORFFWhirlPool.getText().toString();
        refregeratorFFHaier = "IFBPC1000040" + "-" + "IFBCC000021" + "#" + etREFRIGERATORFFHaier.getText().toString();
        refregeratorFFGodrej = "IFBPC1000040" + "-" + "IFBCC000006" + "#" + etREFRIGERATORFFGodrej.getText().toString();
        refregeratorFFOthers = "IFBPC1000040" + "-" + "IFBCC000004" + "#" + etREFRIGERATORFFOthers.getText().toString();


        model = airItem + "," + clothsItem + "," + dishItem + "," + microItem + "," + KAItem + "," + FLUItem + "," + tlItem + "," + washerDyerItem + "," + refItem + "," + refffItem;
        modelId = model.replaceAll("\\s+", "");
        modelArray.add(model);
        Log.d("newList", modelArray.toString());
        category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airVoltas + "," + airSAMSUNG + "," + airCARRIER + "," + airBLUESTAR + "," + airONIDA + "," + airPANASONIC + "," + airWHIRLPOOL + "," + airOGENERAL + "," + airGODREJ + "," + airHAIER + "," + clothsIFB + "," + clothsBOSCH + "," + dishIfb + "," + dishBosch + "," + dishLg + "," + dishSamsung + "," + dishOthers + "," + microIfb + "," + microLg + "," + microSamSung + "," + microWhirlPool + "," + microPanasonic + "," + microGodrej + "," + microOnida + "," + microOthers + "," + kaIfb + "," + KaFaber + "," + KaSunFlame + "," + KaElica + "," + KaKaff + "," + KaBosch + "," + KaOthers + "," + FLUIfb + "," + FLULg + "," + FLUSamsung + "," + FLUBosch + "," + FLUWhirlPool + "," + FLUBeko + "," + FLUOthers + "," + TLIfb + "," + TLLg + "," + TLSamsung + "," + TLBosch + "," + TLWhirlPool + "," + TLPanasonic + "," + TLGodrej + "," + TLOnida + "," + TLOthers + "," + washerIfb + "," + washerLg + "," + washerSamSung + "," + washerWhirlPool + "," + washerPanasonic + "," + washerGodrej + "," + washerOnida + "," + washerOthers + "," + refregeratorIfb + "," + refregeratorLg + "," + refregeratorSamSung + "," + refregeratorWhirlPool + "," + refregeratorHaier + "," + refregeratorGodrej + "," + refregeratorOthers + "," + refregeratorFFIfb + "," + refregeratorFFLg + "," + refregeratorFFSamSung + "," + refregeratorFFWhirlPool + "," + refregeratorFFHaier + "," + refregeratorFFGodrej + "," + refregeratorFFOthers;
        //  category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airVoltas + "," + airSAMSUNG + "," + airCARRIER + "," + airBLUESTAR + "," + airONIDA + "," + airPANASONIC + "," + airWHIRLPOOL + "," + airOGENERAL + "," + airGODREJ + "," + airHAIER + "," + clothsIFB + "," + clothsBOSCH + "," + dishIfb + "," + dishBosch + "," + dishLg + "," + dishSamsung + "," + dishOthers + "," + microIfb + "," + microLg + "," + microSamSung + "," + microWhirlPool + "," + microPanasonic + "," + microGodrej + "," + microOnida + "," + microOthers + "," + kaIfb + "," + KaFaber + "," + KaSunFlame + "," + KaElica + "," + KaKaff + "," + KaBosch + "," + KaOthers + "," + FLUIfb + "," + FLULg + "," + FLUSamsung + "," + FLUBosch + "," + FLUWhirlPool + "," + FLUBeko + "," + FLUOthers + "," + TLIfb + "," + TLLg + "," + TLSamsung + "," + TLBosch + "," + TLWhirlPool + "," + TLPanasonic + "," + TLGodrej + "," + TLOnida + "," + TLOthers;
        final ProgressDialog pd = new ProgressDialog(DisplayMatrixDynamicActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        AndroidNetworking.upload(AppController.APIURL + "api/post_DisplayMatrix")
                .addMultipartParameter("SalesDate", salesdate)
                .addMultipartParameter("Category", category)
                .addMultipartParameter("Model", modelId)
                .addMultipartParameter("AEMEmployeeID", userid)
                .addMultipartParameter("SecurityCode", securitycode)
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
                            pd.dismiss();
                            if (prefManager.getUserCode().equalsIgnoreCase("IFBAPPL00001")) {

                            } else {
                                imageAlert();
                            }


                            JSONArray jsonArray = job1.optJSONArray("responseData");

                            JSONObject object = jsonArray.optJSONObject(0);
                            String RowNum = object.optString("RowNum");
                            acFlag = RowNum;
                            sendACModelList.clear();
                            prefManager.saveAirConditionerId("");
                            prefManager.saveClothsDryerId("");
                            prefManager.saveMicroOvenId("");
                            prefManager.saveDishWasherId("");
                            prefManager.SaveKAItemId("");
                            prefManager.saveWasherDryerId("");
                            prefManager.saveWashingFLUId("");
                            prefManager.saveWashingTLId("");


                        } else {
                            pd.dismiss();
                            Toast.makeText(DisplayMatrixDynamicActivity.this, responseText, Toast.LENGTH_LONG).show();

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


    private void successAlert(String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrixDynamicActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_success, null);
        dialogBuilder.setView(dialogView);
        TextView tvInvalidDate = (TextView) dialogView.findViewById(R.id.tvSuccess);
        tvInvalidDate.setText(msg);

        Button btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();


                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, DisplayMatrixReportActivity.class);
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


    private void imageAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrixDynamicActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_display_camera, null);
        dialogBuilder.setView(dialogView);

        imgPic1 = (ImageView) dialogView.findViewById(R.id.imgPic1);
        imgPic2 = (ImageView) dialogView.findViewById(R.id.imgPic2);
        imgPic3 = (ImageView) dialogView.findViewById(R.id.imgPic3);

        imgPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialogForPic1();
            }
        });
        imgPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialogForPic2();
            }
        });

        imgPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialogForPic3();
            }
        });

        Button btnSave = (Button) dialogView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pic1Flag == 1) {
                    if (pic2Flag == 1) {
                        acChecking();

                    } else {
                        Toast.makeText(DisplayMatrixDynamicActivity.this, "Please Upload Image 2", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(DisplayMatrixDynamicActivity.this, "Please Upload Image 1", Toast.LENGTH_LONG).show();
                }

            }
        });


        alertDialog2 = dialogBuilder.create();
        alertDialog2.setCancelable(false);
        Window window = alertDialog2.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog2.show();
    }


    private void displayMatrixChecking() {
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
        progressBar.show();
        String surl = AppController.APIURL + "api/get_DisplayMatrixReport?AEMEmployeeID=" + prefManager.getUserId() + "&FinancialYear=" + finalcialchecking + "&Month=" + month + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputtlreport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.dismiss();

                        Log.d("responsetlreport", response);

                        // attendabceInfiList.clear();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();

                                displayMatrixAlert();


                            } else {

                                displayMatrixAlertForPreviousMonth();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DisplayMatrixDynamicActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();

                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };


        RequestQueue requestQueue = Volley.newRequestQueue(DisplayMatrixDynamicActivity.this);
        requestQueue.add(stringRequest);

    }


    private void displayMatrixAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrixDynamicActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_compsale, null);
        dialogBuilder.setView(dialogView);
        Button btnNow = (Button) dialogView.findViewById(R.id.btnNow);
        TextView tvResponse = (TextView) dialogView.findViewById(R.id.tvResponse);
        tvResponse.setText(responseText + " .Do you want to update ?");
        btnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                getReportList();

            }
        });

        Button btnLate = (Button) dialogView.findViewById(R.id.btnLate);
        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixDynamicActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog.show();
    }

    private void ifbAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrixDynamicActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_compsale, null);
        dialogBuilder.setView(dialogView);
        Button btnNow = (Button) dialogView.findViewById(R.id.btnNow);
        TextView tvResponse = (TextView) dialogView.findViewById(R.id.tvResponse);
        tvResponse.setText("Are You Sure,IFB Models Zero Display In Your Store");
        btnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog3.dismiss();
                postDisplaymatrix();


            }
        });

        Button btnLate = (Button) dialogView.findViewById(R.id.btnLate);
        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDisplaymatrix();
                alertDialog3.dismiss();
            }
        });
        alertDialog3 = dialogBuilder.create();
        alertDialog3.setCancelable(false);
        Window window = alertDialog3.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog3.show();
    }


    private void displayMatrixAlertForPreviousMonth() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrixDynamicActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_compsale, null);
        dialogBuilder.setView(dialogView);
        Button btnNow = (Button) dialogView.findViewById(R.id.btnNow);
        TextView tvResponse = (TextView) dialogView.findViewById(R.id.tvResponse);
        tvResponse.setText("Will you carry forward the previous month display data?");
        btnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                getReportListForPreviousMonth();

            }
        });

        Button btnLate = (Button) dialogView.findViewById(R.id.btnLate);
        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog.show();
    }

    private void getReportList() {

        pd.show();

        String surl = AppController.APIURL + "api/get_DisplayMatrixForUpdate?AEMEmployeeID=" + prefManager.getUserId() + "&FinancialYear=" + finalcialchecking + "&Month=" + month + "&SecurityCode=" + prefManager.getSecurityCode() + "&Opertaion=1";
        Log.d("inputtlreport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("responsetlreport", response);

                        // attendabceInfiList.clear();
                        pd.show();

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
                                    String CategoryName = obj.optString("CategoryName");
                                    String CompanyName = obj.optString("CompanyName");
                                    String Quantity = obj.optString("Quantity");
                                    String FinancialYear = obj.optString("FinancialYear");
                                    String Month = obj.optString("Month");
                                    String CategoryID = obj.optString("CategoryID");
                                    String CompetitorCompanyID = obj.optString("CompetitorCompanyID");
                                    String Btn_Flag = obj.optString("Btn_Flag");
                                    //Airconditioner
                                    if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etAirLG.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etAirSamSung.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etAirOthers.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etAirWhirlPool.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etAirGodrej.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000007")) {
                                        etAirPanaSonic.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000008")) {
                                        etAirVoltas.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000009")) {
                                        etAirDaikin.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000010")) {
                                        etAirLloyds.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etAirIFB.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000017")) {
                                        etAirOnida.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000018")) {
                                        etCarrier.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000019")) {
                                        etAirBlueStar.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000020")) {
                                        etAirOGenaral.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000021")) {
                                        etAirHaier.setText(Quantity);

                                    }

                                    //CLOTHS DRYER

                                    if (CategoryID.equals("IFBPC1000005") && CompetitorCompanyID.equals("IFBCC000003")) {
                                        etClothsBosch.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000005") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etClothsIFB.setText(Quantity);
                                    }

                                    //DISHWASHER

                                    if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etDishLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etDishSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000003")) {
                                        etDishBosch.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etDishOther.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etDishIFB.setText(Quantity);
                                    }

                                    //MICROVEN

                                    if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etMicroLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etMicroSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etMicroOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etMicroWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etMicroGodrej.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000007")) {
                                        etMicroPanasonic.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etMicroIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000017")) {
                                        etMicroOnida.setText(Quantity);
                                    }

                                    //KITCHEN APPLIANCE


                                    if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000003")) {
                                        etKABosch.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etKAOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000012")) {
                                        etKAKaff.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000013")) {
                                        etKAFaber.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000014")) {
                                        etKAElica.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etKAIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000022")) {
                                        etKASunFlame.setText(Quantity);
                                    }

                                    //WASHING FLU


                                    if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etFLULg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etFLUSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000003")) {
                                        etFLUBosch.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etFLUOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etFLUWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etFLUIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000024")) {
                                        etFLUBeko.setText(Quantity);
                                    }
                                    //WASHING TL

                                    if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etTLLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etTLSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000003")) {
                                        etTLBosch.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etTLOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etTLWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etTLGodrej.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000007")) {
                                        etTLPanasonic.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etTLIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000017")) {
                                        etTLOnida.setText(Quantity);
                                    }

                                    //WASHER DRYER

                                    if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etWasherDisherLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etWasherDisherSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etWasherDisherOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etWasherDisherWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etWasherDisherGodrej.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000007")) {
                                        etWasherDisherPanasonic.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etWasherDisherIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000017")) {
                                        etWasherDisherOnida.setText(Quantity);
                                    }

                                    //refregerator dc

                                    if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etREFRIGERATORLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etREFRIGERATORSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etREFRIGERATOROthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etREFRIGERATORWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etREFRIGERATORGodrej.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etREFRIGERATORIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000021")) {
                                        etREFRIGERATORHaier.setText(Quantity);
                                    }


                                    //refregerator ff

                                    if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etREFRIGERATORFFLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etREFRIGERATORFFSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etREFRIGERATORFFOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etREFRIGERATORFFWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etREFRIGERATORFFGodrej.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etREFRIGERATORFFIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000021")) {
                                        etREFRIGERATORFFHaier.setText(Quantity);
                                    }


                                }
                                getReportListForModel();


                            } else {


                                //Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DisplayMatrixDynamicActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();


                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };


        RequestQueue requestQueue = Volley.newRequestQueue(DisplayMatrixDynamicActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void getReportListForModel() {

        pd.show();


        String surl = AppController.APIURL + "api/get_DisplayMatrixForUpdate?AEMEmployeeID=" + prefManager.getUserId() + "&FinancialYear=" + finalcialchecking + "&Month=" + month + "&SecurityCode=" + prefManager.getSecurityCode() + "&Opertaion=2";
        Log.d("inputtlreport", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("responsetlreport", response);

                        airConditionerModel.clear();
                        clothsdryerModel.clear();
                        dishwasherModel.clear();
                        microOvenModel.clear();
                        kitchenModel.clear();
                        wmFluModel.clear();
                        wmTLModel.clear();
                        dryerModel.clear();
                        refModel.clear();
                        // attendabceInfiList.clear();
                        pd.dismiss();

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
                                    String CategoryID = obj.optString("CategoryID");
                                    String ModelID = obj.optString("ModelID");
                                    String ModelName = obj.optString("ModelName");
                                    //Airconditioner
                                    if (CategoryID.equals("IFBPC1000001")) {
                                        airConditionerModel.add(CategoryID + "-" + ModelID);
                                        //sendACModelList.add(ModelName);
                                    } else if (CategoryID.equals("IFBPC1000005")) {
                                        clothsdryerModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000007")) {
                                        dishwasherModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000011")) {
                                        microOvenModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000035")) {
                                        kitchenModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000021")) {
                                        wmFluModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000025")) {
                                        wmTLModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000039")) {
                                        dryerModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000013")) {
                                        refModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000040")) {
                                        refFFModel.add(CategoryID + "-" + ModelID);
                                    }


                                }

                                Set<String> set = new HashSet<String>(airConditionerModel);
                                airConditionerModel.clear();
                                airConditionerModel.addAll(set);

                                String airConditionerItem = String.valueOf(airConditionerModel);
                                String refreshairConditionerItem = airConditionerItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                airItem = refreshairConditionerItem;

                                //CTOTHS
                                Set<String> set1 = new HashSet<String>(clothsdryerModel);
                                clothsdryerModel.clear();
                                clothsdryerModel.addAll(set1);


                                String ClothsItem = String.valueOf(clothsdryerModel);
                                String refreshairClothsItem = ClothsItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                clothsItem = refreshairClothsItem;

                                //DISHWASHER

                                Set<String> set2 = new HashSet<String>(dishwasherModel);
                                dishwasherModel.clear();
                                dishwasherModel.addAll(set2);

                                String dishwasherItem = String.valueOf(dishwasherModel);
                                String refreshdishwasherItem = dishwasherItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                dishItem = refreshdishwasherItem;

                                //MICROOVEN

                                Set<String> set3 = new HashSet<String>(microOvenModel);
                                microOvenModel.clear();
                                microOvenModel.addAll(set3);

                                String microOvenItem = String.valueOf(microOvenModel);
                                String refreshmicroOvenItem = microOvenItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                microItem = refreshmicroOvenItem;

                                //Kitchen

                                Set<String> set4 = new HashSet<String>(kitchenModel);
                                kitchenModel.clear();
                                kitchenModel.addAll(set4);

                                String kaItem = String.valueOf(kitchenModel);
                                String refreshkaItem = kaItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                KAItem = refreshkaItem;

                                //WMFLU

                                Set<String> set5 = new HashSet<String>(wmFluModel);
                                wmFluModel.clear();
                                wmFluModel.addAll(set5);


                                String wmfluItem = String.valueOf(wmFluModel);
                                String refreshwmfluItem = wmfluItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                FLUItem = refreshwmfluItem;

                                //WMTL

                                Set<String> set6 = new HashSet<String>(wmTLModel);
                                wmTLModel.clear();
                                wmTLModel.addAll(set6);


                                String wmtlItem = String.valueOf(wmTLModel);
                                String refreshwmtlItem = wmtlItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                tlItem = refreshwmtlItem;

                                //Dryer

                                Set<String> set7 = new HashSet<String>(dryerModel);
                                dryerModel.clear();
                                dryerModel.addAll(set7);

                                String dryerItem = String.valueOf(dryerModel);
                                String refreshdryerItem = dryerItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                washerDyerItem = refreshdryerItem;

                                //refregrator dc

                                Set<String> set8 = new HashSet<String>(refModel);
                                refModel.clear();
                                refModel.addAll(set8);

                                String referegeritorItem = String.valueOf(refModel);
                                String refreshrefItem = referegeritorItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                refItem = refreshrefItem;

                                //refregrator FF

                                Set<String> set9 = new HashSet<String>(refFFModel);
                                refFFModel.clear();
                                refFFModel.addAll(set9);

                                String referegeritorFFItem = String.valueOf(refFFModel);
                                String refreshrefFFItem = referegeritorFFItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                refffItem = refreshrefFFItem;


                            } else {


                                //    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DisplayMatrixDynamicActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();


                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };


        RequestQueue requestQueue = Volley.newRequestQueue(DisplayMatrixDynamicActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void getReportListForPreviousMonth() {
        if (premonth.equals("January")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else if (premonth.equals("February")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else if (premonth.equals("March")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else {
            int futureyear = y + 1;
            finalcialchecking = year + "-" + futureyear;
        }

        pd.show();

        String surl = AppController.APIURL + "api/get_DisplayMatrixForUpdate?AEMEmployeeID=" + prefManager.getUserId() + "&FinancialYear=" + finalcialchecking + "&Month=" + premonth + "&SecurityCode=" + prefManager.getSecurityCode() + "&Opertaion=1";
        Log.d("inputtlreportpre", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("responsetlreport", response);

                        // attendabceInfiList.clear();
                        pd.show();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.e("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                previousMonthData = "true";
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String CategoryName = obj.optString("CategoryName");
                                    String CompanyName = obj.optString("CompanyName");
                                    String Quantity = obj.optString("Quantity");
                                    String FinancialYear = obj.optString("FinancialYear");
                                    String Month = obj.optString("Month");
                                    String CategoryID = obj.optString("CategoryID");
                                    String CompetitorCompanyID = obj.optString("CompetitorCompanyID");
                                    String Btn_Flag = obj.optString("Btn_Flag");
                                    //Airconditioner
                                    if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etAirLG.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etAirSamSung.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etAirOthers.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etAirWhirlPool.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etAirGodrej.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000007")) {
                                        etAirPanaSonic.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000008")) {
                                        etAirVoltas.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000009")) {
                                        etAirDaikin.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000010")) {
                                        etAirLloyds.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etAirIFB.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000017")) {
                                        etAirOnida.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000018")) {
                                        etCarrier.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000019")) {
                                        etAirBlueStar.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000020")) {
                                        etAirOGenaral.setText(Quantity);

                                    } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000021")) {
                                        etAirHaier.setText(Quantity);

                                    }

                                    //CLOTHS DRYER

                                    if (CategoryID.equals("IFBPC1000005") && CompetitorCompanyID.equals("IFBCC000003")) {
                                        etClothsBosch.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000005") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etClothsIFB.setText(Quantity);
                                    }

                                    //DISHWASHER

                                    if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etDishLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etDishSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000003")) {
                                        etDishBosch.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etDishOther.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etDishIFB.setText(Quantity);
                                    }

                                    //MICROVEN

                                    if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etMicroLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etMicroSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etMicroOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etMicroWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etMicroGodrej.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000007")) {
                                        etMicroPanasonic.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etMicroIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000017")) {
                                        etMicroOnida.setText(Quantity);
                                    }

                                    //KITCHEN APPLIANCE


                                    if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000003")) {
                                        etKABosch.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etKAOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000012")) {
                                        etKAKaff.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000013")) {
                                        etKAFaber.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000014")) {
                                        etKAElica.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etKAIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000022")) {
                                        etKASunFlame.setText(Quantity);
                                    }

                                    //WASHING FLU


                                    if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etFLULg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etFLUSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000003")) {
                                        etFLUBosch.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etFLUOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etFLUWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etFLUIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000024")) {
                                        etFLUBeko.setText(Quantity);
                                    }
                                    //WASHING TL

                                    if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etTLLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etTLSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000003")) {
                                        etTLBosch.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etTLOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etTLWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etTLGodrej.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000007")) {
                                        etTLPanasonic.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etTLIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000017")) {
                                        etTLOnida.setText(Quantity);
                                    }
                                    //dryer

                                    if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etWasherDisherLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etWasherDisherSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etWasherDisherOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etWasherDisherWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etWasherDisherGodrej.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000007")) {
                                        etWasherDisherPanasonic.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etWasherDisherIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000017")) {
                                        etWasherDisherOnida.setText(Quantity);
                                    }

                                    //refregerator DC
                                    if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etREFRIGERATORLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etREFRIGERATORSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etREFRIGERATOROthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etREFRIGERATORWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etREFRIGERATORGodrej.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etREFRIGERATORIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000021")) {
                                        etREFRIGERATORHaier.setText(Quantity);
                                    }

                                    //refregerator FF
                                    if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000001")) {
                                        etREFRIGERATORFFLg.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000002")) {
                                        etREFRIGERATORFFSamsung.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000004")) {
                                        etREFRIGERATORFFOthers.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000005")) {
                                        etREFRIGERATORFFWhirlPool.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000006")) {
                                        etREFRIGERATORFFGodrej.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000015")) {
                                        etREFRIGERATORFFIfb.setText(Quantity);
                                    } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000021")) {
                                        etREFRIGERATORFFHaier.setText(Quantity);
                                    }


                                    getReportListForModelPreviousMonth();


                                }


                            } else {

                                previousMonthData = "false";
                                //Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DisplayMatrixDynamicActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();


                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DisplayMatrixDynamicActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void getReportListForModelPreviousMonth() {
        if (premonth.equals("January")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else if (premonth.equals("February")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else if (premonth.equals("March")) {
            int futureyear = y - 1;
            finalcialchecking = futureyear + "-" + year;
        } else {
            int futureyear = y + 1;
            finalcialchecking = year + "-" + futureyear;
        }

        pd.show();
        String surl = AppController.APIURL + "api/get_DisplayMatrixForUpdate?AEMEmployeeID=" + prefManager.getUserId() + "&FinancialYear=" + finalcialchecking + "&Month=" + premonth + "&SecurityCode=" + prefManager.getSecurityCode() + "&Opertaion=2";
        Log.d("inputtlreportpre", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responsetlreport", response);
                        sendACModelList.clear();
                        airConditionerModel.clear();
                        clothsdryerModel.clear();
                        dishwasherModel.clear();
                        microOvenModel.clear();
                        kitchenModel.clear();
                        wmFluModel.clear();
                        wmTLModel.clear();
                        dryerModel.clear();
                        refModel.clear();
                        // attendabceInfiList.clear();
                        pd.dismiss();

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
                                    String CategoryID = obj.optString("CategoryID");
                                    String ModelID = obj.optString("ModelID");
                                    String ModelName = obj.optString("ModelName");
                                    //Airconditioner
                                    if (CategoryID.equals("IFBPC1000001")) {
                                        airConditionerModel.add(CategoryID + "-" + ModelID);
                                        sendACModelList.add(ModelName);
                                    } else if (CategoryID.equals("IFBPC1000005")) {
                                        clothsdryerModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000007")) {
                                        dishwasherModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000011")) {
                                        microOvenModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000035")) {
                                        kitchenModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000021")) {
                                        wmFluModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000025")) {
                                        wmTLModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000039")) {
                                        dryerModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000013")) {
                                        refModel.add(CategoryID + "-" + ModelID);
                                    } else if (CategoryID.equals("IFBPC1000040")) {
                                        refFFModel.add(CategoryID + "-" + ModelID);
                                    }


                                }

                                Set<String> set = new HashSet<String>(airConditionerModel);
                                airConditionerModel.clear();
                                airConditionerModel.addAll(set);

                                String airConditionerItem = String.valueOf(airConditionerModel);
                                String refreshairConditionerItem = airConditionerItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                prefManager.saveAirConditionerId(refreshairConditionerItem);

                                //CTOTHS
                                Set<String> set1 = new HashSet<String>(clothsdryerModel);
                                clothsdryerModel.clear();
                                clothsdryerModel.addAll(set1);


                                String ClothsItem = String.valueOf(clothsdryerModel);
                                String refreshairClothsItem = ClothsItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                prefManager.saveClothsDryerId(refreshairClothsItem);

                                //DISHWASHER

                                Set<String> set2 = new HashSet<String>(dishwasherModel);
                                dishwasherModel.clear();
                                dishwasherModel.addAll(set2);

                                String dishwasherItem = String.valueOf(dishwasherModel);
                                String refreshdishwasherItem = dishwasherItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                dishItem = refreshdishwasherItem;

                                //MICROOVEN

                                Set<String> set3 = new HashSet<String>(microOvenModel);
                                microOvenModel.clear();
                                microOvenModel.addAll(set3);

                                String microOvenItem = String.valueOf(microOvenModel);
                                String refreshmicroOvenItem = microOvenItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                microItem = refreshmicroOvenItem;

                                //Kitchen

                                Set<String> set4 = new HashSet<String>(kitchenModel);
                                kitchenModel.clear();
                                kitchenModel.addAll(set4);

                                String kaItem = String.valueOf(kitchenModel);
                                String refreshkaItem = kaItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                KAItem = refreshkaItem;

                                //WMFLU

                                Set<String> set5 = new HashSet<String>(wmFluModel);
                                wmFluModel.clear();
                                wmFluModel.addAll(set5);


                                String wmfluItem = String.valueOf(wmFluModel);
                                String refreshwmfluItem = wmfluItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                FLUItem = refreshwmfluItem;

                                //WMTL

                                Set<String> set6 = new HashSet<String>(wmTLModel);
                                wmTLModel.clear();
                                wmTLModel.addAll(set6);


                                String wmtlItem = String.valueOf(wmTLModel);
                                String refreshwmtlItem = wmtlItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                tlItem = refreshwmtlItem;

                                //Dryer

                                Set<String> set7 = new HashSet<String>(dryerModel);
                                dryerModel.clear();
                                dryerModel.addAll(set7);

                                String dryerItem = String.valueOf(dryerModel);
                                String refreshdryerItem = dryerItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                washerDyerItem = refreshdryerItem;

                                //refregerator DC
                                Set<String> set8 = new HashSet<String>(refModel);
                                refModel.clear();
                                refModel.addAll(set8);

                                String refregeratorItem = String.valueOf(refModel);
                                String refreshrefregeratorItemItem = refregeratorItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                refItem = refreshrefregeratorItemItem;


                                //refregerator FF
                                Set<String> set9 = new HashSet<String>(refFFModel);
                                refFFModel.clear();
                                refFFModel.addAll(set9);

                                String refregeratorFFItem = String.valueOf(refFFModel);
                                String refreshrefregeratorFFItemItem = refregeratorFFItem.replace("[", "").replace("]", "").replaceAll("\\s+", "");
                                refffItem = refreshrefregeratorFFItemItem;


                            } else {


                                //    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DisplayMatrixDynamicActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();


                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };


        RequestQueue requestQueue = Volley.newRequestQueue(DisplayMatrixDynamicActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private void cameraIntentforPic1() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Profile Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void cameraIntentforPic2() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Profile Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri1 = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri1);
        startActivityForResult(cameraIntent, CAMERA_REQUEST1);
    }

    private void cameraIntentforPic3() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Profile Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri2 = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri2);
        startActivityForResult(cameraIntent, CAMERA_REQUEST2);
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

                            BitmapFactory.Options o = new BitmapFactory.Options();
                            o.inSampleSize = 2;
                            Bitmap bm = cropToSquare(BitmapFactory.decodeFile(imageurl, o));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            imgPic1.setImageBitmap(bm);
                            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                            String contentType = "image/jpg";
                            String[] brkDown = imageurl.split("/");
                            String name = brkDown[5];
                            stringFile = name + "_" + encodedImage + "_" + contentType;
                            pic1Flag = 1;
                            cameraAlert.dismiss();


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

            case LongImageCameraActivity.LONG_IMAGE_RESULT_CODE:


                if (resultCode == RESULT_OK && requestCode == LongImageCameraActivity.LONG_IMAGE_RESULT_CODE) {
                    imageFileName = data.getStringExtra(LongImageCameraActivity.IMAGE_PATH_KEY);
                    Log.d("imageFileName", imageFileName);
                    Bitmap d = BitmapFactory.decodeFile(imageFileName);
                    int newHeight = (int) (d.getHeight() * (512.0 / d.getWidth()));
                    Bitmap putImage = Bitmap.createScaledBitmap(d, 512, newHeight, true);
                    imgPic1.setImageBitmap(putImage);
                    pictureFile = (File) data.getExtras().get("picture");


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    putImage.compress(Bitmap.CompressFormat.PNG, 10, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    cameraAlert.dismiss();
                    String contentType = "image/png";
                    String[] brkDown = imageFileName.split("/");
                    String name = brkDown[6];
                    stringFile = name + "_" + encodedImage + "_" + contentType;
                    pic1Flag = 1;


                }
                break;
            case CAMERA_REQUEST1:

                if (resultCode == Activity.RESULT_OK) {
                    try {
                        try {
                            String imageurl = /*"file://" +*/ getRealPathFromURI(imageUri1);
                            file1 = new File(imageurl);

                            BitmapFactory.Options o = new BitmapFactory.Options();
                            o.inSampleSize = 2;
                            Bitmap bm = cropToSquare(BitmapFactory.decodeFile(imageurl, o));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            imgPic2.setImageBitmap(bm);
                            encodedImage1 = Base64.encodeToString(b, Base64.DEFAULT);

                            String contentType = "image/jpg";
                            String[] brkDown = imageurl.split("/");
                            String name = brkDown[5];
                            stringFile1 = name + "_" + encodedImage1 + "_" + contentType;
                            pic2Flag = 1;
                            cameraAlert.dismiss();


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

            case LongImageCameraActivity.LONG_IMAGE_RESULT_CODE_PIC_1:


                if (resultCode == RESULT_OK && requestCode == LongImageCameraActivity.LONG_IMAGE_RESULT_CODE_PIC_1) {
                    imageFileName = data.getStringExtra(LongImageCameraActivity.IMAGE_PATH_KEY);
                    Log.d("imageFileName", imageFileName);
                    Bitmap d = BitmapFactory.decodeFile(imageFileName);
                    int newHeight = (int) (d.getHeight() * (512.0 / d.getWidth()));
                    Bitmap putImage = Bitmap.createScaledBitmap(d, 512, newHeight, true);
                    imgPic2.setImageBitmap(putImage);
                    pictureFile = (File) data.getExtras().get("picture");


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    putImage.compress(Bitmap.CompressFormat.PNG, 10, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    cameraAlert.dismiss();
                    String contentType = "image/png";
                    String[] brkDown = imageFileName.split("/");
                    String name = brkDown[6];
                    stringFile = name + "_" + encodedImage + "_" + contentType;
                    pic2Flag = 1;


                }
                break;
            case CAMERA_REQUEST2:

                if (resultCode == Activity.RESULT_OK) {
                    try {
                        try {
                            String imageurl = /*"file://" +*/ getRealPathFromURI(imageUri2);
                            file2 = new File(imageurl);

                            BitmapFactory.Options o = new BitmapFactory.Options();
                            o.inSampleSize = 2;
                            Bitmap bm = cropToSquare(BitmapFactory.decodeFile(imageurl, o));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            imgPic3.setImageBitmap(bm);
                            encodedImage2 = Base64.encodeToString(b, Base64.DEFAULT);

                            String contentType = "image/jpg";
                            String[] brkDown = imageurl.split("/");
                            String name = brkDown[5];
                            stringFile2 = name + "_" + encodedImage2 + "_" + contentType;
                            pic3Flag = 1;
                            cameraAlert.dismiss();


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

            case LongImageCameraActivity.LONG_IMAGE_RESULT_CODE_PIC_2:


                if (resultCode == RESULT_OK && requestCode == LongImageCameraActivity.LONG_IMAGE_RESULT_CODE_PIC_2) {
                    imageFileName = data.getStringExtra(LongImageCameraActivity.IMAGE_PATH_KEY);
                    Log.d("imageFileName", imageFileName);
                    Bitmap d = BitmapFactory.decodeFile(imageFileName);
                    int newHeight = (int) (d.getHeight() * (512.0 / d.getWidth()));
                    Bitmap putImage = Bitmap.createScaledBitmap(d, 512, newHeight, true);
                    imgPic3.setImageBitmap(putImage);
                    pictureFile = (File) data.getExtras().get("picture");


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    putImage.compress(Bitmap.CompressFormat.PNG, 10, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    cameraAlert.dismiss();
                    String contentType = "image/png";
                    String[] brkDown = imageFileName.split("/");
                    String name = brkDown[6];
                    stringFile = name + "_" + encodedImage + "_" + contentType;
                    pic3Flag = 1;


                }
                break;
            case ACREQUEST:

                try {

                    Log.d("acsize", String.valueOf(AppController.ifbac));
                    etAirIFB.setText(String.valueOf(AppController.ifbac));
                    etAirIFB.setEnabled(false);
                    airItem = AppController.acid;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case CLOTHSDRYERREQUEST:

                try {


                    etClothsIFB.setText(String.valueOf(AppController.ifbclotsdryersize));
                    etClothsIFB.setEnabled(false);
                    clothsItem = AppController.clothsdryedid;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case DISHREQUEST:

                try {


                    etDishIFB.setText(String.valueOf(AppController.ifbdishsize));
                    etDishIFB.setEnabled(false);
                    dishItem = AppController.dishid;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case WASHERDRYERREQUEST:

                try {


                    etWasherDisherIfb.setText(String.valueOf(AppController.ifbwashersize));
                    etWasherDisherIfb.setEnabled(false);
                    washerDyerItem = AppController.washerid;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case MICROOVENREQUEST:

                try {


                    etMicroIfb.setText(String.valueOf(AppController.ifbovensize));
                    etMicroIfb.setEnabled(false);
                    microItem = AppController.ovenid;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case KITCHENREQUEST:

                try {


                    etKAIfb.setText(String.valueOf(AppController.ifbkasize));
                    etKAIfb.setEnabled(false);
                    KAItem = AppController.kaid;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case FLUREQUEST:

                try {


                    etFLUIfb.setText(String.valueOf(AppController.ifbflusize));
                    etFLUIfb.setEnabled(false);
                    FLUItem = AppController.fluid;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case TLREQUEST:

                try {


                    etTLIfb.setText(String.valueOf(AppController.ifbtlsize));
                    etTLIfb.setEnabled(false);
                    tlItem = AppController.tlid;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case REFREQUEST:

                try {


                    etREFRIGERATORIfb.setText(String.valueOf(AppController.ifbrefsize));
                    etREFRIGERATORIfb.setEnabled(false);
                    refItem = AppController.refid;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case REFFFREQUEST:

                try {


                    etREFRIGERATORFFIfb.setText(String.valueOf(AppController.ifbrefffsize));
                    etREFRIGERATORFFIfb.setEnabled(false);
                    refffItem = AppController.refffid;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case ACLG:

                try {


                    etAirLG.setText(String.valueOf(AppController.air_lg));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case ACSAMSUNG:

                try {


                    etAirSamSung.setText(String.valueOf(AppController.air_samsung));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case ACDAIKEN:

                try {


                    etAirDaikin.setText(String.valueOf(AppController.air_daiken));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case ACCARRIER:

                try {


                    etCarrier.setText(String.valueOf(AppController.air_carrier));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case ACBLUESTAR:

                try {


                    etAirBlueStar.setText(String.valueOf(AppController.air_bluestar));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case ACVOLTAS:

                try {


                    etAirVoltas.setText(String.valueOf(AppController.air_voltas));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case ACONIDA:

                try {


                    etAirOnida.setText(String.valueOf(AppController.air_onida));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case ACPANASONIC:

                try {


                    etAirPanaSonic.setText(String.valueOf(AppController.air_panasonic));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ACWHIRLPOOL:

                try {


                    etAirWhirlPool.setText(String.valueOf(AppController.air_whirlpool));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case ACOGENERAL:

                try {


                    etAirOGenaral.setText(String.valueOf(AppController.air_ogeneral));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ACOGODREJ:

                try {


                    etAirGodrej.setText(String.valueOf(AppController.air_godrej));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ACHAIER:

                try {


                    etAirHaier.setText(String.valueOf(AppController.air_Haier));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ACLLYODS:

                try {


                    etAirLloyds.setText(String.valueOf(AppController.air_llyods));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case CLOTHSBOSCH:

                try {


                    etClothsBosch.setText(String.valueOf(AppController.cloths_bosch));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DISHBOSCH:

                try {


                    etDishBosch.setText(String.valueOf(AppController.dish_bosch));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DISHLG:

                try {


                    etDishLg.setText(String.valueOf(AppController.dish_lg));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DISHSAMSUNG:

                try {


                    etDishSamsung.setText(String.valueOf(AppController.dish_sam));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case MICROLG:

                try {


                    etMicroLg.setText(String.valueOf(AppController.micro_lg));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case MICROSAMSUNG:

                try {


                    etMicroSamsung.setText(String.valueOf(AppController.micro_sam));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case MICROWHIRLPOOL:

                try {


                    etMicroWhirlPool.setText(String.valueOf(AppController.micro_whirl));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MICROPANASONIC:

                try {


                    etMicroPanasonic.setText(String.valueOf(AppController.micro_pana));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MICROGODREJ:

                try {


                    etMicroGodrej.setText(String.valueOf(AppController.micro_godrej));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MICROONIDA:

                try {


                    etMicroOnida.setText(String.valueOf(AppController.micro_onida));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case KAFABER:

                try {


                    etKAFaber.setText(String.valueOf(AppController.ka_faber));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case KASUN:

                try {


                    etKASunFlame.setText(String.valueOf(AppController.ka_sun));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case KAELICA:

                try {


                    etKAElica.setText(String.valueOf(AppController.ka_elica));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case KAKAFF:

                try {


                    etKAKaff.setText(String.valueOf(AppController.ka_kaff));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case KABOSCH:

                try {


                    etKABosch.setText(String.valueOf(AppController.ka_bosch));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case WMFLULG:

                try {


                    etFLULg.setText(String.valueOf(AppController.wmflu_lg));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case WMFLUSAM:

                try {


                    etFLUSamsung.setText(String.valueOf(AppController.wmflu_sam));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case WMFLUBOSCH:

                try {


                    etFLUBosch.setText(String.valueOf(AppController.wmflu_bosch));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case WMFLUWHIRLPOOL:

                try {


                    etFLUWhirlPool.setText(String.valueOf(AppController.wmflu_whirlpool));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case WMFLUWHIRLBEKO:

                try {


                    etFLUBeko.setText(String.valueOf(AppController.wmflu_beko));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TLLG:

                try {


                    etTLLg.setText(String.valueOf(AppController.tl_lg));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TLSAM:

                try {


                    etTLSamsung.setText(String.valueOf(AppController.tl_sam));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TLBOSCH:

                try {


                    etTLBosch.setText(String.valueOf(AppController.tl_bosch));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TLWHIRLPOOL:

                try {


                    etTLWhirlPool.setText(String.valueOf(AppController.tl_whirlpool));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TLPANSONIC:

                try {


                    etTLPanasonic.setText(String.valueOf(AppController.tl_pana));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case TLGODREJ:

                try {


                    etTLGodrej.setText(String.valueOf(AppController.tl_godrej));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TLONIDA:

                try {


                    etTLOnida.setText(String.valueOf(AppController.tl_onida));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DRYERLG:

                try {


                    etWasherDisherLg.setText(String.valueOf(AppController.dryer_lg));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DRYERSAM:

                try {


                    etWasherDisherSamsung.setText(String.valueOf(AppController.dryer_sam));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DRYERWHIRLPOOL:

                try {


                    etWasherDisherWhirlPool.setText(String.valueOf(AppController.dryer_whirlpool));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DRYERPANASONIC:

                try {


                    etWasherDisherPanasonic.setText(String.valueOf(AppController.dryer_pansonic));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DRYERGODREJ:

                try {


                    etWasherDisherGodrej.setText(String.valueOf(AppController.dryer_godrej));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DRYERONIDA:

                try {


                    etWasherDisherOnida.setText(String.valueOf(AppController.dryer_onida));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DCSAM:

                try {


                    etREFRIGERATORSamsung.setText(String.valueOf(AppController.dc_sam));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DCLG:

                try {


                    etREFRIGERATORLg.setText(String.valueOf(AppController.dc_lg));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DCWHIRLPOOL:

                try {


                    etREFRIGERATORWhirlPool.setText(String.valueOf(AppController.dc_whirlpool));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DCHAIER:

                try {


                    etREFRIGERATORHaier.setText(String.valueOf(AppController.dc_haier));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DCGodrej:

                try {


                    etREFRIGERATORGodrej.setText(String.valueOf(AppController.dc_godrej));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case FFGodrej:

                try {


                    etREFRIGERATORFFGodrej.setText(String.valueOf(AppController.ff_godrej));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case FFHAIER:

                try {


                    etREFRIGERATORFFHaier.setText(String.valueOf(AppController.ff_haier));


                } catch (Exception e) {
                    e.printStackTrace();
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


    private void postImage() {

        final ProgressDialog pd = new ProgressDialog(DisplayMatrixDynamicActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        Log.d("shubusen", "1");

        AndroidNetworking.upload(AppController.APIURL + "api/post_DisplayMatrixWithProductCopy")
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("CategoryID1", "IFBPC1000024")
                .addMultipartParameter("ProductCopy1", stringFile)
                .addMultipartParameter("CategoryID2", "IFBPC1000024")
                .addMultipartParameter("ProductCopy2", stringFile1)
                .addMultipartParameter("CategoryID3", "IFBPC1000001")
                .addMultipartParameter("ProductCopy3", stringFile2)
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
                            Toast.makeText(DisplayMatrixDynamicActivity.this, responseText, Toast.LENGTH_LONG).show();

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

    private void acChecking() {
        if (acFlag.equals("1")) {
            if (pic3Flag == 1) {
                postImage();
            } else {
                Toast.makeText(DisplayMatrixDynamicActivity.this, "Please Upload AC Products Image", Toast.LENGTH_LONG).show();
            }
        } else {
            postImage();

        }

    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

    private void cameraDialogForPic1() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrixDynamicActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.camera_dialog, null);
        dialogBuilder.setView(dialogView);
        TextView tvCamera = (TextView) dialogView.findViewById(R.id.tvCamera);
        tvCamera.setText("Default Camera");
        LinearLayout llCamera = (LinearLayout) dialogView.findViewById(R.id.llCamera);
        LinearLayout llGallery = (LinearLayout) dialogView.findViewById(R.id.llGallery);
        LinearLayout llCustomCamera = (LinearLayout) dialogView.findViewById(R.id.llCustomCamera);
        llCustomCamera.setVisibility(View.VISIBLE);
        llGallery.setVisibility(View.GONE);
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntentforPic1();
            }
        });

        llCustomCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LongImageCameraActivity.launch(DisplayMatrixDynamicActivity.this);

            }
        });


        cameraAlert = dialogBuilder.create();
        cameraAlert.setCancelable(true);
        Window window = cameraAlert.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        cameraAlert.show();
    }

    private void cameraDialogForPic2() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrixDynamicActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.camera_dialog, null);
        dialogBuilder.setView(dialogView);
        TextView tvCamera = (TextView) dialogView.findViewById(R.id.tvCamera);
        tvCamera.setText("Default Camera");
        LinearLayout llCamera = (LinearLayout) dialogView.findViewById(R.id.llCamera);
        LinearLayout llGallery = (LinearLayout) dialogView.findViewById(R.id.llGallery);
        LinearLayout llCustomCamera = (LinearLayout) dialogView.findViewById(R.id.llCustomCamera);
        llCustomCamera.setVisibility(View.VISIBLE);
        llGallery.setVisibility(View.GONE);
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntentforPic2();
            }
        });

        llCustomCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LongImageCameraActivity.launchPic(DisplayMatrixDynamicActivity.this);

            }
        });


        cameraAlert = dialogBuilder.create();
        cameraAlert.setCancelable(true);
        Window window = cameraAlert.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        cameraAlert.show();
    }

    private void cameraDialogForPic3() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrixDynamicActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.camera_dialog, null);
        dialogBuilder.setView(dialogView);
        TextView tvCamera = (TextView) dialogView.findViewById(R.id.tvCamera);
        tvCamera.setText("Default Camera");
        LinearLayout llCamera = (LinearLayout) dialogView.findViewById(R.id.llCamera);
        LinearLayout llGallery = (LinearLayout) dialogView.findViewById(R.id.llGallery);
        LinearLayout llCustomCamera = (LinearLayout) dialogView.findViewById(R.id.llCustomCamera);
        llCustomCamera.setVisibility(View.VISIBLE);
        llGallery.setVisibility(View.GONE);
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntentforPic3();
            }
        });

        llCustomCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LongImageCameraActivity.launchPic1(DisplayMatrixDynamicActivity.this);

            }
        });


        cameraAlert = dialogBuilder.create();
        cameraAlert.setCancelable(true);
        Window window = cameraAlert.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        cameraAlert.show();
    }


    private void getAddButton() {


        pd.show();
        String surl = AppController.APIURL + "api/get_DisplayMatrixForUpdatev1?AEMEmployeeID=" + prefManager.getUserId() + "&FinancialYear=" + finalcialchecking + "&Month=" + premonth + "&SecurityCode=" + prefManager.getSecurityCode() + "&Opertaion=2";
        Log.d("inputtlreportpre", surl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, surl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // attendabceInfiList.clear();
                        pd.dismiss();

                        try {
                            JSONObject job1 = new JSONObject(response);
                            Log.d("response12", "@@@@@@" + job1);
                            String responseText = job1.optString("responseText");

                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                //          Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();
                                JSONArray responseData = job1.optJSONArray("responseData");
                                for (int i = 0; i < responseData.length(); i++) {
                                    JSONObject obj = responseData.getJSONObject(i);
                                    String CategoryName = obj.optString("CategoryName");
                                    String CompanyName = obj.optString("CompanyName");
                                    String Quantity = obj.optString("Quantity");
                                    String FinancialYear = obj.optString("FinancialYear");
                                    String Month = obj.optString("Month");
                                    String CategoryID = obj.optString("CategoryID");
                                    String CompetitorCompanyID = obj.optString("CompetitorCompanyID");
                                    String Btn_Flag = obj.optString("Btn_Flag");
                                    //Airconditioner

                                    if (Btn_Flag.equalsIgnoreCase("True")) {
                                        if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000001")) {


                                            tvAirLgAdd.setVisibility(View.VISIBLE);
                                            etAirLG.setEnabled(false);
                                            Log.d("DisplayMatrix", "1");


                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000002")) {


                                            tvAirSamsungAdd.setVisibility(View.VISIBLE);
                                            etAirSamSung.setEnabled(false);
                                            Log.d("DisplayMatrix", "2");

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000004")) {
                                            etAirOthers.setText(Quantity);

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000005")) {


                                            tvAirWhirlpoolAdd.setVisibility(View.VISIBLE);
                                            etAirWhirlPool.setEnabled(false);
                                            Log.d("DisplayMatrix", "3");

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000006")) {


                                            tvAirGodrejAdd.setVisibility(View.VISIBLE);
                                            etAirGodrej.setEnabled(false);
                                            Log.d("DisplayMatrix", "4");

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000007")) {


                                            tvAirPanasonicAdd.setVisibility(View.VISIBLE);
                                            etAirPanaSonic.setEnabled(false);
                                            Log.d("DisplayMatrix", "5");

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000008")) {


                                            tvAirVoltasAdd.setVisibility(View.VISIBLE);
                                            etAirVoltas.setEnabled(false);
                                            Log.d("DisplayMatrix", "6");

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000009")) {


                                            tvAirDaikenAdd.setVisibility(View.VISIBLE);
                                            etAirDaikin.setEnabled(false);
                                            Log.d("DisplayMatrix", "7");

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000010")) {


                                            tvAirLloydsAdd.setVisibility(View.VISIBLE);
                                            etAirLloyds.setEnabled(false);
                                            Log.d("DisplayMatrix", "8");

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000015")) {
                                            etAirIFB.setText(Quantity);

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000017")) {


                                            tvAirOnidaAdd.setVisibility(View.VISIBLE);
                                            etAirOnida.setEnabled(false);
                                            Log.d("DisplayMatrix", "9");

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000018")) {


                                            tvAirCarrierAdd.setVisibility(View.VISIBLE);
                                            etCarrier.setEnabled(false);
                                            Log.d("DisplayMatrix", "10");

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000019")) {


                                            tvAirBluestarAdd.setVisibility(View.VISIBLE);
                                            etAirBlueStar.setEnabled(false);
                                            Log.d("DisplayMatrix", "11");

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000020")) {


                                            tvAiroGeneralAdd.setVisibility(View.VISIBLE);
                                            etAirOGenaral.setEnabled(false);
                                            Log.d("DisplayMatrix", "12");

                                        } else if (CategoryID.equals("IFBPC1000001") && CompetitorCompanyID.equals("IFBCC000021")) {


                                            tvAirHaierAdd.setVisibility(View.VISIBLE);
                                            etAirHaier.setEnabled(false);
                                            Log.d("DisplayMatrix", "13");

                                        }

                                        //CLOTHS DRYER

                                        if (CategoryID.equals("IFBPC1000005") && CompetitorCompanyID.equals("IFBCC000003")) {


                                            tvClothsBoschAdd.setVisibility(View.VISIBLE);
                                            etClothsBosch.setEnabled(false);
                                            Log.d("DisplayMatrix", "14");

                                        } else if (CategoryID.equals("IFBPC1000005") && CompetitorCompanyID.equals("IFBCC000015")) {

                                        }

                                        //DISHWASHER

                                        if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000001")) {


                                            tvDishLgAdd.setVisibility(View.VISIBLE);
                                            etDishLg.setEnabled(false);
                                            Log.d("DisplayMatrix", "15");

                                        } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000002")) {


                                            tvDishSamsungAdd.setVisibility(View.VISIBLE);
                                            etDishSamsung.setEnabled(false);
                                            Log.d("DisplayMatrix", "16");

                                        } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000003")) {


                                            tvDishBoschAdd.setVisibility(View.VISIBLE);
                                            etDishBosch.setEnabled(false);
                                            Log.d("DisplayMatrix", "17");

                                        } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000004")) {

                                        } else if (CategoryID.equals("IFBPC1000007") && CompetitorCompanyID.equals("IFBCC000015")) {

                                        }

                                        //MICROVEN

                                        if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000001")) {


                                            tvMicroLgAdd.setVisibility(View.VISIBLE);
                                            etMicroLg.setEnabled(false);
                                            Log.d("DisplayMatrix", "18");


                                        } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000002")) {


                                            tvMicroSamsungAdd.setVisibility(View.VISIBLE);
                                            etMicroSamsung.setEnabled(false);
                                            Log.d("DisplayMatrix", "19");

                                        } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000004")) {


                                        } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000005")) {


                                            tvMicroWhirlpoolAdd.setVisibility(View.VISIBLE);
                                            etMicroWhirlPool.setEnabled(false);
                                            Log.d("DisplayMatrix", "20");

                                        } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000006")) {


                                            tvMicroGodrejAdd.setVisibility(View.VISIBLE);
                                            etMicroGodrej.setEnabled(false);
                                            Log.d("DisplayMatrix", "21");

                                        } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000007")) {


                                            tvMicroPanasonicAdd.setVisibility(View.VISIBLE);
                                            etMicroPanasonic.setEnabled(false);
                                            Log.d("DisplayMatrix", "22");

                                        } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000015")) {

                                        } else if (CategoryID.equals("IFBPC1000011") && CompetitorCompanyID.equals("IFBCC000017")) {


                                            tvMicroOnidaAdd.setVisibility(View.VISIBLE);
                                            etMicroOnida.setEnabled(false);
                                            Log.d("DisplayMatrix", "23");

                                        }

                                        //KITCHEN APPLIANCE


                                        if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000003")) {


                                            tvKABoschAdd.setVisibility(View.VISIBLE);
                                            etKABosch.setEnabled(false);
                                            Log.d("DisplayMatrix", "24");

                                        } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000004")) {

                                        } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000012")) {


                                            tvKAKaffAdd.setVisibility(View.VISIBLE);
                                            etKAKaff.setEnabled(false);
                                            Log.d("DisplayMatrix", "25");

                                        } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000013")) {


                                            tvKAFaberAdd.setVisibility(View.VISIBLE);
                                            etKAFaber.setEnabled(false);
                                            Log.d("DisplayMatrix", "26");

                                        } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000014")) {


                                            tvKAElicaAdd.setVisibility(View.VISIBLE);
                                            etKAElica.setEnabled(false);
                                            Log.d("DisplayMatrix", "27");

                                        } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000015")) {

                                        } else if (CategoryID.equals("IFBPC1000035") && CompetitorCompanyID.equals("IFBCC000022")) {


                                            tvKASunflameAdd.setVisibility(View.VISIBLE);
                                            etKASunFlame.setEnabled(false);
                                            Log.d("DisplayMatrix", "28");

                                        }

                                        //WASHING FLU


                                        if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000001")) {


                                            tvFLULgAdd.setVisibility(View.VISIBLE);
                                            etFLULg.setEnabled(false);
                                            Log.d("DisplayMatrix", "29");

                                        } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000002")) {


                                            tvFLUSamsungAdd.setVisibility(View.VISIBLE);
                                            etFLUSamsung.setEnabled(false);
                                            Log.d("DisplayMatrix", "30");

                                        } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000003")) {


                                            tvFLUBoschAdd.setVisibility(View.VISIBLE);
                                            etFLUBosch.setEnabled(false);
                                            Log.d("DisplayMatrix", "31");


                                        } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000004")) {

                                        } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000005")) {


                                            tvFLUWhirlpoolAdd.setVisibility(View.VISIBLE);
                                            etFLUWhirlPool.setEnabled(false);
                                            Log.d("DisplayMatrix", "32");

                                        } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000015")) {

                                        } else if (CategoryID.equals("IFBPC1000021") && CompetitorCompanyID.equals("IFBCC000024")) {


                                            tvFLUBekoAdd.setVisibility(View.VISIBLE);
                                            etFLUBeko.setEnabled(false);
                                            Log.d("DisplayMatrix", "33");

                                        }
                                        //WASHING TL

                                        if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000001")) {


                                            tvTLLgAdd.setVisibility(View.VISIBLE);
                                            etTLLg.setEnabled(false);
                                            Log.d("DisplayMatrix", "34");

                                        } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000002")) {


                                            tvTLSamsungAdd.setVisibility(View.VISIBLE);
                                            etTLSamsung.setEnabled(false);
                                            Log.d("DisplayMatrix", "35");

                                        } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000003")) {


                                            tvTLBoschAdd.setVisibility(View.VISIBLE);
                                            etTLBosch.setEnabled(false);
                                            Log.d("DisplayMatrix", "36");

                                        } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000004")) {

                                        } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000005")) {


                                            tvTLWhirlpoolAdd.setVisibility(View.VISIBLE);
                                            etTLWhirlPool.setEnabled(false);
                                            Log.d("DisplayMatrix", "37");

                                        } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000006")) {


                                            tvTLGodrejAdd.setVisibility(View.VISIBLE);
                                            etTLGodrej.setEnabled(false);
                                            Log.d("DisplayMatrix", "38");

                                        } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000007")) {


                                            tvTLPanasonicAdd.setVisibility(View.VISIBLE);
                                            etTLPanasonic.setEnabled(false);
                                            Log.d("DisplayMatrix", "39");

                                        } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000015")) {

                                        } else if (CategoryID.equals("IFBPC1000025") && CompetitorCompanyID.equals("IFBCC000017")) {


                                            tvTLOnidaAdd.setVisibility(View.VISIBLE);
                                            etTLOnida.setEnabled(false);
                                            Log.d("DisplayMatrix", "40");

                                        }
                                        //dryer

                                        if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000001")) {


                                            tvWasherDisherLgAdd.setVisibility(View.VISIBLE);
                                            etWasherDisherLg.setEnabled(false);
                                            Log.d("DisplayMatrix", "41");

                                        } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000002")) {


                                            tvWasherDisherSamsungAdd.setVisibility(View.VISIBLE);
                                            etWasherDisherSamsung.setEnabled(false);
                                            Log.d("DisplayMatrix", "42");

                                        } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000004")) {

                                        } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000005")) {


                                            tvWasherDisherWhirlpoolAdd.setVisibility(View.VISIBLE);
                                            etWasherDisherWhirlPool.setEnabled(false);
                                            Log.d("DisplayMatrix", "43");

                                        } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000006")) {


                                            tvWasherDisherGodrejAdd.setVisibility(View.VISIBLE);
                                            etWasherDisherGodrej.setEnabled(false);
                                            Log.d("DisplayMatrix", "44");

                                        } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000007")) {


                                            tvWasherDisherPanasonicAdd.setVisibility(View.VISIBLE);
                                            etWasherDisherPanasonic.setEnabled(false);
                                            Log.d("DisplayMatrix", "45");

                                        } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000015")) {

                                        } else if (CategoryID.equals("IFBPC1000039") && CompetitorCompanyID.equals("IFBCC000017")) {


                                            tvWasherDisherOnidaAdd.setVisibility(View.VISIBLE);
                                            etWasherDisherOnida.setEnabled(false);
                                            Log.d("DisplayMatrix", "46");

                                        }

                                        //refregerator DC
                                        if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000001")) {


                                            tvREFRIGERATORLgAdd.setVisibility(View.VISIBLE);
                                            etREFRIGERATORLg.setEnabled(false);
                                            Log.d("DisplayMatrix", "47");

                                        } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000002")) {


                                            tvREFRIGERATORSamsungAdd.setVisibility(View.VISIBLE);
                                            etREFRIGERATORSamsung.setEnabled(false);
                                            Log.d("DisplayMatrix", "48");

                                        } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000004")) {


                                        } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000005")) {


                                            tvREFRIGERATORWhirlpoolAdd.setVisibility(View.VISIBLE);
                                            etREFRIGERATORWhirlPool.setEnabled(false);
                                            Log.d("DisplayMatrix", "49");

                                        } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000006")) {


                                            tvREFRIGERATORGodrejAdd.setVisibility(View.VISIBLE);
                                            etREFRIGERATORGodrej.setEnabled(false);
                                            Log.d("DisplayMatrix", "50");

                                        } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000015")) {


                                        } else if (CategoryID.equals("IFBPC1000013") && CompetitorCompanyID.equals("IFBCC000021")) {


                                            tvREFRIGERATORHaierAdd.setVisibility(View.VISIBLE);
                                            etREFRIGERATORHaier.setEnabled(false);
                                            Log.d("DisplayMatrix", "51");

                                        }

                                        //refregerator FF
                                        if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000001")) {


                                            tvREFRIGERATORFFLgAdd.setVisibility(View.VISIBLE);
                                            etREFRIGERATORFFLg.setEnabled(false);
                                            Log.d("DisplayMatrix", "52");

                                        } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000002")) {


                                            tvREFRIGERATORFFSamsungAdd.setVisibility(View.VISIBLE);
                                            etREFRIGERATORFFSamsung.setEnabled(false);
                                            Log.d("DisplayMatrix", "53");

                                        } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000004")) {

                                        } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000005")) {


                                            tvREFRIGERATORFFWhirlpoolAdd.setVisibility(View.VISIBLE);
                                            etREFRIGERATORFFWhirlPool.setEnabled(false);
                                            Log.d("DisplayMatrix", "54");


                                        } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000006")) {


                                            tvREFRIGERATORFFGodrejAdd.setVisibility(View.VISIBLE);
                                            etREFRIGERATORFFGodrej.setEnabled(false);
                                            Log.d("DisplayMatrix", "55");

                                        } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000015")) {

                                        } else if (CategoryID.equals("IFBPC1000040") && CompetitorCompanyID.equals("IFBCC000021")) {


                                            tvREFRIGERATORFFHaierAdd.setVisibility(View.VISIBLE);
                                            etREFRIGERATORFFHaier.setEnabled(false);
                                            Log.d("DisplayMatrix", "56");

                                        }
                                    }


                                }


                            } else {


                                //    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DisplayMatrixDynamicActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();


                //Toast.makeText(SupAttenReportActivity.this, "volly 2"+error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };


        RequestQueue requestQueue = Volley.newRequestQueue(DisplayMatrixDynamicActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


}
