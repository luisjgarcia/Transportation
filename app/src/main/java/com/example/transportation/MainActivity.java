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

    Button btGet;
    TextView lbBook;
    String URL = "https://aqueous-island-97232.herokuapp.com/api/books/";

    private List<String>[] list = new List[100];
    private ListView bookList;

    // For login/account activities
    public static boolean isLoggedIn = false;
    final int LOGIN_REQ = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // btGet = findViewById(R.id.btGet);
        bookList = findViewById(R.id.bookList);

        // Get all the books
        signIn();

        // On the first pass, ensure that we are taken to the login page
        if(!isLoggedIn) {
            Intent loginReplyIntent = new Intent(MainActivity.this, LoginScreen.class);
            startActivityForResult(loginReplyIntent, LOGIN_REQ);
        }





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
        for(int i=0; i <100; i++){
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



        Log.d("log1", "Passed ... "+ extras.toString());
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
