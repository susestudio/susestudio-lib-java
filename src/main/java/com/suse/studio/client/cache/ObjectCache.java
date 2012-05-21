package com.suse.studio.client.cache;

/**
 * Interface representing a generic cache for objects.
 */
public interface ObjectCache {

    /**
     * Init the cache.
     *
     * @param maxObjects maxObjects
     * @param secondsToLive secondsToLive
     */
    abstract void init(int maxObjects, int secondsToLive);

    /**
     * Put an object to the cache.
     *
     * @param key key
     * @param value value
     */
    public void put(String key, Object value);

    /**
     * Get an object from the cache.
     *
     * @param key key
     * @return object
     */
    public Object get(String key);
}
