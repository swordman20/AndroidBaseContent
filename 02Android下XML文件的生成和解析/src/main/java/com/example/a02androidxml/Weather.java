package com.example.a02androidxml;

/**
 * Created by xwf on 16/5/7.
 */
public class Weather {
    private String id;//city id
    private String name;//city name
    private String pm;//pm2.5指数
    private String wind;//风力
    private String temp;//温度

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pm='" + pm + '\'' +
                ", wind='" + wind + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }
}
