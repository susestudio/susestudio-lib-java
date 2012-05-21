package com.suse.studio.client.cache;

/**
 * This class encapsulates the actual cache implementation.
 */
public class CacheManager {

    private static ObjectCache cache;

    private CacheManager() {
    }

    /**
     * Return the {@link ObjectCache} object.
     * @return cache
     */
    public static ObjectCache getCache() {
        if (cache == null) {
            try {
                Class.forName("com.spaceprogram.kittycache.KittyCache");
                cache = new KittyCacheAdapter(5000, 120);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return cache;
    }
}
