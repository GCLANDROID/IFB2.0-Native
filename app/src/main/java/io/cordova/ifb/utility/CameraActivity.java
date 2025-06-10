package io.cordova.ifb.utility;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;


import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileOutputStream;

import io.cordova.ifb.R;

public class CameraActivity extends AppCompatActivity {
    private PreviewView previewView;
    private Button captureBtn;
    private ProcessCameraProvider cameraProvider;
    private ImageCapture imageCapture;

    public CameraActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        previewView = findViewById(R.id.previewView);
        captureBtn = findViewById(R.id.captureBtn);


            startCamera();


        captureBtn.setOnClickListener(v -> takePhoto());
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(this);
        future.addListener(() -> {
            try {
                cameraProvider = future.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build();

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        File file = new File(getExternalFilesDir(null),
                "IMG_" + System.currentTimeMillis() + ".jpg");

        ImageCapture.OutputFileOptions options =
                new ImageCapture.OutputFileOptions.Builder(file).build();

        imageCapture.takePicture(options, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults output) {
                        runOnUiThread(() -> {
                            Uri uri = Uri.fromFile(file);
                            cropToSquare(uri, file);
                        });
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exc) {
                        exc.printStackTrace();
                    }
                });
    }

    private void cropToSquare(Uri imageUri, File originalFile) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            int newDim = Math.min(width, height);
            int xOffset = (width - newDim) / 2;
            int yOffset = (height - newDim) / 2;

            Bitmap cropped = Bitmap.createBitmap(bitmap, xOffset, yOffset, newDim, newDim);

            File croppedFile = new File(getExternalFilesDir(null),
                    "CROPPED_" + System.currentTimeMillis() + ".jpg");

            FileOutputStream out = new FileOutputStream(croppedFile);
            cropped.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            Intent intent = new Intent();
            intent.putExtra("imageUri", Uri.fromFile(croppedFile));
            intent.putExtra("imagePath", croppedFile.getAbsolutePath());
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}