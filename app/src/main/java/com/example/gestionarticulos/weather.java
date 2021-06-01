package com.example.gestionarticulos;

public class weather {
    int id;
    String main;
    String description;
    String few_clouds;
    String icon;

    public weather(int id, String main, String description, String few_clouds, String icon){
        this.id=id;
        this.main=main;
        this.description=description;
        this.few_clouds=few_clouds;
        this.icon=icon;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFew_clouds() {
        return few_clouds;
    }

    public void setFew_clouds(String few_clouds) {
        this.few_clouds = few_clouds;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
