package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DisplayUserInfo extends AppCompatActivity {
    TextView tvUserEmail, tvUserFName, tvUserLName, tvGetUserError;
    JSONArray resultArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_info);


        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserFName = findViewById(R.id.tvUserFName);
        tvUserLName = findViewById(R.id.tvUserLName);
        tvGetUserError = findViewById(R.id.tvGetUserError);


        tvUserEmail.setText(MainActivity.userEmail);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("method", "getAllAccounts");
        }
        catch(Exception e){}

        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, LoginScreen.SERVER_URL,
                jsonBody, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                //TODO:
                // * fill this area with something to parse JSON response
                // * as well as something to set tv's to given fields

                String email;
                JSONObject foundUser = null;
                try{  // pass resulting token to the main activity
                    resultArray = new JSONArray(response);
                    for(int i = 0; i < resultArray.length(); i++){
                        JSONObject current = resultArray.getJSONObject(i);
                        email = current.getString("email");

                        if(email.equalsIgnoreCase(MainActivity.userEmail)){
                            foundUser = current;
                            //break; // exit the loop once we have our winner
                        }
                        tvGetUserError.setText("None found!!!!");
                    }
                    tvUserFName.setText(foundUser.getString("first_name"));
                    tvUserLName.setText(foundUser.getString("last_name"));
                    tvGetUserError.setText("Success!!");
                }
                catch (Exception e){
                    tvGetUserError.setText("Unspecified Error");
                }

            }}, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                tvGetUserError.setText("Failure: " +// Validator.getNetworkErrorMsg(error));
                        jsonBody.toString());
            }
        }) {

            //Here we override the header creation method of the Request class
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();

                headers.put("autho_token", MainActivity.current_token);
                return headers;
            }
        };



        requestQueue.add(jsonPostRequest);


    }
}
