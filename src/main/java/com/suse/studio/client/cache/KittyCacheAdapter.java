package com.suse.studio.client.cache;

import com.spaceprogram.kittycache.KittyCache;

/**
 * Implementation of an {@link ObjectCache} using {@link KittyCache}.
 */
public class KittyCacheAdapter implements ObjectCache {

    private int secondsToLive;
    private KittyCache<String, Object> cache;

    public KittyCacheAdapter(int maxSize, int secondsToLive) {
        this.init(maxSize, secondsToLive);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(int maxSize, int secondsToLive) {
        this.secondsToLive = secondsToLive;
        cache = new KittyCache<String, Object>(maxSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(String key, Object value) {
        cache.put(key, value, secondsToLive);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(String key) {
        return cache.get(key);
    }
}
