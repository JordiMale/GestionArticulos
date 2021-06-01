package com.example.gestionarticulos;

public class coord {
    double lon;
    double lat;

    public coord(double longitud, double latitud){

        this.lon=longitud;
        this.lat=latitud;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

}
