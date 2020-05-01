package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class CreateAccount extends AppCompatActivity {
    Button btRegisterEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        btRegisterEmail = findViewById(R.id.btSendEmail);


        btRegisterEmail.setOnClickListener((v)->{
            Intent registerNewAcctIntent = new Intent(this, RegisterAccount.class);
            startActivity(registerNewAcctIntent);
            finish();
            //setResult(RESULT_OK, loginIntent);
            //MainActivity.isLoggedIn = true;
            //finish();
        });

    }
}
