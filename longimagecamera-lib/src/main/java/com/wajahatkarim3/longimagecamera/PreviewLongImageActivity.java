package com.wajahatkarim3.longimagecamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PreviewLongImageActivity extends AppCompatActivity {

    TouchImageView imgLongPreview;
    ImageView imgClose;

    String imageFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_long_image);

        imgLongPreview = (TouchImageView) findViewById(R.id.imgLongPreview);
        imgClose = (ImageView) findViewById(R.id.imgClose);




        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent().getExtras() != null && getIntent().hasExtra("imageName"))
        {
            imageFileName = getIntent().getExtras().getString("imageName");
            Log.d("imageFileName",imageFileName);

            Bitmap d = BitmapFactory.decodeFile(imageFileName);
            int newHeight = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
            Bitmap putImage = Bitmap.createScaledBitmap(d, 512, newHeight, true);
            imgLongPreview.setImageBitmap(putImage);
        }



//        //SHARE IMAGE ON BUTTON CLICK
//
//        sharebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //get image as bitmap from ImageView
//                Drawable myDrawable = imgLongPreview.getDrawable();
//                Bitmap bitmap = ((BitmapDrawable)myDrawable).getBitmap();
//
//                //share image
//                try {
//                    File file = new File(PreviewLongImageActivity.this.getExternalCacheDir(), "myImage.png");
//                    FileOutputStream fout = new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, fout);
//                    fout.flush();
//                    fout.close();
//                    file.setReadable(true, false);
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//                    intent.setType("image/png");
//                    startActivity(Intent.createChooser(intent, "Share Image Via"));
//                     } catch (FileNotFoundException e) {
//                     e.printStackTrace();
//                     Toast.makeText(PreviewLongImageActivity.this, "File not found", Toast.LENGTH_SHORT).show();
//                     }catch (IOException e) {
//                     e.printStackTrace();
//                     }catch (Exception e){
//                     e.printStackTrace();
//                     }
//                    }
//        });







    }


}
