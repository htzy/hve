package com.huangshihe.game.manage;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2016/7/25.
 */
public class AssignIdentityTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();

    }

    public void testAssign() throws Exception {

    }

    public void testGetRandomNums() throws Exception {
        int[] r = {1, 2, 3, 4, 5};
        for (int i : AssignIdentity.getRandomNums(5)){
            System.out.println("i = " + i);
        }
//        assertEquals(r, AssignIdentity.getRandomNums(5));
    }

}