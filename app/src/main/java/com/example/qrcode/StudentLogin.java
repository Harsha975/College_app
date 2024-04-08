package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

public class StudentLogin extends AppCompatActivity {
    Button studentlogin;
    EditText emailstud;
    EditText passwordstud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        studentlogin=(Button)findViewById(R.id.studentlogin);
        studentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentLogin.this,ScanQRCodeActivity.class);
                startActivity(i);
            }

        });
    }
}