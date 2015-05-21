package com.kokoshnikov.pavel.cache;

/**
 * @author Kokoshnikov Pavel
 *         <p/>
 *         This interface must be implemented by class which will create new
 *         instances of cached objects
 */
public interface FactoryObjects {
    public Cacheable loadObject(Integer id);
}
