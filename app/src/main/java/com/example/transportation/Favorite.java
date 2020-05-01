package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Favorite extends AppCompatActivity {


    private List<String>[] list = new List[10];
    private ListView bookList;
    private int trueLength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // btGet = findViewById(R.id.btGet);
        bookList = findViewById(R.id.bookList);


        signIn();

    }

    public void signIn() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        String URL = "https://aqueous-island-97232.herokuapp.com/api/wishlist/" + getIntent().getStringExtra("user");

        // Get me all the books
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                            Log.d("log1","from fav " + response.toString());
                            // Loop through the array elements
                            for(int i = 0; i < response.length(); i++){
                                // Get current json object
                                JSONObject bookJSON = response.getJSONObject(i);
                                List<String> book = new ArrayList<>();

                                // Get the current student (json object) data
                                book.add(bookJSON.getString("title"));


                                list[i]= book;
                                trueLength++;

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

        List<String> l1 = new ArrayList<>();
        // Filters: Display titles only
        for(int i=0; i <trueLength; i++){
            l1.add(list[i].get(0));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, l1);
        bookList.setAdapter(arrayAdapter);

        scrollMyListViewToBottom();

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
        menu.findItem(R.id.menu_fav).setVisible(false);

        return true;
    }
}
