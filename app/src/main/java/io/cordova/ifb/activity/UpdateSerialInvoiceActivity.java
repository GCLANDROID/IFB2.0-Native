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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.wajahatkarim3.longimagecamera.LongImageCameraActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import id.zelory.compressor.Compressor;
import io.cordova.ifb.R;
import io.cordova.ifb.utility.AppController;
import io.cordova.ifb.utility.PrefManager;

public class UpdateSerialInvoiceActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgAttach, imgDoc;
    AlertDialog alert1, cameraAlert;
    private String encodedImage;
    private Uri imageUri;
    private static final int CAMERA_REQUEST = 1;
    File file, compressedImageFile, file1;
    File dFile;
    private static final int REQUEST_GALLERY_CODE = 200;
    String stringFile = "";
    Uri uri;
    String imageFileName;
    ;
    File pictureFile;
    String serialnoFlag, invoiceFlag, tokenNo;
    LinearLayout lnSerialNumber, llInvoice;
    Button btnSave;
    PrefManager prefManager;
    AlertDialog alerDialog1;
    String financialYear, month;
    EditText etSerial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_serial_invoice);
        initView();
    }

    private void initView() {
        prefManager = new PrefManager(UpdateSerialInvoiceActivity.this);
        imgAttach = (ImageView) findViewById(R.id.imgAttach);
        imgDoc = (ImageView) findViewById(R.id.imgDoc);
        imgAttach.setOnClickListener(this);
        tokenNo = getIntent().getStringExtra("tokenNo");
        serialnoFlag = getIntent().getStringExtra("serialnoFlag");
        invoiceFlag = getIntent().getStringExtra("invoiceFlag");
        month = getIntent().getStringExtra("month");
        financialYear = getIntent().getStringExtra("financialYear");
        lnSerialNumber = (LinearLayout) findViewById(R.id.lnSerialNumber);
        llInvoice = (LinearLayout) findViewById(R.id.llInvoice);
        if (serialnoFlag.equals("1")) {
            lnSerialNumber.setVisibility(View.VISIBLE);
        } else {
            lnSerialNumber.setVisibility(View.GONE);
        }

        if (invoiceFlag.equals("1")) {
            llInvoice.setVisibility(View.VISIBLE);
        } else {
            llInvoice.setVisibility(View.GONE);
        }
        etSerial = (EditText) findViewById(R.id.etSerial);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }


    private void attachDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UpdateSerialInvoiceActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.camera_dialog, null);
        dialogBuilder.setView(dialogView);
        LinearLayout llCamera = (LinearLayout) dialogView.findViewById(R.id.llCamera);
        LinearLayout llGallery = (LinearLayout) dialogView.findViewById(R.id.llGallery);
        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialog();
            }
        });

        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent();

            }
        });


        alert1 = dialogBuilder.create();
        alert1.setCancelable(true);
        Window window = alert1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alert1.show();
    }

    private void cameraDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UpdateSerialInvoiceActivity.this, R.style.CustomDialogNew);
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
                cameraIntent();
            }
        });

        llCustomCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LongImageCameraActivity.launch(UpdateSerialInvoiceActivity.this);

            }
        });


        cameraAlert = dialogBuilder.create();
        cameraAlert.setCancelable(true);
        Window window = cameraAlert.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        cameraAlert.show();
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
                            imgDoc.setImageBitmap(bm);
                            alert1.dismiss();
                            String contentType = "image/jpg";
                            String[] brkDown = imageurl.split("/");
                            String name = brkDown[5];
                            stringFile = name + "_" + encodedImage + "_" + contentType;
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
            case REQUEST_GALLERY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    InputStream imageStream = null;
                    try {
                        try {
                            uri = data.getData();
                            String filePath = getRealPathFromURIPath(uri, UpdateSerialInvoiceActivity.this);
                            file = new File(filePath);
                            //  Log.d(TAG, "filePath=" + filePath);
                            imageStream = getContentResolver().openInputStream(uri);
                            Bitmap bm = cropToSquare(BitmapFactory.decodeStream(imageStream));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                            imgDoc.setImageBitmap(bm);
                            alert1.dismiss();
                            cameraAlert.dismiss();
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

            case LongImageCameraActivity.LONG_IMAGE_RESULT_CODE:


                if (resultCode == RESULT_OK && requestCode == LongImageCameraActivity.LONG_IMAGE_RESULT_CODE) {
                    imageFileName = data.getStringExtra(LongImageCameraActivity.IMAGE_PATH_KEY);
                    Log.d("imageFileName", imageFileName);
                    Bitmap d = BitmapFactory.decodeFile(imageFileName);
                    int newHeight = (int) (d.getHeight() * (512.0 / d.getWidth()));
                    Bitmap putImage = Bitmap.createScaledBitmap(d, 512, newHeight, true);
                    imgDoc.setImageBitmap(putImage);
                    pictureFile = (File) data.getExtras().get("picture");
                    Log.d("fjjgk", pictureFile.toString());
                    try {
                        compressedImageFile = new Compressor(this).compressToFile(pictureFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    putImage.compress(Bitmap.CompressFormat.PNG, 10, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    alert1.dismiss();
                    cameraAlert.dismiss();
                    String contentType = "image/png";
                    String[] brkDown = imageFileName.split("/");
                    String name = brkDown[6];
                    stringFile = name + "_" + encodedImage + "_" + contentType;
                    Log.d("stringFile", stringFile);

                    alert1.dismiss();


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

    @Override
    public void onClick(View view) {
        if (view == imgAttach) {
            attachDialog();
        }else if (view==btnSave){
            if (serialnoFlag.equals("1") && invoiceFlag.equals("1")){
                validationcheckForAll();
            }else if (serialnoFlag.equals("1") && invoiceFlag.equals("0")){
                validationcheckForSerial();
            }else if (serialnoFlag.equals("0") && invoiceFlag.equals("1")){
                validationcheckForInvoice();
            }
        }

    }


    private void postSaleWithImage() {

        final ProgressDialog pd = new ProgressDialog(UpdateSerialInvoiceActivity.this);
        pd.setMessage("Loading..");
        pd.setCancelable(false);

        AndroidNetworking.upload( AppController.APIURL+"api/post_EmployeeSalesSerialNoInvoiceCopy")
                .addMultipartParameter("AEMEmployeeID", prefManager.getUserId())
                .addMultipartParameter("TokenNo", tokenNo)
                .addMultipartParameter("FinancialYear", financialYear)
                .addMultipartParameter("Month", month)
                .addMultipartParameter("Invoicecopy", stringFile)
                .addMultipartParameter("SerialNo", etSerial.getText().toString())
                .addMultipartParameter("Operation", "2")
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
                            Toast.makeText(UpdateSerialInvoiceActivity.this, responseText, Toast.LENGTH_LONG).show();

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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UpdateSerialInvoiceActivity.this, R.style.CustomDialogNew);
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
                onBackPressed();
            }
        });

        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(false);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }

    private void validationcheckForAll() {

        if (etSerial.getText().toString().length() > 17) {
            if (!stringFile.equals("")) {
                postSaleWithImage();
            } else {
                 Toast.makeText(UpdateSerialInvoiceActivity.this,"Please Capture Your Invoice Copy",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(UpdateSerialInvoiceActivity.this,"Please Enter Serial number",Toast.LENGTH_LONG).show();

        }
    }

    private void validationcheckForSerial() {

        if (etSerial.getText().toString().length() > 17) {

                postSaleWithImage();

        } else {
            Toast.makeText(UpdateSerialInvoiceActivity.this,"Please Enter Serial number",Toast.LENGTH_LONG).show();

        }
    }

    private void validationcheckForInvoice() {


            if (!stringFile.equals("")) {
                postSaleWithImage();
            } else {
                Toast.makeText(UpdateSerialInvoiceActivity.this,"Please Capture Your Invoice Copy",Toast.LENGTH_LONG).show();
            }

    }
}