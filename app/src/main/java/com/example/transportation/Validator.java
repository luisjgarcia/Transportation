package com.example.transportation;

import android.app.Activity;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Validator {
    //public static class LoginValidator{
    public static int respCode; // response codes
    public static boolean isValidResponse ;//= true;

        public static boolean hasInput(EditText et){
            if(et.getText().toString().isEmpty()){
                return false;
            }
            return true;
        }

        public static boolean isEmailAddress(EditText et){
            String input = et.getText().toString();
            if(input.contains("@") && input.contains("."))
                return true;
            return false;
        }




        public static String getNetworkErrorMsg(VolleyError error){
            String errorMsg = "";
            NetworkResponse networkResponse = error.networkResponse;

            if (networkResponse != null && networkResponse.data != null) {
                String jsonError = new String(networkResponse.data);
                try{
                    JSONObject reader = new JSONObject(jsonError);
                    errorMsg = reader.getString("message");
                }
                catch (Exception e){}

            }
            return errorMsg;
        }





/*
        public static boolean isExistingUser(EditText etEmail, String SERVER_URL, RequestQueue requestQueue) {
            //final RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("method", "forgotPassword");
                jsonBody.put("email", etEmail.getText().toString());
            } catch (Exception e) { }

            JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST,
                    SERVER_URL, jsonBody, new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response) {
                    Validator.isValidResponse = true;
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Validator.isValidResponse = false;
                }
            });


            requestQueue.add(jsonPostRequest);

            return Validator.isValidResponse;
        }*/






   // }
}
