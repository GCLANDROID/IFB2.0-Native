package io.cordova.ifb.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

public class DisplayMatrixActivity extends AppCompatActivity {
    TextView tvDate;
    LinearLayout llDate;
    TextView tvAdd1, tvAdd2, tvAdd3, tvAdd4, tvAdd5, tvAdd6, tvAdd7, tvAdd8, tvAdd9, tvAdd10, tvAdd11;
    ImageView imgBack;
    String model = "0";
    PrefManager prefManager;
    EditText etAirDaikin, etAirIfb, etAirLG, etAirLloyds, etAirOther, etAirSSG, etAirVoltas, etAirWPL;
    EditText etHobElica, etHobFaber, etHobIfb, etHobKAFF, etHobOther;
    EditText etOvenIFB;
    EditText etClothsBosch, etClothsIfb, etClothsLg, etClothsOthers, etClothsSimen;
    EditText etCookerElica, etCookerFaber, etCookerIFB, etCookerKaff, etCookerOthers;
    EditText etDishBosch, etDishIfb, etDishOthers, etDishSimens;
    EditText etMicroGodrej, etMicroIfb, etMicroLg, etMicroOthers, etMicroPanasonic, etMicroSSG, etMicropWPL;
    EditText etRefIFB, etWaterIfb;
    EditText etWMFLUBosch, etWMFLUIfb, etWMFLULg, etWMFLUOthers, etWMFLUSSG;
    EditText etWMTLGodrej, etWMTLIfb, etWMTLLG, etWMTLOthers, etWMTLPanasonic, etWMTLSSG, etWMTLWML;
    String modelId = "";
    //Air conditioner
    String airDaikin = "IFBPC1000001" + "-" + "IFBCC000009" + "#" + "0";
    String airIfb = "IFBPC1000001" + "-" + "IFBCC000015" + "#" + "0";
    String airLg = "IFBPC1000001" + "-" + "IFBCC000001" + "#" + "0";
    String airLloyds = "IFBPC1000001" + "-" + "IFBCC000001" + "#" + "0";
    String airOthers = "IFBPC1000001" + "-" + "IFBCC000004" + "#" + "0";
    String airssg = "IFBPC1000001" + "-" + "IFBCC000002" + "#" + "0";
    String airVoltas = "IFBPC1000001" + "-" + "IFBCC000008" + "#" + "0";
    String airWPL = "IFBPC1000001" + "-" + "IFBCC000005" + "#" + "0";

    //Bulit in Hobs
    String hobsElica = "IFBPC1000002" + "-" + "IFBCC000014" + "#" + "0";
    String hobFaber = "IFBPC1000002" + "-" + "IFBCC000013" + "#" + "0";
    String hobIFB = "IFBPC1000002" + "-" + "IFBCC000015" + "#" + "0";
    String hobKaff = "IFBPC1000002" + "-" + "IFBCC000012" + "#" + "0";
    String hobOther = "IFBPC1000002" + "-" + "IFBCC000004" + "#" + "0";

    //Built in Oven
    String ovenIFB = "IFBPC1000004" + "-" + "IFBCC000015" + "#" + "0";

    //Cloths Dryer
    String clothsBosch = "IFBPC1000005" + "-" + "IFBCC000003" + "#" + "0";
    String clothsIFB = "IFBPC1000005" + "-" + "IFBCC000015" + "#" + "0";
    String clothLG = "IFBPC1000005" + "-" + "IFBCC000001" + "#" + "0";
    String clothOther = "IFBPC1000005" + "-" + "IFBCC000004" + "#" + "0";
    String clothSimens = "IFBPC1000005" + "-" + "IFBCC000011" + "#" + "0";

    //cooker
    String cookerElica = "IFBPC1000006" + "-" + "IFBCC000014" + "#" + "0";
    String cookerFaber = "IFBPC1000006" + "-" + "IFBCC000013" + "#" + "0";
    String cookerIFB = "IFBPC1000006" + "-" + "IFBCC000015" + "#" + "0";
    String cookerKaff = "IFBPC1000006" + "-" + "IFBCC000012" + "#" + "0";
    String cookerOther = "IFBPC1000006" + "-" + "IFBCC000004" + "#" + "0";

    //DishWasher
    String dishBosch = "IFBPC1000007" + "-" + "IFBCC000003" + "#" + "0";
    String dishIFB = "IFBPC1000007" + "-" + "IFBCC000015" + "#" + "0";
    String dishOther = "IFBPC1000007" + "-" + "IFBCC000004" + "#" + "0";
    String dishSimen = "IFBPC1000007" + "-" + "IFBCC000011" + "#" + "0";

    //Micro Oven
    String microGodrej = "IFBPC1000011" + "-" + "IFBCC000006" + "#" + "0";
    String microIFB = "IFBPC1000011" + "-" + "IFBCC000015" + "#" + "0";
    String microLG = "IFBPC1000011" + "-" + "IFBCC000001" + "#" + "0";
    String microOTher = "IFBPC1000011" + "-" + "IFBCC000004" + "#" + "0";
    String microPanasonic = "IFBPC1000011" + "-" + "IFBCC000007" + "#" + "0";
    String microSSG = "IFBPC1000011" + "-" + "IFBCC000002" + "#" + "0";
    String microWPL = "IFBPC1000011" + "-" + "IFBCC000005" + "#" + "0";

    //Refrigerator

    String refIfb = "IFBPC1000013" + "-" + "IFBCC000015" + "#" + "0";

    //Water purifier

    String waterIFB = "IFBPC1000016" + "-" + "IFBCC000015" + "#" + "0";

    //Washing maching flu

    String WMFLUBosch = "IFBPC1000021" + "-" + "IFBCC000003" + "#" + "0";
    String WMFLUIFB = "IFBPC1000021" + "-" + "IFBCC000015" + "#" + "0";
    String WMFLULG = "IFBPC1000021" + "-" + "IFBCC000001" + "#" + "0";
    String WMFLUOthers = "IFBPC1000021" + "-" + "IFBCC000004" + "#" + "0";
    String WMFLUSSG = "IFBPC1000021" + "-" + "IFBCC000002" + "#" + "0";

    //Washing machine TL

