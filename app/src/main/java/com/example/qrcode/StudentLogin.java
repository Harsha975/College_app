package com.example.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentLogin extends AppCompatActivity {
    Button studentlogin;
    EditText emailstud;
    EditText passwordstud;
    FirebaseAuth mAuth;
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(),StudentLogin.class);
//            startActivity(intent);
//            finish();
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        emailstud = findViewById(R.id.emailstd);
        passwordstud= findViewById(R.id.passwordstd);
        studentlogin=(Button)findViewById(R.id.studentlogin);

        studentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email = String.valueOf(emailstud.getText());
                password = String.valueOf(passwordstud.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(StudentLogin.this,"Enter Email",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(StudentLogin.this,"Enter password",Toast.LENGTH_LONG).show();
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(StudentLogin.this, "Login Succesfull",
                                            Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(StudentLogin.this,ScanQRCodeActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(StudentLogin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });
    }
}