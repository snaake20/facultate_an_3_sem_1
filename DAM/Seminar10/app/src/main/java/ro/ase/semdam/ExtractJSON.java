package ro.ase.semdam;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ExtractJSON extends AsyncTask<URL, Void, String> {

    public List<Masina> masinaListJSON = new ArrayList<>();
    @Override
    protected String doInBackground(URL... urls) {

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) urls[0].openConnection();
            connection.setRequestMethod("GET");

            InputStream ist = connection.getInputStream();

            //var 2 - prelucrare String
            InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br = new BufferedReader(isr);
            String linie = null;
            String rezultat = "";
            while((linie=br.readLine())!=null)
                rezultat+=linie;

            parsareJSON(rezultat);

            return rezultat;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void parsareJSON(String jsonStr)
    {
        if(jsonStr!=null)
        {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonStr);
                JSONArray masini = jsonObject.getJSONArray("masini");
                for(int i=0;i<masini.length();i++)
                {
                    JSONObject masina = masini.getJSONObject(i);

                    String marca = masina.getString("Marca");
                    Date dataFabricatiei = new Date(masina.getString("DataFabricatiei"));
                    float pret = Float.parseFloat(masina.getString("Pret"));
                    String culoare = masina.getString("Culoare");
                    String motorizare = masina.getString("Motorizare");

                    Masina masina1 = new Masina(marca, dataFabricatiei, pret, culoare, motorizare);
                    masinaListJSON.add(masina1);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        else
            Log.e("parsareJSON", "JSON este null!");
    }
}
