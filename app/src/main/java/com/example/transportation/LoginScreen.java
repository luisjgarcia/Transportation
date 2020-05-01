package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LoginScreen extends AppCompatActivity {
    Button btLogin, btForgotPassword, btNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        btLogin = findViewById(R.id.btLogin);
        btForgotPassword = findViewById(R.id.btForgot);
        btNewUser = findViewById(R.id.btNewAcct);


        btLogin.setOnClickListener((v)->{

            // After validating change Activity
            Intent intent;
            intent = new Intent(this, MainActivity.class);
            // Should send users input
            //intent.putExtra();
            startActivity(intent);
        });



        btForgotPassword.setOnClickListener((v)->{
            Intent forgotPWIntent = new Intent(this, ForgotPassword.class);
            startActivity(forgotPWIntent);
            //setResult(RESULT_OK, loginIntent);
            finish();
        });


        btNewUser.setOnClickListener((v)->{
            Intent newUserIntent = new Intent(this, CreateAccount.class);
            startActivity(newUserIntent);
            //setResult(RESULT_OK, loginIntent);
            finish();
        });


    }


}
