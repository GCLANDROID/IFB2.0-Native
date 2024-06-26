package io.cordova.ifb.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONObject;

import java.util.Calendar;

import io.cordova.ifb.R;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class TeamCollaborationActivity extends AppCompatActivity {
    ImageView imgCalendar;
    RadioGroup radioGroupQ1, radioGroupQ2, radioGroupQ3, radioGroupQ4, radioGroupQ5;
    Button btnSubmit;
    String q1 = "", q2 = "", q3 = "", q4 = "", q5 = "";
    String monthname;
    String salesDate = "";
    TextView tvDate;
    PrefManager prefManager;
    AlertDialog alerDialog1;
    LinearLayout llReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_collaboration);
        initView();
        onClick();
    }

    private void initView() {
        radioGroupQ1 = (RadioGroup) findViewById(R.id.radioGroupQ1);
        radioGroupQ2 = (RadioGroup) findViewById(R.id.radioGroupQ2);
        radioGroupQ3 = (RadioGroup) findViewById(R.id.radioGroupQ3);
        radioGroupQ4 = (RadioGroup) findViewById(R.id.radioGroupQ4);
        radioGroupQ5 = (RadioGroup) findViewById(R.id.radioGroupQ5);
        radioGroupQ1.clearCheck();

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        tvDate = (TextView) findViewById(R.id.tvDate);
        imgCalendar = (ImageView) findViewById(R.id.imgCalendar);
        prefManager = new PrefManager(TeamCollaborationActivity.this);

        llReport = (LinearLayout) findViewById(R.id.llReport);

    }

    private void onClick() {
        radioGroupQ1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonQ1VerySatisfied:
                        q1 = "IFBQC00001,1";
                        break;
                    case R.id.radioButtonQ1Satisfied:
                        q1 = "IFBQC00001,2";
                        break;
                    case R.id.radioButtonQ1SomeSatisfied:
                        q1 = "IFBQC00001,3";
                        break;
                    case R.id.radioButtonQ1NotSatisfied:
                        q1 = "IFBQC00001,4";
                        break;
                }
            }
        });

        radioGroupQ2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonQ2VerySatisfied:
                        q2 = "IFBQC00002,1";
                        break;
                    case R.id.radioButtonQ2Satisfied:
                        q2 = "IFBQC00002,2";
                        break;
                    case R.id.radioButtonQ2SomeSatisfied:
                        q2 = "IFBQC00002,3";
                        break;
                    case R.id.radioButtonQ2NotSatisfied:
                        q2 = "IFBQC00002,4";
                        break;
                }
            }
        });

        radioGroupQ3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonQ3VerySatisfied:
                        q3 = "IFBQC00003,1";
                        break;
                    case R.id.radioButtonQ3Satisfied:
                        q3 = "IFBQC00003,2";
                        break;
                    case R.id.radioButtonQ3SomeSatisfied:
                        q3 = "IFBQC00003,3";
                        break;
                    case R.id.radioButtonQ3NotSatisfied:
                        q3 = "IFBQC00003,4";
                        break;
                }
            }
        });

        radioGroupQ4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonQ4VerySatisfied:
                        q4 = "IFBQC00004,1";
                        break;
                    case R.id.radioButtonQ4Satisfied:
                        q4 = "IFBQC00004,2";
                        break;
                    case R.id.radioButtonQ4SomeSatisfied:
                        q4 = "IFBQC00004,3";
                        break;
                    case R.id.radioButtonQ4NotSatisfied:
                        q4 = "IFBQC00004,4";
                        break;
                }
            }
        });

        radioGroupQ5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonQ5VerySatisfied:
                        q5 = "IFBQC00005,1";
                        break;
                    case R.id.radioButtonQ5Satisfied:
                        q5 = "IFBQC00005,2";
                        break;
                    case R.id.radioButtonQ5SomeSatisfied:
                        q5 = "IFBQC00005,3";
                        break;
                    case R.id.radioButtonQ5NotSatisfied:
                        q5 = "IFBQC00005,4";
                        break;
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!q1.equals("")) {
                    if (!q2.equals("")) {
                        if (!q3.equals("")) {
                            if (!q4.equals("")) {
                                if (!q5.equals("")) {
                                    if (!salesDate.equals("")) {
                                        String question = q1 + "#" + q2 + "#" + q3 + "#" + q4 + "#" + q5;
                                        postQuestion(question);
                                    }else {
                                        Toast.makeText(TeamCollaborationActivity.this, "Please Select Date of Interaction", Toast.LENGTH_LONG).show();

                                    }
                                }else {
                                    Toast.makeText(TeamCollaborationActivity.this, "Please Choose Question 5", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Toast.makeText(TeamCollaborationActivity.this, "Please Choose Question 4", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            Toast.makeText(TeamCollaborationActivity.this, "Please Choose Question 3", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(TeamCollaborationActivity.this, "Please Choose Question 2", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(TeamCollaborationActivity.this, "Please Choose Question 1", Toast.LENGTH_LONG).show();
                }
            }
        });

        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeamCollaborationActivity.this, CollaborationReportActivity.class);
                startActivity(intent);
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
        final DatePickerDialog dialog = new DatePickerDialog(TeamCollaborationActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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

    private void postQuestion(String question) {

        final ProgressDialog pd = new ProgressDialog(TeamCollaborationActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        pd.show();

        AndroidNetworking.upload( AppController.APIURL+"api/post_EmployeeCollaborationQuestion")
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("Date_Of_Intraction", salesDate)
                .addMultipartParameter("Answer", question)
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
                            Toast.makeText(TeamCollaborationActivity.this, responseText, Toast.LENGTH_LONG).show();

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

    private void successAlert(String text) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TeamCollaborationActivity.this, R.style.CustomDialogNew);
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
                Intent intent = new Intent(TeamCollaborationActivity.this, SalesDashboardActivity.class);
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