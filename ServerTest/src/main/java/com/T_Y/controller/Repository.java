package com.T_Y.controller;


import com.T_Y.model.City;
import com.T_Y.model.ForecastResult;
import com.T_Y.model.HangoutsResult;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Timer;


public class Repository {

    private ForecastResult parseForecastJson(String jsonData) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonData);
        JSONObject row = jsonArray.getJSONObject(0);

        JSONObject temperature = row.getJSONObject("Temperature");
        JSONObject metric = temperature.getJSONObject("Metric");
        String value = String.valueOf(metric.getDouble("Value"));
        ForecastResult currentWeather = new ForecastResult();
        currentWeather.setTime(row.getLong("EpochTime"));
        currentWeather.setIconNumber(String.valueOf(row.getDouble("WeatherIcon")));
        currentWeather.setHeadline(row.getString("WeatherText"));
        currentWeather.setTemperature(value);

        return currentWeather;
    }

    private HangoutsResult[] parseHangoutsJson(String jsonData) throws JSONException {
        HangoutsResult[] arrOfHangouts = new HangoutsResult[43];
        HangoutsResult[] finalArrOfHangouts = new HangoutsResult[12];
        JSONArray jsonArray = new JSONArray(jsonData);
        for(int i=0;i<43;i++)
        {
        JSONObject row = jsonArray.getJSONObject(i);

            arrOfHangouts[i]=new HangoutsResult();
            arrOfHangouts[i].setName(row.getString("Name"));
            arrOfHangouts[i].setHeadline(row.getString("Category"));
            arrOfHangouts[i].setRating(String.valueOf(row.getDouble("Value")));
            if(!row.isNull("Link"))
                arrOfHangouts[i].setLink(row.getString("Link"));
        }
        {
            finalArrOfHangouts[0] = arrOfHangouts[0];
            finalArrOfHangouts[1] = arrOfHangouts[2];
            finalArrOfHangouts[2] = arrOfHangouts[4];
            finalArrOfHangouts[3] = arrOfHangouts[5];
            finalArrOfHangouts[4] = arrOfHangouts[6];
            finalArrOfHangouts[5] = arrOfHangouts[7];
            finalArrOfHangouts[6] = arrOfHangouts[13];
            finalArrOfHangouts[7] = arrOfHangouts[9];
            finalArrOfHangouts[8] = arrOfHangouts[10];
            finalArrOfHangouts[9] = arrOfHangouts[11];
            finalArrOfHangouts[10] = arrOfHangouts[12];
            finalArrOfHangouts[11] = arrOfHangouts[16];

            return finalArrOfHangouts;
        }
    }

    private boolean resultCacheCheck(City ct) {
        if (CityAlgoImpl.getInstance().getForecasts().getElement(ct.getCityCode()) != null) {
            ct.setResult(CityAlgoImpl.getInstance().getForecasts().getElement(ct.getCityCode()));
            return true;
        }
        return false;
    }

    private boolean hangoutCacheCheck(City ct) {
        if (CityAlgoImpl.getInstance().getHangouts().getElement(ct.getCityCode()) != null) {
            ct.setResult(CityAlgoImpl.getInstance().getForecasts().getElement(ct.getCityCode()));

            return true;
        }
        return false;
    }

    private void updateCityHangoutCache(City ct, HangoutsResult[] resultsArr) {
        CityAlgoImpl.getInstance().getHangouts().putElement(ct.getCityCode(), resultsArr);
    }

    private void updateCityForecastsCache(City ct) {
        CityAlgoImpl.getInstance().getForecasts().putElement(ct.getCityCode(), ct.getResult());
    }
    public ForecastResult getCityForecast(City ct) {

        String url = "http://dataservice.accuweather.com/currentconditions/v1/" + ct.getCityCode() + "?apikey=" + ApiKey.getApiKey();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse resp = null;
        if (this.resultCacheCheck(ct)) {
            return CityAlgoImpl.getInstance().getForecasts().getElement(ct.getCityCode());


        }
        try {
            resp = client.execute(get);
            HttpEntity entity = resp.getEntity();
            String result1 = EntityUtils.toString(entity);
            try {
                ForecastResult output = parseForecastJson(result1);
                ct.setResult(output);
                this.updateCityForecastsCache(ct);
                return output;


            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (IOException ioe) {
            System.err.println("Something went wrong and we couldn't get the information you requested: ");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unknown Error: ");
            e.printStackTrace();
        }
        throw new ArithmeticException("cant find city results");
    }

    public ForecastResult getHangouts(City ct) {

        String url = "http://dataservice.accuweather.com/indices/v1/daily/1day/" + ct.getCityCode() + "/groups/1" + "?apikey=" + ApiKey.getApiKey();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse resp = null;
        if (this.hangoutCacheCheck(ct)) {
            return CityAlgoImpl.getInstance().getForecasts().getElement(ct.getCityCode());

        }
        try {

            resp = client.execute(get);
            HttpEntity entity = resp.getEntity();
            String result1 = EntityUtils.toString(entity);
            try {
                ForecastResult output = ct.getResult();
                HangoutsResult[] finalHangouts = parseHangoutsJson(result1);

                output.setHangoutsResultsArr(finalHangouts);
                ct.setResult(output);
                this.updateCityHangoutCache(ct, finalHangouts);
                return output;


            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (IOException ioe) {
            System.err.println("Something went wrong and we couldn't get the information you requested: ");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unknown Error: ");
            e.printStackTrace();
        }
        throw new ArithmeticException("cant find city results");
    }

    public boolean cityCodeSearch(City ct) throws UnsupportedEncodingException {

        String url = "http://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey=" + ApiKey.getApiKey();

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse resp = null;


                if (checkCityCodeCache(ct)) {
            return true;
        }
        url = url + "&q=" + URLEncoder.encode(ct.getCityName(), StandardCharsets.UTF_8.name());
        try {
            HttpGet get = new HttpGet(url);
            resp = client.execute(get);
            HttpEntity entity = resp.getEntity();
            String result1 = EntityUtils.toString(entity);
            try {
                this.parseCityCode(result1, ct);
                this.updateCityCodeCache(ct);



                return true;

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (IOException ioe) {
            System.err.println("Something went wrong and we couldn't get the information you requested: ");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unknown Error: ");
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkCityCodeCache(City ct) {
        if (CityAlgoImpl.getInstance().getCityCodes().getElement(ct.getCityName()) != null) {
            ct.setCityCode(CityAlgoImpl.getInstance().getCityCodes().getElement(ct.getCityName()));
            return true;
        }
        return false;
    }

    private void updateCityCodeCache(City ct) {
        Timer timer = new Timer();
        long timeout = 1000_000;
        CityAlgoImpl.getInstance().getCityCodes().putElement(ct.getCityName(), ct.getCityCode());

    }

    private void parseCityCode(String jsonData,City ct) {

        JSONArray jsonArray = new JSONArray(jsonData);
        JSONObject row = jsonArray.getJSONObject(0);
        String cityCodeUpdated = row.getString("Key");
        ct.setCityCode(cityCodeUpdated);


    }

}




