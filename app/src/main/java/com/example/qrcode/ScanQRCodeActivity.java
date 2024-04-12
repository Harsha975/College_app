package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

public class ScanQRCodeActivity extends AppCompatActivity {
    Button btn;
    TextView textt;
    double latitudestud = 0, longitudestud = 0, latitudefact = 0, longitudefact = 0;

    private static final int REQUEST_LOCATION = 1;

    TextView showLocationTxt;

    LocationManager locationManager;

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Check if GPS is enabled
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // GPS is not enabled, ask user to enable it
            enableGPS();
        } else {
            // GPS is enabled, proceed to get location
            // Check permissions
            if (ActivityCompat.checkSelfPermission(ScanQRCodeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(ScanQRCodeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ScanQRCodeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                // Get the location
                Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Location locationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                Location location = locationGps != null ? locationGps : (locationNetwork != null ? locationNetwork : locationPassive);

                if (location != null) {
                    latitudestud = location.getLatitude();
                    longitudestud = location.getLongitude();
                    // Update TextView with latitude and longitude
                    showLocationTxt.setText("Latitude: " + latitudestud + "\nLongitude: " + longitudestud);
                } else {
                    Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        btn = findViewById(R.id.scnbtn);
        textt = findViewById(R.id.text);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        showLocationTxt = findViewById(R.id.show_location);
        getLocation();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ScanQRCodeActivity.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("Scan QR Code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String contents = result.getContents();

            if (contents != null) {
                String[] parts = contents.split(",");
                double latitude = Double.parseDouble(parts[0]);
                double longitude = Double.parseDouble(parts[1]);
                String dateTime = parts[2];

                String[] dateTimeParts = dateTime.split(" ");
                String date = dateTimeParts[0];
                String time = dateTimeParts[1];

                // Set the date and time to respective TextViews
                TextView dateTextView = findViewById(R.id.dateres);
                TextView timeTextView = findViewById(R.id.timeres);
                dateTextView.setText("Date: " + date);
                timeTextView.setText("Time: " + time);

                // Calculate distance between student's location and QR code location
                float[] distance = new float[1];
                Location.distanceBetween(latitudestud, longitudestud, latitude, longitude, distance);

                // Display the distance
                Toast.makeText(ScanQRCodeActivity.this, "Distance: " + distance[0] + " meters", Toast.LENGTH_SHORT).show();

                // Enable or disable the "Mark Attendance" button based on distance
                Button markAttendanceBtn = findViewById(R.id.mark_attendance_btn);
                if (distance[0] < 3) {
                    // Attendance successful
                    textt.setVisibility(View.VISIBLE);
                    textt.setText("Attendance Successful");
                    markAttendanceBtn.setEnabled(true);
                } else {
                    // Attendance failed
                    textt.setVisibility(View.VISIBLE);
                    textt.setText("Attendance Failed");
                    markAttendanceBtn.setEnabled(false);
                    Toast.makeText(ScanQRCodeActivity.this, "Better luck next time with proxy!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}