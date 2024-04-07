package com.example.qrcode;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;

public class LandingPage extends AppCompatActivity {
    Button loginStd;
    Button loginFact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        loginStd=findViewById(R.id.btn1);
        loginFact=findViewById(R.id.btn22);

        loginStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this,StudentLogin.class);
                startActivity(intent);
            }

        });
    }
}