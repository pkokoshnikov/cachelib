package com.kokoshnikov.pavel.cache;

public abstract class DataCache {
    public static String fileFormat = "cacheobj";
    public static String cacheDir = "cachedir";

    public static String getFilePrefix() {
        return cacheDir + "\\" + fileFormat;
    }

    public abstract Cacheable getData(Integer Id);

    public abstract void putData(Cacheable putOb, Integer substituteId);
}
