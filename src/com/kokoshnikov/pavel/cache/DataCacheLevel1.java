package com.kokoshnikov.pavel.cache;

import java.util.TreeMap;

/**
 * @author Kokoshnikov Pavel
 *         <p/>
 *         This class provides access to the data for cache of level 1
 */
class DataCacheLevel1 extends DataCache {
    private TreeMap<Integer, Cacheable> map;

    DataCacheLevel1() {
        map = new TreeMap<Integer, Cacheable>();
    }

    @Override
    public Cacheable getData(Integer Id) {

        return map.get(Id);
    }

    @Override
    public void putData(Cacheable putOb, Integer substituteId) {
        if (substituteId == null) map.put(putOb.getObjectId(), putOb);
        else {
            map.remove(substituteId);
            map.put(putOb.getObjectId(), putOb);
        }
    }
}
