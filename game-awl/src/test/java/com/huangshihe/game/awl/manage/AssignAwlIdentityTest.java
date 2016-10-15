package com.huangshihe.game.awl.manage;

import com.huangshihe.game.awl.core.Awl;
import com.huangshihe.game.awl.core.AwlUser;
import com.huangshihe.game.core.GameUser;
import junit.framework.TestCase;

/**
 * Created by Administrator on 2016/7/26.
 */
public class AssignAwlIdentityTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        AwlCache.getInstance().create(1, "111");
    }

    public void testAssign() throws Exception {
        // 获得id为1（即创建者的用户id为1）的awl游戏。
        Awl awl = AwlCache.getInstance().get(1);

        // 新建一个阿瓦隆游戏者，用户id为1,2,3,4,5
        assertEquals(true, awl.add(new AwlUser(1)));
        assertEquals(true, awl.add(new AwlUser(2)));
        assertEquals(true, awl.add(new AwlUser(3)));
        assertEquals(true, awl.add(new AwlUser(4)));
        assertEquals(true, awl.add(new AwlUser(5)));

        new AssignAwlIdentity().assign(awl);
        for (GameUser gameUser : awl.getGamers()){
            System.out.println("gameUser.getNum() = " + gameUser.getNum());
            System.out.println("gameUser.getIdentity().getName() = " + gameUser.getIdentity().getName());
            System.out.println("gameUser.getInfo() = " + gameUser.getInfo());
        }
    }

}