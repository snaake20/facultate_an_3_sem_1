package ro.ase.semdam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class BNRActivity extends AppCompatActivity {

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bnr);

        Log.e("lifecycle", "Apel metoda onCreate()");

        TextView tvData = findViewById(R.id.tvDataCurs);
        EditText etEUR = findViewById(R.id.editTextEUR);
        EditText etUSD = findViewById(R.id.editTextUSD);
        EditText etGBP = findViewById(R.id.editTextGBP);
        EditText etXAU = findViewById(R.id.editTextXAU);

        Button btnAfisare = findViewById(R.id.btnShow);
        btnAfisare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEUR.setText("4.97");
                etUSD.setText("4.55");
                etGBP.setText("5.46");
                etXAU.setText("235.67");
                Toast.makeText(getApplicationContext(),
                        "Curs afisat cu succes!", Toast.LENGTH_LONG).show();

                CursValutar cursValutar = new CursValutar("24/10/2023", etEUR.getText().toString(),
                        etUSD.getText().toString(),
                        etGBP.getText().toString(),
                        etXAU.getText().toString());

                Intent intent = new Intent(getApplicationContext(), AfisareBNRActivity.class);
                intent.putExtra("curs", cursValutar);
                startActivity(intent);
            }
        });

        TextView tvExtract = findViewById(R.id.tvExtract);
        Button btnExtract = findViewById(R.id.btnExtract);
        btnExtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Network network = new Network()
                {
                    @Override
                    protected void onPostExecute(InputStream inputStream) {

                        //Toast.makeText(getApplicationContext(), Network.rezultat, Toast.LENGTH_LONG).show();
                        //tvExtract.setText(Network.rezultat);
                        tvData.setText(cv.getDataCurs());
                        etEUR.setText(cv.getCursEUR());
                        etUSD.setText(cv.getCursUSD());
                        etGBP.setText(cv.getCursGBP());
                        etXAU.setText(cv.getCursXAU());
                    }
                };
                try {
                    network.execute(new URL("https://bnr.ro/nbrfxrates.xml"));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("lifecycle", "Apel metoda onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("lifecycle", "Apel metoda onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("lifecycle", "Apel metoda onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("lifecycle", "Apel metoda onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("lifecycle", "Apel metoda onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("lifecycle", "Apel metoda onDestroy()");
    }
}