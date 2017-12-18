package com.example.demo.common;

import com.example.demo.common.annotation.CheckPermission;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * Created by guiqi on 2017/12/18.
 */

@Component
public class CheckPermissionImpl implements CheckPermission {


    @Override
    public String value() {
        return null;
    }

    @Override
    public boolean check() {

        return false;
    }


}
