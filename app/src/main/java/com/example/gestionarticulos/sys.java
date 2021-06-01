package com.example.gestionarticulos;

public class sys {

    int id;
    int type;
    int sunrise;
    String country;
    int sunset;

    public sys(int type, int id, String country, int sunrise, int sunset){

        this.type=type;
        this.id=id;
        this.country=country;
        this.sunrise=sunrise;
        this.sunset=sunset;

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }
}
