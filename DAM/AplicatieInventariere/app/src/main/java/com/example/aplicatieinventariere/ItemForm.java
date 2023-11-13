package com.example.aplicatieinventariere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ItemForm extends AppCompatActivity {

    Item item = null;
    TextView tv_url;
    EditText cod, denumire, inventarFaptic, url;

    Executor executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.myLooper());

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

    private void initControls() {
        if (item == null) {
            return;
        }
        cod.setText(String.valueOf(item.getCod()));
        cod.setEnabled(false);
        denumire.setText(item.getDenumire());
        inventarFaptic.setText(String.valueOf(item.getInventarFaptic()));
        url.setText(item.getUrl());
        url.setVisibility(View.INVISIBLE);
        tv_url.setVisibility(View.INVISIBLE);

        executor.execute(() -> {
            Bitmap bp = downloadImage(item.getUrl());
            handler.post(() -> ((ImageView) findViewById(R.id.image)).setImageBitmap(bp));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

        item = (Item) getIntent().getSerializableExtra("item");

        Toolbar toolbar = findViewById(R.id.toolbar_form);
        if (item != null) {
            toolbar.setTitle("Bun: " + item.getDenumire());
        } else {
            toolbar.setTitle("Inregistrare nou bun");
        }
        setSupportActionBar(toolbar);

        cod = findViewById(R.id.cod);
        denumire = findViewById(R.id.denumire);
        inventarFaptic = findViewById(R.id.inventarFaptic);
        url = findViewById(R.id.url);
        tv_url = findViewById(R.id.tv_url);

        initControls();
    }

    public void saveItem(View v) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("item", new Item(Integer.parseInt(String.valueOf(cod.getText())), String.valueOf(denumire.getText()), Integer.parseInt(String.valueOf(inventarFaptic.getText())), String.valueOf(url.getText())));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}