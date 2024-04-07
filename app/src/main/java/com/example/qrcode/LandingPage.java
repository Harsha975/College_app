package com.example.qrcode;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;

public class LandingPage extends AppCompatActivity {
    Button loginstd;
    Button loginfact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginstd=(Button)findViewById(R.id.btn1);
        loginfact=(Button)findViewById(R.id.btn22);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this,StudentLogin.class);
                startActivity(intent);

            }

        });
    }
}