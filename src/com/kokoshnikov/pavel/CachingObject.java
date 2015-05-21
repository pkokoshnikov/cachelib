package com.kokoshnikov.pavel;

import com.kokoshnikov.pavel.cache.Cacheable;

/**
 * @author Kokoshnikov Pavel
 *         <p/>
 *         This is simple implementation of Cacheable
 */
public class CachingObject implements Cacheable {

    private static final long serialVersionUID = 7692348428522253710L;

    String nameObj;
    Integer Id;

    public CachingObject(String nameObj, Integer id) {
        this.Id = id;
        this.nameObj = nameObj;
    }

    @Override
    public Integer getObjectId() {
        return Id;
    }

    public String getNameObject() {
        return nameObj;
    }
}
