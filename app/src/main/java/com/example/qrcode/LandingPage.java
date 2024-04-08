package com.example.qrcode;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;

public class LandingPage extends AppCompatActivity {
    Button loginfact;
    Button loginstd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        loginfact=findViewById(R.id.btn2);
        loginstd=findViewById(R.id.btn1);
        loginfact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LandingPage.this,FacultyLogin.class);
                startActivity(i);
            }

        });

        loginstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LandingPage.this,StudentLogin.class);
                startActivity(i);
            }

        });


    }
}