package com.smartwebarts.samajsewa.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.smartwebarts.samajsewa.R;
import com.smartwebarts.samajsewa.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    int pageHeight = 1120;
    int pagewidth = 792;
    Bitmap bitmap;
    Bitmap bmp, scaledbmp;

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.homeinclude.opendrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.draweld.openDrawer(GravityCompat.START);
            }
        });


        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 250, 170, false);


        if (checkPermission()) {
          //  Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }


        binding.navBar.myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(intent);
            }
        });

        binding.navBar.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent( Intent.ACTION_DIAL );
                    intent.setData( Uri.parse( "tel:" + "8707091593" ) );
                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity( intent );
                }

        });

        binding.homeinclude.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDF();
            }
        });



    }


    private void generatePDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint title = new Paint();
        Paint fathername = new Paint();
        Paint address = new Paint();
        Paint namess = new Paint();


        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();
        canvas.drawBitmap(scaledbmp, 56, 40, paint);
        title.setTextSize(30);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        title.setColor(ContextCompat.getColor(this, R.color.black));
        canvas.drawText("Samaj Sewa", 350, 150, title);


        namess.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        namess.setColor(ContextCompat.getColor(this, R.color.black));
        namess.setTextSize(25);
        canvas.drawText("Name :- Rohan Kumar", 56, 400, namess);

        fathername.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        fathername.setColor(ContextCompat.getColor(this, R.color.black));
        fathername.setTextSize(25);
        canvas.drawText("Father's Name :- Rameshss Kumar", 56, 450, namess);

        address.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        address.setColor(ContextCompat.getColor(this, R.color.black));
        address.setTextSize(25);
        canvas.drawText("Village :- Maharupur, District :- Azamgarh\nUttar Pardesh 276406", 56, 500, namess);

        pdfDocument.finishPage(myPage);
        File files = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(files));
            Toast.makeText(MainActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private boolean checkPermission() {

        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {

                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

}

