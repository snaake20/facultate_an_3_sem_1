package dam.example.seminar_9_dam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView tvLoc,tvTempCrt,tvTempMin,tvTempMax;
    Spinner spOras;
    ImageView ivNebunoliztate;
    private void updateSpinner(String localitate){
        new Thread(() -> {
            Prognoza prognoza = WService.obtinePrognoza(localitate);
            try {
                prognoza.nebulozitate = obtineBitmap(prognoza.cod_meteo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            runOnUiThread(() -> {
                // COD EXECUTAT IN FIRUL PRINCIPAL
                //tvLoc.setText(prognoza.localitate);
                tvTempCrt.setText("Temperatura curenta este: " + String.valueOf(prognoza.tempCrt));
                tvTempMin.setText("Temperatura minima este: " + String.valueOf(prognoza.tempMin));
                tvTempMax.setText("Temperatura maxima este: " + String.valueOf(prognoza.tempMax));
                ivNebunoliztate.setImageBitmap(prognoza.nebulozitate);
            });

        }).start();
    }
    private Bitmap obtineBitmap(int cod_meteo) throws IOException, JSONException {
        Bitmap bitmap = null;
        Resources res = this.getResources();

        InputStream inputStream = res.openRawResource(R.raw.coduri);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONObject jsonObject = new JSONObject(json);
        String url = jsonObject.getJSONObject(String.valueOf(cod_meteo)).getJSONObject("day").getString("image");

        URL url1 = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) url1.openConnection();
        connection.connect();
        InputStream inputStream1 = connection.getInputStream();
        bitmap = BitmapFactory.decodeStream(inputStream1);
        inputStream1.close();
        return bitmap;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tvLoc = findViewById(R.id.oras);
        spOras = findViewById(R.id.spOrase);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.orase_array, android.R.layout.simple_spinner_item
                );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOras.setAdapter(arrayAdapter);

        tvTempCrt = findViewById(R.id.tvTempCrt);
        tvTempMin = findViewById(R.id.tvTempMin);
        tvTempMax = findViewById(R.id.tvTempMax);
        ivNebunoliztate = findViewById(R.id.nebulozitate);

        spOras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelectat = parent.getItemAtPosition(position).toString();
                updateSpinner(itemSelectat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
//        Executor myExecutor = Executors.newSingleThreadExecutor();
//        Handler myHandler = new Handler(Looper.myLooper());
//        myExecutor


    }
}