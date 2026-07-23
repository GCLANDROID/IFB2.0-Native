package io.cordova.ifb.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Util implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static NetworkInfo networkInfo;
    private static int countryCode;
    private static Context c = null;
    public static String globalDateFormate = "yyyy-MM-dd'T'HH:mm:ss";
    private static String INIT_VECTOR="6832054171691981";
    public static String SECRET_KEY="74074750353890398886017484399862";


    public static String getFreshValue(String value, String defaultValue) {
        return ( value == null  || value.equals("null") || value.equals("NaN")) ? defaultValue : value;
    }



    public static String changeAnyDateFormat(String reqdate, String dateformat, String reqformat) {
        //String	date1=reqdate;

        if (reqdate.equalsIgnoreCase("") ||reqdate.equalsIgnoreCase("null") || dateformat.equalsIgnoreCase("") || reqformat.equalsIgnoreCase(""))
            return "";
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        String changedate = "";
        Date dt = null;
        if (!reqdate.equals("") && !reqdate.equals("null")) {
            try {
                dt = format.parse(reqdate);
                //SimpleDateFormat your_format = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat your_format = new SimpleDateFormat(reqformat);
                changedate = your_format.format(dt);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return reqdate;
            }


        }
        return changedate;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }


    public static boolean isImageGreaterThan2MB(Context context, Uri imageUri) {
        try {
            // Open a stream to read the image file
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(imageUri, "r");
            if (parcelFileDescriptor == null) {
                // Failed to open file descriptor
                return false;
            }

            // Get the file descriptor
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

            // Get the size of the file
            FileInputStream inputStream = new FileInputStream(fileDescriptor);
            long fileSize = inputStream.getChannel().size(); // Size in bytes

            // Convert bytes to megabytes
            double fileSizeInMB = fileSize / (1024.0 * 1024.0); // Size in MB

            // Compare with 2 MB
            return fileSizeInMB > 2.0;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap fileToBitmap(File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public static String fileToBase64(File file) throws IOException {
        Bitmap bitmap = fileToBitmap(file);
        return bitmapToBase64(bitmap);
    }

    public static String encrypt(String value,String KEY) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
