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

public class LoginScreen extends AppCompatActivity {
    Button btLogin, btForgotPassword, btNewUser;
    TextView tvLoginScreenError;
    EditText etLoginPassword, etLoginEmail;

    final String  SERVER_URL = "http://192.168.1.15:12345";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btLogin = findViewById(R.id.btLogin);
        btForgotPassword = findViewById(R.id.btForgot);
        btNewUser = findViewById(R.id.btNewAcct);

        tvLoginScreenError = findViewById(R.id.tvLoginScreenError);
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);


        btLogin.setOnClickListener((v)->{

            boolean isValidEmail = Validator.hasInput(etLoginEmail) && Validator.isEmailAddress(etLoginEmail);
            final RequestQueue requestQueue = Volley.newRequestQueue(this);


            if(isValidEmail && Validator.hasInput(etLoginPassword)) {
                // above: check that all the fields have input that makes sense

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("password", etLoginPassword.getText().toString());
                    jsonBody.put("time_span", 1000);
                    jsonBody.put("method", "authenticate");
                    jsonBody.put("time_unit", "SECONDS");
                    jsonBody.put("email", etLoginEmail.getText().toString());
                } catch (Exception e) { }

                JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST,
                        SERVER_URL, jsonBody, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try{  // pass resulting token to the main activity
                            MainActivity.current_user = response.getString("token");
                        }
                        catch (Exception e){}
                        Intent loginIntent = new Intent(LoginScreen.this, MainActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        tvLoginScreenError.setText(Validator.getNetworkErrorMsg(error));
                    }
                });

                requestQueue.add(jsonPostRequest);

            }
            else{
                String msg = (isValidEmail ? "Please ensure all fields have input." :
                        "Please enter a valid email address.");
                tvLoginScreenError.setText(msg);
            }
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
