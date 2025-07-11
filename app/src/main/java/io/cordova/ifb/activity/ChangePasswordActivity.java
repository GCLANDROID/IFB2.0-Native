package io.cordova.ifb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText etOld, etNew, etConfirm;
    TextView ruleLength, ruleUpper, ruleLower, ruleNumber, ruleSpecial;
    Button btnUpdate;

    private static final Pattern UPPER = Pattern.compile(".*[A-Z].*");
    private static final Pattern LOWER = Pattern.compile(".*[a-z].*");
    private static final Pattern NUMBER = Pattern.compile(".*[0-9].*");
    private static final Pattern SPECIAL = Pattern.compile(".*[!@#\\$%\\(\\).].*");
    PrefManager prefManager;
    AlertDialog alerDialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
    }

    private void initView(){
        prefManager = new PrefManager(this);
        etOld = findViewById(R.id.etOld);
        etNew = findViewById(R.id.etNew);
        etConfirm = findViewById(R.id.etConfirm);

        ruleLength  = findViewById(R.id.ruleLength);
        ruleUpper   = findViewById(R.id.ruleUpper);
        ruleLower   = findViewById(R.id.ruleLower);
        ruleNumber  = findViewById(R.id.ruleNumber);
        ruleSpecial = findViewById(R.id.ruleSpecial);

        btnUpdate = findViewById(R.id.btnUpdate);
        initRules();


        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                checkRules();
            }
            @Override public void afterTextChanged(Editable editable) {}
        };

        etNew.addTextChangedListener(watcher);
        etConfirm.addTextChangedListener(watcher);

        btnUpdate.setOnClickListener(v -> {
            changePassword();
        });
    }


    private void initRules() {
        ruleLength.setText("Password Length 6 - 12 Characters");
        ruleUpper.setText("At Least 1 Upper Case");
        ruleLower.setText("At Least 1 Lower Case");
        ruleNumber.setText("At Least 1 Number");
        ruleSpecial.setText("At Least 1 Special Character (!,@,#,$,%,(,))");
    }

    private void checkRules() {
        String password = etNew.getText().toString();
        String confirm = etConfirm.getText().toString();

        boolean lenOk = password.length() >= 6 && password.length() <= 12;
        boolean upperOk = UPPER.matcher(password).matches();
        boolean lowerOk = LOWER.matcher(password).matches();
        boolean numberOk = NUMBER.matcher(password).matches();
        boolean specialOk = SPECIAL.matcher(password).matches();
        boolean matchOk = password.equals(confirm);

        setRuleView(ruleLength, lenOk);
        setRuleView(ruleUpper, upperOk);
        setRuleView(ruleLower, lowerOk);
        setRuleView(ruleNumber, numberOk);
        setRuleView(ruleSpecial, specialOk);

        btnUpdate.setEnabled(lenOk && upperOk && lowerOk && numberOk && specialOk && matchOk);
    }

    private void setRuleView(TextView tv, boolean isValid) {
        int icon = isValid ? R.drawable.iccheck : R.drawable.ic_close;
        int color = isValid ? Color.parseColor("#388E3C") : Color.RED;
        tv.setTextColor(color);
        tv.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
    }

    public void changePassword() {
        byte[] olddata = new byte[0];
        byte[] newdata = new byte[0];
        try {
            olddata = etOld.getText().toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String oldbase64 = Base64.encodeToString(olddata, Base64.DEFAULT).replaceAll("\\s+", "");;
        try {
            newdata = etNew.getText().toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String newbase64 = Base64.encodeToString(newdata, Base64.DEFAULT).replaceAll("\\s+", "");;

        String surl = AppController.APIURL+ "api/EmployeeChangedPassword?Code="+prefManager.getMasterId()+"&password="+oldbase64+"&NewPassword="+newbase64+"&SecurityCode="+prefManager.getSecurityCode();
        Log.d("inputLogin", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Authenticating...");
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
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                successAlert(responseText);
                                // Toast.makeText(getApplicationContext(),responseText,Toast.LENGTH_LONG).show();



                            } else {
                                Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();

                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(LoginActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(ChangePasswordActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();

                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(ChangePasswordActivity.this);
        requestQueue.add(stringRequest);

    }


    private void successAlert(String text) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ChangePasswordActivity.this, R.style.CustomDialogNew);
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
                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
}