package com.kokoshnikov.pavel.cache;

import java.io.Serializable;

/**
 * 
 * @author Kokoshnikov Pavel
 * 
 * This interface must be implemented by class which wants to use Cache.
 *
 */
public interface Cacheable extends Serializable {
	public Integer getObjectId();
}
