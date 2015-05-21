package com.kokoshnikov.pavel.cache;

public class CacheStatistic {

    private int hitLevel1 = 0;
    private int hitLevel2 = 0;
    private int miss = 0;

    public int getHitLevel1() {
        return hitLevel1;
    }

    public void incHitLevel1() {
        hitLevel1++;
    }

    public int getHitLevel2() {
        return hitLevel2;
    }

    public void incHitLevel2() {
        hitLevel2++;
    }

    public int getMiss() {
        return miss;
    }

    public void incMiss() {
        miss++;
    }

    public void refresh() {
        hitLevel1 = 0;
        hitLevel2 = 0;
        miss = 0;
    }

}
