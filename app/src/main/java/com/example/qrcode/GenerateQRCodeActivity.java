package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQRCodeActivity extends AppCompatActivity {
    private ImageView qrCodeIV;
    private TextView showLocationTxt;
    private LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

        qrCodeIV = findViewById(R.id.idIVQRCode);
        showLocationTxt = findViewById(R.id.show_location);
        Button generateQRBtn = findViewById(R.id.idBtnGenerateQR);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        generateQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // No need to generate QR code here, it will be generated automatically after getting location
                getLocation();
            }
        });

        getLocation();
    }

    private void generateQRCode(double latitude, double longitude) {
        // Combine latitude and longitude into a single string
        String locationString =latitude+","+longitude;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            // Encode the location string into a QR code
            BitMatrix bitMatrix = multiFormatWriter.encode(locationString, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCodeIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating QR Code", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Check if GPS is enabled
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // GPS is not enabled, ask user to enable it
            enableGPS();
        } else {
            // GPS is enabled, proceed to get location
            // Check permissions
            if (ActivityCompat.checkSelfPermission(GenerateQRCodeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GenerateQRCodeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(GenerateQRCodeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                // Get the location
                try {
                    Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    Location locationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                    Location location = locationGps != null ? locationGps : (locationNetwork != null ? locationNetwork : locationPassive);
                    System.out.print("badaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+location);
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        // Update TextView with latitude and longitude
                        showLocationTxt.setText("Latitude: " + latitude + "\nLongitude: " + longitude);

                        // Generate QR code with latitude and longitude
                        generateQRCode(latitude, longitude);
                    } else {
                        Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void enableGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this, grantResults[0], Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}