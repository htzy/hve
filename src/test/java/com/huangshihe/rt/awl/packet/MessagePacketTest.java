package com.huangshihe.rt.awl.packet;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;

/**
 * Created by dell on 2016/10/22.
 */
public class MessagePacketTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testGetOperate() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = "{\"operate\":\"postTeam\", \"teamPacket\":{\"creatorNum\":0,\"members\":[0,1]}}";
        MessagePacket packet1 = objectMapper.readValue(message, MessagePacket.class);
        assertEquals("postTeam", packet1.getOperate());
        System.out.println("packet1 = " + packet1);
    }

    public void testGetData() throws Exception {

    }

}