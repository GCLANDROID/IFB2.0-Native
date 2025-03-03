package io.cordova.ifb.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.cordova.ifb.R;

public class BarcodeScannerActivity extends AppCompatActivity {
    private static final String TAG = "BarcodeScannerActivity";
    ImageView imgView;
    Button btnScanner;
    private Camera camera;
    ImageView imgFlashOnOff,imgScannerAnim;
    private boolean isFlashOn = false; // Track flashlight state
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        /*imgView = findViewById(R.id.imgView);
        btnScanner = findViewById(R.id.btnScanner);
        BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        InputImage image = InputImage.fromBitmap(bitmap,90);
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //scanBarcodes(image);
                BarcodeScanner barcodeScanner = BarcodeScanning.getClient();
                barcodeScanner.process(image).addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        Log.e(TAG, "onSuccess: called");
                        for (Barcode barcode : barcodes) {
                            String barcodeData = barcode.getRawValue();
                            Log.e(TAG, "onSuccess: barcodeData: "+barcodeData);
                            Toast.makeText(BarcodeScannerActivity.this,barcodeData, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: called");
                        Log.e(TAG, "onFailure: "+e.getMessage());
                    }
                });
            }
        });*/
        startCamera();
        imgFlashOnOff = findViewById(R.id.imgFlashOnOff);
        imgScannerAnim = findViewById(R.id.imgScannerAnim);
        imgFlashOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFlashlight();
            }
        });

    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                // Bind CameraX lifecycle to the activity
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                PreviewView viewFinder = findViewById(R.id.viewFinder);
                //PreviewView viewFinder1 = findViewById(R.id.viewFinder1);

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().build();
                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new BarcodeAnalyzer());

                camera = cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalysis);

            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error starting camera", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private class BarcodeAnalyzer implements ImageAnalysis.Analyzer {
        @OptIn(markerClass = ExperimentalGetImage.class)
        @Override
        public void analyze(ImageProxy image) {
            InputImage inputImage;
            try {
                inputImage = InputImage.fromMediaImage(image.getImage(), 90);

                // Get the dimensions of the scan area
                //Rect scanArea = getScanArea(); // Define your scan area (Rect area) programmatically

                // Crop the InputImage to focus on the scan area
                //InputImage croppedImage = cropImage(inputImage, scanArea);

                BarcodeScanner scanner = BarcodeScanning.getClient();
                scanner.process(inputImage)
                        .addOnSuccessListener(barcodes -> {

                            for (Barcode barcode : barcodes) {
                                String barcodeData = barcode.getDisplayValue();
                                Log.d(TAG, "Detected barcode: " + barcodeData);

                                // Handle the barcode data (you can show it or perform an action)
                                //Toast.makeText(BarcodeScannerActivity.this, "Barcode Detected: " + barcodeData, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent();
                                intent.putExtra("code",barcodeData);
                                setResult(Activity.RESULT_OK,intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(e -> Log.e(TAG, "Barcode scanning failed", e))
                        .addOnCompleteListener(task -> image.close());

            } catch (Exception e) {
                Log.e(TAG, "Failed to process image", e);
                image.close();
            }
        }
    }



    private void scanBarcodes(InputImage image) {
        // [START set_detector_options]
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_QR_CODE,
                                Barcode.FORMAT_AZTEC)
                        .build();
        // [END set_detector_options]

        // [START get_detector]
        BarcodeScanner scanner = BarcodeScanning.getClient();
        // Or, to specify the formats to recognize:
        // BarcodeScanner scanner = BarcodeScanning.getClient(options);
        // [END get_detector]

        // [START run_detector]
        Task<List<Barcode>> result = scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        // Task completed successfully
                        // [START_EXCLUDE]
                        // [START get_barcodes]
                        for (Barcode barcode: barcodes) {
                            Rect bounds = barcode.getBoundingBox();
                            Point[] corners = barcode.getCornerPoints();

                            String rawValue = barcode.getRawValue();
                            Log.e(TAG, "onSuccess: "+rawValue);
                            int valueType = barcode.getValueType();
                            // See API reference for complete list of supported types
                            switch (valueType) {
                                case Barcode.TYPE_WIFI:
                                    String ssid = barcode.getWifi().getSsid();
                                    String password = barcode.getWifi().getPassword();
                                    int type = barcode.getWifi().getEncryptionType();
                                    break;
                                case Barcode.TYPE_URL:
                                    String title = barcode.getUrl().getTitle();
                                    String url = barcode.getUrl().getUrl();
                                    break;
                            }
                        }
                        // [END get_barcodes]
                        // [END_EXCLUDE]
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // ...
                    }
                });
        // [END run_detector]
    }

    // Method to get the defined scan area
    /*private Rect getScanArea() {
        // Define the area where barcode scanning should occur (e.g., coordinates of the center rectangle)
        // You can get the position and size of the scan area from the view (ScanAreaView).
        // For example:
        View scanAreaView = findViewById(R.id.scan_area_overlay);
        int left = scanAreaView.getLeft();
        int top = scanAreaView.getTop();
        int right = scanAreaView.getRight();
        int bottom = scanAreaView.getBottom();

        return new Rect(left, top, right, bottom);
    }*/

    // Crop the image to the scan area
    private InputImage cropImage(InputImage inputImage, Rect scanArea) {
        // Create a cropped image using the scan area dimensions
        Bitmap bitmap = inputImage.getBitmapInternal();
        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, scanArea.left, scanArea.top,
                scanArea.width(), scanArea.height());

        // Convert cropped bitmap back to InputImage
        return InputImage.fromBitmap(croppedBitmap, inputImage.getRotationDegrees());
    }

    private void toggleFlashlight() {
        if (camera != null) {
            CameraControl cameraControl = camera.getCameraControl();
            if (isFlashOn) {
                cameraControl.enableTorch(false);
                imgFlashOnOff.setImageResource(R.drawable.flash_off);
                isFlashOn = false;
            } else {
                cameraControl.enableTorch(true);
                imgFlashOnOff.setImageResource(R.drawable.flash_on);
                isFlashOn = true;
            }
        }
    }
}