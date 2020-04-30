package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button btGet;
    TextView lbBook;
    String URL = "https://aqueous-island-97232.herokuapp.com/api/books/";
    private ArrayList<String> list = new ArrayList<>();
    private ListView bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // btGet = findViewById(R.id.btGet);
        bookList = findViewById(R.id.bookList);

        list.add("first");

        // Get all the books
        signIn();




    }

    private void scrollMyListViewToBottom() {
        bookList.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                bookList.setSelection(bookList.getCount() - 1);
            }
        });
    }


    public void signIn() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Enter the correct url for your api service site
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                            Log.d("log1",response.toString());
                            // Loop through the array elements
                            for(int i = 0; i < 100; i++){
                                // Get current json object
                                JSONObject book = response.getJSONObject(i);

                                // Get the current student (json object) data
                                String title = book.getString("title");
                                String author = book.getString("author");
                                String year = book.getString("publication_year");

                                list.add(title + " by " + author + " " + year);
                                Log.d("log1",list.get(i));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("log1", "Something went wrong");
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void refresh(){

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        bookList.setAdapter(arrayAdapter);

        scrollMyListViewToBottom();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refresh();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }
}
