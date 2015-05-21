package com.kokoshnikov.pavel.cache;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Kokoshnikov Pavel
 *         <p/>
 *         This class implements LRU strategy
 */
public class StrategyLRU extends AbstractStrategy {

    LinkedList<IdCount> lru = new LinkedList<IdCount>();

    StrategyLRU(int size, byte level) throws CacheException {
        super(size, level);
    }

    @Override
    public void put(Cacheable obj) {
        if (lru.size() < getSize()) {
            IncrementCounts();
            lru.add(new IdCount(obj.getObjectId(), 0));
            dataCache.putData(obj, null);
        } else {
            IdCount larger = lru.get(0);
            for (IdCount idcount : lru) {
                if (larger.count < idcount.count) larger = idcount;
            }
            lru.remove(larger);
            IncrementCounts();
            lru.add(new IdCount(obj.getObjectId(), 0));
            dataCache.putData(obj, larger.id);
        }
    }

    private void IncrementCounts() {
        for (IdCount idcount : lru) {
            idcount.count++;
        }
    }

    @Override
    public Cacheable get(Integer id) {
        for (IdCount idcount : lru) {
            if (idcount.id == id) {
                IncrementCounts();
                idcount.count = 0;
                return dataCache.getData(id);
            }
        }
        return null;
    }

    private class IdCount {
        public Integer id;
        public Integer count;

        IdCount(Integer id, Integer count) {
            this.id = id;
            this.count = count;
        }
    }

    @Override
    public ArrayList<Integer> getCachingIds() {

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (IdCount idcount : lru) {
            list.add(idcount.id);
        }
        return list;
    }
}
