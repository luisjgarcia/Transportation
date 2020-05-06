package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BookView extends AppCompatActivity {

    WebView image;
    ArrayList<String> singleBook = new ArrayList<>();
    String URL = "https://aqueous-island-97232.herokuapp.com/api/wishlist/";
    String user, title;
    TextView message;
    Button btWishList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        image = findViewById(R.id.webImage);
        message = findViewById(R.id.tvMessage);
        btWishList =  findViewById(R.id.wishlist);


        Bundle extras = getIntent().getExtras();

        title = extras.getString("title");
        String author = extras.getString("author");
        String year = extras.getString("year");
        String publisher = extras.getString("publisher");
        String imgURL = extras.getString("img");
        user = extras.getString("user");

        Log.d("log1", title +" "+ author +" "+ year +" "+ publisher +" "+ imgURL);

        image.loadUrl(imgURL);

    }

    public void clickFav(View view) {

        Map<String, String> params = new HashMap<>();
        params.put("user", user);
        params.put("title", title);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                Log.d("log1",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);


        message.setText("Added to your wishlist");
        btWishList.setEnabled(false);

    }

    public void clickBack(View view) {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
