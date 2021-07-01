package com.T_Y.controller;


import com.T_Y.model.ForecastResult;
import com.T_Y.model.HangoutsResult;
import com.hit.algo.CacheImpl;
import com.hit.algo.IAlgoCache;

import java.time.LocalDateTime;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CityAlgoImpl {
    private static final CityAlgoImpl uniqueInstance = new CityAlgoImpl();

    private final  IAlgoCache<String, String> codesCache;
    private final  IAlgoCache<String, ForecastResult> forecastsCache;
    private final  IAlgoCache<String, HangoutsResult[]> hangoutsCache;
    private final ScheduledExecutorService scheduler;

    private CityAlgoImpl() {


       codesCache = CacheImpl.createAlgo(CacheImpl.LRU, 5);
        forecastsCache = CacheImpl.createAlgo(CacheImpl.LRU, 5);
        hangoutsCache = CacheImpl.createAlgo(CacheImpl.SECOND_CHANCE, 5);


        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            System.out.println(LocalDateTime.now() +  ": Clearing cache");
            forecastsCache.clear();
            hangoutsCache.clear();
        }, 30, TimeUnit.MINUTES);
    }

    public static CityAlgoImpl getInstance() {
        return uniqueInstance;
    }

    public IAlgoCache<String, String> getCityCodes() {
        return codesCache;
    }

    public IAlgoCache<String, ForecastResult> getForecasts() {
        return forecastsCache;
    }

    public IAlgoCache<String, HangoutsResult[]> getHangouts() {
        return hangoutsCache;
    }

    public void stop() {
        scheduler.shutdownNow();
    }
}
