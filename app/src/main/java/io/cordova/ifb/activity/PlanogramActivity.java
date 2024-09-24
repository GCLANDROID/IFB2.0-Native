package io.cordova.ifb.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.developers.imagezipper.ImageZipper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.cordova.ifb.AndroidXCamera.AndroidXCameraActivity;
import io.cordova.ifb.R;
import io.cordova.ifb.adapter.PlanopgrameHygeneAdapter;
import io.cordova.ifb.adapter.ScannedBarcodeAdapter;
import io.cordova.ifb.databinding.ActivityPlanogramBinding;
import io.cordova.ifb.module.PlanogramHygeneModel;
import io.cordova.ifb.module.ScannedPlanogramBarcodeModel;
import io.cordova.ifb.utility.Util;

public class PlanogramActivity extends AppCompatActivity {
    ActivityPlanogramBinding binding;
    private static final int SCANNER_REQUEST = 801;
    ArrayList<ScannedPlanogramBarcodeModel>itemList=new ArrayList<>();
    String nosaledate;
    ArrayList<PlanogramHygeneModel>reportitemList=new ArrayList<>();
    PlanopgrameHygeneAdapter adapter;
    ArrayList<String>monthList=new ArrayList<>();
    ArrayList<String>yearList=new ArrayList<>();
    String monthname;
    int y;
    String year,month;
    String financialYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_planogram);
        initView();
    }

    private void initView(){
        binding.llReportHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llReport.setVisibility(View.VISIBLE);
                binding.llManage.setVisibility(View.GONE);
                binding.tvReport.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvManage.setTextColor(Color.parseColor("#E59B9B"));
            }
        });


        binding.llManageHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llReport.setVisibility(View.GONE);
                binding.llManage.setVisibility(View.VISIBLE);
                binding.tvReport.setTextColor(Color.parseColor("#E59B9B"));
                binding.tvManage.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        binding.llScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanogramActivity.this, ODUScannerActivity.class);
                startActivityForResult(intent, SCANNER_REQUEST);
            }
        });

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(PlanogramActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvItem.setLayoutManager(layoutManager);
        getItemList();
        binding.llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.llSearchByMonth.getVisibility()==View.GONE){
                    binding.llSearchByMonth.setVisibility(View.VISIBLE);
                    binding.llSearchByDate.setVisibility(View.GONE);
                    binding.tvSearchTxt.setText("Search by Date");
                    binding.rvPlanogram.setVisibility(View.GONE);

                }else {
                    binding.llSearchByMonth.setVisibility(View.GONE);
                    binding.llSearchByDate.setVisibility(View.VISIBLE);
                    binding.tvSearchTxt.setText("Search by Month & Year");
                    binding.rvPlanogram.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rvPlanogram.setVisibility(View.GONE);
              showDateDialog();
            }
        });

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar cal = Calendar.getInstance();
        nosaledate = df.format(cal.getTime());;
        binding.tvDate.setText(nosaledate+"  ");

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

        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");


        yearList.add("2023-2024");
        yearList.add("2024-2025");
        yearList.add("2025-2026");
        yearList.add("2026-2027");


        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>
                (PlanogramActivity.this, android.R.layout.simple_spinner_item,
                        monthList); //selected item will look like a spinner set from XML
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spMonth.setAdapter(monthAdapter);

        int pos=monthList.indexOf(month);
        binding.spMonth.setSelection(pos);


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>
                (PlanogramActivity.this, android.R.layout.simple_spinner_item,
                        yearList); //selected item will look like a spinner set from XML
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.SpYear.setAdapter(yearAdapter);

        int yearpos=yearList.indexOf(financialYear);
        binding.SpYear.setSelection(yearpos);

        LinearLayoutManager palogramlayoutManager
                = new LinearLayoutManager(PlanogramActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvPlanogram.setLayoutManager(palogramlayoutManager);
        getReportList();
        binding.tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rvPlanogram.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getItemList(){
        ScannedBarcodeAdapter barcodeAdapter=new ScannedBarcodeAdapter(itemList,PlanogramActivity.this);
        binding.rvItem.setAdapter(barcodeAdapter);
    }

    private void getReportList(){
        reportitemList.add(new PlanogramHygeneModel("EXECUTIVE ZXV 9/6/3","8903287031755","WASHER DRYER","23 Sept 2024 at 12:20 PM"));
        reportitemList.add(new PlanogramHygeneModel("EXECUTIVE ZXV 9/6/3","8903287031755","WASHER DRYER","23 Sept 2024 at 12:10 PM"));
        reportitemList.add(new PlanogramHygeneModel("EXECUTIVE ZXV 9/6/3","8903287031755","WASHER DRYER","23 Sept 2024 at 10:10 AM"));
        reportitemList.add(new PlanogramHygeneModel("EXECUTIVE ZXV 9/6/3","8903287031755","WASHER DRYER","23 Sept 2024 at 08:10 AM"));
        adapter=new PlanopgrameHygeneAdapter(reportitemList,PlanogramActivity.this);
        binding.rvPlanogram.setAdapter(adapter);
    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if ((requestCode == SCANNER_REQUEST)) {
            String message1 = data.getStringExtra("MESSAGE");
            binding.llScanReport.setVisibility(View.VISIBLE);


        }
    }


    public void updateStatus(int position,boolean status)
    {
        for (int i =0 ;i<reportitemList.size();i++)
        {
            if (i==position)
            {
                reportitemList.get(i).setExpanded(status);
            }
            else
            {
                reportitemList.get(i).setExpanded(false);
            }
        }
        adapter.notifyDataSetChanged();
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
        final DatePickerDialog dialog = new DatePickerDialog(PlanogramActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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

                nosaledate = d + "-" + monthname + "-" + y;
                binding.tvDate.setText(nosaledate+"  ");
                binding.rvPlanogram.setVisibility(View.VISIBLE);


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
}