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

//import static sun.jvm.hotspot.debugger.win32.coff.DebugVC50X86RegisterEnums.TAG;

public class Repository {


//    private static String[] parseResult(String result1) {
//        String[] arrOfStr = result1.split(":", 35);
//        String[] arrOfStr2 = new String[5];
//        arrOfStr2[0] = arrOfStr[1];//gets the date
//        arrOfStr2[1] = arrOfStr[6];//gets a general description of weather
//        arrOfStr2[2] = arrOfStr[13];//minimum degrees
//        arrOfStr2[3] = arrOfStr[7];//icon number
//        arrOfStr2[4] = arrOfStr[5];//time
//        arrOfStr2[0] = arrOfStr2[0].substring(1, 11);//trimming a perfect string for date
//        arrOfStr2[1] = arrOfStr2[1].substring(1, arrOfStr2[1].indexOf(",") - 1);//trimming a perfect string for headline
//        arrOfStr2[2] = arrOfStr2[2].substring(0, arrOfStr2[2].indexOf(",") - 2);//trimming a perfect string for min degrees
//        arrOfStr2[3] = (arrOfStr2[3].substring(0, 1));//trimming the icon number
//        arrOfStr2[4] = arrOfStr2[4].substring(0, arrOfStr2[4].indexOf(","));//trimming a perfect string for the time in millisec
//        return arrOfStr2;
//    }

    private ForecastResult parseForecastJson(String jsonData) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonData);
        JSONObject row = jsonArray.getJSONObject(0);
        String LocalObservationDateTime = row.getString("LocalObservationDateTime");

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

        return arrOfHangouts;
    }

//    private static HangoutsResult[] HangoutParseResult(String result1) {
//        String[] arrOfStr = result1.split("}", 0);
//        String[] arrOfStr2 = new String[12];
//        arrOfStr2[0] = arrOfStr[0].substring(122);
//        arrOfStr2[1] = arrOfStr[18].substring(135);
//        arrOfStr2[2] = arrOfStr[19].substring(132);
//        arrOfStr2[3] = arrOfStr[arrOfStr.length - 3].substring(137);
//        arrOfStr2[4] = arrOfStr[2].substring(124);
//        arrOfStr2[5] = arrOfStr[6].substring(129);
//        arrOfStr2[6] = arrOfStr[7].substring(123);
//        arrOfStr2[7] = arrOfStr[4].substring(123);
//        arrOfStr2[8] = arrOfStr[5].substring(126);
//        arrOfStr2[9] = arrOfStr[8].substring(130);
//        arrOfStr2[10] = arrOfStr[9].substring(132);
//        arrOfStr2[11] = arrOfStr[11].substring(130);
//
//
//        HangoutsResult[] arrOfHangouts = new HangoutsResult[12];
//        for (int i = 0; i < 12; i++) {
//            HangoutsResult temp = new HangoutsResult(arrOfStr2[i].substring(23, arrOfStr2[i].indexOf(",", 24) - 1), arrOfStr2[i].substring(7, arrOfStr2[0].indexOf(",") - 1));
//            arrOfHangouts[i] = temp;
//        }
//        arrOfHangouts[0].setHeadline(arrOfHangouts[0].getHeadline().substring(1));//first cell of arr needed special attention
//        arrOfHangouts[0].setRating(arrOfHangouts[0].getRating()+"0");//first cell of arr needed special attention
//        return arrOfHangouts;
//    }

//    private boolean resultHushCheck(City ct) {
//        if (CityCodeHushMap.getInstance().getForecasts().get(ct.getCityCode()) != null) {
//            ct.setResult(CityCodeHushMap.getInstance().getForecasts().get(ct.getCityCode()));
//            return true;
//        }
//        return false;
//    }

    private boolean resultCacheCheck(City ct) {
        if (CityAlgoImpl.getInstance().getForecasts().getElement(ct.getCityCode()) != null) {
//            ct.setResult(CityCodeHushMap.getInstance().getForecasts().get(ct.getCityCode()));
            ct.setResult(CityAlgoImpl.getInstance().getForecasts().getElement(ct.getCityCode()));
            return true;
        }
        return false;
    }

//    private boolean hangoutHushCheck(City ct) {
////        if (CityCodeHushMap.getInstance().getHangouts().get(ct.getCityCode()) != null) {
//        if (CityAlgoImpl.getInstance().getHangouts().getElement(ct.getCityCode()) != null) {
////            ct.setResult(CityCodeHushMap.getInstance().getForecasts().get(ct.getCityCode()));
//            ct.setResult(CityAlgoImpl.getInstance().getForecasts().getElement(ct.getCityCode()));
//
//            return true;
//        }
//        return false;
//    }

    private boolean hangoutCacheCheck(City ct) {
//        if (CityCodeHushMap.getInstance().getHangouts().get(ct.getCityCode()) != null) {
        if (CityAlgoImpl.getInstance().getHangouts().getElement(ct.getCityCode()) != null) {
//            ct.setResult(CityCodeHushMap.getInstance().getForecasts().get(ct.getCityCode()));
            ct.setResult(CityAlgoImpl.getInstance().getForecasts().getElement(ct.getCityCode()));

            return true;
        }
        return false;
    }

