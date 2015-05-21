package com.kokoshnikov.pavel;

import com.kokoshnikov.pavel.cache.Cacheable;
import com.kokoshnikov.pavel.cache.FactoryObjects;

/**
 * @author Kokoshnikov Pavel
 *         <p/>
 *         This is simple implementation of FactoryObjects
 */
public class CachingFactory implements FactoryObjects {

    @Override
    public Cacheable loadObject(Integer id) {

        return new CachingObject("object number :" + id, id);
    }

}
