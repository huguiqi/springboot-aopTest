package com.example.demo;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by guiqi on 2018/1/19.
 */
public class StringTest {

    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);

    }

    @Test
    public void testCaptureName(){

        Assert.assertEquals("eq","Name",captureName("name"));
    }
}
