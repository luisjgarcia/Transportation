package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

public class BookView extends AppCompatActivity {

    WebView image;
    ArrayList<String> singleBook = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        image = findViewById(R.id.webImage);


        Bundle extras = getIntent().getExtras();

        String title = extras.getString("title");
        String author = extras.getString("author");
        String year = extras.getString("year");
        String publisher = extras.getString("publisher");
        String imgURL = extras.getString("img");

        Log.d("log1", title + author + year + publisher + imgURL);

        image.loadUrl(imgURL);


    }
}
