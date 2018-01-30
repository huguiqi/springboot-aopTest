package com.example.demo;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by guiqi on 2018/1/19.
 */
public class StringTest {

    /**
     * 首字母转大写
     * @param name
     * @return
     */
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);

    }

    @Test
    public void testCaptureName(){

        Assert.assertEquals("eq","Name",captureName("name"));
    }


    private Object givenInvokeGetFieldValue(Object instance,String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String getter = "get" + captureName(name);
        return instance.getClass().getDeclaredMethod(getter).invoke(instance);
    }


}
