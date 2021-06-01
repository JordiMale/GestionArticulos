package com.example.gestionarticulos;

public class City {
    String base;
    clouds Clouds;
    int cod;
    coord Coord;
    int dt;
    int id;
    main Main;
    String name;
    sys sys;
    int timezone;
    int visibility;
    weather Weather;
    wind Wind;


    public City(String base, clouds Clouds, int cod,coord Coord,int dt ,int id, main Main, String name, sys sys, int timezone, int visibility, weather Weather, wind Wind){

        this.base=base;
        this.Clouds=Clouds;
        this.cod=cod;
        this.Coord=Coord;
        this.dt=dt;
        this.id=id;
        this.Main=Main;
        this.name=name;
        this.sys=sys;
        this.timezone=timezone;
        this.visibility=visibility;
        this.Weather=Weather;
        this.Wind=Wind;

    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public clouds getClouds() {
        return Clouds;
    }

    public void setClouds(clouds clouds) {
        Clouds = clouds;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public coord getCoord() {
        return Coord;
    }

    public void setCoord(coord coord) {
        Coord = coord;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public main getMain() {
        return Main;
    }

    public void setMain(main main) {
        Main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public sys getSys() {
        return sys;
    }

    public void setSys(sys sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public int getVivibility() {
        return visibility;
    }

    public void setVivibility(int vivibility) {
        this.visibility = vivibility;
    }

    public weather getWeather() {
        return Weather;
    }

    public void setWeather(weather weather) {
        Weather = weather;
    }

    public wind getWind() {
        return Wind;
    }

    public void setWind(wind wind) {
        Wind = wind;
    }
}
