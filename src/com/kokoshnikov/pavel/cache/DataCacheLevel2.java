package com.kokoshnikov.pavel.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * @author Kokoshnikov Pavel
 *         <p/>
 *         This class provides access to the data for cache of level 2
 */
public class DataCacheLevel2 extends DataCache {

    @Override
    public Cacheable getData(Integer Id) {
        Cacheable obj = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(DataCache.getFilePrefix() + Id);
            ObjectInputStream oin = new ObjectInputStream(fis);
            obj = (Cacheable) oin.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    @Override
    public void putData(Cacheable putOb, Integer substituteId) {

        FileOutputStream fos = null;
        try {
            if (substituteId == null) {
                fos = new FileOutputStream(DataCache.getFilePrefix() + putOb.getObjectId());
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(putOb);
                oos.flush();
                oos.close();
            } else {
                File file = new File(DataCache.getFilePrefix() + substituteId);
                file.delete();
                fos = new FileOutputStream(DataCache.getFilePrefix() + putOb.getObjectId());
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(putOb);
                oos.flush();
                oos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
