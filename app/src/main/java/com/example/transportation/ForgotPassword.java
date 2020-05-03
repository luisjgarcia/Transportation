package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {
    Button btResetEmail;
    EditText etEmail;
    TextView tvErrorMsg;
    final String SERVER_URL = "http://192.168.86.99:12345";

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


                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("method", "forgotPassword");
                    jsonBody.put("email", etEmail.getText().toString());
                } catch (Exception e) { }

                JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST,
                        SERVER_URL, jsonBody, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent registerAcctIntent = new Intent(ForgotPassword.this, RegisterAccount.class);
                        startActivity(registerAcctIntent);
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
                tvErrorMsg.setText("Please enter a valid email address.");
                etEmail.setText("");
            }


            //setResult(RESULT_OK, loginIntent);
            //MainActivity.isLoggedIn = true;
            //finish();
        });
    }


}