//    private void updateCityHangoutHush(City ct, HangoutsResult[] resultsArr) {
////        CityCodeHushMap.getInstance().getHangouts().put(ct.getCityCode(), resultsArr);
//        CityAlgoImpl.getInstance().getHangouts().putElement(ct.getCityCode(), resultsArr);
//    }

    private void updateCityHangoutCache(City ct, HangoutsResult[] resultsArr) {
//        CityCodeHushMap.getInstance().getHangouts().put(ct.getCityCode(), resultsArr);
        CityAlgoImpl.getInstance().getHangouts().putElement(ct.getCityCode(), resultsArr);
    }

//    private void updateCityForecastsHush(City ct) {
////        CityCodeHushMap.getInstance().getForecasts().put(ct.getCityCode(), ct.getResult());
//        CityAlgoImpl.getInstance().getForecasts().putElement(ct.getCityCode(), ct.getResult());
//    }

    private void updateCityForecastsCache(City ct) {
//        CityCodeHushMap.getInstance().getForecasts().put(ct.getCityCode(), ct.getResult());
        CityAlgoImpl.getInstance().getForecasts().putElement(ct.getCityCode(), ct.getResult());
    }
    public ForecastResult getCityForecast(City ct) {

        String url = "http://dataservice.accuweather.com/currentconditions/v1/" + ct.getCityCode() + "?apikey=" + ApiKey.getApiKey();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse resp = null;
        if (this.resultCacheCheck(ct)) {
//            return CityCodeHushMap.getInstance().getForecasts().get(ct.getCityCode());
            return CityAlgoImpl.getInstance().getForecasts().getElement(ct.getCityCode());


        }
        try {
            resp = client.execute(get);
            HttpEntity entity = resp.getEntity();
            String result1 = EntityUtils.toString(entity);
            try {
//                String[] finalResult = parseResult(result1);
//                String[] finalResult =
//                ForcastResult output = new ForcastResult(finalResult);
                ForecastResult output = parseForecastJson(result1);
                ct.setResult(output);
                this.updateCityForecastsCache(ct);
                return output;


            } catch (Exception e1) {
                e1.printStackTrace();;
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
//            return CityCodeHushMap.getInstance().getForecasts().get(ct.getCityCode());
            return CityAlgoImpl.getInstance().getForecasts().getElement(ct.getCityCode());

        }
        try {

            resp = client.execute(get);
            HttpEntity entity = resp.getEntity();
            String result1 = EntityUtils.toString(entity);
            try {
//              HangoutsResult[] finalResult = HangoutParseResult(result1);
                ForecastResult output = ct.getResult();
                HangoutsResult[] finalHangouts = parseHangoutsJson(result1);

                output.setHangoutsResultsArr(finalHangouts);
                ct.setResult(output);
                this.updateCityHangoutCache(ct, finalHangouts);
                return output;


            } catch (Exception e1) {
                e1.printStackTrace();;
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

        String url = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + ApiKey.getApiKey();
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse resp = null;


//        if (checkCityCodeHush(ct)) {
//            return null;
//        }

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
//                this.updateCityCodeHush(ct);
                this.updateCityCodeCache(ct);


                //now that we got city code we can search for a forecast

//                this.getCityForecast(ct);
//                this.getHangouts(ct);
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

//    private boolean checkCityCodeHush(City ct) {
//        if (CityCodeHushMap.getInstance().getCityCodes().get(ct.getCityName()) != null) {
//            ct.setCityCode(CityCodeHushMap.getInstance().getCityCodes().get(ct.getCityName()));
//            return true;
//        }
//        return false;
//    }

    private boolean checkCityCodeCache(City ct) {
        if (CityAlgoImpl.getInstance().getCityCodes().getElement(ct.getCityName()) != null) {
            ct.setCityCode(CityAlgoImpl.getInstance().getCityCodes().getElement(ct.getCityName()));
            return true;
        }
        return false;
    }

//    private void updateCityCodeHush(City ct) {
//        Timer timer = new Timer();
//        long timeout = 1000_000;
//        CityCodeHushMap.getInstance().getCityCodes().put(ct.getCityName(), ct.getCityCode());
//
//    }

    private void updateCityCodeCache(City ct) {
        Timer timer = new Timer();
        long timeout = 1000_000;
        CityAlgoImpl.getInstance().getCityCodes().putElement(ct.getCityName(), ct.getCityCode());

    }
    private void parseCityCode(String result1, City ct) {
        String[] arrOfStr = result1.split(":", 4);
        String temp = arrOfStr[2];
        temp = temp.substring(1, temp.indexOf(",") - 1);
        ct.setCityCode(temp);


    }

}




