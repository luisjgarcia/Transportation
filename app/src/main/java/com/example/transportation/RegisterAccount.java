package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegisterAccount extends AppCompatActivity {
    Button btSetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        //btSetPassword = findViewById(R.id.btSetPassword);


        btSetPassword = findViewById(R.id.btSetPassword);


        btSetPassword.setOnClickListener((v)->{
            Intent setPasswordIntent = new Intent(this, LoginScreen.class);
            startActivity(setPasswordIntent);
            finish();
            //setResult(RESULT_OK, loginIntent);
            //MainActivity.isLoggedIn = true;
            //finish();
        });

    }

}
