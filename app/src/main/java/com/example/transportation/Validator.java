package com.example.transportation;

import android.widget.EditText;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import org.json.JSONObject;


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




   // }
}
