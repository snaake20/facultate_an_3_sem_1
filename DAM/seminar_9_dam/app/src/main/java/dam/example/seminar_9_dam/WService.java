package dam.example.seminar_9_dam;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class WService {
    static String url_coord = "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=1&language=en&format=json";

    static String url_prognoza = "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current=temperature_2m,is_day,weather_code&daily=temperature_2m_max,temperature_2m_min&timezone=auto&forecast_days=%d";
    public static Prognoza obtinePrognoza(String localitate) {

        Prognoza prognoza;

        Coordonate coord = obtineCoordonate(localitate);

        prognoza = obtinePrognozaCoord(coord,1);// 1 se refera la zile pentru prognoza
//        prognoza.tempCrt = 7;
//        prognoza.tempMin = 3;
//        prognoza.tempMax = 8;
        prognoza.localitate = localitate;

        return prognoza;
    }
    private static Prognoza obtinePrognozaCoord(Coordonate coord, int i) {
        Prognoza prognoza = new Prognoza();
        try {
            URL url = new URL(String.format(url_prognoza,coord.lat,coord.longit,i));
            String json = new Scanner(url.openStream()).useDelimiter("\\A").next(); // e ca si cum am scrie getUrlconnection
            JSONObject jsonPrognoza = new JSONObject(json);
            //validari, null, index etc..
            prognoza.tempCrt = (float) jsonPrognoza.getJSONObject("current").getDouble("temperature_2m");
            prognoza.tempMax = (float) jsonPrognoza.getJSONObject("daily").getJSONArray("temperature_2m_max").getDouble(0);
            prognoza.tempMin = (float) jsonPrognoza.getJSONObject("daily").getJSONArray("temperature_2m_min").getDouble(0);

            prognoza.cod_meteo = jsonPrognoza.getJSONObject("current").getInt("weather_code");
        }
        catch(IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prognoza;
    }

    private static Coordonate obtineCoordonate(String localitate){
        Coordonate coord = new Coordonate();
        try {
            URL url = new URL(String.format(url_coord, localitate));
            String json = new Scanner(url.openStream()).useDelimiter("\\A").next(); // e ca si cum am scrie getUrlconnection
            JSONObject jsonCoord = new JSONObject(json);
            //validari, null, index etc..
            coord.lat = jsonCoord.getJSONArray("results").getJSONObject(0).getDouble("latitude");
            coord.longit = jsonCoord.getJSONArray("results").getJSONObject(0).getDouble("longitude");
        }
        catch(IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return coord;
    }
}
