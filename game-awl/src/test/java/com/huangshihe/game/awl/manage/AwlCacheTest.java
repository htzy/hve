package com.huangshihe.game.awl.manage;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2016/7/23.
 */
public class AwlCacheTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
        AwlCache.getInstance().remove(1);
    }

    public void testGetGame() throws Exception {
        assertEquals(null, AwlCache.getInstance().get(1));
        AwlCache.getInstance().create(1, "11");
        assertEquals(1, AwlCache.getInstance().get(1).getId());
    }

}