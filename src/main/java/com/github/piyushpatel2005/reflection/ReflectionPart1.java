package com.github.piyushpatel2005.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class MyClass {
    private MyClass() {
        System.out.println("MyClass object!");
    }
}
public class ReflectionPart1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clss = Class.forName("com.github.piyushpatel2005.reflection.MyClass");
        Constructor<?> con = clss.getDeclaredConstructor();
        con.setAccessible(true);
        MyClass newInstance = (MyClass) con.newInstance();
    }
}
