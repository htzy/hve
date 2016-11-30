package com.huangshihe.game.awl.core;

import com.huangshihe.game.awl.manage.AwlCache;
import com.huangshihe.game.core.GameUser;
import junit.framework.TestCase;

/**
 * Created by Administrator on 2016/7/25.
 */
public class AwlTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
        // 由用户（用户id：1，用户名：111）创建一个awl游戏。
        AwlCache.getInstance().create(1, "111");
    }

    public void testInitCurrentTeamMembers() throws Exception {
        boolean flag = false;
        flag&=true;
        assertEquals(false, flag);
        flag=true;
        flag&=false;
        assertEquals(false, flag);
    }

    public void testAdd() throws Exception {
        // 获得id为1（即创建者的用户id为1）的awl游戏。
        Awl awl = AwlCache.getInstance().get(1);

        // 新建一个阿瓦隆游戏者，用户id为1,2,3,4,5
        assertEquals(true, awl.add(new AwlUser(1)));
        assertEquals(true, awl.add(new AwlUser(2)));
        assertEquals(true, awl.add(new AwlUser(3)));
        assertEquals(true, awl.add(new AwlUser(4)));
        AwlUser awlUser = new AwlUser(5);
        assertEquals(true, awl.add(awlUser));
        assertEquals(false, awl.add(awlUser));
        assertEquals(true, awl.remove(awlUser));
        assertEquals(4, awl.getGamers().size());

        assertEquals(true, awl.add(awlUser));

        AwlIdentity awlIdentity = new AwlIdentity(AwlIdentity.AwlIdentityEnum.LOYAL.ordinal());

        awl.getGamer(1).setIdentity(awlIdentity);

        assertEquals(2, awl.getGamer(1).getIdentity().getId());

        assertEquals("忠臣", awl.getGamer(1).getIdentity().getName());

        assertEquals("即：平民。", awl.getGamer(1).getIdentity().getDescription());

        assertEquals(5, awl.getGamers().size());

        assertEquals(1, awl.getGamer(1).getUserId());
    }

}