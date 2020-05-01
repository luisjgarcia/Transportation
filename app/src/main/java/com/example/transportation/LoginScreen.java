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
            Intent loginIntent = new Intent(LoginScreen.this, MainActivity.class);
            setResult(RESULT_OK, loginIntent);
            MainActivity.isLoggedIn = true;
            finish();
        });



        btForgotPassword.setOnClickListener((v)->{
            Intent forgotPWIntent = new Intent(LoginScreen.this, ForgotPassword.class);
            startActivity(forgotPWIntent);
            //setResult(RESULT_OK, loginIntent);
            finish();
        });


        btNewUser.setOnClickListener((v)->{
            Intent newUserIntent = new Intent(LoginScreen.this, CreateAccount.class);
            startActivity(newUserIntent);
            //setResult(RESULT_OK, loginIntent);
            finish();
        });


    }


}
