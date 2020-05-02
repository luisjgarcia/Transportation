package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ForgotPassword extends AppCompatActivity {
    Button btResetEmail;
    EditText etEmail;
    TextView tvErrorMsg;
    final String SERVER_URL = "http://192.168.1.15:12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        btResetEmail = findViewById(R.id.btResetEmail);
        etEmail = findViewById(R.id.etEmail);
        tvErrorMsg = findViewById(R.id.tvErrorMsg);


        btResetEmail.setOnClickListener((v)->{
            final RequestQueue requestQueue = Volley.newRequestQueue(this);

            if(Validator.hasInput(etEmail) && Validator.isEmailAddress(etEmail)) {
                if(Validator.isExistingUser(etEmail, SERVER_URL, requestQueue)){
                    Intent registerAcctIntent = new Intent(this, RegisterAccount.class);
                    startActivity(registerAcctIntent);
                    finish();
                }
                else{
                    tvErrorMsg.setText("User does not exist! Create an account.");
                    etEmail.setText("");
                }
            }
            else{
                tvErrorMsg.setText("Please enter a valid email address.");
                etEmail.setText("");
            }


            //setResult(RESULT_OK, loginIntent);
            //MainActivity.isLoggedIn = true;
            //finish();
        });
    }


}
