package io.cordova.ifb.activity;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.Calendar;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.QuestionAdapter;
import io.cordova.ifb.module.QusetionModel;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class DialogActivity extends AppCompatActivity {
    LinearLayout llSave, llSkip;
    ArrayList<QusetionModel> itemList = new ArrayList<>();
    PrefManager prefManager;
    int y;
    String year, month, financialYear;
    QuestionAdapter aAdapter;
    ArrayList<String> item = new ArrayList<>();
    EditText etAns1, etAns2, etAns3, etAns4, etAns5, etAns6;
    String ans1, ans2, ans3, ans4, ans5, ans6;
    String answer;
    String ansswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        this.setFinishOnTouchOutside(false);
        initialize();

        onClick();
    }

    private void initialize() {
        prefManager = new PrefManager(this);
        llSave = (LinearLayout) findViewById(R.id.llSave);
        llSkip = (LinearLayout) findViewById(R.id.llSkip);

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

        etAns1 = (EditText) findViewById(R.id.etAns1);
        etAns2 = (EditText) findViewById(R.id.etAns2);
        etAns3 = (EditText) findViewById(R.id.etAns3);
        etAns4 = (EditText) findViewById(R.id.etAns4);
        etAns5 = (EditText) findViewById(R.id.etAns5);
        etAns6 = (EditText) findViewById(R.id.etAns6);


    }

    private void onClick() {


        llSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etAns1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAns1.getText().toString().length() > 0) {
                    if (prefManager.getSecurityCode().equals("GCL")){
                        ans1 = etAns1.getText().toString().replaceAll("\\s+", "-") + "/" + "GCLEQ00001";

                    }else {
                        ans1 = etAns1.getText().toString().replaceAll("\\s+", "-") + "/" + "IFBEQ00001";
                    }
                    String ans223=etAns1.getText().toString();
                    Log.d("ans223",ans223);
                }

            }
        });


        etAns2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAns2.getText().toString().length() > 0) {
                    if (prefManager.getSecurityCode().equals("GCL")){
                        ans2 = etAns2.getText().toString().replaceAll("\\s+", "-") + "/" + "GCLEQ00002";
                    }else {
                        ans2 = etAns2.getText().toString().replaceAll("\\s+", "-") + "/" + "IFBEQ00002";
                        Log.d("ans2",ans2);
                    }
                }

            }
        });

        etAns3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAns3.getText().toString().length() > 0) {
                    if (prefManager.getSecurityCode().equals("GCL")){
                        ans3 = etAns3.getText().toString().replaceAll("\\s+", "-") + "/" + "GCLEQ00003";
                    }else {
                        ans3 = etAns3.getText().toString().replaceAll("\\s+", "-") + "/" + "IFBEQ00003";
                    }
                }

            }
        });

        etAns4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAns4.getText().toString().length() > 0) {
                    if (prefManager.getSecurityCode().equals("GCL")){
                        ans4 = etAns4.getText().toString().replaceAll("\\s+", "-") + "/" + "GCLEQ00004";
                    }else {
                        ans4 = etAns4.getText().toString().replaceAll("\\s+", "-") + "/" + "IFBEQ00004";
                    }
                }

            }
        });

        etAns5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAns5.getText().toString().length() > 0) {
                    if (prefManager.getSecurityCode().equals("GCL")){
                        ans5 = etAns5.getText().toString().replaceAll("\\s+", "-") + "/" + "GCLEQ00005";
                    }else {
                        ans5 = etAns5.getText().toString().replaceAll("\\s+", "-") + "/" + "IFBEQ00005";
                    }
                }

            }
        });

        etAns6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAns6.getText().toString().length() > 0) {
                    if (prefManager.getSecurityCode().equals("GCL")){
                        ans6 = etAns6.getText().toString().replaceAll("\\s+", "-") + "/" + "GCLEQ00006";
                    }else {
                        ans6 = etAns6.getText().toString().replaceAll("\\s+", "-") + "/" + "IFBEQ00006";
                    }
                    answer = ans1 + "," + ans2 + "," + ans3 + "," + ans4 + "," + ans5 + "," + ans6;
                    ansswer = answer.replaceAll("\\s+", "");
                    Log.d("ansswer", ansswer);

                }

            }
        });
        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (etAns1.getText().toString().length()>0){
                        if (etAns2.getText().toString().length()>0){
                            if (etAns3.getText().toString().length()>0){
                                if (etAns4.getText().toString().length()>0){
                                    if (etAns5.getText().toString().length()>0){
                                        if (etAns6.getText().toString().length()>0){
                                            answerFunction();

                                        }else {
                                            Toast.makeText(getApplicationContext(),"Please enter Answer6",Toast.LENGTH_LONG).show();

                                        }

                                    }else {
                                        Toast.makeText(getApplicationContext(),"Please enter Answer5",Toast.LENGTH_LONG).show();

                                    }

                                }else {
                                    Toast.makeText(getApplicationContext(),"Please enter Answer4",Toast.LENGTH_LONG).show();

                                }

                            }else {
                                Toast.makeText(getApplicationContext(),"Please enter Answer3",Toast.LENGTH_LONG).show();

                            }

                        }else {
                            Toast.makeText(getApplicationContext(),"Please enter Answer2",Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(getApplicationContext(),"Please enter Answer1",Toast.LENGTH_LONG).show();
                    }

            }
        });


    }


    public void updateItemStatus(int position, boolean status) {
        itemList.get(position).setSelected(status);
        if (itemList.get(position).isSelected() == true) {
            item.add(itemList.get(position).getQuestionId());
        } else {
            item.clear();
        }


        Log.d("arpan", item.toString());
        String i = item.toString();
        String d = i.replace("[", "").replace("]", "");
        String questionid = d.replaceAll("\\s+", "");
        Log.d("commas", questionid);


        aAdapter.notifyDataSetChanged();
    }


    public void answerFunction() {
        String surl =  AppController.APIURL+"api/post_IFBCSRQuestionAnswer?ZoneId=" + prefManager.getZoneId() + "&BranchId=" + prefManager.getBranchId() + "&AEMEmployeeID=" + prefManager.getUserId() + "&FinancialYear=" + financialYear + "&Month=" + month + "&Answer=" + ansswer + "&SecurityCode=" + prefManager.getSecurityCode();
        Log.d("inputanswaer", surl);
        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
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
                            String responseText = job1.optString("responseText");
                            boolean responseStatus = job1.optBoolean("responseStatus");
                            if (responseStatus) {
                                Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_LONG).show();
                                finish();


                            } else {
                                //  shoeDialog();

                            }

                            // boolean _status = job1.getBoolean("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DialogActivity.this, "Volly Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(DialogActivity.this, "volly 2" + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("ert", error.toString());
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(DialogActivity.this);
        requestQueue.add(stringRequest);

    }


}
