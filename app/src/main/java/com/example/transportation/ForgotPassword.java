package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ForgotPassword extends AppCompatActivity {
    Button btResetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        btResetEmail = findViewById(R.id.btResetEmail);


        btResetEmail.setOnClickListener((v)->{
            Intent registerAcctIntent = new Intent(this, RegisterAccount.class);
            startActivity(registerAcctIntent);
            finish();
            //setResult(RESULT_OK, loginIntent);
            //MainActivity.isLoggedIn = true;
            //finish();
        });
    }


}
