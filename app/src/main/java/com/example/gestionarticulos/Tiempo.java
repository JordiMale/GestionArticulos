package com.example.gestionarticulos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;
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

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    City Json = null;

    String key="e5a9baee8a1b0febc34984498bfe654f";

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tulabartiempo, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo);

        Pon_Ciud  = (TextView)findViewById(R.id.Poner_ciudad1);
        Poner_Grados = (TextView)findViewById(R.id.Grados);
        Poner_Tiempo = (TextView)findViewById(R.id.Tiempo_Hace);

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


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Poner_Ubicacion = bundle.getString("1234");
        }

        String idioma = "&lang=es";
        String api = "http://api.openweathermap.org/data/2.5/weather?q=" + Poner_Ubicacion + "&appid=" + key + idioma;



        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0,10000);

        client.get(this, api, new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

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
                    Cloudes = new clouds(Ti.getJSONObject("clouds").getDouble("all"));

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
                    Weathere = new weather(Ti.getJSONArray("weather").getJSONObject(0).getInt("id"),
                            Ti.getJSONArray("weather").getJSONObject(0).getString("main"),
                            Ti.getJSONArray("weather").getJSONObject(0).getString("description"),
                            Ti.getJSONArray("weather").getJSONObject(0).optString("few_clouds", ""),
                            Ti.getJSONArray("weather").getJSONObject(0).optString("icon", ""));

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
                            Ti.optInt("visibility", 0),
                            Weathere,
                            Windy
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Ponemos ciudad
                String Poner_Ciud = Json.getName();
                Pon_Ciud.setText(Poner_Ciud);

                //Convertimos los kel a cel
                double Grados_final = Json.getMain().getTemp() -273.15;
                String Grados_Finall = String.valueOf(Grados_final);
                String Gra = Grados_Finall.charAt(0) + "" + Grados_Finall.charAt(1) + "º";
                Poner_Grados.setText(Gra);

                //Ponemos el tiempo
                Poner_Tiempo.setText(Json.getWeather().getDescription());

                //Cojemos la fecha del movil
                Date d=new Date();
                SimpleDateFormat data = new SimpleDateFormat("d MMMM ");
                String DataCom = data.format(d);
                Horario.setText(DataCom);

                //Hacemos igual que los grados
                double Sen_Ter = Json.getMain().getFeels_like() - 273.15;
                String Sen_Ter_Final = String.valueOf(Sen_Ter);
                String Sen_Ter_Stegen = Sen_Ter_Final.charAt(0) + "" + Sen_Ter_Final.charAt(1) + "º";
                Sen_Termica.setText(Sen_Ter_Stegen);

                Viento.setText("S " + String.valueOf(Json.getWind().getSpeed()) + " km/h");

                double Grados_Min =Json.getMain().getTemp_min() - 273.15;
                String Grados_Min_Final = String.valueOf(Grados_Min);
                String Gra2 = Grados_Min_Final.charAt(0) + "" + Grados_Min_Final.charAt(1) + "º";
                Temp_Min.setText(Gra2);

                double Grados_Max =Json.getMain().getTemp_max() - 273.15;
                String Grados_Max_Final = String.valueOf(Grados_Max);
                String Gra3 = Grados_Max_Final.charAt(0) + "" + Grados_Max_Final.charAt(1) + "º";
                Temp_Max.setText(Gra3);
/*
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            ImageTi = (ImageView)findViewById(R.id.imageView2);
                            URL url = new URL("https://openweathermap.org/img/w/" + Json.getWeather().getIcon() +".png");
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            ImageTi.setImageBitmap(bmp);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();

 */

                Long.setText(String.valueOf(Json.getCoord().getLon()));
                Lat.setText(String.valueOf(Json.getCoord().getLat()));
                Presion.setText(String.valueOf(Json.getMain().getPressure()) + " hPa");
                Hume.setText(String.valueOf(Json.getMain().getHumidity()) + "%");
                Visi.setText(String.valueOf(Json.getVivibility() + " m"));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String STRING2 = new String(error.getMessage().toString());
                String valor = "No se ha podido recuperar los datos. " + STRING2;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.IrcasaTiempo:
                Intent intent = new Intent(Tiempo.this, MainActivity.class);
                startActivity(intent);
                finish();

                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }
}