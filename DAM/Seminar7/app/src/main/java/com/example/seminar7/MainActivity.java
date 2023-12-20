package com.example.seminar7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ArrayList<CustomItem> items = new ArrayList<CustomItem>();
    Executor executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper()); // or new Handler(Looper.myLooper());
    ListView listView;
    ItemAdapter adapter;

    protected void initItems() {
        items.add(new CustomItem("https://csie.ase.ro/wp-content/uploads/2020/10/csie3.jpg", null, "nu am idee"));
        items.add(new CustomItem("https://csie.ase.ro/wp-content/uploads/2020/10/csie5.jpg", null, "ce fac"));
        items.add(new CustomItem("https://csie.ase.ro/wp-content/uploads/2020/10/csie4.jpg", null, "acum"));
        items.add(new CustomItem("https://csie.ase.ro/wp-content/uploads/2020/10/csie5.jpg", null, "acum tttttt"));

    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void downloadAllImages() {
        items.forEach((item)-> {
            Runnable r = () -> {
                Bitmap bitmap = downloadImage(item.getUrl());
                item.setImg(bitmap);
                handler.post(() -> {
                    adapter.notifyDataSetChanged();
                });
            };
            executor.execute(r);
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Anunturi");
        setSupportActionBar(toolbar);

        initItems();
        downloadAllImages();

        listView = findViewById(R.id.listview);
        adapter = new ItemAdapter(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
    }
}