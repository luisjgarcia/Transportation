package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterAccount extends AppCompatActivity {
    Button btSetPassword;
    EditText etEmail, etTempPW, etNewPW;
    TextView tvErrorMsg;
    final String  SERVER_URL = "http://192.168.86.99:12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);


        btSetPassword = findViewById(R.id.btSetPassword);
        etEmail = findViewById(R.id.etEmail);
        etTempPW = findViewById(R.id.etTempPW);
        etNewPW = findViewById(R.id.etNewPW);
        tvErrorMsg = findViewById(R.id.tvErrorMsg);


        btSetPassword.setOnClickListener((v)->{
            boolean isValidEmail = Validator.hasInput(etEmail) && Validator.isEmailAddress(etEmail);
            final RequestQueue requestQueue = Volley.newRequestQueue(this);


            if(isValidEmail && Validator.hasInput(etTempPW) && Validator.hasInput(etNewPW)) {
                // above: check that all the fields have input that makes sense

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("method", "registerAccount");
                    jsonBody.put("password", etNewPW.getText().toString());
                    jsonBody.put("temp_password", etTempPW.getText().toString());
                    jsonBody.put("email", etEmail.getText().toString());
                } catch (Exception e) { }

                JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST,
                        SERVER_URL, jsonBody, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                    Intent setPasswordIntent = new Intent(RegisterAccount.this, LoginScreen.class);
                    startActivity(setPasswordIntent);
                    finish();
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        tvErrorMsg.setText(Validator.getNetworkErrorMsg(error));
                    }
                });


                requestQueue.add(jsonPostRequest);


            }
            else{
                String msg = (isValidEmail ? "Please ensure all fields have input." :
                        "Please enter a valid email address.");
                tvErrorMsg.setText(msg);
            }







            ////////////////
            /*
            Intent setPasswordIntent = new Intent(this, LoginScreen.class);
            startActivity(setPasswordIntent);
            finish();
            */
            /////////////////

            //setResult(RESULT_OK, loginIntent);
            //MainActivity.isLoggedIn = true;
            //finish();
        });

    }




}
