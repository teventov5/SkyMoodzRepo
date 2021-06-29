//package com.T_Y.controller;
//
//
//import com.T_Y.model.ForecastResult;
//import com.T_Y.model.HangoutsResult;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.*;
//
//public class CityCodeHushMap {
//    private static final CityCodeHushMap uniqueInstance = new CityCodeHushMap();
//
//    private final Map<String, String> cityCodes;
//    private final Map<String, ForecastResult> forecasts;
//    private final Map<String, HangoutsResult[]> hangouts;
//
//    private final ScheduledExecutorService scheduler;
//
//    private CityCodeHushMap() {
//        cityCodes = new ConcurrentHashMap<>();
//        forecasts = new ConcurrentHashMap<>();
//        hangouts = new ConcurrentHashMap<>();
//
//        scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.schedule(() -> {
//            System.out.println(LocalDateTime.now() +  ": Clearing cache");
//            forecasts.clear();
//            hangouts.clear();
//        }, 30, TimeUnit.MINUTES);
//    }
//
//    public static CityCodeHushMap getInstance() {
//        return uniqueInstance;
//    }
//
//    public Map<String, String> getCityCodes() {
//        return cityCodes;
//    }
//
//    public Map<String, ForecastResult> getForecasts() {
//        return forecasts;
//    }
//
//    public Map<String, HangoutsResult[]> getHangouts() {
//        return hangouts;
//    }
//
//    public void stop() {
//        scheduler.shutdownNow();
//    }
//}
