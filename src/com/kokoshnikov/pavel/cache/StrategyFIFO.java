package com.kokoshnikov.pavel.cache;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Kokoshnikov Pavel
 *         <p/>
 *         This class implements FIFO strategy
 */
public class StrategyFIFO extends AbstractStrategy {

    private Deque<Integer> fifo = new LinkedList<Integer>();

    public StrategyFIFO(int size, byte level) throws CacheException {
        super(size, level);
    }

    @Override
    public void put(Cacheable obj) {
        if (fifo.size() < getSize()) {
            fifo.addFirst(obj.getObjectId());
            dataCache.putData(obj, null);
        } else {
            Integer deletedId = fifo.pollLast();
            fifo.addFirst(obj.getObjectId());
            dataCache.putData(obj, deletedId);
        }
    }

    @Override
    public Cacheable get(Integer id) {

        Cacheable o = null;
        if (fifo.contains(id))
            o = dataCache.getData(id);
        return o;
    }

    @Override
    public ArrayList<Integer> getCachingIds() {

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (Integer id : fifo) {
            list.add(id);
        }
        return list;
    }
}
