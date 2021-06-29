//package com.T_Y.model;
//
//import com.T_Y.controller.ApiKey;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//
//////////////////////NOT IN USE ATM///////////////////////////
//public class Repository2 {
//    private static String[] parseResult(String result1) {
//        String[] arrOfStr = result1.split(":", 35);
//        String[] arrOfStr2 = new String[5];
//        arrOfStr2[0] = arrOfStr[17];//gets the date
//        arrOfStr2[1] = arrOfStr[8];//gets a general description of weather
//        arrOfStr2[2] = arrOfStr[24];//minimum degrees
//        arrOfStr2[3] = arrOfStr[28];//maximum degrees
//        arrOfStr2[4] = arrOfStr[32];//icon number
//        arrOfStr2[0] = arrOfStr2[0].substring(1, 11);//trimming a perfect string for date
//        arrOfStr2[1] = arrOfStr2[1].substring(1, arrOfStr2[1].indexOf(",") - 1);//trimming a perfect string for headline
//        arrOfStr2[2] = arrOfStr2[2].substring(0, arrOfStr2[2].indexOf(",") - 2);//trimming a perfect string for min degrees
//        arrOfStr2[3] = arrOfStr2[3].substring(0, arrOfStr2[3].indexOf(","));//trimming a perfect string for max degrees
//        arrOfStr2[4] = (arrOfStr2[4].substring(0, 1));//trimming the icon number
//        return arrOfStr2;
//    }
//
//    public static ForecastResult doHttpGet(City ct) {
//        //http://dataservice.accuweather.com/forecasts/v1/daily/1day/348735?apikey=<com.shar.controller.ApiKey>
//        //http://dataservice.accuweather.com/forecasts/v1/daily/1day/<CITYID>?apikey=<com.shar.controller.ApiKey>
//
//        String url = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/" + ct.getCityCode() + "?apikey=" + ApiKey.getApiKey();
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpGet get = new HttpGet(url);
//        CloseableHttpResponse resp = null;
//
//        try {
//            resp = client.execute(get);
//            HttpEntity entity = resp.getEntity();
//            String result1 = EntityUtils.toString(entity);
//            try {
//                String[] finalResult = parseResult(result1);
//                ForecastResult output = new ForecastResult(finalResult);
//                return output;
//
//
//            } catch (Exception e1) {
//               e1.printStackTrace();;
//            }
//        } catch (IOException ioe) {
//            System.err.println("Something went wrong and we couldnt get the information you requested: ");
//            ioe.printStackTrace();
//        } catch (Exception e) {
//            System.err.println("Unknown Error: ");
//            e.printStackTrace();
//        }
//        throw new ArithmeticException("cant find city results");
//    }
//
//}
