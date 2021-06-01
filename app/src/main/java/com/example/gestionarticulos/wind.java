package com.example.gestionarticulos;

public class wind {
    double speed;
    double deg;

    public wind(double speed, double deg){
        this.speed=speed;
        this.deg=deg;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }
}
