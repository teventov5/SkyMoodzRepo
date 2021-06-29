package com.T_Y.model;


import java.io.Serializable;

public class City implements Serializable {
    private String cityName;
    private String cityCode;
    private ForecastResult result;

    public City(String cityName, String cityCode, ForecastResult result) {
        setCityName(cityName);
        setCityCode(cityCode);
        setResult(result);
    }

    public City(String cityName, String cityCode) {
        setCityName(cityName);
        setCityCode(cityCode);
    }

    public City() {
        setCityName("Holon");
        setCityCode("215838");
    }

    public ForecastResult getResult() {
        return result;
    }

    public void setResult(ForecastResult result) {
        this.result = result;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
