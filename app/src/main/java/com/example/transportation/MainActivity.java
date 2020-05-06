package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity  extends AppCompatActivity implements AdapterView.OnItemClickListener{
    public static String current_token; // To hold on to the current user's authentication token
    public static String userEmail; // To hold on to the current user's email address


    String URL = "https://aqueous-island-97232.herokuapp.com/api/books/";

    private List<String>[] list = new List[100];
    private ListView bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // btGet = findViewById(R.id.btGet);
        bookList = findViewById(R.id.bookList);
        userEmail = getIntent().getStringExtra("user");

        // Get all the books
        signIn();

        // TODO: update/delete this section!!!
        Button btDisplayInfo = findViewById(R.id.btDisplayInfo);
        btDisplayInfo.setOnClickListener((v)-> {
                    Intent displayInfoIntent = new Intent(MainActivity.this, DisplayUserInfo.class);
                    startActivity(displayInfoIntent);
                    finish();
        });
       // TODO: end section to update/delete


    }

    @Override
    public void onRestart(){
        super.onRestart();

        refresh();

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


        // Get me all the books
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{

                            // Loop through the array elements
                            for(int i = 0; i < 100; i++){
                                // Get current json object
                                JSONObject bookJSON = response.getJSONObject(i);
                                List<String> book = new ArrayList<>();

                                // Get the current student (json object) data
                                book.add(bookJSON.getString("title"));
                                book.add(bookJSON.getString("author"));
                                book.add(bookJSON.getString("publication_year"));
                                book.add(bookJSON.getString("publisher"));
                                book.add(bookJSON.getString("image_url_l"));

                                list[i]= book;

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
        for(int i=0; i < list.length; i++){
            l1.add(list[i].get(0));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, l1);
        bookList.setAdapter(arrayAdapter);
        bookList.setOnItemClickListener(this);

        scrollMyListViewToBottom();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("log1", "Clicked ... "+list[position].toString());
        Bundle extras = new Bundle();

        extras.putString("title",list[position].get(0));
        extras.putString("author",list[position].get(1));
        extras.putString("year",list[position].get(2));
        extras.putString("publisher",list[position].get(3));
        extras.putString("img",list[position].get(4));

        // We should be getting it from LogIn intent
        extras.putString("user", userEmail);



        Log.d("log1", "Passed ... "+ userEmail);
        Intent intent;
        intent = new Intent(this, BookView.class);
        intent.putExtras(extras);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refresh();
                break;
            case R.id.menu_fav:
                onWishlist();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onWishlist() {
        Intent intent;
        intent = new Intent(this, Favorite.class);

        // Should be getting it from LogIn
        //String userEmail = "test@gmail.com"; // added a static variable to top of this class
        intent.putExtra("user", userEmail);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }
}
