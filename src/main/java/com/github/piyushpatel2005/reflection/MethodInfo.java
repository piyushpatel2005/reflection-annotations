package com.github.piyushpatel2005.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInfo {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Entity e = new Entity(10, "id");
        Class<? extends Entity> clss = e.getClass();
        System.out.println("====== Get methods on a class and its super class (only public) ========");
        Method[] methods =  clss.getMethods();
        for(Method m: methods) {
            System.out.println(m.getName());
        }
        System.out.println("====== Get methods from only Entity class (private + public) ======");
        Method[] declaredMethods =  clss.getDeclaredMethods();
        for(Method m: declaredMethods) {
            System.out.println(m.getName());
        }
        System.out.println("======= Get details of a method ========");
        Method method = clss.getMethod("setVal", int.class);
        method.invoke(e, 20);
        System.out.println("Value changed to " + e.getVal());
        Method getMethod = clss.getMethod("getVal", null);
        System.out.println(getMethod.invoke(e, null));
    }
}
