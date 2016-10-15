package com.huangshihe.game.awl.core;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2016/7/25.
 */
public class AwlIdentityTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();

    }

    public void testGetName() throws Exception {
        AwlIdentity awlIdentity = new AwlIdentity(AwlIdentity.AwlIdentityEnum.ASSASSIN.ordinal());
//        assertEquals("1", awlIdentity.getName(0));
        assertEquals(4, AwlIdentity.AwlIdentityEnum.ASSASSIN.ordinal());
    }

    public void testGetDescription() throws Exception {

    }

}