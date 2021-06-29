package com.hit.algo;

import org.junit.Assert;
import org.junit.Test;

public class AlgoTest {

    @Test
    public void testSecondChance_tryRemoveOnce_elementIsRemoved() {
        IAlgoCache<Integer, Integer> cache = CacheImpl.createAlgo(CacheImpl.SECOND_CHANCE, 0);

        Integer key = 1;
        cache.putElement(key, 3);
        cache.removeElement(key);

        Assert.assertNull("Element supposed to be removed", cache.getElement(key));
    }

    @Test
    public void testSecondChance_tryRemoveTwice_elementIsNotRemoved() {
        IAlgoCache<Integer, Integer> cache = CacheImpl.createAlgo(CacheImpl.SECOND_CHANCE, 0);

        Integer key = 1;
        cache.putElement(key, 3);
        cache.putElement(key, 3);
        cache.removeElement(key);

        Assert.assertNotNull("Element was not supposed to be removed", cache.getElement(key));
    }

    @Test
    public void testSecondChance_tryRemoveTwice_elementIsRemovedAfterSecondTime() {
        IAlgoCache<Integer, Integer> cache = CacheImpl.createAlgo(CacheImpl.SECOND_CHANCE, 0);

        Integer key = 1;
        cache.putElement(key, 3);
        cache.putElement(key, 3);
        cache.removeElement(key);
        cache.removeElement(key);

        Assert.assertNull("Element supposed to be removed", cache.getElement(key));
    }
@Test
    public void testLru_remove_after_cleared() {
        IAlgoCache<Integer, Integer> cache = CacheImpl.createAlgo(CacheImpl.LRU, 1);

        Integer key = 1;
        cache.putElement(key, 3);
        cache.clear();
        Assert.assertNull("Element supposed to be removed", cache.getElement(key));
    }
}
