package com.kokoshnikov.pavel.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.kokoshnikov.pavel.CachingFactory;

/**
 * @author Kokoshnikov Pavel
 *         <p/>
 *         This class provide creating of cache instance. Also it provides access to this instance
 */
public class Cache {
    private static final String STRATEGY = "STRATEGY";
    private static final String LEVEL_SIZE_1 = "LEVEL_SIZE_1";
    private static final String LEVEL_SIZE_2 = "LEVEL_SIZE_2";

    private static final Cache cache = new Cache();
    private Strategies strategy = null;
    private AbstractStrategy cacheLevel1;
    private AbstractStrategy cacheLevel2;
    private CacheStatistic statistic;

    public CacheStatistic getStatistic() {
        return statistic;
    }

    private CachingFactory cachingFactory;

    private boolean trace = false;

    public void setTrace(boolean trace) {
        this.trace = trace;
    }

    private Cache() {
        try {
            loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        statistic = new CacheStatistic();
    }

    private void loadProperties() throws IOException {
        FileInputStream in = null;
        int sizeLevel1;
        int sizeLevel2;
        clearCacheFolder();
        createCacheFolder();
        try {
            Properties props = new Properties();
            in = new FileInputStream("cache.properties");
            props.load(in);
            in.close();


            if (props.getProperty(STRATEGY) == null) throw new CacheException("Property " + STRATEGY + "was not set");
            else
                strategy = Strategies.valueOf(props.getProperty(STRATEGY));

            if (props.getProperty(LEVEL_SIZE_1) == null) throw new CacheException("Property " + LEVEL_SIZE_1 + " was not set");
            else
                sizeLevel1 = Integer.parseInt(props.getProperty(LEVEL_SIZE_1));

            if (props.getProperty(LEVEL_SIZE_2) == null) throw new CacheException("Property " + LEVEL_SIZE_2 + " was not set");
            else
                sizeLevel2 = Integer.parseInt(props.getProperty(LEVEL_SIZE_2));

            if (strategy == Strategies.FIFO) {
                cacheLevel1 = new StrategyFIFO(sizeLevel1, (byte) 1);
                cacheLevel2 = new StrategyFIFO(sizeLevel2, (byte) 2);
            } else {
                cacheLevel1 = new StrategyLRU(sizeLevel1, (byte) 1);
                cacheLevel2 = new StrategyLRU(sizeLevel2, (byte) 2);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File was not found. Please check the file cache.properties.");
            e.printStackTrace();
        } catch (CacheException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) in.close();
        }
    }

    public static Cache getInstance() {
        return cache;
    }

    private void putObj(Cacheable o) {
        cacheLevel1.put(o);
        cacheLevel2.put(o);
    }

    public Cacheable getObj(Integer id) throws CacheException {
        Cacheable o = null;
        synchronized (this) {
            o = cacheLevel1.get(id);
            if (o == null) {
                o = cacheLevel2.get(id);
            } else {
                printMessage("Object has been found in the CacheLevel1");
                statistic.incHitLevel1();
                return o;
            }
            if (o == null) {
                if (cachingFactory != null) {
                    o = cachingFactory.loadObject(id);
                    if (o != null) {
                        putObj(o);
                        statistic.incMiss();
                        printMessage("Object has been loaded");
                    } else {
                        printMessage("Object has not been loaded");
                        throw new CacheException("Object has not been loaded");
                    }
                } else {
                    throw new CacheException("The Objects factory did not find");
                }
            } else {
                statistic.incHitLevel2();
                printMessage("Object has been found in the CacheLevel2");
            }
            return o;
        }
    }

    public void clearCacheFolder() {
        File file = new File(DataCache.cacheDir);
        String[] filesList = file.list();
        if (filesList != null) {
            for (String fileName : filesList) {
                File cacheFile = new File(DataCache.cacheDir + "\\" + fileName);
                cacheFile.delete();
            }
        }
    }

    private void printMessage(String message) {
        if (trace == true) System.out.println(message);
    }

    private void createCacheFolder() {
        File file = new File(DataCache.cacheDir);
        file.mkdirs();
    }

    public void setFactoryObjects(CachingFactory cachingFactory) {
        this.cachingFactory = cachingFactory;
    }

    @Override
    public String toString() {

        String string = "Cache \n" + "Strategy:";
        if (cacheLevel1 instanceof StrategyFIFO) {
            string += Strategies.FIFO;
        } else {
            string += Strategies.LRU;
        }
        string += "\nSize level1: " + cacheLevel1.getSize() + "\n";
        string += "Size level2: " + cacheLevel2.getSize() + "\n";
        string += getInformation();
        return string;
    }

    public String getInformation() {
        String string = "CacheLevel1 contains following Ids: \n";
        string += cacheLevel1.getCachingIds().toString() + "\n";
        string += "CacheLevel2 contains following Ids: \n";
        string += cacheLevel2.getCachingIds().toString();

        return string;
    }
}
