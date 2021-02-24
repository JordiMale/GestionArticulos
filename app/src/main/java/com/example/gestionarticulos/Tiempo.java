package com.example.gestionarticulos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Tiempo extends AppCompatActivity {

    String Poner_Ubicacion = "";
    TextView Pon_Ciud;
    TextView Poner_Grados;
    TextView Poner_Tiempo;
    ImageView ImageTi;
    TextView Horario;
    TextView Sen_Termica;
    TextView Viento;
    TextView Temp_Min;
    TextView Temp_Max;
    TextView Long;
    TextView Lat;
    TextView Presion;
    TextView Hume;
    TextView Visi;

    String key="e5a9baee8a1b0febc34984498bfe654f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Pon_Ciud  = (TextView)findViewById(R.id.Poner_ciudad);
        Poner_Grados = (TextView)findViewById(R.id.Grados);
        Poner_Tiempo = (TextView)findViewById(R.id.Tiempo_Hace);
        ImageTi = (ImageView)findViewById(R.id.TiempoImagen);
        Horario = (TextView)findViewById(R.id.Horario);
        Sen_Termica = (TextView)findViewById(R.id.Sen_Termica);
        Viento = (TextView)findViewById(R.id.Viento);
        Temp_Min = (TextView)findViewById(R.id.Temp_Min);
        Temp_Max = (TextView)findViewById(R.id.Temp_Max);
        Long = (TextView)findViewById(R.id.Long);
        Lat = (TextView)findViewById(R.id.Lati);
        Presion = (TextView)findViewById(R.id.Presion);
        Hume = (TextView)findViewById(R.id.Humedad);
        Visi = (TextView)findViewById(R.id.Visi);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Poner_Ubicacion = bundle.getString("1234");
        }


        String api = "http://api.openweathermap.org/data/2.5/weather?q=" + Poner_Ubicacion + "&appid=" + key;
        String idioma = "&lang=es";


        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0,10000);

        client.get(this, api, new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                City Json = null;
                JSONObject Ti = null;
                String STRING = new String(responseBody);

                try {
                    Ti = new JSONObject(STRING);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                coord Corden = null;
                clouds Cloudes = null;
                sys Syso = null;
                main Mainn = null;
                wind Windy = null;
                weather Weathere = null;


                try {

                    //Poniendo todas las variables de cada classe en el json.

                    //Classe Coord
                    Corden = new coord(Ti.getJSONObject("coord").getDouble("lon"),
                            Ti.getJSONObject("coord").getDouble("lat"));

                    //Classe clouds
                    Cloudes = new clouds(Ti.getJSONObject("Clouds").getDouble("all"));

                    //Classe sys
                    Syso = new sys(Ti.getJSONObject("sys").getInt("type"),
                            Ti.getJSONObject("sys").getInt("id"),
                            Ti.getJSONObject("sys").getString("country"),
                            Ti.getJSONObject("sys").getInt("sunrise"),
                            Ti.getJSONObject("sys").getInt("sunset"));

                    //Classe Mainn
                    Mainn = new main(Ti.getJSONObject("main").getDouble("temp"),
                            Ti.getJSONObject("main").getDouble("feels_like"),
                            Ti.getJSONObject("main").getDouble("temp_min"),
                            Ti.getJSONObject("main").getDouble("temp_max"),
                            Ti.getJSONObject("main").getDouble("pressure"),
                            Ti.getJSONObject("main").getDouble("humidity"));

                    //Classe wind
                    Windy = new wind(Ti.getJSONObject("wind").getDouble("speed"),
                            Ti.getJSONObject("wind").getDouble("deg"));

                    //Classe wheater
                    Weathere = new weather(Ti.getJSONObject("weather").getInt("id"),
                            Ti.getJSONObject("weather").getString("main"),
                            Ti.getJSONObject("weather").getString("description"),
                            Ti.getJSONObject("weather").getString("few_clouds"),
                            Ti.getJSONObject("weather").getString("icon"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    Json = new City(Ti.getString("base"),
                            Cloudes,
                            Ti.getInt("cod"),
                            Corden,
                            Ti.getInt("dt"),
                            Ti.getInt("id"),
                            Mainn,
                            Ti.getString("name"),
                            Syso,
                            Ti.getInt("timezone"),
                            Ti.getInt("visibility"),
                            Weathere,
                            Windy
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Pon_Ciud.setText(Json.getName());
                Poner_Grados.setText((int) Json.getMain().getTemp());
                Poner_Tiempo.setText(Json.getWeather().getDescription());

                Horario.setText(Json.getTimezone());
                Sen_Termica.setText((int) Json.getMain().getFeels_like());
                Viento.setText((int) Json.getWind().getSpeed());
                Temp_Min.setText((int) Json.getMain().getTemp_min());
                Temp_Max.setText((int) Json.getMain().getTemp_max());
                Long.setText((int) Json.getCoord().getLon());
                Lat.setText((int) Json.getCoord().getLat());
                Presion.setText((int) Json.getMain().getPressure());
                Hume.setText((int) Json.getMain().getHumidity());
                Visi.setText(Json.getVivibility());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String STRING2 = new String(error.getMessage().toString());
                String valor = "No se ha podido recuperar los datos. " + STRING2;
            }
        });

    }
}