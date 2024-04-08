package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

public class FacultyLogin extends AppCompatActivity {
    Button facultylogin;
    EditText emailfact;
    EditText passwordfact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);
        facultylogin=(Button)findViewById(R.id.facultylogin);
        facultylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FacultyLogin.this,GenerateQRCodeActivity.class);
                startActivity(i);
            }

        });
    }
}