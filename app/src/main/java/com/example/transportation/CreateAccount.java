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

public class CreateAccount extends AppCompatActivity {
    Button btCreateUser;
    EditText etEmail, etFName, etLName;
    TextView tvCreateError;
    //final String  SERVER_URL = "http://192.168.86.99:12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        btCreateUser = findViewById(R.id.btCreateUser);
        etEmail = findViewById(R.id.etEmail);
        etFName = findViewById(R.id.etFName);
        etLName = findViewById(R.id.etLName);
        tvCreateError = findViewById(R.id.tvCreateError);


        btCreateUser.setOnClickListener((v)->{

            boolean isValidEmail = Validator.hasInput(etEmail) && Validator.isEmailAddress(etEmail);
            final RequestQueue requestQueue = Volley.newRequestQueue(this);


            if(isValidEmail && Validator.hasInput(etFName) && Validator.hasInput(etLName)) {
                // above: check that all the fields have input that makes sense

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("method", "createAccount");
                    jsonBody.put("last_name", etLName.getText().toString());
                    jsonBody.put("first_name", etFName.getText().toString());
                    jsonBody.put("email", etEmail.getText().toString());
                } catch (Exception e) { }

                JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST,
                        LoginScreen.SERVER_URL, jsonBody, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent createUserIntent = new Intent(CreateAccount.this, RegisterAccount.class);
                        startActivity(createUserIntent);
                        finish();
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        tvCreateError.setText(Validator.getNetworkErrorMsg(error));
                    }
                });


                requestQueue.add(jsonPostRequest);


            }
            else{
                String msg = (isValidEmail ? "Please ensure all fields have input." :
                        "Please enter a valid email address.");
                tvCreateError.setText(msg);
            }




        });






    }
}