    String WMTLGodrej = "IFBPC1000025" + "-" + "IFBCC000006" + "#" + "0";
    String WMTLIFB = "IFBPC1000025" + "-" + "IFBCC000015" + "#" + "0";
    String WMTLLG = "IFBPC1000025" + "-" + "IFBCC000001" + "#" + "0";
    String WMTLOthers = "IFBPC1000025" + "-" + "IFBCC000004" + "#" + "0";
    String WMTLPanasonic = "IFBPC1000025" + "-" + "IFBCC000007" + "#" + "0";
    String WMTLSSG = "IFBPC1000025" + "-" + "IFBCC000002" + "#" + "0";
    String WMTLWPL = "IFBPC1000025" + "-" + "IFBCC000005" + "#" + "0";
    AlertDialog alerDialog1;
    String responseText;
    TextView tvSave;
    private static final String SERVER_PATH =  AppController.APIURL+"api/";
    private PostDisplayMatrixService uploadService;
    ProgressDialog progressDialog;
    String salesdate;
    String msg = "";
    String securitycode, userid;
    String formattedDate;
    String category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
    String airifb = "0";
    String hobifb = "0";
    String ovenifb = "0";
    String clothsifb = "0";
    String cookerifb = "0";
    String dishifb = "0";
    String microifb = "0";
    String refifb = "0";
    String waterifb = "0";
    String wmfluifb = "0";
    String wmtlifb = "0";
    String refregeratorifb = "0";
    ImageView imgHome;
    String monthname;
    String month, showYear, showMonth, year, premonth, finalcialchecking;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_matrix);
        initialize();
        displayMatrixChecking();
        onClick();


    }

    private void initialize() {
        prefManager = new PrefManager(DisplayMatrixActivity.this);
        tvDate = (TextView) findViewById(R.id.tvDate);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

        llDate = (LinearLayout) findViewById(R.id.llDate);

        tvAdd1 = (TextView) findViewById(R.id.tvAdd1);
        tvAdd2 = (TextView) findViewById(R.id.tvAdd2);
        tvAdd3 = (TextView) findViewById(R.id.tvAdd3);
        tvAdd4 = (TextView) findViewById(R.id.tvAdd4);
        tvAdd5 = (TextView) findViewById(R.id.tvAdd5);
        tvAdd6 = (TextView) findViewById(R.id.tvAdd6);
        tvAdd7 = (TextView) findViewById(R.id.tvAdd7);
        tvAdd8 = (TextView) findViewById(R.id.tvAdd8);
        tvAdd9 = (TextView) findViewById(R.id.tvAdd9);
        tvAdd10 = (TextView) findViewById(R.id.tvAdd10);
        tvAdd11 = (TextView) findViewById(R.id.tvAdd11);

        imgBack = (ImageView) findViewById(R.id.imgBack);
        if (!prefManager.getAirConditionerId().equals("")) {
            airifb = prefManager.getAirConditionerId();

        } else {
            airifb = "0";

        }

        Log.d("airifbid", airifb);
        if (!prefManager.getKAItemId().equals("")) {

            hobifb = prefManager.getKAItemId();
        } else {
            hobifb = "0";
        }
        if (!prefManager.getBuiltInOvenId().equals("")) {
            ovenifb = prefManager.getBuiltInOvenId();
        } else {
            ovenifb = "0";
        }
        if (!prefManager.getClothsDryerId().equals("")) {
            clothsifb = prefManager.getClothsDryerId();
            Log.d("clotsifb", clothsifb);
        } else {
            clothsifb = "0";
        }
        if (!prefManager.getCookerHoodsId().equals("")) {
            cookerifb = prefManager.getCookerHoodsId();
        } else {
            cookerifb = "0";
        }
        if (!prefManager.getDishWasherId().equals("")) {
            dishifb = prefManager.getDishWasherId();
        } else {
            dishifb = "0";
        }
        if (!prefManager.getMicroOvenId().equals("")) {
            microifb = prefManager.getMicroOvenId();
        } else {
            microifb = "0";
        }
        if (!prefManager.getRefrigeratorId().equals("")) {

            refifb = prefManager.getRefrigeratorId();
        } else {
            refifb = "0";
        }
        if (!prefManager.getWaterPurifierId().equals("")) {
            waterifb = prefManager.getWaterPurifierId();
        } else {
            waterifb = "0";
        }
        if (!prefManager.getWashingFLUId().equals("")) {
            wmfluifb = prefManager.getWashingFLUId();
        } else {
            wmfluifb = "0";
        }
        if (!prefManager.getWashingTLId().equals("")) {
            wmtlifb = prefManager.getWashingTLId();
        } else {
            wmtlifb = "0";
        }


        model = airifb + "," + hobifb + "," + ovenifb + "," + clothsifb + "," + cookerifb + "," + dishifb + "," + microifb + "," + refifb + "," + waterifb + "," + wmfluifb + "," + wmtlifb;
        Log.d("modelid", model);
        modelId = model.replaceAll("\\s+", "");

        etAirDaikin = (EditText) findViewById(R.id.etAirDaikin);
        if (prefManager.getetAirDaikin().equals("")) {
            etAirDaikin.setText("0");
        } else {
            etAirDaikin.setText(prefManager.getetAirDaikin());
        }

        etAirIfb = (EditText) findViewById(R.id.etAirIfb);
        etAirIfb.setEnabled(false);
        String size = String.valueOf(prefManager.getAirIfbSize());
        Log.d("size", size);
        etAirIfb.setText(size);
        /*if (prefManager.getetAirIFB().equals("")) {
            etAirIfb.setText("0");

        } else {
            etAirIfb.setText(prefManager.getetAirIFB());

        }*/


        etAirLG = (EditText) findViewById(R.id.etAirLG);
        if (prefManager.getAirLG().equals("")) {
            etAirLG.setText("0");
        } else {
            etAirLG.setText(prefManager.getAirLG());
        }


        etAirLloyds = (EditText) findViewById(R.id.etAirLloyds);
        if (prefManager.getetAirLloyds().equals("")) {
            etAirLloyds.setText("0");
        } else {
            etAirLloyds.setText(prefManager.getetAirLloyds());
        }

        etAirOther = (EditText) findViewById(R.id.etAirOther);
        if (prefManager.getetAirLOthers().equals("")) {
            etAirOther.setText("0");
        } else {
            etAirOther.setText(prefManager.getetAirLOthers());
        }

        etAirSSG = (EditText) findViewById(R.id.etAirSSG);
        if (prefManager.getetAirLSSG().equals("")) {
            etAirSSG.setText("0");
        } else {
            etAirSSG.setText(prefManager.getetAirLSSG());
        }

        etAirVoltas = (EditText) findViewById(R.id.etAirVoltas);
        if (prefManager.getetAirVoltas().equals("")) {
            etAirVoltas.setText("0");
        } else {
            etAirVoltas.setText(prefManager.getetAirVoltas());
        }

        etAirWPL = (EditText) findViewById(R.id.etAirWPL);
        if (prefManager.getetAirWPL().equals("")) {
            etAirWPL.setText("0");
        } else {
            etAirWPL.setText(prefManager.getetAirWPL());
        }


        etHobElica = (EditText) findViewById(R.id.etHobElica);
        if (prefManager.getetHobElica().equals("")) {
            etHobElica.setText("0");
        } else {
            etHobElica.setText(prefManager.getetHobElica());
        }


        etHobFaber = (EditText) findViewById(R.id.etHobFaber);
        if (prefManager.getetHobFaber().equals("")) {
            etHobFaber.setText("0");
        } else {
            etHobFaber.setText(prefManager.getetHobFaber());
        }

        etHobIfb = (EditText) findViewById(R.id.etHobIfb);
        if (prefManager.getKAIfbSize() != 0) {
            String hobsize = String.valueOf(prefManager.getKAIfbSize());
            etHobIfb.setText(hobsize);
        } else {
            etHobIfb.setText("0");
        }
       /* if (prefManager.getetHobIfb().equals("")) {
            etHobIfb.setText("0");
        } else {
            etHobIfb.setText(prefManager.getetHobIfb());
        }*/

        etHobKAFF = (EditText) findViewById(R.id.etHobKAFF);
        if (prefManager.getetHobKAFF().equals("")) {
            etHobKAFF.setText("0");
        } else {
            etHobKAFF.setText(prefManager.getetHobKAFF());
        }

        etHobOther = (EditText) findViewById(R.id.etHobOther);
        if (prefManager.getetHobOther().equals("")) {
            etHobOther.setText("0");
        } else {
            etHobOther.setText(prefManager.getetHobOther());
        }

        etOvenIFB = (EditText) findViewById(R.id.etOvenIFB);
        etOvenIFB.setEnabled(false);

        String ovensize = String.valueOf(prefManager.getOvenIfbSize());
        Log.d("ovensizedddd", ovensize);
        etOvenIFB.setText(ovensize);


        etClothsBosch = (EditText) findViewById(R.id.etClothsBosch);
        if (prefManager.getetClothBosch().equals("")) {
            etClothsBosch.setText("0");
        } else {
            etClothsBosch.setText(prefManager.getetClothBosch());
        }

        etClothsIfb = (EditText) findViewById(R.id.etClothsIfb);
        etClothsIfb.setEnabled(false);
        if (prefManager.getClothsIfbSize() == 0) {
            etClothsIfb.setText("0");
        } else {
            String clothssize = String.valueOf(prefManager.getClothsIfbSize());
            etClothsIfb.setText(clothssize);
        }

        etClothsLg = (EditText) findViewById(R.id.etClothsLg);
        if (prefManager.getetClothLG().equals("")) {
            etClothsLg.setText("0");
        } else {
            etClothsLg.setText(prefManager.getetClothLG());
        }

        etClothsOthers = (EditText) findViewById(R.id.etClothsOthers);
        if (prefManager.getetClothOthrs().equals("")) {
            etClothsOthers.setText("0");
        } else {
            etClothsOthers.setText(prefManager.getetClothOthrs());
        }

        etClothsSimen = (EditText) findViewById(R.id.etClothsSimen);
        if (prefManager.getetClothSimens().equals("")) {
            etClothsSimen.setText("0");
        } else {
            etClothsSimen.setText(prefManager.getetClothSimens());
        }

        etCookerElica = (EditText) findViewById(R.id.etCookerElica);
        if (prefManager.getCookerElica().equals("")) {
            etCookerElica.setText("0");
        } else {
            etCookerElica.setText(prefManager.getCookerElica());
        }

        etCookerFaber = (EditText) findViewById(R.id.etCookerFaber);
        if (prefManager.getCookerFaber().equals("")) {
            etCookerFaber.setText("0");
        } else {
            etCookerFaber.setText(prefManager.getCookerFaber());
        }

        etCookerIFB = (EditText) findViewById(R.id.etCookerIFB);
        etCookerIFB.setEnabled(false);
        if (prefManager.getCookerIfbSize() == 0) {
            etCookerIFB.setText("0");
        } else {
            String cookersize = String.valueOf(prefManager.getCookerIfbSize());
            etCookerIFB.setText(cookersize);
        }

        etCookerKaff = (EditText) findViewById(R.id.etCookerKaff);
        if (prefManager.getCookerKaff().equals("")) {
            etCookerKaff.setText("0");
        } else {
            etCookerKaff.setText(prefManager.getCookerKaff());
        }

        etCookerOthers = (EditText) findViewById(R.id.etCookerOthers);
        if (prefManager.getCookerOthers().equals("")) {
            etCookerOthers.setText("0");
        } else {
            etCookerOthers.setText(prefManager.getCookerOthers());
        }

        etDishBosch = (EditText) findViewById(R.id.etDishBosch);
        if (prefManager.getDishBosch().equals("")) {
            etDishBosch.setText("0");
        } else {
            etDishBosch.setText(prefManager.getDishBosch());
        }

        etDishIfb = (EditText) findViewById(R.id.etDishIfb);
        etDishIfb.setEnabled(false);
        if (prefManager.getDishIfbSize() == 0) {
            etDishIfb.setText("0");
        } else {
            String dishsize = String.valueOf(prefManager.getDishIfbSize());
            etDishIfb.setText(dishsize);
        }

        etDishOthers = (EditText) findViewById(R.id.etDishOthers);
        if (prefManager.getDishOthers().equals("")) {
            etDishOthers.setText("0");
        } else {
            etDishOthers.setText(prefManager.getDishOthers());
        }

        etDishSimens = (EditText) findViewById(R.id.etDishSimens);
        if (prefManager.getDishSimens().equals("")) {
            etDishSimens.setText("0");
        } else {
            etDishSimens.setText(prefManager.getDishSimens());
        }

        etMicroGodrej = (EditText) findViewById(R.id.etMicroGodrej);
        if (prefManager.getMicroGodrej().equals("")) {
            etMicroGodrej.setText("0");
        } else {
            etMicroGodrej.setText(prefManager.getMicroGodrej());
        }

        etMicroIfb = (EditText) findViewById(R.id.etMicroIfb);
        etMicroIfb.setEnabled(false);
        if (prefManager.getMicroOvenIfbSize() == 0) {
            etMicroIfb.setText("0");
        } else {
            String imcrosize = String.valueOf(prefManager.getMicroOvenIfbSize());
            etMicroIfb.setText(imcrosize);
        }

        etMicroLg = (EditText) findViewById(R.id.etMicroLg);
        if (prefManager.getMicroLG().equals("")) {
            etMicroLg.setText("0");
        } else {
            etMicroLg.setText(prefManager.getMicroLG());
        }

        etMicroOthers = (EditText) findViewById(R.id.etMicroOthers);
        if (prefManager.getMicroOthrs().equals("")) {
            etMicroOthers.setText("0");
        } else {
            etMicroOthers.setText(prefManager.getMicroOthrs());
        }

        etMicroPanasonic = (EditText) findViewById(R.id.etMicroPanasonic);
        if (prefManager.getMicroPanasonic().equals("")) {
            etMicroPanasonic.setText("0");
        } else {
            etMicroPanasonic.setText(prefManager.getMicroPanasonic());
        }

        etMicroSSG = (EditText) findViewById(R.id.etMicroSSG);
        if (prefManager.getMicroSSG().equals("")) {
            etMicroSSG.setText("0");
        } else {
            etMicroSSG.setText(prefManager.getMicroSSG());
        }

        etMicropWPL = (EditText) findViewById(R.id.etMicropWPL);
        if (prefManager.getMicroWPL().equals("")) {
            etMicropWPL.setText("0");
        } else {
            etMicropWPL.setText(prefManager.getMicroWPL());
        }
        etRefIFB = (EditText) findViewById(R.id.etRefIFB);
        etRefIFB.setEnabled(false);
        if (prefManager.getRefIfbSize() == 0) {
            etRefIFB.setText("0");
        } else {
            String refSize = String.valueOf(prefManager.getRefIfbSize());
            etRefIFB.setText(refSize);
        }

        etWaterIfb = (EditText) findViewById(R.id.etWaterIfb);
        if (prefManager.getWaterIFB().equals("")) {
            etWaterIfb.setText("0");
        } else {
            etWaterIfb.setText(prefManager.getWaterIFB());
        }

        etWMFLUBosch = (EditText) findViewById(R.id.etWMFLUBosch);
        if (prefManager.getWMFLUBosch().equals("")) {
            etWMFLUBosch.setText("0");
        } else {
            etWMFLUBosch.setText(prefManager.getWMFLUBosch());
        }

        etWMFLUIfb = (EditText) findViewById(R.id.etWMFLUIfb);
        etWMFLUIfb.setEnabled(false);
        if (prefManager.getWMFLUIfbSize() == 0) {
            etWMFLUIfb.setText("0");
        } else {
            String wmflusize = String.valueOf(prefManager.getWMFLUIfbSize());
            etWMFLUIfb.setText(wmflusize);
        }

        etWMFLULg = (EditText) findViewById(R.id.etWMFLULg);
        if (prefManager.getWMFLULG().equals("")) {
            etWMFLULg.setText("0");
        } else {
            etWMFLULg.setText(prefManager.getWMFLULG());
        }

        etWMFLUOthers = (EditText) findViewById(R.id.etWMFLUOthers);
        if (prefManager.getWMFLUOthers().equals("")) {
            etWMFLUOthers.setText("0");
        } else {
            etWMFLUOthers.setText(prefManager.getWMFLUOthers());
        }

        etWMFLUSSG = (EditText) findViewById(R.id.etWMFLUSSG);
        if (prefManager.getWMFLUSSG().equals("")) {
            etWMFLUSSG.setText("0");
        } else {
            etWMFLUSSG.setText(prefManager.getWMFLUSSG());
        }

        etWMTLGodrej = (EditText) findViewById(R.id.etWMTLGodrej);
        if (prefManager.getWMTLGodrej().equals("")) {
            etWMTLGodrej.setText("0");
        } else {
            etWMTLGodrej.setText(prefManager.getWMTLGodrej());
        }

        etWMTLIfb = (EditText) findViewById(R.id.etWMTLIfb);
        etWMFLUIfb.setEnabled(false);
        if (prefManager.getWMTLIFBSize() == 0) {
            etWMTLIfb.setText("0");
        } else {
            String wmtlsize = String.valueOf(prefManager.getWMTLIFBSize());
            etWMTLIfb.setText(wmtlsize);
        }

        etWMTLLG = (EditText) findViewById(R.id.etWMTLLG);
        if (prefManager.getWMTLLG().equals("")) {
            etWMTLLG.setText("0");
        } else {
            etWMTLLG.setText(prefManager.getWMTLLG());
        }

        etWMTLOthers = (EditText) findViewById(R.id.etWMTLOthers);
        if (prefManager.getWMTLOthers().equals("")) {
            etWMTLOthers.setText("0");
        } else {
            etWMTLOthers.setText(prefManager.getWMTLOthers());
        }

        etWMTLPanasonic = (EditText) findViewById(R.id.etWMTLPanasonic);
        if (prefManager.getWMTLPanasonic().equals("")) {
            etWMTLPanasonic.setText("0");
        } else {
            etWMTLPanasonic.setText(prefManager.getWMTLPanasonic());
        }

        etWMTLSSG = (EditText) findViewById(R.id.etWMTLSSG);
        if (prefManager.getWMTLSSG().equals("")) {
            etWMTLSSG.setText("0");
        } else {
            etWMTLSSG.setText(prefManager.getWMTLSSG());
        }

        etWMTLWML = (EditText) findViewById(R.id.etWMTLWML);
        if (prefManager.getWMTLWML().equals("")) {
            etWMTLWML.setText("0");
        } else {
            etWMTLWML.setText(prefManager.getWMTLWML());
        }

        if (!prefManager.getetAirDaikin().equals("")) {
            airDaikin = "IFBPC1000001" + "-" + "IFBPC1000001" + "#" + prefManager.getetAirDaikin();
        } else {
            airDaikin = "IFBPC1000001" + "-" + "IFBPC1000001" + "#" + "0";
        }
        Log.d("airDaikin", airDaikin);
        if (!prefManager.getetAirIFB().equals("")) {
            airIfb = "IFBPC1000001" + "-" + "IFBCC000015" + "#" + prefManager.getAirIfbSize();
        } else {
            airIfb = "IFBPC1000001" + "-" + "IFBCC000015" + "#" + "0";
        }
        Log.d("airIfb", airIfb);
        if (!prefManager.getAirLG().equals("")) {
            airLg = "IFBPC1000001" + "-" + "IFBCC000001" + "#" + prefManager.getAirLG();
        } else {
            airLg = "IFBPC1000001" + "-" + "IFBCC000001" + "#" + "0";
        }
        Log.d("airLg", airLg);
        if (!prefManager.getetAirLloyds().equals("")) {
            airLloyds = "IFBPC1000001" + "-" + "IFBCC000001" + "#" + prefManager.getetAirLloyds();
        } else {
            airLloyds = "IFBPC1000001" + "-" + "IFBCC000001" + "#" + "0";
        }
        Log.d("airLloyds", airLloyds);
        if (!prefManager.getetAirLOthers().equals("")) {
            airOthers = "IFBPC1000001" + "-" + "IFBCC000004" + "#" + prefManager.getetAirLOthers();
        } else {
            airOthers = "IFBPC1000001" + "-" + "IFBCC000004" + "#" + "0";
        }
        Log.d("airOthers", airOthers);
        if (!prefManager.getetAirLSSG().equals("")) {
            airssg = "IFBPC1000001" + "-" + "IFBCC000002" + "#" + prefManager.getetAirLSSG();
        } else {
            airssg = "IFBPC1000001" + "-" + "IFBCC000002" + "#" + "0";
        }
        Log.d("airssg", airssg);
        if (!prefManager.getetAirVoltas().equals("")) {
            airVoltas = "IFBPC1000001" + "-" + "IFBCC000008" + "#" + prefManager.getetAirVoltas();
        } else {
            airVoltas = "IFBPC1000001" + "-" + "IFBCC000008" + "#" + "0";
        }
        Log.d("airVoltas", airVoltas);
        if (prefManager.getetAirWPL().equals("")) {
            airWPL = "IFBPC1000001" + "-" + "IFBCC000005" + "#" + prefManager.getetAirWPL();
        } else {
            airWPL = "IFBPC1000001" + "-" + "IFBCC000005" + "#" + "0";
        }
        Log.d("airWPL", airWPL);
        if (!prefManager.getetHobElica().equals("")) {
            hobsElica = "IFBPC1000002" + "-" + "IFBCC000014" + "#" + prefManager.getetHobElica();
        } else {
            hobsElica = "IFBPC1000002" + "-" + "IFBCC000014" + "#" + "0";
        }
        Log.d("hobsElica", hobsElica);
        if (!prefManager.getetHobFaber().equals("")) {
            hobFaber = "IFBPC1000002" + "-" + "IFBCC000013" + "#" + prefManager.getetHobFaber();
        } else {
            hobFaber = "IFBPC1000002" + "-" + "IFBCC000013" + "#" + "0";
        }
        Log.d("hobFaber", hobFaber);
        if (prefManager.getKAIfbSize() != 0) {
            hobIFB = "IFBPC1000002" + "-" + "IFBCC000015" + "#" + prefManager.getKAIfbSize();
        } else {
            hobIFB = "IFBPC1000002" + "-" + "IFBCC000015" + "#" + "0";
        }
        Log.d("hobIFB", hobIFB);
        if (!prefManager.getetHobKAFF().equals("")) {
            hobKaff = "IFBPC1000002" + "-" + "IFBCC000012" + "#" + prefManager.getetHobKAFF();
        } else {
            hobKaff = "IFBPC1000002" + "-" + "IFBCC000012" + "#" + "0";
        }
        Log.d("hobKaff", hobKaff);
        if (!prefManager.getetHobOther().equals("")) {
            hobOther = "IFBPC1000002" + "-" + "IFBCC000004" + "#" + prefManager.getetHobOther();
        } else {
            hobOther = "IFBPC1000002" + "-" + "IFBCC000004" + "#" + "0";
        }
        Log.d("hobOther", hobOther);
        if (prefManager.getKAIfbSize() != 0) {
            ovenIFB = "IFBPC1000004" + "-" + "IFBCC000015" + "#" + prefManager.getOvenIfbSize();
        } else {
            ovenIFB = "IFBPC1000004" + "-" + "IFBCC000015" + "#" + "0";
        }
        Log.d("ovenIFB", ovenIFB);
        if (!prefManager.getetClothBosch().equals("")) {
            clothsBosch = "IFBPC1000005" + "-" + "IFBCC000003" + "#" + prefManager.getetClothBosch();
        } else {
            clothsBosch = "IFBPC1000005" + "-" + "IFBCC000003" + "#" + "0";
        }
        Log.d("clothsBosch", clothsBosch);
        if (prefManager.getClothsIfbSize() != 0) {
            clothsIFB = "IFBPC1000005" + "-" + "IFBCC000015" + "#" + prefManager.getClothsIfbSize();
        } else {
            clothsIFB = "IFBPC1000005" + "-" + "IFBCC000015" + "#" + "0";
        }
        Log.d("clothsIFB", clothsIFB);
        if (!prefManager.getetClothLG().equals("")) {
            clothLG = "IFBPC1000005" + "-" + "IFBCC000001" + "#" + prefManager.getetClothLG();
        } else {
            clothLG = "IFBPC1000005" + "-" + "IFBCC000001" + "#" + "0";
        }
        Log.d("clothLG", clothLG);
        if (!prefManager.getetClothOthrs().equals("")) {
            clothOther = "IFBPC1000005" + "-" + "IFBCC000004" + "#" + prefManager.getetClothOthrs();
        } else {
            clothOther = "IFBPC1000005" + "-" + "IFBCC000004" + "#" + "0";
        }
        Log.d("clothOther", clothOther);
        if (!prefManager.getetClothSimens().equals("")) {
            clothSimens = "IFBPC1000005" + "-" + "IFBCC000011" + "#" + prefManager.getetClothSimens();
        } else {
            clothSimens = "IFBPC1000005" + "-" + "IFBCC000011" + "#" + "0";
        }
        Log.d("clothSimens", clothSimens);
        if (!prefManager.getCookerElica().equals("")) {
            cookerElica = "IFBPC1000006" + "-" + "IFBCC000014" + "#" + prefManager.getCookerElica();
        } else {
            cookerElica = "IFBPC1000006" + "-" + "IFBCC000014" + "#" + "0";
        }
        Log.d("cookerElica", cookerElica);
        if (!prefManager.getCookerFaber().equals("")) {
            cookerFaber = "IFBPC1000006" + "-" + "IFBCC000013" + "#" + prefManager.getCookerFaber();
        } else {
            cookerFaber = "IFBPC1000006" + "-" + "IFBCC000013" + "#" + "0";
        }
        Log.d("cookerFaber", cookerFaber);
        if (prefManager.getCookerIfbSize() != 0) {
            cookerIFB = "IFBPC1000006" + "-" + "IFBCC000015" + "#" + prefManager.getCookerIfbSize();
        } else {
            cookerIFB = "IFBPC1000006" + "-" + "IFBCC000015" + "#" + "0";
        }
        Log.d("cookerIFB", cookerIFB);
        if (!prefManager.getCookerKaff().equals("0")) {
            cookerKaff = "IFBPC1000006" + "-" + "IFBCC000012" + "#" + prefManager.getCookerKaff();
        } else {
            cookerKaff = "IFBPC1000006" + "-" + "IFBCC000012" + "#" + "0";
        }
        Log.d("cookerKaff", cookerKaff);
        if (!prefManager.getCookerOthers().equals("")) {
            cookerOther = "IFBPC1000006" + "-" + "IFBCC000004" + "#" + prefManager.getCookerOthers();
        } else {
            cookerOther = "IFBPC1000006" + "-" + "IFBCC000004" + "#" + "0";
        }
        Log.d("cookerOther", cookerOther);
        if (!prefManager.getDishBosch().equals("")) {
            dishBosch = "IFBPC1000007" + "-" + "IFBCC000003" + "#" + prefManager.getDishBosch();
        } else {
            dishBosch = "IFBPC1000007" + "-" + "IFBCC000003" + "#" + "0";
        }
        Log.d("dishBosch", dishBosch);
        if (prefManager.getDishIfbSize() != 0) {
            dishIFB = "IFBPC1000007" + "-" + "IFBCC000015" + "#" + prefManager.getDishIfbSize();
        } else {
            dishIFB = "IFBPC1000007" + "-" + "IFBCC000015" + "#" + "0";
        }
        Log.d("dishIFB", dishIFB);
        if (!prefManager.getDishOthers().equals("")) {
            dishOther = "IFBPC1000007" + "-" + "IFBCC000004" + "#" + prefManager.getDishOthers();
        } else {
            dishOther = "IFBPC1000007" + "-" + "IFBCC000004" + "#" + "0";
        }
        Log.d("dishOther", dishOther);
        if (!prefManager.getDishSimens().equals("")) {
            dishSimen = "IFBPC1000007" + "-" + "IFBCC000011" + "#" + prefManager.getDishSimens();
        } else {
            dishSimen = "IFBPC1000007" + "-" + "IFBCC000011" + "#" + "0";
        }
        Log.d("dishSimen", dishSimen);
        if (!prefManager.getMicroGodrej().equals("")) {
            microGodrej = "IFBPC1000011" + "-" + "IFBCC000006" + "#" + prefManager.getMicroGodrej();
        } else {
            microGodrej = "IFBPC1000011" + "-" + "IFBCC000006" + "#" + "0";
        }
        Log.d("microOven", microGodrej);
        if (prefManager.getMicroOvenIfbSize() != 0) {
            microIFB = "IFBPC1000011" + "-" + "IFBCC000015" + "#" + prefManager.getMicroOvenIfbSize();
        } else {
            microIFB = "IFBPC1000011" + "-" + "IFBCC000015" + "#" + "0";
        }
        Log.d("microIFB", microIFB);
        if (!prefManager.getMicroLG().equals("")) {
            microLG = "IFBPC1000011" + "-" + "IFBCC000001" + "#" + prefManager.getMicroLG();
        } else {
            microLG = "IFBPC1000011" + "-" + "IFBCC000001" + "#" + "0";
        }
        Log.d("microLG", microLG);
        if (!prefManager.getMicroOthrs().equals("")) {
            microOTher = "IFBPC1000011" + "-" + "IFBCC000004" + "#" + prefManager.getMicroOthrs();
        } else {
            microOTher = "IFBPC1000011" + "-" + "IFBCC000004" + "#" + "0";
        }

        Log.d("microOTher", microOTher);
        if (!prefManager.getMicroPanasonic().equals("")) {
            microPanasonic = "IFBPC1000011" + "-" + "IFBCC000007" + "#" + prefManager.getMicroPanasonic();
        } else {
            microPanasonic = "IFBPC1000011" + "-" + "IFBCC000007" + "#" + "0";
        }
        Log.d("microPanasonic", microPanasonic);
        if (!prefManager.getMicroSSG().equals("")) {
            microSSG = "IFBPC1000011" + "-" + "IFBCC000002" + "#" + prefManager.getMicroSSG();
        } else {
            microSSG = "IFBPC1000011" + "-" + "IFBCC000002" + "#" + "0";
        }
        Log.d("microSSG", microSSG);
        if (!prefManager.getMicroWPL().equals("")) {
            microWPL = "IFBPC1000011" + "-" + "IFBCC000005" + "#" + prefManager.getMicroWPL();
        } else {
            microWPL = "IFBPC1000011" + "-" + "IFBCC000005" + "#" + "0";
        }
        Log.d("microWPL", microWPL);
        if (prefManager.getRefIfbSize() != 0) {
            refIfb = "IFBPC1000013" + "-" + "IFBCC000015" + "#" + prefManager.getRefIfbSize();
        } else {
            refIfb = "IFBPC1000013" + "-" + "IFBCC000015" + "#" + "0";
        }
        Log.d("refIfb", refIfb);
        if (!prefManager.getWaterIFB().equals("")) {
            waterIFB = "IFBPC1000016" + "-" + "IFBCC000015" + "#" + prefManager.getWaterIFB();
        } else {
            waterIFB = "IFBPC1000016" + "-" + "IFBCC000015" + "#" + "0";
        }
        Log.d("waterIFB", waterIFB);
        if (!prefManager.getWMFLUBosch().equals("")) {
            WMFLUBosch = "IFBPC1000021" + "-" + "IFBCC000003" + "#" + prefManager.getWMFLUBosch();
        } else {
            WMFLUBosch = "IFBPC1000021" + "-" + "IFBCC000003" + "#" + "0";
        }
        Log.d("WMFLUBosch", WMFLUBosch);
        if (prefManager.getWMFLUIfbSize() != 0) {
            WMFLUIFB = "IFBPC1000021" + "-" + "IFBCC000015" + "#" + prefManager.getWMFLUIfbSize();
        } else {
            WMFLUIFB = "IFBPC1000021" + "-" + "IFBCC000015" + "#" + "0";
        }
        Log.d("WMFLUIFB", WMFLUIFB);
        if (!prefManager.getWMFLULG().equals("")) {
            WMFLULG = "IFBPC1000021" + "-" + "IFBCC000001" + "#" + prefManager.getWMFLULG();
        } else {
            WMFLULG = "IFBPC1000021" + "-" + "IFBCC000001" + "#" + "0";
        }
        Log.d("WMFLULG", WMFLULG);
        if (!prefManager.getWMFLUOthers().equals("")) {
            WMFLUOthers = "IFBPC1000021" + "-" + "IFBCC000004" + "#" + prefManager.getWMFLUOthers();
        } else {
            WMFLUOthers = "IFBPC1000021" + "-" + "IFBCC000004" + "#" + "0";
        }
        Log.d("WMFLUOthers", WMFLUOthers);
        if (!prefManager.getWMFLUSSG().equals("")) {
            WMFLUSSG = "IFBPC1000021" + "-" + "IFBCC000002" + "#" + prefManager.getWMFLUSSG();
        } else {
            WMFLUSSG = "IFBPC1000021" + "-" + "IFBCC000002" + "#" + "0";
        }
        Log.d("WMFLUSSG", WMFLUSSG);
        if (!prefManager.getWMTLGodrej().equals("")) {
            WMTLGodrej = "IFBPC1000025" + "-" + "IFBCC000006" + "#" + prefManager.getWMTLGodrej();
        } else {
            WMTLGodrej = "IFBPC1000025" + "-" + "IFBCC000006" + "#" + "0";
        }
        Log.d("WMTLGodrej", WMTLGodrej);
        if (prefManager.getWMTLIFBSize() != 0) {
            WMTLIFB = "IFBPC1000025" + "-" + "IFBCC000015" + "#" + prefManager.getWMTLIFBSize();
        } else {
            WMTLIFB = "IFBPC1000025" + "-" + "IFBCC000015" + "#" + "0";
        }
        Log.d("WMTLIFB", WMTLIFB);
        if (!prefManager.getWMTLLG().equals("")) {
            WMTLLG = "IFBPC1000025" + "-" + "IFBCC000001" + "#" + prefManager.getWMTLLG();
        } else {
            WMTLLG = "IFBPC1000025" + "-" + "IFBCC000001" + "#" + "0";
        }
        Log.d("WMTLLG", WMTLLG);
        if (!prefManager.getWMTLOthers().equals("")) {
            WMTLOthers = "IFBPC1000025" + "-" + "IFBCC000004" + "#" + prefManager.getWMTLOthers();
        } else {
            WMTLOthers = "IFBPC1000025" + "-" + "IFBCC000004" + "#" + "0";
        }
        Log.d("WMTLOthers", WMTLOthers);
        if (!prefManager.getWMTLPanasonic().equals("")) {
            WMTLPanasonic = "IFBPC1000025" + "-" + "IFBCC000007" + "#" + prefManager.getWMTLPanasonic();
        } else {
            WMTLPanasonic = "IFBPC1000025" + "-" + "IFBCC000007" + "#" + "0";
        }
        Log.d("WMTLPanasonic", WMTLPanasonic);
        if (!prefManager.getWMTLSSG().equals("")) {
            WMTLSSG = "IFBPC1000025" + "-" + "IFBCC000002" + "#" + prefManager.getWMTLSSG();
        } else {
            WMTLSSG = "IFBPC1000025" + "-" + "IFBCC000002" + "#" + "0";
        }
        Log.d("WMTLSSG", WMTLSSG);
        if (!prefManager.getWMTLWML().equals("")) {
            WMTLWPL = "IFBPC1000025" + "-" + "IFBCC000005" + "#" + prefManager.getWMTLWML();
        } else {
            WMTLWPL = "IFBPC1000025" + "-" + "IFBCC000005" + "#" + "0";
        }
        Log.d("WMTLWPL", WMTLWPL);
        tvSave = (TextView) findViewById(R.id.tvSave);
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
        category = airDaikin + "," + airIfb + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
        Log.d("categoryyy", category);
        imgHome = (ImageView) findViewById(R.id.imgHome);

        int y = Calendar.getInstance().get(Calendar.YEAR);
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
            showYear = String.valueOf(y - 1);
            showMonth = "January" + "-" + showYear;
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
            showMonth = "August";

        } else if (month.equals("October")) {
            premonth = "September";

        } else if (month.equals("November")) {
            premonth = "October";
        } else if (month.equals("December")) {
            premonth = "November";

        }


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


    }

    private void onClick() {
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, AirConditionerDialogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, KADialogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvAdd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, OvenDialogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvAdd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, ClothsDryerDialogActivity.class);
                startActivity(intent);
                finish();
            }
        });


        tvAdd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, CookerDialogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvAdd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, DishwasherDialogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvAdd7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, MicroOvenDialogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvAdd8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, RefrigeratorDialogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvAdd9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, WaterPurifierDialogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvAdd10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, WMFLUDialogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvAdd11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, WMTLDialogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                    prefManager.saveetAirDaikin(etAirDaikin.getText().toString());
                    airDaikin = "IFBPC1000001" + "-" + "IFBPC1000001" + "#" + etAirDaikin.getText().toString();
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);


                }

                Log.d("airDaikin", airDaikin);

            }
        });

        etAirIfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirIfb.getText().toString().length() > 0) {
                    prefManager.saveetAirIFB(etAirIfb.getText().toString());
                    airIfb = "IFBPC1000001" + "-" + "IFBCC000015" + "#" + etAirIfb.getText().toString();
                    Log.d("airIfb", airIfb);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

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
                    Log.d("airLg", airLg);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);

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
                    prefManager.saveetAirLloyds(etAirLloyds.getText().toString());
                    airLloyds = "IFBPC1000001" + "-" + "IFBCC000001" + "#" + etAirLloyds.getText().toString();
                    Log.d("airLloyds", airLloyds);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etAirOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirOther.getText().toString().length() > 0) {
                    prefManager.saveetAirLOthers(etAirOther.getText().toString());
                    airOthers = "IFBPC1000001" + "-" + "IFBCC000004" + "#" + etAirOther.getText().toString();
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }
            }
        });


        etAirSSG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirSSG.getText().toString().length() > 0) {
                    prefManager.saveetAirLSSG(etAirSSG.getText().toString());
                    airssg = "IFBPC1000001" + "-" + "IFBCC000002" + "#" + etAirSSG.getText().toString();
                    Log.d("airssg", airssg);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
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
                    prefManager.saveetAirVoltas(etAirVoltas.getText().toString());
                    airVoltas = "IFBPC1000001" + "-" + "IFBCC000008" + "#" + etAirVoltas.getText().toString();
                    Log.d("airVoltas", airVoltas);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etAirWPL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAirWPL.getText().toString().length() > 0) {
                    prefManager.saveetAirWPL(etAirWPL.getText().toString());
                    airWPL = "IFBPC1000001" + "-" + "IFBCC000005" + "#" + etAirWPL.getText().toString();
                    Log.d("airWPL", airWPL);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etHobElica.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etHobElica.getText().toString().length() > 0) {
                    prefManager.saveetHobElica(etHobElica.getText().toString());
                    hobsElica = "IFBPC1000002" + "-" + "IFBCC000014" + "#" + etHobElica.getText().toString();
                    Log.d("hobsElica", hobsElica);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etHobFaber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etHobFaber.getText().toString().length() > 0) {
                    prefManager.saveetHobFaber(etHobFaber.getText().toString());
                    hobFaber = "IFBPC1000002" + "-" + "IFBCC000013" + "#" + etHobFaber.getText().toString();
                    Log.d("hobFaber", hobFaber);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etHobIfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etHobIfb.getText().toString().length() > 0) {
                    prefManager.saveetHobIfb(etHobIfb.getText().toString());
                    hobIFB = "IFBPC1000002" + "-" + "IFBCC000015" + "#" + etHobIfb.getText().toString();
                    Log.d("hobIFB", hobIFB);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etHobKAFF.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etHobKAFF.getText().toString().length() > 0) {
                    prefManager.saveetHobKAFF(etHobKAFF.getText().toString());
                    hobKaff = "IFBPC1000002" + "-" + "IFBCC000012" + "#" + etHobKAFF.getText().toString();
                    Log.d("hobKaff", hobKaff);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etHobOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etHobOther.getText().toString().length() > 0) {
                    prefManager.saveetHobOther(etHobOther.getText().toString());
                    hobOther = "IFBPC1000002" + "-" + "IFBCC000004" + "#" + etHobOther.getText().toString();
                    Log.d("hobOther", hobOther);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etOvenIFB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etOvenIFB.getText().toString().length() > 0) {
                    prefManager.saveetOvenIfb(etOvenIFB.getText().toString());
                    ovenIFB = "IFBPC1000004" + "-" + "IFBCC000015" + "#" + etOvenIFB.getText().toString();
                    Log.d("ovenIFB", ovenIFB);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

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
                    prefManager.saveetClothBosch(etClothsBosch.getText().toString());
                    clothsBosch = "IFBPC1000005" + "-" + "IFBCC000003" + "#" + etClothsBosch.getText().toString();
                    Log.d("clothsBosch", clothsBosch);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etClothsIfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etClothsIfb.getText().toString().length() > 0) {
                    prefManager.saveetClothIFB(etClothsIfb.getText().toString());
                    clothsIFB = "IFBPC1000005" + "-" + "IFBCC000015" + "#" + etClothsIfb.getText().toString();
                    Log.d("clothsIFB", clothsIFB);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etClothsLg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etClothsLg.getText().toString().length() > 0) {
                    prefManager.saveetClothLG(etClothsLg.getText().toString());
                    clothLG = "IFBPC1000005" + "-" + "IFBCC000001" + "#" + etClothsLg.getText().toString();
                    Log.d("clothLG", clothLG);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etClothsOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etClothsOthers.getText().toString().length() > 0) {
                    prefManager.saveetClothOthrs(etClothsOthers.getText().toString());
                    clothOther = "IFBPC1000005" + "-" + "IFBCC000004" + "#" + etClothsOthers.getText().toString();
                    Log.d("clothOther", clothOther);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etClothsSimen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etClothsSimen.getText().toString().length() > 0) {
                    prefManager.saveetClothSimens(etClothsSimen.getText().toString());
                    clothSimens = "IFBPC1000005" + "-" + "IFBCC000011" + "#" + etClothsSimen.getText().toString();
                    Log.d("clothSimens", clothSimens);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etCookerElica.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCookerElica.getText().toString().length() > 0) {
                    prefManager.saveCookerElica(etCookerElica.getText().toString());
                    cookerElica = "IFBPC1000006" + "-" + "IFBCC000014" + "#" + etCookerElica.getText().toString();
                    Log.d("cookerElica", cookerElica);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etCookerFaber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCookerFaber.getText().toString().length() > 0) {
                    prefManager.saveCookerFaber(etCookerFaber.getText().toString());
                    cookerFaber = "IFBPC1000006" + "-" + "IFBCC000013" + "#" + etCookerFaber.getText().toString();
                    Log.d("cookerFaber", cookerFaber);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etCookerIFB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCookerIFB.getText().toString().length() > 0) {
                    prefManager.saveCookerIFB(etCookerIFB.getText().toString());
                    cookerIFB = "IFBPC1000006" + "-" + "IFBCC000015" + "#" + etCookerIFB.getText().toString();
                    Log.d("cookerIFB", cookerIFB);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etCookerKaff.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCookerKaff.getText().toString().length() > 0) {
                    prefManager.saveCookerKaff(etCookerKaff.getText().toString());
                    cookerKaff = "IFBPC1000006" + "-" + "IFBCC000012" + "#" + etCookerKaff.getText().toString();
                    Log.d("cookerKaff", cookerKaff);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etCookerOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etCookerOthers.getText().toString().length() > 0) {
                    prefManager.saveCookerOthers(etCookerOthers.getText().toString());
                    cookerOther = "IFBPC1000006" + "-" + "IFBCC000004" + "#" + etCookerOthers.getText().toString();
                    Log.d("cookerOther", cookerOther);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
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
                    prefManager.saveDishBosch(etDishBosch.getText().toString());
                    dishBosch = "IFBPC1000007" + "-" + "IFBCC000003" + "#" + etDishBosch.getText().toString();
                    Log.d("dishBosch", dishBosch);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etDishIfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etDishIfb.getText().toString().length() > 0) {
                    prefManager.saveDishIFB(etDishIfb.getText().toString());
                    dishIFB = "IFBPC1000007" + "-" + "IFBCC000015" + "#" + etDishIfb.getText().toString();
                    Log.d("dishIFB", dishIFB);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);

                }

            }
        });

        etDishOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etDishOthers.getText().toString().length() > 0) {
                    prefManager.saveDishOthers(etDishOthers.getText().toString());
                    dishOther = "IFBPC1000007" + "-" + "IFBCC000004" + "#" + etDishOthers.getText().toString();
                    Log.d("dishOther", dishOther);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etDishSimens.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etDishSimens.getText().toString().length() > 0) {
                    prefManager.saveDishSimens(etDishSimens.getText().toString());
                    dishSimen = "IFBPC1000007" + "-" + "IFBCC000011" + "#" + etDishSimens.getText().toString();
                    Log.d("dishSimen", dishSimen);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
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
                if (etMicroGodrej.getText().toString().length() > 0) {
                    prefManager.saveMicroGodrej(etMicroGodrej.getText().toString());
                    microGodrej = "IFBPC1000011" + "-" + "IFBCC000006" + "#" + etMicroGodrej.getText().toString();
                    Log.d("microOven", microGodrej);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etMicroIfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMicroIfb.getText().toString().length() > 0) {
                    prefManager.saveMicroIfb(etMicroIfb.getText().toString());
                    microIFB = "IFBPC1000011" + "-" + "IFBCC000015" + "#" + etMicroIfb.getText().toString();
                    Log.d("microIFB", microIFB);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
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
                    prefManager.saveMicroLG(etMicroLg.getText().toString());
                    microLG = "IFBPC1000011" + "-" + "IFBCC000001" + "#" + etMicroLg.getText().toString();
                    Log.d("microLG", microLG);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
                if (etMicroOthers.getText().toString().length() > 0) {
                    prefManager.saveMicroOthrs(etMicroOthers.getText().toString());
                    microOTher = "IFBPC1000011" + "-" + "IFBCC000004" + "#" + etMicroOthers.getText().toString();
                    Log.d("microOTher", microOTher);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
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
                if (etMicroPanasonic.getText().toString().length() > 0) {
                    prefManager.saveMicroPanasonic(etMicroPanasonic.getText().toString());
                    microPanasonic = "IFBPC1000011" + "-" + "IFBCC000007" + "#" + etMicroPanasonic.getText().toString();
                    Log.d("microPanasonic", microPanasonic);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etMicroSSG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMicroPanasonic.getText().toString().length() > 0) {
                    prefManager.saveMicroSSG(etMicroSSG.getText().toString());
                    microSSG = "IFBPC1000011" + "-" + "IFBCC000002" + "#" + etMicroSSG.getText().toString();
                    Log.d("microSSG", microSSG);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etMicropWPL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMicropWPL.getText().toString().length() > 0) {
                    prefManager.saveMicroWPL(etMicropWPL.getText().toString());
                    microWPL = "IFBPC1000011" + "-" + "IFBCC000005" + "#" + etMicropWPL.getText().toString();
                    Log.d("microWPL", microWPL);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etRefIFB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etRefIFB.getText().toString().length() > 0) {
                    prefManager.saveRefIFB(etRefIFB.getText().toString());
                    refIfb = "IFBPC1000013" + "-" + "IFBCC000015" + "#" + etRefIFB.getText().toString();
                    Log.d("refIfb", refIfb);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etWaterIfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWaterIfb.getText().toString().length() > 0) {
                    prefManager.saveWaterIFB(etWaterIfb.getText().toString());
                    waterIFB = "IFBPC1000016" + "-" + "IFBCC000015" + "#" + etWaterIfb.getText().toString();
                    Log.d("waterIFB", waterIFB);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etWMFLUBosch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMFLUBosch.getText().toString().length() > 0) {
                    prefManager.saveWMFLUBosch(etWMFLUBosch.getText().toString());
                    WMFLUBosch = "IFBPC1000021" + "-" + "IFBCC000003" + "#" + etWMFLUBosch.getText().toString();
                    Log.d("WMFLUBosch", WMFLUBosch);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etWMFLUIfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMFLUIfb.getText().toString().length() > 0) {
                    prefManager.saveWMFLUIFB(etWMFLUIfb.getText().toString());
                    WMFLUIFB = "IFBPC1000021" + "-" + "IFBCC000015" + "#" + etWMFLUIfb.getText().toString();
                    Log.d("WMFLUIFB", WMFLUIFB);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }
            }
        });

        etWMFLULg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMFLULg.getText().toString().length() > 0) {
                    prefManager.saveWMFLULG(etWMFLULg.getText().toString());
                    WMFLULG = "IFBPC1000021" + "-" + "IFBCC000001" + "#" + etWMFLULg.getText().toString();
                    Log.d("WMFLULG", WMFLULG);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etWMFLUOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMFLUOthers.getText().toString().length() > 0) {
                    prefManager.saveWMFLUOthers(etWMFLUOthers.getText().toString());
                    WMFLUOthers = "IFBPC1000021" + "-" + "IFBCC000004" + "#" + etWMFLUOthers.getText().toString();
                    Log.d("WMFLUOthers", WMFLUOthers);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etWMFLUSSG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMFLUSSG.getText().toString().length() > 0) {
                    prefManager.saveWMFLUSSG(etWMFLUSSG.getText().toString());
                    WMFLUSSG = "IFBPC1000021" + "-" + "IFBCC000002" + "#" + etWMFLUSSG.getText().toString();
                    Log.d("WMFLUSSG", WMFLUSSG);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etWMTLGodrej.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMTLGodrej.getText().toString().length() > 0) {
                    prefManager.saveWMTLGodrej(etWMTLGodrej.getText().toString());
                    WMTLGodrej = "IFBPC1000025" + "-" + "IFBCC000006" + "#" + etWMTLGodrej.getText().toString();
                    Log.d("WMTLGodrej", WMTLGodrej);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });


        etWMTLIfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMTLIfb.getText().toString().length() > 0) {
                    prefManager.saveWMTLIFB(etWMTLIfb.getText().toString());
                    WMTLIFB = "IFBPC1000025" + "-" + "IFBCC000015" + "#" + etWMTLIfb.getText().toString();
                    Log.d("WMTLIFB", WMTLIFB);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etWMTLLG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMTLLG.getText().toString().length() > 0) {
                    prefManager.saveWMTLLG(etWMTLLG.getText().toString());
                    WMTLLG = "IFBPC1000025" + "-" + "IFBCC000001" + "#" + etWMTLLG.getText().toString();
                    Log.d("WMTLLG", WMTLLG);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etWMTLOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMTLOthers.getText().toString().length() > 0) {
                    prefManager.saveWMTLOthers(etWMTLOthers.getText().toString());
                    WMTLOthers = "IFBPC1000025" + "-" + "IFBCC000004" + "#" + etWMTLOthers.getText().toString();
                    Log.d("WMTLOthers", WMTLOthers);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etWMTLPanasonic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMTLPanasonic.getText().toString().length() > 0) {
                    prefManager.saveWMTLPanasonic(etWMTLPanasonic.getText().toString());
                    WMTLPanasonic = "IFBPC1000025" + "-" + "IFBCC000007" + "#" + etWMTLPanasonic.getText().toString();
                    Log.d("WMTLPanasonic", WMTLPanasonic);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);

                }

            }
        });

        etWMTLSSG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMTLSSG.getText().toString().length() > 0) {
                    prefManager.saveWMTLSSG(etWMTLSSG.getText().toString());
                    WMTLSSG = "IFBPC1000025" + "-" + "IFBCC000002" + "#" + etWMTLSSG.getText().toString();
                    Log.d("WMTLSSG", WMTLSSG);
                    category = airDaikin + "," + airIfb + "," + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });

        etWMTLWML.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etWMTLWML.getText().toString().length() > 0) {
                    prefManager.saveWMTLWML(etWMTLWML.getText().toString());
                    WMTLWPL = "IFBPC1000025" + "-" + "IFBCC000005" + "#" + etWMTLWML.getText().toString();
                    Log.d("WMTLWPL", WMTLWPL);
                    category = airDaikin + "," + airIfb + "," + airLg + "," + airLloyds + "," + airOthers + "," + airssg + "," + airVoltas + "," + airWPL + "," + hobsElica + "," + hobFaber + "," + hobIFB + "," + hobKaff + "," + hobOther + "," + ovenIFB + "," + clothsBosch + "," + clothsIFB + "," + clothLG + "," + clothOther + "," + clothSimens + "," + cookerElica + "," + cookerFaber + "," + cookerIFB + "," + cookerKaff + "," + cookerOther + "," + dishBosch + "," + dishIFB + "," + dishOther + "," + dishSimen + "," + microGodrej + "," + microIFB + "," + microLG + "," + microOTher + "," + microPanasonic + "," + microSSG + "," + microWPL + "," + refIfb + "," + waterIFB + "," + WMFLUBosch + "," + WMFLUIFB + "," + WMFLULG + "," + WMFLUOthers + "," + WMFLUSSG + "," + WMFLUSSG + "," + WMTLGodrej + "," + WMTLIFB + "," + WMTLLG + "," + WMTLOthers + "," + WMTLPanasonic + "," + WMTLSSG + "," + WMTLWPL;
                    Log.d("categoryyy", category);
                }

            }
        });


        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAirDaikin.getText().toString().length() > 0) {
                    if (etAirIfb.getText().toString().length() > 0) {
                        if (etAirLG.getText().toString().length() > 0) {
                            if (etAirLloyds.getText().toString().length() > 0) {
                                if (etAirOther.getText().toString().length() > 0) {
                                    if (etAirSSG.getText().toString().length() > 0) {
                                        if (etAirVoltas.getText().toString().length() > 0) {
                                            if (etAirWPL.getText().toString().length() > 0) {
                                                if (etHobElica.getText().toString().length() > 0) {
                                                    if (etHobFaber.getText().toString().length() > 0) {
                                                        if (etHobIfb.getText().toString().length() > 0) {
                                                            if (etHobKAFF.getText().toString().length() > 0) {
                                                                if (etHobOther.getText().toString().length() > 0) {
                                                                    if (etOvenIFB.getText().toString().length() > 0) {
                                                                        if (etClothsBosch.getText().toString().length() > 0) {
                                                                            if (etClothsIfb.getText().toString().length() > 0) {
                                                                                if (etClothsLg.getText().toString().length() > 0) {
                                                                                    if (etClothsOthers.getText().toString().length() > 0) {
                                                                                        if (etClothsSimen.getText().toString().length() > 0) {
                                                                                            if (etCookerElica.getText().toString().length() > 0) {
                                                                                                if (etCookerFaber.getText().toString().length() > 0) {
                                                                                                    if (etCookerIFB.getText().toString().length() > 0) {
                                                                                                        if (etCookerKaff.getText().toString().length() > 0) {
                                                                                                            if (etCookerOthers.getText().toString().length() > 0) {
                                                                                                                if (etDishBosch.getText().toString().length() > 0) {
                                                                                                                    if (etDishIfb.getText().toString().length() > 0) {
                                                                                                                        if (etDishOthers.getText().toString().length() > 0) {
                                                                                                                            if (etDishSimens.getText().toString().length() > 0) {
                                                                                                                                if (etMicroGodrej.getText().toString().length() > 0) {
                                                                                                                                    if (etMicroIfb.getText().toString().length() > 0) {
                                                                                                                                        if (etMicroLg.getText().toString().length() > 0) {
                                                                                                                                            if (etMicroOthers.getText().toString().length() > 0) {
                                                                                                                                                if (etMicroPanasonic.getText().toString().length() > 0) {
                                                                                                                                                    if (etMicroSSG.getText().toString().length() > 0) {
                                                                                                                                                        if (etMicropWPL.getText().toString().length() > 0) {
                                                                                                                                                            if (etRefIFB.getText().toString().length() > 0) {
                                                                                                                                                                if (etWaterIfb.getText().toString().length() > 0) {
                                                                                                                                                                    if (etWMFLUBosch.getText().toString().length() > 0) {
                                                                                                                                                                        if (etWMFLUIfb.getText().toString().length() > 0) {
                                                                                                                                                                            if (etWMFLUOthers.getText().toString().length() > 0) {
                                                                                                                                                                                if (etWMFLUSSG.getText().toString().length() > 0) {
                                                                                                                                                                                    if (etWMTLGodrej.getText().toString().length() > 0) {
                                                                                                                                                                                        if (etWMTLIfb.getText().toString().length() > 0) {
                                                                                                                                                                                            if (etWMTLLG.getText().toString().length() > 0) {
                                                                                                                                                                                                if (etWMTLOthers.getText().toString().length() > 0) {
                                                                                                                                                                                                    if (etWMTLPanasonic.getText().toString().length() > 0) {
                                                                                                                                                                                                        if (etWMTLSSG.getText().toString().length() > 0) {
                                                                                                                                                                                                            if (etWMTLWML.getText().toString().length() > 0) {
                                                                                                                                                                                                                if (etWMFLULg.getText().toString().length() > 0) {
                                                                                                                                                                                                                    postDisplaymatrix();
                                                                                                                                                                                                                } else {
                                                                                                                                                                                                                    etWMFLULg.requestFocus();
                                                                                                                                                                                                                    etWMFLULg.setError("Please enter quantity");
                                                                                                                                                                                                                }


                                                                                                                                                                                                            } else {
                                                                                                                                                                                                                etWMTLWML.requestFocus();
                                                                                                                                                                                                                etWMTLWML.setError("Please enter quantity");
                                                                                                                                                                                                            }


                                                                                                                                                                                                        } else {
                                                                                                                                                                                                            etWMTLSSG.requestFocus();
                                                                                                                                                                                                            etWMTLSSG.setError("Please enter quantity");
                                                                                                                                                                                                        }


                                                                                                                                                                                                    } else {
                                                                                                                                                                                                        etWMTLPanasonic.requestFocus();
                                                                                                                                                                                                        etWMTLPanasonic.setError("Please enter quantity");
                                                                                                                                                                                                    }


                                                                                                                                                                                                } else {
                                                                                                                                                                                                    etWMTLOthers.requestFocus();
                                                                                                                                                                                                    etWMTLOthers.setError("Please enter quantity");
                                                                                                                                                                                                }


                                                                                                                                                                                            } else {
                                                                                                                                                                                                etWMTLLG.requestFocus();
                                                                                                                                                                                                etWMTLLG.setError("Please enter quantity");
                                                                                                                                                                                            }


                                                                                                                                                                                        } else {
                                                                                                                                                                                            etWMTLIfb.requestFocus();
                                                                                                                                                                                            etWMTLIfb.setError("Please enter quantity");
                                                                                                                                                                                        }


                                                                                                                                                                                    } else {
                                                                                                                                                                                        etWMTLGodrej.requestFocus();
                                                                                                                                                                                        etWMTLGodrej.setError("Please enter quantity");
                                                                                                                                                                                    }


                                                                                                                                                                                } else {
                                                                                                                                                                                    etWMFLUSSG.requestFocus();
                                                                                                                                                                                    etWMFLUSSG.setError("Please enter quantity");
                                                                                                                                                                                }


                                                                                                                                                                            } else {
                                                                                                                                                                                etWMFLUOthers.requestFocus();
                                                                                                                                                                                etWMFLUOthers.setError("Please enter quantity");
                                                                                                                                                                            }


                                                                                                                                                                        } else {
                                                                                                                                                                            etWMFLUIfb.requestFocus();
                                                                                                                                                                            etWMFLUIfb.setError("Please enter quantity");
                                                                                                                                                                        }


                                                                                                                                                                    } else {
                                                                                                                                                                        etWMFLUBosch.requestFocus();
                                                                                                                                                                        etWMFLUBosch.setError("Please enter quantity");
                                                                                                                                                                    }


                                                                                                                                                                } else {
                                                                                                                                                                    etWaterIfb.requestFocus();
                                                                                                                                                                    etWaterIfb.setError("Please enter quantity");
                                                                                                                                                                }


                                                                                                                                                            } else {
                                                                                                                                                                etRefIFB.requestFocus();
                                                                                                                                                                etRefIFB.setError("Please enter quantity");
                                                                                                                                                            }


                                                                                                                                                        } else {
                                                                                                                                                            etMicropWPL.requestFocus();
                                                                                                                                                            etMicropWPL.setError("Please enter quantity");
                                                                                                                                                        }


                                                                                                                                                    } else {
                                                                                                                                                        etMicroSSG.requestFocus();
                                                                                                                                                        etMicroSSG.setError("Please enter quantity");
                                                                                                                                                    }


                                                                                                                                                } else {
                                                                                                                                                    etMicroPanasonic.requestFocus();
                                                                                                                                                    etMicroPanasonic.setError("Please enter quantity");
                                                                                                                                                }


                                                                                                                                            } else {
                                                                                                                                                etMicroOthers.requestFocus();
                                                                                                                                                etMicroOthers.setError("Please enter quantity");
                                                                                                                                            }


                                                                                                                                        } else {
                                                                                                                                            etMicroLg.requestFocus();
                                                                                                                                            etMicroLg.setError("Please enter quantity");
                                                                                                                                        }


                                                                                                                                    } else {
                                                                                                                                        etMicroIfb.requestFocus();
                                                                                                                                        etMicroIfb.setError("Please enter quantity");
                                                                                                                                    }


                                                                                                                                } else {
                                                                                                                                    etMicroGodrej.requestFocus();
                                                                                                                                    etMicroGodrej.setError("Please enter quantity");
                                                                                                                                }


                                                                                                                            } else {
                                                                                                                                etDishSimens.requestFocus();
                                                                                                                                etDishSimens.setError("Please enter quantity");
                                                                                                                            }


                                                                                                                        } else {
                                                                                                                            etDishOthers.requestFocus();
                                                                                                                            etDishOthers.setError("Please enter quantity");
                                                                                                                        }


                                                                                                                    } else {
                                                                                                                        etDishIfb.requestFocus();
                                                                                                                        etDishIfb.setError("Please enter quantity");
                                                                                                                    }


                                                                                                                } else {
                                                                                                                    etDishBosch.requestFocus();
                                                                                                                    etDishBosch.setError("Please enter quantity");
                                                                                                                }


                                                                                                            } else {
                                                                                                                etCookerOthers.requestFocus();
                                                                                                                etCookerOthers.setError("Please enter quantity");
                                                                                                            }


                                                                                                        } else {
                                                                                                            etCookerKaff.requestFocus();
                                                                                                            etCookerKaff.setError("Please enter quantity");
                                                                                                        }


                                                                                                    } else {
                                                                                                        etCookerIFB.requestFocus();
                                                                                                        etCookerIFB.setError("Please enter quantity");
                                                                                                    }


                                                                                                } else {
                                                                                                    etCookerFaber.requestFocus();
                                                                                                    etCookerFaber.setError("Please enter quantity");
                                                                                                }


                                                                                            } else {
                                                                                                etCookerElica.requestFocus();
                                                                                                etCookerElica.setError("Please enter quantity");
                                                                                            }


                                                                                        } else {
                                                                                            etClothsSimen.requestFocus();
                                                                                            etClothsSimen.setError("Please enter quantity");
                                                                                        }


                                                                                    } else {
                                                                                        etClothsOthers.requestFocus();
                                                                                        etClothsOthers.setError("Please enter quantity");
                                                                                    }


                                                                                } else {
                                                                                    etClothsLg.requestFocus();
                                                                                    etClothsLg.setError("Please enter quantity");
                                                                                }


                                                                            } else {
                                                                                etClothsIfb.requestFocus();
                                                                                etClothsIfb.setError("Please enter quantity");
                                                                            }


                                                                        } else {
                                                                            etClothsBosch.requestFocus();
                                                                            etClothsBosch.setError("Please enter quantity");
                                                                        }


                                                                    } else {
                                                                        etOvenIFB.requestFocus();
                                                                        etOvenIFB.setError("Please enter quantity");
                                                                    }


                                                                } else {
                                                                    etHobOther.requestFocus();
                                                                    etHobOther.setError("Please enter quantity");
                                                                }


                                                            } else {
                                                                etHobKAFF.requestFocus();
                                                                etHobKAFF.setError("Please enter quantity");
                                                            }


                                                        } else {
                                                            etHobIfb.requestFocus();
                                                            etHobIfb.setError("Please enter quantity");
                                                        }


                                                    } else {
                                                        etHobFaber.requestFocus();
                                                        etHobFaber.setError("Please enter quantity");
                                                    }


                                                } else {
                                                    etHobElica.requestFocus();
                                                    etHobElica.setError("Please enter quantity");
                                                }


                                            } else {
                                                etAirWPL.requestFocus();
                                                etAirWPL.setError("Please enter quantity");
                                            }


                                        } else {
                                            etAirVoltas.requestFocus();
                                            etAirVoltas.setError("Please enter quantity");
                                        }


                                    } else {
                                        etAirSSG.requestFocus();
                                        etAirSSG.setError("Please enter quantity");
                                    }


                                } else {
                                    etAirOther.requestFocus();
                                    etAirOther.setError("Please enter quantity");
                                }


                            } else {
                                etAirLloyds.requestFocus();
                                etAirLloyds.setError("Please enter quantity");
                            }


                        } else {
                            etAirLG.requestFocus();
                            etAirLG.setError("Please enter quantity");
                        }


                    } else {
                        etAirIfb.requestFocus();
                        etAirIfb.setError("Please enter quantity");
                    }

                } else {
                    etAirDaikin.requestFocus();
                    etAirDaikin.setError("Please enter quantity");
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
        final int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(DisplayMatrixActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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

                salesdate = d + "-" + monthname + "-" + y;

                tvDate.setText(salesdate);

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


    private void postDisplaymatrix() {
        progressDialog.show();

        Call<UploadObject> fileUpload = uploadService.postdisplaymatrix(salesdate, category, modelId, userid, securitycode);
        fileUpload.enqueue(new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, retrofit2.Response<UploadObject> response) {
                progressDialog.dismiss();
                UploadObject extraWorkingDayModel = response.body();
                if (extraWorkingDayModel.isResponseStatus()) {
                    msg = extraWorkingDayModel.getResponseText();
                    Toast.makeText(getApplicationContext(), extraWorkingDayModel.getResponseText(), Toast.LENGTH_SHORT).show();
                    Log.d("riku", "withocamera");
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
    }

    private void successAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrixActivity.this, R.style.CustomDialogNew);
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
                prefManager.saveAirConditionerId("");
                prefManager.SaveKAItemId("");
                prefManager.saveBuiltInOvenId("");
                prefManager.saveClothsDryerId("");
                prefManager.saveCookerHoodsId("");
                prefManager.saveDishWasherId("");
                prefManager.saveMicroOvenId("");
                prefManager.saveRefrigeratorId("");
                prefManager.saveWaterPurifierId("");
                prefManager.saveWashingFLUId("");
                prefManager.saveWashingTLId("");
                prefManager.saveetAirDaikin("");
                prefManager.saveetAirIFB("");
                prefManager.saveetAirLSSG("");
                prefManager.saveetAirLloyds("");
                prefManager.saveetAirLOthers("");
                prefManager.saveAirLG("");
                prefManager.saveetAirVoltas("");
                prefManager.saveetAirWPL("");
                prefManager.saveetHobElica("");
                prefManager.saveetHobFaber("");
                prefManager.saveetHobIfb("");
                prefManager.saveetHobKAFF("");
                prefManager.saveetHobOther("");
                prefManager.saveetOvenIfb("");
                prefManager.saveetClothBosch("");
                prefManager.saveetClothIFB("");
                prefManager.saveetClothLG("");
                prefManager.saveetClothOthrs("");
                prefManager.saveetClothSimens("");
                prefManager.saveCookerElica("");
                prefManager.saveCookerFaber("");
                prefManager.saveCookerIFB("");
                prefManager.saveCookerKaff("");
                prefManager.saveCookerOthers("");
                prefManager.saveDishBosch("");
                prefManager.saveDishIFB("");
                prefManager.saveDishOthers("");
                prefManager.saveDishSimens("");
                prefManager.saveMicroGodrej("");
                prefManager.saveMicroIfb("");
                prefManager.saveMicroLG("");
                prefManager.saveMicroOthrs("");
                prefManager.saveMicroPanasonic("");
                prefManager.saveMicroSSG("");
                prefManager.saveMicroWPL("");
                prefManager.saveRefIFB("");
                prefManager.saveWaterIFB("");
                prefManager.saveWMFLUBosch("");
                prefManager.saveWMFLUIFB("");
                prefManager.saveWMFLULG("");
                prefManager.saveWMFLUOthers("");
                prefManager.saveWMFLUSSG("");
                prefManager.saveWMTLGodrej("");
                prefManager.saveWMTLIFB("");
                prefManager.saveWMTLLG("");
                prefManager.saveWMTLOthers("");
                prefManager.saveWMTLPanasonic("");
                prefManager.saveWMTLSSG("");
                prefManager.saveWMTLWML("");
                etAirDaikin.setText("0");
                etAirIfb.setText("0");
                etAirLG.setText("0");
                etAirLloyds.setText("0");
                etAirOther.setText("0");
                etAirSSG.setText("0");
                etAirVoltas.setText("0");
                etAirWPL.setText("0");
                etHobElica.setText("0");
                etHobFaber.setText("0");
                etHobIfb.setText("0");
                etHobKAFF.setText("0");
                etHobOther.setText("0");
                etOvenIFB.setText("0");
                etClothsBosch.setText("0");
                etClothsIfb.setText("0");
                etClothsLg.setText("0");
                etClothsOthers.setText("0");
                etClothsSimen.setText("0");
                etCookerElica.setText("0");
                etCookerFaber.setText("0");
                etCookerIFB.setText("0");
                etCookerKaff.setText("0");
                etCookerOthers.setText("0");
                etDishBosch.setText("0");
                etDishIfb.setText("0");
                etDishOthers.setText("0");
                etDishSimens.setText("0");
                etMicroGodrej.setText("0");
                etMicroIfb.setText("0");
                etMicroLg.setText("0");
                etMicroOthers.setText("0");
                etMicroOthers.setText("0");
                etMicroPanasonic.setText("0");
                etMicroSSG.setText("0");
                etMicropWPL.setText("0");
                etRefIFB.setText("0");
                etWaterIfb.setText("0");
                etWMFLUBosch.setText("0");
                etWMFLUIfb.setText("0");
                etWMFLULg.setText("0");
                etWMFLUOthers.setText("0");
                etWMFLUSSG.setText("0");
                etWMTLGodrej.setText("0");
                etWMTLIfb.setText("0");
                etWMTLLG.setText("0");
                etWMTLPanasonic.setText("0");
                etWMTLOthers.setText("0");
                etWMTLSSG.setText("0");
                etWMTLWML.setText("0");
                prefManager.saveetAirIFB("0");
                prefManager.saveAirIfbSize(0);
                prefManager.saveKAItemSize(0);
                prefManager.saveOvenIfbSize(0);
                prefManager.saveClothsIfbSize(0);
                prefManager.saveCookerIfbSize(0);
                prefManager.saveDishIfbSize(0);
                prefManager.saveMicroOvenIfbSize(0);
                prefManager.saveRefIfbSize(0);
                prefManager.saveWMFLUIfbSize(0);
                prefManager.saveWMTLIFBSize(0);


            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }


    private void displayMatrixChecking() {
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
        progressBar.show();
        String surl =  AppController.APIURL+"api/get_DisplayMatrixReport?AEMEmployeeID=" + prefManager.getUserId() + "&FinancialYear=" + finalcialchecking + "&Month=" + premonth + "&SecurityCode=" + prefManager.getSecurityCode();
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


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DisplayMatrixActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
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


        RequestQueue requestQueue = Volley.newRequestQueue(DisplayMatrixActivity.this);
        requestQueue.add(stringRequest);

    }


    private void displayMatrixAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayMatrixActivity.this, R.style.CustomDialogNew);
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

            }
        });

        Button btnLate = (Button) dialogView.findViewById(R.id.btnLate);
        btnLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMatrixActivity.this, DashBoardActivity.class);
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
}
