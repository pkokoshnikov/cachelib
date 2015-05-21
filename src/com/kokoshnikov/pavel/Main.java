package com.kokoshnikov.pavel;

import java.util.Scanner;

import com.kokoshnikov.pavel.cache.Cache;
import com.kokoshnikov.pavel.cache.CacheException;
import com.kokoshnikov.pavel.cache.CacheStatistic;
import com.kokoshnikov.pavel.cache.Cacheable;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello! This simple program shows a work of my cachelib");
        Cache cache = Cache.getInstance();
        cache.setFactoryObjects(new CachingFactory());
        System.out.println("+----------------------------------------------------------+");
        System.out.println("Test. 1000 operations get from cache \nStart");
        int i = 0;
        while (i < 1000) {
            i++;
            Cacheable obj = null;
            try {
                int r = (int) (40 * Math.random());
                obj = cache.getObj(r);
            } catch (CacheException e) {
                System.out.println("Error" + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("End");
        CacheStatistic cacheStatistic = cache.getStatistic();
        System.out.println("Count of a cache hit level 1 : " + cacheStatistic.getHitLevel1());
        System.out.println("Count of a cache hit level 2 : " + cacheStatistic.getHitLevel2());
        System.out.println("Count of a cache miss        : " + cacheStatistic.getHitLevel2());
        System.out.println("Test completed");
        System.out.println("+----------------------------------------------------------+");
        System.out.println("Information about current state of cache object");
        System.out.println(cache);

        System.out.println("\nNow you can enter id. Cache returns object for this id. \nPlease enter Id or q for exit");
        System.out.println("+----------------------------------------------------------+");
        Scanner scanner = new Scanner(System.in);

        cache.setTrace(true);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("q")) {
                break;
            }
            try {
                int id = Integer.parseInt(line);

                CachingObject obj;
                try {
                    obj = (CachingObject) cache.getObj(id);
                    System.out.println(obj.getNameObject());
                    System.out.println(cache.getInformation());
                } catch (CacheException e) {
                    System.out.println("Error" + e.getMessage());
                    e.printStackTrace();
                }
            } catch (NumberFormatException e) {
                System.out.println("This is not a number. Please reenter id");
            }
        }
        cache.clearCacheFolder();
    }

}
