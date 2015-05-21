package com.kokoshnikov.pavel.cache;

import java.util.ArrayList;

public abstract class AbstractStrategy {

    private int size;

    protected DataCache dataCache;

    public AbstractStrategy(int size, byte level) throws CacheException {
        this.size = size;
        if (level == 1)
            dataCache = new DataCacheLevel1();
        else if (level == 2) dataCache = new DataCacheLevel2();
        else
            throw new CacheException("Incorrect cache level");
    }

    public int getSize() {
        return size;
    }

    public abstract void put(Cacheable obj);

    public abstract Cacheable get(Integer id);

    public abstract ArrayList<Integer> getCachingIds();
}
