package com.wzb.support.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 作者 文泽彪
 * 时间 2016/5/30 0030.
 */
public enum Event {
    INSTANCE;
    private List<Object> list = new ArrayList<>();

    public void register(Object object) {
        list.add(object);
    }

    public void unregister(Object object) {
        list.remove(object);
    }

    public void post(String methedName, Object... params) {
        for (Object obj : list) {
            Class clz = obj.getClass();
            Class[] arr = new Class[params.length];
            Object[] p = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                arr[i] = params[i].getClass();
                p[i] = params[i];
            }
            try {
                Method method = clz.getMethod(methedName, arr);
                System.out.println(method);
                method.invoke(obj, p);
                // method.invoke(arg0, arg1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
